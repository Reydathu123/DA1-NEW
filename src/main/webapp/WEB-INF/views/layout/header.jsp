<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>${param.pageTitle != null ? param.pageTitle : 'Shop Vợt Cầu Lông 99'} - DA1 Nhom 9</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css" rel="stylesheet">
</head>
<body>
<header class="header">
    <div class="header-top">
        <div class="container">
            <span><i class="fas fa-phone"></i> Hotline: 0969978084 </span>
            <div>
                <c:choose>
                    <c:when test="${sessionScope.user != null}">
                        <a href="${pageContext.request.contextPath}/orders">
                            <i class="fas fa-user"></i> ${sessionScope.userName}
                        </a>
                        &nbsp;|&nbsp;
                        <c:if test="${sessionScope.user.admin}">
                            <a href="${pageContext.request.contextPath}/admin/dashboard">
                                <i class="fas fa-cog"></i> Quản lý
                            </a>
                            &nbsp;|&nbsp;
                        </c:if>
                        <a href="${pageContext.request.contextPath}/logout">
                            <i class="fas fa-sign-out-alt"></i> Đăng xuất
                        </a>
                    </c:when>
                    <c:otherwise>
                        <a href="${pageContext.request.contextPath}/login">
                            <i class="fas fa-sign-in-alt"></i> Đăng nhập
                        </a>
                        &nbsp;|&nbsp;
                        <a href="${pageContext.request.contextPath}/register">
                            <i class="fas fa-user-plus"></i> Đăng ký
                        </a>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
    </div>
    <div class="header-main">
        <div class="container">
            <a href="${pageContext.request.contextPath}/" class="logo">
                <i class="fas fa-shuttlecock"></i> Vợt cầu lông <span>99</span>
            </a>
            <form class="search-bar" action="${pageContext.request.contextPath}/search" method="get">
                <input type="text" name="keyword" placeholder="Tìm kiếm vợt cầu lông..." value="${keyword}">
                <button type="submit"><i class="fas fa-search"></i></button>
            </form>
            <div class="header-actions">
                <a href="${pageContext.request.contextPath}/cart">
                    <i class="fas fa-shopping-cart"></i> Giỏ hàng
                    <span class="cart-badge" id="cart-count">0</span>
                </a>
            </div>
        </div>
    </div>
    <nav class="nav">
        <div class="container">
            <ul>
                <li><a href="${pageContext.request.contextPath}/">Trang chủ</a></li>
                <li><a href="${pageContext.request.contextPath}/products">Sản phẩm</a></li>
                <c:forEach var="cat" items="${categories}">
                    <li><a href="${pageContext.request.contextPath}/products?categoryId=${cat.id}">${cat.name}</a></li>
                </c:forEach>
            </ul>
        </div>
    </nav>
</header>
<script>var contextPath = '${pageContext.request.contextPath}';</script>