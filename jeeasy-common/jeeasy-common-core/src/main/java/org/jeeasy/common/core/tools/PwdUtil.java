package org.jeeasy.common.core.tools;

import cn.hutool.core.util.CharsetUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.digest.MD5;
import cn.hutool.crypto.symmetric.SymmetricAlgorithm;
import cn.hutool.crypto.symmetric.SymmetricCrypto;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.Charset;

/**
 * 密码工具类
 *
 * @author AlpsDDJ
 * @date 2020/11/10
 */
@Slf4j
public class PwdUtil {

    private static final SymmetricAlgorithm ALGORITHM = SymmetricAlgorithm.AES;
    private static final Charset CHARSET = CharsetUtil.CHARSET_UTF_8;

    /**
     * 加密
     *
     * @param username 用户名
     * @param password 密码
     * @param salt     盐值
     * @return 加密后的密码
     */
    public static String encrypt(String username, String password, String salt) {
        SymmetricCrypto crypto = getSymmetricCrypto(username, salt);
        return crypto.encryptHex(password, CHARSET);
    }

    /**
     * 解密
     *
     * @param username 用户名
     * @param password 密码
     * @param salt     盐值
     * @return 解密后的密码
     */
    public static String decrypt(String username, String password, String salt) {
        SymmetricCrypto crypto = getSymmetricCrypto(username, salt);
        return crypto.decryptStr(password, CHARSET);
    }

    /**
     * 根据给定的用户名和盐值获取对称加密对象
     *
     * @param username 用户名
     * @param salt     盐值
     * @return 对称加密对象
     */
    private static SymmetricCrypto getSymmetricCrypto(String username, String salt) {
        String key = MD5.create().digestHex16(username + salt, CHARSET);
        return new SymmetricCrypto(ALGORITHM, SecureUtil.generateKey(ALGORITHM.getValue(), key.getBytes(CHARSET)).getEncoded());
    }


}
