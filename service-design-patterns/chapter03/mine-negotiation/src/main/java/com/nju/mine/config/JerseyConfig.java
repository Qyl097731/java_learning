package com.nju.mine.config;

import com.nju.mine.controller.StoresController;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.context.annotation.Configuration;

/**
 * 创建ResourceConfig配置类
 * @author qyl
 */
@Configuration
public class JerseyConfig extends ResourceConfig {
    public JerseyConfig() {
        register(StoresController.class);
    }
}
