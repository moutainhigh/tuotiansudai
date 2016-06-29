var require = {
    'baseUrl': staticServer,
    'paths': {
        'text': staticServer + '/activity/js/libs/text-2.0.14',
        'jquery': staticServer + '/activity/js/libs/jquery-1.11.3.min',
        'csrf': staticServer + '/activity/js/libs/csrf',
        'jqueryPage': staticServer + '/activity/js/libs/jquery.page',
        'jquery.validate': staticServer + '/activity/js/libs/jquery.validate-1.14.0.min',
        'jquery.form': staticServer + '/activity/js/libs/jquery.form-3.51.0.min',
        'autoNumeric': staticServer + '/activity/js/libs/autoNumeric',
        'mustache': staticServer + '/activity/js/libs/mustache-2.1.3.min',
        'moment': staticServer + '/activity/js/libs/moment-2.10.6.min',
        'underscore': staticServer + '/activity/js/libs/underscore-1.8.3.min',
        'jquery.ajax.extension': staticServer + '/activity/js/jquery_ajax_extension',
        'daterangepicker': staticServer + '/activity/js/libs/jquery.daterangepicker-0.0.7',
        'pagination': staticServer + '/activity/js/pagination',
        'lodash': staticServer + '/activity/js/libs/lodash.min',
        'layer': staticServer + '/activity/js/libs/layer/layer',
        'layer-extend':staticServer+'/activity/js/libs/layer/extend/layer.ext',
        'echarts': staticServer + '/activity/js/libs/echarts/dist/echarts.min',
        'jquery.validate.extension': staticServer + '/activity/js/jquery_validate_extension',
        'commonFun': staticServer + '/activity/js/common',
        'layerWrapper': staticServer + '/activity/js/wrapper-layer',
        'fullPage':staticServer+'/activity/js/libs/jquery.fullPage.min',
        'swiper':staticServer+'/activity/js/libs/swiper-3.2.7.jquery.min',
        'load-swiper':staticServer+'/activity/js/load_swiper',
        'coupon-alert': staticServer+'/activity/js/coupon_alert',
        'cnzz-statistics': staticServer+'/activity/js/cnzz_statistics',
        'red-envelope-float': staticServer+'/activity/js/red-envelope-float',
        'drag': staticServer+'/activity/js/libs/drag',
        'rotate': staticServer+'/activity/js/libs/jqueryrotate.min',
        'template':staticServer+'/activity/js/libs/template.min',
        'fancybox':staticServer+'/activity/js/libs/jquery.fancybox.min',
        'count_down': staticServer+'/activity/js/count_down',
        'placeholder':staticServer+'/activity/js/libs/jquery.enplaceholder',
        'superslide': staticServer + '/activity/js/libs/jquery.SuperSlide.2.1.1'
    },
    'waitSeconds':0,
    'shim': {
        'jquery.validate': ['jquery'],
        'jquery.form': ['jquery'],
        'jqueryPage': ['jquery'],
        'autoNumeric': ['jquery'],
        'pagination': ['jquery'],
        'layer': ['jquery'],
        'layer-extend': ['jquery','layer'],
        'layerWrapper':['layer','layer-extend'],
        'commonFun': ['jquery.validate'],
        'jquery.validate.extension': ['jquery', 'jquery.validate'],
        'fullPage': ['jquery'],
        'swiper':['jquery'],
        'load-swiper':['swiper'],
        'drag':['jquery'],
        'rotate':['jquery'],
        'fancybox':['jquery'],
        'placeholder':['jquery']
    },

    config: {
        text: {
            useXhr: function (url, protocol, hostname, port) {
                return true;
            }
        }
    }
};

