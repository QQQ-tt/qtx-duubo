CREATE TABLE `ac_name`
(
    `id`            int                                                          NOT NULL AUTO_INCREMENT,
    `ac_uuid`       varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci          DEFAULT NULL COMMENT '流程uuid',
    `name`          varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci          DEFAULT NULL COMMENT '流程名称',
    `init_type`     bit(1)                                                                DEFAULT b'1' COMMENT '是否逐一初始化',
    `business_mean` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci          DEFAULT NULL COMMENT '业务含义',
    `table_name`    varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci          DEFAULT NULL COMMENT '实际业务表名称',
    `is_history`    bit(1)                                                       NOT NULL DEFAULT b'0' COMMENT '是否为历史记录 0:否 1:是',
    `delete_flag`   bit(1)                                                       NOT NULL DEFAULT b'0' COMMENT '是否删除 0:否 1:是',
    `create_by`     varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '创建人',
    `create_on`     datetime                                                     NOT NULL COMMENT '创建时间',
    `update_by`     varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci          DEFAULT NULL COMMENT '修改人',
    `update_on`     datetime                                                              DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `ac_uuid` (`ac_uuid`) COMMENT '唯一索引'
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci COMMENT ='单一流程名称表';