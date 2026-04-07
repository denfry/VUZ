import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.*;

/**
 * Лабораторная работа 5
 * Обработка событий в Java
 * 
 * Расширенная форма "Информация о сотруднике" с обработкой различных событий:
 * 1. События фокуса (FocusEvent) - изменение цвета фона при получении/потере фокуса
 * 2. События движения мыши (MouseMotionEvent) - отображение координат мыши
 * 3. События изменения текста (DocumentEvent) - вывод информации об изменении текста
 */
public class EmployeeFormWithEvents extends JFrame {
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
    
    // Панель для отображения информации о событиях
    private JLabel mouseCoordinatesLabel;  // Метка для координат мыши
    private JTextArea eventLogArea;       // Область для лога событий
    
    // Цвета для фокуса
    private Color focusColor = new Color(200, 255, 200); // Светло-зеленый
    private Color defaultColor = Color.WHITE;
    
    public EmployeeFormWithEvents() {
        // Настройка фрейма
        setTitle("Информация о сотруднике - Обработка событий");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(700, 500);
        setLocationRelativeTo(null);
        setResizable(true);
        
        // Добавление обработчика событий окна
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowOpened(WindowEvent e) {
                logEvent("Окно открыто");
            }
            
            @Override
            public void windowClosing(WindowEvent e) {
                logEvent("Окно закрывается");
            }
            
            @Override
            public void windowIconified(WindowEvent e) {
                logEvent("Окно свернуто");
            }
            
            @Override
            public void windowDeiconified(WindowEvent e) {
                logEvent("Окно восстановлено");
            }
        });
        
        // Создание компонентов
        createComponents();
        
        // Размещение компонентов
        layoutComponents();
        
        // Регистрация обработчиков событий
        registerEventHandlers();
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
        
        // Компоненты для отображения событий
        mouseCoordinatesLabel = new JLabel("Координаты мыши: X=0, Y=0");
        mouseCoordinatesLabel.setBorder(BorderFactory.createLoweredBevelBorder());
        mouseCoordinatesLabel.setOpaque(true);
        mouseCoordinatesLabel.setBackground(new Color(240, 240, 240));
        
        eventLogArea = new JTextArea(8, 40);
        eventLogArea.setEditable(false);
        eventLogArea.setFont(new Font("Monospaced", Font.PLAIN, 11));
        eventLogArea.setBackground(new Color(250, 250, 250));
        eventLogArea.setBorder(BorderFactory.createTitledBorder("Лог событий"));
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
        
        // Панель с формой
        JPanel formContainer = new JPanel(new BorderLayout());
        formContainer.add(formPanel, BorderLayout.CENTER);
        
        // Панель с кнопками
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);
        formContainer.add(buttonPanel, BorderLayout.SOUTH);
        
        // Левая панель с формой
        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.add(formContainer, BorderLayout.CENTER);
        leftPanel.add(mouseCoordinatesLabel, BorderLayout.SOUTH);
        
        // Правая панель с логом событий
        JScrollPane scrollPane = new JScrollPane(eventLogArea);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        
        // Разделение на две части
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPanel, scrollPane);
        splitPane.setDividerLocation(400);
        splitPane.setResizeWeight(0.5);
        
        contentPane.add(splitPane, BorderLayout.CENTER);
    }
    
    /**
     * Регистрация обработчиков событий
     */
    private void registerEventHandlers() {
        // 1. Обработка событий фокуса (FocusEvent)
        // При получении фокуса - устанавливаем цветной фон, при потере - белый
        FocusListener focusListener = new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                Component component = (Component) e.getSource();
                component.setBackground(focusColor);
                String componentName = getComponentName(component);
                logEvent("Получен фокус: " + componentName);
            }
            
            @Override
            public void focusLost(FocusEvent e) {
                Component component = (Component) e.getSource();
                component.setBackground(defaultColor);
                String componentName = getComponentName(component);
                logEvent("Потерян фокус: " + componentName);
            }
        };
        
        // Регистрация обработчика фокуса для всех текстовых полей
        lastNameField.addFocusListener(focusListener);
        firstNameField.addFocusListener(focusListener);
        patronymicField.addFocusListener(focusListener);
        salaryField.addFocusListener(focusListener);
        enrollmentDateField.addFocusListener(focusListener);
        departmentCombo.addFocusListener(focusListener);
        positionCombo.addFocusListener(focusListener);
        
        // 2. Обработка событий движения мыши (MouseMotionEvent)
        // Отображение координат мыши при перемещении в области формы
        MouseMotionListener mouseMotionListener = new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                int x = e.getX();
                int y = e.getY();
                mouseCoordinatesLabel.setText(String.format("Координаты мыши: X=%d, Y=%d", x, y));
            }
        };
        
        // Регистрация обработчика движения мыши для всей формы
        addMouseMotionListener(mouseMotionListener);
        getContentPane().addMouseMotionListener(mouseMotionListener);
        
        // 3. Обработка событий изменения текста (DocumentEvent)
        // Вывод информации об изменении текста в поле ввода
        DocumentListener documentListener = new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                handleTextChange(e, "добавлен текст");
            }
            
            @Override
            public void removeUpdate(DocumentEvent e) {
                handleTextChange(e, "удален текст");
            }
            
            @Override
            public void changedUpdate(DocumentEvent e) {
                handleTextChange(e, "изменен текст");
            }
            
            private void handleTextChange(DocumentEvent e, String action) {
                try {
                    javax.swing.text.Document doc = e.getDocument();
                    if (doc instanceof javax.swing.text.PlainDocument) {
                        JTextField source = findTextField(doc);
                        if (source != null) {
                            String text = source.getText();
                            String componentName = getComponentName(source);
                            logEvent(String.format("Изменение текста в '%s': %s (длина: %d)", 
                                    componentName, action, text.length()));
                        }
                    }
                } catch (Exception ex) {
                    // Игнорируем ошибки
                }
            }
        };
        
        // Регистрация обработчика изменения текста для всех текстовых полей
        lastNameField.getDocument().addDocumentListener(documentListener);
        firstNameField.getDocument().addDocumentListener(documentListener);
        patronymicField.getDocument().addDocumentListener(documentListener);
        salaryField.getDocument().addDocumentListener(documentListener);
        enrollmentDateField.getDocument().addDocumentListener(documentListener);
    }
    
    /**
     * Получение имени компонента для лога
     */
    private String getComponentName(Component component) {
        if (component == lastNameField) return "Фамилия";
        if (component == firstNameField) return "Имя";
        if (component == patronymicField) return "Отчество";
        if (component == departmentCombo) return "Отдел";
        if (component == positionCombo) return "Должность";
        if (component == salaryField) return "Оклад";
        if (component == enrollmentDateField) return "Дата зачисления";
        return component.getClass().getSimpleName();
    }
    
    /**
     * Поиск JTextField по документу
     */
    private JTextField findTextField(javax.swing.text.Document doc) {
        if (lastNameField.getDocument() == doc) return lastNameField;
        if (firstNameField.getDocument() == doc) return firstNameField;
        if (patronymicField.getDocument() == doc) return patronymicField;
        if (salaryField.getDocument() == doc) return salaryField;
        if (enrollmentDateField.getDocument() == doc) return enrollmentDateField;
        return null;
    }
    
    /**
     * Добавление записи в лог событий
     */
    private void logEvent(String message) {
        String timestamp = java.time.LocalTime.now().toString().substring(0, 8);
        eventLogArea.append(String.format("[%s] %s\n", timestamp, message));
        eventLogArea.setCaretPosition(eventLogArea.getDocument().getLength());
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
        
        logEvent("Сохранение данных сотрудника");
        
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
        logEvent("Очистка всех полей формы");
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
                new EmployeeFormWithEvents().setVisible(true);
            }
        });
    }
}

