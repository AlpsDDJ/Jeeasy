package org.jeeasy.common.core.tools;

import cn.hutool.core.util.CharsetUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.digest.MD5;
import cn.hutool.crypto.symmetric.SymmetricAlgorithm;
import cn.hutool.crypto.symmetric.SymmetricCrypto;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.Charset;

/**
 * @author AlpsDDJ
 * @date 2020/11/10
 */
@Slf4j
public class PwdUtil {

    private static final SymmetricAlgorithm ALGORITHM = SymmetricAlgorithm.AES;
    private static final Charset CHARSET = CharsetUtil.CHARSET_UTF_8;

    /**
     * 加密
     * @param username
     * @param password
     * @param salt
     * @return
     */
    public static String encrypt(String username, String password, String salt){
        SymmetricCrypto crypto = getSymmetricCrypto(username, salt);
        return crypto.encryptHex(password, CHARSET);
    }

    /**
     * 解密
     * @param username
     * @param password
     * @param salt
     * @return
     */
    public static String decrypt(String username, String password, String salt){
        SymmetricCrypto crypto = getSymmetricCrypto(username, salt);
        return crypto.decryptStr(password, CHARSET);
    }

    private static SymmetricCrypto getSymmetricCrypto(String username, String salt){
        String key = MD5.create().digestHex16(username + salt, CHARSET);
        return new SymmetricCrypto(ALGORITHM, SecureUtil.generateKey(ALGORITHM.getValue(), key.getBytes(CHARSET)).getEncoded());
    }

//    public static void main(String[] args) {
//        String username = "admi1你好213123n1";
//        String password = "1234564567456748456345623463756";
//        String salt = "kjyck2";
//        String encryptPwd = PwdUtil.encrypt(username, password, salt);
//        System.out.println(encryptPwd);
//        String pwd = decrypt(username, encryptPwd, salt);
//        System.out.println(pwd);
//    }
}