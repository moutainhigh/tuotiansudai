require(['underscore', 'jquery', 'layerWrapper','placeholder', 'jquery.validate', 'jquery.validate.extension', 'jquery.form', 'jquery.ajax.extension','commonFun'], function (_, $, layer) {
    var registerUserForm = $(".register-user-form"),
        fetchCaptchaElement = $('.fetch-captcha', registerUserForm),
        showAgreement = $('.show-agreement', registerUserForm),
        $agreement = $('#agreementInput');
    var $imgCaptchaDialog = $('.image-captcha-dialog'),
        imageCaptchaForm = $('.image-captcha-form', $imgCaptchaDialog),
        imageCaptchaElement = $('.image-captcha', $imgCaptchaDialog),
        imageCaptchaTextElement = $('.image-captcha-text', $imgCaptchaDialog),
        imageCaptchaSubmitElement = $('.image-captcha-confirm', $imgCaptchaDialog),
        $referrerOpen=$('.referrer-open',registerUserForm),
        $referrer=$('input.referrer', registerUserForm),
        $checkbox=$('label.check-label',registerUserForm),
        $registerSubmit=$('input[type="submit"]',registerUserForm),
        passedNumber= 0,
        countTimer;
    var $frontInput=registerUserForm.find('input:lt(4)');
    $('input[type="text"],input[type="password"]',registerUserForm).placeholder();

    $('input.login-name,input.mobile',registerUserForm).on('focusout',function(option) {
        fetchCaptchaElement.prop('disabled', true);
    });

    $checkbox.on('click',function(event) {
        if(event.target.tagName.toUpperCase()=='A') {
            return;
        }
        var $this=$(this),
            $agreeLast=$this.parents('.agree-last'),
            $cIcon=$agreeLast.find('i');
        var checked=$agreement.prop('checked');
        $cIcon[0].className=checked?'sprite-register-no-checked':'sprite-register-yes-checked';

    });
    $referrerOpen.on('click',function() {
        var $this=$(this),
            checkOption=false,
            iconArrow=$this.find('i');
        $this.next('li').toggleClass('hide');
        checkOption=$this.next('li').hasClass('hide');
        iconArrow[0].className=checkOption?'sprite-register-arrow-bottom':'sprite-register-arrow-right';
    });

    showAgreement.click(function () {
        layer.open({
            type: 1,
            title: '拓天速贷服务协议',
            area: ['950px', '600px'],
            shadeClose: true,
            move: false,
            scrollbar: true,
            skin:'register-skin',
            content: $('#agreementBox'),
            success: function (layero, index) {
            }
        });
    });
    fetchCaptchaElement.on('click', function () {
        layer.open({
            type: 1,
            title: '手机验证',
            area: ['300px', '190px'],
            shadeClose: true,
            content: $('.image-captcha-dialog'),
            success: function (layero, index) {
                $('.image-captcha-form label').remove();
                imageCaptchaTextElement.val('');
                refreshCaptcha();
            }
        });
        return false;
    });

    var refreshCaptcha = function () {
        imageCaptchaElement.attr('src', '/register/user/image-captcha?' + new Date().getTime().toString());
    };

    imageCaptchaElement.click(function () {
        refreshCaptcha();
    });

    imageCaptchaForm.validate({
        focusInvalid: false,
        onfocusout: function (element) {
            if (!this.checkable(element) && !this.optional(element)) {
                this.element(element);
            }

        },
        submitHandler: function (form) {
            var self = this;
            $(form).ajaxSubmit({
                data: {mobile: $('.mobile').val()},
                dataType: 'json',
                beforeSubmit: function (arr, $form, options) {
                    imageCaptchaSubmitElement.addClass("loading");

                },
                success: function (response) {
                    var data = response.data;
                    if (data.status && !data.isRestricted) {
                        layer.closeAll();
                        var seconds = 60
                         countTimer = setInterval(function () {
                            fetchCaptchaElement.html(seconds + '秒后重新发送').addClass('disabledButton').prop('disabled',true);
                            if (seconds == 0) {
                                clearInterval(countTimer);
                                fetchCaptchaElement.html('重新发送').removeClass('disabledButton').prop('disabled',false);
                            }
                            seconds--;
                        }, 1000);
                        return;
                    }

                    if (!data.status && data.isRestricted) {
                        self.showErrors({imageCaptcha: '短信发送频繁，请稍后再试'});
                    }

                    if (!data.status && !data.isRestricted) {
                        self.showErrors({imageCaptcha: '图形验证码不正确'});
                    }
                    self.invalid['imageCaptcha'] = true;
                    refreshCaptcha();
                },
                error: function () {
                    self.invalid['imageCaptcha'] = true;
                    self.showErrors({imageCaptcha: '图形验证码不正确'});
                    refreshCaptcha();
                },
                complete: function () {
                    imageCaptchaSubmitElement.removeClass("loading");

                }
            });
        },
        rules: {
            imageCaptcha: {
                required: true,
                regex: /^[a-zA-Z0-9]{5}$/
            }
        },
        messages: {
            imageCaptcha: {
                required: "请输入图形验证码",
                regex: "图形验证码位数不对"
            }
        }
    });

    registerUserForm.validate({
        ignore:'.referrer',
        rules: {
            loginName: {
                required: true,
                regex: /(?!^\d+$)^\w{5,25}$/,
                isExist: "/register/user/login-name/{0}/is-exist"
            },
            mobile: {
                required: true,
                digits: true,
                minlength: 11,
                maxlength: 11,
                isExist: "/register/user/mobile/{0}/is-exist"
            },
            password: {
                required: true,
                regex: /^(?=.*[^\d])(.{6,20})$/
            },
            captcha: {
                required: true,
                digits: true,
                maxlength: 6,
                minlength: 6,
                captchaVerify: {
                    param: function () {
                        var mobile = $('input[name="mobile"]').val();
                        return "/register/user/mobile/" + mobile + "/captcha/{0}/verify"
                    }
                }
            },
            agreement: {
                required: true
            }
        },
        messages: {
            loginName: {
                required: "请输入用户名",
                regex: '5位至25位数字与字母下划线组合，不能全部数字',
                isExist: '用户名已存在'
            },
            mobile: {
                required: '请输入手机号',
                digits: '必须是数字',
                minlength: '手机格式不正确',
                maxlength: '手机格式不正确',
                isExist: '手机号已存在'
            },
            password: {
                required: "请输入密码",
                regex: '6位至20位，不能全是数字'
            },
            captcha: {
                required: '请输入验证码',
                digits: '验证码格式不正确',
                maxlength: '验证码格式不正确',
                minlength: '验证码格式不正确',
                captchaVerify: '验证码不正确'
            },
            agreement: {
                required: "请同意服务协议"
            }
        },
        success: function (error, element) {
            var loginName = $('input.login-name', registerUserForm),
                mobile = $('input.mobile', registerUserForm);

            if(!fetchCaptchaElement.hasClass('disabledButton')) {
                if (element.name === 'mobile' && loginName.hasClass('valid')) {
                    fetchCaptchaElement.prop('disabled', false);
                    mobile.attr('preValue',mobile.val());
                }
                if (element.name === 'loginName' && mobile.hasClass('valid')) {
                    fetchCaptchaElement.prop('disabled', false);
                }
            }
        }
    });

    function checkValidNum(event) {
        var passedNumber= 0,
            frontInputValid=true;

        $frontInput.each(function(key,option) {
            if(!$(option).hasClass('valid')) {
                frontInputValid=false;
                return;
            }
        });

        var referrerVal=$referrer.val(),
            referrerValid=$referrer.hasClass('valid'),
            defaultValue=$referrer[0].defaultValue,
            referrer= _.isEmpty(referrerVal) || referrerValid || referrerVal==$referrer.attr('placeholder');
        // check button disabled, referrer can be empty, if not empty, it must be right
        if(frontInputValid && $agreement.prop('checked') && referrer) {
            $registerSubmit.prop('disabled',false);
        }
        else {
            $registerSubmit.prop('disabled',true);
        }

    }
    $agreement.on('click',function() {
        checkValidNum();
    });
    $frontInput.on('blur',function(event) {
        var mobile = $('input.mobile', registerUserForm);
        if(event.target.name=='mobile' && mobile.attr('preValue')!=mobile.val()) {
            if(countTimer) {
                clearInterval(countTimer);
                $('input.captcha', registerUserForm).removeClass('valid').val('')
                    .next('label')
                    .html('请输入验证码');
                fetchCaptchaElement.html('重新发送').removeClass('disabledButton').prop('disabled',false);
            }
            else {
                $('input.captcha', registerUserForm).removeClass('valid').val('');
            }
        }
        setTimeout(checkValidNum,100);
    });
    $referrer.on('keyup',function(event) {
        var $target=$(event.target),
            referrerError=$('#referrerError'),
        value=event.target.value;
        if(value) {
            var checkValid=false;
            $.ajax({
                url:'/register/user/referrer/'+value+'/is-exist',
                type: 'GET',
                dataType: 'json',
                contentType: 'application/json; charset=UTF-8',
            })
                .done(function (res) {
                    checkValid=res.data.status?true:false;
                    if(checkValid) {
                        $target.removeClass('error').addClass('valid');
                        referrerError.html('');
                    }
                    else {
                        $target.removeClass('valid').addClass('error');
                        referrerError.html('推荐人不存在');
                    }
                    checkValidNum();
                });

        }
        else {
            $target.removeClass('error').addClass('valid');
            referrerError.html('');
            checkValidNum();
        }

    });
});
