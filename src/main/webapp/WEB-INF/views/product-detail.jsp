<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<jsp:include page="layout/header.jsp"><jsp:param name="pageTitle" value="${racket.name}"/></jsp:include>

<section class="product-detail">
    <div class="container">
        <div class="product-detail-grid">
            <div class="product-detail-img">
                <img src="${pageContext.request.contextPath}/images/${racket.image != null ? racket.image : 'default.jpg'}"
                     alt="${racket.name}" onerror="this.src='https://placehold.co/600x500/e9ecef/495057?text=${racket.name}'">
            </div>
            <div class="product-detail-info">
                <c:if test="${racket.brand != null}">
                    <div class="product-brand">${racket.brand.name}</div>
                </c:if>
                <h1>${racket.name}</h1>
                <div class="product-meta">
                    <c:if test="${racket.category != null}"><span><i class="fas fa-tag"></i> ${racket.category.name}</span></c:if>
                    <c:if test="${racket.material != null}"><span><i class="fas fa-layer-group"></i> ${racket.material}</span></c:if>
                    <c:if test="${racket.gender != null}"><span><i class="fas fa-venus-mars"></i> ${racket.gender}</span></c:if>
                    <span class="stars">
                        <c:forEach begin="1" end="5" var="i">
                            <c:choose>
                                <c:when test="${i <= avgRating}"><i class="fas fa-star"></i></c:when>
                                <c:otherwise><i class="far fa-star"></i></c:otherwise>
                            </c:choose>
                        </c:forEach>
                        (<fmt:formatNumber value="${avgRating}" maxFractionDigits="1"/>)
                    </span>
                </div>
                <div class="detail-price">
                    <c:choose>
                        <c:when test="${racket.discount != null && racket.discount > 0}">
                            <span class="price-current"><fmt:formatNumber value="${racket.discountedPrice}" type="number"/> VND</span>
                            <span class="price-original"><fmt:formatNumber value="${racket.price}" type="number"/> VND</span>
                            <span class="badge" style="display:inline-block;position:static;margin-left:8px;">-<fmt:formatNumber value="${racket.discount}" maxFractionDigits="0"/>%</span>
                        </c:when>
                        <c:otherwise>
                            <span class="price-current"><fmt:formatNumber value="${racket.price}" type="number"/> VND</span>
                        </c:otherwise>
                    </c:choose>
                </div>

                <c:if test="${not empty racket.variants}">
                    <div class="variant-selector">
                        <label>Chọn biến thể:</label>
                        <div class="variant-options">
                            <c:forEach var="v" items="${racket.variants}">
                                <div class="variant-option" onclick="selectVariant(this, ${v.id})">
                                    <c:if test="${v.color != null}">${v.color.name}</c:if>
                                    <c:if test="${v.gripSize != null}"> - ${v.gripSize}</c:if>
                                    <c:if test="${v.weight != null}"> (${v.weight}g)</c:if>
                                    <br><small>Tồn kho: ${v.stock}</small>
                                </div>
                            </c:forEach>
                        </div>
                        <input type="hidden" id="selectedVariantId" value="">
                    </div>
                </c:if>

                <div class="quantity-selector">
                    <label>Số lượng:</label>
                    <div class="qty-controls">
                        <button type="button" onclick="changeQty('qty', -1)">-</button>
                        <input type="number" id="qty" value="1" min="1" max="99">
                        <button type="button" onclick="changeQty('qty', 1)">+</button>
                    </div>
                </div>

                <div style="display:flex;gap:12px;">
                    <button class="btn btn-accent btn-lg" onclick="addToCart(document.getElementById('selectedVariantId').value, document.getElementById('qty').value)">
                        <i class="fas fa-cart-plus"></i> Thêm vào giỏ
                    </button>
                </div>

                <div class="product-description">
                    <h3>Mô tả sản phẩm</h3>
                    <p>${racket.description != null ? racket.description : 'Đang cập nhật mô tả...'}</p>
                </div>
            </div>
        </div>

        <!-- Reviews -->
        <div class="section">
            <h2 class="section-title">Đánh giá sản phẩm</h2>
            <div style="margin-top:24px;">
                <c:forEach var="review" items="${reviews}">
                    <div class="review-card">
                        <div class="review-header">
                            <span class="review-author">${review.user != null ? review.user.fullName : 'Ẩn danh'}</span>
                            <span class="review-date">${review.createdAt}</span>
                        </div>
                        <div class="stars">
                            <c:forEach begin="1" end="${review.rating}"><i class="fas fa-star"></i></c:forEach>
                        </div>
                        <p style="margin-top:8px;">${review.comment}</p>
                    </div>
                </c:forEach>
                <c:if test="${empty reviews}">
                    <p style="color:var(--gray-600);">Chưa có đánh giá nào.</p>
                </c:if>
            </div>
        </div>
    </div>
</section>

<jsp:include page="layout/footer.jsp"/>