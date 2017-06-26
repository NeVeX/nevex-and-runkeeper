package com.mark.nevexandrunkeeper.util;

import java.net.URLEncoder;
import java.util.Map;

/**
 * Created by NeVeX on 7/6/2016.
 */
public class APIUtil {

    public static String getString(Map<String, String> map, String key) {
        return map.get(key);
    }

    public static String urlEncodeString(String string) {
        try {
            return URLEncoder.encode(string, "UTF-8");
        } catch (Exception e ) {
            return "";
        }
    }
}
