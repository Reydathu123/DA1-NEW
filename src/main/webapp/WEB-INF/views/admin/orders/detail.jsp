<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<jsp:include page="../../layout/admin-header.jsp"><jsp:param name="pageTitle" value="Chi Tiết Đơn Hàng"/><jsp:param name="menu" value="orders"/></jsp:include>

<div class="admin-header">
    <h1><i class="fas fa-eye"></i> Chi tiết đơn hàng #${order.code}</h1>
    <a href="${pageContext.request.contextPath}/admin/orders" class="btn btn-outline"><i class="fas fa-arrow-left"></i> Quay lại</a>
</div>

<c:if test="${param.success == 'status'}">
    <div class="alert alert-success">Cập nhật trạng thái thành công!</div>
</c:if>

<div style="display:grid; grid-template-columns: 2fr 1fr; gap:24px; align-items: start;">
    <div>
        <div class="order-card" style="margin-bottom:24px;">
            <h3>Thông tin khách hàng</h3>
            <p style="margin-top:12px;"><strong>Họ tên:</strong> ${order.user != null ? order.user.fullName : 'Không xác định'}</p>
            <p><strong>Email:</strong> ${order.user != null ? order.user.email : 'N/A'}</p>
            <p><strong>Số điện thoại:</strong> ${order.user != null ? order.user.phone : 'N/A'}</p>
            <p><strong>Địa chỉ giao hàng:</strong> ${order.shippingAddress}</p>
        </div>

        <table class="admin-table">
            <thead>
            <tr>
                <th>Sản phẩm</th>
                <th>Đơn giá</th>
                <th>Số lượng</th>
                <th>Thành tiền</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="od" items="${orderDetails}">
                <tr>
                    <td>
                            ${od.variant.racket.name}
                        <c:if test="${od.variant.color != null}"> - ${od.variant.color.name}</c:if>
                    </td>
                    <td><fmt:formatNumber value="${od.unitPrice}" type="number"/> VND</td>
                    <td>${od.quantity}</td>
                    <td><strong><fmt:formatNumber value="${od.unitPrice * od.quantity}" type="number"/> VND</strong></td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>

    <div>
        <div class="cart-summary" style="margin-top:0;">
            <h3>Trạng thái thanh toán</h3>
            <div class="cart-summary-row" style="margin-top:12px;">
                <span>Phương thức:</span>
                <span><strong>${order.paymentMethod}</strong></span>
            </div>
            <div class="cart-summary-row">
                <span>Ngày đặt:</span>
                <span>${order.createdAt}</span>
            </div>
            <div class="cart-summary-row">
                <span>Tổng cộng:</span>
                <span style="color:var(--danger);font-weight:700;"><fmt:formatNumber value="${order.total}" type="number"/> VND</span>
            </div>
            <div class="cart-summary-row">
                <span>Trạng thái hiện tại:</span>
                <span class="status-badge status-${order.status}">${order.status}</span>
            </div>


            <form action="${pageContext.request.contextPath}/admin/orders/status" method="post" style="margin-top:20px;">
                <input type="hidden" name="orderId" value="${order.id}">
                <div class="form-group">
                    <label>Cập nhật trạng thái:</label>
                    <select name="status" class="form-control">
                        <option value="pending" ${order.status == 'pending' ? 'selected' : ''}>Chưa xử lý (Pending)</option>
                        <option value="confirmed" ${order.status == 'confirmed' ? 'selected' : ''}>Đã xác nhận (Confirmed)</option>
                        <option value="shipping" ${order.status == 'shipping' ? 'selected' : ''}>Đang giao hàng (Shipping)</option>
                        <option value="delivered" ${order.status == 'delivered' ? 'selected' : ''}>Đã giao hàng (Delivered)</option>
                        <option value="cancelled" ${order.status == 'cancelled' ? 'selected' : ''}>Đã hủy (Cancelled)</option>
                    </select>
                </div>
                <button type="submit" class="btn btn-accent btn-sm" style="width:100%; justify-content:center;">
                    <i class="fas fa-sync-alt"></i> Cập nhật trạng thái
                </button>
            </form>
        </div>
    </div>
</div>

<jsp:include page="../../layout/admin-footer.jsp"/>