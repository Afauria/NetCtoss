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
        //保存成功的提示消息
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

        function validate() {
            var hasSelected = false;
            var isValidate=true;
            $(".required").prev().each(function () {
                if ($(this).val() == "") {
                    $(this).siblings(".validate_msg").addClass("error_msg");
                    isValidate=false;
                    return false;
                } else {
                    $(this).siblings(".validate_msg").removeClass("error_msg");
                }
            });
            if($("input[name='password']").val() !=$("input[name='confirmPassword']").val()){
                $("input[name='confirmPassword']").siblings(".validate_msg").addClass("error_msg");
                isValidate=false;
            }else{
                $("input[name='confirmPassword']").siblings(".validate_msg").removeClass("error_msg");
            }

            if(!isValidate){
                return false;
            }
            $(".select_role").each(function () {
                if ($(this).is(":checked")) {
                    hasSelected = true;
                    //结束循环
                    return false;
                }
            });
            if (!hasSelected) {
                $(".must_selected").addClass("error_msg");
                return false;
            }else{
                $(".must_selected").removeClass("error_msg");
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
    <div id="save_result_info" class="save_success">保存成功！</div>
    <form action="addAdmin.do" method="post" class="main_form" onsubmit="return validate()">
        <div class="text_info clearfix"><span>姓名：</span></div>
        <div class="input_info">
            <input type="text" name="adminName"/>
            <span class="required">*</span>
            <div class="validate_msg_long validate_msg">20长度以内的汉字、字母、数字的组合</div>
        </div>
        <div class="text_info clearfix"><span>管理员账号：</span></div>
        <div class="input_info">
            <input type="text" name="adminCode"/>
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
            <input type="password" name="confirmPassword" />
            <span class="required">*</span>
            <div class="validate_msg_long validate_msg">两次密码必须相同</div>
        </div>
        <div class="text_info clearfix"><span>电话：</span></div>
        <div class="input_info">
            <input type="text" class="width200" name="telephone"/>
            <span class="required">*</span>
            <div class="validate_msg_medium validate_msg">正确的电话号码格式：手机或固话</div>
        </div>
        <div class="text_info clearfix"><span>Email：</span></div>
        <div class="input_info">
            <input type="text" class="width200" name="email" />
            <span class="required">*</span>
            <div class="validate_msg_medium validate_msg">50长度以内，正确的 email 格式</div>
        </div>
        <div class="text_info clearfix"><span>角色：</span></div>
        <div class="input_info_high">
            <div class="input_info_scroll">
                <ul>
                    <c:forEach items="${totalRoles}" var="roleItem">
                        <li><input class="select_role" type="checkbox" name="selectRolesId" value="${roleItem.roleId}">
                                ${roleItem.roleName}
                        </li>
                    </c:forEach>
                </ul>
            </div>
            <span style="color:red;font-size: 10pt">*</span>
            <div class="validate_msg_tiny validate_msg must_selected">至少选择一个</div>
        </div>
        <div class="button_info clearfix">
            <input type="submit" value="保存" class="btn_save"/>
            <input type="button" value="取消" class="btn_save" onclick="location.href='findAdmins.do'"/>
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
        alert("该用户已存在");
    }
</script>
</body>
</html>
