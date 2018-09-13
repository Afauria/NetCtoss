<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>达内－NetCTOSS</title>
    <link type="text/css" rel="stylesheet" media="all" href="styles/global.css"/>
    <link type="text/css" rel="stylesheet" media="all" href="styles/global_color.css"/>
</head>
<body onload="initialYearAndMonth();">
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
            <div>账务账号：<span class="readonly width70">${bill.account.loginName}</span></div>
            <div>身份证：<span class="readonly width150">${bill.account.idCard}</span></div>
            <div>姓名：<span class="readonly width70">${bill.account.realName}</span></div>
            <div>计费时间：<span class="readonly width70">${bill.billMonth}</span></div>
            <div>总费用：<span class="readonly width70">${bill.billFee}</span></div>
            <input type="button" value="返回" class="btn_add" onclick="location.href='findBills.do';"/>
        </div>
        <!--数据区域：用表格展示数据-->
        <div id="data">
            <table id="datalist">
                <tr>
                    <th class="width70">账单明细ID</th>
                    <th class="width150">OS 账号</th>
                    <th class="width150">服务器 IP</th>
                    <th class="width70">账务账号ID</th>
                    <th class="width150">时长</th>
                    <th>费用</th>
                    <th class="width150">资费</th>
                    <th class="width50"></th>
                </tr>
                <c:forEach items="${bill.services}" var="serviceItem">
                    <tr>
                        <td>${serviceItem.serviceId}</td>
                        <td>${serviceItem.osUsername}</td>
                        <td>${serviceItem.unixHost}</td>
                        <td>${bill.accountId}</td>
                        <td>1小时3分15秒</td>
                        <td>43.45</td>
                        <td>${serviceItem.cost.name}</td>
                        <td><a href="billServiceDetail.do?billId=${bill.billId}&serviceId=${serviceItem.serviceId}"
                               title="业务详单">详单</a></td>
                    </tr>
                </c:forEach>
            </table>
        </div>
        <!--分页-->
        <div id="pages">
            <c:if test="${currentPage>1}">
                <a href="billItem.do?billId=${bill.billId}&currentPage=${currentPage-1}">上一页</a>
            </c:if>
            <c:forEach begin="1" end="${pageCount}" var="p">
                <c:choose>
                    <c:when test="${p==currentPage}">
                        <a href="billItem.do?billId=${bill.billId}&currentPage=${p}" class="current_page">${p}</a>
                    </c:when>
                    <c:otherwise>
                        <a href="billItem.do?billId=${bill.billId}&currentPage=${p}">${p}</a>
                    </c:otherwise>
                </c:choose>
            </c:forEach>
            <c:if test="${currentPage<pageCount}">
                <a href="billItem.do?billId=${bill.billId}&currentPage=${currentPage+1}">下一页</a>
            </c:if>
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
