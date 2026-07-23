<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<jsp:include page="../layout/admin-header.jsp"><jsp:param name="pageTitle" value="Dashboard"/><jsp:param name="menu" value="dashboard"/></jsp:include>

<div class="admin-header">
    <h1><i class="fas fa-tachometer-alt"></i> Dashboard</h1>
    <span>Xin chào, ${sessionScope.userName}!</span>
</div>

<div class="stats-grid">
    <div class="stat-card">
        <h3><i class="fas fa-table-tennis"></i> Tổng sản phẩm</h3>
        <div class="stat-value">${totalProducts}</div>
    </div>
    <div class="stat-card">
        <h3><i class="fas fa-shopping-bag"></i> Tổng đơn hàng</h3>
        <div class="stat-value">${totalOrders}</div>
    </div>
    <div class="stat-card">
        <h3><i class="fas fa-users"></i> Tổng người dùng</h3>
        <div class="stat-value">${totalUsers}</div>
    </div>
    <div class="stat-card">
        <h3><i class="fas fa-money-bill-wave"></i> Doanh thu</h3>
        <div class="stat-value"><fmt:formatNumber value="${totalRevenue}" type="number"/> VND</div>
    </div>
</div>

<div class="stats-grid" style="grid-template-columns:1fr 1fr;">
    <div class="stat-card" style="border-left-color:var(--warning);">
        <h3><i class="fas fa-clock"></i> Đơn chờ xử lý</h3>
        <div class="stat-value">${pendingOrders}</div>
        <a href="${pageContext.request.contextPath}/admin/orders?status=pending" style="color:var(--primary);font-size:14px;">Xem chi tiết &rarr;</a>
    </div>
    <div class="stat-card" style="border-left-color:var(--primary);">
        <h3><i class="fas fa-truck"></i> Đang giao hàng</h3>
        <div class="stat-value">${shippingOrders}</div>
        <a href="${pageContext.request.contextPath}/admin/orders?status=shipping" style="color:var(--primary);font-size:14px;">Xem chi tiết &rarr;</a>
    </div>
</div>

<jsp:include page="../layout/admin-footer.jsp"/>