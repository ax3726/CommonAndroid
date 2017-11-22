package ml.gsy.com.library.widget.webview;

/**
 * 监听webview上的图片
 * 
 * @author dennis
 *
 */
public interface OnWebViewImageListener {

	/**
	 * 点击webview上的图片，传入该缩略图的大图Url
	 * @param bigImageUrl
	 */
	void onImageClick(String bigImageUrl);
	
}
