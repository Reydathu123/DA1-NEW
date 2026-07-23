package com.example.da1nhom9jav102.service;

import com.example.da1nhom9jav102.dao.*;
import com.example.da1nhom9jav102.entity.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CartService {
    private final CartDAO cartDAO = new CartDAO();
    private final CartItemDAO cartItemDAO = new CartItemDAO();
    private final VariantDAO variantDAO = new VariantDAO();

    public Cart getOrCreateCart(User user, String sessionId) {
        Optional<Cart> cartOpt;
        if (user != null) {
            cartOpt = cartDAO.findByUserId(user.getId());
        } else {
            cartOpt = cartDAO.findBySessionId(sessionId);
        }

        if (cartOpt.isPresent()) {
            return cartOpt.get();
        }

        Cart cart = new Cart();
        cart.setUser(user);
        cart.setSessionId(sessionId);
        cart.setCreatedAt(LocalDate.now());
        cart.setExpiresAt(LocalDate.now().plusDays(30));
        return cartDAO.save(cart);
    }

    public void addToCart(Cart cart, Integer variantId, int quantity) {
        Optional<CartItem> existingItem = cartItemDAO.findByCartIdAndVariantId(cart.getId(), variantId);

        if (existingItem.isPresent()) {
            CartItem item = existingItem.get();
            item.setQuantity(item.getQuantity() + quantity);
            cartItemDAO.update(item);
        } else {
            Variant variant = variantDAO.findByIdWithDetails(variantId);
            if (variant == null) throw new RuntimeException("Biến thể không tồn tại!");

            CartItem item = new CartItem();
            item.setCart(cart);
            item.setVariant(variant);
            item.setQuantity(quantity);
            // Tính giá sau giảm
            double price = variant.getRacket().getPrice();
            Double discount = variant.getRacket().getDiscount();
            if (discount != null && discount > 0) {
                price = price * (1 - discount / 100);
            }
            item.setPrice(price);
            cartItemDAO.save(item);
        }
    }

    public void updateCartItem(Integer cartItemId, int quantity) {
        Optional<CartItem> opt = cartItemDAO.findById(cartItemId);
        opt.ifPresent(item -> {
            if (quantity <= 0) {
                cartItemDAO.delete(cartItemId);
            } else {
                item.setQuantity(quantity);
                cartItemDAO.update(item);
            }
        });
    }

    public void removeCartItem(Integer cartItemId) {
        cartItemDAO.delete(cartItemId);
    }

    public List<CartItem> getCartItems(Integer cartId) {
        return cartItemDAO.findByCartId(cartId);
    }

    public double getCartTotal(Integer cartId) {
        List<CartItem> items = cartItemDAO.findByCartId(cartId);
        return items.stream()
                .mapToDouble(item -> item.getPrice() * item.getQuantity())
                .sum();
    }

    public int getCartItemCount(Integer cartId) {
        List<CartItem> items = cartItemDAO.findByCartId(cartId);
        return items.stream().mapToInt(CartItem::getQuantity).sum();
    }

    public void clearCart(Integer cartId) {
        cartItemDAO.deleteByCartId(cartId);
    }
}
