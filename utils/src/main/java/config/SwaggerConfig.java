package config;

import com.google.common.collect.Sets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
@EnableWebMvc
//@ComponentScan("com.nsec.controller")
public class SwaggerConfig extends WebMvcConfigurationSupport {
    @Autowired
    private WebBaseCfg webBaseCfg;
    @Bean
    public Docket buildDocket(){
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(buildApiInf())
//                .host("localhost:8080")
                .protocols(Sets.newHashSet("https","http"))
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.nsec.controller"))//controller路径
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo buildApiInf(){

        String isOpen = webBaseCfg.getSwaggerIsOpen();
        if("1".equals(isOpen)){
            return new ApiInfoBuilder()
                    .title("青海木里app api")
                    .description("文档接口api")
                    .version("V1.0.0")
                    .build();

        }else{
            return null;
        }

    }
}