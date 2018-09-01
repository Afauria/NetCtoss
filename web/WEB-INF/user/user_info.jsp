<%@ page import="com.zwy.work.entity.Admin" %>
<%@ page import="com.zwy.work.entity.Role" %>
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
    <div id="save_result_info" class="save_success">保存成功！</div><!--保存失败，数据并发错误！-->
    <form action="updateUserInfo.do" method="" class="main_form">
        <%
            Admin userInfo = (Admin) request.getAttribute("userInfo");
            StringBuilder rolesStr=new StringBuilder();
            for(int i=0;i<userInfo.getAdminRoles().size();i++){
                if(i!=0){
                    rolesStr.append("、");
                }
                rolesStr.append(userInfo.getAdminRoles().get(i).getRoleName());
            }
        %>
        <div class="text_info clearfix"><span>管理员账号：</span></div>
        <div class="input_info">
            <input type="text" readonly="readonly" class="readonly" name="adminCode" value="<%= userInfo.getAdminCode() %>"/></div>
        <div class="text_info clearfix"><span>角色：</span></div>
        <div class="input_info">
            <input type="text" readonly="readonly" class="readonly width400" value="<%= rolesStr %>"/>
        </div>
        <div class="text_info clearfix"><span>姓名：</span></div>
        <div class="input_info">
            <input type="text" value="<%= userInfo.getAdminName() %>" name="adminName"/>
            <span class="required">*</span>
            <div class="validate_msg_long error_msg">20长度以内的汉字、字母的组合</div>
        </div>
        <div class="text_info clearfix"><span>电话：</span></div>
        <div class="input_info">
            <input type="text" class="width200" value="<%= userInfo.getTelephone() %>" name="telephone" />
            <div class="validate_msg_medium">电话号码格式：手机或固话</div>
        </div>
        <div class="text_info clearfix"><span>Email：</span></div>
        <div class="input_info">
            <input type="text" class="width200" value="<%= userInfo.getEmail() %>" name="email"/>
            <div class="validate_msg_medium">50长度以内，符合 email 格式</div>
        </div>
        <div class="text_info clearfix"><span>创建时间：</span></div>
        <div class="input_info">
            <input type="text" readonly="readonly" class="readonly" value="<%= userInfo.getEnrolldate() %>"/>
        </div>
        <div class="button_info clearfix">
            <input type="submit" value="保存" class="btn_save" onclick="showResult();"/>
            <input type="button" value="取消" class="btn_save" onclick="location.href='toIndex.do'"/>
        </div>
    </form>
</div>
<!--主要区域结束-->
<div id="footer">
    <p>[源自北美的技术，最优秀的师资，最真实的企业环境，最适用的实战项目]</p>
    <p>版权所有(C)加拿大达内IT培训集团公司 </p>
</div>
</body>
</html>
