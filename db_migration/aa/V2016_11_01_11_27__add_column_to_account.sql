ALTER TABLE `user` ADD COLUMN `user_name` VARCHAR(50) DEFAULT NULL
AFTER `mobile`;

ALTER TABLE `user` ADD COLUMN `identity_number` VARCHAR(18) DEFAULT NULL
AFTER `user_name`;

ALTER TABLE `user` ADD INDEX INDEX_USER_IDENTITY_NUMBER(`identity_number`);