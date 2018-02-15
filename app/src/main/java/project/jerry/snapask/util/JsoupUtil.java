package project.jerry.snapask.util;

import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

/**
 * Created by Jerry on 2018/1/30.
 */

public class JsoupUtil {

    private static final String TAG = JsoupUtil.class.getSimpleName();

    public static void parseUrl(String url) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Document doc = Jsoup.connect("http://www.ssaurel.com/blog").get();
                    Log.d(TAG, "parseUrl: " + doc.toString());
                    Log.d(TAG, "title: " + doc.title());
                } catch (IOException ioe) {
                    ioe.printStackTrace();
                }
            }
        }).start();
    }

}
