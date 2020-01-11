package com.xtp.service;

import org.springframework.stereotype.Service;

@Service("userServics")
public class userServicsImpl implements UserServics{
    @Override
    public void talk() {
        System.out.println("hello word ......");
    }
}
