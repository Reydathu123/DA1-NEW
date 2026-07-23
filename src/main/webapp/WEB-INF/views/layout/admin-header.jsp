<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>${param.pageTitle} - Admin Panel</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css" rel="stylesheet">
</head>
<body>
<div class="admin-layout">
    <aside class="admin-sidebar">
        <div class="logo"><i class="fas fa-shuttlecock"></i> Admin<span>Panel</span></div>
        <nav class="admin-menu">
            <a href="${pageContext.request.contextPath}/admin/dashboard" class="${param.menu == 'dashboard' ? 'active' : ''}">
                <i class="fas fa-tachometer-alt"></i> Dashboard
            </a>
            <a href="${pageContext.request.contextPath}/admin/categories" class="${param.menu == 'categories' ? 'active' : ''}">
                <i class="fas fa-list"></i> Danh mục
            </a>
            <a href="${pageContext.request.contextPath}/admin/brands" class="${param.menu == 'brands' ? 'active' : ''}">
                <i class="fas fa-tags"></i> Thương hiệu
            </a>
            <a href="${pageContext.request.contextPath}/admin/rackets" class="${param.menu == 'rackets' ? 'active' : ''}">
                <i class="fas fa-table-tennis"></i> Sản phẩm
            </a>
            <a href="${pageContext.request.contextPath}/admin/variants" class="${param.menu == 'variants' ? 'active' : ''}">
                <i class="fas fa-th"></i> Biến thể
            </a>
            <a href="${pageContext.request.contextPath}/admin/orders" class="${param.menu == 'orders' ? 'active' : ''}">
                <i class="fas fa-shopping-bag"></i> Đơn hàng
            </a>
            <a href="${pageContext.request.contextPath}/" style="margin-top:32px;border-top:1px solid rgba(255,255,255,0.1);padding-top:20px;">
                <i class="fas fa-home"></i> Trang chủ
            </a>
            <a href="${pageContext.request.contextPath}/logout">
                <i class="fas fa-sign-out-alt"></i> Đăng xuất
            </a>
        </nav>
    </aside>
    <main class="admin-content">
        <script>var contextPath = '${pageContext.request.contextPath}';</script>