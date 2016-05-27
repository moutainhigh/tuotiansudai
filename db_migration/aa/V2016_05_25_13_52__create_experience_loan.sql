BEGIN;

INSERT INTO loan (`id`,
                  `name`,
                  `loaner_login_name`,
                  `loaner_user_name`,
                  `loaner_identity_number`,
                  `type`,
                  `periods`,
                  `duration`,
                  `description_text`,
                  `description_html`,
                  `loan_amount`,
                  `invest_fee_rate`,
                  `min_invest_amount`,
                  `max_invest_amount`,
                  `invest_increasing_amount`,
                  `activity_type`,
                  `product_type`,
                  `base_rate`,
                  `contract_id`,
                  `fundraising_start_time`,
                  `fundraising_end_time`,
                  `verify_login_name`,
                  `verify_time`,
                  `status`,
                  `creator_login_name`,
                  `created_time`,
                  `update_time`)
  SELECT
    1,
    '新手体验项目',
    '',
    '',
    '',
    'INVEST_INTEREST_LUMP_SUM_REPAY',
    1,
    3,
    '',
    '',
    58880000,
    0.1,
    0,
    0,
    0,
    'NEWBIE',
    'EXPERIENCE',
    0.15,
    0,
    '2015-07-01 00:00:00',
    '9999-12-31 00:00:00',
    'sidneygao',
    now(),
    'RAISING',
    'sidneygao',
    now(),
    now()
  FROM dual
  WHERE EXISTS(SELECT *
               FROM `user`
               WHERE login_name = 'sidneygao');

INSERT INTO coupon (`amount`,
                    `rate`,
                    `birthday_benefit`,
                    `multiple`,
                    `start_time`,
                    `end_time`,
                    `deadline`,
                    `used_count`,
                    `total_count`,
                    `issued_count`,
                    `active`,
                    `created_by`,
                    `created_time`,
                    `activated_by`,
                    `activated_time`,
                    `updated_by`,
                    `updated_time`,
                    `invest_lower_limit`,
                    `product_types`,
                    `coupon_type`,
                    `user_group`)
  SELECT
    588800,
    0,
    0,
    0,
    '2015-07-01',
    '9999-12-31',
    30,
    0,
    1000000,
    0,
    TRUE,
    'sidneygao',
    now(),
    'sidneygao',
    now(),
    'sidneygao',
    now(),
    0,
    'EXPERIENCE',
    'NEWBIE_COUPON',
    'NEW_REGISTERED_USER'
  FROM dual
  WHERE EXISTS(SELECT *
               FROM `user`
               WHERE login_name = 'sidneygao');

INSERT INTO coupon (`amount`,
                    `rate`,
                    `birthday_benefit`,
                    `multiple`,
                    `start_time`,
                    `end_time`,
                    `deadline`,
                    `used_count`,
                    `total_count`,
                    `issued_count`,
                    `active`,
                    `created_by`,
                    `created_time`,
                    `activated_by`,
                    `activated_time`,
                    `updated_by`,
                    `updated_time`,
                    `invest_lower_limit`,
                    `product_types`,
                    `coupon_type`,
                    `user_group`)
  SELECT
    653,
    0,
    0,
    0,
    '2015-07-01',
    '9999-12-31',
    30,
    0,
    1000000,
    0,
    TRUE,
    'sidneygao',
    now(),
    'sidneygao',
    now(),
    'sidneygao',
    now(),
    10000,
    '_30,_90,_180,_360',
    'RED_ENVELOPE',
    'EXPERIENCE_REPAY_SUCCESS'
  FROM dual
  WHERE EXISTS(SELECT *
               FROM `user`
               WHERE login_name = 'sidneygao');

COMMIT;