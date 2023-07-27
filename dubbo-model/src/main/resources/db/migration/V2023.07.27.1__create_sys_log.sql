CREATE TABLE `sys_log`
(
    `id`          int                                                          NOT NULL AUTO_INCREMENT,
    `user_code`   varchar(50)                                                  NULL COMMENT '用户信息',
    `method`      varchar(100)                                                 NULL COMMENT '方法类型',
    `path`        varchar(100)                                                 NULL COMMENT '路径',
    `json`        varchar(255)                                                 NULL COMMENT '参数1',
    `param`       varchar(255)                                                 NULL COMMENT '参数2',
    `delete_flag` bit(1)                                                       NOT NULL DEFAULT b'0' COMMENT '是否删除 0:否 1:是',
    `create_by`   varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '创建人',
    `create_on`   datetime(0)                                                  NOT NULL COMMENT '创建时间',
    `update_by`   varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL     DEFAULT NULL COMMENT '修改人',
    `update_on`   datetime(0)                                                  NULL     DEFAULT NULL COMMENT '修改时间',
    PRIMARY KEY (`id`)
) COMMENT = '系统日志';