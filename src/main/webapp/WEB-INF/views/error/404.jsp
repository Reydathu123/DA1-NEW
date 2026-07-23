<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:include page="../layout/header.jsp"><jsp:param name="pageTitle" value="Lỗi 404 - Không Tìm Thấy"/></jsp:include>

<section class="section">
    <div class="container">
        <div class="empty-state">
            <h1><i class="fas fa-exclamation-triangle" style="font-size: 80px; color: var(--danger); margin-bottom: 20px;"></i></h1>
            <h2>404 - Trang không tồn tại</h2>
            <p style="margin: 16px 0 24px;">Trang bạn đang tìm kiếm không tồn tại hoặc đã bị xóa.</p>
            <a href="${pageContext.request.contextPath}/" class="btn btn-primary"><i class="fas fa-home"></i> Quay về Trang chủ</a>
        </div>
    </div>
</section>

<jsp:include page="../layout/footer.jsp"/>