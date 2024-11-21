package qtx.dubbo.security.bean;

import lombok.Data;

/**
 * @author qtx
 * @since 2024/11/21 20:06
 */
@Data
public class AuthPathBO {

    private String path;

    private String method;

    private String description;

    private String type;

    private String service;
}
