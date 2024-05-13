package qtx.dubbo.java.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SecureDigestAlgorithm;
import lombok.Data;
import lombok.val;
import org.apache.dubbo.common.utils.StringUtils;
import qtx.dubbo.java.enums.DataEnums;
import qtx.dubbo.java.exception.DataException;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author qtx
 * @since 2022/8/30
 */
@Data
public class JwtUtils {

    // 密钥
    private static final String SECRET_KEY;
    // 密钥实例
    private static final SecretKey KEY;

    static {
        SECRET_KEY = "Wem0eH23QIuujXvefmx8GZbfIz+3nWvltFaYlq0EjtvnRvoz414Yhx+6wLptWnJ00mTqbcFeDjXWfwSQAIcc5w==";
        KEY = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    }

    // 10 days jwt过期时间，单位毫秒
    private static final long EXPIRATION_TIME = 864_000_000;

    // 签发人
    private static final String ISSUER = "qtx";


    /**
     * 生成jwt
     * 使用Hs256算法，私钥使用固定密钥
     *
     * @param claims 设置的信息
     * @return token
     */
    public static String generateToken(String userId, Map<String, Object> claims) {
        //指定加密算法
        SecureDigestAlgorithm<SecretKey, SecretKey> algorithm = Jwts.SIG.HS256;
        //生成JWT的时间
        long expMillis = System.currentTimeMillis() + EXPIRATION_TIME;
        Date exp = new Date(expMillis);

        return Jwts.builder()
                .claims(claims)
                .subject(userId)
                .signWith(KEY, algorithm)
                .issuer(ISSUER)
                .expiration(exp)
                .compact();
    }

    /**
     * 解密信息
     *
     * @param token 加密字符串
     * @return 用户信息
     */
    public static String getBodyFromToken(String token) {
        if (StringUtils.isBlank(token)) {
            new DataException(DataEnums.DATA_IS_ABNORMAL);
        }
        if (getIssuerFromToken(token).equals(ISSUER)) {
            val claims = getClaimsFromToken(token);
            assert claims != null;
            return claims.getSubject();
        }
        return null;
    }

    /**
     * 解密信息
     *
     * @param token 加密字符串
     * @return 用户信息
     */
    public static Claims getClaimsFromToken(String token) {
        if (StringUtils.isBlank(token)) {
            new DataException(DataEnums.DATA_IS_ABNORMAL);
        }
        if (getIssuerFromToken(token).equals(ISSUER)) {
            return Jwts.parser()
                    .verifyWith(KEY)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
        }
        return null;
    }

    /**
     * 获取签发人
     *
     * @param token 加密信息
     * @return 签发人
     */
    public static String getIssuerFromToken(String token) {
        val claims = Jwts.parser()
                .verifyWith(KEY)
                .build()
                .parseSignedClaims(token);
        return claims.getPayload()
                .getIssuer();
    }

    /**
     * 判断token合法性
     *
     * @param token 加密信息
     * @return 是否合法
     */
    public static boolean validateToken(String token) {
        try {
            Jwts.parser()
                    .verifyWith(KEY)
                    .build()
                    .parseSignedClaims(token);
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
        String userId = getBodyFromToken(token);
        val claims = getClaimsFromToken(token);
        assert claims != null;
        HashMap<String, Object> map = new HashMap<>(claims);
        return generateToken(userId, map);
    }

    /**
     * 判断令牌是否过期
     *
     * @param token 加密信息
     * @return 是否过期
     */
    public static Boolean isTokenExpired(String token) {
        try {
            val claims = getClaimsFromToken(token);
            assert claims != null;
            return !claims.getExpiration()
                    .after(new Date());
        } catch (Exception e) {
            return true;
        }
    }
}
