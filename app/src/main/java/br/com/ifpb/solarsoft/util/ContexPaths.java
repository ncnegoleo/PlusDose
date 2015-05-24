package br.com.ifpb.solarsoft.util;

/**
 * Created by leonardo on 16/05/2015.
 */
public enum ContexPaths {

   RESOUCER("android.resource://");

    public String contexPath;

    ContexPaths(String value) {
        contexPath = value;
    }

    public String getContexPath() {
        return contexPath;
    }
}
