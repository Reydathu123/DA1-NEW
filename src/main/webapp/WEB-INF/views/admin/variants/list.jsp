<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<jsp:include page="../../layout/admin-header.jsp"><jsp:param name="pageTitle" value="Biến Thể Sản Phẩm"/><jsp:param name="menu" value="variants"/></jsp:include>

<div class="admin-header">
    <h1><i class="fas fa-th"></i> Biến thể sản phẩm (Variants)</h1>
    <a href="${pageContext.request.contextPath}/admin/variants/add" class="btn btn-primary"><i class="fas fa-plus"></i> Thêm mới</a>
</div>
<c:if test="${param.success != null}">
    <div class="alert alert-success"><i class="fas fa-check"></i> Thao tác thành công!</div>
</c:if>
<c:if test="${param.error != null}">
    <div class="alert alert-danger">Không thể xóa biến thể này!</div>
</c:if>
<table class="admin-table">
    <thead>
    <tr>
        <th>ID</th>
        <th>Tên sản phẩm</th>
        <th>Màu sắc</th>
        <th>Kích thước</th>
        <th>Grip Size</th>
        <th>Flex</th>
        <th>Độ cân bằng</th>
        <th>Trọng lượng (g)</th>
        <th>Số lượng kho</th>
        <th>Thao tác</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="v" items="${variants}">
        <tr>
            <td>${v.id}</td>
            <td><strong>${v.racket != null ? v.racket.name : 'N/A'}</strong></td>
            <td>${v.color != null ? v.color.name : 'N/A'}</td>
            <td>${v.size != null ? v.size.sizeValue : 'N/A'}</td>
            <td>${v.gripSize}</td>
            <td>${v.flex}</td>
            <td>${v.balance}</td>
            <td>${v.weight}</td>
            <td><strong style="color: ${v.stock <= 3 ? 'var(--danger)' : 'inherit'}">${v.stock}</strong></td>
            <td class="actions">
                <a href="${pageContext.request.contextPath}/admin/variants/edit?id=${v.id}" class="btn btn-warning btn-sm"><i class="fas fa-edit"></i></a>
                <button class="btn btn-danger btn-sm" onclick="confirmDelete('${pageContext.request.contextPath}/admin/variants/delete?id=${v.id}')"><i class="fas fa-trash"></i></button>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>

<jsp:include page="../../layout/admin-footer.jsp"/>