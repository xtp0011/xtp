package com.xtp.sourceanalysis.nio;

import java.nio.ByteBuffer;

public class TestByteBuffer {

    public static void main(String[] args){
        ByteBuffer buffer = ByteBuffer.allocate(1024);//jvm
        //ByteBuffer.allocateDirect(1024);//os
        doPrint(buffer.position(),//位置
                buffer.limit(),//限制
                buffer.capacity());//容量
        buffer.put("hello".getBytes());
        doPrint(buffer.position(),
                buffer.limit(),
                buffer.capacity());
        buffer.flip();
        doPrint(buffer.position(),
                buffer.limit(),
                buffer.capacity());
        System.out.println("chr:"+(char)buffer.get());
        doPrint(buffer.position(),
                buffer.limit(),
                buffer.capacity());
        buffer.clear();
        buffer.compact();//清除已读过的数据
    }

    private static void doPrint(int position, int limit, int capacity) {
        System.out.println("position : "+position);
        System.out.println("limit : "+limit) ;
        System.out.println("capacity : "+capacity);
    }
}
