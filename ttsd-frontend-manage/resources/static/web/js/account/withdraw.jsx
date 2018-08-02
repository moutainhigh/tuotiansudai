require('webJs/plugins/autoNumeric');
require('webStyle/account/withdraw.scss');

let $withdraw = $('.withdraw'),
    amountInputElement = $(".amount-display", $withdraw),
    submitElement = $('.withdraw-submit', $withdraw),
    formElement = $('form', $withdraw),
    errorElement = $('.error', $withdraw),
    actualAmountElement = $('.actual-amount', $withdraw),
    withdrawFeeElement = $('.withdraw-fee', $withdraw);
amountInputElement.autoNumeric("init");
let isFudianBank = withdrawFeeElement.data('bankcode');
if(isFudianBank === 466){
    withdrawFeeElement.html('1.00');
    $('#cash').html('1.00')
}else {
    $('#cash').html('1.50')
        moneyCheck()
}


var u = navigator.userAgent;
var isInWeChat = /(micromessenger|webbrowser)/.test(u.toLocaleLowerCase());
var isIos = /(iPhone|iPad|iPod|iOS)/i.test(u);
if (isInWeChat && isIos) {
    $('#withdraw').removeAttr('target');
}

amountInputElement.keyup(function () {
    let amount = parseFloat(amountInputElement.autoNumeric("get")),
        withdrawFee = parseFloat(withdrawFeeElement.html());
    moneyCheck()
    if(amountInputElement.val()==''){
        if(isFudianBank === 466){
            withdrawFeeElement.html('1.00');
            $('#cash').html('1.00')
        }else {
            $('#cash').html('1.50')
        }
    }
    if (isNaN(amount) || amount <= withdrawFee) {
        submitElement.prop('disabled', true);
        errorElement.show();
        actualAmountElement.html('0.00');
    } else {
        submitElement.prop('disabled', false);
        errorElement.hide();
        actualAmountElement.html((amount - withdrawFee).toFixed(2));
    }
});

submitElement.click(function () {
    let amount = parseFloat(amountInputElement.autoNumeric("get"));
    if (isNaN(amount) || submitElement.hasClass('inactive')) {
        return false;
    }
    $(".withdraw form input[name='amount']").val(amount);
    formElement.submit();

    // if (hasAccess) {
    //     formElement.submit();
    //     layer.open({
    //         type: 1,
    //         title: '登录到联动优势支付平台提现',
    //         area: ['560px', '270px'],
    //         shadeClose: true,
    //         content: $('#popWithdraw')
    //     });
    // } else {
    //     layer.open({
    //         type: 1,
    //         title: '提现失败',
    //         area: ['330px', '185px'],
    //         shadeClose: true,
    //         content: $('#popWithdrawFail')
    //     });
    //     return false;
    // }
});
function moneyCheck() {
    let amount = parseFloat(amountInputElement.autoNumeric("get"));

    if(amount<=50000){
        withdrawFeeElement.html('1.50');
    }else {
        withdrawFeeElement.html('5.00');
    }
    if(amountInputElement.val()==''){

        withdrawFeeElement.html('1.50')
    }
}
