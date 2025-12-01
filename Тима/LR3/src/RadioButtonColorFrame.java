import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class RadioButtonColorFrame extends JFrame {

    public RadioButtonColorFrame() {
        setTitle("Выбор цвета фона и текста");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Основная панель с GridBagLayout для удобного размещения
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        // Метка посередине
        JLabel label = new JLabel("Java", SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 48));
        label.setOpaque(true);                    // важно! чтобы видеть цвет фона метки
        label.setBackground(Color.WHITE);         // начальный фон метки
        label.setForeground(Color.BLACK);         // начальный цвет текста

        // Создаём группу для радиокнопок (чтобы выбиралась только одна)
        ButtonGroup group = new ButtonGroup();

        // Радиокнопка 1 — меняет цвет фона всей формы
        JRadioButton rbBackground = new JRadioButton("Красный фон окна");
        rbBackground.setSelected(true); // выбрана по умолчанию

        // Радиокнопка 2 — меняет цвет фона метки
        JRadioButton rbLabel = new JRadioButton("Синий фон метки");

        group.add(rbBackground);
        group.add(rbLabel);

        // Слушатель изменений выбора
        ItemListener listener = new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    if (e.getSource() == rbBackground) {
                        // Выбран красный фон окна
                        panel.setBackground(Color.RED);
                        label.setBackground(Color.WHITE);     // возвращаем метке белый фон
                        label.setForeground(Color.BLACK);
                    } else if (e.getSource() == rbLabel) {
                        // Выбран синий фон метки
                        panel.setBackground(SystemColor.window); // возвращаем стандартный фон окна
                        label.setBackground(Color.BLUE);
                        label.setForeground(Color.WHITE);     // чтобы текст был виден на синем
                    }
                }
            }
        };

        rbBackground.addItemListener(listener);
        rbLabel.addItemListener(listener);

        // Размещаем элементы
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(rbBackground, gbc);

        gbc.gridx = 1;
        panel.add(rbLabel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        panel.add(label, gbc);

        add(panel);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new RadioButtonColorFrame().setVisible(true);
        });
    }
}