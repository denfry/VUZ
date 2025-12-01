import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.*;

/**
 * Лабораторная работа 5
 * Обработка событий в Java
 * 
 * Расширенная форма "Нарушение ПДД" с обработкой различных событий:
 * 1. События фокуса (FocusEvent) - изменение цвета фона при получении/потере фокуса
 * 2. События движения мыши (MouseMotionEvent) - отображение координат мыши
 * 3. События изменения текста (TextEvent) - вывод информации об изменении текста
 */
public class EventHandlingForm extends JFrame {
    // Поля для п. ПДД
    private JTextField pddField1;
    private JTextField pddField2;
    private JButton pddButton;
    
    // Поля для ст. КОАП
    private JTextField koapField1;
    private JTextField koapField2;
    private JButton koapButton;
    
    // Поля для кода нарушения
    private JTextField violationCodeField;
    private JComboBox<String> violationCodeCombo;
    
    // Поле для фактических сведений
    private JTextArea factualInfoArea;
    
    // Кнопки действий
    private JButton saveButton;
    private JButton cancelButton;
    private JButton fillTestDataButton;
    
    // Панель для отображения информации о событиях
    private JPanel mainPanel;
    private JLabel mouseCoordinatesLabel;
    private JTextArea eventLogArea;
    
    // Цвета для фокуса
    private Color focusColor = new Color(255, 255, 200); // Светло-желтый
    private Color defaultColor = Color.WHITE;
    
