package qtx.dubbo.security;

import lombok.Data;

/**
 * @author qtx
 * @since 2023/12/22
 */
@Data
public class Login {

    private String userCode;

    private String password;
}
