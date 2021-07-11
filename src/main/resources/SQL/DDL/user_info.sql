CREATE TABLE user_info (
	id INTEGER UNSIGNED auto_increment NOT NULL COMMENT '使用者索引',
	user_name varchar(30) NOT NULL COMMENT '使用者名稱',
	user_password varchar(30) NOT NULL COMMENT '使用者密碼',
	is_active TINYINT DEFAULT 1 NOT NULL COMMENT '使用者停啟用，1: 啟用，0: 停用',
	is_delete TINYINT DEFAULT 0 NOT NULL COMMENT '使用者刪除標記，1: 已刪除，0: 未刪除',
	last_login_datetime DATETIME NULL COMMENT '使用者最後登入時間',
	create_user_id INTEGER UNSIGNED NOT NULL COMMENT '建立者索引',
	create_datetime DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL COMMENT '建立時間',
	CONSTRAINT USERINFO_PK PRIMARY KEY (id)
)
ENGINE=InnoDB
DEFAULT CHARSET=utf8mb4
COLLATE=utf8mb4_unicode_ci;
