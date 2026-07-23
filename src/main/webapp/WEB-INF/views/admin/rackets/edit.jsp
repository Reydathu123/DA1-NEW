<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<jsp:include page="../../layout/admin-header.jsp"><jsp:param name="pageTitle" value="Sửa Sản Phẩm"/><jsp:param name="menu" value="rackets"/></jsp:include>

<div class="admin-header">
    <h1><i class="fas fa-edit"></i> Sửa sản phẩm</h1>
    <a href="${pageContext.request.contextPath}/admin/rackets" class="btn btn-outline"><i class="fas fa-arrow-left"></i> Quay lại</a>
</div>
<c:if test="${not empty error}"><div class="alert alert-danger">${error}</div></c:if>
<div class="admin-form-card">
    <form action="${pageContext.request.contextPath}/admin/rackets/edit" method="post">
        <input type="hidden" name="id" value="${racket.id}">
        <div class="form-group">
            <label>Tên sản phẩm (Vợt) *</label>
            <input type="text" name="name" class="form-control" value="${racket.name}" required>
        </div>
        <div class="form-group">
            <label>Giá niêm yết *</label>
            <input type="number" name="price" step="0.01" class="form-control" value="${racket.price}" required>
        </div>
        <div class="form-group">
            <label>Giảm giá (%)</label>
            <input type="number" name="discount" step="0.01" class="form-control" value="${racket.discount}">
        </div>
        <div class="form-group">
            <label>Link / Tên hình ảnh</label>
            <input type="text" name="image" class="form-control" value="${racket.image}">
        </div>
        <div class="form-group">
            <label>Danh mục *</label>
            <select name="categoryId" class="form-control" required>
                <c:forEach var="cat" items="${categories}">
                    <option value="${cat.id}" ${racket.category != null && racket.category.id == cat.id ? 'selected' : ''}>${cat.name}</option>
                </c:forEach>
            </select>
        </div>
        <div class="form-group">
            <label>Thương hiệu *</label>
            <select name="brandId" class="form-control" required>
                <c:forEach var="b" items="${brands}">
                    <option value="${b.id}" ${racket.brand != null && racket.brand.id == b.id ? 'selected' : ''}>${b.name}</option>
                </c:forEach>
            </select>
        </div>
        <div class="form-group">
            <label>Chất liệu</label>
            <input type="text" name="material" class="form-control" value="${racket.material}">
        </div>
        <div class="form-group">
            <label>Đối tượng (Gender)</label>
            <select name="gender" class="form-control">
                <option value="Unisex" ${racket.gender == 'Unisex' ? 'selected' : ''}>Unisex</option>
                <option value="Nam" ${racket.gender == 'Nam' ? 'selected' : ''}>Nam</option>
                <option value="Nữ" ${racket.gender == 'Nữ' ? 'selected' : ''}>Nữ</option>
            </select>
        </div>
        <div class="form-group">
            <label>Mô tả chi tiết</label>
            <textarea name="description" class="form-control">${racket.description}</textarea>
        </div>
        <div class="form-group">
            <label><input type="checkbox" name="active" ${racket.active ? 'checked' : ''}> Hoạt động</label>
        </div>
        <button type="submit" class="btn btn-primary"><i class="fas fa-save"></i> Cập nhật</button>
    </form>
</div>

<jsp:include page="../../layout/admin-footer.jsp"/>