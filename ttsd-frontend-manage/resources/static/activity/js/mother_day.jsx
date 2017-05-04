require('publicJs/login_tip');
let drawCircle = require('activityJsModule/gift_circle_draw');
require('activityStyle/mother_day.scss');
let tpl = require('art-template/dist/template');
let commonFun = require('publicJs/commonFun');


let $motherDayContainer = $('#motherDayContainer'),
    tipGroupObj = {};
var $pointerBtn = $('.pointer-img',$motherDayContainer);
var $oneThousandPoints=$('.gift-item',$motherDayContainer);
var pointAllList='/activity/wechat/lottery/getLotteryList',  //中奖记录接口地址
    pointUserList='/activity/wechat/lottery/getMyLotteryList',   //我的奖品接口地址
    drawURL='/activity/wechat/lottery/draw';    //抽奖的接口链接

var oneData='';

$motherDayContainer.find('.tip-list-frame .tip-list').each(function (key, option) {
    let kind = $(option).attr('data-return');
    tipGroupObj[kind] = option;
});



//pointAllList:中奖记录接口地址
//pointUserList:我的奖品接口地址
//drawURL:抽奖的接口链接
//oneData:接口参数
//$oneThousandPoints:抽奖模版dom
var drawCircleOne=new drawCircle(pointAllList,pointUserList,drawURL,oneData,$oneThousandPoints);

//渲染中奖记录
drawCircleOne.GiftRecord(10);

//渲染我的奖品
drawCircleOne.MyGift(10);

//点击切换按钮
drawCircleOne.PrizeSwitch();

//开始抽奖
$pointerBtn.on('click', function(event) {
    drawCircleOne.rotateFn(45*4-20,tipGroupObj['concrete']);
//     drawCircleOne.beginLuckDraw(function(data) {
//         //抽奖接口成功后奖品指向位置
//         if (data.data.returnCode == 0) {
//             var angleNum=0;
//             switch (data.data.wechatLotteryPrize) {
//                 case 'WECHAT_LOTTERY_BEDCLOTHES': //欧式奢华贡缎床品四件套 
//                     angleNum=72*5-20;
//                     $(tipGroupObj['concrete']).find('.prizeValue').text('一等奖')
//                     .parent().siblings('.des-text').text('欧式奢华贡缎床品四件套')
//                     .siblings('.reward-text').addClass('gift-one');
//                     break;
//                 case 'WECHAT_LOTTERY_BAG': //时尚百搭真皮子母包
//                     angleNum=72*4-20;
//                     $(tipGroupObj['concrete']).find('.prizeValue').text('二等奖')
//                     .parent().siblings('.des-text').text('时尚百搭真皮子母包')
//                     .siblings('.reward-text').addClass('gift-two');
//                     break;
//                 case 'WECHAT_LOTTERY_HEADGEAR':  //简约吊坠百搭锁骨链
//                     angleNum=72*3-20;
//                      $(tipGroupObj['concrete']).find('.prizeValue').text('三等奖')
//                      .parent().siblings('.des-text').text('简约吊坠百搭锁骨链')
//                     .siblings('.reward-text').addClass('gift-three');
//                     break;
//                 case 'WECHAT_LOTTERY_TOWEL':  //精品定制毛巾礼盒
//                     angleNum=72*2-20;
//                      $(tipGroupObj['concrete']).find('.prizeValue').text('四等奖')
//                      .parent().siblings('.des-text').text('精品定制毛巾礼盒')
//                     .siblings('.reward-text').addClass('gift-four');
//                     break;
//                 case 'WECHAT_LOTTERY_RED_ENVELOP_20': //20元红包
//                     angleNum=72*1-20;
//                      $(tipGroupObj['concrete']).find('.prizeValue').text('五等奖')
//                      .parent().siblings('.des-text').text('20元红包')
//                     .siblings('.reward-text').addClass('gift-five');
//                     break;
//             }
//             drawCircleOne.rotateFn(angleNum,tipGroupObj['concrete']);

//         } else if(data.data.returnCode == 1) {
//             //抽奖次数不足
//             drawCircleOne.tipWindowPop(tipGroupObj['nochance']);
//         }
//         else if (data.data.returnCode == 2) {
//             //未登录
//             layer.open({
			// 	type: 1,
			// 	title: false,
			// 	closeBtn: 0,
			// 	area: ['auto', 'auto'],
			// 	content: $('#loginTip')
			// });

//         } else if(data.data.returnCode == 3){
//             //不在活动时间范围内！
//             drawCircleOne.tipWindowPop(tipGroupObj['expired']);

//         } else if(data.data.returnCode == 4){
//             //实名认证
//             drawCircleOne.tipWindowPop(tipGroupObj['authentication']);
//         }
//     });
});


    