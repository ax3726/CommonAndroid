package ml.gsy.com.library.utils;

import android.content.Context;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;




import java.io.*;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Administrator on 2015/5/23.
 * 更新下载工具
 */
public class VideoDownloadUtils {

    public static final int FAILURE = 1;//失败
    public static final int SUCCESS = 2;//成功
    public static final int PROGRESS = 3;//进度

    private Context mContext;
    private Handler mHandler;

    public static final String VIDEO_CACHE_PATH = Utils.getMemeoryPath("Android/data/cacheData/lebaos/video");

    public VideoDownloadUtils(Context context, Handler handler) {
        this.mContext = context;
        this.mHandler = handler;
    }
    private OkHttpClient okHttpClient= new OkHttpClient();



    public void download(String url, final String packName, final String Account) {

        Request request = new Request.Builder().url(url).tag(this).build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                mHandler.sendEmptyMessage(FAILURE);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    File file = saveFile(response, packName,Account);
                    Message msg = new Message();
                    msg.obj = file;
                    msg.what = SUCCESS;
                    mHandler.sendMessage(msg);

                } catch (IOException e) {
                    mHandler.sendEmptyMessage(FAILURE);
                    e.printStackTrace();
                }
            }


        });

    }

    public File saveFile(Response response,String packName,String Account) throws IOException
    {
        InputStream is = null;
        byte[] buf = new byte[2048];
        int len = 0;
        FileOutputStream fos = null;
        try
        {
            is = response.body().byteStream();
            final long total = response.body().contentLength();
            long sum = 0;
            File dirFile = new File( Utils.getMemeoryPath("Android/data/cacheData/"+Account+"/video"));
            if (!dirFile.exists()) {
                dirFile.mkdirs();
            }
//
            File file = new File(dirFile.getAbsolutePath(), packName);
            // 若存在则删除
            if (file.exists()) {
                file.delete();
            }
            // 创建文件
            file.createNewFile();
            fos = new FileOutputStream(file);
            while ((len = is.read(buf)) != -1)
            {
                sum += len;
                fos.write(buf, 0, len);
                final long finalSum = sum;
                Message msg = new Message();
                int v = (int) (finalSum * 1.0f / total * 100);
                msg.obj =   v;
                msg.what = PROGRESS;
                mHandler.sendMessage(msg);

            }
            fos.flush();

            return file;

        } finally
        {
            try
            {
                if (is != null) is.close();
            } catch (IOException e)
            {
            }
            try
            {
                if (fos != null) fos.close();
            } catch (IOException e)
            {
            }

        }
    }
}
