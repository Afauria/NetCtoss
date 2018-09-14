<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>达内－NetCTOSS</title>
    <link type="text/css" rel="stylesheet" media="all" href="styles/global.css"/>
    <link type="text/css" rel="stylesheet" media="all" href="styles/global_color.css"/>
    <script type="text/javascript" src="js/jquery-3.3.1.min.js"></script>
    <script type="text/javascript" src="js/all.js"></script>
    <script language="javascript" type="text/javascript">
        //保存成功的提示信息
        function showResult(msg) {
            showResultDiv(true, msg);
            window.setTimeout("showResultDiv(false,'');", 3000);
        }

        function showResultDiv(flag, msg) {
            if (flag) {
                $("#save_result_info").text(msg);
                $("#save_result_info").css("display", "block");
            } else {
                $("#save_result_info").css("display", "none");
            }
        }

        function validate() {
            var isValidate = true;
            $(".required").prev().each(function () {
                if ($(this).val() == "") {
                    $(this).siblings(".validate_msg").addClass("error_msg");
                    isValidate = false;
                    return false;
                } else {
                    $(this).siblings(".validate_msg").removeClass("error_msg");
                }
            });
            if ($("input[name='newPwd']").val() != $("input[name='confirmPwd']").val()) {
                $("input[name='confirmPwd']").siblings(".validate_msg").addClass("error_msg");
                isValidate = false;
            } else {
                $("input[name='confirmPwd']").siblings(".validate_msg").removeClass("error_msg");
            }
            if (!isValidate) {
                return false;
            }
            return true;
        }
    </script>
</head>
<body>
<!--Logo区域开始-->
<div id="header">
    <%@ include file="../logo.jsp" %>
</div>
<!--Logo区域结束-->
<!--导航区域开始-->
<div id="navi">
    <%@include file="../menu.jsp" %>
</div>
<!--导航区域结束-->
<!--主要区域开始-->
<div id="main">
    <!--保存操作后的提示信息：成功或者失败-->
    <div id="save_result_info" class="save_fail">保存成功！</div><!--保存失败，旧密码错误！-->
    <form action="modifyPwd.do" method="post" class="main_form" onsubmit="return validate()">
        <div class="text_info clearfix"><span>旧密码：</span></div>
        <div class="input_info">
            <input type="password" class="width200" name="oldPwd"/>
            <span class="required">*</span>
            <div class="validate_msg_medium validate_msg">30长度以内的字母、数字和下划线的组合</div>
        </div>
        <div class="text_info clearfix"><span>新密码：</span></div>
        <div class="input_info">
            <input type="password" class="width200" name="newPwd"/>
            <span class="required">*</span>
            <div class="validate_msg_medium validate_msg">30长度以内的字母、数字和下划线的组合</div>
        </div>
        <div class="text_info clearfix"><span>重复新密码：</span></div>
        <div class="input_info">
            <input type="password" class="width200" name="confirmPwd"/>
            <span class="required">*</span>
            <div class="validate_msg_medium validate_msg">两次新密码必须相同</div>
        </div>
        <div class="button_info clearfix">
            <input type="submit" value="保存" class="btn_save"/>
            <input type="button" value="取消" class="btn_save" onclick="location.href='toIndex.do'"/>
        </div>
    </form>
</div>
<!--主要区域结束-->
<div id="footer">
    <p>[源自北美的技术，最优秀的师资，最真实的企业环境，最适用的实战项目]</p>
    <p>版权所有(C)加拿大达内IT培训集团公司 </p>
</div>
<script>
    if (${param.error!=null}) {
        showResult(${param.error});
    }
</script>
</body>
</html>
