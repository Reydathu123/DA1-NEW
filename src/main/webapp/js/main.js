// ===== CONFIRM DELETE =====
function confirmDelete(url) {
    if (confirm('Ban co chac chan muon xoa khong?')) {
        window.location.href = url;
    }
    return false;
}

// ===== ADD TO CART (AJAX) =====
function addToCart(variantId, quantity) {
    if (!variantId) {
        showToast('Vui long chon bien the san pham!', 'warning');
        return;
    }
    quantity = quantity || 1;

    fetch(contextPath + '/cart/add', {
        method: 'POST',
        headers: {'Content-Type': 'application/x-www-form-urlencoded'},
        body: 'variantId=' + variantId + '&quantity=' + quantity
    })
    .then(function(response) { return response.json(); })
    .then(function(data) {
        if (data.success) {
            showToast(data.message, 'success');
            var badge = document.getElementById('cart-count');
            if (badge) badge.textContent = data.cartCount;
        } else {
            showToast(data.message || 'Co loi xay ra!', 'danger');
        }
    })
    .catch(function(err) {
        showToast('Co loi xay ra!', 'danger');
        console.error(err);
    });
}

// ===== UPDATE CART ITEM =====
function updateCartItem(cartItemId, quantity) {
    fetch(contextPath + '/cart/update', {
        method: 'POST',
        headers: {'Content-Type': 'application/x-www-form-urlencoded'},
        body: 'cartItemId=' + cartItemId + '&quantity=' + quantity
    })
    .then(function(response) { return response.json(); })
    .then(function(data) {
        if (data.success) {
            location.reload();
        } else {
            showToast(data.message || 'Co loi!', 'danger');
        }
    })
    .catch(function(err) { console.error(err); });
}

// ===== REMOVE CART ITEM =====
function removeCartItem(cartItemId) {
    if (!confirm('Xoa san pham nay khoi gio hang?')) return;

    fetch(contextPath + '/cart/remove', {
        method: 'POST',
        headers: {'Content-Type': 'application/x-www-form-urlencoded'},
        body: 'cartItemId=' + cartItemId
    })
    .then(function(response) { return response.json(); })
    .then(function(data) {
        if (data.success) {
            location.reload();
        }
    })
    .catch(function(err) { console.error(err); });
}

// ===== QUANTITY CONTROLS =====
function changeQty(inputId, delta) {
    var input = document.getElementById(inputId);
    if (!input) return;
    var val = parseInt(input.value) || 1;
    val += delta;
    if (val < 1) val = 1;
    if (val > 99) val = 99;
    input.value = val;
}

// ===== TOAST NOTIFICATION =====
function showToast(message, type) {
    type = type || 'info';
    var toast = document.createElement('div');
    toast.className = 'toast toast-' + type;
    toast.textContent = message;
    toast.style.cssText = 'position:fixed;top:20px;right:20px;padding:14px 24px;border-radius:8px;color:#fff;font-weight:500;z-index:9999;animation:slideIn 0.3s ease;font-size:14px;max-width:400px;box-shadow:0 4px 16px rgba(0,0,0,0.2);';

    if (type === 'success') toast.style.background = '#34a853';
    else if (type === 'danger') toast.style.background = '#ea4335';
    else if (type === 'warning') toast.style.background = '#fbbc04';
    else toast.style.background = '#1a73e8';

    document.body.appendChild(toast);
    setTimeout(function() {
        toast.style.opacity = '0';
        toast.style.transition = 'opacity 0.3s';
        setTimeout(function() { toast.remove(); }, 300);
    }, 3000);
}

// ===== FORM VALIDATION =====
function validateForm(formId) {
    var form = document.getElementById(formId);
    if (!form) return true;

    var required = form.querySelectorAll('[required]');
    var valid = true;

    required.forEach(function(field) {
        field.style.borderColor = '';
        if (!field.value.trim()) {
            field.style.borderColor = '#ea4335';
            valid = false;
        }
    });

    if (!valid) {
        showToast('Vui long dien day du thong tin!', 'warning');
    }
    return valid;
}

// ===== SELECT VARIANT =====
function selectVariant(element, variantId) {
    document.querySelectorAll('.variant-option').forEach(function(el) {
        el.classList.remove('active');
    });
    element.classList.add('active');

    var input = document.getElementById('selectedVariantId');
    if (input) input.value = variantId;
}

// ===== FORMAT CURRENCY =====
function formatCurrency(amount) {
    return new Intl.NumberFormat('vi-VN').format(amount) + ' VND';
}

// ===== INIT =====
document.addEventListener('DOMContentLoaded', function() {
    // Auto-hide alerts after 5 seconds
    var alerts = document.querySelectorAll('.alert');
    alerts.forEach(function(alert) {
        setTimeout(function() {
            alert.style.opacity = '0';
            alert.style.transition = 'opacity 0.5s';
            setTimeout(function() { alert.remove(); }, 500);
        }, 5000);
    });
});
