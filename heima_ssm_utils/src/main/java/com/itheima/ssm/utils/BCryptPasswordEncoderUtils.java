package com.itheima.ssm.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class BCryptPasswordEncoderUtils {

    private static BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder ();


    public static String encodePassword(String password){

        return bCryptPasswordEncoder.encode (password);

    }

    public static void main(String[] args) {
        String password = "admin";
        String pwd = encodePassword (password);
        //$2a$10$.lRcoxnjq5mJsamE6HaKGeiTeXiVNaiCAIfhelqvNboDaJk7tKyIW
        //$2a$10$a11DZIxzdI5R6D5qR4K5F.XWOcz3BgjEondTpkZENF8KVCYPQqdma
        //每次加密底层加盐 所以每次得到结果不一样  但是底层解密后是一样的密码 所以更安全
        System.out.println (pwd);
        System.out.println (pwd.length ());
    }
}
