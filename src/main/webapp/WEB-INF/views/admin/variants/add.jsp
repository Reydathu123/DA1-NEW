<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<jsp:include page="../../layout/admin-header.jsp"><jsp:param name="pageTitle" value="Thêm Biến Thể"/><jsp:param name="menu" value="variants"/></jsp:include>

<div class="admin-header">
    <h1><i class="fas fa-plus"></i> Thêm biến thể sản phẩm</h1>
    <a href="${pageContext.request.contextPath}/admin/variants" class="btn btn-outline"><i class="fas fa-arrow-left"></i> Quay lại</a>
</div>
<c:if test="${not empty error}"><div class="alert alert-danger">${error}</div></c:if>
<div class="admin-form-card">
    <form action="${pageContext.request.contextPath}/admin/variants/add" method="post">
        <div class="form-group">
            <label>Sản phẩm *</label>
            <select name="racketId" class="form-control" required>
                <option value="">-- Chọn sản phẩm --</option>
                <c:forEach var="r" items="${rackets}">
                    <option value="${r.id}">${r.name}</option>
                </c:forEach>
            </select>
        </div>x
        <div class="form-group">
            <label>Màu sắc *</label>
            <select name="colorId" class="form-control" required>
                <option value="">-- Chọn màu sắc --</option>
                <c:forEach var="c" items="${colors}">
                    <option value="${c.id}">${c.name}</option>
                </c:forEach>
            </select>
        </div>
        <div class="form-group">
            <label>Kích thước (Size)</label>
            <select name="sizeId" class="form-control">
                <option value="">-- Không có --</option>
                <c:forEach var="s" items="${sizes}">
                    <option value="${s.id}">${s.sizeValue}</option>
                </c:forEach>
            </select>
        </div>
        <div class="form-group">
            <label>Grip Size (ví dụ: G5, G4)</label>
            <input type="text" name="gripSize" class="form-control" placeholder="ví dụ: G5">
        </div>
        <div class="form-group">
            <label>Độ cứng đũa (Flex)</label>
            <input type="text" name="flex" class="form-control" placeholder="ví dụ: Cứng, Trung bình, Dẻo">
        </div>
        <div class="form-group">
            <label>Độ cân bằng (Balance)</label>
            <input type="text" name="balance" class="form-control" placeholder="ví dụ: Đầu nặng, Cân bằng, Nhẹ đầu">
        </div>
        <div class="form-group">
            <label>Trọng lượng (g)</label>
            <input type="number" name="weight" step="0.1" class="form-control" placeholder="ví dụ: 85.5">
        </div>
        <div class="form-group">
            <label>Số lượng kho *</label>
            <input type="number" name="stock" class="form-control" value="10" required>
        </div>
        <button type="submit" class="btn btn-primary"><i class="fas fa-save"></i> Lưu biến thể</button>
    </form>
</div>

<jsp:include page="../../layout/admin-footer.jsp"/>