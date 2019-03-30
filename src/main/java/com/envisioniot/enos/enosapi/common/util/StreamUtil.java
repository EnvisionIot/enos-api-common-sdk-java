package com.envisioniot.enos.enosapi.common.util;

import com.google.common.io.CharStreams;

import java.io.*;

/**
 * @author dongdong.wang
 */
public class StreamUtil {

    public static String inputStreamToString(InputStream ins) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(ins, "UTF-8"));
        return CharStreams.toString(reader);
    }

    public static void  inputStreamToOutputStream(InputStream in, OutputStream out) throws IOException {
        int ch;
        byte[] buff = new byte[4096];
        while ((ch = in.read(buff)) != -1) {
            out.write(buff, 0, ch);
        }
    }
}
