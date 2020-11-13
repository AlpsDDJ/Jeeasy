package org.jeeasy.system.tools;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import lombok.Getter;
import lombok.Setter;
import org.jeeasy.common.core.tools.PwdUtil;
import org.jeeasy.common.core.tools.Tools;
import org.jeeasy.system.modules.user.entity.SysUser;

/**
 * @author AlpsDDJ
 * @date 2020/11/10
 */
@Getter
@Setter
public class SysUserUtil {
    private SysUser sysUser;

    private static final String DEFAULT_PASSWORD = "123456";

    public SysUserUtil(SysUser sysUser) {
        this.sysUser = sysUser;
    }

    public static SysUserUtil create(SysUser sysUser){
        return new SysUserUtil(sysUser);
    }

    /**
     * 初始化用户 salt 和 password
     * @return SysUser
     */
    public SysUser initSaltAndPassword() {
        if(Tools.isEmpty(this.sysUser)){
            return null;
        }
        String salt = getSalt();
        String pwd = PwdUtil.encrypt(this.sysUser.getUsername(), DEFAULT_PASSWORD, salt);
        this.sysUser.setSalt(salt);
        this.sysUser.setPassword(pwd);
        return this.sysUser;
    }

    public SysUser changePassword(String password){
        if(Tools.isEmpty(this.sysUser)){
            return null;
        }
        String pwd = PwdUtil.encrypt(this.sysUser.getUsername(), password, this.sysUser.getSalt());
        this.sysUser.setPassword(pwd);
        return this.sysUser;
    }

    public static String getSalt() {
        return RandomUtil.randomString(10);
    }


    /**
     *
     * @param password 用户输入的密码
     * @return
     */
    public boolean checkPassword(String password){
        if(Tools.isEmpty(this.sysUser) || Tools.isEmpty(password)){
            return false;
        }
        return checkPassword(sysUser.getUsername(), password, this.sysUser.getPassword(),  this.sysUser.getSalt());
    }

    /**
     * 密码验证
     * @param username 用户名
     * @param password 用户输入的密码
     * @param realPassword 数据库中取出的真实存储密码
     * @param salt 密码盐
     * @return
     */
    public static boolean checkPassword(String username, String password, String realPassword, String salt){
        if(StrUtil.hasEmpty(realPassword, username, realPassword, salt)){
            return false;
        }
        String encryptPassword = PwdUtil.encrypt(username, password, salt);
        return encryptPassword.equals(realPassword);
    }
}
