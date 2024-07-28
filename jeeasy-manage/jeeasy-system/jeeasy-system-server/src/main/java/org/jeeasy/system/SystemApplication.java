package org.jeeasy.system;

import com.tangzc.mpe.autotable.EnableAutoTable;
import lombok.extern.slf4j.Slf4j;
import org.jeeasy.common.core.tools.ApplicationUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ConfigurableApplicationContext;

import java.net.UnknownHostException;

/**
 * 入 口 启 动 类
 *
 * @author AlpsDDJ
 * @date
 */
@Slf4j
@EnableAutoTable
@EnableDiscoveryClient
@SpringBootApplication(scanBasePackages = "org.jeeasy")
@EnableFeignClients(basePackages = "org.jeeasy")
// @MapperScan("org.jeeasy.**.mapper")
//@EnableConfigurationProperties({ SwaggerModuleConfiguration.class })
public class SystemApplication extends SpringBootServletInitializer {
    public static void main(String[] args) throws UnknownHostException {
        ConfigurableApplicationContext application = SpringApplication.run(SystemApplication.class, args);
        ApplicationUtil.printAppInfo(application);
    }
}
