package com.skd.client;

import com.skd.client.config.MonitorConfig;
import com.skd.client.manager.FileServiceManager;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ConfigurableApplicationContext;


@SpringBootApplication
@EnableConfigurationProperties({MonitorConfig.class})
public class ClientApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(ClientApplication.class, args);
        context.getBean(FileServiceManager.class).start();
    }

}

