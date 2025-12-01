import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * Лабораторная работа 3
 * Конвертер единиц измерения длины
 * 
 * Фрейм с JRadioButton для выбора единиц измерения (дюйм, фут, ярд)
 * и двумя полями ввода для конвертации из сантиметров
 */
public class UnitConverter extends JFrame {
    // Переключатели единиц измерения
    private JRadioButton inchRadio;
    private JRadioButton footRadio;
    private JRadioButton yardRadio;
    private ButtonGroup unitGroup;
    
    // Поля ввода
    private JTextField inputField;      // Поле для ввода в см
    private JTextField outputField;      // Поле для вывода результата
    
    // Константы конвертации (в сантиметрах)
    private static final double INCH_TO_CM = 2.54;
    private static final double FOOT_TO_CM = 30.48;
    private static final double YARD_TO_CM = 91.44;
    
    public UnitConverter() {
        // Настройка фрейма
        setTitle("Конвертер единиц измерения длины");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 300);
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
        // Создание переключателей единиц измерения
        inchRadio = new JRadioButton("Дюйм (inch)");
        footRadio = new JRadioButton("Фут (foot)");
        yardRadio = new JRadioButton("Ярд (yard)");
        
        // Группировка переключателей
        unitGroup = new ButtonGroup();
        unitGroup.add(inchRadio);
        unitGroup.add(footRadio);
        unitGroup.add(yardRadio);
        
        // Установка дюйма по умолчанию
        inchRadio.setSelected(true);
        
        // Добавление обработчиков событий для переключателей
        ActionListener radioListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                convert();
            }
        };
        
        inchRadio.addActionListener(radioListener);
        footRadio.addActionListener(radioListener);
        yardRadio.addActionListener(radioListener);
        
        // Создание полей ввода (2 поля ввода по заданию)
        inputField = new JTextField(20);
        inputField.setToolTipText("Введите значение в сантиметрах");
        inputField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.GRAY),
            BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        
        outputField = new JTextField(20);
        outputField.setToolTipText("Результат конвертации");
        outputField.setBackground(Color.WHITE);
        outputField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.GRAY),
            BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        
        // Обработчик ввода в поле сантиметров
        inputField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                convert();
            }
        });
    }
    
    /**
     * Размещение компонентов на панели
     */
    private void layoutComponents() {
        Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout(15, 15));
        ((JComponent) contentPane).setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Заголовок
        JLabel titleLabel = new JLabel("Конвертер единиц измерения длины", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        contentPane.add(titleLabel, BorderLayout.NORTH);
        
        // Центральная панель
        JPanel centerPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;
        
        // Панель с переключателями (сначала переключатели)
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        JPanel radioPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 5));
        radioPanel.setBorder(BorderFactory.createTitledBorder("Выберите единицу измерения:"));
        radioPanel.add(inchRadio);
        radioPanel.add(footRadio);
        radioPanel.add(yardRadio);
        centerPanel.add(radioPanel, gbc);
        
        // Первое поле ввода (см)
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        JLabel label1 = new JLabel("1-е поле ввода (см):");
        label1.setFont(new Font("Arial", Font.PLAIN, 12));
        centerPanel.add(label1, gbc);
        
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        inputField.setPreferredSize(new Dimension(250, 30));
        centerPanel.add(inputField, gbc);
        
        // Второе поле ввода
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        JLabel label2 = new JLabel("2-е поле ввода:");
        label2.setFont(new Font("Arial", Font.PLAIN, 12));
        centerPanel.add(label2, gbc);
        
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        outputField.setPreferredSize(new Dimension(250, 30));
        centerPanel.add(outputField, gbc);
        
        contentPane.add(centerPanel, BorderLayout.CENTER);
        
        // Информационная панель внизу
        JPanel infoPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel infoLabel = new JLabel("1 дюйм = 2.54 см | 1 фут = 30.48 см | 1 ярд = 91.44 см");
        infoLabel.setFont(new Font("Arial", Font.ITALIC, 11));
        infoLabel.setForeground(Color.GRAY);
        infoPanel.add(infoLabel);
        contentPane.add(infoPanel, BorderLayout.SOUTH);
    }
    
    /**
     * Метод конвертации значения
     */
    private void convert() {
        try {
            String inputText = inputField.getText().trim();
            
            // Проверка на пустое поле
            if (inputText.isEmpty()) {
                outputField.setText("");
                return;
            }
            
            // Парсинг введенного значения
            double cmValue = Double.parseDouble(inputText.replace(",", "."));
            
            // Проверка на отрицательное значение
            if (cmValue < 0) {
                outputField.setText("Ошибка: отрицательное значение");
                outputField.setBackground(new Color(255, 200, 200));
                return;
            }
            
            double result = 0.0;
            String unit = "";
            
            // Конвертация в выбранную единицу
            if (inchRadio.isSelected()) {
                result = cmValue / INCH_TO_CM;
                unit = " дюйм";
            } else if (footRadio.isSelected()) {
                result = cmValue / FOOT_TO_CM;
                unit = " фут";
            } else if (yardRadio.isSelected()) {
                result = cmValue / YARD_TO_CM;
                unit = " ярд";
            }
            
            // Форматирование результата
            String resultText = String.format("%.6f", result) + unit;
            outputField.setText(resultText);
            outputField.setBackground(Color.WHITE);
            
        } catch (NumberFormatException e) {
            outputField.setText("Ошибка: неверный формат числа");
            outputField.setBackground(new Color(255, 200, 200));
        }
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
                new UnitConverter().setVisible(true);
            }
        });
    }
}

