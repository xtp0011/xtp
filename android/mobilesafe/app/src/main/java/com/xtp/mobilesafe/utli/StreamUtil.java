package com.xtp.mobilesafe.utli;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class StreamUtil {
    public static String stream2String(InputStream inputStream) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] bytes = new byte[1024];
        int temp = -1;
        try {
            if((temp=inputStream.read(bytes))!=-1){
                byteArrayOutputStream.write(bytes,0,temp);
                return byteArrayOutputStream.toString();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                inputStream.close();
                byteArrayOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
