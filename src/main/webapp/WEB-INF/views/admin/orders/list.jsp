<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<jsp:include page="../../layout/admin-header.jsp"><jsp:param name="pageTitle" value="Đơn Hàng"/><jsp:param name="menu" value="orders"/></jsp:include>

<div class="admin-header">
    <h1><i class="fas fa-shopping-bag"></i> Quản lý đơn hàng</h1>
</div>

<form action="${pageContext.request.contextPath}/admin/orders" method="get" style="display:flex;gap:12px;margin-bottom:24px;">
    <select name="status" class="form-control" style="max-width:200px;">
        <option value="">-- Tất cả trạng thái --</option>
        <option value="pending" ${statusFilter == 'pending' ? 'selected' : ''}>Chưa xử lý (Pending)</option>
        <option value="confirmed" ${statusFilter == 'confirmed' ? 'selected' : ''}>Đã xác nhận (Confirmed)</option>
        <option value="shipping" ${statusFilter == 'shipping' ? 'selected' : ''}>Đang giao hàng (Shipping)</option>
        <option value="delivered" ${statusFilter == 'delivered' ? 'selected' : ''}>Đã giao hàng (Delivered)</option>
        <option value="cancelled" ${statusFilter == 'cancelled' ? 'selected' : ''}>Đã hủy (Cancelled)</option>
    </select>
    <button type="submit" class="btn btn-primary btn-sm"><i class="fas fa-filter"></i> Lọc</button>
    <a href="${pageContext.request.contextPath}/admin/orders" class="btn btn-outline btn-sm">Xóa bộ lọc</a>
</form>

<table class="admin-table">
    <thead>
    <tr>
        <th>Code</th>
        <th>Khách hàng</th>
        <th>Ngày đặt</th>
        <th>Tổng tiền</th>
        <th>Phương thức thanh toán</th>
        <th>Trạng thái</th>
        <th>Thao tác</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="o" items="${orders}">
        <tr>
            <td><strong>#${o.code}</strong></td>
            <td>
                    ${o.user != null ? o.user.fullName : 'N/A'}<br>
                <small>${o.user != null ? o.user.phone : ''}</small>
            </td>
            <td>${o.createdAt}</td>
            <td><strong style="color:var(--danger)"><fmt:formatNumber value="${o.total}" type="number"/> VND</strong></td>
            <td>${o.paymentMethod}</td>
            <td><span class="status-badge status-${o.status}">${o.status}</span></td>
            <td class="actions">
                <a href="${pageContext.request.contextPath}/admin/orders/detail?id=${o.id}" class="btn btn-primary btn-sm"><i class="fas fa-eye"></i> Chi tiết</a>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>

<jsp:include page="../../layout/admin-footer.jsp"/>