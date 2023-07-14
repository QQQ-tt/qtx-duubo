package qtx.dubbo.config.utils;


import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.Data;
import org.springframework.stereotype.Component;
import qtx.dubbo.java.enums.DataEnums;
import qtx.dubbo.java.exception.DataException;

import java.util.Date;

/**
 * @author qtx
 * @since 2022/8/30
 */
@Data
public class JwtUtils {

    // 密钥
    private static final String SECRET_KEY;

    static {
        SECRET_KEY = "Wem0eH23QIuujXvefmx8GZbfIz+3nWvltFaYlq0EjtvnRvoz414Yhx+6wLptWnJ00mTqbcFeDjXWfwSQAIcc5w==";
    }

    // 10 days
    private static final long EXPIRATION_TIME = 864_000_000;
    // 签发人
    private static final String ISSUER = "qtx";

    /**
     * 生成token
     *
     * @param username 加密字符
     * @return token
     */
    public static String generateToken(String username) {
        Date now = new Date();
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .setIssuer(ISSUER)
                .compact();
    }

    /**
     * 解密信息
     *
     * @param token 加密字符串
     * @return 用户信息
     */
    public static String getUsernameFromToken(String token) {
        if (StringUtils.isBlank(token)) {
            new DataException(DataEnums.DATA_IS_ABNORMAL);
        }
        if (getIssuerFromToken(token).equals(ISSUER)) {
            Claims claims = Jwts.parser()
                    .setSigningKey(SECRET_KEY)
                    .parseClaimsJws(token)
                    .getBody();
            return claims.getSubject();
        }
        return null;
    }

    /**
     * 获取签发人
     * @param token 加密信息
     * @return 签发人
     */
    public static String getIssuerFromToken(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();
        return claims.getIssuer();
    }

    /**
     * 判断token合法性
     * @param token 加密信息
     * @return 是否合法
     */
    public static boolean validateToken(String token) {
        try {
            Jwts.parser()
                    .setSigningKey(SECRET_KEY)
                    .parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 刷新token过期时间
     *
     * @param token 加密信息
     * @return 新的加密信息
     */
    public static String refreshToken(String token) {
        String username = getUsernameFromToken(token);
        return generateToken(username);
    }

    /**
     * 判断令牌是否过期
     *
     * @param token 加密信息
     * @return 是否过期
     */
    public static Boolean isTokenExpired(String token) {
        try {
            return Jwts.parser()
                    .setSigningKey(SECRET_KEY)
                    .parseClaimsJws(token)
                    .getBody()
                    .getExpiration()
                    .after(new Date());
        } catch (Exception e) {
            return false;
        }
    }
}
