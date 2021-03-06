require(['jquery', 'bootstrap', 'bootstrapDatetimepicker', 'jquery-ui', 'bootstrapSelect', 'moment'], function ($) {

    $(function(){
        $('.selectpicker').selectpicker();

        $('#datetimepicker1').datetimepicker({format: 'YYYY-MM-DD'});
        $('#datetimepicker2').datetimepicker({format: 'YYYY-MM-DD'});
        $('form button[type="reset"]').click(function () {
            location.href = "experience-record";
        });
        $('form button[type="submit"]').click(function (event) {
            var queryParams = '';
            if ($('form input[name="loanId"]').val() != "" && !$('form input[name="loanId"]').val().match("^[0-9]*$")) {
                $('form input[name="loanId"]').val('0');
            }
            if ($('form input[name="loanId"]').val()) {
                queryParams += "loanId=" + $('form input[name="loanId"]').val() + "&";
            }
            if ($('form input[name="mobile"]').val().length > 0) {
                queryParams += "mobile=" + $('form input[name="mobile"]').val() + "&";
            }
            if ($('form input[name="startTime"]').val()) {
                queryParams += "startTime=" + $('form input[name="startTime"]').val() + "&";
            }
            if ($('form input[name="endTime"]').val()) {
                queryParams += "endTime=" + $('form input[name="endTime"]').val() + "&";
            }
            if ($('form select[name="source"]').val()) {
                queryParams += "source=" + $('form select[name="source"]').val() + "&";
            }
            location.href = "?" + queryParams;
            return false;
        });

        //自动完成提示
        var autoValue = '';
        $("#tags").autocomplete({
            source: function (query, process) {
                $.get('/user-manage/mobile/' + query.term + '/search', function (respData) {
                    autoValue = respData;
                    return process(respData);
                });
            }
        });
        $("#tags").blur(function () {
            for (var i = 0; i < autoValue.length; i++) {
                if ($(this).val() == autoValue[i]) {
                    $(this).removeClass('Validform_error');
                    return false;
                } else {
                    $(this).addClass('Validform_error');
                }

            }

        });

    })

});
