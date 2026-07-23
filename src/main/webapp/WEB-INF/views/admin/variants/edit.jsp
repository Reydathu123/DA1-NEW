<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<jsp:include page="../../layout/admin-header.jsp"><jsp:param name="pageTitle" value="Sửa Biến Thể"/><jsp:param name="menu" value="variants"/></jsp:include>

<div class="admin-header">
    <h1><i class="fas fa-edit"></i> Sửa biến thể sản phẩm</h1>
    <a href="${pageContext.request.contextPath}/admin/variants" class="btn btn-outline"><i class="fas fa-arrow-left"></i> Quay lại</a>
</div>
<c:if test="${not empty error}"><div class="alert alert-danger">${error}</div></c:if>
<div class="admin-form-card">
    <form action="${pageContext.request.contextPath}/admin/variants/edit" method="post">
        <input type="hidden" name="id" value="${variant.id}">
        <div class="form-group">
            <label>Sản phẩm *</label>
            <select name="racketId" class="form-control" required>
                <c:forEach var="r" items="${rackets}">
                    <option value="${r.id}" ${variant.racket != null && variant.racket.id == r.id ? 'selected' : ''}>${r.name}</option>
                </c:forEach>
            </select>
        </div>
        <div class="form-group">
            <label>Màu sắc *</label>
            <select name="colorId" class="form-control" required>
                <c:forEach var="c" items="${colors}">
                    <option value="${c.id}" ${variant.color != null && variant.color.id == c.id ? 'selected' : ''}>${c.name}</option>
                </c:forEach>
            </select>
        </div>
        <div class="form-group">
            <label>Kích thước (Size)</label>
            <select name="sizeId" class="form-control">
                <option value="">-- Không có --</option>
                <c:forEach var="s" items="${sizes}">
                    <option value="${s.id}" ${variant.size != null && variant.size.id == s.id ? 'selected' : ''}>${s.sizeValue}</option>
                </c:forEach>
            </select>
        </div>
        <div class="form-group">
            <label>Grip Size (ví dụ: G5, G4)</label>
            <input type="text" name="gripSize" class="form-control" value="${variant.gripSize}">
        </div>
        <div class="form-group">
            <label>Độ cứng đũa (Flex)</label>
            <input type="text" name="flex" class="form-control" value="${variant.flex}">
        </div>
        <div class="form-group">
            <label>Độ cân bằng (Balance)</label>
            <input type="text" name="balance" class="form-control" value="${variant.balance}">
        </div>
        <div class="form-group">
            <label>Trọng lượng (g)</label>
            <input type="number" name="weight" step="0.1" class="form-control" value="${variant.weight}">
        </div>
        <div class="form-group">
            <label>Số lượng kho *</label>
            <input type="number" name="stock" class="form-control" value="${variant.stock}" required>
        </div>
        <button type="submit" class="btn btn-primary"><i class="fas fa-save"></i> Cập nhật</button>
    </form>
</div>

<jsp:include page="../../layout/admin-footer.jsp"/>