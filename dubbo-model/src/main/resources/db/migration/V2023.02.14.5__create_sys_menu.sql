CREATE TABLE `sys_menu`
(
    `id`          int                                                           NOT NULL AUTO_INCREMENT,
    `name`        varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci  NOT NULL COMMENT '菜单名称',
    `code`        varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci  NOT NULL COMMENT '菜单编号',
    `path`        varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '菜单地址',
    `parent_id`   int                                                                    DEFAULT '0' COMMENT '父id',
    `is_menu`     bit(1)                                                                 DEFAULT NULL COMMENT '是否为菜单',
    `delete_flag` bit(1)                                                        NOT NULL DEFAULT b'0' COMMENT '是否删除 0:否 1:是',
    `create_by`   varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci  NOT NULL COMMENT '创建人',
    `create_on`   datetime                                                      NOT NULL COMMENT '创建时间',
    `update_by`   varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci           DEFAULT NULL COMMENT '修改人',
    `update_on`   datetime                                                               DEFAULT NULL COMMENT '修改时间',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci COMMENT ='菜单信息表';