package org.jeeasy.cloud.gateway;

import com.alibaba.cloud.commons.lang.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * 入 口 启 动 类
 * @author AlpsDDJ
 * @date
 */
@Slf4j
@SpringBootApplication(scanBasePackages = "org.jeeasy")
@EnableDiscoveryClient
// @MapperScan("org.jeeasy.**.mapper")
//@EnableConfigurationProperties({ SwaggerModuleConfiguration.class })
public class JeeasyGatewayApplication extends SpringBootServletInitializer {
    public static void main(String[] args) throws UnknownHostException {
        ConfigurableApplicationContext application = SpringApplication.run(JeeasyGatewayApplication.class, args);
        Environment env = application.getEnvironment();
        String ip = InetAddress.getLocalHost().getHostAddress();
        String port = env.getProperty("server.port");
        String path = env.getProperty("server.servlet.context-path");
        path = StringUtils.isBlank(path) ? "": path;
        log.info("\n----------------------------------------------------------\n\t" +
                "Application JeeasyGateway is running! Access URLs:\n\t" +
                "Local: \t\thttp://localhost:" + port + path + "/\n\t" +
                "External: \thttp://" + ip + ":" + port + path + "/\n\t" +
                "----------------------------------------------------------");
    }
}
