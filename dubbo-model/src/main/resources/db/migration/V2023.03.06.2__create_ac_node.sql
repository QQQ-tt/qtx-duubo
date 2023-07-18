CREATE TABLE `ac_node`
(
    `id`            int                                                          NOT NULL AUTO_INCREMENT,
    `ac_name_id`    int                                                          NOT NULL,
    `node_name`     varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci          DEFAULT NULL COMMENT '节点名称',
    `node_group`    int                                                                   DEFAULT NULL COMMENT '节点组编号',
    `node_pass_num` int                                                                   DEFAULT NULL COMMENT
        '当前节点通过个数',
    `is_hidden`     bit(1)                                                                DEFAULT b'0' COMMENT '是否隐藏',
    `node_type`     bit(1)                                                                DEFAULT b'1' COMMENT '是否为基础节点',
    `ac_name_uuid`  varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci          DEFAULT NULL COMMENT '节点对应的流程uuid',
    `delete_flag`   bit(1)                                                       NOT NULL DEFAULT b'0' COMMENT '是否删除 0:否 1:是',
    `create_by`     varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '创建人',
    `create_on`     datetime                                                     NOT NULL COMMENT '创建时间',
    `update_by`     varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci          DEFAULT NULL COMMENT '修改人',
    `update_on`     datetime                                                              DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci COMMENT ='流程节点表';