package com.tech_viks.steganoapp;

import java.security.Key;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;
import android.annotation.SuppressLint;
import android.util.Base64;

/*
 * PBE——Password-based encryption（基于密码加密）。<br>
 * 其特点在于口令由用户自己掌管，不借助任何物理媒体；采用随机数（这里我们叫做盐）杂凑多重加密等方法保证数据的安全性。<br>
 * 是一种简便的加密方式。<br>
 *
 * @author <a href="mailto:hongtenzone@foxmail.com">hongten</a><br>
 * @date 2013-4-3<br>
 *
 * @see <a href="http://blog.csdn.net/hexingzhi/article/details/7424872">原文</a>
 */
public class PBE {

    /*
     * JAVA6支持以下任意一种算法 PBEWITHMD5ANDDES PBEWITHMD5ANDTRIPLEDES<测试的时候报错>
     * PBEWITHSHAANDDESEDE<测试的时候报错> PBEWITHSHA1ANDRC2_40 PBKDF2WITHHMACSHA1<测试的时候报错>
     */

    /*
     * 本地测试通过：<code>PBEWITHMD5ANDDES</code>,<code>PBEWITHSHA1ANDRC2_40</code>
     */

    /*
     * 定义使用的算法为:PBEWITHMD5andDES算法
     */
    public static final String ALGORITHM = "PBEWITHMD5ANDDES";

    /*
     * 定义迭代次数为1000次,次数越多，运算越大，越不容易破解之类。
     */
    private static final int ITERATIONCOUNT = 190;//origin use 1000

    private  String saltStr;
    /*
     * 获取加密算法中使用的盐值,解密中使用的盐值必须与加密中使用的相同才能完成操作. 盐长度必须为8字节的倍数
     *
     * @return byte[] 盐值
     */
    @SuppressLint("TrulyRandom")
    private  byte[] getSalt() throws Exception {
        // 实例化安全随机数  // 产出盐
        byte[] a={0,2,3,8,7,5,};
        //SecureRandom random = new SecureRandom();
       // return random.generateSeed(32);
        return a;
    }
    /*
     * 根据PBE密码生成一把密钥
     *
     * @param password
     *            生成密钥时所使用的密码
     * @return Key PBE算法密钥
     * */

    private  Key getPBEKey(String password) throws Exception {
        // 实例化使用的算法
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(ALGORITHM);
        // 设置PBE密钥参数
        PBEKeySpec keySpec = new PBEKeySpec(password.toCharArray());
        // 生成密钥
        SecretKey secretKey = keyFactory.generateSecret(keySpec);

        return secretKey;
    }

    /*
     * 加密明文字符串
     *
     * @param plaintext
     *            待加密的明文字符串
     * @param password
     *            生成密钥时所使用的密码
     * @param salt
     *            盐值
     * @return 加密后的密文字符串
     * @throws Exception
     */
    public  String encrypt(String plaintext, String password)
            throws Exception {

        Key key = getPBEKey(password);
        byte[] salt = getSalt();
        saltStr = Base64.encodeToString(salt, Base64.DEFAULT);

        PBEParameterSpec parameterSpec = new PBEParameterSpec(salt,ITERATIONCOUNT);

        Cipher cipher = Cipher.getInstance(ALGORITHM);

        cipher.init(Cipher.ENCRYPT_MODE, key, parameterSpec);

        byte encipheredData[] = cipher.doFinal(plaintext.getBytes("UTF-8"));

        return Base64.encodeToString(encipheredData, Base64.DEFAULT);
    }
    public String getSaltStr(){
        return saltStr;
    }
    /*
     * 解密密文字符串
     *
     * @param ciphertext
     *            待解密的密文字符串
     * @param password
     *            生成密钥时所使用的密码(如需解密,该参数需要与加密时使用的一致)
     * @param salt
     *            盐值(如需解密,该参数需要与加密时使用的一致)
     * @return 解密后的明文字符串
     * @throws Exception
     */
    // public  String decrypt(String ciphertext, String password, String saltStr)
    public  String decrypt(String ciphertext, String password)
            throws Exception {

        Key key = getPBEKey(password);
        //byte[] salt = Base64.decode(saltStr,Base64.DEFAULT);

        PBEParameterSpec parameterSpec = new PBEParameterSpec(getSalt(),
                ITERATIONCOUNT);

        Cipher cipher = Cipher.getInstance(ALGORITHM);

        cipher.init(Cipher.DECRYPT_MODE, key, parameterSpec);

        byte[] passDec = cipher.doFinal(Base64.decode(ciphertext, Base64.DEFAULT));
        return new String(passDec);
    }

}