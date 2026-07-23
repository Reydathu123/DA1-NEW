<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<jsp:include page="layout/header.jsp"><jsp:param name="pageTitle" value="Thanh toán"/></jsp:include>

<section class="section">
    <div class="container">
        <h2 class="section-title">Thanh toán</h2>
        <c:if test="${not empty error}">
            <div class="alert alert-danger">${error}</div>
        </c:if>
        <form action="${pageContext.request.contextPath}/checkout" method="post" id="checkoutForm">
            <div class="checkout-grid">
                <div>
                    <div class="admin-form-card" style="max-width:100%;">
                        <h3 style="margin-bottom:20px;">Thông tin giao hàng</h3>
                        <div class="form-group">
                            <label>Họ và tên</label>
                            <input type="text" class="form-control" value="${user.fullName}" readonly>
                        </div>
                        <div class="form-group">
                            <label>Email</label>
                            <input type="text" class="form-control" value="${user.email}" readonly>
                        </div>
                        <div class="form-group">
                            <label>Số điện thoại</label>
                            <input type="text" class="form-control" value="${user.phone}" readonly>
                        </div>
                        <div class="form-group">
                            <label>Địa chỉ giao hàng *</label>
                            <textarea name="shippingAddress" class="form-control" required>${user.address}</textarea>
                        </div>
                        <div class="form-group">
                            <label>Phương thức thanh toán</label>
                            <select name="paymentMethod" class="form-control">
                                <option value="COD">Thanh toán khi nhận hàng (COD)</option>
                                <option value="Banking">Chuyển khoản ngân hàng</option>
                            </select>
                        </div>
                    </div>
                </div>
                <div>
                    <div class="cart-summary">
                        <h3 style="margin-bottom:16px;">Đơn hàng của bạn</h3>
                        <c:forEach var="item" items="${cartItems}">
                            <div class="cart-summary-row">
                                <span>${item.variant.racket.name} x${item.quantity}</span>
                                <span><fmt:formatNumber value="${item.price * item.quantity}" type="number"/> VND</span>
                            </div>
                        </c:forEach>
                        <div class="cart-summary-row" style="border-top:2px solid var(--gray-200);margin-top:12px;padding-top:16px;">
                            <span>Phí vận chuyển:</span>
                            <span style="color:var(--success);font-weight:600;">Miễn phí</span>
                        </div>
                        <div class="cart-summary-row cart-summary-total">
                            <span>Tổng cộng:</span>
                            <span><fmt:formatNumber value="${cartTotal}" type="number"/> VND</span>
                        </div>
                        <button type="submit" class="btn btn-accent btn-lg" style="width:100%;justify-content:center;margin-top:16px;">
                            <i class="fas fa-check-circle"></i> Đặt hàng
                        </button>
                    </div>
                </div>
            </div>
        </form>
    </div>
</section>

<jsp:include page="layout/footer.jsp"/>