CREATE TABLE `sys_department`
(
    `id`              int                                                           NOT NULL AUTO_INCREMENT,
    `department_code` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci  NOT NULL COMMENT '科室编号',
    `department_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '科室名称',
    `delete_flag`     bit(1)                                                        NOT NULL DEFAULT b'0' COMMENT '是否删除 0:否 1:是',
    `create_by`       varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci  NOT NULL COMMENT '创建人',
    `create_on`       datetime                                                      NOT NULL COMMENT '创建时间',
    `update_by`       varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci           DEFAULT NULL COMMENT '修改人',
    `update_on`       datetime                                                               DEFAULT NULL COMMENT '修改时间',
    `remark`          varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci          DEFAULT NULL COMMENT '备注',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci COMMENT ='科室管理表';