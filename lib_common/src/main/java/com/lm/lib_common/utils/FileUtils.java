package com.lm.lib_common.utils;

import android.content.Context;
import android.content.res.AssetManager;

import java.io.*;

/**
 * *                            _ooOoo_
 * *                           o8888888o
 * *                           88" . "88
 * *                           (| -_- |)
 * *                            O\ = /O
 * *                        ____/`---'\____
 * *                      .   ' \\| |// `.
 * *                       / \\||| : |||// \
 * *                     / _||||| -:- |||||- \
 * *                       | | \\\ - /// | |
 * *                     | \_| ''\---/'' | |
 * *                      \ .-\__ `-` ___/-. /
 * *                   ___`. .' /--.--\ `. . __
 * *                ."" '< `.___\_<|>_/___.' >'"".
 * *               | | : `- \`.;`\ _ /`;.`/ - ` : | |
 * *                 \ \ `-. \_ __\ /__ _/ .-` / /
 * *         ======`-.____`-.___\_____/___.-`____.-'======
 * *                            `=---='
 * *         .............................................
 * *
 * *                 佛祖保佑             永无BUG
 * *          佛曰:
 * *                  别人笑我太疯癫，我笑他人看不穿。
 *
 * @author Young
 * @date 15/9/13
 */
public class FileUtils {

    public static String readFile(Context context,String fileName) throws IOException {
//        StringBuilder sb = new StringBuilder();
//        File file = new File(fileName);
//        FileReader reader = new FileReader(file);
//        int ch = 0;
//        while ((ch = reader.read()) != -1) {
//            sb.append((char) ch);
//        }
//        return sb.toString();
        StringBuilder stringBuilder = new StringBuilder();
        AssetManager assetManager = context.getAssets();
        BufferedReader bf = new BufferedReader(new InputStreamReader(
                assetManager.open(fileName)));
        String line;
        while ((line = bf.readLine()) != null) {
            stringBuilder.append(line);
        }
        return stringBuilder.toString();
    }
}

