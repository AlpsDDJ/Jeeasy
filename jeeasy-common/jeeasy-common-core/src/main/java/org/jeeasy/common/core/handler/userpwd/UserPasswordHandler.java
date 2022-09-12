package org.jeeasy.common.core.handler.userpwd;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import lombok.Getter;
import lombok.Setter;
import org.jeeasy.common.core.exception.JeeasyException;
import org.jeeasy.common.core.tools.PwdUtil;
import org.jeeasy.common.core.tools.Tools;

/**
 * @author AlpsDDJ
 * @date 2020/11/10
 */
@Getter
@Setter
public class UserPasswordHandler<T extends IUserPassword> {
    private T user;

    private static final String DEFAULT_PASSWORD = "123456";

    public UserPasswordHandler(T user) {
        this.user = user;
    }

    public static <T extends IUserPassword> UserPasswordHandler<T> create(T sysUser) {
        return new UserPasswordHandler<>(sysUser);
    }

    /**
     * 初始化用户 salt 和 password
     *
     * @return SysUser
     */
    public T initSaltAndPassword() {
        if (Tools.isEmpty(this.user)) {
            throw new JeeasyException("用户不存在");
        }
        String username = this.user.getUsername();
        if(StrUtil.isEmpty(username)) {
            throw new JeeasyException("用户名不能为空");
        }
        String salt = getSalt();
        String pwd = PwdUtil.encrypt(username, DEFAULT_PASSWORD, salt);
        this.user.setSalt(salt);
        this.user.setPassword(pwd);
        return this.user;
    }

    public T changePassword(String password) {
        if (Tools.isEmpty(this.user)) {
            return null;
        }
        String pwd = PwdUtil.encrypt(this.user.getUsername(), password, this.user.getSalt());
        this.user.setPassword(pwd);
        return this.user;
    }

    public static String getSalt() {
        return RandomUtil.randomString(10);
    }


    /**
     * @param password 用户输入的密码
     * @return
     */
    public boolean checkPassword(String password) {
        if (Tools.isEmpty(this.user) || Tools.isEmpty(password)) {
            return false;
        }
        return checkPassword(user.getUsername(), password, this.user.getPassword(), this.user.getSalt());
    }

    /**
     * 密码验证
     *
     * @param username     用户名
     * @param password     用户输入的密码
     * @param realPassword 数据库中取出的真实存储密码
     * @param salt         密码盐
     * @return
     */
    public static boolean checkPassword(String username, String password, String realPassword, String salt) {
        if (StrUtil.hasEmpty(realPassword, username, realPassword, salt)) {
            return false;
        }
        String encryptPassword = PwdUtil.encrypt(username, password, salt);
        return encryptPassword.equals(realPassword);
    }
}
