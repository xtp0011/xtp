package com.xtp.sourceanalysis.nio;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class TestFileChannel {
    public static void main(String[] args) throws  Exception
    {
        //do1();
        ByteBuffer buffer = ByteBuffer.allocate(2);//jvm
        FileChannel fc = FileChannel.open(Paths.get("SourceAnalysis.eml"),StandardOpenOption.READ);
        int len=-1;
        do {
            len=fc.read(buffer);
            buffer.flip();
            while (buffer.hasRemaining()){
                System.out.print((char)buffer.get());
            }
            buffer.flip();
            buffer.clear();
        }while (len!=-1);

    }

    private static void do1() throws IOException {
        ByteBuffer buffer = ByteBuffer.allocate(1024*1024);//jvm
        FileChannel fc = FileChannel.open(Paths.get("SourceAnalysis.eml"),StandardOpenOption.READ);
        fc.read(buffer);
        buffer.flip();
        byte[] bytes = buffer.array();
        System.out.println(new String(bytes));
    }
}
