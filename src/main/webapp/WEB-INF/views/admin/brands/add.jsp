<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<jsp:include page="../../layout/admin-header.jsp"><jsp:param name="pageTitle" value="Thêm Thương Hiệu"/><jsp:param name="menu" value="brands"/></jsp:include>

<div class="admin-header">
    <h1><i class="fas fa-plus"></i> Thêm thương hiệu </h1>
    <a href="${pageContext.request.contextPath}/admin/brands" class="btn btn-outline"><i class="fas fa-arrow-left"></i> Quay lại </a>
</div>
<c:if test="${not empty error}"><div class="alert alert-danger">${error}</div></c:if>
<div class="admin-form-card">
    <form action="${pageContext.request.contextPath}/admin/brands/add" method="post" enctype="multipart/form-data">
        <div class="form-group">
            <label>Tên thương hiệu *</label>
            <input type="text" name="name" class="form-control" required placeholder="Nhập tên thương hiệu...">
        </div>
        <div class="form-group">
            <label>Logo thương hiệu</label>
            <input type="file" name="logoFile" accept="image/*" class="form-control-file" style="padding: 10px 0;">
        </div>
        <div class="form-group">
            <label><input type="checkbox" name="active" checked> Đang hoạt động</label>
        </div>
        <button type="submit" class="btn btn-primary"><i class="fas fa-save"></i> Lưu</button>
    </form>
</div>

<jsp:include page="../../layout/admin-footer.jsp"/>
