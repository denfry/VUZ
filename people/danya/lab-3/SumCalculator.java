import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Лабораторная работа 3
 * Вариант 19
 * 
 * Создать фрейм. Поместить на панели него поля ввода слагаемых и кнопку.
 * При нажатии кнопки вычислять сумму значений из полей и помещать ее в еще одно поле.
 */
public class SumCalculator extends JFrame {
    private JTextField firstNumberField;   // Поле для первого слагаемого
    private JTextField secondNumberField;  // Поле для второго слагаемого
    private JTextField resultField;         // Поле для результата
    private JButton calculateButton;        // Кнопка вычисления
    
    public SumCalculator() {
        // Настройка фрейма
        setTitle("Калькулятор суммы");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(450, 250);
        setLocationRelativeTo(null);
        setResizable(false);
        
        // Создание компонентов
        createComponents();
        
        // Размещение компонентов
        layoutComponents();
        
        // Упаковка компонентов
        pack();
    }
    
    /**
     * Создание компонентов интерфейса
     */
    private void createComponents() {
        firstNumberField = new JTextField(15);
        firstNumberField.setFont(new Font("Arial", Font.PLAIN, 14));
        
        secondNumberField = new JTextField(15);
        secondNumberField.setFont(new Font("Arial", Font.PLAIN, 14));
        
        resultField = new JTextField(15);
        resultField.setFont(new Font("Arial", Font.BOLD, 14));
        resultField.setEditable(false);  // Поле результата только для чтения
        resultField.setBackground(Color.LIGHT_GRAY);
        
        calculateButton = new JButton("Вычислить сумму");
        calculateButton.setFont(new Font("Arial", Font.BOLD, 14));
        calculateButton.setPreferredSize(new Dimension(200, 35));
        
        // Обработчик события нажатия кнопки
        calculateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                calculateSum();
            }
        });
        
        // Обработка нажатия Enter в полях ввода
        firstNumberField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                calculateSum();
            }
        });
        
        secondNumberField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                calculateSum();
            }
        });
    }
    
    /**
     * Размещение компонентов на панели
     */
    private void layoutComponents() {
        // Создание основной панели
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridBagLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        // Первое слагаемое
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.weightx = 0.3;
        mainPanel.add(new JLabel("Первое слагаемое:"), gbc);
        
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.weightx = 0.7;
        mainPanel.add(firstNumberField, gbc);
        
        // Второе слагаемое
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.weightx = 0.3;
        mainPanel.add(new JLabel("Второе слагаемое:"), gbc);
        
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.weightx = 0.7;
        mainPanel.add(secondNumberField, gbc);
        
        // Кнопка
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.NONE;
        mainPanel.add(calculateButton, gbc);
        
        // Результат
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.weightx = 0.3;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        mainPanel.add(new JLabel("Сумма:"), gbc);
        
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.weightx = 0.7;
        mainPanel.add(resultField, gbc);
        
        // Добавление панели на фрейм
        add(mainPanel);
    }
    
    /**
     * Вычисление суммы значений из полей ввода
     */
    private void calculateSum() {
        try {
            // Получение значений из полей ввода
            String firstText = firstNumberField.getText().trim();
            String secondText = secondNumberField.getText().trim();
            
            // Проверка на пустые поля
            if (firstText.isEmpty() || secondText.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                    "Пожалуйста, введите оба слагаемых!",
                    "Ошибка ввода",
                    JOptionPane.WARNING_MESSAGE);
                resultField.setText("");
                return;
            }
            
            // Парсинг чисел
            double firstNumber = Double.parseDouble(firstText);
            double secondNumber = Double.parseDouble(secondText);
            
            // Вычисление суммы
            double sum = firstNumber + secondNumber;
            
            // Вывод результата в поле
            resultField.setText(String.format("%.2f", sum));
            
        } catch (NumberFormatException e) {
            // Обработка ошибки неверного формата числа
            JOptionPane.showMessageDialog(this,
                "Ошибка: Введите корректные числа!",
                "Ошибка формата",
                JOptionPane.ERROR_MESSAGE);
            resultField.setText("");
        }
    }
    
    /**
     * Точка входа в приложение
     */
    public static void main(String[] args) {
        // Установка Look and Feel системы
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        // Создание и отображение фрейма
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new SumCalculator().setVisible(true);
            }
        });
    }
}

