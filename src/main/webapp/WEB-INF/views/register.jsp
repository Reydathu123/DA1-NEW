<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<jsp:include page="layout/header.jsp"><jsp:param name="pageTitle" value="Đăng ký"/></jsp:include>

<div class="auth-page">
    <div class="auth-card">
        <h2><i class="fas fa-user-plus" style="color:var(--primary);"></i> Đăng ký</h2>
        <p class="subtitle">Tạo tài khoản mới</p>
        <c:if test="${not empty error}">
            <div class="alert alert-danger">${error}</div>
        </c:if>
        <form action="${pageContext.request.contextPath}/register" method="post" id="registerForm">
            <div class="form-group">
                <label>Họ và tên *</label>
                <input type="text" name="fullName" class="form-control" value="${fullName}" placeholder="Nhập họ tên..." required>
            </div>
            <div class="form-group">
                <label>Email *</label>
                <input type="email" name="email" class="form-control" value="${email}" placeholder="Nhập email..." required>
            </div>
            <div class="form-group">
                <label>Số điện thoại *</label>
                <input type="text" name="phone" class="form-control" value="${phone}" placeholder="0xxx xxx xxx" required>
            </div>
            <div class="form-group">
                <label>Địa chỉ</label>
                <input type="text" name="address" class="form-control" value="${address}" placeholder="Nhập địa chỉ...">
            </div>
            <div class="form-group">
                <label>Mật khẩu *</label>
                <input type="password" name="password" class="form-control" placeholder="Ít nhất 3 ký tự..." required>
            </div>
            <div class="form-group">
                <label>Xác nhận mật khẩu *</label>
                <input type="password" name="confirmPassword" class="form-control" placeholder="Nhập lại mật khẩu..." required>
            </div>
            <button type="submit" class="btn btn-primary btn-lg" style="width:100%;justify-content:center;">
                <i class="fas fa-user-plus"></i> Đăng ký
            </button>
        </form>
        <p style="text-align:center;margin-top:20px;color:var(--gray-600);">
            Đã có tài khoản? <a href="${pageContext.request.contextPath}/login" style="color:var(--primary);font-weight:600;">Đăng nhập</a>
        </p>
    </div>
</div>

<jsp:include page="layout/footer.jsp"/>