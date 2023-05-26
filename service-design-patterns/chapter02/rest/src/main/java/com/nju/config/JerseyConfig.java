package com.nju.config;

import com.nju.controller.MusicGenreController;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.context.annotation.Configuration;

/**
 * 创建ResourceConfig配置类
 * @author qyl
 */
@Configuration
public class JerseyConfig extends ResourceConfig {
    public JerseyConfig() {
        register(MusicGenreController.class);
    }
}
