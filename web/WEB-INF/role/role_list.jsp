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
    <script language="javascript" type="text/javascript">
        function deleteRole(roleId) {
            var bool = window.confirm("确定要删除此角色吗？");
            if (bool) {
                location.href = "deleteRole.do?roleId=" + roleId;
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
    <form action="" method="">
        <!--查询-->
        <div class="search_add">
            <input type="button" value="增加" class="btn_add" onclick="location.href='toAddRole.do';"/>
        </div>
        <!--删除的操作提示-->
        <div id="operate_result_info" class="operate_success">
            <img src="images/close.png" onclick="this.parentNode.style.display='none';"/>
            删除成功！
        </div> <!--删除错误！该角色被使用，不能删除。-->
        <!--数据区域：用表格展示数据-->
        <div id="data">
            <table id="datalist">
                <tr>
                    <th>角色 ID</th>
                    <th>角色名称</th>
                    <th class="width600">拥有的权限</th>
                    <th class="td_modi"></th>
                </tr>
                <%
                    List<Role> roles = (List<Role>) request.getAttribute("roles");
                    for (Role role : roles) {
                %>
                <tr>
                    <td><%= role.getRoleId()%></td>
                    <td><%= role.getRoleName()%></td>
                    <td><% for (int i = 0; i < role.getRoleModules().size(); i++) {
                        if (i != 0) {
                            out.println("、");
                        }
                        out.print(role.getRoleModules().get(i).getModuleName());
                    }%></td>
                    <td>
                        <input type="button" value="修改" class="btn_modify"
                               onclick="location.href='toModifyRole.do?roleId='+<%= role.getRoleId() %>;"/>
                        <input type="button" value="删除" class="btn_delete" onclick="deleteRole(<%=role.getRoleId()%>);"/>
                    </td>
                </tr>
                <%
                    }
                %>
            </table>
        </div>
        <!--分页-->
        <div id="pages">
            <c:if test="${currentPage>1}">
                <a href="findRoles.do?currentPage=${currentPage-1}">上一页</a>
            </c:if>
            <c:forEach begin="1" end="${pageCount}" var="p">
                <c:choose>
                    <c:when test="${p==currentPage}">
                        <a href="findRoles.do?currentPage=${p}" class="current_page">${p}</a>
                    </c:when>
                    <c:otherwise>
                        <a href="findRoles.do?currentPage=${p}">${p}</a>
                    </c:otherwise>
                </c:choose>
            </c:forEach>
            <c:if test="${currentPage<pageCount}">
                <a href="findRoles.do?currentPage=${currentPage+1}">下一页</a>
            </c:if>
        </div>
    </form>
</div>
<!--主要区域结束-->
<div id="footer">
    <p>[源自北美的技术，最优秀的师资，最真实的企业环境，最适用的实战项目]</p>
    <p>版权所有(C)加拿大达内IT培训集团公司 </p>
</div>
<script>
    if (${param.deleteSuccess==1}) {
        document.getElementById("operate_result_info").style.display = "block";
    }
</script>
</body>
</html>
