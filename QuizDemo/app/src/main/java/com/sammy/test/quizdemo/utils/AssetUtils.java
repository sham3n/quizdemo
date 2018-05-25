package com.sammy.test.quizdemo.utils;

import com.sammy.test.quizdemo.MyApp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by sng on 4/9/18.
 */

public class AssetUtils {

    public static String getStringFromAsset(String file) {
        StringBuilder buf = new StringBuilder();
        try {
            InputStream json = MyApp.getContext().getAssets().open(file);
            BufferedReader in = new BufferedReader(new InputStreamReader(json, "UTF-8"));
            String str;

            while ((str = in.readLine()) != null) {
                buf.append(str);
            }

            in.close();
        }
        catch(IOException exception) {
            //exception
        }

        return buf.toString();
    }
}
