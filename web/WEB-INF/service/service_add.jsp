<%@ page import="com.alibaba.fastjson.JSONArray" %>
<%@ page import="com.zwy.work.entity.Account" %>
<%@ page import="java.util.List" %>
<%@ page import="com.alibaba.fastjson.JSON" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>达内－NetCTOSS</title>
    <link type="text/css" rel="stylesheet" media="all" href="styles/global.css"/>
    <link type="text/css" rel="stylesheet" media="all" href="styles/global_color.css"/>
    <script type="text/javascript" src="js/jquery-3.3.1.min.js"></script>
    <script language="javascript" type="text/javascript">
        //保存成功的提示信息
        function showResult(msg) {
            showResultDiv(true,msg);
            window.setTimeout("showResultDiv(false,'');", 3000);
        }

        function showResultDiv(flag,msg) {
            if (flag){
                $("#save_result_info").text(msg);
                $("#save_result_info").css("display","block");
            } else{
                $("#save_result_info").css("display","none");
            }
        }

        //自动查询账务账号
        function searchAccounts(obj,jsonstr) {
            var accounts=jsonstr;
            var hasAccount=false;
            for (var i in accounts) {
                if (accounts[i].idCard == $("input[name=idcard]").val()) {
                    $("input[name=accountLoginName]").val(accounts[i].loginName);
                    hasAccount=true;
                    return false;
                }
            }
            if(!hasAccount){
                $(obj).siblings(".validate_msg").addClass("error_msg");
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
            if ($("input[name='password']").val() != $("input[name='confirmPassword']").val()) {
                $("input[name='confirmPassword']").siblings(".validate_msg").addClass("error_msg");
                isValidate = false;
            } else {
                $("input[name='confirmPassword']").siblings(".validate_msg").removeClass("error_msg");
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
    <!--保存操作的提示信息-->
    <div id="save_result_info" class="save_fail">保存失败！192.168.0.23服务器上已经开通过 OS 账号 “mary”。</div>
    <form action="addService.do" method="post" class="main_form" onsubmit="return validate()">
        <%
            List<Account> accounts = (List<Account>) request.getAttribute("accounts");
            String jsonstr = JSON.toJSONString(accounts);
        %>
        <!--内容项-->
        <div class="text_info clearfix"><span>身份证：</span></div>
        <div class="input_info">
            <input type="text" value="" class="width180" name="idcard"/>
            <span class="required">*</span>
            <input type="button" value="查询账务账号" class="btn_search_large" onclick='searchAccounts(this,<%=jsonstr%>)'/>
            <div class="validate_msg_short validate_msg">没有此身份证号，请重新录入。</div>
        </div>
        <div class="text_info clearfix"><span>账务账号：</span></div>
        <div class="input_info">
            <input type="text" value="" name="accountLoginName" readonly class="readonly"/>
            <span class="required">*</span>
            <div class="validate_msg_long validate_msg">没有此账务账号，请重新录入。</div>
        </div>
        <div class="text_info clearfix"><span>资费类型：</span></div>
        <div class="input_info">
            <select name="costName">
                <c:forEach items="${costs}" var="costItem">
                    <option>${costItem.name}</option>
                </c:forEach>
            </select>
        </div>
        <div class="text_info clearfix"><span>服务器 IP：</span></div>
        <div class="input_info">
            <input type="text" value="192.168.0.23" name="host"/>
            <span class="required">*</span>
            <div class="validate_msg_long validate_msg">15 长度，符合IP的地址规范</div>
        </div>
        <div class="text_info clearfix"><span>登录 OS 账号：</span></div>
        <div class="input_info">
            <input type="text" value="" name="osUsername"/>
            <span class="required">*</span>
            <div class="validate_msg_long validate_msg">8长度以内的字母、数字和下划线的组合</div>
        </div>
        <div class="text_info clearfix"><span>密码：</span></div>
        <div class="input_info">
            <input type="password" name="password"/>
            <span class="required">*</span>
            <div class="validate_msg_long validate_msg">30长度以内的字母、数字和下划线的组合</div>
        </div>
        <div class="text_info clearfix"><span>重复密码：</span></div>
        <div class="input_info">
            <input type="password" name="confirmPassword"/>
            <span class="required">*</span>
            <div class="validate_msg_long validate_msg">两次密码必须相同</div>
        </div>
        <!--操作按钮-->
        <div class="button_info clearfix">
            <input type="submit" value="保存" class="btn_save"/>
            <input type="button" value="取消" class="btn_save" onclick="location.href='findServices.do'"/>
        </div>
    </form>
</div>
<!--主要区域结束-->
<div id="footer">
    <span>[源自北美的技术，最优秀的师资，最真实的企业环境，最适用的实战项目]</span>
    <br/>
    <span>版权所有(C)加拿大达内IT培训集团公司 </span>
</div>
<script>
    if (${param.error!=null}) {
        showResult(${param.error});
    }
</script>
</body>
</html>
