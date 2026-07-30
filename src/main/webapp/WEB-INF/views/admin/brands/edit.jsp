<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<jsp:include page="../../layout/admin-header.jsp"><jsp:param name="pageTitle" value="Sửa Thương Hiệu"/><jsp:param name="menu" value="brands"/></jsp:include>

<div class="admin-header">
    <h1><i class="fas fa-edit"></i> Sửa thương hiệu</h1>
    <a href="${pageContext.request.contextPath}/admin/brands" class="btn btn-outline"><i class="fas fa-arrow-left"></i> Quay lại</a>
</div>
<c:if test="${not empty error}"><div class="alert alert-danger">${error}</div></c:if>
<div class="admin-form-card">
    <form action="${pageContext.request.contextPath}/admin/brands/edit" method="post" enctype="multipart/form-data">
        <input type="hidden" name="id" value="${brand.id}">
        <div class="form-group">
            <label>Tên thương hiệu *</label>
            <input type="text" name="name" class="form-control" value="${brand.name}" required>
        </div>
        <div class="form-group">
            <label>Logo thương hiệu (để trống nếu không đổi)</label>
            <div style="margin-bottom: 10px;">
                <img src="${pageContext.request.contextPath}/images/${brand.logo != null ? brand.logo : 'default-logo.png'}"
                     alt="${brand.name}" style="width:120px;height:120px;object-fit:cover;border-radius:8px;border:1px solid #ddd;"
                     onerror="this.src='https://placehold.co/120x120/e9ecef/495057?text=Logo'">
            </div>
            <input type="file" name="logoFile" accept="image/*" class="form-control-file" style="padding: 10px 0;">
        </div>
        <div class="form-group">
            <label><input type="checkbox" name="active" ${brand.active ? 'checked' : ''}> Đang hoạt động</label>
        </div>
        <button type="submit" class="btn btn-primary"><i class="fas fa-save"></i> Cập nhật</button>
    </form>
</div>

<jsp:include page="../../layout/admin-footer.jsp"/>