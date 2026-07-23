<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<jsp:include page="layout/header.jsp"><jsp:param name="pageTitle" value="Đăng nhập"/></jsp:include>

<div class="auth-page">
    <div class="auth-card">
        <h2><i class="fas fa-sign-in-alt" style="color:var(--primary);"></i> Đăng nhập</h2>
        <p class="subtitle">Chào mừng bạn quay trở lại!</p>
        <c:if test="${not empty error}">
            <div class="alert alert-danger">${error}</div>
        </c:if>
        <c:if test="${not empty success}">
            <div class="alert alert-success">${success}</div>
        </c:if>
        <form action="${pageContext.request.contextPath}/login" method="post" id="loginForm">
            <div class="form-group">
                <label>Email</label>
                <input type="email" name="email" class="form-control" value="${email}" placeholder="Nhập email..." required>
            </div>
            <div class="form-group">
                <label>Mật khẩu</label>
                <input type="password" name="password" class="form-control" placeholder="Nhập mật khẩu..." required>
            </div>
            <button type="submit" class="btn btn-primary btn-lg" style="width:100%;justify-content:center;">
                <i class="fas fa-sign-in-alt"></i> Đăng nhập
            </button>
        </form>
        <p style="text-align:center;margin-top:20px;color:var(--gray-600);">
            Chưa có tài khoản? <a href="${pageContext.request.contextPath}/register" style="color:var(--primary);font-weight:600;">Đăng ký ngay</a>
        </p>
    </div>
</div>

<jsp:include page="layout/footer.jsp"/>