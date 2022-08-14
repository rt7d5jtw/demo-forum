package demo.utils;

import java.security.Key;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.spec.SecretKeySpec;

import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import lombok.AccessLevel;
import lombok.Getter;

@Service
final public class JwtHandler {

  @Getter(AccessLevel.PUBLIC)
  private final String jwtSecret;

  public JwtHandler(String jwtSecret) {
    this.jwtSecret = jwtSecret;
  }

  public String createToken(
      Map<String, Object> body,
      LocalDateTime expiration
      ) {
    
    byte[] secretBytes = Base64.getDecoder().decode(jwtSecret);
    Key key = new SecretKeySpec(secretBytes, SignatureAlgorithm.HS512.getJcaName());

    var jwt = Jwts.builder()
      .setSubject("test user")
      .setAudience("test demo")
      .setIssuedAt(Timestamp.valueOf(LocalDateTime.now()))
      .setExpiration(Timestamp.valueOf(LocalDateTime.now().plusMinutes(20)))
      .setClaims(body)
      .signWith(key)
      .compact();

    return jwt;
  }

  public HashMap<String, Object> verifyToken(String token) throws JwtException {
    Claims claims = Jwts.parserBuilder()
      .setSigningKey(Base64.getDecoder().decode(jwtSecret))
      .build()
      .parseClaimsJws(token).getBody();

    return new HashMap<>(claims);
  }
}
