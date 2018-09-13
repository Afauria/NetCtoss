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
        //写入下拉框中的年份和月份
        function initialYearAndMonth(_year, _month) {
            //写入最近3年
            var yearObj = document.getElementById("selYears");
            var year = (new Date()).getFullYear();
            for (var i = 0; i <= 2; i++) {
                var opObj = new Option(year - i, year - i);
                if (_year == year - i) {
                    opObj.setAttribute("selected", "selected");
                }
                yearObj.options[i] = opObj;
            }
            //写入 12 月
            var monthObj = document.getElementById("selMonths");
            var opObj = new Option("全部", "全部");
            if (_month == "全部") {
                opObj.setAttribute("selected", "selected");
            }
            monthObj.options[0] = opObj;
            for (var i = 1; i <= 12; i++) {
                var opObj = new Option(i, i);
                if (_month == i) {
                    opObj.setAttribute("selected", "selected");
                }
                monthObj.options[i] = opObj;
            }
        }

        function searchBills() {
            var month = $("#selMonths").val();
            location.href = 'searchBills.do?idCard=' + $("#idcard_search").val() +
                "&loginName=" + $("#loginname_search").val() +
                "&realName=" + $("#realname_search").val() +
                "&year=" + $("#selYears").val() +
                "&month=" + month;
        }
    </script>
</head>
<body onload="initialYearAndMonth('${year}','${month}');">
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
            <div>身份证：<input type="text" value="${idCard}" class="text_search" id="idcard_search"/></div>
            <div>账务账号：<input type="text" value="${loginName}" class="width100 text_search" id="loginname_search"/></div>
            <div>姓名：<input type="text" value="${realName}" class="width70 text_search" id="realname_search"/></div>
            <div>
                <select class="select_search" id="selYears">
                </select>
                年
                <select class="select_search" id="selMonths">
                </select>
                月
            </div>
            <div><input type="button" value="搜索" class="btn_search" onclick="searchBills()"/></div>
        </div>
        <!--数据区域：用表格展示数据-->
        <div id="data">
            <table id="datalist">
                <tr>
                    <th class="width50">账单ID</th>
                    <th class="width70">姓名</th>
                    <th class="width150">身份证</th>
                    <th class="width150">账务账号</th>
                    <th>费用</th>
                    <th class="width100">月份</th>
                    <th class="width100">支付方式</th>
                    <th class="width100">支付状态</th>
                    <th class="width50"></th>
                </tr>
                <c:forEach items="${bills}" var="billItem">
                    <tr>
                        <td>${billItem.billId}</td>
                        <td>${billItem.account.realName}</td>
                        <td>${billItem.account.idCard}</td>
                        <td>${billItem.account.loginName}</td>
                        <td>${billItem.billFee}</td>
                        <td>${billItem.billMonth}</td>
                        <td>${billItem.payMode}</td>
                        <td>${billItem.payStatus}</td>
                        <td><a href="billItem.do?billId=${billItem.billId}" title="账单明细">明细</a></td>
                    </tr>
                </c:forEach>
            </table>

            <p>业务说明：<br/>
                1、设计支付方式和支付状态，为用户自服务中的支付功能预留；<br/>
                2、只查询近 3 年的账单，即当前年和前两年，如2013/2012/2011；<br/>
                3、年和月的数据由 js 代码自动生成；<br/>
                4、由数据库中的ｊｏｂ每月的月底定时计算账单数据。</p>
        </div>
        <!--分页-->
        <div id="pages">
            <c:if test="${currentPage>1}">
                <a href="findBills.do?currentPage=${currentPage-1}">上一页</a>
            </c:if>
            <c:forEach begin="1" end="${pageCount}" var="p">
                <c:choose>
                    <c:when test="${p==currentPage}">
                        <a href="findBills.do?currentPage=${p}" class="current_page">${p}</a>
                    </c:when>
                    <c:otherwise>
                        <a href="findBills.do?currentPage=${p}">${p}</a>
                    </c:otherwise>
                </c:choose>
            </c:forEach>
            <c:if test="${currentPage<pageCount}">
                <a href="findBills.do?currentPage=${currentPage+1}">下一页</a>
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
