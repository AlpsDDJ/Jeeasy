package org.jeeasy.ai;

import lombok.extern.slf4j.Slf4j;
import org.jeeasy.common.core.tools.ApplicationUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * 入 口 启 动 类
 *
 * @author AlpsDDJ
 * @date
 */
@Slf4j
@EnableFeignClients
@EnableDiscoveryClient
@SpringBootApplication(scanBasePackages = "org.jeeasy")
public class JeeasyAiApplication extends SpringBootServletInitializer {
    public static void main(String[] args) {
        ConfigurableApplicationContext application = SpringApplication.run(JeeasyAiApplication.class, args);
        ApplicationUtil.printAppInfo(application);
    }
}
