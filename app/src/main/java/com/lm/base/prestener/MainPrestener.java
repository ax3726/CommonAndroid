package com.lm.base.prestener;

import android.net.Uri;
import android.util.Log;


import com.lm.base.common.Api;
import com.lm.base.common.MyApplication;
import com.lm.base.model.BaseBean;
import com.lm.base.model.TestModel;
import com.lm.base.model.UserInfoModel;

import com.lm.base.view.IMainView;
import com.lm.lib_common.base.BaseNetListener;
import com.lm.lib_common.base.BasePresenter;
import com.lm.lib_common.net.UploadFileRequestBody;

import org.reactivestreams.Subscriber;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

import io.reactivex.Flowable;
import okhttp3.MultipartBody;
import okhttp3.ResponseBody;

/**
 * Created by Administrator on 2017/9/21.
 */
public class MainPrestener extends BasePresenter<IMainView> {

    public void getUserInfo() {
        Api.getApi().search("15170193726", "fffffff",new TestModel("测试name==黎明"))
                .compose(callbackOnIOToMainThread())
                .subscribe(new BaseNetListener<UserInfoModel>(this,true) {
                    @Override
                    public void onSuccess(UserInfoModel userInfoModel) {
                        getView().getUserInfo(userInfoModel);
                    }
                    @Override
                    public void onFail(String errMsg) {

                    }
                });
    }

    public void getUserInfo1() {
        Api.getApi().search1()
                .compose(callbackOnIOToMainThread())
                .subscribe(new BaseNetListener<UserInfoModel>(this) {
                    @Override
                    public void onSuccess(UserInfoModel userInfoModel) {
                        getView().getUserInfo1(userInfoModel);
                    }

                    @Override
                    public void onFail(String errMsg) {
                    }
                });

    }



    /**
     * 文件上传
     */
    public void upLoadFile(String name, String gender, String imgPath) {

        File flie = new File("imgPath");

        UploadFileRequestBody fileRequestBody = new UploadFileRequestBody(flie, new UploadFileRequestBody.ProgressListener() {
            @Override
            public void onProgress(long hasWrittenLen, long totalLen, boolean hasFinish) {
                long l = hasWrittenLen * 100 / totalLen;
                getView().showToast(l + "%");
            }
        });

        // MultipartBody.Part  和后端约定好Key，这里的partName是用image
        MultipartBody.Part body =
                MultipartBody.Part.createFormData("file", flie.getName(), fileRequestBody);

        Api.getApi().getLoginRegisterImage(name, gender, body)
                .compose(callbackOnIOToMainThread()).subscribe(new BaseNetListener<BaseBean>(this) {
            @Override
            public void onSuccess(BaseBean baseBean) {

            }

            @Override
            public void onFail(String errMsg) {

            }
        });

    }


    /**
     * 文件下载
     */
    public void downLoadFile() {
        String all_url = "https://github.com/wzgiceman/RxjavaRetrofitDemo-master/archive/master.zip";//全路径
        Uri url = Uri.parse(all_url);
        //拆分两个
        String base_url = "https://github.com/";
        String jie_url = "wzgiceman/RxjavaRetrofitDemo-master/archive/master.zip";
        Api.getDownLoadApi(base_url, (total, progress) -> {
            getView().downProgress(total, progress * 100 / total);
        }).download(jie_url)
                .compose(callbackOnIOToMainThread())
                .subscribe(new BaseNetListener<ResponseBody>(this) {
                    @Override
                    public void onSuccess(ResponseBody responseBody) {
                        InputStream is = null;
                        byte[] buf = new byte[2048];
                        int len;
                        FileOutputStream fos = null;
                        try {
                            is = responseBody.byteStream();
                            File dir = new File(MyApplication.Base_Path + "/zip");
                            if (!dir.exists()) {
                                dir.mkdirs();
                            }
                            File file = new File(dir, "RxjavaRetrofitDemo-master-master.zip");
                            fos = new FileOutputStream(file);
                            while ((len = is.read(buf)) != -1) {
                                fos.write(buf, 0, len);
                            }
                            fos.flush();
                            getView().showToast("下载完毕!");
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        } finally {
                            try {
                                if (is != null) is.close();
                                if (fos != null) fos.close();
                            } catch (IOException e) {
                                Log.e("saveFile", e.getMessage());
                            }
                        }

                    }

                    @Override
                    public void onFail(String errMsg) {

                    }
                });



    }


}
