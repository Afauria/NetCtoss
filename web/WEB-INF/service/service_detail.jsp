<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
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
        <!--必填项-->
        <div class="text_info clearfix"><span>业务账号ID：</span></div>
        <div class="input_info"><input type="text" value="${service.serviceId}" readonly class="readonly"/></div>
        <div class="text_info clearfix"><span>账务账号ID：</span></div>
        <div class="input_info"><input type="text" value="${service.accountId}" readonly class="readonly"/></div>
        <div class="text_info clearfix"><span>客户姓名：</span></div>
        <div class="input_info"><input type="text" readonly class="readonly" value="${service.account.realName}"/></div>
        <div class="text_info clearfix"><span>身份证号码：</span></div>
        <div class="input_info"><input type="text" readonly class="readonly" value="${service.account.idCard}"/></div>
        <div class="text_info clearfix"><span>服务器 IP：</span></div>
        <div class="input_info"><input type="text" value="${service.unixHost}" readonly class="readonly"/></div>
        <div class="text_info clearfix"><span>OS 账号：</span></div>
        <div class="input_info"><input type="text" value="${service.osUsername}" readonly class="readonly"/></div>
        <div class="text_info clearfix"><span>状态：</span></div>
        <div class="input_info">
            <select disabled>
                <option ${service.status eq "1"?"selected":""}>开通</option>
                <option ${service.status eq "2"?"selected":""}>暂停</option>
                <option ${service.status eq "3"?"selected":""}>删除</option>
            </select>
        </div>
        <div class="text_info clearfix"><span>开通时间：</span></div>
        <div class="input_info"><input type="text" readonly class="readonly" value="${service.createDate}"/></div>
        <div class="text_info clearfix"><span>资费 ID：</span></div>
        <div class="input_info"><input type="text" class="readonly" readonly value="${service.costId}"/></div>
        <div class="text_info clearfix"><span>资费名称：</span></div>
        <div class="input_info"><input type="text" readonly class="width200 readonly" value="${service.cost.name}"/></div>
        <div class="text_info clearfix"><span>资费说明：</span></div>
        <div class="input_info_high">
            <textarea class="width300 height70 readonly" readonly>${service.cost.descr} </textarea>
        </div>
        <!--操作按钮-->
        <div class="button_info clearfix">
            <input type="button" value="返回" class="btn_save" onclick="location.href='findServices.do'"/>
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
