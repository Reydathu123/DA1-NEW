<%@ page contentType="text/html;charset=UTF-8" language="java" isErrorPage="true" %>
<jsp:include page="../layout/header.jsp"><jsp:param name="pageTitle" value="Lỗi 500 - Lỗi Hệ Thống"/></jsp:include>

<section class="section">
    <div class="container">
        <div class="empty-state">
            <h1><i class="fas fa-bug" style="font-size: 80px; color: var(--danger); margin-bottom: 20px;"></i></h1>
            <h2>500 - Lỗi Hệ Thống</h2>
            <p style="margin: 16px 0 24px;">Đã xảy ra lỗi không mong muốn từ phía máy chủ. Chúng tôi đang khắc phục sự cố này.</p>
            <c:if test="${pageContext.exception != null}">
                <div style="text-align: left; background: var(--gray-100); padding: 20px; border-radius: var(--radius-sm); font-family: monospace; overflow-x: auto; margin-bottom: 24px;">
                    <strong>Chi tiết lỗi:</strong> ${pageContext.exception.message}
                </div>
            </c:if>
            <a href="${pageContext.request.contextPath}/" class="btn btn-primary"><i class="fas fa-home"></i> Quay về Trang chủ</a>
        </div>
    </div>
</section>

<jsp:include page="../layout/footer.jsp"/>