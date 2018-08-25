<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>达内－NetCTOSS</title>
    <link type="text/css" rel="stylesheet" media="all" href="styles/global.css"/>
    <link type="text/css" rel="stylesheet" media="all" href="styles/global_color.css"/>
    <script language="javascript" type="text/javascript">
        //保存成功的提示信息
        function showResult() {
            showResultDiv(true);
            window.setTimeout("showResultDiv(false);", 3000);
        }

        function showResultDiv(flag) {
            var divResult = document.getElementById("save_result_info");
            if (flag)
                divResult.style.display = "block";
            else
                divResult.style.display = "none";
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
    <div id="save_result_info" class="save_success">保存成功！</div><!--保存失败，旧密码错误！-->
    <form action="modifyPwd.do" method="post" class="main_form">
        <div class="text_info clearfix"><span>旧密码：</span></div>
        <div class="input_info">
            <input type="password" class="width200" name="oldPwd"/><span class="required">*</span>
            <div class="validate_msg_medium">30长度以内的字母、数字和下划线的组合</div>
        </div>
        <div class="text_info clearfix"><span>新密码：</span></div>
        <div class="input_info">
            <input type="password" class="width200" name="newPwd"/><span class="required">*</span>
            <div class="validate_msg_medium">30长度以内的字母、数字和下划线的组合</div>
        </div>
        <div class="text_info clearfix"><span>重复新密码：</span></div>
        <div class="input_info">
            <input type="password" class="width200" name="confirmPwd"/><span class="required">*</span>
            <div class="validate_msg_medium">两次新密码必须相同</div>
        </div>
        <div class="button_info clearfix">
            <input type="submit" value="保存" class="btn_save" onclick="showResult();"/>
            <input type="button" value="取消" class="btn_save"/>
        </div>
    </form>
    <div style="margin:auto;font-size: 20pt;color: red;text-align: center">${error}</div>
</div>
<!--主要区域结束-->
<div id="footer">
    <p>[源自北美的技术，最优秀的师资，最真实的企业环境，最适用的实战项目]</p>
    <p>版权所有(C)加拿大达内IT培训集团公司 </p>
</div>
</body>
</html>
