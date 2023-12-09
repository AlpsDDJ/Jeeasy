package org.jeeasy.common.core.handler.userpwd;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import lombok.Getter;
import lombok.Setter;
import org.jeeasy.common.core.exception.JeeasyException;
import org.jeeasy.common.core.tools.PwdUtil;
import org.jeeasy.common.core.tools.Tools;

/**
 * 用户密码处理器
 *
 * @param <T> 泛型类型参数，扩展自 IUserPassword 接口
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

    /**
     * 创建一个UserPasswordHandler对象
     *
     * @param sysUser 用户对象
     * @param <T>     用户密码接口类型
     * @return UserPasswordHandler对象
     */
    public static <T extends IUserPassword> UserPasswordHandler<T> create(T sysUser) {
        return new UserPasswordHandler<>(sysUser);
    }


    /**
     * 初始化盐值和密码
     *
     * @return 返回用户对象
     * @throws JeeasyException 用户不存在时抛出该异常
     * @throws JeeasyException 用户名为空时抛出该异常
     */
    public T initSaltAndPassword() {
        if (Tools.isEmpty(this.user)) {
            throw new JeeasyException("用户不存在");
        }
        String username = this.user.getUsername();
        if (StrUtil.isEmpty(username)) {
            throw new JeeasyException("用户名不能为空");
        }
        String salt = getSalt();
        String pwd = PwdUtil.encrypt(username, DEFAULT_PASSWORD, salt);
        this.user.setSalt(salt);
        this.user.setPassword(pwd);
        return this.user;
    }


    /**
     * 修改密码
     *
     * @param password 新密码
     * @return 修改后的用户对象
     */
    public T changePassword(String password) {
        if (Tools.isEmpty(this.user)) {
            return null;
        }
        String pwd = PwdUtil.encrypt(this.user.getUsername(), password, this.user.getSalt());
        this.user.setPassword(pwd);
        return this.user;
    }


    /**
     * 获取盐值
     * 盐值是一段随机字符串，用于增加密码等敏感信息的安全性
     *
     * @return 返回一个长度为10的随机字符串作为盐值
     */
    public static String getSalt() {
        return RandomUtil.randomString(10);
    }


    /**
     * 检查密码是否符合要求
     *
     * @param password 用户输入的密码
     * @return 如果密码符合要求返回true，否则返回false
     */
    public boolean checkPassword(String password) {
        if (Tools.isEmpty(this.user) || Tools.isEmpty(password)) {
            return false;
        }
        return checkPassword(user.getUsername(), password, this.user.getPassword(), this.user.getSalt());
    }


    /**
     * 检查密码是否匹配
     *
     * @param username     用户名
     * @param password     用户输入的密码
     * @param realPassword 数据库中取出的真实存储密码
     * @param salt         密码盐
     * @return 密码是否匹配
     */
    public static boolean checkPassword(String username, String password, String realPassword, String salt) {
        if (StrUtil.hasEmpty(realPassword, username, realPassword, salt)) {
            return false;
        }
        String encryptPassword = PwdUtil.encrypt(username, password, salt);
        return encryptPassword.equals(realPassword);
    }

}
