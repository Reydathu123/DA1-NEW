<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<jsp:include page="layout/header.jsp"><jsp:param name="pageTitle" value="Trang chủ"/></jsp:include>

<!-- Hero Banner -->
<section class="hero">
    <div class="container">
        <div class="hero-content">
            <h1>Vợt Cầu Lông <span>Chính Hãng</span></h1>
            <p>Khám phá bộ sưu tập vợt cầu lông từ các thương hiệu hàng đầu thế giới với giá tốt nhất.</p>
            <a href="${pageContext.request.contextPath}/products" class="btn btn-accent btn-lg">
                <i class="fas fa-shopping-bag"></i> Mua ngay
            </a>
        </div>
    </div>
</section>

<!-- Sản phẩm mới -->
<section class="section">
    <div class="container">
        <div class="section-header">
            <h2 class="section-title">Sản Phẩm Mới Nhất</h2>
            <p class="section-subtitle">Những sản phẩm vợt cầu lông mới nhất vừa cập nhật</p>
        </div>
        <div class="product-grid">
            <c:forEach var="racket" items="${latestRackets}">
                <div class="product-card">
                    <c:if test="${racket.discount != null && racket.discount > 0}">
                        <span class="badge">-<fmt:formatNumber value="${racket.discount}" maxFractionDigits="0"/>%</span>
                    </c:if>
                    <a href="${pageContext.request.contextPath}/product?id=${racket.id}">
                        <div class="product-img">
                            <img src="${pageContext.request.contextPath}/images/${racket.image != null ? racket.image : 'default.jpg'}"
                                 alt="${racket.name}" onerror="this.src='https://placehold.co/300x260/e9ecef/495057?text=Racket'">
                        </div>
                    </a>
                    <div class="product-info">
                        <c:if test="${racket.brand != null}">
                            <div class="product-brand">${racket.brand.name}</div>
                        </c:if>
                        <h3 class="product-name">
                            <a href="${pageContext.request.contextPath}/product?id=${racket.id}">${racket.name}</a>
                        </h3>
                        <div class="product-price">
                            <c:choose>
                                <c:when test="${racket.discount != null && racket.discount > 0}">
                                    <span class="price-current"><fmt:formatNumber value="${racket.discountedPrice}" type="number"/> VND</span>
                                    <span class="price-original"><fmt:formatNumber value="${racket.price}" type="number"/> VND</span>
                                </c:when>
                                <c:otherwise>
                                    <span class="price-current"><fmt:formatNumber value="${racket.price}" type="number"/> VND</span>
                                </c:otherwise>
                            </c:choose>
                        </div>
                        <div class="product-actions">
                            <a href="${pageContext.request.contextPath}/product?id=${racket.id}" class="btn btn-outline btn-sm">
                                <i class="fas fa-eye"></i> Chi tiết
                            </a>
                        </div>
                    </div>
                </div>
            </c:forEach>
        </div>
        <c:if test="${empty latestRackets}">
            <div class="empty-state">
                <h3>Chưa có sản phẩm nào</h3>
                <p>Hãy quay lại sau!</p>
            </div>
        </c:if>
    </div>
</section>

<!-- Sản phẩm giảm giá -->
<c:if test="${not empty discountedRackets}">
    <section class="section" style="background:var(--white);">
        <div class="container">
            <div class="section-header">
                <h2 class="section-title">Khuyến Mãi Hot</h2>
                <p class="section-subtitle">Sản phẩm đang được giảm giá đặc biệt</p>
            </div>
            <div class="product-grid">
                <c:forEach var="racket" items="${discountedRackets}" end="3">
                    <div class="product-card">
                        <span class="badge">-<fmt:formatNumber value="${racket.discount}" maxFractionDigits="0"/>%</span>
                        <a href="${pageContext.request.contextPath}/product?id=${racket.id}">
                            <div class="product-img">
                                <img src="${pageContext.request.contextPath}/images/${racket.image != null ? racket.image : 'default.jpg'}"
                                     alt="${racket.name}" onerror="this.src='https://placehold.co/300x260/e9ecef/495057?text=Sale'">
                            </div>
                        </a>
                        <div class="product-info">
                            <c:if test="${racket.brand != null}">
                                <div class="product-brand">${racket.brand.name}</div>
                            </c:if>
                            <h3 class="product-name">
                                <a href="${pageContext.request.contextPath}/product?id=${racket.id}">${racket.name}</a>
                            </h3>
                            <div class="product-price">
                                <span class="price-current"><fmt:formatNumber value="${racket.discountedPrice}" type="number"/> VND</span>
                                <span class="price-original"><fmt:formatNumber value="${racket.price}" type="number"/> VND</span>
                            </div>
                        </div>
                    </div>
                </c:forEach>
            </div>
        </div>
    </section>
</c:if>

<jsp:include page="layout/footer.jsp"/>