package com.lm.lib_common.utils;

import android.app.Notification;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.widget.RemoteViews;
import android.widget.Toast;



import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Administrator on 2015/5/23.
 * 更新下载工具
 */
public class UpdateDownloadUtils {

    public static final int FAILURE = 1;//失败
    public static final int SUCCESS = 2;//成功
    public static final int PROGRESS = 3;//进度

    private Context mContext;
    private Handler mHandler;
    private DownloadListener mDownloadListener;

    public UpdateDownloadUtils(Context context, Handler handler) {
        this.mContext = context;
        this.mHandler = handler;
    }

    public UpdateDownloadUtils(Context context, DownloadListener downloadListener) {
        this.mContext = context;
        this.mDownloadListener = downloadListener;
    }

    private OkHttpClient okHttpClient = new OkHttpClient();

    public void downloadFile(String url, final String packName) {
        Request request = new Request.Builder().url(url).tag(this).build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                mHandler.sendEmptyMessage(FAILURE);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    File file = saveFile(response, packName);
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

    public File saveFile(Response response, String packName) throws IOException {
        InputStream is = null;
        byte[] buf = new byte[2048];
        int len = 0;
        FileOutputStream fos = null;
        try {
            is = response.body().byteStream();
            final long total = response.body().contentLength();
            long sum = 0;

            String tempPath = Utils.getCacheDirectory(mContext, Environment.DIRECTORY_DOWNLOADS).getAbsolutePath();
            File file = new File(tempPath+"/" + packName + ".zip");
            // 若存在则删除
            if (file.exists()) {
                file.delete();
            }
            // 创建文件
            file.createNewFile();
            fos = new FileOutputStream(file);
            while ((len = is.read(buf)) != -1) {
                sum += len;
                fos.write(buf, 0, len);
                final long finalSum = sum;
                Message msg = new Message();
                int v = (int) (finalSum * 1.0f / total * 100);
                msg.obj = v;
                msg.what = PROGRESS;
                mHandler.sendMessage(msg);

            }
            fos.flush();

            return file;

        } finally {
            try {
                if (is != null) is.close();
            } catch (IOException e) {
            }
            try {
                if (fos != null) fos.close();
            } catch (IOException e) {
            }

        }
    }

    public void startDownLoad(String url, final String packName) {
        DownFileAsyncTask downFileAsyncTask = new DownFileAsyncTask(url);
        String tempPath = Utils.getCacheDirectory(mContext, Environment.DIRECTORY_DOWNLOADS).getAbsolutePath();
        downFileAsyncTask.execute(tempPath + "/" + packName + ".zip");
    }


    //异步任务下载文件
    class DownFileAsyncTask extends AsyncTask<String, Long, File> {
        private int lastPosition = 0;
        private boolean autoCancel;

        private String downUrl = "";

        public DownFileAsyncTask(String downUrl) {
            this.downUrl = downUrl;
        }

        @Override
        protected File doInBackground(String... params) {
            try {
                FileOutputStream fos = null;
                BufferedInputStream bis = null;
                HttpURLConnection httpUrl = null;
                URL url = null;
                byte[] buf = new byte[2048];
                int size = 0;

                //建立链接
                url = new URL(downUrl);
                httpUrl = (HttpURLConnection) url.openConnection();
                //设置网络连接超时时间5S
                httpUrl.setConnectTimeout(10 * 1000);
                //连接指定的资源
                httpUrl.connect();
                //获取网络输入流
                bis = new BufferedInputStream(httpUrl.getInputStream());
                //建立文件
                File file = new File(params[0]);
                if (!file.exists()) {
                    if (!file.getParentFile().exists()) {
                        file.getParentFile().mkdirs();
                    }
                    file.createNewFile();
                }
                fos = new FileOutputStream(params[0]);

                final long total = httpUrl.getContentLength();
                long sum = 0;

                //保存文件
                while ((size = bis.read(buf)) != -1) {
                    sum += size;
                    fos.write(buf, 0, size);
                    publishProgress(total, sum);
                }
                fos.close();
                bis.close();
                httpUrl.disconnect();
                return file;
            } catch (IOException e) {

            }
            return null;
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
            if (!autoCancel)
                Toast.makeText(mContext, "已取消下载", Toast.LENGTH_LONG).show();
        }

        @Override
        protected void onPostExecute(File file) {
            super.onPostExecute(file);
            if (file != null) {
                if (mDownloadListener != null) {
                    mDownloadListener.onSuccess(file);
                }
            } else {
                if (mDownloadListener != null) {
                    mDownloadListener.onFail();
                }
            }

        }

        @Override
        protected void onProgressUpdate(Long... values) {
            super.onProgressUpdate(values);
            long total = values[0];
            long sum = values[1];
            int rate = (int) (sum * 100 / total);
            if ((rate % 1 == 0 || rate == 100) && rate != lastPosition) {
                if (rate < 100) {

                } else {

                }
                if (mDownloadListener != null) {
                    mDownloadListener.onProgress(total, sum, rate);
                }

            }
            lastPosition = rate;

        }
    }

    public interface DownloadListener {
        void onFail();

        void onSuccess(File file);

        void onProgress(long total, long sum, int progress);
    }

}
