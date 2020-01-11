package com.xtp.sourceanalysis.spring.spring.Service;

import com.xtp.sourceanalysis.spring.spring.annotation.Lazy;
import com.xtp.sourceanalysis.spring.spring.annotation.Service;

@Lazy(false)
@Service("sysUserService")
public class SysUserService {
    public SysUserService() {
        System.out.println("SysUserService");
    }
}
