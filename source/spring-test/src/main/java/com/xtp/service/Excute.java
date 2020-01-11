package com.xtp.service;

import org.springframework.beans.factory.annotation.Autowired;

public class Excute implements UserServics {

    @Autowired
    private UserServics userServics;

    @Override
    public void talk() {
        userServics.talk();
    }
}
