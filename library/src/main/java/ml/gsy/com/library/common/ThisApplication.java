package ml.gsy.com.library.common;

import android.app.Application;
import android.content.Context;
import android.net.wifi.WifiManager;

/**
 * Created by LiMing on 2016/11/12.
 */
public class ThisApplication extends Application {
    public static Context applicationContext;
    private static ThisApplication instance;
    @Override
    public void onCreate() {
        super.onCreate();
        applicationContext = this;
        instance = this;
    }

    public static ThisApplication getInstance() {
        return instance;
    }
    /**
     * 获取登录设备mac地址
     *
     * @return
     */
    public String getMacAddr(Context context) {
        WifiManager wm = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        String mac = wm.getConnectionInfo().getMacAddress();
        return mac == null ? "" : mac;
    }

}
