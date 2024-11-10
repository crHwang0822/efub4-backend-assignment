package efub.assignment.community.global.jwt;

import efub.assignment.community.global.util.RedisUtil;
import efub.assignment.community.member.domain.Member;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import java.time.Duration;
import java.util.Collections;
import java.util.Date;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Log4j2
public class TokenProvider {

    @Value("${jwt.secret}")
    private String secretKey;

    private static final long ACCESS_TOKEN_EXPIRE_TIME = 1000 * 60 * 30L;
    public static final long REFRESH_TOKEN_EXPIRE_TIME = 1000 * 60 * 60L * 24 * 7;

    private final RedisUtil redisUtil;

    public String createAccessToken(Member member){
        Date now = new Date();
        return Jwts.builder()
            .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
            .setIssuedAt(now)
            .setExpiration(new Date(now.getTime()+ACCESS_TOKEN_EXPIRE_TIME))
            .setSubject(member.getEmail())
            .signWith(SignatureAlgorithm.HS256,secretKey)
            .compact();
    }

    public String createRefreshToken(Member member){
        Date now = new Date();
        return Jwts.builder()
            .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
            .setIssuedAt(now)
            .setExpiration(new Date(now.getTime()+REFRESH_TOKEN_EXPIRE_TIME))
            .setSubject(member.getEmail())
            .signWith(SignatureAlgorithm.HS256,secretKey)
            .compact();
    }

    public void saveRefreshToken(Long memberId, String refreshToken) {
        redisUtil.setValues(memberId.toString(), refreshToken, Duration.ofMillis(REFRESH_TOKEN_EXPIRE_TIME));
    }

    public String extractEmail(String accessToken){
        if(validateToken(accessToken)){
            return getClaims(accessToken).getSubject();
        }
        return null;
    }

    public boolean validateToken(String token){
        try{
            Jwts.parser()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token);
            return true;
        } catch (SecurityException | MalformedJwtException e) {
            log.info("Invalid JWT token", e);
        } catch (ExpiredJwtException e) {
            log.info("Expired JWT token", e);
        } catch (UnsupportedJwtException e) {
            log.info("Unsupported JWT token", e);
        } catch (IllegalArgumentException e) {
            log.info("JWT claims string is empty", e);
        }
        return false;
    }


    public Authentication getAuthentication(String token){
        Claims claims = getClaims(token);

        Set<SimpleGrantedAuthority> authorities = Collections
            .singleton(new SimpleGrantedAuthority("ROLE_USER"));

        return new UsernamePasswordAuthenticationToken(new org.springframework.security.core.userdetails
            .User(claims.getSubject(), "", authorities), token, authorities);
    }

    private Claims getClaims(String token){
        return Jwts.parser()
            .setSigningKey(secretKey)
            .build()
            .parseClaimsJws(token)
            .getPayload();
    }
}
