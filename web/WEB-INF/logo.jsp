<%@ page language="java" pageEncoding="UTF-8" %>
<img src="images/logo.png" alt="logo" class="left"/>
<!-- cookie.key.value -->
<%-- <span>${cookie.adminCode.value}</span> --%>
<span>${sessionScope.adminCode}</span>

<a href="/netctoss/toLogin.do">[退出]</a>
