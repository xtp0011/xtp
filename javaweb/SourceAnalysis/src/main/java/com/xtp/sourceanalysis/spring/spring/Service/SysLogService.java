package com.xtp.sourceanalysis.spring.spring.Service;

import com.xtp.sourceanalysis.spring.spring.annotation.Lazy;
import com.xtp.sourceanalysis.spring.spring.annotation.Service;

@Service("sysLogService")
@Lazy
public class SysLogService {
    public SysLogService() {
        System.out.println("SysLogService");
    }
}
