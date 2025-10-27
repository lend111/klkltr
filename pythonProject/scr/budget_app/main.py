from expense import Expense


def main():
    """Основная функция приложения"""
    print("=== Калькулятор бюджета ===")

    # Пример использования
    expenses = [
        Expense("Еда", 1500.50, "15.12.2023"),
        Expense("Транспорт", 500.75, "16.12.2023"),
        Expense("Развлечения", 2000.0, "17.12.2023")
    ]

    total = sum(expense.get_amount() for expense in expenses)
    print(f"Общая сумма расходов: {total:.2f} руб.")

    for expense in expenses:
        print(f"- {expense.to_string()}")


if __name__ == "__main__":
    main()