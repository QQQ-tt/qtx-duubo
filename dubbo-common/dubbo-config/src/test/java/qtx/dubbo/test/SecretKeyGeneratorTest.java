package qtx.dubbo.test;

import org.junit.jupiter.api.Test;

import java.security.SecureRandom;
import java.util.Base64;

/**
 * @author qtx
 * @since 2023/7/14
 */
public class SecretKeyGeneratorTest {

    /**
     * 生成安全密钥
     */
    @Test
    public void test() {
        SecureRandom random = new SecureRandom();
        byte[] keyBytes = new byte[64];
        random.nextBytes(keyBytes);

        System.out.println(Base64.getEncoder()
                .encodeToString(keyBytes));
    }
}
