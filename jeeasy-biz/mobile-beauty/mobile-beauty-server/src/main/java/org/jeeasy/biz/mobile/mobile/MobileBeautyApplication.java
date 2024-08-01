package org.jeeasy.biz.mobile.mobile;

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
@EnableFeignClients(basePackages = "org.jeeasy")
@SpringBootApplication(scanBasePackages = "org.jeeasy")
public class MobileBeautyApplication extends SpringBootServletInitializer {
    public static void main(String[] args) throws UnknownHostException {
        ConfigurableApplicationContext application = SpringApplication.run(MobileBeautyApplication.class, args);
        ApplicationUtil.printAppInfo(application);
    }
}
