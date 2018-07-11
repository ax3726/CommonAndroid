package com.lm.base.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.lm.base.R;
import com.lm.lib_common.utils.UpdateDownloadUtils;


import java.io.File;

public class DownLoadActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_down_load);
    }

    public void startDownLoad(View view) {
        UpdateDownloadUtils updateDownloadUtils = new UpdateDownloadUtils(this, new UpdateDownloadUtils.DownloadListener() {
            @Override
            public void onFail() {
                Toast.makeText(DownLoadActivity.this,"下载失败!",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSuccess(File file) {
                Toast.makeText(DownLoadActivity.this,"下载成功!",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onProgress(long total, long sum, int progress) {
                Toast.makeText(DownLoadActivity.this,"下载中："+progress,Toast.LENGTH_SHORT).show();

            }
        });
        updateDownloadUtils.startDownLoad("https://github.com/ax3726/SlideHover/archive/master.zip", "测试的");

    }

    public void startDownLoad1(View view)
    {
        UpdateDownloadUtils   mUdl = new UpdateDownloadUtils(getApplicationContext(), downloadHandler);
        try {
            mUdl.downloadFile("https://github.com/ax3726/SlideHover/archive/master.zip", "测试的");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    Handler downloadHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case UpdateDownloadUtils.FAILURE:
                    Toast.makeText(DownLoadActivity.this,"下载失败!",Toast.LENGTH_SHORT).show();
                    break;
                case UpdateDownloadUtils.SUCCESS://下载 成功
                    Toast.makeText(DownLoadActivity.this,"下载成功!",Toast.LENGTH_SHORT).show();
                    break;
                case UpdateDownloadUtils.PROGRESS://下载 进度
                    int progress = (int) msg.obj;
                    Toast.makeText(DownLoadActivity.this,"下载中："+progress,Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

}