    public EventHandlingForm() {
        // Настройка фрейма
        setTitle("Нарушение ПДД - Обработка событий");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(700, 650);
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
            public void windowClosed(WindowEvent e) {
                logEvent("Окно закрыто");
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
        // Поля п. ПДД
        pddField1 = new JTextField(8);
        pddField2 = new JTextField(8);
        pddButton = new JButton("...");
        pddButton.setPreferredSize(new Dimension(30, 25));
        pddButton.addActionListener(e -> showPddDialog());
        
        // Поля ст. КОАП
        koapField1 = new JTextField(8);
        koapField2 = new JTextField(8);
        koapButton = new JButton("...");
        koapButton.setPreferredSize(new Dimension(30, 25));
        koapButton.addActionListener(e -> showKoapDialog());
        
        // Поля кода нарушения
        violationCodeField = new JTextField(8);
        String[] violationCodes = {"", "12.1", "12.2", "12.3", "12.4", "12.5", "12.6", "12.7", "12.8", "12.9"};
        violationCodeCombo = new JComboBox<>(violationCodes);
        violationCodeCombo.setEditable(true);
        violationCodeCombo.addActionListener(e -> {
            if (violationCodeCombo.getSelectedItem() != null) {
                violationCodeField.setText(violationCodeCombo.getSelectedItem().toString());
            }
        });
        
        // Поле фактических сведений
        factualInfoArea = new JTextArea(6, 40);
        factualInfoArea.setLineWrap(true);
        factualInfoArea.setWrapStyleWord(true);
        
        // Кнопки действий
        saveButton = new JButton("Сохранить");
        saveButton.addActionListener(e -> saveData());
        
        cancelButton = new JButton("Отмена");
        cancelButton.addActionListener(e -> cancelAction());
        
        fillTestDataButton = new JButton("Заполнить тестовыми данными");
        fillTestDataButton.addActionListener(e -> fillTestData());
        
        // Метка для координат мыши
        mouseCoordinatesLabel = new JLabel("Координаты мыши: X=0, Y=0");
        mouseCoordinatesLabel.setBorder(BorderFactory.createLoweredBevelBorder());
        mouseCoordinatesLabel.setHorizontalAlignment(JLabel.CENTER);
        
        // Область для лога событий
        eventLogArea = new JTextArea(8, 50);
        eventLogArea.setEditable(false);
        eventLogArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 11));
        eventLogArea.setBackground(new Color(240, 240, 240));
    }
    
    /**
     * Размещение компонентов на панели
     */
    private void layoutComponents() {
        // Создание основной панели
        mainPanel = new JPanel();
        mainPanel.setLayout(new GridBagLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.anchor = GridBagConstraints.WEST;
        
        // п. ПДД
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.0;
        mainPanel.add(new JLabel("п. ПДД:"), gbc);
        
        gbc.gridx = 1;
        gbc.weightx = 0.0;
        mainPanel.add(pddField1, gbc);
        
        gbc.gridx = 2;
        mainPanel.add(pddField2, gbc);
        
        gbc.gridx = 3;
        mainPanel.add(pddButton, gbc);
        
        // ст. КОАП
        gbc.gridx = 0;
        gbc.gridy = 1;
        mainPanel.add(new JLabel("ст. КОАП:"), gbc);
        
        gbc.gridx = 1;
        mainPanel.add(koapField1, gbc);
        
        gbc.gridx = 2;
        mainPanel.add(koapField2, gbc);
        
        gbc.gridx = 3;
        mainPanel.add(koapButton, gbc);
        
        // Код нарушения
        gbc.gridx = 0;
        gbc.gridy = 2;
        mainPanel.add(new JLabel("Код нарушения:"), gbc);
        
        gbc.gridx = 1;
        gbc.gridwidth = 1;
        mainPanel.add(violationCodeField, gbc);
        
        gbc.gridx = 2;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        mainPanel.add(violationCodeCombo, gbc);
        
        // Фактические сведения
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        gbc.weightx = 0.0;
        gbc.anchor = GridBagConstraints.NORTHWEST;
        mainPanel.add(new JLabel("Фактические сведения:"), gbc);
        
        gbc.gridx = 1;
        gbc.gridwidth = 3;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 0.3;
        JScrollPane factualScroll = new JScrollPane(factualInfoArea);
        factualScroll.setPreferredSize(new Dimension(400, 100));
        mainPanel.add(factualScroll, gbc);
        
        // Метка координат мыши
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 4;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        gbc.weighty = 0.0;
        gbc.anchor = GridBagConstraints.CENTER;
        mainPanel.add(mouseCoordinatesLabel, gbc);
        
        // Кнопка заполнения тестовыми данными
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 4;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        gbc.weighty = 0.0;
        gbc.anchor = GridBagConstraints.CENTER;
        mainPanel.add(fillTestDataButton, gbc);
        
        // Область лога событий
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 4;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 0.4;
        gbc.anchor = GridBagConstraints.CENTER;
        JScrollPane logScroll = new JScrollPane(eventLogArea);
        logScroll.setBorder(BorderFactory.createTitledBorder("Лог событий"));
        mainPanel.add(logScroll, gbc);
        
        // Кнопки Сохранить и Отмена
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);
        
        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.gridwidth = 4;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        gbc.weighty = 0.0;
        gbc.anchor = GridBagConstraints.EAST;
        mainPanel.add(buttonPanel, gbc);
        
        // Добавление панели на фрейм
        add(mainPanel);
    }
    
    /**
     * Регистрация обработчиков событий
     */
    private void registerEventHandlers() {
        // СОБЫТИЕ 1: Обработка событий фокуса (FocusEvent)
        // При получении фокуса - устанавливаем цветной фон, при потере - белый
        FocusListener focusHandler = new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                Component component = (Component) e.getSource();
                component.setBackground(focusColor);
                logEvent("Фокус получен: " + getComponentName(component));
            }
            
            @Override
            public void focusLost(FocusEvent e) {
                Component component = (Component) e.getSource();
                component.setBackground(defaultColor);
                logEvent("Фокус потерян: " + getComponentName(component));
            }
        };
        
        // Регистрация обработчика фокуса для всех текстовых полей
        pddField1.addFocusListener(focusHandler);
        pddField2.addFocusListener(focusHandler);
        koapField1.addFocusListener(focusHandler);
        koapField2.addFocusListener(focusHandler);
        violationCodeField.addFocusListener(focusHandler);
        factualInfoArea.addFocusListener(focusHandler);
        
        // СОБЫТИЕ 2: Обработка событий движения мыши (MouseMotionEvent)
        // Отображение координат мыши при перемещении в области панели
        MouseMotionListener mouseMotionHandler = new MouseMotionListener() {
            @Override
            public void mouseMoved(MouseEvent e) {
                int x = e.getX();
                int y = e.getY();
                mouseCoordinatesLabel.setText(String.format("Координаты мыши: X=%d, Y=%d", x, y));
            }
            
            @Override
            public void mouseDragged(MouseEvent e) {
                int x = e.getX();
                int y = e.getY();
                mouseCoordinatesLabel.setText(String.format("Координаты мыши (перетаскивание): X=%d, Y=%d", x, y));
                logEvent("Перетаскивание мыши: X=" + x + ", Y=" + y);
            }
        };
        
        // Регистрация обработчика движения мыши для основной панели
        mainPanel.addMouseMotionListener(mouseMotionHandler);
        
        // СОБЫТИЕ 3: Обработка событий изменения текста (DocumentEvent)
        // Вывод информации об изменении текста в полях ввода
        DocumentListener textChangeHandler = new DocumentListener() {
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
                try {
                    javax.swing.text.Document doc = e.getDocument();
                    String text = doc.getText(0, doc.getLength());
                    String componentName = getComponentNameFromDocument(doc);
                    logEvent("Изменение текста в " + componentName + ": \"" + 
                            (text.length() > 20 ? text.substring(0, 20) + "..." : text) + "\"");
                } catch (Exception ex) {
                    // Игнорируем ошибки при получении текста
                }
            }
        };
        
        // Регистрация обработчика изменения текста для текстовых полей
        pddField1.getDocument().addDocumentListener(textChangeHandler);
        pddField2.getDocument().addDocumentListener(textChangeHandler);
        koapField1.getDocument().addDocumentListener(textChangeHandler);
        koapField2.getDocument().addDocumentListener(textChangeHandler);
        violationCodeField.getDocument().addDocumentListener(textChangeHandler);
        factualInfoArea.getDocument().addDocumentListener(textChangeHandler);
    }
    
    /**
     * Получение имени компонента для лога
     */
    private String getComponentName(Component component) {
        if (component == pddField1) return "п. ПДД (1)";
        if (component == pddField2) return "п. ПДД (2)";
        if (component == koapField1) return "ст. КОАП (1)";
        if (component == koapField2) return "ст. КОАП (2)";
        if (component == violationCodeField) return "Код нарушения";
        if (component == factualInfoArea) return "Фактические сведения";
        return component.getClass().getSimpleName();
    }
    
    /**
     * Получение имени компонента из документа
     */
    private String getComponentNameFromDocument(javax.swing.text.Document doc) {
        if (doc == pddField1.getDocument()) return "п. ПДД (1)";
        if (doc == pddField2.getDocument()) return "п. ПДД (2)";
        if (doc == koapField1.getDocument()) return "ст. КОАП (1)";
        if (doc == koapField2.getDocument()) return "ст. КОАП (2)";
        if (doc == violationCodeField.getDocument()) return "Код нарушения";
        if (doc == factualInfoArea.getDocument()) return "Фактические сведения";
        return "Неизвестное поле";
    }
    
    /**
     * Добавление записи в лог событий
     */
    private void logEvent(String message) {
        String timestamp = String.format("%tH:%tM:%tS", System.currentTimeMillis(), 
                                         System.currentTimeMillis(), System.currentTimeMillis());
        eventLogArea.append("[" + timestamp + "] " + message + "\n");
        // Автоматическая прокрутка вниз
        eventLogArea.setCaretPosition(eventLogArea.getDocument().getLength());
    }
    
    /**
     * Диалог для выбора п. ПДД
     */
    private void showPddDialog() {
        String[] pddOptions = {"1.1", "1.2", "1.3", "2.1", "2.2", "3.1", "3.2", "4.1", "4.2", "5.1"};
        String selected = (String) JOptionPane.showInputDialog(
            this,
            "Выберите пункт ПДД:",
            "Выбор п. ПДД",
            JOptionPane.QUESTION_MESSAGE,
            null,
            pddOptions,
            pddOptions[0]
        );
        
        if (selected != null) {
            String[] parts = selected.split("\\.");
            if (parts.length >= 2) {
                pddField1.setText(parts[0]);
                pddField2.setText(parts[1]);
            }
        }
    }
    
    /**
     * Диалог для выбора ст. КОАП
     */
    private void showKoapDialog() {
        String[] koapOptions = {"12.1", "12.2", "12.3", "12.4", "12.5", "12.6", "12.7", "12.8", "12.9", "12.10"};
        String selected = (String) JOptionPane.showInputDialog(
            this,
            "Выберите статью КОАП:",
            "Выбор ст. КОАП",
            JOptionPane.QUESTION_MESSAGE,
            null,
            koapOptions,
            koapOptions[0]
        );
        
        if (selected != null) {
            String[] parts = selected.split("\\.");
            if (parts.length >= 2) {
                koapField1.setText(parts[0]);
                koapField2.setText(parts[1]);
            }
        }
    }
    
    /**
     * Заполнение полей тестовыми данными
     */
    private void fillTestData() {
        // Тестовые данные для п. ПДД
        pddField1.setText("12");
        pddField2.setText("1");
        
        // Тестовые данные для ст. КОАП
        koapField1.setText("12");
        koapField2.setText("1");
        
        // Тестовые данные для кода нарушения
        violationCodeField.setText("12.1");
        violationCodeCombo.setSelectedItem("12.1");
        
        // Тестовые данные для фактических сведений
        factualInfoArea.setText(
            "Водитель транспортного средства нарушил правила дорожного движения:\n" +
            "- Превышение установленной скорости движения на 20 км/ч\n" +
            "- Нарушение произошло 15.03.2024 в 14:30\n" +
            "- Место нарушения: г. Москва, ул. Ленина, д. 10"
        );
        
        logEvent("Поля заполнены тестовыми данными");
        JOptionPane.showMessageDialog(
            this,
            "Поля заполнены тестовыми данными",
            "Информация",
            JOptionPane.INFORMATION_MESSAGE
        );
    }
    
    /**
     * Сохранение данных
     */
    private void saveData() {
        StringBuilder data = new StringBuilder();
        data.append("=== Данные о нарушении ПДД ===\n\n");
        data.append("п. ПДД: ").append(pddField1.getText()).append(".").append(pddField2.getText()).append("\n");
        data.append("ст. КОАП: ").append(koapField1.getText()).append(".").append(koapField2.getText()).append("\n");
        data.append("Код нарушения: ").append(violationCodeField.getText()).append("\n");
        data.append("Фактические сведения:\n").append(factualInfoArea.getText()).append("\n");
        
        logEvent("Данные сохранены");
        JOptionPane.showMessageDialog(
            this,
            data.toString(),
            "Сохраненные данные",
            JOptionPane.INFORMATION_MESSAGE
        );
    }
    
    /**
     * Отмена действия
     */
    private void cancelAction() {
        int result = JOptionPane.showConfirmDialog(
            this,
            "Вы уверены, что хотите отменить? Все несохраненные данные будут потеряны.",
            "Подтверждение",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE
        );
        
        if (result == JOptionPane.YES_OPTION) {
            // Очистка полей
            pddField1.setText("");
            pddField2.setText("");
            koapField1.setText("");
            koapField2.setText("");
            violationCodeField.setText("");
            violationCodeCombo.setSelectedIndex(0);
            factualInfoArea.setText("");
            logEvent("Данные отменены, поля очищены");
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
                new EventHandlingForm().setVisible(true);
            }
        });
    }
}

