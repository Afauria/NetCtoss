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

        //显示修改密码的信息项
        function showPwd(chkObj) {
            if (chkObj.checked)
                document.getElementById("divPwds").style.display = "block";
            else
                document.getElementById("divPwds").style.display = "none";
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
            if(!limit20Validate($("input[name=realName]").val())){
                $("input[name=realName]").siblings(".validate_msg").addClass("error_msg");
            }
            if (!phoneValidate($("input[name=telephone]").val())) {
                $("input[name=telephone]").siblings(".validate_msg").addClass("error_msg");
            }
            if (!emailValidate($("input[name=email]").val())) {
                $("input[name=email]").siblings(".validate_msg").addClass("error_msg");
            }
            if (!qqValidate($("input[name=QQ]").val())) {
                $("input[name=QQ]").siblings(".validate_msg").addClass("error_msg");
            }
            if(!limit6Validate($("input[name=zipcode]").val())){
                $("input[name=zipcode]").siblings(".validate_msg").addClass("error_msg");
            }
            if ($(".error_msg").length == 0) {
                return true;
            } else {
                return false;
            }
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
    <!--保存成功或者失败的提示消息-->
    <div id="save_result_info" class="save_fail">保存失败，旧密码错误！</div>
    <form action="modifyAccount.do" method="post" class="main_form" onsubmit="return validate()">
        <!--必填项-->
        <div class="text_info clearfix"><span>账务账号ID：</span></div>
        <div class="input_info">
            <input type="text" value="${accountInfo.accountId}" readonly class="readonly" name="accountId"/>
        </div>
        <div class="text_info clearfix"><span>姓名：</span></div>
        <div class="input_info">
            <input type="text" value="${accountInfo.realName}" name="realName"/>
            <span class="required">*</span>
            <div class="validate_msg_long validate_msg">20长度以内的汉字、字母和数字的组合</div>
        </div>
        <div class="text_info clearfix"><span>身份证：</span></div>
        <div class="input_info">
            <input type="text" value="${accountInfo.idCard}" readonly class="readonly"/>
        </div>
        <div class="text_info clearfix"><span>登录账号：</span></div>
        <div class="input_info">
            <input type="text" value="${accountInfo.loginName}" readonly class="readonly"/>
            <!--
            <div class="change_pwd">
                <input id="chkModiPwd" type="checkbox" onclick="showPwd(this);" />
                <label for="chkModiPwd">修改密码</label>
            </div>
            -->
        </div>
        <!--修改密码部分-->
        <!--
        <div id="divPwds">
            <div class="text_info clearfix"><span>旧密码：</span></div>
            <div class="input_info">
                <input type="password"  />
                <span class="required">*</span>
                <div class="validate_msg_long">30长度以内的字母、数字和下划线的组合</div>
            </div>
            <div class="text_info clearfix"><span>新密码：</span></div>
            <div class="input_info">
                <input type="password"  />
                <span class="required">*</span>
                <div class="validate_msg_long">30长度以内的字母、数字和下划线的组合</div>
            </div>
            <div class="text_info clearfix"><span>重复新密码：</span></div>
            <div class="input_info">
                <input type="password"  />
                <span class="required">*</span>
                <div class="validate_msg_long">两次密码必须相同</div>
            </div>
        </div>
        -->
        <div class="text_info clearfix"><span>电话：</span></div>
        <div class="input_info">
            <input type="text" class="width200" value="${accountInfo.telephone}" name="telephone"/>
            <span class="required">*</span>
            <div class="validate_msg_medium validate_msg">正确的电话号码格式：手机或固话</div>
        </div>
        <div class="text_info clearfix"><span>推荐人身份证号码：</span></div>
        <div class="input_info">
            <input type="text" value="${recommenderInfo.idCard}" readonly class="readonly"/>
            <%--<div class="validate_msg_long validate_msg">正确的身份证号码格式</div>--%>
        </div>
        <div class="text_info clearfix"><span>生日：</span></div>
        <div class="input_info">
            <input type="text" value="${accountInfo.birthdate}" readonly class="readonly"/>
        </div>
        <div class="text_info clearfix"><span>Email：</span></div>
        <div class="input_info">
            <input type="text" class="width200" value="${accountInfo.email}" name="email"/>
            <div class="validate_msg_medium" validate_msg>50长度以内，合法的 Email 格式</div>
        </div>
        <div class="text_info clearfix"><span>职业：</span></div>
        <div class="input_info">
            <select name="occupation">
                <option ${accountInfo.occupation=="干部"?"checked":""}>干部</option>
                <option ${accountInfo.occupation=="学生"?"checked":""}>学生</option>
                <option ${accountInfo.occupation=="技术人员"?"checked":""}>技术人员</option>
                <option ${accountInfo.occupation=="其他"?"checked":""}>其他</option>
            </select>
        </div>
        <div class="text_info clearfix"><span>性别：</span></div>
        <div class="input_info fee_type">
            <input type="radio" name="gender" value="0" id="female" ${accountInfo.gender=="0"?"checked":""} onclick="feeTypeChange(1);"/>
            <label for="female">女</label>
            <input type="radio" name="gender" value="1" id="male" ${accountInfo.gender=="1"?"checked":""} onclick="feeTypeChange(2);"/>
            <label for="male">男</label>
        </div>
        <div class="text_info clearfix"><span>通信地址：</span></div>
        <div class="input_info">
            <input type="text" class="width350" value="${accountInfo.mailAddress}" name="mailAddress"/>
            <div class="validate_msg_tiny validate_msg">50长度以内</div>
        </div>
        <div class="text_info clearfix"><span>邮编：</span></div>
        <div class="input_info">
            <input type="text" value="${accountInfo.zipcode}" name="zipcode"/>
            <div class="validate_msg_long validate_msg">6位数字</div>
        </div>
        <div class="text_info clearfix"><span>QQ：</span></div>
        <div class="input_info">
            <input type="text" value="${accountInfo.QQ}" name="QQ"/>
            <div class="validate_msg_long validate_msg">5到13位数字</div>
        </div>
        <!--操作按钮-->
        <div class="button_info clearfix">
            <input type="submit" value="保存" class="btn_save"/>
            <input type="button" value="取消" class="btn_save" onclick="location.href='findAccounts.do'"/>
        </div>
    </form>
</div>
<!--主要区域结束-->
<div id="footer">
    <span>[源自北美的技术，最优秀的师资，最真实的企业环境，最适用的实战项目]</span>
    <br/>
    <span>版权所有(C)加拿大达内IT培训集团公司 </span>
</div>
</body>
</html>
