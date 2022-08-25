package config.bean;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;

/**
 * @program:  qhmuli-service
 * @description 系统配置
 * @author: SiYe
 * @create: 2022-03-03 15:57
 **/
@Data
public class SystemCfg {
    //文件库路径
    @Value("${system.basepath}")
    private String basePath;
}
