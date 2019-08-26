package com.funnyx.oauth.util;

import com.google.common.collect.Maps;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class JwtUtil {

  @Value("${secret:aaabbbcccdddeeefffggghhhiiijjjkkklllmmmnnnooopppqqqrrrsssttt}")
  private String secret;

  @Value("${expire-time-in-second:1209600}")
  private Long expirationTimeInSecond;

  /**
   * 从token中获取claim
   *
   * @param token token
   * @return claim
   */
  public Claims getClaimsFromToken(String token) {
    try {
      return Jwts.parser().setSigningKey(this.secret.getBytes()).parseClaimsJws(token).getBody();
    } catch (ExpiredJwtException
        | UnsupportedJwtException
        | MalformedJwtException
        | IllegalArgumentException e) {
      log.error("token解析错误", e);
      throw new IllegalArgumentException("Token invalided.");
    }
  }

  /**
   * 获取token的过期时间
   *
   * @param token token
   * @return 过期时间
   */
  public Date getExpirationDateFromToken(String token) {
    return getClaimsFromToken(token).getExpiration();
  }

  /**
   * 判断token是否过期
   *
   * @param token token
   * @return 已过期返回true，未过期返回false
   */
  private Boolean isTokenExpired(String token) {
    Date expiration = getExpirationDateFromToken(token);
    return expiration.before(new Date());
  }

  /**
   * 计算token的过期时间
   *
   * @return 过期时间
   */
  private Date getExpirationTime() {
    return new Date(System.currentTimeMillis() + this.expirationTimeInSecond * 1000);
  }

  /**
   * 为指定用户生成token
   *
   * @param claims 用户信息
   * @return token
   */
  public String generateToken(Map<String, Object> claims) {
    Date createdTime = new Date();
    Date expirationTime = this.getExpirationTime();

    byte[] keyBytes = secret.getBytes();
    SecretKey key = Keys.hmacShaKeyFor(keyBytes);

    return Jwts.builder()
        .setClaims(claims)
        .setIssuedAt(createdTime)
        .setExpiration(expirationTime)
        // 你也可以改用你喜欢的算法
        // 支持的算法详见：https://github.com/jwtk/jjwt#features
        .signWith(key, SignatureAlgorithm.HS256)
        .compact();
  }

  /**
   * 判断token是否非法
   *
   * @param token token
   * @return 未过期返回true，否则返回false
   */
  public Boolean validateToken(String token) {
    return !isTokenExpired(token);
  }

  public static void main(String[] args) {

    JwtUtil jwtUtil = new JwtUtil();
    jwtUtil.expirationTimeInSecond = 1209600L;
    jwtUtil.secret = "aaabbbcccdddeeefffggghhhiiijjjkkklllmmmnnnooopppqqqrrrsssttt";

    // 2.设置用户信息
    HashMap<String, Object> objectObjectHashMap = Maps.newHashMap();
    objectObjectHashMap.put("id", "1");

    String token = jwtUtil.generateToken(objectObjectHashMap);
    System.out.println(token);
  }
}
