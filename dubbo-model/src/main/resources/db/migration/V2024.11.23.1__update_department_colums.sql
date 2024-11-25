alter table sys_department
    add parent_id int default 0 not null after department_name;