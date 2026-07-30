package com.example.da1nhom9jav102.service;

import com.example.da1nhom9jav102.dao.*;
import com.example.da1nhom9jav102.entity.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class OrderService {
    private final OrderDAO orderDAO = new OrderDAO();
    private final OrderDetailDAO orderDetailDAO = new OrderDetailDAO();
    private final CartItemDAO cartItemDAO = new CartItemDAO();
    private final VariantDAO variantDAO = new VariantDAO();

    public Order createOrder(User user, String shippingAddress, String paymentMethod, List<CartItem> cartItems) {
        // Tạo mã đơn hàng
        String code = "ORD" + UUID.randomUUID().toString().substring(0, 7).toUpperCase();

        // Tính tổng tiền
        double total = cartItems.stream()
                .mapToDouble(item -> item.getPrice() * item.getQuantity())
                .sum();

        Order order = new Order();
        order.setCode(code);
        order.setUser(user);
        order.setCreatedAt(LocalDate.now());
        order.setTotal(total);
        order.setStatus("pending");
        order.setPaymentMethod(paymentMethod);
        order.setShippingAddress(shippingAddress);

        Order savedOrder = orderDAO.save(order);

        // Tạo chi tiết đơn hàng
        for (CartItem cartItem : cartItems) {
            OrderDetail detail = new OrderDetail();
            detail.setOrder(savedOrder);
            detail.setVariant(cartItem.getVariant());
            detail.setQuantity(cartItem.getQuantity());
            detail.setUnitPrice(cartItem.getPrice());
            detail.setDiscount(0.0);
            orderDetailDAO.save(detail);

            // Giảm tồn kho
            variantDAO.updateStock(cartItem.getVariant().getId(), cartItem.getQuantity());
        }

        return savedOrder;
    }

    public List<Order> findByUserId(Integer userId) {
        return orderDAO.findByUserId(userId);
    }

    public Order findByIdWithDetails(Integer id) {
        return orderDAO.findByIdWithDetails(id);
    }

    public List<Order> findAll() {
        return orderDAO.findAllWithUser();
    }

    public List<Order> findByStatus(String status) {
        return orderDAO.findByStatus(status);
    }

    public void updateStatus(Integer orderId, String status) {
        Optional<Order> opt = orderDAO.findById(orderId);
        opt.ifPresent(order -> {
            if ("cancelled".equals(status) && !"cancelled".equals(order.getStatus())) {
                List<OrderDetail> details = orderDetailDAO.findByOrderId(orderId);
                for (OrderDetail detail : details) {
                    if (detail.getVariant() != null) {
                        variantDAO.restoreStock(detail.getVariant().getId(), detail.getQuantity());
                    }
                }
            }
            order.setStatus(status);
            if ("shipping".equals(status)) {
                order.setShippingDate(LocalDate.now());
            } else if ("delivered".equals(status)) {
                order.setDeliveryDate(LocalDate.now());
            }
            orderDAO.update(order);
        });
    }

    public long count() {
        return orderDAO.count();
    }

    public long countByStatus(String status) {
        return orderDAO.countByStatus(status);
    }

    public Double getTotalRevenue() {
        return orderDAO.getTotalRevenue();
    }

    public List<OrderDetail> findOrderDetails(Integer orderId) {
        return orderDetailDAO.findByOrderId(orderId);
    }
}
