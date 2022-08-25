package config;


import config.bean.SshCfg;
import config.bean.SystemCfg;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * @program: qhmuli-service
 * @description 系统业务配置类
 * @author: SiYe
 * @create: 2022-03-03 15:56
 **/
@PropertySource(value = {"classpath:properties/business.properties"}, encoding = "utf-8")
@Configuration
public class BusinessConfig {
    //系统相关配置
    @Bean
    public SystemCfg systemCfg(){
        return new SystemCfg();
    }

    @Bean
    public SshCfg sshCfg(){return new SshCfg();}
}
