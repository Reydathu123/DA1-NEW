package com.example.da1nhom9jav102.util;

public class JdbcMain {
    public static void main(String[] args) {
        DatabaseConnectionManager dcm = new DatabaseConnectionManager("da1nhom9jav102", "sa", "123456789");
        try (var em = EntityManagerUtils.getEntityManager()) {

            System.out.println("Create table ....");

        } catch (Exception e) {

            System.out.println("Failed to create table ....");

            e.printStackTrace();
        }
    }
}
