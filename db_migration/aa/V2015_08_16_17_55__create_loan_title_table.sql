CREATE TABLE ${aa}.`loan_title` (
  `id`                    varchar (32)        NOT NULL ,
  `loan_id`               varchar(32)         NOT NULL,/***借款标的***/
  `title_id`              varchar(32)         NOT NULL,/***申请材料标题***/
  `apply_metarial_url`    varchar(32)         NOT NULL,/***申请材料存放路径***/
  PRIMARY KEY (`id`),
  CONSTRAINT `FK_LOAN_TITLE_LOAN_ID_REF_LOAN_ID` FOREIGN KEY (`loan_id`) REFERENCES ${aa}.`loan` (`id`),
  CONSTRAINT `FK_LOAN_TITLE_TITLE_ID_REF_LOAN_ID` FOREIGN KEY (`title_id`) REFERENCES ${aa}.`title` (`id`)
)
ENGINE=InnoDB
DEFAULT CHARSET=utf8;