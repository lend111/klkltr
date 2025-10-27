package org.example;

public class Expense {
    private String category;
    private double amount;
    private String date;

    public Expense(String category, double amount, String date) {
        if (category == null || category.trim().isEmpty()) {
            throw new IllegalArgumentException("Категория не может быть пустой");
        }
        if (date == null) {
            throw new IllegalArgumentException("Дата не может быть null");
        }

        this.category = category;
        this.amount = amount;
        this.date = date;
    }

    // Геттеры
    public String getCategory() {
        return category;
    }

    public double getAmount() {
        return amount;
    }

    public String getDate() {
        return date;
    }
}
