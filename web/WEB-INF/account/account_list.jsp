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
        //删除
        function deleteAccount(accountId) {
            var bool = window.confirm("确定要删除此账务账号吗？\r\n删除后将不能恢复，且会删除其下属的所有业务账号。");
            // document.getElementById("operate_result_info").style.display = "block";
            if (bool) {
                location.href = "deleteAccount.do?accountId=" + accountId;
            }
        }

        //开通或暂停
        function setState(accountId, status) {
            var bool = false;
            if (status == "3") {
                alert("该账务账号已删除，无法修改！");
            } else if (status == "2") {
                bool = window.confirm("确定要开通此账务账号吗？");
                status = "1";
            } else {
                bool = window.confirm("确定要暂停此账务账号吗？");
                status = "2";
            }
            if (bool) {
                location.href = "setAccountState.do?accountId=" + accountId + "&status=" + status;
            }
        }

        function modifyAccount(accountId, status) {
            if (status == "3") {
                alert("该账务账号已删除，无法修改！");
                return;
            }
            location.href = 'toModifyAccount.do?accountId=' + accountId;
        }

        function searchAccounts() {
            location.href = 'searchAccounts.do?status=' + $("#status_search").val()
                + '&idcard=' + $("#idcard_search").val()
                + '&realname=' + $("#realname_search").val()
                + '&loginname=' + $("#loginname_search").val();
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
            <div>身份证：<input type="text" class="text_search" id="idcard_search" value="${idcard}"/></div>
            <div>姓名：<input type="text" class="width70 text_search" id="realname_search" value="${realname}"/></div>
            <div>登录名：<input type="text" class="text_search" id="loginname_search" value="${loginname}"/></div>
            <div>
                状态：
                <select class="select_search" id="status_search">
                    <option value="0" ${status eq "0"?"selected":""}>全部</option>
                    <option value="1" ${status eq "1"?"selected":""}>开通</option>
                    <option value="2" ${status eq "2"?"selected":""}>暂停</option>
                    <option value="3" ${status eq "3"?"selected":""}>删除</option>
                </select>
            </div>
            <div><input type="button" value="搜索" class="btn_search" onclick="searchAccounts()"/></div>
            <input type="button" value="增加" class="btn_add" onclick="location.href='toAddAccount.do';"/>
        </div>
        <!--删除等的操作提示-->
        <div id="operate_result_info">
            <img src="images/close.png" onclick="this.parentNode.style.display='none';"/>
            <span>删除成功，且已删除其下属的业务账号！</span>
        </div>
        <!--数据区域：用表格展示数据-->
        <div id="data">
            <table id="datalist">
                <tr>
                    <th>账号ID</th>
                    <th>姓名</th>
                    <th class="width150">身份证</th>
                    <th>登录名</th>
                    <th>状态</th>
                    <th class="width150">创建日期</th>
                    <th class="width150">上次登录时间</th>
                    <th class="width200"></th>
                </tr>
                <c:forEach items="${accounts}" var="accountItem">
                    <tr>
                        <td>${accountItem.accountId}</td>
                        <td><a href="accountDetail.do?id=${accountItem.accountId}">${accountItem.realName}</a></td>
                        <td>${accountItem.idCard}</td>
                        <td>${accountItem.loginName}</td>
                        <td>${accountItem.status eq "1"?"开通":(accountItem.status eq "2"?"暂停":(accountItem.status eq "3"?"删除":""))}</td>
                        <td>${accountItem.createDate}</td>
                        <td>2013-02-23 00:00:00</td>
                        <td class="td_modi">
                            <input type="button" value="${accountItem.status eq "1"?"暂停":"开通"}"
                                   class="${accountItem.status eq "1"?"btn_pause":"btn_start"}"
                                   onclick="setState(${accountItem.accountId},${accountItem.status});"/>
                            <input type="button" value="修改" class="btn_modify"
                                   onclick="modifyAccount(${accountItem.accountId},${accountItem.status});"/>
                            <input type="button" value="删除" class="btn_delete"
                                   onclick="deleteAccount(${accountItem.accountId});"/>
                        </td>
                    </tr>
                </c:forEach>
            </table>
            <p>业务说明：<br/>
                1、创建则开通，记载创建时间；<br/>
                2、暂停后，记载暂停时间；<br/>
                3、重新开通后，删除暂停时间；<br/>
                4、删除后，记载删除时间，标示为删除，不能再开通、修改、删除；<br/>
                5、暂停账务账号，同时暂停下属的所有业务账号；<br/>
                6、暂停后重新开通账务账号，并不同时开启下属的所有业务账号，需要在业务账号管理中单独开启；<br/>
                7、删除账务账号，同时删除下属的所有业务账号。</p>
        </div>
        <div id="pages">
            <c:if test="${currentPage>1}">
                <a href=${path}?currentPage=${currentPage-1}&status=${status}&idcard=${idcard}&realname=${realname}&loginname=${loginname}>上一页</a>
            </c:if>
            <c:forEach begin="1" end="${pageCount}" var="p">
                <c:choose>
                    <c:when test="${p==currentPage}">
                        <a href=${path}?currentPage=${p}&status=${status}&idcard=${idcard}&realname=${realname}&loginname=${loginname}
                           class="current_page">${p}</a>
                    </c:when>
                    <c:otherwise>
                        <a href=${path}?currentPage=${p}&status=${status}&idcard=${idcard}&realname=${realname}&loginname=${loginname}>${p}</a>
                    </c:otherwise>
                </c:choose>
            </c:forEach>
            <c:if test="${currentPage<pageCount}">
                <a href=${path}?currentPage=${currentPage+1}&status=${status}&idcard=${idcard}&realname=${realname}&loginname=${loginname}>下一页</a>
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
    if (${param.success!=null}) {
        $("#operate_result_info").css("display", "block");
        $("#operate_result_info").addClass("operate_success");
        $("#operate_result_info").children("span").text(${param.success});
    }
</script>
</body>
</html>
