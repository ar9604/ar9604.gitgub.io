<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>购物车</title>
	<%--静态包base标签，css样式，jQuery文件--%>
	<%@include file="/pages/common/head.jsp"%>
	<script type="text/javascript">
		$(function (){
			//给删除绑定单机事件
			$("a.deleteItem").click(function (){
				return confirm("你确定要删除" + $(this).parent().parent().find("td:first").text() +"吗")
			});
			//给清空购物车绑定单机事件
			$("#clearCart").click(function (){
				return confirm("你确定要清空购物车吗")
			});
			//给输入框绑定change事件
			$(".updateCount").change(function (){
				//获取商品名称
				var name = $(this).parent().parent().find("td:first").text();
				//获取商品数量
				var count = $(this).val();
				//获取商品id
				var id = $(this).attr("bookId");
				var bookStock = $(this).attr("bookStock");

				if (bookStock > count) {
					if (confirm("你确定要将" + name + "修改为: [ " + count + " ] 吗")) {
						//发起请求给服务器保存修改

						location.href = "cartServlet?action=updateCount&count=" + count + "&id=" + id;
					} else {
						//defaultValue属性是表单项dom对象的属性，它表示默认的value属性值
						this.value = this.defaultValue;
					}
				}else {
					if (confirm(name + "由于库存原因，最多只能修改为 [ " + bookStock + " ] 你确定要修改吗")) {
						//发起请求给服务器保存修改
						location.href = "cartServlet?action=updateCount&count=" + bookStock + "&id=" + id;
					} else {
						//defaultValue属性是表单项dom对象的属性，它表示默认的value属性值
						this.value = this.defaultValue;
					}
				}
			});

		});
	</script>
</head>
<body>
	
	<div id="header">
			<img class="logo_img" alt="" src="static/img/logo.gif" >
			<span class="wel_word">购物车</span>
		<%--静态包含 登录成功后 的菜单--%>
		<%@include file="/pages/common/login_sucess_menu.jsp"%>
	</div>
	
	<div id="main">

		<table>
			<tr>
				<td>商品名称</td>
				<td>数量</td>
				<td>库存</td>
				<td>单价</td>
				<td>金额</td>
				<td>操作</td>
			</tr>
			<c:if test="${empty sessionScope.cart.items}">
				<%--如果购物车为空的情况--%>
					<tr>
						<td colspan="5"><a href="index.jsp">
							亲！当前购物车为空...快跟小伙伴们去浏览商品吧...
						</a></td>
					</tr>
			</c:if>
			<c:if test="${not empty sessionScope.cart.items}">
				<%--如果购物车为非空的情况--%>
				<c:forEach items="${sessionScope.cart.items}" var="entry">
					<tr>
						<td>${entry.value.name}</td>
						<td>
						<input class="updateCount" style="width: 70px"
							   bookId="${entry.value.id}"
							   bookStock="${entry.value.bookStock}"
							   type="text" value="${entry.value.count}">
						</td>
						<td>${entry.value.bookStock}</td>
						<td>${entry.value.price}</td>
						<td>${entry.value.totalPrice}</td>
						<td><a class="deleteItem" href="cartServlet?action=deleteItem&id=${entry.value.id}" >删除</a></td>
					</tr>
				</c:forEach>
			</c:if>



			

			
		</table>
		<%--如果购物车非空才输出下面的内容--%>
		<c:if test="${not empty sessionScope.cart.items}">
		<div class="cart_info">
			<span class="cart_span">购物车中共有<span class="b_count">${sessionScope.cart.totalCount}</span>件商品</span>
			<span class="cart_span">总金额<span class="b_price">${sessionScope.cart.totalPrice}</span>元</span>
			<span class="cart_span"><a id="clearCart" href="cartServlet?action=clear">清空购物车</a></span>
			<span class="cart_span"><a href="orderServlet?action=createOrder">去结账</a></span>
		</div>
		</c:if>
	</div>

	<%--静态包含页脚内容--%>
	<%@include file="/pages/common/fooster.jsp"%>
</body>
</html>