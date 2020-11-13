package org.jeeasy.security.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * @author AlpsDDJ
 * @date 2020/11/12
 */
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class JwtUserDetails {
    private String username;
    private Integer number;
    private LocalDateTime loginTime;
    private String loginIp;
}
