package com.bob.tensquare;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

import java.text.SimpleDateFormat;

public class ParseJwt {
    public static void main(String[] args) {
        //无状态登录，服务器端没有保存相关登录信息；有状态登录，在服务器端保存相关信息
         Claims claims=Jwts.parser().setSigningKey("hell")
                .parseClaimsJws("eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiIxIiwic3ViIjoi5YiY5a625q-FIiwiaWF0IjoxNTg5ODU4ODU1LCJleHAiOjE1ODk4NTg5MTUsInJvbGUiOiJhZG1pbiJ9.0h2dH66NJCoUzkJoJyA5g8KB2scPuKoIH8F98kzyiNQ")
                .getBody();
        System.out.println("用户id:"+claims.getId());
        System.out.println("用户名:"+claims.getSubject());
        System.out.println("签发时间:"+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(claims.getIssuedAt()));
        System.out.println("过期时间:"+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(claims.getExpiration()));
        System.out.println(":"+claims.get("role"));
    }
}
