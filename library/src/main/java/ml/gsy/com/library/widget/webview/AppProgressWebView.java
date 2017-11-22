package ml.gsy.com.library.widget.webview;

import android.content.Context;
import android.os.Handler;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.webkit.JsResult;
import android.webkit.WebSettings;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.webkit.WebSettings.ZoomDensity;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.TextView;

import ml.gsy.com.library.R;


public class AppProgressWebView extends WebView {
    private static ProgressBar progressbar;
    private static TextView mTextViewTitle;
    private static Handler mProgressHandler;

    public void setProgressHandler(Handler mProgressHandler) {
        this.mProgressHandler = mProgressHandler;
    }

    public void setTitleText(TextView t) {
        mTextViewTitle = t;
    }

    public AppProgressWebView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    public AppProgressWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public AppProgressWebView(Context context) {
        super(context);
        init(context);
    }

    @SuppressWarnings("deprecation")
    private void init(Context c) {
        setBackgroundColor(getResources().getColor(R.color.main_item_bg_color));
        WebSettings webSettings = getSettings();

        // 支持javascript
        webSettings.setJavaScriptEnabled(true);

        webSettings.setPluginState(WebSettings.PluginState.ON);
        // 设置可以支持缩放
        webSettings.setSupportZoom(true);
        // 设置出现缩放工具
        webSettings.setBuiltInZoomControls(false);
        webSettings.setDefaultZoom(ZoomDensity.CLOSE);
        // 扩大比例的缩放
        webSettings.setUseWideViewPort(true);


        webSettings.setSavePassword(true);
        webSettings.setSaveFormData(true);// 保存表单数据
        webSettings.setJavaScriptEnabled(true);


        webSettings.setDomStorageEnabled(true);
        webSettings.setSupportMultipleWindows(true);// 新加
        // 自适应屏幕
        webSettings.setLayoutAlgorithm(LayoutAlgorithm.SINGLE_COLUMN);
        webSettings.setLoadWithOverviewMode(true);

        progressbar = new ProgressBar(c, null,
                android.R.attr.progressBarStyleHorizontal);
        progressbar
                .setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
                        getResources()
                                .getDimensionPixelOffset(R.dimen.space_10), 0,
                        0));
        progressbar.setProgressDrawable(getResources().getDrawable(R.drawable.progress_bar_states));
        progressbar.setProgress(10);
        addView(progressbar);
        setWebViewClient(new WebWebViewClient());
        setWebChromeClient(new WebChromeClient());
    }

    public static class WebChromeClient extends android.webkit.WebChromeClient {

        @Override
        public View getVideoLoadingProgressView() {
            return super.getVideoLoadingProgressView();
        }

        @Override
        public void onShowCustomView(View view, CustomViewCallback callback) {
            super.onShowCustomView(view, callback);

        }

        @Override
        public void onHideCustomView() {
            super.onHideCustomView();

        }

        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            if (newProgress == 100) {
                progressbar.setProgress(100);
                new Handler().postDelayed(runnable, 200);
            } else if (progressbar.getVisibility() == GONE) {
                progressbar.setVisibility(VISIBLE);
            }
            progressbar.setProgress(newProgress + progressbar.getProgress());
            super.onProgressChanged(view, newProgress);
        }

        @Override
        public void onReceivedTitle(WebView view, String title) {
            if (mTextViewTitle != null) {
                if (title.isEmpty()) {
                    mTextViewTitle.setText(R.string.app_name);
                } else {
                    mTextViewTitle.setText(title);
                }
            }
            super.onReceivedTitle(view, title);
        }

        @Override
        public boolean onJsAlert(WebView view, String url, String message,
                                 JsResult result) {
            // 监测弹窗
            return super.onJsAlert(view, url, message, result);
        }

    }

    public class WebWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            // 网页点击
//			if (!url.isEmpty()) {
//				view.loadUrl(url);
//			}
            return super.shouldOverrideUrlLoading(view, url);
//			return true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            if (!isError) {
                if (mProgressHandler != null) {
                    // 错误的情况
                    mProgressHandler.sendEmptyMessage(1);
                }
            }
            /**
             * 防止title初始化为空的情况
             */
            if (view != null) {
                if (!TextUtils.isEmpty(view.getTitle())) {
                    //mTextViewTitle.setText(view.getTitle());
                }
                view.loadUrl("javascript:window._obj.getWebViewCode(document.body.innerHTML);");
            }
            super.onPageFinished(view, url);
        }

        /**
         * 执行错误了不执行成功
         */
        private boolean isError;

        @Override
        public void onReceivedError(WebView view, int errorCode,
                                    String description, String failingUrl) {
            isError = true;
            view.stopLoading();
            view.removeAllViews();
            if (mProgressHandler != null) {
                // 错误的情况
                mProgressHandler.sendEmptyMessage(-1);
            }
            super.onReceivedError(view, errorCode, description, failingUrl);
        }

    }

    // @Override
    // protected void onScrollChanged(int l, int t, int oldl, int oldt) {
    // LayoutParams lp = (LayoutParams) progressbar.getLayoutParams();
    // lp.x = l;
    // lp.y = t;
    // progressbar.setLayoutParams(lp);
    // super.onScrollChanged(l, t, oldl, oldt);
    // }

    private static Runnable runnable = new Runnable() {
        @Override
        public void run() {
            progressbar.setVisibility(View.GONE);
        }
    };

    public void loadTextToHtml(String content) {
        loadDataWithBaseURL(null, content, "text/html", "utf-8", null);
    }

}
