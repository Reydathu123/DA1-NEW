package com.example.da1nhom9jav102.controller;

import com.example.da1nhom9jav102.entity.*;
import com.example.da1nhom9jav102.service.CartService;
import com.example.da1nhom9jav102.service.CategoryService;
import com.example.da1nhom9jav102.service.BrandService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet(name = "CartServlet", urlPatterns = {"/cart", "/cart/add", "/cart/update", "/cart/remove"})
public class CartServlet extends HttpServlet {
    private final CartService cartService = new CartService();
    private final CategoryService categoryService = new CategoryService();
    private final BrandService brandService = new BrandService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");

        req.setAttribute("categories", categoryService.findAllActive());
        req.setAttribute("brands", brandService.findAllActive());

        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");

        Cart cart = cartService.getOrCreateCart(user, session.getId());
        List<CartItem> cartItems = cartService.getCartItems(cart.getId());
        double cartTotal = cartService.getCartTotal(cart.getId());

        req.setAttribute("cartItems", cartItems);
        req.setAttribute("cartTotal", cartTotal);
        req.setAttribute("cartItemCount", cartService.getCartItemCount(cart.getId()));

        req.getRequestDispatcher("/WEB-INF/views/cart.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json;charset=UTF-8");

        String path = req.getServletPath();
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");

        Cart cart = cartService.getOrCreateCart(user, session.getId());

        switch (path) {
            case "/cart/add":
                handleAddToCart(req, resp, cart);
                break;
            case "/cart/update":
                handleUpdateCart(req, resp, cart);
                break;
            case "/cart/remove":
                handleRemoveFromCart(req, resp, cart);
                break;
        }
    }

    private void handleAddToCart(HttpServletRequest req, HttpServletResponse resp, Cart cart) throws IOException {
        try {
            Integer variantId = Integer.parseInt(req.getParameter("variantId"));
            int quantity = Integer.parseInt(req.getParameter("quantity"));

            cartService.addToCart(cart, variantId, quantity);
            int count = cartService.getCartItemCount(cart.getId());

            PrintWriter out = resp.getWriter();
            out.print("{\"success\": true, \"message\": \"Đã thêm vào giỏ hàng!\", \"cartCount\": " + count + "}");
        } catch (Exception e) {
            PrintWriter out = resp.getWriter();
            out.print("{\"success\": false, \"message\": \"" + e.getMessage() + "\"}");
        }
    }

    private void handleUpdateCart(HttpServletRequest req, HttpServletResponse resp, Cart cart) throws IOException {
        try {
            Integer cartItemId = Integer.parseInt(req.getParameter("cartItemId"));
            int quantity = Integer.parseInt(req.getParameter("quantity"));

            cartService.updateCartItem(cartItemId, quantity);
            double total = cartService.getCartTotal(cart.getId());
            int count = cartService.getCartItemCount(cart.getId());

            PrintWriter out = resp.getWriter();
            out.print("{\"success\": true, \"cartTotal\": " + total + ", \"cartCount\": " + count + "}");
        } catch (Exception e) {
            PrintWriter out = resp.getWriter();
            out.print("{\"success\": false, \"message\": \"" + e.getMessage() + "\"}");
        }
    }

    private void handleRemoveFromCart(HttpServletRequest req, HttpServletResponse resp, Cart cart) throws IOException {
        try {
            Integer cartItemId = Integer.parseInt(req.getParameter("cartItemId"));
            cartService.removeCartItem(cartItemId);
            double total = cartService.getCartTotal(cart.getId());
            int count = cartService.getCartItemCount(cart.getId());

            PrintWriter out = resp.getWriter();
            out.print("{\"success\": true, \"cartTotal\": " + total + ", \"cartCount\": " + count + "}");
        } catch (Exception e) {
            PrintWriter out = resp.getWriter();
            out.print("{\"success\": false, \"message\": \"" + e.getMessage() + "\"}");
        }
    }
}
