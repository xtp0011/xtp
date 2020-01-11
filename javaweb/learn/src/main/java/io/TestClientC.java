package io;

import java.io.IOException;

public class TestClientA {
    public static void main(String[] args) throws IOException {
        NioClient nioClient = new NioClient();
        nioClient.start("南少霞1");
    }
}
