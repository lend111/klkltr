import pytest
import sys
import os

# Добавляем путь к src для импорта
sys.path.append(os.path.join(os.path.dirname(__file__), '../src'))

from scr.budget_app.expense import Expense


class TestExpense:
    """Тестовый класс для проверpytest -m unit -vки класса Expense"""

    @pytest.fixture
    def sample_expense(self):
        """Фикстура для создания тестового расхода"""
        return Expense("Еда", 1500.50, "15.12.2023")

    # ========== ГРУППА: unit ==========
    @pytest.mark.unit
    def test_expense_creation(self, sample_expense):
        """Тест создания объекта расхода"""
        assert sample_expense.get_category() == "Еда"
        assert sample_expense.get_amount() == 1500.50
        assert sample_expense.get_date() == "15.12.2023"

    @pytest.mark.unit
    def test_expense_methods(self):
        """Тест методов класса"""
        expense = Expense("Транспорт", 500.75, "20.12.2023")
        assert expense.get_category() == "Транспорт"
        assert expense.get_amount() == 500.75
        assert expense.get_date() == "20.12.2023"

    # ========== ГРУППА: integration ==========
    @pytest.mark.integration
    def test_multiple_expenses_total(self):
        """Тест расчета общей суммы нескольких расходов"""
        expenses = [
            Expense("Еда", 1000.0, "01.12.2023"),
            Expense("Транспорт", 500.0, "02.12.2023"),
            Expense("Развлечения", 1500.0, "03.12.2023")
        ]
        total = sum(expense.get_amount() for expense in expenses)
        assert total == 3000.0

    @pytest.mark.integration
    def test_expense_string_format(self, sample_expense):
        """Тест форматирования строки"""
        result = sample_expense.to_string()
        assert "Еда" in result
        assert "руб." in result
        assert "15.12.2023" in result

    @pytest.mark.integration
    def test_apply_discount(self, sample_expense):
        """Тест применения скидки"""
        discounted_amount = sample_expense.apply_discount(10)  # 10% скидка
        expected_amount = 1500.50 * 0.9
        assert discounted_amount == expected_amount

    @pytest.mark.integration
    def test_is_high_cost(self):
        """Тест проверки дорогостоящего расхода"""
        high_cost_expense = Expense("Техника", 2000.0, "10.12.2023")
        low_cost_expense = Expense("Канцелярия", 500.0, "11.12.2023")

        assert high_cost_expense.is_high_cost() is True
        assert low_cost_expense.is_high_cost() is False

    # ========== ТЕСТЫ ИСКЛЮЧЕНИЙ ==========
    @pytest.mark.exception
    def test_expense_empty_category(self):
        """Тест исключения при пустой категории"""
        with pytest.raises(ValueError, match="Категория не может быть пустой"):
            Expense("", 100.0, "01.01.2023")

    @pytest.mark.exception
    def test_expense_empty_date(self):
        """Тест исключения при пустой дате"""
        with pytest.raises(ValueError, match="Дата не может быть пустой"):
            Expense("Категория", 100.0, "")

    @pytest.mark.exception
    def test_expense_negative_amount(self):
        """Тест исключения при отрицательной сумме"""
        with pytest.raises(ValueError, match="Сумма не может быть отрицательной"):
            Expense("Категория", -100.0, "01.01.2023")

    @pytest.mark.exception
    def test_invalid_discount(self, sample_expense):
        """Тест исключения при невалидной скидке"""
        with pytest.raises(ValueError, match="Скидка должна быть от 0 до 100%"):
            sample_expense.apply_discount(150)

    # ========== PARAMETRIZE ТЕСТ ==========
    @pytest.mark.parametrize(
        "category, amount, date, expected_amount",
        [
            ("Еда", 500.0, "01.01.2023", 500.0),
            ("Транспорт", 250.75, "02.01.2023", 250.75),
            ("Развлечения", 1000.0, "03.01.2023", 1000.0),
            ("Комуналка", 3500.25, "04.01.2023", 3500.25)
        ]
    )
    def test_multiple_expenses_with_parametrize(self, category, amount, date, expected_amount):
        """Параметризованный тест с различными данными"""
        expense = Expense(category, amount, date)
        assert expense.get_amount() == expected_amount*2
        assert expense.get_category() == category
        assert expense.get_date() == date