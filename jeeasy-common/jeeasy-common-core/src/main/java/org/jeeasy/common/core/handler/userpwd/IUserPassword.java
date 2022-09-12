package org.jeeasy.common.core.handler.userpwd;

/**
 * TODO
 *
 * @author wei.yang
 * @date 2022-09-11 23:57
 */
public interface IUserPassword {
    IUserPassword setUsername(String username);
    IUserPassword setSalt(String salt);
    IUserPassword setPassword(String password);
    String getUsername();
    String getSalt();
    String getPassword();
}
