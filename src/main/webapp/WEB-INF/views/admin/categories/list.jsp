<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<jsp:include page="../../layout/admin-header.jsp"><jsp:param name="pageTitle" value="Danh mục"/><jsp:param name="menu" value="categories"/></jsp:include>

<div class="admin-header">
    <h1><i class="fas fa-list"></i> Quản lý danh mục</h1>
    <a href="${pageContext.request.contextPath}/admin/categories/add" class="btn btn-primary"><i class="fas fa-plus"></i> Thêm mới</a>
</div>
<c:if test="${param.success != null}">
    <div class="alert alert-success"><i class="fas fa-check"></i> Thao tác thành công!</div>
</c:if>
<c:if test="${param.error != null}">
    <div class="alert alert-danger">Không thể xóa danh mục này!</div>
</c:if>
<table class="admin-table">
    <thead><tr><th>ID</th><th>Tên danh mục</th><th>Trạng thái</th><th>Thao tác</th></tr></thead>
    <tbody>
    <c:forEach var="cat" items="${categories}">
        <tr>
            <td>${cat.id}</td>
            <td>${cat.name}</td>
            <td><span class="status-badge ${cat.active ? 'status-delivered' : 'status-cancelled'}">${cat.active ? 'Hoạt động' : 'Ẩn'}</span></td>
            <td class="actions">
                <a href="${pageContext.request.contextPath}/admin/categories/edit?id=${cat.id}" class="btn btn-warning btn-sm"><i class="fas fa-edit"></i></a>
                <button class="btn btn-danger btn-sm" onclick="confirmDelete('${pageContext.request.contextPath}/admin/categories/delete?id=${cat.id}')"><i class="fas fa-trash"></i></button>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>

<jsp:include page="../../layout/admin-footer.jsp"/>