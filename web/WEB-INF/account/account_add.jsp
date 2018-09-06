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

        //显示选填的信息项
        function showOptionalInfo(imgObj) {
            var div = document.getElementById("optionalInfo");
            if (div.className == "hide") {
                div.className = "show";
                imgObj.src = "images/hide.png";
            }
            else {
                div.className = "hide";
                imgObj.src = "images/show.png";
            }
        }

        function calBirthdate(obj) {
            var idcard = $(obj).val();
            var year = idcard.substring(6, 10);
            var month = idcard.substring(10, 12);
            var day = idcard.substring(12, 14);
            $("input[name=birthdate]").val(year + "-" + month + "-" + day);
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
            showResult();
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
    <!--保存成功或者失败的提示消息-->
    <div id="save_result_info" class="save_fail">保存失败，该身份证已经开通过账务账号！</div>
    <form action="addAccount.do" method="post" class="main_form" onsubmit="return validate()">
        <!--必填项-->
        <div class="text_info clearfix"><span>姓名：</span></div>
        <div class="input_info">
            <input type="text" value="张三" name="realName"/>
            <span class="required">*</span>
            <div class="validate_msg_long validate_msg">20长度以内的汉字、字母和数字的组合</div>
        </div>
        <div class="text_info clearfix"><span>身份证：</span></div>
        <div class="input_info">
            <input type="text" value="440582199602272754" name="accountIdCard" onchange="calBirthdate(this)"/>
            <span class="required">*</span>
            <div class="validate_msg_long validate_msg">正确的身份证号码格式</div>
        </div>
        <div class="text_info clearfix"><span>登录账号：</span></div>
        <div class="input_info">
            <input type="text" placeholder="创建即启用，状态为开通" name="loginName"/>
            <span class="required">*</span>
            <div class="validate_msg_long validate_msg">30长度以内的字母、数字和下划线的组合</div>
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
        <div class="text_info clearfix"><span>电话：</span></div>
        <div class="input_info">
            <input type="text" class="width200" name="telephone"/>
            <span class="required">*</span>
            <div class="validate_msg_medium validate_msg">正确的电话号码格式：手机或固话</div>
        </div>
        <!--可选项-->
        <div class="text_info clearfix"><span>可选项：</span></div>
        <div class="input_info">
            <img src="images/show.png" alt="展开" onclick="showOptionalInfo(this);"/>
        </div>
        <div id="optionalInfo" class="hide">
            <div class="text_info clearfix"><span>推荐人身份证号码：</span></div>
            <div class="input_info">
                <input type="text" name="recommenderIdCard"/>
                <div class="validate_msg_long validate_msg">正确的身份证号码格式</div>
            </div>
            <div class="text_info clearfix"><span>生日：</span></div>
            <div class="input_info">
                <input type="text" value="由身份证号计算而来" readonly class="readonly" name="birthdate"/>
            </div>
            <div class="text_info clearfix"><span>Email：</span></div>
            <div class="input_info">
                <input type="text" class="width250" name="email"/>
                <div class="validate_msg_short validate_msg">50长度以内，合法的 Email 格式</div>
            </div>
            <div class="text_info clearfix"><span>职业：</span></div>
            <div class="input_info">
                <select name="occupation">
                    <option>干部</option>
                    <option>学生</option>
                    <option>技术人员</option>
                    <option>其他</option>
                </select>
            </div>
            <div class="text_info clearfix"><span>性别：</span></div>
            <div class="input_info fee_type">
                <input type="radio" value="0" checked="checked" id="female" name="gender"/>
                <label for="female">女</label>
                <input type="radio" value="1" id="male" name="gender"/>
                <label for="male">男</label>
            </div>
            <div class="text_info clearfix"><span>通信地址：</span></div>
            <div class="input_info">
                <input type="text" class="width350" name="mailAddress"/>
                <div class="validate_msg_tiny validate_msg">50长度以内</div>
            </div>
            <div class="text_info clearfix"><span>邮编：</span></div>
            <div class="input_info">
                <input type="text" name="zipcode"/>
                <div class="validate_msg_short validate_msg">6位数字</div>
            </div>
            <div class="text_info clearfix"><span>QQ：</span></div>
            <div class="input_info">
                <input type="text" name="QQ"/>
                <div class="validate_msg_short validate_msg">5到13位数字</div>
            </div>
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
<script>
    if (${param.error!=null}) {
        showResult(${param.error});
    }
</script>
</body>
</html>
