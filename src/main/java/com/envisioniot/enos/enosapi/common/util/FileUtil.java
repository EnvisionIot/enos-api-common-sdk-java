package com.envisioniot.enos.enosapi.common.util;

import java.io.*;

/**
 * created by dongdong.wang
 */
public class FileUtil {

    private static final String CHARSET = "UTF-8";
    private static final long MAX_FILE_SIZE = 1024 * 1024 * 1024;

    public static String readFileAsString(String filePath) throws IOException {
        return readFileAsString(filePath, CHARSET);
    }

    /**
     * 读取文件内容，作为字符串返回
     */
    public static String readFileAsString(String filePath, String charset) throws IOException {
        File file = new File(filePath);
        if (!file.exists()) {
            throw new FileNotFoundException(filePath);
        }

        if (file.length() > MAX_FILE_SIZE) {
            throw new IOException("File is too large");
        }

        StringBuilder sb = new StringBuilder((int) (file.length()));
        // 创建字节输入流
        BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(filePath), charset));

        // 创建一个长度为10240的Buffer
        char[] cbuf = new char[10240];
        // 用于保存实际读取的字节数
        int hasRead = 0;
        while ((hasRead = in.read(cbuf)) > 0) {
            sb.append(new String(cbuf, 0, hasRead));
        }
        in.close();
        return sb.toString();
    }

    public static void writeFile(String filePath, String content) throws IOException {
        File file = new File(filePath);
        if (file.exists()) {
            file.delete();
        }
        Writer out = new FileWriter(file);
        out.write(content);
        out.close();
    }

    public static void readFileAsStream(String filePath, OutputStream os) throws IOException {

        byte[] b = new byte[4096];
        int length;
        FileInputStream in = null;
        try {
            in = new FileInputStream(new File(filePath));
            //将文件内容输出到os中
            while ((length = in.read(b)) != -1) {
                os.write(b, 0, length);
            }
        } finally {
            in.close();
            if (os != null) {
                os.close();
            }
            if (in != null) {
                in.close();
            }
        }
    }

    public static void writeFileAsStream(String filePath, InputStream inputStream) throws IOException {

        OutputStream os = new FileOutputStream(new File(filePath));
        int bytesRead = 0;
        byte[] buffer = new byte[4096];
        while ((bytesRead = inputStream.read(buffer)) != -1) {
            os.write(buffer, 0, bytesRead);
        }
        os.close();
//        inputStream.close();
    }


    public static void main(String[] args) throws IOException {
        String filePath = "doc\\zh-CN\\Common\\CommonParams.json";
        System.out.println(readFileAsString(filePath, "UTF-8"));
    }
}
