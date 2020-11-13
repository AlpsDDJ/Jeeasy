package org.jeeasy.system.modules.demo.security.model;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author AlpsDDJ
 * @date 2020/11/13
 */
@Data
@Accessors(chain = true)
public class DemoUser {
    private String id;
    private String username;
    private String password;
}
