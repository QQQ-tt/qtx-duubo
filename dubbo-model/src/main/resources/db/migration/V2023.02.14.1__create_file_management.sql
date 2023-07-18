CREATE TABLE `file_management`
(
    `id`            int                                                          NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `other_info`    varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci          DEFAULT NULL COMMENT
        '与其他表信息关联字段',
    `file_uuid`     varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci          DEFAULT NULL COMMENT '文件uuid',
    `file_name_old` varchar(255)                                                          DEFAULT NULL COMMENT '文件原始名称',
    `file_name_new` varchar(255)                                                          DEFAULT NULL COMMENT '文件真实名称',
    `bucket_name`   varchar(50)                                                           DEFAULT NULL COMMENT 'bucket名称',
    `file_object`   varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci         DEFAULT NULL COMMENT
        'bucket路径',
    `file_version`  varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci          DEFAULT 'NA' COMMENT '版本号',
    `is_history`    bit(1)                                                                DEFAULT b'0' COMMENT '是否为历史版本',
    `file_type`     varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci         DEFAULT NULL COMMENT
        '文件所属业务',
    `delete_flag`   bit(1)                                                       NOT NULL DEFAULT b'0' COMMENT '是否删除 0:否 1:是',
    `create_by`     varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '创建人',
    `create_on`     datetime                                                     NOT NULL COMMENT '创建时间',
    `update_by`     varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci          DEFAULT NULL COMMENT '修改人',
    `update_on`     datetime                                                              DEFAULT NULL COMMENT '修改时间',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci COMMENT ='文件管理表';