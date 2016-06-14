-- update table account 
UPDATE `aa`.`account`
SET membership_point =
CASE
WHEN (SELECT sum(floor(amount / 100))
      FROM invest
      WHERE account.login_name = invest.login_name) > 0
  THEN (SELECT sum(floor(amount / 100))
        FROM invest
        WHERE account.login_name = invest.login_name)
ELSE
  0
END;

-- update user_membership
INSERT INTO `aa`.`user_membership` (login_name, membership_id, expired_time, created_time, type)
  SELECT
    `user`.login_name,
    (
      CASE
      WHEN (SELECT `account`.membership_point
            FROM `aa`.`account`
            WHERE `account`.login_name = `user`.`login_name`)
           >= (SELECT `membership`.experience
               FROM `aa`.`membership`
               WHERE `membership`.level = 1)
           AND (SELECT `account`.membership_point
                FROM `aa`.`account`
                WHERE `account`.login_name = `user`.`login_name`)
               < (SELECT `membership`.experience
                  FROM `aa`.`membership`
                  WHERE `membership`.level = 2)
        THEN
          (SELECT `membership`.id
           FROM `aa`.`membership`
           WHERE `membership`.level = 1)
      WHEN (SELECT `account`.membership_point
            FROM `aa`.`account`
            WHERE `account`.login_name = `user`.`login_name`)
           >= (SELECT `membership`.experience
               FROM `aa`.`membership`
               WHERE `membership`.level = 2)
           AND (SELECT `account`.membership_point
                FROM `aa`.`account`
                WHERE `account`.login_name = `user`.`login_name`)
               < (SELECT `membership`.experience
                  FROM `aa`.`membership`
                  WHERE `membership`.level = 3)
        THEN
          (SELECT `membership`.id
           FROM `aa`.`membership`
           WHERE `membership`.level = 2)
      WHEN (SELECT `account`.membership_point
            FROM `aa`.`account`
            WHERE `account`.login_name = `user`.`login_name`)
           >= (SELECT `membership`.experience
               FROM `aa`.`membership`
               WHERE `membership`.level = 3)
           AND (SELECT `account`.membership_point
                FROM `aa`.`account`
                WHERE `account`.login_name = `user`.`login_name`)
               < (SELECT `membership`.experience
                  FROM `aa`.`membership`
                  WHERE `membership`.level = 4)
        THEN
          (SELECT `membership`.id
           FROM `aa`.`membership`
           WHERE `membership`.level = 3)
      WHEN (SELECT `account`.membership_point
            FROM `aa`.`account`
            WHERE `account`.login_name = `user`.`login_name`)
           >= (SELECT `membership`.experience
               FROM `aa`.`membership`
               WHERE `membership`.level = 4)
           AND (SELECT `account`.membership_point
                FROM `aa`.`account`
                WHERE `account`.login_name = `user`.`login_name`)
               < (SELECT `membership`.experience
                  FROM `aa`.`membership`
                  WHERE `membership`.level = 5)
        THEN
          (SELECT `membership`.id
           FROM `aa`.`membership`
           WHERE `membership`.level = 4)
      WHEN (SELECT `account`.membership_point
            FROM `aa`.`account`
            WHERE `account`.login_name = `user`.`login_name`)
           >= (SELECT `membership`.experience
               FROM `aa`.`membership`
               WHERE `membership`.level = 5)
        THEN
          (SELECT `membership`.id
           FROM `aa`.`membership`
           WHERE `membership`.level = 5)
      ELSE
        (SELECT `membership`.id
         FROM `aa`.`membership`
         WHERE `membership`.level = 0)
      END),
    '9999-12-31 23:59:59',
    now(),
    'UPGRADE'
  FROM `aa`.`user`;

