package ml.gsy.com.library.widget.slide;

/**
 * Created by Administrator on 2017/7/28.
 */

public interface ISlideScrollListener {

    void onScrollChange(int Y);

    void onScrollState(int state);

    void onScrollScale(float alpha);
}
