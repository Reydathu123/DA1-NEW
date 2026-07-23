<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<jsp:include page="layout/header.jsp"><jsp:param name="pageTitle" value="Chi tiết đơn hàng"/></jsp:include>

<section class="section">
    <div class="container">
        <c:if test="${param.success == 'true'}">
            <div class="alert alert-success"><i class="fas fa-check-circle"></i> Đặt hàng thành công! Mã đơn hàng: <strong>#${order.code}</strong></div>
        </c:if>

        <h2 class="section-title">Đơn hàng #${order.code}</h2>
        <div style="margin-top:24px;">
            <div class="order-card">
                <div class="order-header">
                    <span class="order-code">#${order.code}</span>
                    <span class="status-badge status-${order.status}">${order.status}</span>
                </div>
                <div style="display:grid;grid-template-columns:1fr 1fr;gap:20px;margin-top:16px;">
                    <div>
                        <p><strong>Ngày đặt:</strong> ${order.createdAt}</p>
                        <p><strong>Địa chỉ:</strong> ${order.shippingAddress}</p>
                        <p><strong>Thanh toán:</strong> ${order.paymentMethod}</p>
                    </div>
                    <div>
                        <p><strong>Ngày gửi:</strong> ${order.shippingDate != null ? order.shippingDate : 'Chưa gửi'}</p>
                        <p><strong>Ngày nhận:</strong> ${order.deliveryDate != null ? order.deliveryDate : 'Chưa nhận'}</p>
                    </div>
                </div>
            </div>

            <table class="cart-table" style="margin-top:24px;">
                <thead><tr><th>Sản phẩm</th><th>Đơn giá</th><th>SL</th><th>Thành tiền</th></tr></thead>
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
            <div class="cart-summary" style="max-width:400px;margin-left:auto;margin-top:16px;">
                <div class="cart-summary-row cart-summary-total">
                    <span>Tổng cộng:</span>
                    <span><fmt:formatNumber value="${order.total}" type="number"/> VND</span>
                </div>
            </div>
        </div>
        <a href="${pageContext.request.contextPath}/orders" class="btn btn-outline" style="margin-top:24px;">
            <i class="fas fa-arrow-left"></i> Quay lại
        </a>
    </div>
</section>

<jsp:include page="layout/footer.jsp"/>