package io;

import java.io.IOException;

public class TestClientB {
    public static void main(String[] args) throws IOException {
        NioClient nioClient = new NioClient();
        nioClient.start("南少霞B");
    }
}
