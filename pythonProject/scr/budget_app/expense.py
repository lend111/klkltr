class Expense:
    """Класс для представления расхода"""

    def __init__(self, category: str, amount: float, date: str):
        if not category or not category.strip():
            raise ValueError("Категория не может быть пустой")
        if not date:
            raise ValueError("Дата не может быть пустой")
        if amount < 0:
            raise ValueError("Сумма не может быть отрицательной")

        self.category = category
        self.amount = amount
        self.date = date

    def get_category(self) -> str:
        return self.category

    def get_amount(self) -> float:
        return self.amount

    def get_date(self) -> str:
        return self.date

    def to_string(self) -> str:
        """Возвращает строковое представление расхода"""
        return f"{self.category} - {self.amount:.2f} руб. ({self.date})"

    def apply_discount(self, discount_percent: float) -> float:
        """Применяет скидку к сумме расхода"""
        if discount_percent < 0 or discount_percent > 100:
            raise ValueError("Скидка должна быть от 0 до 100%")
        return self.amount * (1 - discount_percent / 100)

    def is_high_cost(self, threshold: float = 1000.0) -> bool:
        """Проверяет, является ли расход дорогостоящим"""
        return self.amount > threshold