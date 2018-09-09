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
        <div class="text_info clearfix"><span>账务账号ID：</span></div>
        <div class="input_info"><input type="text" value="${account.accountId}" readonly class="readonly"/></div>
        <div class="text_info clearfix"><span>姓名：</span></div>
        <div class="input_info"><input type="text" value="${account.realName}" readonly class="readonly"/></div>
        <div class="text_info clearfix"><span>身份证：</span></div>
        <div class="input_info">
            <input type="text" value="${account.idCard}" readonly class="readonly"/>
        </div>
        <div class="text_info clearfix"><span>登录账号：</span></div>
        <div class="input_info">
            <input type="text" value="${account.loginName}" readonly class="readonly"/>
        </div>
        <div class="text_info clearfix"><span>电话：</span></div>
        <div class="input_info">
            <input type="text" class="width200 readonly" readonly value="${account.telephone}"/>
        </div>
        <div class="text_info clearfix"><span>推荐人账务账号ID：</span></div>
        <div class="input_info"><input type="text" readonly class="readonly" value="${account.recommenderId}"/></div>
        <div class="text_info clearfix"><span>推荐人身份证号码：</span></div>
        <div class="input_info"><input type="text" readonly class="readonly" value="${account.recommender.idCard}"/></div>
        <div class="text_info clearfix"><span>状态：</span></div>
        <div class="input_info">
            <select disabled>
                <option ${account.status eq "1"?"selected":""}>开通</option>
                <option ${account.status eq "2"?"selected":""}>暂停</option>
                <option ${account.status eq "3"?"selected":""}>删除</option>
            </select>
        </div>
        <div class="text_info clearfix"><span>开通/暂停/删除时间：</span></div>
        <div class="input_info">
            <input type="text" readonly class="readonly"
                   value="${account.status eq "1"?account.createDate:(account.status eq "2"?account.pauseDate:(account.status eq "3"?account.closeDate:""))}"/>
        </div>
        <div class="text_info clearfix"><span>上次登录时间：</span></div>
        <div class="input_info">
            <input type="text" readonly class="readonly" value="${account.lastLoginTime}"/>
        </div>
        <div class="text_info clearfix"><span>上次登录IP：</span></div>
        <div class="input_info">
            <input type="text" readonly class="readonly" value="${account.lastLoginHost}"/>
        </div>
        <!--可选项数据-->
        <div class="text_info clearfix"><span>生日：</span></div>
        <div class="input_info">
            <input type="text" readonly class="readonly" value="${account.birthdate}"/>
        </div>
        <div class="text_info clearfix"><span>Email：</span></div>
        <div class="input_info">
            <input type="text" class="width350 readonly" readonly value="${account.email}"/>
        </div>
        <div class="text_info clearfix"><span>职业：</span></div>
        <div class="input_info">
            <select disabled>
                <option ${account.occupation eq "干部"?"selected":""}>干部</option>
                <option ${account.occupation eq "学生"?"selected":""}>学生</option>
                <option ${account.occupation eq "技术人员"?"selected":""}>技术人员</option>
                <option ${account.occupation eq "其他"?"selected":""}>其他</option>
            </select>
        </div>
        <div class="text_info clearfix"><span>性别：</span></div>
        <div class="input_info fee_type">
            <input type="radio" name="radSex" id="female" disabled  ${account.gender eq "0"?"checked":""}/>
            <label for="female">女</label>
            <input type="radio" name="radSex" id="male" disabled ${account.gender eq "1"?"checked":""}/>
            <label for="male">男</label>
        </div>
        <div class="text_info clearfix"><span>通信地址：</span></div>
        <div class="input_info">
            <input type="text" class="width350 readonly" readonly value="${account.mailAddress}"/>
        </div>
        <div class="text_info clearfix"><span>邮编：</span></div>
        <div class="input_info">
            <input type="text" class="readonly" readonly value="${account.zipcode}"/>
        </div>
        <div class="text_info clearfix"><span>QQ：</span></div>
        <div class="input_info">
            <input type="text" class="readonly" readonly value="${account.QQ}"/>
        </div>
        <!--操作按钮-->
        <div class="button_info clearfix">
            <input type="button" value="返回" class="btn_save" onclick="location.href='findAccounts.do'"/>
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
