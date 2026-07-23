<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<jsp:include page="layout/header.jsp"><jsp:param name="pageTitle" value="Lịch sử đơn hàng"/></jsp:include>

<section class="section">
    <div class="container">
        <h2 class="section-title">Lịch sử đơn hàng</h2>
        <p class="section-subtitle">${orders.size()} đơn hàng</p>

        <c:choose>
            <c:when test="${not empty orders}">
                <c:forEach var="order" items="${orders}">
                    <div class="order-card">
                        <div class="order-header">
                            <span class="order-code">#${order.code}</span>
                            <span class="status-badge status-${order.status}">${order.status}</span>
                        </div>
                        <div style="display:flex;justify-content:space-between;flex-wrap:wrap;gap:12px;">
                            <div>
                                <p><strong>Ngày đặt:</strong> ${order.createdAt}</p>
                                <p><strong>Địa chỉ:</strong> ${order.shippingAddress}</p>
                                <p><strong>Thanh toán:</strong> ${order.paymentMethod}</p>
                            </div>
                            <div style="text-align:right;">
                                <p style="font-size:24px;font-weight:700;color:var(--danger);">
                                    <fmt:formatNumber value="${order.total}" type="number"/> VND
                                </p>
                                <a href="${pageContext.request.contextPath}/order?id=${order.id}" class="btn btn-outline btn-sm" style="margin-top:8px;">
                                    <i class="fas fa-eye"></i> Xem chi tiết
                                </a>
                            </div>
                        </div>
                    </div>
                </c:forEach>
            </c:when>
            <c:otherwise>
                <div class="empty-state">
                    <h3>Chưa có đơn hàng</h3>
                    <p>Bạn chưa đặt đơn hàng nào.</p>
                    <a href="${pageContext.request.contextPath}/products" class="btn btn-primary">Mua sắm ngay</a>
                </div>
            </c:otherwise>
        </c:choose>
    </div>
</section>

<jsp:include page="layout/footer.jsp"/>