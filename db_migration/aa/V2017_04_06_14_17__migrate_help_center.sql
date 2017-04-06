BEGIN;

UPDATE help_center SET content='拓天速贷是一家稳健型的互联网金融信息服务平台，其优势有以下几个方面：
1、相比传统投资渠道，收益更高，预期年化收益高达8%-11%；门槛更低，50元即可投资；
2、第三方支付服务，交易过程中的充值、投资、提现都通过联动优势进行，保证了资金流转的透明和安全；
3、提现便捷，提现原则上支持当日到账，工作日16：00前提现当天到账，工作日16:00后提现第二个工作日11:00前到账，节假日则推迟至下一个工作日；
4、为用户提供安心签电子签章服务，其形成的数据电文或电子缔约文件符合中国法律规定，与纸质文件具有同样的法律效力。' WHERE id='100002';

UPDATE help_center SET content='拓天速贷主要产品为房屋抵押借款、车辆抵押借款，50元起投；还款方式主要为：先付收益后还投资本金，按天计息，放款后生息。' WHERE id='100003';

INSERT INTO `aa`.`help_center` (`title`, `content`, `category`, `hot`) VALUES (
'投资项目的募集期限是多久？', '平台所有标的募集期限均为7天，如到期未筹满，该项目视为流标，投资资金将返回到第三方资金托管账户联动优势账户中，届时您可以在账户余额中查看该笔资金', 'INVEST_REPAY', false);


COMMIT;
