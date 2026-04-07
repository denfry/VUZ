import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class TrafficViolationForm extends JFrame {
    private JTextField pddField1;
    private JTextField pddField2;
    private JButton pddButton;
    
    private JTextField koapField1;
    private JTextField koapField2;
    private JButton koapButton;
    
    private JTextField violationCodeField;
    private JComboBox<String> violationCodeCombo;
    
    private JTextArea factualInfoArea;
    
    private JButton saveButton;
    private JButton cancelButton;
    private JButton fillTestDataButton;
    
    public TrafficViolationForm() {
        setTitle("Нарушение ПДД");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 500);
        setLocationRelativeTo(null);
        setResizable(true);
        
        createComponents();
        
        layoutComponents();
    }
    
    private void createComponents() {
        pddField1 = new JTextField(8);
        pddField2 = new JTextField(8);
        pddButton = new JButton("...");
        pddButton.setPreferredSize(new Dimension(30, 25));
        pddButton.addActionListener(e -> showPddDialog());
        
        koapField1 = new JTextField(8);
        koapField2 = new JTextField(8);
        koapButton = new JButton("...");
        koapButton.setPreferredSize(new Dimension(30, 25));
        koapButton.addActionListener(e -> showKoapDialog());
        
        violationCodeField = new JTextField(8);
        String[] violationCodes = {"", "12.1", "12.2", "12.3", "12.4", "12.5", "12.6", "12.7", "12.8", "12.9"};
        violationCodeCombo = new JComboBox<>(violationCodes);
        violationCodeCombo.setEditable(true);
        violationCodeCombo.addActionListener(e -> {
            if (violationCodeCombo.getSelectedItem() != null) {
                violationCodeField.setText(violationCodeCombo.getSelectedItem().toString());
            }
        });
        
        factualInfoArea = new JTextArea(8, 40);
        factualInfoArea.setLineWrap(true);
        factualInfoArea.setWrapStyleWord(true);
        JScrollPane scrollPane = new JScrollPane(factualInfoArea);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        
        saveButton = new JButton("Сохранить");
        saveButton.addActionListener(e -> saveData());
        
        cancelButton = new JButton("Отмена");
        cancelButton.addActionListener(e -> cancelAction());
        
        fillTestDataButton = new JButton("Заполнить тестовыми данными");
        fillTestDataButton.addActionListener(e -> fillTestData());
    }
    
    private void layoutComponents() {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridBagLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.anchor = GridBagConstraints.WEST;
        
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
        
        gbc.gridx = 0;
        gbc.gridy = 1;
        mainPanel.add(new JLabel("ст. КОАП:"), gbc);
        
        gbc.gridx = 1;
        mainPanel.add(koapField1, gbc);
        
        gbc.gridx = 2;
        mainPanel.add(koapField2, gbc);
        
        gbc.gridx = 3;
        mainPanel.add(koapButton, gbc);
        
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
        gbc.weighty = 1.0;
        JScrollPane factualScroll = new JScrollPane(factualInfoArea);
        factualScroll.setPreferredSize(new Dimension(400, 150));
        mainPanel.add(factualScroll, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 4;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        gbc.weighty = 0.0;
        gbc.anchor = GridBagConstraints.CENTER;
        mainPanel.add(fillTestDataButton, gbc);
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);
        
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 4;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.EAST;
        mainPanel.add(buttonPanel, gbc);
        
        add(mainPanel);
    }
    
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
    
    private void fillTestData() {
        pddField1.setText("12");
        pddField2.setText("1");
        
        koapField1.setText("12");
        koapField2.setText("1");
        
        violationCodeField.setText("12.1");
        violationCodeCombo.setSelectedItem("12.1");
        
        factualInfoArea.setText(
            "Водитель транспортного средства нарушил правила дорожного движения:\n" +
            "- Превышение установленной скорости движения на 20 км/ч\n" +
            "- Нарушение произошло 15.03.2024 в 14:30\n" +
            "- Место нарушения: г. Москва, ул. Ленина, д. 10\n" +
            "- Транспортное средство: легковой автомобиль, гос. номер А123БВ777\n" +
            "- Водитель: Иванов Иван Иванович, водительское удостоверение серия 77АА №123456"
        );
        
        JOptionPane.showMessageDialog(
            this,
            "Поля заполнены тестовыми данными",
            "Информация",
            JOptionPane.INFORMATION_MESSAGE
        );
    }
    
    private void saveData() {
        StringBuilder data = new StringBuilder();
        data.append("=== Данные о нарушении ПДД ===\n\n");
        data.append("п. ПДД: ").append(pddField1.getText()).append(".").append(pddField2.getText()).append("\n");
        data.append("ст. КОАП: ").append(koapField1.getText()).append(".").append(koapField2.getText()).append("\n");
        data.append("Код нарушения: ").append(violationCodeField.getText()).append("\n");
        data.append("Фактические сведения:\n").append(factualInfoArea.getText()).append("\n");
        
        JOptionPane.showMessageDialog(
            this,
            data.toString(),
            "Сохраненные данные",
            JOptionPane.INFORMATION_MESSAGE
        );
    }
    
    private void cancelAction() {
        int result = JOptionPane.showConfirmDialog(
            this,
            "Вы уверены, что хотите отменить? Все несохраненные данные будут потеряны.",
            "Подтверждение",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE
        );
        
        if (result == JOptionPane.YES_OPTION) {
            pddField1.setText("");
            pddField2.setText("");
            koapField1.setText("");
            koapField2.setText("");
            violationCodeField.setText("");
            violationCodeCombo.setSelectedIndex(0);
            factualInfoArea.setText("");
        }
    }
    
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new TrafficViolationForm().setVisible(true);
            }
        });
    }
}
