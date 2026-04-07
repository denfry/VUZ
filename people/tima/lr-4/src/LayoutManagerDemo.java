import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Лабораторная работа №4 - Вариант 9
 * Форма "Регистрация авто"
 * Демонстрация использования менеджеров компоновки:
 * BorderLayout, GridLayout, FlowLayout
 */
public class LayoutManagerDemo extends JFrame {
    
    // Поля формы
    private JComboBox<String> typeCombo;
    private JTextField gosnumberField;
    private JTextField markaField;
    private JTextField stsField;
    private JTextField modelField;
    private JTextField ptsField;
    private JTextField powerField;
    private JTextField massField;
    
    public LayoutManagerDemo() {
        setTitle("Регистрация авто");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        // Основной контейнер с BorderLayout
        Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout(15, 15));
        
        // Заголовок по центру вверху
        JLabel titleLabel = new JLabel("Регистрация авто", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        contentPane.add(titleLabel, BorderLayout.NORTH);
        
        // Центральная панель с формой ввода
        JPanel formPanel = new JPanel(new GridLayout(4, 4, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Строка 1: Тип (левая колонка) и Госномер (правая колонка)
        formPanel.add(new JLabel("Тип:",SwingConstants.RIGHT));
        typeCombo = new JComboBox<>(new String[]{"Легковой", "Грузовой", "Мотоцикл", "Автобус"});
        formPanel.add(typeCombo);
        formPanel.add(new JLabel("Госномер:",SwingConstants.RIGHT));
        gosnumberField = new JTextField();
        formPanel.add(gosnumberField);
        
        // Строка 2: Марка (левая колонка) и СТС (правая колонка)
        formPanel.add(new JLabel("Марка:",SwingConstants.RIGHT));
        markaField = new JTextField();
        formPanel.add(markaField);
        formPanel.add(new JLabel("СТС:",SwingConstants.RIGHT));
        stsField = new JTextField();
        formPanel.add(stsField);
        
        // Строка 3: Модель (левая колонка) и ПТС (правая колонка)
        formPanel.add(new JLabel("Модель:",SwingConstants.RIGHT));
        modelField = new JTextField();
        formPanel.add(modelField);
        formPanel.add(new JLabel("ПТС:",SwingConstants.RIGHT));
        ptsField = new JTextField();
        formPanel.add(ptsField);
        
        // Строка 4: Мощность (левая колонка) и Масса (правая колонка)
        formPanel.add(new JLabel("Мощность:",SwingConstants.RIGHT));
        powerField = new JTextField();
        formPanel.add(powerField);
        formPanel.add(new JLabel("Масса:",SwingConstants.RIGHT));
        massField = new JTextField();
        formPanel.add(massField);
        
        contentPane.add(formPanel, BorderLayout.CENTER);
        
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
        setSize(500, 300);
        setMinimumSize(new Dimension(450, 250));
    }
    
    /**
     * Показывает диалоговое окно с вариантами сохранения
     */
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
    
    /**
     * Сохранение данных в файл
     */
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
                
                JOptionPane.showMessageDialog(
                    this,
                    "Данные успешно сохранены в файл:\n" + filename,
                    "Успех",
                    JOptionPane.INFORMATION_MESSAGE
                );
            } catch (IOException e) {
                JOptionPane.showMessageDialog(
                    this,
                    "Ошибка при сохранении файла:\n" + e.getMessage(),
                    "Ошибка",
                    JOptionPane.ERROR_MESSAGE
                );
            }
        }
    }
    
    /**
     * Показывает данные в диалоговом окне
     */
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
        
        JOptionPane.showMessageDialog(
            this,
            data.toString(),
            "Данные регистрации",
            JOptionPane.INFORMATION_MESSAGE
        );
    }
    
    /**
     * Очистка всех полей формы
     */
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
            new LayoutManagerDemo().setVisible(true);
        });
    }
}

