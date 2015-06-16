/*
-- Query: select * from loan where id = '20150317000012'
LIMIT 0, 1000

-- case1:
		第1个月 完成
		第2个月 逾期
		第3个月 还款中
		第4个月 还款中
*/
INSERT INTO `loan` (`id`,`actual_rate`,`business_type`,`cancel_time`,`cardinal_number`,`commit_time`,`company_description`,`company_name`,`companyno`,`complete_time`,`contract_type`,`custom_picture`,`deadline`,`deposit`,`description`,`expect_time`,`fee_on_repay`,`fund_description`,`give_money_time`,`guarantee_company_description`,`guarantee_company_name`,`guarantee_info_description`,`has_pawn`,`interest_begin_time`,`investor_fee_rate`,`loan_gurantee_fee`,`loan_instruction`,`loan_money`,`loan_purpose`,`location`,`min_invest_money`,`money`,`name`,`overdue_info`,`pawn`,`pawn_name`,`policy_description`,`rate`,`repay_day`,`repay_period`,`repay_type`,`risk_description`,`risk_instruction`,`risk_level`,`seq_num`,`status`,`verified`,`verify_message`,`verify_time`,`video_id`,`type`,`user_id`,`verify_user_id`,`max_invest_money`,`order_code`,`transfer_type`,`rate_iboi`,`loan_activity_type`,`invest_password`,`jk_rate`,`hd_rate`,`agent`) VALUES ('20150317000012',NULL,'个人借款',NULL,1,'2015-03-17 11:29:02','',NULL,NULL,'2015-03-18 16:33:06','quartet_contract','',4,0,'','2015-03-21 00:00:00',0,NULL,'2015-03-18 11:35:33','','3',NULL,NULL,'2015-03-18 11:35:33',0,0,NULL,200000,'',NULL,50,200000,'首页标的',NULL,NULL,NULL,NULL,0.12,NULL,NULL,NULL,NULL,NULL,'AAA',0,'complete','通过','','2015-03-17 11:29:10',NULL,'loan_type_2','tuotian','admin',99999999,NULL,'transfer_type',NULL,'pt','0',0.12,0,NULL);

INSERT INTO `loan_repay` (`id`,`corpus`,`default_interest`,`interest`,`length`,`period`,`repay_day`,`status`,`time`,`loan_id`,`fee`,`remark`,`repay_way`) VALUES ('201503170000120001',0,0,2038.36,1,1,'2015-04-17 11:35:33','complete','2015-03-18 16:33:03','20150317000012',0,NULL,NULL);
INSERT INTO `loan_repay` (`id`,`corpus`,`default_interest`,`interest`,`length`,`period`,`repay_day`,`status`,`time`,`loan_id`,`fee`,`remark`,`repay_way`) VALUES ('201503170000120002',0,47.34,1972.6,1,2,'2015-05-17 11:35:33','overdue','2015-05-27 16:33:03','20150317000012',0,NULL,NULL);
INSERT INTO `loan_repay` (`id`,`corpus`,`default_interest`,`interest`,`length`,`period`,`repay_day`,`status`,`time`,`loan_id`,`fee`,`remark`,`repay_way`) VALUES ('201503170000120003',0,0,2038.36,1,3,'2015-06-17 11:35:33','repaying','2015-06-17 11:35:33','20150317000012',0,NULL,NULL);
INSERT INTO `loan_repay` (`id`,`corpus`,`default_interest`,`interest`,`length`,`period`,`repay_day`,`status`,`time`,`loan_id`,`fee`,`remark`,`repay_way`) VALUES ('201503170000120004',200000,0,1972.6,1,4,'2015-07-17 11:35:33','repaying','2015-07-17 11:35:33','20150317000012',0,NULL,NULL);

