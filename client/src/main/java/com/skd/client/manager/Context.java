package com.skd.client.manager;

import org.springframework.context.ApplicationContext;

public class Context {

    private static ApplicationContext applicationContext;
    public static void setContext(ApplicationContext context){
        applicationContext = context;
    }

    public static ApplicationContext getContext( ){
        return applicationContext;
    }
}
