package com.lm.rxtest.prestener;

import android.net.Uri;
import android.util.Log;

import com.lm.rxtest.base.BasePresenter;
import com.lm.rxtest.common.Api;
import com.lm.rxtest.common.MyApplication;
import com.lm.rxtest.model.UserInfoModel;
import com.lm.rxtest.net.UploadFileRequestBody;
import com.lm.rxtest.view.IMainView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;

/**
 * Created by Administrator on 2017/9/21.
 */
public class MainPrestener extends BasePresenter<IMainView> {

    public void getUserInfo() {
        Api.getApi().search("15170193726", "fffffff")
                .compose(callbackOnIOThread())
                .compose(verifyOnMainThread())
                .subscribe(new NetSubscriber<UserInfoModel>() {
                    @Override
                    public void onNext(UserInfoModel userInfoModel) {
                        super.onNext(userInfoModel);
                        getView().getUserInfo(userInfoModel);
                    }

                });
    }

    public void getUserInfo1() {
        Api.getApi().search("15170193726", "fffffff")

                .compose(callbackOnIOThread())
                .compose(verifyOnMainThread())

                .subscribe(new NetSubscriber<UserInfoModel>() {
                    @Override
                    public void onNext(UserInfoModel userInfoModel) {
                        super.onNext(userInfoModel);
                        getView().getUserInfo1(userInfoModel);
                    }
                });
    }

    public void longin() {
        Api.getApi().search("15170193726", "fffffff")
                .compose(callbackOnIOThread())
                .compose(verifyOnMainThread()).subscribe(new NetSubscriber<UserInfoModel>() {
            @Override
            public void onNext(UserInfoModel userInfoModel) {
                super.onNext(userInfoModel);
                getView().login();//

            }
        });
    }

    /**
     * 文件上传
     */
    public void upLoadFile() {
        File file = new File("filepath");
        //Map<String, RequestBody> requestBodyMap = new HashMap<>();
        // requestBodyMap.put("file\"; filename=\"" + file.getName(), fileRequestBody);
        UploadFileRequestBody fileRequestBody = new UploadFileRequestBody(file, new UploadFileRequestBody.ProgressListener() {
            @Override
            public void onProgress(long hasWrittenLen, long totalLen, boolean hasFinish) {

            }
        });
        // MultipartBody.Part  和后端约定好Key，这里的partName是用image
        MultipartBody.Part body =
                MultipartBody.Part.createFormData("image", file.getName(), fileRequestBody);
        Api.getApi().upload(fileRequestBody, body)
                .compose(callbackOnIOThread())
                .compose(verifyOnMainThread()).subscribe(new NetSubscriber<ResponseBody>() {
            @Override
            public void onNext(ResponseBody responseBody) {
                super.onNext(responseBody);
            }
        });

    }

    /**
     * 文件下载
     */
    public void downLoadFile() {
        String all_url="https://github.com/wzgiceman/RxjavaRetrofitDemo-master/archive/master.zip";//全路径

        Uri url = Uri.parse(all_url);

        //拆分两个
        String base_url="https://github.com/";
        String jie_url="wzgiceman/RxjavaRetrofitDemo-master/archive/master.zip";
        Api.getDownLoadApi(base_url,(total, progress) -> {
            getView().downProgress(total, progress * 100 / total);
        })
                .download(jie_url)
                .compose(callbackOnIOThread())
                .subscribe(new NetSubscriber<ResponseBody>() {
                    @Override
                    public void onNext(ResponseBody responseBody) {
                        super.onNext(responseBody);
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
                });
    }


}
