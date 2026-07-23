<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<jsp:include page="../../layout/admin-header.jsp"><jsp:param name="pageTitle" value="Thương Hiệu"/><jsp:param name="menu" value="brands"/></jsp:include>

<div class="admin-header">
    <h1><i class="fas fa-tags"></i> Quản lý thương hiệu</h1>
    <a href="${pageContext.request.contextPath}/admin/brands/add" class="btn btn-primary"><i class="fas fa-plus"></i> Thêm mới</a>
</div>
<c:if test="${param.success != null}">
    <div class="alert alert-success"><i class="fas fa-check"></i> Thao tác thành công!</div>
</c:if>
<c:if test="${param.error != null}">
    <div class="alert alert-danger">Không thể xóa thương hiệu này!</div>
</c:if>
<table class="admin-table">
    <thead><tr><th>ID</th><th>Logo</th><th>Tên thương hiệu</th><th>Trạng thái</th><th>Thao tác</th></tr></thead>
    <tbody>
    <c:forEach var="brand" items="${brands}">
        <tr>
            <td>${brand.id}</td>
            <td>
                <img src="${pageContext.request.contextPath}/images/${brand.logo != null ? brand.logo : 'default-logo.png'}"
                     alt="" style="max-height: 40px; max-width: 80px;" onerror="this.src='https://placehold.co/80x40/e9ecef/495057?text=Logo'">
            </td>
            <td>${brand.name}</td>
            <td><span class="status-badge ${brand.active ? 'status-delivered' : 'status-cancelled'}">${brand.active ? 'Hoạt động' : 'Ẩn'}</span></td>
            <td class="actions">
                <a href="${pageContext.request.contextPath}/admin/brands/edit?id=${brand.id}" class="btn btn-warning btn-sm"><i class="fas fa-edit"></i></a>
                <button class="btn btn-danger btn-sm" onclick="confirmDelete('${pageContext.request.contextPath}/admin/brands/delete?id=${brand.id}')"><i class="fas fa-trash"></i></button>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>

<jsp:include page="../../layout/admin-footer.jsp"/>