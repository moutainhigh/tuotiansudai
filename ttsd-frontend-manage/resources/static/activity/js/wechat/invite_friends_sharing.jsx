require('activityStyle/wechat/invite_friends_sharing.scss');
let commonFun = require('publicJs/commonFun');
let sourceKind = globalFun.parseURL(location.href);
commonFun.calculationFun(document,window);
let Cycle = commonFun.Cycle;
let timer;

setTimeout(function () {
    let fontSize = $('html').css('fontSize');
    let opts2 = {
        id: "cycle",
        width: 24 * parseInt(fontSize),
        height: 24 * parseInt(fontSize),
        percent: "1",
        border: parseInt(fontSize) / 2,
        bgColor: "rgba(187,185,176,.7)",
        barColor: "#e7051c",
        fillColor: "rgba(251,233,197,.47)"
    };
    function getPercentLight() {
        let currentPer = $("#cycle").data("mycash");
        let percentArr = $("#cycle").data("cashchain").split(",");
        if (parseFloat(currentPer) < parseFloat(percentArr[0])) return;
        for (let i = 0;i < percentArr.length;i++) {
            let item = percentArr[i];
            if (parseFloat(currentPer) < parseFloat(item)) {
                let index = percentArr.indexOf(item) - 1;
                opts2.percent = 100 / 6 * index;
                for(let j = 1; j < index + 2;j++) {
                    let lightCircle = 'percent' + j;
                    $('.' + lightCircle).addClass('light');
                }
                return;
            }
            else {
                if (parseFloat(currentPer) >= parseFloat(percentArr[percentArr.length - 1])) {
                    opts2.percent = '100';
                    for(let j = 1; j < 7;j++) {
                        let lightCircle = 'percent' + j;
                        $('.' + lightCircle).addClass('light');
                    }
                }
            }
        }
    }

    getPercentLight();

    new Cycle(opts2).init();
    $('canvas').css('width',12 * parseInt(fontSize) + 'px');
    $('canvas').css('height',12 * parseInt(fontSize) + 'px');

},0);

$('.invite_friends_btn').on('click',() => {
    if(is_wechat()) {
        $('.wechat_share_tip').show();
    }
    else {
        alert('请在微信中分享');
    }
});

$('.wechat_share_tip').on('click',function () {
   $(this).hide();
});

$('.rules').on('click',() => {
    $('.flex_rules').show();
    $('body').css('overflow','hidden');
});

$('.close_rules').on('click',() => {
    $('.flex_rules').hide();
    $('body').css('overflow','auto');
});

function countTimePop(str) {
    $('.pic_wrapper').show();
    $('.time_over').hide();
    let end = new Date(str).getTime();
    let now = new Date().getTime();
    let leftTime = (end-now)/1000;
    timerCount();
    timer = setInterval(() => {
        timerCount();
    },1000);
    function timerCount() {
        let h,m,s;
        if (leftTime > 0) {
            if (leftTime <= 1) {
                clearInterval(timer);
                $('.pic_wrapper').hide();
                $('.time_over').show();
                return;
            }
            h = Math.floor(leftTime/60/60%24);
            m = Math.floor(leftTime/60%60);
            s = Math.floor(leftTime%60);
            h = h < '10' ? '0' + h : h + '';
            m = m < '10' ? '0' + m : m + '';
            s = s < '10' ? '0' + s : s + '';

            $('.hour1').html(h.charAt(0));
            $('.hour2').html(h.charAt(1));
            $('.minutes1').html(m.charAt(0));
            $('.minutes2').html(m.charAt(1));
            $('.seconds1').html(s.charAt(0));
            $('.seconds2').html(s.charAt(1));
            --leftTime;
        }
        else {
            clearInterval(timer);
            $('.pic_wrapper').hide();
            $('.time_over').show();
        }
    }
}
countTimePop($("#countDown").data("countdown").replace(/-/g,'/'));


function is_wechat(){
    return navigator.userAgent.toLowerCase().match(/MicroMessenger/i)=="micromessenger";
}











