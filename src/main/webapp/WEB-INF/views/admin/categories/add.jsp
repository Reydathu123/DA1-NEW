<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<jsp:include page="../../layout/admin-header.jsp"><jsp:param name="pageTitle" value="Thêm danh mục"/><jsp:param name="menu" value="categories"/></jsp:include>

<div class="admin-header">
    <h1><i class="fas fa-plus"></i> Thêm danh mục</h1>
    <a href="${pageContext.request.contextPath}/admin/categories" class="btn btn-outline"><i class="fas fa-arrow-left"></i> Quay lại</a>
</div>
<c:if test="${not empty error}"><div class="alert alert-danger">${error}</div></c:if>
<div class="admin-form-card">
    <form action="${pageContext.request.contextPath}/admin/categories/add" method="post">
        <div class="form-group">
            <label>Tên danh mục*</label>
            <input type="text" name="name" class="form-control" required placeholder="Nhập tên danh mục...">
        </div>
        <div class="form-group">
            <label><input type="checkbox" name="active" checked> Hoạt động</label>
        </div>
        <button type="submit" class="btn btn-primary"><i class="fas fa-save"></i> Lưu </button>
    </form>
</div>

<jsp:include page="../../layout/admin-footer.jsp"/>