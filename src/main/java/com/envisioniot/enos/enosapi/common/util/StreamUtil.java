package com.envisioniot.enos.enosapi.common.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * created by dongdong.wang
 */
public class StreamUtil {

    public static String inputStreamToString(InputStream ins) throws IOException {
        InputStream inputStream = null;

        StringBuffer stringBuffer = new StringBuffer();
        byte[] byt = new byte[4096];
        int i;
        while((i = ins.read(byt)) != -1) {
            stringBuffer.append(new String(byt, 0, i));
        }
        return stringBuffer.toString();
    }

    public static void  inputStreamToOutputStream(InputStream in, OutputStream out) throws IOException {
        int ch;
        byte[] buff = new byte[4096];
        while ((ch = in.read(buff)) != -1) {
            out.write(buff, 0, ch);
        }
    }
}
