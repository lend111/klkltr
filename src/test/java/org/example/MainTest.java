package org.example;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.annotations.DataProvider;
import org.testng.annotations.BeforeMethod;
import java.util.ArrayList;
import java.util.List;

public class MainTest {

    // ========== ГРУППА: unit ==========
    @Test(groups = {"unit", "expense"})
    public void testExpenseCreation() {
        // ARRANGE
        final String category = "Еда";
        final double amount = 1500.50;
        final String date = "15.12.2023";

        // ACT
        Expense expense = new Expense(category, amount, date);

        // ASSERT
        Assert.assertEquals(expense.getCategory(), category);
        Assert.assertEquals(expense.getAmount(), amount);
        Assert.assertEquals(expense.getDate(), date);
    }

    @Test(groups = {"unit", "expense"})
    public void testExpenseGetters() {
        // ARRANGE
        final String category = "Транспорт";
        final double amount = 500.75;
        final String date = "20.12.2023";
        Expense expense = new Expense(category, amount, date);

        // ACT & ASSERT
        Assert.assertEquals(expense.getCategory(), "Транспорт");
        Assert.assertEquals(expense.getAmount(), 500.75);
        Assert.assertEquals(expense.getDate(), "20.12.2023");
    }

    // ========== ТЕСТЫ ИСКЛЮЧЕНИЙ ==========
    @Test(groups = {"exception", "boundary"},
            expectedExceptions = IllegalArgumentException.class)
    public void testExpenseWithEmptyCategory() {
        // ACT & ASSERT - ожидаем исключение
        new Expense("", 100.0, "01.01.2023");
    }

    @Test(groups = {"exception", "boundary"},
            expectedExceptions = IllegalArgumentException.class)
    public void testExpenseWithNullDate() {
        // ACT & ASSERT - ожидаем исключение
        new Expense("Категория", 100.0, null);
    }
    // ========== НОВЫЕ РАБОТАЮЩИЕ ТЕСТЫ ==========
    @Test(groups = {"integration", "calculation"})
    public void testMultipleExpensesTotal() {
        // Тест без использования поля класса
        List<Expense> expensesList = new ArrayList<>();
        expensesList.add(new Expense("Еда", 1000.0, "01.12.2023"));
        expensesList.add(new Expense("Транспорт", 500.0, "02.12.2023"));

        double total = expensesList.stream().mapToDouble(Expense::getAmount).sum();
        Assert.assertEquals(total, 1500.0);
    }

    @Test(groups = {"integration", "calculation"})
    public void testSingleExpenseAmount() {
        // Простой тест одного расхода
        Expense expense = new Expense("Развлечения", 2000.0, "03.12.2023");
        Assert.assertEquals(expense.getAmount(), 2000.0);
    }

    @Test(groups = {"integration", "calculation"})
    public void testExpenseCategoryValidation() {
        // Тест валидации категории
        Expense expense = new Expense("Супермаркет", 750.50, "04.12.2023");
        Assert.assertEquals(expense.getCategory(), "Супермаркет");
    }

    @Test(groups = {"boundary", "calculation"})
    public void testLargeAmountExpense() {
        // Тест с большой суммой
        Expense expense = new Expense("Недвижимость", 1_000_000.0, "05.12.2023");
        Assert.assertEquals(expense.getAmount(), 1_000_000.0);
    }
    // ========== DATAPROVIDER ТЕСТ ==========
    @DataProvider(name = "expenseData")
    public Object[][] provideExpenseData() {
        return new Object[][] {
                {"Еда", 500.0, "01.01.2023", 500.0},
                {"Транспорт", 250.75, "02.01.2023", 250.75},
                {"Развлечения", 1000.0, "03.01.2023", 1000.0},
                {"Комуналка", 3500.25, "04.01.2023", 3500.25}
        };
    }

    @Test(groups = {"dataprovider", "calculation"},
            dataProvider = "expenseData")
    public void testMultipleExpensesWithDataProvider(String category, double amount, String date, double expectedAmount) {
        // ARRANGE & ACT
        Expense expense = new Expense(category, amount, date);

        // ASSERT
        Assert.assertEquals(expense.getAmount(), expectedAmount);
        Assert.assertEquals(expense.getCategory(), category);
        Assert.assertEquals(expense.getDate(), date);
    }

    // ========== ДОПОЛНИТЕЛЬНЫЕ ТЕСТЫ ==========
    @Test(groups = {"boundary", "calculation"})
    public void testNegativeAmountExpense() {
        // ARRANGE
        final String category = "Возврат";
        final double amount = -1000.0;
        final String date = "05.12.2023";

        // ACT
        Expense expense = new Expense(category, amount, date);

        // ASSERT
        Assert.assertEquals(expense.getAmount(), -1000.0);
    }

    @Test(groups = {"boundary", "calculation"})
    public void testZeroAmountExpense() {
        // ARRANGE
        final String category = "Бесплатно";
        final double amount = 0.0;
        final String date = "06.12.2023";

        // ACT
        Expense expense = new Expense(category, amount, date);

        // ASSERT
        Assert.assertEquals(expense.getAmount(), 0.0);
    }

    @Test(groups = {"integration", "format"})
    public void testExpenseStringFormat() {
        // ARRANGE
        Expense expense = new Expense("Категория", 1234.56, "25.12.2023");

        // ACT
        String actualFormat = String.format("%s - %.2f руб. (%s)",
                expense.getCategory(), expense.getAmount(), expense.getDate());

        // ASSERT
        Assert.assertTrue(actualFormat.contains("Категория"));
        Assert.assertTrue(actualFormat.contains("руб."));
        Assert.assertTrue(actualFormat.contains("25.12.2023"));
    }
}