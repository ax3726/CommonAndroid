package com.lm.lib_common.utils;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;
import android.telephony.TelephonyManager;
import android.text.InputFilter;
import android.text.InputType;
import android.text.Spanned;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {

    /**
     * 获取手机型号
     **/
    public static String getPhoneModelInfo() {
        return android.os.Build.MODEL;
    }


    /**
     * 关闭软键盘
     *
     * @param activity
     */
    public static void closeInputPad(Activity activity) {
        View view = activity.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public static void openInputPad(Activity activity) {
        View view = activity.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    /**
     * TODO 判断程序是否正在前台运行
     **/
    public static boolean isRunningNow(Context context, String packageName) {
        boolean flag = false;
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> tasksInfo = activityManager.getRunningTasks(1);
        if (tasksInfo.size() > 0) {
            //应用程序位于堆栈的顶层
            if (packageName.equals(tasksInfo.get(0).topActivity.getPackageName())) {
                return true;
            }
        }
        return flag;
    }

    /**
     * 禁止EditText输入空格
     *
     * @param editText
     */
    public static void setEditTextInhibitInputSpace(EditText editText) {
        InputFilter filter = new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                if (source.equals(" ")) {
                    return "";
                } else {
                    return null;
                }

            }
        };
        editText.setFilters(new InputFilter[]{filter});
    }

    /**
     * 禁止EditText输入特殊字符
     *
     * @param editText
     */
    public static void setEditTextInhibitInputSpeChat(EditText editText) {

        InputFilter filter = new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                String speChat = "[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
                Pattern pattern = Pattern.compile(speChat);
                Matcher matcher = pattern.matcher(source.toString());
                if (matcher.find()) return "";
                else return null;
            }
        };
        editText.setFilters(new InputFilter[]{filter});
    }

    /**
     * TODO 判断程序是否正在前台运行
     **/
    public static boolean isActivityRunningNow(Context context, String activityName) {
        boolean flag = false;
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> tasksInfo = activityManager.getRunningTasks(1);
        if (tasksInfo.size() > 0) {
            if (tasksInfo.get(0).topActivity.getShortClassName().contains(activityName)) {
                return true;
            }
        }
        return flag;
    }

    /**
     * 获取屏幕宽高
     *
     * @param context 上下文对象
     * @return size[0]:宽 size[1]:高
     */
    public static float[] getScreenSize(Context context) {
        float[] size = new float[2];
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        size[0] = display.getWidth();
        size[1] = display.getHeight();
        return size;
    }


    /**
     * px转换成dip工具
     */
    public static float px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (pxValue / scale + 0.5f);
    }

    /**
     * dip转px工具
     *
     * @param context  上下文
     * @param dipValue DIP大小
     * @return px值
     */
    public static float dip2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (dipValue * scale + 0.5f);
    }

    /**
     * 将px值转换为sp值，保证文字大小不变
     */
    public static int px2sp(Context context, float pxValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

    /**
     * 将sp值转换为px值，保证文字大小不变
     */
    public static int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    /**
     * 获取存储路径，如果存在SD卡则在SD卡内，没有则在内存中
     */
    public static String getMemeoryPath(String savedir) {
        String mpath = "";
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            mpath = Environment.getExternalStorageDirectory().getPath();
        } else {
            mpath = Environment.getRootDirectory().getPath();
        }
        //6.0

        String[] split = savedir.split("/");
        String path = "";
        for (int i = 0; i < split.length; i++) {
            path = path + "/" + split[i];
            String p = mpath + path;
            File dirFile = new File(p);
            if (!dirFile.exists()) {
                dirFile.mkdirs();
            }
        }
        return mpath + "/" + savedir;
    }

    /**
     * 获取应用专属缓存目录
     * android 4.4及以上系统不需要申请SD卡读写权限
     * 因此也不用考虑6.0系统动态申请SD卡读写权限问题，切随应用被卸载后自动清空 不会污染用户存储空间
     *
     * @param context 上下文
     * @param type    文件夹类型 可以为空，为空则返回API得到的一级目录
     * @return 缓存文件夹 如果没有SD卡或SD卡有问题则返回内存缓存目录，否则优先返回SD卡缓存目录
     */
    public static File getCacheDirectory(Context context, String type) {
        File appCacheDir = getExternalCacheDirectory(context, type);
        if (appCacheDir == null) {
            appCacheDir = getInternalCacheDirectory(context, type);
        }

        if (appCacheDir == null) {
            Log.e("getCacheDirectory", "getCacheDirectory fail ,the reason is mobile phone unknown exception !");
        } else {
            if (!appCacheDir.exists() && !appCacheDir.mkdirs()) {
                Log.e("getCacheDirectory", "getCacheDirectory fail ,the reason is make directory fail !");
            }
        }
        return appCacheDir;
    }

    /**
     * 获取SD卡缓存目录
     *
     * @param context 上下文
     * @param type    文件夹类型 如果为空则返回 /storage/emulated/0/Android/data/app_package_name/cache
     *                否则返回对应类型的文件夹如Environment.DIRECTORY_PICTURES 对应的文件夹为 .../data/app_package_name/files/Pictures
     *                {@link Environment#DIRECTORY_MUSIC},
     *                {@link Environment#DIRECTORY_PODCASTS},
     *                {@link Environment#DIRECTORY_RINGTONES},
     *                {@link Environment#DIRECTORY_ALARMS},
     *                {@link Environment#DIRECTORY_NOTIFICATIONS},
     *                {@link Environment#DIRECTORY_PICTURES}, or
     *                {@link Environment#DIRECTORY_MOVIES}.or 自定义文件夹名称
     * @return 缓存目录文件夹 或 null（无SD卡或SD卡挂载失败）
     */
    public static File getExternalCacheDirectory(Context context, String type) {
        File appCacheDir = null;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            if (TextUtils.isEmpty(type)) {
                appCacheDir = context.getExternalCacheDir();
            } else {
                appCacheDir = context.getExternalFilesDir(type);
            }

            if (appCacheDir == null) {// 有些手机需要通过自定义目录
                appCacheDir = new File(Environment.getExternalStorageDirectory(), "Android/data/" + context.getPackageName() + "/cache/" + type);
            }

            if (appCacheDir == null) {
                Log.e("getExternalDirectory", "getExternalDirectory fail ,the reason is sdCard unknown exception !");
            } else {
                if (!appCacheDir.exists() && !appCacheDir.mkdirs()) {
                    Log.e("getExternalDirectory", "getExternalDirectory fail ,the reason is make directory fail !");
                }
            }
        } else {
            Log.e("getExternalDirectory", "getExternalDirectory fail ,the reason is sdCard nonexistence or sdCard mount fail !");
        }
        return appCacheDir;
    }

    /**
     * 获取内存缓存目录
     *
     * @param type 子目录，可以为空，为空直接返回一级目录
     * @return 缓存目录文件夹 或 null（创建目录文件失败）
     * 注：该方法获取的目录是能供当前应用自己使用，外部应用没有读写权限，如 系统相机应用
     */
    public static File getInternalCacheDirectory(Context context, String type) {
        File appCacheDir = null;
        if (TextUtils.isEmpty(type)) {
            appCacheDir = context.getCacheDir();// /data/data/app_package_name/cache
        } else {
            appCacheDir = new File(context.getFilesDir(), type);// /data/data/app_package_name/files/type
        }

        if (!appCacheDir.exists() && !appCacheDir.mkdirs()) {
            Log.e("getInternalDirectory", "getInternalDirectory fail ,the reason is make directory fail !");
        }
        return appCacheDir;
    }

    /**
     * 验证正则表达式
     */
    public static boolean realRule(String str, String regex) {
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(str);
        return m.find();
    }

    /**
     * 获取当前版本
     */
    public static int getVersionCode(Context context) {
        try {
            // 获取packagemanager的实例
            PackageManager packageManager = context.getPackageManager();
            // getPackageName()是你当前类的包名，0代表是获取版本信息
            PackageInfo packInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            String version = packInfo.versionName;
            return packInfo.versionCode;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }

    }

    /**
     * 获取当前版本  v x.x
     */
    public static String getVersionName(Context context) {
        PackageManager manager;
        PackageInfo info = null;
        manager = context.getPackageManager();
        try {
            info = manager.getPackageInfo(context.getPackageName(), 0);
            return info.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return "1.0";
        }
    }

    /**
     * 将bitmap保存为文件
     */
    public static void saveBitmap2file(Bitmap bmp, String filepath, String filename) {
        FileOutputStream stream = null;
        try {
            File file = new File(filepath);
            if (!file.exists()) {
                file.mkdir();
            }
            stream = new FileOutputStream(filepath + "/" + filename);
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (null != stream) {
                try {
                    stream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 将Bitmap转换成Base64字符串
     *
     * @param bitmap 源图
     * @return base64字符串
     */
    public static String Bitmap2Base64(Bitmap bitmap) {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);//参数100表示不压缩
        byte[] bytes = bos.toByteArray();
        return Base64.encodeToString(bytes, Base64.DEFAULT);
    }


    // byte[]转换成Bitmap
    public static Bitmap Bytes2Bitmap(byte[] b) {
        if (b.length != 0) {
            return BitmapFactory.decodeByteArray(b, 0, b.length);
        }
        return null;
    }

    //将byte[]转换为base64
    public static String Bytes2Base64(byte[] b) {
        if (b == null) {
            return "";
        }
        return Base64.encodeToString(b, Base64.DEFAULT);
    }

    /**
     * 将Bitmap转换成Base64字符串
     *
     * @param filepath 源图
     * @return base64字符串
     */
    public static String FileBase64(String filepath) throws IOException {
        File file = new File(filepath);
        FileInputStream inputFile = new FileInputStream(file);
        byte[] buffer = new byte[(int) file.length()];
        inputFile.read(buffer);
        inputFile.close();
        return Base64.encodeToString(buffer, Base64.DEFAULT);
    }

    /**
     * 两个毫秒值之间的天数
     **/
    public static Long getDaysBetween(long startDate, long endDate) {

        Calendar fromCalendar = Calendar.getInstance();
        fromCalendar.setTimeInMillis(startDate);
        fromCalendar.set(Calendar.HOUR_OF_DAY, 0);
        fromCalendar.set(Calendar.MINUTE, 0);
        fromCalendar.set(Calendar.SECOND, 0);
        fromCalendar.set(Calendar.MILLISECOND, 0);

        Calendar toCalendar = Calendar.getInstance();
        toCalendar.setTimeInMillis(endDate);
        toCalendar.set(Calendar.HOUR_OF_DAY, 0);
        toCalendar.set(Calendar.MINUTE, 0);
        toCalendar.set(Calendar.SECOND, 0);
        toCalendar.set(Calendar.MILLISECOND, 0);

        return (toCalendar.getTime().getTime() - fromCalendar.getTime().getTime()) / (1000 * 60 * 60 * 24);
    }


    /**
     * 拼接字符串
     *
     * @param source     源字符串集合
     * @param joinSuffix 连接的字符串
     **/
    public static String joinStr(List<String> source, String joinSuffix) {
        if (null != source && !source.isEmpty()) {
            StringBuffer sb = new StringBuffer();
            int size = source.size();
            for (int i = 0; i < size; i++) {
                sb.append(source.get(i));
                if (i != (size - 1)) {
                    sb.append(joinSuffix);
                }
            }
            String re = sb.toString();
            return re;
        } else {
            return null;
        }
    }

    /**
     * 比较两个类是否为同一个类
     *
     * @param classA
     * @param classB
     * @return
     */
    public static boolean equalsClass(Class classA, Class classB) {
        return classA.getSimpleName().equals(classB.getSimpleName());
    }

    /**
     * 设置EditText密码是否可见
     *
     * @param editText 密码框
     * @param visible  是否可见
     */
    public static void setTextVisible(EditText editText, boolean visible) {
        editText.setInputType(visible ? InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD : InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
    }

    public static boolean isSmallTime(long time) {
        long cur = System.currentTimeMillis();
        if (cur > time * 1000)
            return true;
        else
            return false;
    }

    public static boolean isEveryDayTime(long time) {
        long cur = System.currentTimeMillis();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        if (sdf.format(new Date(cur)).equals(sdf.format(new Date(time * 1000))))
            return true;
        else
            return false;
    }

    public static boolean isEveryWeekTime(long time) {
        long cur = System.currentTimeMillis();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        if (StringData(cur).equals(StringData(time)) &&
                sdf.format(new Date(cur)).equals(sdf.format(new Date(time * 1000))))
            return true;
        else
            return false;
    }


    public static boolean isEveryMonthTime(long time) {
        long cur = System.currentTimeMillis();
        SimpleDateFormat sdf = new SimpleDateFormat("dd日HH:mm");
        if (sdf.format(new Date(cur)).equals(sdf.format(new Date(time * 1000))))
            return true;
        else
            return false;
    }

    public static boolean isEveryYearTime(long time) {
        long cur = System.currentTimeMillis();
        SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日HH:mm");
        if (sdf.format(new Date(cur)).equals(sdf.format(new Date(time * 1000))))
            return true;
        else
            return false;
    }

    public static boolean isCurTime(long time) {
        long cur = System.currentTimeMillis();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日HH:mm");
        String format = sdf.format(new Date(cur));
        String format1 = sdf.format(new Date(time * 1000));
        if (format.equals(format1))
            return true;
        else
            return false;
    }

    public static String getAllTime(long time) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日HH:mm");
        return sdf.format(new Date(time));
    }

    public static String getTimeStyle1(long time) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
        return sdf.format(new Date(time));
    }
    public static String getTimeStyle5(long time) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd HH:mm");
        return sdf.format(new Date(time));
    }
    public static String getTimeStyle3(long time) {
        SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
        return sdf.format(new Date(time));
    }

    public static String getTimeStyle4(long time) {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        return sdf.format(new Date(time));
    }

    public static String getTimeCha(long finishTime) {
        /*if (finishTime < 60 * 60 * 1000) {
            long min = ((finishTime / (60 * 1000)));
            long s = (finishTime / 1000 - min * 60);
            return min + "分钟" + s + "秒";
        } else {
            long hour = (finishTime / (60 * 60 * 1000));
            long min = ((finishTime / (60 * 1000)) - hour * 60);
            long s = (finishTime / 1000 - hour * 60 * 60 - min * 60);
            return hour + "小时" + min + "分钟" + s + "秒";
        }*/

        if (finishTime < 60 * 60 * 1000) {
            long s = finishTime / 1000 % 60;
            long min = finishTime / (60 * 1000) % 60;
            return min + "分" + s + "秒";
        } else {
            long s = finishTime / 1000 % 60;
            long min = finishTime / (60 * 1000) % 60;
            long hour = finishTime / (60 * 60 * 1000) % 24;
            // long day = finishTime / (24*60 * 60 * 1000);
            return hour + "小时" + min + "分" + s + "秒";
        }

    }
    /**
     * 半角转全角
     * @param input String.
     * @return 全角字符串.
     */
    public static String ToSBC(String input) {
        char c[] = input.toCharArray();
        for (int i = 0; i < c.length; i++) {
            if (c[i] == ' ') {
                c[i] = '\u3000';                 //采用十六进制,相当于十进制的12288


            } else if (c[i] < '\177') {    //采用八进制,相当于十进制的127
                c[i] = (char) (c[i] + 65248);

            }
        }
        return new String(c);
    }


    /**
     * 全角转半角
     * @param input String.
     * @return 半角字符串
     */
    public static String ToDBC(String input) {


        char c[] = input.toCharArray();
        for (int i = 0; i < c.length; i++) {
            if (c[i] == '\u3000') {
                c[i] = ' ';
            } else if (c[i] > '\uFF00' && c[i] < '\uFF5F') {
                c[i] = (char) (c[i] - 65248);

            }
        }
        String returnString = new String(c);

        return returnString;
    }

    /*
        public static String getTimeStyle2(long time) {
            long curtime = System.currentTimeMillis();
            if (curtime - time < 1000 * 60 * 60) {
                return "刚刚";
            } else if (curtime - time < 1000 * 60 * 60 * 24) {
                return String.valueOf((curtime - time) / 1000 / 60 / 60) + "小时前";
            } else if (curtime - time < 1000 * 60 * 60 * 24 * 7) {
                return String.valueOf((curtime - time) / 1000 / 60 / 60 / 24) + "天前";
            } else {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
                return sdf.format(new Date(time));
            }
        } */
    public static String getTimeStyle22(long time) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy");
        SimpleDateFormat df = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");
        SimpleDateFormat df2 = new SimpleDateFormat("MM月dd日 HH:mm");
        if (formatter.format(time).equals(formatter.format(new Date(System.currentTimeMillis())))) {
            return df2.format(time);
        } else {
            return df.format(time);
        }
    }

    public static String getTimeStyle2(long time) {
        SimpleDateFormat df1 = new SimpleDateFormat("HH:mm");
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy");
        SimpleDateFormat df = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");
        SimpleDateFormat df2 = new SimpleDateFormat("MM月dd日 HH:mm");
        GregorianCalendar currentDate = new GregorianCalendar();
        Date time1 = currentDate.getTime();
        long curTime = time1.getTime();//当前时间
        Date time2 = new Date(time);
        if (curTime - time <= 1000) {
            return "刚刚";
        } else if (curTime - time < 1000 * 60) {
            return (curTime - time) / 1000 + "秒前";
        } else if (curTime - time < 1000 * 60 * 60) {
            return (curTime - time) / 1000 / 60 + "分钟前";
        } else if (curTime - time < 1000 * 60 * 60 * 24) {
            return (curTime - time) / (1000 * 60 * 60) + "小时前";
        } else if (time1.getDate() - time2.getDate() == 1 && curTime - time < 1000 * 60 * 60 * 24 * 4) {
            return "昨天\t" + df1.format(time);
        } else if (time1.getDate() - time2.getDate() == 2 && curTime - time < 1000 * 60 * 60 * 24 * 4) {
            return "前天\t" + df1.format(time);
        } else {
            if (formatter.format(time).equals(formatter.format(new Date(System.currentTimeMillis())))) {
                return df2.format(time);
            } else {
                return df.format(time);
            }
        }
    }

    public static String getTimeStyle222(long time) {
        GregorianCalendar currentDate = new GregorianCalendar();
        Date time1 = currentDate.getTime();
        long curTime = time1.getTime();//当前时间
        if (time - curTime < 1000 * 60) {
            return "还剩" + ((time - curTime) / 1000) + "秒";
        } else if (time - curTime < 1000 * 60 * 60) {
            return "还剩" + ((time - curTime) / 1000 / 60) + "分钟";
        } else if (time - curTime < 1000 * 60 * 60 * 24) {
            return "还剩" + ((time - curTime) / (1000 * 60 * 60)) + "小时";
        } else {
            return "还剩" + ((time - curTime) / (1000 * 60 * 60 * 24)) + "天";
        }
    }


    /*将字符串转为时间戳*/
    public static long getStringToDate(String time, String Fromat) {
        SimpleDateFormat sdf = new SimpleDateFormat(Fromat);
        Date date = new Date();
        try {
            date = sdf.parse(time);
        } catch (ParseException e) {

            e.printStackTrace();
        }
        return date.getTime();
    }

    /*时间戳转换成字符窜*/
    public static String getDateToString(long time, String Fromat) {
        Date d = new Date(time);
        SimpleDateFormat sf = new SimpleDateFormat(Fromat);
        return sf.format(d);
    }

    /*判断是不是同一天*/
    public static boolean isOneDay(long time) {
        long cur = System.currentTimeMillis();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
        String format = sdf.format(new Date(cur));
        String format1 = sdf.format(new Date(time));
        if (format.equals(format1))
            return true;
        else
            return false;
    }

    public static String StringData(long time) {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(time);
        String mWay = String.valueOf(c.get(Calendar.DAY_OF_WEEK));
        if ("1".equals(mWay)) {
            mWay = "天";
        } else if ("2".equals(mWay)) {
            mWay = "一";
        } else if ("3".equals(mWay)) {
            mWay = "二";
        } else if ("4".equals(mWay)) {
            mWay = "三";
        } else if ("5".equals(mWay)) {
            mWay = "四";
        } else if ("6".equals(mWay)) {
            mWay = "五";
        } else if ("7".equals(mWay)) {
            mWay = "六";
        }
        return "星期" + mWay;
    }


    /**
     * bitmap转为base64
     *
     * @param bitmap
     * @return
     */
    public static String bitmapToBase64(Bitmap bitmap) {

        String result = null;
        ByteArrayOutputStream baos = null;
        try {
            if (bitmap != null) {
                baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);

                baos.flush();
                baos.close();

                byte[] bitmapBytes = baos.toByteArray();
                result = Base64.encodeToString(bitmapBytes, Base64.DEFAULT);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (baos != null) {
                    baos.flush();
                    baos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    /**
     * base64转为bitmap
     *
     * @param base64Data
     * @return
     */
    public static Bitmap base64ToBitmap(String base64Data) {
        //将字符串转换成Bitmap类型
        Bitmap bitmap = null;
        try {
            byte[] bitmapArray;
            bitmapArray = Base64.decode(base64Data, Base64.DEFAULT);
            bitmap = BitmapFactory.decodeByteArray(bitmapArray, 0, bitmapArray.length);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    /**
     * 使用root方式,静态安装apk
     */
    public static boolean installUseRoot(String filePath) {
        if (TextUtils.isEmpty(filePath))
            throw new IllegalArgumentException("Please check apk file path!");
        boolean result = false;
        Process process = null;
        OutputStream outputStream = null;
        BufferedReader errorStream = null;
        try {
            process = Runtime.getRuntime().exec("su");
            outputStream = process.getOutputStream();

            String command = "pm install -r " + filePath + "\n";
            outputStream.write(command.getBytes());
            outputStream.flush();
            outputStream.write("exit\n".getBytes());
            outputStream.flush();
            process.waitFor();
            errorStream = new BufferedReader(new InputStreamReader(process.getErrorStream()));
            StringBuilder msg = new StringBuilder();
            String line;
            while ((line = errorStream.readLine()) != null) {
                msg.append(line);
            }
            Log.d("install", "install msg is " + msg);
            if (msg.toString().contains("Success")) {
                result = true;
            }
        } catch (Exception e) {
            Log.e("install", e.getMessage(), e);
        } finally {
            try {
                if (outputStream != null) {
                    outputStream.close();
                }
                if (errorStream != null) {
                    errorStream.close();
                }
            } catch (IOException e) {
                outputStream = null;
                errorStream = null;
                process.destroy();
            }
        }
        return result;
    }

    public static boolean isBackground(Context context) {
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> appProcesses = activityManager.getRunningAppProcesses();
        for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {
            if (appProcess.processName.equals(context.getPackageName())) {
                if (appProcess.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_BACKGROUND) {
                    Log.i("后台", appProcess.processName);
                    return true;
                } else {
                    Log.i("前台", appProcess.processName);
                    return false;
                }
            }
        }
        return false;
    }

    /**
     * 获取手机的网络类型
     *
     * @return
     */
    public static String GetNetworkType(Context context) {
        String strNetworkType = "";

        NetworkInfo networkInfo = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            if (networkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                strNetworkType = "WIFI";
            } else if (networkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
                String _strSubTypeName = networkInfo.getSubtypeName();


                Log.e("cocos2d-x", "Network getSubtypeName : " + _strSubTypeName);

                // TD-SCDMA   networkType is 17
                int networkType = networkInfo.getSubtype();
                switch (networkType) {
                    case TelephonyManager.NETWORK_TYPE_GPRS:
                    case TelephonyManager.NETWORK_TYPE_EDGE:
                    case TelephonyManager.NETWORK_TYPE_CDMA:
                    case TelephonyManager.NETWORK_TYPE_1xRTT:
                    case TelephonyManager.NETWORK_TYPE_IDEN: //api<8 : replace by 11
                        strNetworkType = "2G";
                        break;
                    case TelephonyManager.NETWORK_TYPE_UMTS:
                    case TelephonyManager.NETWORK_TYPE_EVDO_0:
                    case TelephonyManager.NETWORK_TYPE_EVDO_A:
                    case TelephonyManager.NETWORK_TYPE_HSDPA:
                    case TelephonyManager.NETWORK_TYPE_HSUPA:
                    case TelephonyManager.NETWORK_TYPE_HSPA:
                    case TelephonyManager.NETWORK_TYPE_EVDO_B: //api<9 : replace by 14
                    case TelephonyManager.NETWORK_TYPE_EHRPD:  //api<11 : replace by 12
                    case TelephonyManager.NETWORK_TYPE_HSPAP:  //api<13 : replace by 15
                        strNetworkType = "3G";
                        break;
                    case TelephonyManager.NETWORK_TYPE_LTE:    //api<11 : replace by 13
                        strNetworkType = "4G";
                        break;
                    default:
                        // http://baike.baidu.com/item/TD-SCDMA 中国移动 联通 电信 三种3G制式
                        if (_strSubTypeName.equalsIgnoreCase("TD-SCDMA") || _strSubTypeName.equalsIgnoreCase("WCDMA") || _strSubTypeName.equalsIgnoreCase("CDMA2000")) {
                            strNetworkType = "3G";
                        } else {
                            strNetworkType = _strSubTypeName;
                        }

                        break;
                }

                Log.e("cocos2d-x", "Network getSubtype : " + Integer.valueOf(networkType).toString());
            }
        }

        Log.e("cocos2d-x", "Network Type : " + strNetworkType);

        return strNetworkType;
    }
}