-- update membership_experience_bill
-- insert detail info
INSERT INTO `aa`.`membership_experience_bill` (login_name, experience, created_time, description, total_experience)
  SELECT
    `invest`.login_name,
    floor(`invest`.amount / 100),
    `invest`.trading_time,
    concat(`invest`.login_name, '于', `invest`.invest_time, '投资', `invest`.loan_id, '项目',
           round(`invest`.amount / 100, 2), '元，获得成长值', floor(`invest`.amount / 100), '点'),
    (select ifnull(sum(floor(`temp`.amount / 100)),0) from invest temp where temp.id <= invest.id and invest.login_name= temp.login_name and temp.status='SUCCESS' and temp.transfer_invest_id IS NULL)
  FROM `aa`.`invest`
  WHERE `invest`.transfer_invest_id IS NULL and `invest`.status = 'SUCCESS';

-- insert UPGRADE info
INSERT INTO `aa`.`membership_experience_bill` (login_name, experience, created_time, total_experience, description)
  SELECT
    `user`.login_name,
    0,
    now(),
    0,
    concat(
        '根据', `user`.login_name, '历史投资记录，在会员上线时获得初始会员等级V',
        CASE
        WHEN (SELECT `account`.membership_point
              FROM `aa`.`account`
              WHERE `account`.login_name = `user`.`login_name`)
             >= (SELECT `membership`.experience
                 FROM `aa`.`membership`
                 WHERE `membership`.level = 1)
             AND (SELECT `account`.membership_point
                  FROM `aa`.`account`
                  WHERE `account`.login_name = `user`.`login_name`)
                 < (SELECT `membership`.experience
                    FROM `aa`.`membership`
                    WHERE `membership`.level = 2)
          THEN
            (SELECT 1)
        WHEN (SELECT `account`.membership_point
              FROM `aa`.`account`
              WHERE `account`.login_name = `user`.`login_name`)
             >= (SELECT `membership`.experience
                 FROM `aa`.`membership`
                 WHERE `membership`.level = 2)
             AND (SELECT `account`.membership_point
                  FROM `aa`.`account`
                  WHERE `account`.login_name = `user`.`login_name`)
                 < (SELECT `membership`.experience
                    FROM `aa`.`membership`
                    WHERE `membership`.level = 3)
          THEN
            (SELECT 2)
        WHEN (SELECT `account`.membership_point
              FROM `aa`.`account`
              WHERE `account`.login_name = `user`.`login_name`)
             >= (SELECT `membership`.experience
                 FROM `aa`.`membership`
                 WHERE `membership`.level = 3)
             AND (SELECT `account`.membership_point
                  FROM `aa`.`account`
                  WHERE `account`.login_name = `user`.`login_name`)
                 < (SELECT `membership`.experience
                    FROM `aa`.`membership`
                    WHERE `membership`.level = 4)
          THEN
            (SELECT 3)
        WHEN (SELECT `account`.membership_point
              FROM `aa`.`account`
              WHERE `account`.login_name = `user`.`login_name`)
             >= (SELECT `membership`.experience
                 FROM `aa`.`membership`
                 WHERE `membership`.level = 4)
             AND (SELECT `account`.membership_point
                  FROM `aa`.`account`
                  WHERE `account`.login_name = `user`.`login_name`)
                 < (SELECT `membership`.experience
                    FROM `aa`.`membership`
                    WHERE `membership`.level = 5)
          THEN
            (SELECT 4)
        WHEN (SELECT `account`.membership_point
              FROM `aa`.`account`
              WHERE `account`.login_name = `user`.`login_name`)
             >= (SELECT `membership`.experience
                 FROM `aa`.`membership`
                 WHERE `membership`.level = 5)
          THEN
            (SELECT 5)
        ELSE
          (SELECT 0)
        END
    )
  FROM `aa`.`user`
    LEFT JOIN `aa`.`account` ON `user`.login_name = `account`.login_name;

-- invest invest_fee_rate
UPDATE `aa`.`invest`
SET `invest`.invest_fee_rate = (select loan.invest_fee_rate from loan where loan.id = invest.id);