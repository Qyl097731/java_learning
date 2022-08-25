package config.bean;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;

/**
 * @author qyl
 * @program SshCfg.java
 * @Description ssh配置
 * @createTime 2022-08-15 09:05
 */
@Data
public class SshCfg {
    @Value("${ssh.ip}")
    private String ip;

    @Value("${ssh.port}")
    private Integer port;

    @Value("${ssh.username}")
    private String username;

    @Value("${ssh.password}")
    private String password;
}
