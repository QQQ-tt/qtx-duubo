CREATE TABLE `sys_user_info`
(
    `id`            int                                                          NOT NULL AUTO_INCREMENT,
    `user_card`     varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '用户账户',
    `department_id` int                                                          NULL COMMENT '科室id',
    `delete_flag`   bit(1)                                                       NOT NULL DEFAULT b'0' COMMENT '是否删除 0:否 1:是',
    `create_by`     varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '创建人',
    `create_on`     datetime(0)                                                  NOT NULL COMMENT '创建时间',
    `update_by`     varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL     DEFAULT NULL COMMENT '修改人',
    `update_on`     datetime(0)                                                  NULL     DEFAULT NULL COMMENT '修改时间',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci COMMENT ='用户信息扩展表';