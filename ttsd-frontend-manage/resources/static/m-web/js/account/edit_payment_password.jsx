require('mWebStyle/account/edit_payment_password.scss');

let editPasswordForm = globalFun.$('#editPasswordForm');
let resetPassword = globalFun.$('#resetPassword');
let commonFun= require('publicJs/commonFun');

commonFun.calculationFun(document, window);

// 修改支付密码
if(editPasswordForm) {
    let $input = $(editPasswordForm).find('input:visible');

    Array.prototype.forEach.call($input,function(el) {

        globalFun.addEventHandler(el,'keyup',function() {

            checkFormInput();
        });
    });

    function checkFormInput() {
        let $submitBtn = $('.btn-wap-normal',$(editPasswordForm));
        let oldPassword = editPasswordForm.oldPassword.value,
            newPassword = editPasswordForm.newPassword.value;
        if(_.isEmpty(oldPassword) || _.isEmpty(newPassword)) {
            $submitBtn.prop('disabled',true);
        } else {
            $submitBtn.prop('disabled',false);
        }
    }

    editPasswordForm.onsubmit = function(event) {
        event.preventDefault();

        editPasswordForm.submit();
    }
}

//重置支付密码
if(resetPassword) {

    //页面初始化的时候光标移到第一个 ，如果输入一个，光标接着移到下一个输入框
    let $inputBox = $('.input-box',$(resetPassword));
    let $inputEl = $inputBox.find('input');
    let $sendShortMsg = $('#sendShortMsg');
    $inputEl.eq(0).focus();
    $inputEl.on('keyup',function(event) {
        let target = event.target;
        if(target.value.length==1) {
            $(target).next() && $(target).next().focus();
        }
    });

    $sendShortMsg.on('click',function(event) {
        event.preventDefault();
        let paramObj = $(resetPassword).serializeArray();
        let lastCardNum = _.pluck(paramObj, 'value').join('');
        let data = { identityNumber: lastCardNum };
        if(lastCardNum.length==4) {
            //window.location.href = 'sms:10690569687?body=CSMM#'+lastCardNum
            commonFun.useAjax({
                url:"/m/personal-info/reset-umpay-password",
                type:'POST',
                data:data
            },function(response) {
                let data = response.data;
                if (data.status) {
                    location.href = '/m/';
                } else {
                    layer.msg('身份证号输入错误');
                }
            });
        } else {
            layer.msg('请输入身份证后四位');
        }
    })

}

// 点击返回btn
$('.go-back-container').on('click',() => {
    history.go(-1);
});