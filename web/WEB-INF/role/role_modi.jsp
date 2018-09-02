<%@ page import="com.zwy.work.entity.Module" %>
<%@ page import="com.zwy.work.entity.Role" %>
<%@ page import="java.util.List" %>
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
        function validate(){
            var hasSelected=false;
            $(".select_module").each(function () {
                if($(this).is(":checked")){
                    hasSelected=true;
                    //结束循环
                    return false;
                }
            });
            if(!hasSelected){
                alert("至少选择一个权限")
                return false;
            }else{
                showResult();
                return true;
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
    <!--保存操作后的提示信息：成功或者失败-->
    <div id="save_result_info" class="save_success">保存成功！</div>
    <form action="modifyRole.do" method="post" class="main_form" onsubmit="return validate()">
        <div class="text_info clearfix"><span>角色名称：</span></div>
        <div class="input_info">
            <input type="hidden" value="${roleInfo.roleId}" name="roleId"/>
            <input type="text" class="width200" value="${roleInfo.roleName}" name="roleName"/>
            <span class="required">*</span>
            <div class="validate_msg_medium error_msg">不能为空，且为20长度的字母、数字和汉字的组合</div>
        </div>
        <div class="text_info clearfix"><span>设置权限：</span></div>
        <div class="input_info_high">
            <div class="input_info_scroll">
                <ul>
                    <%--el表达式取的对象是request的attribute，不是java程序片的变量--%>
                    <c:forEach items="${totalModules}" var="moduleItem">
                        <li><input class="select_module" type="checkbox" name="selectModulesId" value="${moduleItem.moduleId}"
                        <c:forEach items="${roleInfo.roleModules}" var="roleModuleItem">
                        <c:if test="${roleModuleItem.moduleId eq moduleItem.moduleId}">
                                   checked
                        </c:if>
                        </c:forEach>
                        >${moduleItem.moduleName}
                        </li>
                    </c:forEach>
                </ul>
            </div>
            <span class="required">*</span>
            <span class="validate_msg_tiny">至少选择一个权限</span>
        </div>
        <div class="button_info clearfix">
            <input type="submit" value="保存" class="btn_save"/>
            <input type="button" value="取消" class="btn_save" onclick="location.href='findRoles.do'"/>
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
