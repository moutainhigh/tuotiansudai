ALTER TABLE `loan_application`
  ADD COLUMN (
  identity_number      varchar(18)           DEFAULT NULL          COMMENT '身份证',
  address              varchar(32)           DEFAULT NULL          COMMENT '地址',
  age                  mediumint(8) unsigned DEFAULT '0',
  is_married           TINYINT(1)            NOT NULL              COMMENT '是否结婚',
  have_credit_report   TINYINT(1)            NOT NULL              COMMENT '是否提供征信报告',
  work_position        varchar(20)           DEFAULT NULL          COMMENT '工作职位',
  sesame_credit        varchar(11)                                 COMMENT '芝麻信用积分',
  home_income          int(11)      unsigned NOT NULL              COMMENT '家庭年收入，单位(万)',
  loan_usage           varchar(200)          NOT NULL              COMMENT '借款用途',
  else_pledge          varchar(200)          DEFAULT NULL          COMMENT '其他资产',
  sex                  varchar(10)           DEFAULT NULL
  );

ALTER TABLE `loan_application`
       MODIFY COLUMN region varchar(15)  DEFAULT NULL ;

UPDATE  loan_application  set address = (case  WHEN region='BEI_JING' THEN  '北京' WHEN region='SHI_JIA_ZHUANG' THEN '石家庄' WHEN region='CHENG_DE' THEN '承德' WHEN region='JI_NING' THEN '济宁' ELSE '其他' END );