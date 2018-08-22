<%@ page import="com.zwy.work.entity.Cost" %>
<%@ page import="java.util.List" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>达内－NetCTOSS</title>
    <link type="text/css" rel="stylesheet" media="all" href="styles/global.css"/>
    <link type="text/css" rel="stylesheet" media="all" href="styles/global_color.css"/>
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
    <form action="" method="" class="main_form">
        <%
            Cost c = (Cost) request.getAttribute("cost");
        %>
        <div class="text_info clearfix"><span>资费ID：</span></div>
        <div class="input_info"><input type="text" class="readonly" readonly value="<%= c.getCostId() %>"/></div>
        <div class="text_info clearfix"><span>资费名称：</span></div>
        <div class="input_info"><input type="text" class="readonly" readonly value="<%= c.getName() %>"/></div>
        <div class="text_info clearfix"><span>资费状态：</span></div>
        <div class="input_info">
            <select class="readonly" disabled>
                <option><%= c.getStatus().equals("1") ? "暂停" : "开通" %>
                </option>
            </select>
        </div>
        <div class="text_info clearfix"><span>资费类型：</span></div>
        <div class="input_info fee_type">
            <input type="radio" name="radFeeType" <%= c.getCostType().equals("1") ? "checked" : "" %> id="monthly"
                   disabled="disabled"/>
            <label for="monthly">包月</label>
            <input type="radio" name="radFeeType" <%= c.getCostType().equals("2") ? "checked" : "" %> id="package"
                   disabled="disabled"/>
            <label for="package">套餐</label>
            <input type="radio" name="radFeeType" <%= c.getCostType().equals("3") ? "checked" : "" %> id="timeBased"
                   disabled="disabled"/>
            <label for="timeBased">计时</label>
        </div>
        <div class="text_info clearfix"><span>基本时长：</span></div>
        <div class="input_info">
            <input type="text" class="readonly" readonly value="<%= c.getBaseDuration() %>"/>
            <span>小时</span>
        </div>
        <div class="text_info clearfix"><span>基本费用：</span></div>
        <div class="input_info">
            <input type="text" class="readonly" readonly value="<%= c.getBaseCost() %>"/>
            <span>元</span>
        </div>
        <div class="text_info clearfix"><span>单位费用：</span></div>
        <div class="input_info">
            <input type="text" class="readonly" readonly value="<%= c.getUnitCost() %>"/>
            <span>元/小时</span>
        </div>
        <div class="text_info clearfix"><span>创建时间：</span></div>
        <div class="input_info"><input type="text" class="readonly" readonly value="<%= c.getCreatime() %>"/></div>
        <div class="text_info clearfix"><span>启动时间：</span></div>
        <div class="input_info"><input type="text" class="readonly" readonly value="<%= c.getStartime() %>"/></div>
        <div class="text_info clearfix"><span>资费说明：</span></div>
        <div class="input_info_high">
            <textarea class="width300 height70 readonly" readonly><%= c.getDescr() %></textarea>
        </div>
        <div class="button_info clearfix">
            <input type="button" value="返回" class="btn_save" onclick="location.href='findCosts.do';"/>
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
