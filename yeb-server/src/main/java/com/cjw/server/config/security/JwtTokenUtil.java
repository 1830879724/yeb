package com.cjw.server.config.security;


import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * JwtToken工具类
 */
@Component
public class JwtTokenUtil {
    private static final String CLAIM_KEY_USERNAME = "sub";

    private static final String CLAIM_KEY_CREATED = "created";

    @Value("${jwt.secret}")//jwt秘钥
    private String secret;

    @Value("${jwt.expiration}")//失效时间
    private Long expiration;

    //根据用户信息生成Token
    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();

        claims.put(CLAIM_KEY_USERNAME, userDetails.getUsername());
        claims.put(CLAIM_KEY_CREATED, new Date());
        return generateToken(claims);
    }

    /**
     * 从Token获取用户名
     */
    public String genUserNameFromToken(String token) {
        String username;
        try {
            //从token获取荷载
            Claims claims = getClaimsFormToken(token);
            //通过荷载获取用户名
            username = claims.getSubject();
        } catch (Exception e) {
            username = null;
        }
        return username;
    }

    /**
     *验证token是否有效
     * @param token
     * @param userDetails
     * @return
     */
    public  boolean validateToken(String token,UserDetails userDetails){
        //获取用户名
       String username =  genUserNameFromToken(token);
       return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    /**
     * 判断token是否有效
     * @param token
     * @return
     */
    private boolean isTokenExpired(String token) {
        Date expiredDate=getExpiredDateFromToken(token);
        return expiredDate.before(new Date());
    }

    /**
     * 判断token是否可以被刷新
     * @param token
     * @return
     */
    public boolean canRefresh(String token){
        return  !isTokenExpired(token);
    }

    /**
     * 刷新token
     * @param token
     * @return
     */
    public String refreshToken(String  token){
      Claims claims =   getClaimsFormToken(token);
      claims.put(CLAIM_KEY_CREATED,new Date());
      return generateToken(claims);
    }



    /**
     * 从token中获取过去时间
     * @param token
     * @return
     */
    private Date getExpiredDateFromToken(String token) {
        Claims claims =  getClaimsFormToken(token);
        return claims.getExpiration();
    }


    /**
     * 从token获取荷载
     * @param token
     * @return
     */
    private Claims getClaimsFormToken(String token) {
        Claims claims = null;
        try {
            claims = Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return claims;
    }

    /**
     * 根据荷载生成JWT  Token
     *
     * @param claims
     * @return
     */
    public String generateToken(Map<String, Object> claims) {
        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(generateExpirationDate())
                .signWith(SignatureAlgorithm.ES512, secret)
                .compact();
    }

    /**
     * 生成Token失效时间
     *
     * @return
     */
    public Date generateExpirationDate() {
        return new Date(System.currentTimeMillis() + expiration * 1000);
    }
}
