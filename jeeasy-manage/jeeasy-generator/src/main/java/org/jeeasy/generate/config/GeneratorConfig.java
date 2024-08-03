package org.jeeasy.generate.config;

import com.baomidou.mybatisplus.generator.config.ConstVal;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.StringWriter;
import java.util.Properties;

@Configuration
public class GeneratorConfig {

    //@Bean
    //public GlobalConfig globalConfig() {
    //    return new GlobalConfig.Builder()
    //            .fileOverride()
    //            .outputDir("D://generator")
    //            .author("wei.yang")
    //            .enableKotlin()
    //            .enableSwagger()
    //            .dateType(DateType.TIME_PACK)
    //            .commentDate("yyyy-MM-dd")
    //            .build();
    //}

    @Bean
    public VelocityEngine velocityEngine() {
        Properties p = new Properties();
        p.put("file.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
        p.put(VelocityEngine.FILE_RESOURCE_LOADER_PATH, "");
        p.put("UTF-8", ConstVal.UTF8);
        p.put(Velocity.INPUT_ENCODING, ConstVal.UTF8);
        p.put("file.resource.loader.unicode", "true");
        VelocityEngine velocityEngine = new VelocityEngine(p);
        velocityEngine.init();
        return velocityEngine;
    }

    //@Bean("stringVelocityEngine")
    public static VelocityEngine stringVelocityEngine() {
        Properties p = new Properties();
        p.put("file.resource.loader.class", "org.apache.velocity.runtime.resource.loader.StringResourceLoader");
        p.put(VelocityEngine.FILE_RESOURCE_LOADER_PATH, "");
        p.put("UTF-8", ConstVal.UTF8);
        p.put(Velocity.INPUT_ENCODING, ConstVal.UTF8);
        p.put("file.resource.loader.unicode", "true");
        VelocityEngine velocityEngine = new VelocityEngine(p);
        velocityEngine.init();
        return velocityEngine;
    }

    public static void main(String[] args) {
        VelocityContext context = new VelocityContext();

        context.put("name", "Veloci2231312312ty");
        context.put("project", "Jakarta11111");

        /* 渲染字符串 */
        String s = "We are using $project $name to render this.";
        StringWriter w = new StringWriter();
        Velocity.evaluate(context, w, "mystring", s);
        System.out.println(" string : " + w);
    }

}
