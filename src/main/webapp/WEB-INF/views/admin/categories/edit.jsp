<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<jsp:include page="../../layout/admin-header.jsp"><jsp:param name="pageTitle" value="Sửa danh mục"/><jsp:param name="menu" value="categories"/></jsp:include>

<div class="admin-header">
    <h1><i class="fas fa-edit"></i> Sửa danh mục</h1>
    <a href="${pageContext.request.contextPath}/admin/categories" class="btn btn-outline"><i class="fas fa-arrow-left"></i> Quay lại</a>
</div>
<div class="admin-form-card">
    <form action="${pageContext.request.contextPath}/admin/categories/edit" method="post">
        <input type="hidden" name="id" value="${category.id}">
        <div class="form-group">
            <label>Tên danh mục *</label>
            <input type="text" name="name" class="form-control" value="${category.name}" required>
        </div>
        <div class="form-group">
            <label><input type="checkbox" name="active" ${category.active ? 'checked' : ''}> Hoạt động</label>
        </div>
        <button type="submit" class="btn btn-primary"><i class="fas fa-save"></i> Cập nhật</button>
    </form>
</div>

<jsp:include page="../../layout/admin-footer.jsp"/>