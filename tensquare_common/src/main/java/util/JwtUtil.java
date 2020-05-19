package util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Date;

@ConfigurationProperties("jwt.config")
public class JwtUtil {
    private String key;//盐
    private Long ttl;//过期时间

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Long getTtl() {
        return ttl;
    }

    public void setTtl(Long ttl) {
        this.ttl = ttl;
    }

    /**
     * 生成JwtToken令牌
     * @param id
     * @param subject
     * @param roles
     * @return
     */
    public String createJwt(String id, String subject, String roles){
        JwtBuilder jwtBuilder= Jwts.builder().setId(id)
                .setSubject(subject)
                .setIssuedAt(new Date())
                .signWith(SignatureAlgorithm.HS256,key)
                .claim("roles",roles);
        if(ttl>0){
            jwtBuilder.setExpiration(new Date(new Date().getTime()+ttl));
        }
        return jwtBuilder.compact();
    }

    /**
     * 解析令牌
     * @param secrete
     * @return
     */
    public Claims parseJwt(String secrete){
        Claims claims=Jwts.parser().setSigningKey(key)
                .parseClaimsJws(secrete)
                .getBody();
        return claims;
    }

}
