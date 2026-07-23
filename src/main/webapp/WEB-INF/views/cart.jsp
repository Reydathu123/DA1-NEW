<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<jsp:include page="layout/header.jsp"><jsp:param name="pageTitle" value="Giỏ hàng"/></jsp:include>

<section class="cart-page">
    <div class="container">
        <h2 class="section-title"> Giỏ hàng của bạn </h2>
        <p class="section-subtitle">${cartItems.size()} Sản phẩm </p>

        <c:choose>
            <c:when test="${not empty cartItems}">
                <table class="cart-table">
                    <thead>
                    <tr>
                        <th>Sản phẩm</th><th>Giá</th><th>Số lượng</th><th>Thành tiền</th><th></th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="item" items="${cartItems}">
                        <tr>
                            <td>
                                <div class="cart-item-info">
                                    <img class="cart-item-img"
                                         src="${pageContext.request.contextPath}/images/${item.variant.racket.image != null ? item.variant.racket.image : 'default.jpg'}"
                                         onerror="this.src='https://placehold.co/80x80/e9ecef/495057?text=SP'"
                                         alt="">
                                    <div>
                                        <div class="cart-item-name">${item.variant.racket.name}</div>
                                        <div class="cart-item-variant">
                                            <c:if test="${item.variant.color != null}">${item.variant.color.name}</c:if>
                                            <c:if test="${item.variant.gripSize != null}"> | ${item.variant.gripSize}</c:if>
                                        </div>
                                    </div>
                                </div>
                            </td>
                            <td><fmt:formatNumber value="${item.price}" type="number"/> VND</td>
                            <td>
                                <div class="qty-controls">
                                    <button type="button" onclick="updateCartItem(${item.id}, ${item.quantity - 1})">-</button>
                                    <input type="number" value="${item.quantity}" readonly style="width:50px;">
                                    <button type="button" onclick="updateCartItem(${item.id}, ${item.quantity + 1})">+</button>
                                </div>
                            </td>
                            <td><strong><fmt:formatNumber value="${item.price * item.quantity}" type="number"/> VND</strong></td>
                            <td>
                                <button class="btn btn-danger btn-sm" onclick="removeCartItem(${item.id})">
                                    <i class="fas fa-trash"></i>
                                </button>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>

                <div class="cart-summary" style="max-width:400px;margin-left:auto;">
                    <div class="cart-summary-row cart-summary-total">
                        <span>Tổng cộng :</span>
                        <span><fmt:formatNumber value="${cartTotal}" type="number"/> VND</span>
                    </div>
                    <div style="margin-top:16px;display:flex;gap:12px;">
                        <a href="${pageContext.request.contextPath}/products" class="btn btn-outline" style="flex:1;justify-content:center;">
                            Tiếp tục mua
                        </a>
                        <a href="${pageContext.request.contextPath}/checkout" class="btn btn-accent" style="flex:1;justify-content:center;">
                            Thanh toán <i class="fas fa-arrow-right"></i>
                        </a>
                    </div>
                </div>
            </c:when>
            <c:otherwise>
                <div class="empty-state">
                    <h3><i class="fas fa-shopping-cart" style="font-size:64px;color:var(--gray-300);"></i></h3>
                    <h3>Giỏ hàng trống</h3>
                    <p>Bạn chưa có sản phẩm nào trong giỏ hàng.</p>
                    <a href="${pageContext.request.contextPath}/products" class="btn btn-primary">
                        <i class="fas fa-shopping-bag"></i> Mua sắm ngay
                    </a>
                </div>
            </c:otherwise>
        </c:choose>
    </div>
</section>

<jsp:include page="layout/footer.jsp"/>