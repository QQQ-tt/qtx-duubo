CREATE TABLE `ac_business`
(
    `id`            int                                                          NOT NULL AUTO_INCREMENT,
    `ac_node_id`    int                                                          NOT NULL,
    `ac_name_id`    int                                                          NOT NULL,
    `business_info` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci          DEFAULT NULL COMMENT '实际关联业务(处理人或角色)',
    `delete_flag`   bit(1)                                                       NOT NULL DEFAULT b'0' COMMENT '是否删除 0:否 1:是',
    `create_by`     varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '创建人',
    `create_on`     datetime                                                     NOT NULL COMMENT '创建时间',
    `update_by`     varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci          DEFAULT NULL COMMENT '修改人',
    `update_on`     datetime                                                              DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci COMMENT ='流程节点业务表';