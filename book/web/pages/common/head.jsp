<%--
  Created by IntelliJ IDEA.
  User: Lenovo
  Date: 2023/2/6
  Time: 18:03
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%--静态包含头部信息--%>
    <%--形如<%@include file="/路径"%>--%>

<%
    String basePath = request.getScheme()
            + "://"
            + request.getServerName()
            + ":"
            + request.getServerPort()
            + request.getContextPath()
            + "/";
%>

<!--写base永远固定相对路径跳转的结果-->
<base href="<%=basePath%>">

<link type="text/css" rel="stylesheet" href="static/css/style.css" >
<script type="text/javascript" src="static/jquery-1.7.2.js"></script>
