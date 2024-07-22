package org.jeeasy.common.core.tools;

import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;

import java.net.InetAddress;
import java.net.UnknownHostException;


@Log4j2
public class ApplicationUtil {
    public static void printAppInfo(ConfigurableApplicationContext application) {
        Environment env = application.getEnvironment();
        try {
            String ip = InetAddress.getLocalHost().getHostAddress();
            String port = env.getProperty("server.port");
            String path = env.getProperty("server.servlet.context-path");
            String appName = env.getProperty("spring.application.name");
            String activeProfile = env.getProperty("spring.profiles.active");

            path = StringUtils.isBlank(path) ? "" : path;

            String appInfo = """
                    \n----------------------------------------------------------
                        Application %s is running!
                        Env: %s
                        Access URLs:
                            Local: 		http://localhost:%s/
                            External: 	http://%s:%s/
                    ----------------------------------------------------------
                    """.formatted(appName, activeProfile, port + path, ip, port + path);
            log.info(appInfo);
        } catch (UnknownHostException e) {
            log.warn(String.valueOf(e));
        }
    }
}
