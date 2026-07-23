package com.example.da1nhom9jav102.dao;

import com.example.da1nhom9jav102.entity.OrderDetail;
import jakarta.persistence.EntityManager;

import java.util.List;

public class OrderDetailDAO extends GenericDAO<OrderDetail> {

    public OrderDetailDAO() {
        super(OrderDetail.class);
    }

    public List<OrderDetail> findByOrderId(Integer orderId) {
        EntityManager em = getEntityManager();
        try {
            return em.createQuery(
                            "SELECT od FROM OrderDetail od LEFT JOIN FETCH od.variant v " +
                                    "LEFT JOIN FETCH v.racket LEFT JOIN FETCH v.color " +
                                    "WHERE od.order.id = :orderId",
                            OrderDetail.class)
                    .setParameter("orderId", orderId)
                    .getResultList();
        } finally {
            em.close();
        }
    }
}
