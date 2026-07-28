<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<jsp:include page="../../layout/admin-header.jsp"><jsp:param name="pageTitle" value="Thêm Sản Phẩm"/><jsp:param name="menu" value="rackets"/></jsp:include>

<div class="admin-header">
    <h1><i class="fas fa-plus"></i> Thêm sản phẩm</h1>
    <a href="${pageContext.request.contextPath}/admin/rackets" class="btn btn-outline"><i class="fas fa-arrow-left"></i> Quay lại</a>
</div>
<c:if test="${not empty error}"><div class="alert alert-danger">${error}</div></c:if>
<div class="admin-form-card">
    <form action="${pageContext.request.contextPath}/admin/rackets/add" method="post">
        <div class="form-group">
            <label>Tên sản phẩm *</label>
            <input type="text" name="name" class="form-control" required placeholder="Nhập tên sản phẩm">
        </div>
        <div class="form-group">
            <label>Giá niêm yết *</label>
            <input type="number" name="price" step="0.01" class="form-control" required placeholder="Nhập giá bán">
        </div>
        <div class="form-group">
            <label>Giảm giá (%)</label>
            <input type="number" name="discount" step="0.01" value="0" class="form-control">
        </div>
        <div class="form-group">
            <label>Tên tập tin hình ảnh</label>
            <input type="text" name="image" class="form-control" placeholder="ví dụ: yonex-astrox.png">
        </div>
        <div class="form-group">
            <label>Danh mục *</label>
            <select name="categoryId" class="form-control" required>
                <option value="">-- Chọn danh mục --</option>
                <c:forEach var="cat" items="${categories}">
                    <option value="${cat.id}">${cat.name}</option>
                </c:forEach>
            </select>
        </div>
        <div class="form-group">
            <label>Thương hiệu *</label>
            <select name="brandId" class="form-control" required>
                <option value="">-- Chọn thương hiệu --</option>
                <c:forEach var="b" items="${brands}">
                    <option value="${b.id}">${b.name}</option>
                </c:forEach>
            </select>
        </div>
        <div class="form-group">
            <label>Chất liệu</label>
            <input type="text" name="material" class="form-control" placeholder="ví dụ: Carbon, Graphite...">
        </div>
        <div class="form-group">
            <label>Đối tượng (Gender)</label>
            <select name="gender" class="form-control">
                <option value="Unisex">Unisex</option>
                <option value="Nam">Nam</option>
                <option value="Nữ">Nữ</option>
            </select>
        </div>
        <div class="form-group">
            <label>Mô tả chi tiết</label>
            <textarea name="description" class="form-control" placeholder="Nhập mô tả sản phẩm"></textarea>
        </div>
        <div class="form-group">
            <label><input type="checkbox" name="active" checked> Hoạt động (Hiển thị lên trang)</label>
        </div>
        <button type="submit" class="btn btn-primary"><i class="fas fa-save"></i> Lưu sản phẩm </button>
    </form>
</div>

<jsp:include page="../../layout/admin-footer.jsp"/>
