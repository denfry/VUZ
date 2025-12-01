import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Лабораторная работа 4
 * Форма "Информация о сотруднике"
 * 
 * Интерфейс для ввода информации о сотруднике с использованием
 * различных компонентов Swing (JTextField, JComboBox, JButton)
 */
public class EmployeeForm extends JFrame {
    // Поля ввода
    private JTextField lastNameField;      // Фамилия
    private JTextField firstNameField;     // Имя
    private JTextField patronymicField;    // Отчество
    private JComboBox<String> departmentCombo;  // Отдел
    private JComboBox<String> positionCombo;   // Должность
    private JTextField salaryField;       // Оклад
    private JTextField enrollmentDateField; // Дата зачисления
    
    // Кнопки
    private JButton saveButton;          // Сохранить
    private JButton cancelButton;        // Отмена
    
    public EmployeeForm() {
        // Настройка фрейма
        setTitle("Информация о сотруднике");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 300);
        setLocationRelativeTo(null);
        setResizable(true);
        
        // Создание компонентов
        createComponents();
        
        // Размещение компонентов
        layoutComponents();
    }
    
    /**
     * Создание компонентов интерфейса
     */
    private void createComponents() {
        // Текстовые поля
        lastNameField = new JTextField(25);
        firstNameField = new JTextField(25);
        patronymicField = new JTextField(25);
        salaryField = new JTextField(25);
        enrollmentDateField = new JTextField(25);
        
        // Выпадающие списки
        String[] departments = {"", "Отдел разработки", "Отдел тестирования", 
                               "Отдел маркетинга", "Отдел продаж", "HR отдел"};
        departmentCombo = new JComboBox<>(departments);
        departmentCombo.setEditable(true);
        departmentCombo.setPreferredSize(new Dimension(250, 25));
        
        String[] positions = {"", "Разработчик", "Тестировщик", "Менеджер", 
                            "Дизайнер", "Аналитик", "Руководитель"};
        positionCombo = new JComboBox<>(positions);
        positionCombo.setEditable(true);
        positionCombo.setPreferredSize(new Dimension(250, 25));
        
        // Кнопки
        saveButton = new JButton("Сохранить");
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveEmployee();
            }
        });
        
        cancelButton = new JButton("Отмена");
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cancelAction();
            }
        });
    }
    
    /**
     * Размещение компонентов на панели
     */
    private void layoutComponents() {
        Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout(10, 10));
        ((JComponent) contentPane).setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        
        // Центральная панель с формой
        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.anchor = GridBagConstraints.WEST;
        
        // ФИО идут подряд (вертикально)
        // Фамилия
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        formPanel.add(new JLabel("Фамилия:"), gbc);
        
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        formPanel.add(lastNameField, gbc);
        
        // Имя
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        formPanel.add(new JLabel("Имя:"), gbc);
        
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        formPanel.add(firstNameField, gbc);
        
        // Отчество
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        formPanel.add(new JLabel("Отчество:"), gbc);
        
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        formPanel.add(patronymicField, gbc);
        
        // Отдел и Должность в одной строке (квадрат 2x2)
        // Отдел (левая колонка)
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        formPanel.add(new JLabel("Отдел:"), gbc);
        
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 0.5;
        formPanel.add(departmentCombo, gbc);
        
        // Должность (правая колонка)
        gbc.gridx = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        formPanel.add(new JLabel("Должность:"), gbc);
        
        gbc.gridx = 3;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 0.5;
        formPanel.add(positionCombo, gbc);
        
        // Оклад и Дата зачисления в одной строке (квадрат 2x2)
        // Оклад (левая колонка)
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        formPanel.add(new JLabel("Оклад:"), gbc);
        
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 0.5;
        formPanel.add(salaryField, gbc);
        
        // Дата зачисления (правая колонка)
        gbc.gridx = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        formPanel.add(new JLabel("Дата зачисления:"), gbc);
        
        gbc.gridx = 3;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 0.5;
        formPanel.add(enrollmentDateField, gbc);
        
        contentPane.add(formPanel, BorderLayout.CENTER);
        
        // Панель с кнопками внизу
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);
        contentPane.add(buttonPanel, BorderLayout.SOUTH);
    }
    
    /**
     * Обработка нажатия кнопки "Сохранить"
     */
    private void saveEmployee() {
        // Получение данных из полей
        String lastName = lastNameField.getText().trim();
        String firstName = firstNameField.getText().trim();
        String patronymic = patronymicField.getText().trim();
        String department = departmentCombo.getSelectedItem() != null ? 
                           departmentCombo.getSelectedItem().toString() : "";
        String position = positionCombo.getSelectedItem() != null ? 
                         positionCombo.getSelectedItem().toString() : "";
        String salary = salaryField.getText().trim();
        String enrollmentDate = enrollmentDateField.getText().trim();
        
        // Простая валидация
        if (lastName.isEmpty() || firstName.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "Пожалуйста, заполните обязательные поля: Фамилия и Имя",
                "Ошибка", 
                JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Формирование сообщения
        StringBuilder message = new StringBuilder();
        message.append("Информация о сотруднике:\n\n");
        message.append("Фамилия: ").append(lastName).append("\n");
        message.append("Имя: ").append(firstName).append("\n");
        if (!patronymic.isEmpty()) {
            message.append("Отчество: ").append(patronymic).append("\n");
        }
        if (!department.isEmpty()) {
            message.append("Отдел: ").append(department).append("\n");
        }
        if (!position.isEmpty()) {
            message.append("Должность: ").append(position).append("\n");
        }
        if (!salary.isEmpty()) {
            message.append("Оклад: ").append(salary).append("\n");
        }
        if (!enrollmentDate.isEmpty()) {
            message.append("Дата зачисления: ").append(enrollmentDate).append("\n");
        }
        
        JOptionPane.showMessageDialog(this, 
            message.toString(),
            "Сохранено", 
            JOptionPane.INFORMATION_MESSAGE);
    }
    
    /**
     * Обработка нажатия кнопки "Отмена"
     */
    private void cancelAction() {
        // Очистка всех полей
        lastNameField.setText("");
        firstNameField.setText("");
        patronymicField.setText("");
        departmentCombo.setSelectedIndex(0);
        positionCombo.setSelectedIndex(0);
        salaryField.setText("");
        enrollmentDateField.setText("");
    }
    
    /**
     * Главный метод для запуска приложения
     */
    public static void main(String[] args) {
        // Установка Look and Feel для лучшего внешнего вида
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        // Запуск приложения
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new EmployeeForm().setVisible(true);
            }
        });
    }
}

