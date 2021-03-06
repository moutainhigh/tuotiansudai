CREATE TABLE `announce` (
  `id`           BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  `title`        VARCHAR(100)    NOT NULL,
  `content`      TEXT,
  `content_text` TEXT,
  `show_on_home` BOOLEAN                  DEFAULT TRUE,
  `created_time` DATETIME        NOT NULL,
  `updated_time` DATETIME        NOT NULL,
  PRIMARY KEY (`id`)
)
  ENGINE = InnoDB
  AUTO_INCREMENT = 100001
  DEFAULT CHARSET = utf8;