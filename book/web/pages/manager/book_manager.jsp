<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>图书管理</title>
    <%--静态包base标签，css样式，jQuery文件--%>
    <%@include file="/pages/common/head.jsp" %>
    <script type="text/javascript">
        $(function () {
            //给删除的a标签绑定删除事件，用于删除的确认提示操作
            $("a.deleteClass").click(function () {
                //在事件的function函数中有一个this对象，这个this对象，是当前正在相应的dom对象
                /**
                 * conform是确认提示框函数
                 * 参数是他的提示框内容
                 * 他有两个按钮，一个去确认，一个是取消
                 * 返回true表示点击确认，fa'lse表示取消
                 */
                return confirm("你确定要删除[" + $(this).parent().parent().find("td:first").text() + "]?");
                // return false;//阻止元素的默认行为==不提交
            })


        });
    </script>
</head>
<body>

<div id="header">
    <img class="logo_img" alt="" src="static/img/logo.gif">
    <span class="wel_word">图书管理系统</span>
    <%--静态包含manager管理模块内容--%>
    <%@include file="/pages/common/manager_menu.jsp" %>

</div>

<div id="main">
    <table>
        <tr>
            <td>名称</td>
            <td>价格</td>
            <td>作者</td>
            <td>销量</td>
            <td>库存</td>
            <td colspan="2">操作</td>
        </tr>

        <c:forEach items="${requestScope.page.items}" var="book">
            <tr>
                <td>${book.name}</td>
                <td>${book.price}</td>
                <td>${book.author}</td>
                <td>${book.sales}</td>
                <td>${book.stock}</td>
                <td><a href="manager/bookServlet?action=getBook&id=${book.id}&method=update&pageNo=${requestScope.page.pageNo}">修改</a></td>
                <td><a class="deleteClass" href="manager/bookServlet?action=delete&id=${book.id}&pageNo=${requestScope.page.pageNo}">删除</a></td>
            </tr>
        </c:forEach>


        <tr>
            <td></td>
            <td></td>
            <td></td>
            <td></td>
            <td></td>
            <td></td>
            <td><a href="pages/manager/book_edit.jsp?&method=add&pageNo=${requestScope.page.pageTotal}">添加图书</a></td>
        </tr>
    </table>
    <br/>

    <%--静态包含分页条--%>
  <%@include file="/pages/common/page_nav.jsp"%>



</div>

<%--静态包含页脚内容--%>
<%@include file="/pages/common/fooster.jsp" %>
</body>
</html>