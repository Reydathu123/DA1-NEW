<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<jsp:include page="../../layout/admin-header.jsp"><jsp:param name="pageTitle" value="Sản Phẩm"/><jsp:param name="menu" value="rackets"/></jsp:include>

<div class="admin-header">
    <h1><i class="fas fa-box"></i> Quản lý sản phẩm</h1>
    <a href="${pageContext.request.contextPath}/admin/rackets/add" class="btn btn-primary"><i class="fas fa-plus"></i> Thêm mới</a>
</div>
<c:if test="${param.success != null}">
    <div class="alert alert-success"><i class="fas fa-check"></i> Thao tác thành công!</div>
</c:if>
<c:if test="${param.error != null}">
    <div class="alert alert-danger">Không thể xóa sản phẩm này!</div>
</c:if>
<table class="admin-table">
    <thead>
    <tr>
        <th>ID</th>
        <th>Hình ảnh</th>
        <th>Tên sản phẩm</th>
        <th>Danh mục</th>
        <th>Thương hiệu</th>
        <th>Giá</th>
        <th>Giảm giá</th>
        <th>Trạng thái</th>
        <th>Thao tác</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="r" items="${rackets}">
        <tr>
            <td>${r.id}</td>
            <td>
                <img src="${pageContext.request.contextPath}/images/${r.image != null ? r.image : 'default.jpg'}"
                     alt="" style="max-height: 50px; max-width: 50px; object-fit: cover;"
                     onerror="this.src='https://placehold.co/50x50/e9ecef/495057?text=Vot'">
            </td>
            <td>${r.name}</td>
            <td>${r.category != null ? r.category.name : 'Không có'}</td>
            <td>${r.brand != null ? r.brand.name : 'Không có'}</td>
            <td><fmt:formatNumber value="${r.price}" type="number"/> VND</td>
            <td><fmt:formatNumber value="${r.discount}" type="number"/>%</td>
            <td><span class="status-badge ${r.active ? 'status-delivered' : 'status-cancelled'}">${r.active ? 'Hoạt động' : 'Ẩn'}</span></td>
            <td class="actions">
                <a href="${pageContext.request.contextPath}/admin/rackets/edit?id=${r.id}" class="btn btn-warning btn-sm"><i class="fas fa-edit"></i></a>
                <button class="btn btn-danger btn-sm" onclick="confirmDelete('${pageContext.request.contextPath}/admin/rackets/delete?id=${r.id}')"><i class="fas fa-trash"></i></button>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>

<jsp:include page="../../layout/admin-footer.jsp"/>