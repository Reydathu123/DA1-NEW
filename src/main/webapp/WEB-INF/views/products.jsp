<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<jsp:include page="layout/header.jsp"><jsp:param name="pageTitle" value="Sản phẩm"/></jsp:include>

<section class="section">
    <div class="container">
        <h2 class="section-title">
            <c:choose>
                <c:when test="${keyword != null}">Kết quả tìm kiếm: "${keyword}"</c:when>
                <c:otherwise>Tất cả sản phẩm</c:otherwise>
            </c:choose>
        </h2>
        <p class="section-subtitle">${rackets.size()} sản phẩm</p>

        <!-- Filter -->
        <form action="${pageContext.request.contextPath}/products" method="get"
              style="display:flex;gap:12px;flex-wrap:wrap;margin-bottom:32px;align-items:end;">
            <div class="form-group" style="margin-bottom:0;">
                <label>Danh mục</label>
                <select name="categoryId" class="form-control" style="min-width:160px;">
                    <option value="">-- Tất cả --</option>
                    <c:forEach var="cat" items="${categories}">
                        <option value="${cat.id}" ${selectedCategoryId == cat.id ? 'selected' : ''}>${cat.name}</option>
                    </c:forEach>
                </select>
            </div>
            <div class="form-group" style="margin-bottom:0;">
                <label>Thương hiệu</label>
                <select name="brandId" class="form-control" style="min-width:160px;">
                    <option value="">-- Tất cả --</option>
                    <c:forEach var="brand" items="${brands}">
                        <option value="${brand.id}" ${selectedBrandId == brand.id ? 'selected' : ''}>${brand.name}</option>
                    </c:forEach>
                </select>
            </div>
            <div class="form-group" style="margin-bottom:0;">
                <label>Giới tính</label>
                <select name="gender" class="form-control" style="min-width:140px;">
                    <option value="">-- Tất cả --</option>
                    <option value="Unisex" ${selectedGender == 'Unisex' ? 'selected' : ''}>Unisex</option>
                    <option value="Nam" ${selectedGender == 'Nam' ? 'selected' : ''}>Nam</option>
                    <option value="Nữ" ${selectedGender == 'Nữ' ? 'selected' : ''}>Nữ</option>
                </select>
            </div>
            <button type="submit" class="btn btn-primary btn-sm"><i class="fas fa-filter"></i> Lọc</button>
            <a href="${pageContext.request.contextPath}/products" class="btn btn-outline btn-sm">Xóa lọc</a>
        </form>

        <div class="product-grid">
            <c:forEach var="racket" items="${rackets}">
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
        <c:if test="${empty rackets}">
            <div class="empty-state">
                <h3><i class="fas fa-search" style="font-size:48px;color:var(--gray-400);"></i></h3>
                <h3>Không tìm thấy sản phẩm</h3>
                <p>Hãy thử từ khóa khác hoặc xóa bộ lọc.</p>
                <a href="${pageContext.request.contextPath}/products" class="btn btn-primary">Xem tất cả</a>
            </div>
        </c:if>
    </div>
</section>

<jsp:include page="layout/footer.jsp"/>