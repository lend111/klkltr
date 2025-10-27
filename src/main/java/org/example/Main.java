package org.example;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

// Главный класс приложения
public class Main extends JFrame {
    private List<Expense> expenses;
    private DefaultListModel<String> listModel;
    private JList<String> expenseList;
    private JTextField categoryField;
    private JTextField amountField;
    private JTextField dateField;
    private JLabel totalLabel;

    public Main() {
        expenses = new ArrayList<>();
        initializeUI();
    }

    private void initializeUI() {
        setTitle("Калькулятор бюджета v1.0");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 400);
        setLocationRelativeTo(null);

        // Основная панель
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Панель ввода данных
        JPanel inputPanel = createInputPanel();
        mainPanel.add(inputPanel, BorderLayout.NORTH);

        // Панель списка расходов
        JPanel listPanel = createListPanel();
        mainPanel.add(listPanel, BorderLayout.CENTER);

        // Панель итогов
        JPanel summaryPanel = createSummaryPanel();
        mainPanel.add(summaryPanel, BorderLayout.SOUTH);

        add(mainPanel);
    }

    private JPanel createInputPanel() {
        JPanel panel = new JPanel(new GridLayout(2, 4, 5, 5));

        // Метки
        panel.add(new JLabel("Категория:"));
        panel.add(new JLabel("Сумма:"));
        panel.add(new JLabel("Дата (дд.мм.гггг):"));
        panel.add(new JLabel(""));

        // Поля ввода
        categoryField = new JTextField();
        amountField = new JTextField();
        dateField = new JTextField();

        panel.add(categoryField);
        panel.add(amountField);
        panel.add(dateField);

        // Кнопка добавления
        JButton addButton = new JButton("Добавить расход");
        addButton.addActionListener(new AddExpenseListener());
        panel.add(addButton);

        return panel;
    }

    private JPanel createListPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Список расходов"));

        listModel = new DefaultListModel<>();
        expenseList = new JList<>(listModel);
        JScrollPane scrollPane = new JScrollPane(expenseList);

        // Кнопка удаления
        JButton deleteButton = new JButton("Удалить выбранный");
        deleteButton.addActionListener(new DeleteExpenseListener());

        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(deleteButton, BorderLayout.SOUTH);

        return panel;
    }

    private JPanel createSummaryPanel() {
        JPanel panel = new JPanel(new FlowLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Итоги"));

        totalLabel = new JLabel("Общая сумма: 0.00 руб.");
        panel.add(totalLabel);

        // Кнопка расчета
        JButton calculateButton = new JButton("Пересчитать");
        calculateButton.addActionListener(e -> updateTotal());
        panel.add(calculateButton);

        return panel;
    }

    // Обработчик добавления расхода
    private class AddExpenseListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                String category = categoryField.getText().trim();
                double amount = Double.parseDouble(amountField.getText().trim());
                String date = dateField.getText().trim();

                if (category.isEmpty() || date.isEmpty()) {
                    JOptionPane.showMessageDialog(Main.this,
                            "Заполните все поля!", "Ошибка", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                Expense expense = new Expense(category, amount, date);
                expenses.add(expense);
                listModel.addElement(String.format("%s - %.2f руб. (%s)", category, amount, date));

                // Очистка полей
                categoryField.setText("");
                amountField.setText("");
                dateField.setText("");

                updateTotal();

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(Main.this,
                        "Введите корректную сумму!", "Ошибка", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // Обработчик удаления расхода
    private class DeleteExpenseListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int selectedIndex = expenseList.getSelectedIndex();
            if (selectedIndex != -1) {
                expenses.remove(selectedIndex);
                listModel.remove(selectedIndex);
                updateTotal();
            }
        }
    }

    // Обновление общей суммы
    private void updateTotal() {
        double total = expenses.stream().mapToDouble(Expense::getAmount).sum();
        totalLabel.setText(String.format("Общая сумма: %.2f руб.", total));
    }

    // Точка входа
    public static void main(String[] args) {
        // Установка системного look and feel для кроссплатформенности
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(() -> {
            new Main().setVisible(true);
        });
    }
}

