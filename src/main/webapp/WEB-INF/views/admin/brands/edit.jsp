<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<jsp:include page="../../layout/admin-header.jsp"><jsp:param name="pageTitle" value="Sửa Thương Hiệu"/><jsp:param name="menu" value="brands"/></jsp:include>

<div class="admin-header">
    <h1><i class="fas fa-edit"></i> Sửa thương hiệu</h1>
    <a href="${pageContext.request.contextPath}/admin/brands" class="btn btn-outline"><i class="fas fa-arrow-left"></i> Quay lại</a>
</div>
<div class="admin-form-card">
    <form action="${pageContext.request.contextPath}/admin/brands/edit" method="post">
        <input type="hidden" name="id" value="${brand.id}">
        <div class="form-group">
            <label>Tên thương hiệu *</label>
            <input type="text" name="name" class="form-control" value="${brand.name}" required>
        </div>
        <div class="form-group">
            <label>Tên tập tin logo</label>
            <input type="text" name="logo" class="form-control" value="${brand.logo}" placeholder="ví dụ: yonex.png">
        </div>
        <div class="form-group">
            <label><input type="checkbox" name="active" ${brand.active ? 'checked' : ''}> Đang hoạt động</label>
        </div>
        <button type="submit" class="btn btn-primary"><i class="fas fa-save"></i> Cập nhật</button>
    </form>
</div>

<jsp:include page="../../layout/admin-footer.jsp"/>