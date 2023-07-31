package qtx.dubbo.model.bo.activity;

import lombok.Data;

/**
 * @author qtx
 * @since 2023/3/16 22:07
 */
@Data
public class AcBO {

  private String acUuid;

  private String name;

  private String nodeName;

  private Integer nodeGroup;

  private Integer nodePassNum;

  private Boolean nodeType;

  private String acNameUuid;

  private Boolean hidden;

  private String businessInfo;
}
