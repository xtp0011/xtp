package com.xtp;

import com.xtp.config.Config;
import com.xtp.service.UserServics;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class AppTest 
{

    @Test
    public void shouldAnswerWithTrue()
    {
        ApplicationContext context = new AnnotationConfigApplicationContext(Config.class);
        System.out.println( context.getId());
        UserServics userServics = context.getBean(UserServics.class);
        userServics.talk();

    }
}
