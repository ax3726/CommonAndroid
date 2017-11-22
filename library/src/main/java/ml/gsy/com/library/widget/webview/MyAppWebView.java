package ml.gsy.com.library.widget.webview;


import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import ml.gsy.com.library.R;


public class MyAppWebView extends WebView {

	public MyAppWebView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		TypedArray a = context.obtainStyledAttributes(attrs,
				R.styleable.webview_is_cloumn, defStyle, 0);
		isSingleCloumn = a.getBoolean(R.styleable.webview_is_cloumn_is_column,
				true);
		if (a != null) {
			a.recycle();
		}
		init();
	}

	public MyAppWebView(Context context, AttributeSet attrs) {
		super(context, attrs);
		TypedArray a = context.obtainStyledAttributes(attrs,
				R.styleable.webview_is_cloumn);
		isSingleCloumn = a.getBoolean(R.styleable.webview_is_cloumn_is_column,
				true);
		if (a != null) {
			a.recycle();
		}
		init();
	}

	public MyAppWebView(Context context) {
		super(context);
		init();
	}

	private boolean isSingleCloumn;

	private void init() {
		getSettings().setJavaScriptEnabled(true);
		getSettings().setSupportZoom(false);
		getSettings().setBuiltInZoomControls(false);
		addJavascriptInterface(new WebViewImageClick(), "imgclick");
		if (isSingleCloumn) {
			getSettings().setLayoutAlgorithm(LayoutAlgorithm.SINGLE_COLUMN);
		}
		getSettings().setBlockNetworkImage(true);
		getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
		this.setWebViewClient(new WebViewClient() {

			@Override
			public void onPageStarted(WebView view, String url, Bitmap favicon) {
				if (mWebViewLoadStatus != null) {
					mWebViewLoadStatus.pageOnStart();
				}
				super.onPageStarted(view, url, favicon);
			}

			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				// if (!StringUtil.isEmptyString(url)) {
				// if (url.startsWith("tel:")) {
				// Intent intent = new Intent(Intent.ACTION_VIEW, Uri
				// .parse(url));
				// ((MyBaseActivity) view.getContext())
				// .startMyActivity(intent);
				// return true;
				// }
				// if (url.contains("link://")) {
				// String param = "";
				// String st = url.replace("link://", "");
				// if (!StringUtil.isEmptyString(st)) {
				// String[] temp = st.split("/");
				// if (temp.length > 1) {
				// param = temp[1];
				// }
				// if (!StringUtil.isEmptyString(param)) {
				// try {
				// param = URLDecoder.decode(param, "UTF-8");
				// } catch (UnsupportedEncodingException e) {
				// // TODO Auto-generated catch block
				// e.printStackTrace();
				// }
				// }
				// AdvertEnum em = AdvertEnum.valueOf(
				// AdvertEnum.class, temp[0]);
				// if (em == null) {
				// return super
				// .shouldOverrideUrlLoading(view, url);
				// }
				// ((MyBaseActivity) view.getContext())
				// .startMyActivity(AppConfig
				// .startTypeActivity(em.ordinal(),
				// param, view.getContext()));
				// return true;
				// } else {
				// return super.shouldOverrideUrlLoading(view, url);
				// }
				// }
				// if (url.contains("http://") || url.contains("https://")) {
				// if (url.contains("AdHtml")) {
				// return super.shouldOverrideUrlLoading(view, url);
				// }
				// Intent intent = new Intent();
				// intent.putExtra("id", url);
				// ((MyBaseActivity) view.getContext()).startMyActivity(
				// intent, CeeNlWebActivity.class);
				// return true;
				// }
				// }
				return super.shouldOverrideUrlLoading(view, url);
			}

			@Override
			public void onPageFinished(WebView view, String url) {
				getSettings().setBlockNetworkImage(false);
				if (mWebViewLoadStatus != null) {
					mWebViewLoadStatus.pageOnFinish();
				}
				super.onPageFinished(view, url);
			}
		});
		setWebChromeClient(new WebChromeClient() {
			@Override
			public void onProgressChanged(WebView view, int newProgress) {
				if (mWebViewLoadStatus != null) {
					mWebViewLoadStatus.pageOnLoadProgress(newProgress);
				}
				super.onProgressChanged(view, newProgress);
			}

		});
	}

	private WebViewLoadStatus mWebViewLoadStatus;

	public void setWebViewLoadStatus(WebViewLoadStatus web) {
		this.mWebViewLoadStatus = web;

	}

	/**
	 * 添加java对象到页面中
	 * 
	 * @param obj
	 * @param interfaceName
	 */
	public void setJavaToWebView(Object obj, String interfaceName) {
		this.addJavascriptInterface(obj, interfaceName);
	}

	public void loadTextToHtml(String content) {
		loadDataWithBaseURL(null, content, "text/html", "utf-8", null);
	}

	private class WebViewImageClick implements OnWebViewImageListener {

		@Override
		@JavascriptInterface
		public void onImageClick(String bigImageUrl) {
			// TODO Auto-generated method stub
			// if (getTag() != null && getTag() instanceof List) {
			// List<String> temp = (List<String>) getTag();
			// if (temp != null && temp.size() > 0) {
			// int pos = 0;
			// for (int i = 0; i < temp.size(); i++) {
			// if (bigImageUrl.equals(temp.get(i))) {
			// pos = i;
			// break;
			// }
			// }
			// Intent in = new Intent();
			// in.putExtra("detail_pic", (Serializable) temp);
			// in.putExtra("pos", pos);
			// in.setClass(getContext(),
			// CeeSchoolDetailImageActivity.class);
			// getContext().startActivity(in);
			// }
			// }
		}
	}
}
