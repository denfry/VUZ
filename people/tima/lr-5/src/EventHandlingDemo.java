import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.*;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;


public class EventHandlingDemo extends JFrame {
    
    // Поля формы
    private JComboBox<String> typeCombo;
    private JTextField gosnumberField;
    private JTextField markaField;
    private JTextField stsField;
    private JTextField modelField;
    private JTextField ptsField;
    private JTextField powerField;
    private JTextField massField;
    
    // Панель для отображения информации о событиях
    private JTextArea eventLogArea;
    
    // Цвета для фона при получении фокуса
    private static final Color FOCUS_COLOR = new Color(220, 255, 220); // Светло-зеленый
    private static final Color DEFAULT_COLOR = Color.WHITE;
    
    public EventHandlingDemo() {
        setTitle("Регистрация авто - Обработка событий");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        // Основной контейнер с BorderLayout
        Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout(10, 10));
        
        // Заголовок по центру вверху
        JLabel titleLabel = new JLabel("Регистрация авто", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        contentPane.add(titleLabel, BorderLayout.NORTH);
        
        // Центральная панель с формой ввода
        JPanel formPanel = new JPanel(new GridLayout(4, 4, 10, 10));
        formPanel.setBorder(BorderFactory.createTitledBorder("Форма регистрации"));
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Строка 1: Тип (левая колонка) и Госномер (правая колонка)
        formPanel.add(new JLabel("Тип:", SwingConstants.RIGHT));
        typeCombo = new JComboBox<>(new String[]{"Легковой", "Грузовой", "Мотоцикл", "Автобус"});
        formPanel.add(typeCombo);
        formPanel.add(new JLabel("Госномер:", SwingConstants.RIGHT));
        gosnumberField = new JTextField();
        formPanel.add(gosnumberField);
        
        // Строка 2: Марка (левая колонка) и СТС (правая колонка)
        formPanel.add(new JLabel("Марка:", SwingConstants.RIGHT));
        markaField = new JTextField();
        formPanel.add(markaField);
        formPanel.add(new JLabel("СТС:", SwingConstants.RIGHT));
        stsField = new JTextField();
        formPanel.add(stsField);
        
        // Строка 3: Модель (левая колонка) и ПТС (правая колонка)
        formPanel.add(new JLabel("Модель:", SwingConstants.RIGHT));
        modelField = new JTextField();
        formPanel.add(modelField);
        formPanel.add(new JLabel("ПТС:", SwingConstants.RIGHT));
        ptsField = new JTextField();
        formPanel.add(ptsField);
        
        // Строка 4: Мощность (левая колонка) и Масса (правая колонка)
        formPanel.add(new JLabel("Мощность:", SwingConstants.RIGHT));
        powerField = new JTextField();
        formPanel.add(powerField);
        formPanel.add(new JLabel("Масса:", SwingConstants.RIGHT));
        massField = new JTextField();
        formPanel.add(massField);
        
       
       
        setupFocusListeners();
        
        
        typeCombo.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    String selectedType = (String) e.getItem();
                    logEvent("ItemEvent: Выбран тип транспорта - " + selectedType);
                    JOptionPane.showMessageDialog(
                        EventHandlingDemo.this,
                        "Выбран тип: " + selectedType,
                        "Выбор типа",
                        JOptionPane.INFORMATION_MESSAGE
                    );
                }
            }
        });
        
       
        setupTextListeners();
        
        // Панель с формой и логом событий
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.add(formPanel, BorderLayout.CENTER);
        
        // Панель для отображения лога событий
        JPanel logPanel = new JPanel(new BorderLayout());
        logPanel.setBorder(BorderFactory.createTitledBorder("Лог событий"));
        eventLogArea = new JTextArea(8, 30);
        eventLogArea.setEditable(false);
        eventLogArea.setFont(new Font("Courier New", Font.PLAIN, 11));
        eventLogArea.setBackground(new Color(240, 240, 240));
        JScrollPane scrollPane = new JScrollPane(eventLogArea);
        logPanel.add(scrollPane, BorderLayout.CENTER);
        
        JButton clearLogButton = new JButton("Очистить лог");
        clearLogButton.addActionListener(e -> eventLogArea.setText(""));
        logPanel.add(clearLogButton, BorderLayout.SOUTH);
        
        mainPanel.add(logPanel, BorderLayout.SOUTH);
        contentPane.add(mainPanel, BorderLayout.CENTER);
        
        // Нижняя панель с кнопками
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        JButton saveButton = new JButton("Сохранить");
        JButton cancelButton = new JButton("Отмена");
        
        // Обработчик кнопки "Сохранить"
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showSaveOptions();
            }
        });
        
        // Обработчик кнопки "Отмена"
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearForm();
            }
        });
        
        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);
        
        contentPane.add(buttonPanel, BorderLayout.SOUTH);
        
        // Устанавливаем размеры окна
        setSize(600, 600);
        setMinimumSize(new Dimension(550, 500));
        
        logEvent("Программа запущена");
    }
    

    private void setupFocusListeners() {
        FocusListener focusListener = new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                JTextField field = (JTextField) e.getSource();
                field.setBackground(FOCUS_COLOR);
                logEvent("FocusEvent: Поле '" + getFieldName(field) + "' получило фокус");
            }
            
            @Override
            public void focusLost(FocusEvent e) {
                JTextField field = (JTextField) e.getSource();
                field.setBackground(DEFAULT_COLOR);
                logEvent("FocusEvent: Поле '" + getFieldName(field) + "' потеряло фокус");
            }
        };
        
        // Добавляем обработчик ко всем текстовым полям
        gosnumberField.addFocusListener(focusListener);
        markaField.addFocusListener(focusListener);
        stsField.addFocusListener(focusListener);
        modelField.addFocusListener(focusListener);
        ptsField.addFocusListener(focusListener);
        powerField.addFocusListener(focusListener);
        massField.addFocusListener(focusListener);
        
        // Устанавливаем начальный цвет фона
        gosnumberField.setBackground(DEFAULT_COLOR);
        markaField.setBackground(DEFAULT_COLOR);
        stsField.setBackground(DEFAULT_COLOR);
        modelField.setBackground(DEFAULT_COLOR);
        ptsField.setBackground(DEFAULT_COLOR);
        powerField.setBackground(DEFAULT_COLOR);
        massField.setBackground(DEFAULT_COLOR);
    }
    
  
    private void setupTextListeners() {
        DocumentListener documentListener = new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                handleTextChange(e);
            }
            
            @Override
            public void removeUpdate(DocumentEvent e) {
                handleTextChange(e);
            }
            
            @Override
            public void changedUpdate(DocumentEvent e) {
                handleTextChange(e);
            }
            
            private void handleTextChange(DocumentEvent e) {
                // Находим поле по документу
                JTextField field = findFieldByDocument(e.getDocument());
                if (field != null) {
                    String fieldName = getFieldName(field);
                    String text = field.getText();
                    logEvent("TextEvent: Изменен текст в поле '" + fieldName + "': " + 
                            (text.isEmpty() ? "(пусто)" : text));
                }
            }
            
            private JTextField findFieldByDocument(javax.swing.text.Document doc) {
                if (gosnumberField.getDocument() == doc) return gosnumberField;
                if (markaField.getDocument() == doc) return markaField;
                if (stsField.getDocument() == doc) return stsField;
                if (modelField.getDocument() == doc) return modelField;
                if (ptsField.getDocument() == doc) return ptsField;
                if (powerField.getDocument() == doc) return powerField;
                if (massField.getDocument() == doc) return massField;
                return null;
            }
        };
        
        // Добавляем обработчик ко всем текстовым полям через их документы
        gosnumberField.getDocument().addDocumentListener(documentListener);
        markaField.getDocument().addDocumentListener(documentListener);
        stsField.getDocument().addDocumentListener(documentListener);
        modelField.getDocument().addDocumentListener(documentListener);
        ptsField.getDocument().addDocumentListener(documentListener);
        powerField.getDocument().addDocumentListener(documentListener);
        massField.getDocument().addDocumentListener(documentListener);
    }
    
    private String getFieldName(JTextField field) {
        if (field == gosnumberField) return "Госномер";
        if (field == markaField) return "Марка";
        if (field == stsField) return "СТС";
        if (field == modelField) return "Модель";
        if (field == ptsField) return "ПТС";
        if (field == powerField) return "Мощность";
        if (field == massField) return "Масса";
        return "Неизвестное поле";
    }
    
    private void logEvent(String message) {
        if (eventLogArea != null) {
            String timestamp = String.format("%tH:%tM:%tS", System.currentTimeMillis(), 
                                           System.currentTimeMillis(), System.currentTimeMillis());
            eventLogArea.append("[" + timestamp + "] " + message + "\n");
            // Автоматическая прокрутка вниз
            eventLogArea.setCaretPosition(eventLogArea.getDocument().getLength());
        }
    }
    
    private void showSaveOptions() {
        String[] options = {"Сохранить в файл", "Показать данные", "Отмена"};
        int choice = JOptionPane.showOptionDialog(
            this,
            "Выберите вариант сохранения:",
            "Сохранение данных",
            JOptionPane.YES_NO_CANCEL_OPTION,
            JOptionPane.QUESTION_MESSAGE,
            null,
            options,
            options[0]
        );
        
        switch (choice) {
            case 0: // Сохранить в файл
                saveToFile();
                break;
            case 1: // Показать данные
                showData();
                break;
            case 2: // Отмена
                break;
        }
    }
    
    private void saveToFile() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Сохранить данные");
        int userSelection = fileChooser.showSaveDialog(this);
        
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            try {
                String filename = fileChooser.getSelectedFile().getAbsolutePath();
                if (!filename.endsWith(".txt")) {
                    filename += ".txt";
                }
                
                PrintWriter writer = new PrintWriter(new FileWriter(filename));
                writer.println("=== РЕГИСТРАЦИЯ АВТО ===");
                writer.println("Тип: " + (typeCombo.getSelectedItem() != null ? typeCombo.getSelectedItem() : ""));
                writer.println("Марка: " + markaField.getText());
                writer.println("Модель: " + modelField.getText());
                writer.println("Мощность: " + powerField.getText());
                writer.println("Госномер: " + gosnumberField.getText());
                writer.println("СТС: " + stsField.getText());
                writer.println("ПТС: " + ptsField.getText());
                writer.println("Масса: " + massField.getText());
                writer.close();
                
                logEvent("Данные сохранены в файл: " + filename);
                JOptionPane.showMessageDialog(
                    this,
                    "Данные успешно сохранены в файл:\n" + filename,
                    "Успех",
                    JOptionPane.INFORMATION_MESSAGE
                );
            } catch (IOException e) {
                logEvent("Ошибка при сохранении: " + e.getMessage());
                JOptionPane.showMessageDialog(
                    this,
                    "Ошибка при сохранении файла:\n" + e.getMessage(),
                    "Ошибка",
                    JOptionPane.ERROR_MESSAGE
                );
            }
        }
    }
    
    private void showData() {
        StringBuilder data = new StringBuilder();
        data.append("=== РЕГИСТРАЦИЯ АВТО ===\n\n");
        data.append("Тип: ").append(typeCombo.getSelectedItem() != null ? typeCombo.getSelectedItem() : "").append("\n");
        data.append("Марка: ").append(markaField.getText()).append("\n");
        data.append("Модель: ").append(modelField.getText()).append("\n");
        data.append("Мощность: ").append(powerField.getText()).append("\n");
        data.append("Госномер: ").append(gosnumberField.getText()).append("\n");
        data.append("СТС: ").append(stsField.getText()).append("\n");
        data.append("ПТС: ").append(ptsField.getText()).append("\n");
        data.append("Масса: ").append(massField.getText());
        
        logEvent("Показаны данные регистрации");
        JOptionPane.showMessageDialog(
            this,
            data.toString(),
            "Данные регистрации",
            JOptionPane.INFORMATION_MESSAGE
        );
    }
    
    private void clearForm() {
        int confirm = JOptionPane.showConfirmDialog(
            this,
            "Вы уверены, что хотите очистить все поля?",
            "Очистка формы",
            JOptionPane.YES_NO_OPTION
        );
        
        if (confirm == JOptionPane.YES_OPTION) {
            typeCombo.setSelectedIndex(0);
            gosnumberField.setText("");
            markaField.setText("");
            stsField.setText("");
            modelField.setText("");
            ptsField.setText("");
            powerField.setText("");
            massField.setText("");
            logEvent("Форма очищена");
        }
    }
    
    public static void main(String[] args) {
        // Устанавливаем системный Look and Feel
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        // Запускаем приложение в потоке событий
        SwingUtilities.invokeLater(() -> {
            new EventHandlingDemo().setVisible(true);
        });
    }
}

