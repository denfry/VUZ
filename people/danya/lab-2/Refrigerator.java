/**
 * Базовый класс Холодильник
 * Вариант 1.19
 */
public class Refrigerator {
    protected String manufacturer;  // Производитель
    protected String color;          // Цвет
    protected double height;         // Высота
    protected double width;          // Ширина
    protected double depth;          // Глубина
    
    /**
     * Конструктор базового класса
     */
    public Refrigerator(String manufacturer, String color, double height, double width, double depth) {
        this.manufacturer = manufacturer;
        this.color = color;
        this.height = height;
        this.width = width;
        this.depth = depth;
    }
    
    /**
     * Получить производителя
     */
    public String getManufacturer() {
        return manufacturer;
    }
    
    /**
     * Получить цвет
     */
    public String getColor() {
        return color;
    }
    
    /**
     * Получить высоту
     */
    public double getHeight() {
        return height;
    }
    
    /**
     * Получить ширину
     */
    public double getWidth() {
        return width;
    }
    
    /**
     * Получить глубину
     */
    public double getDepth() {
        return depth;
    }
    
    /**
     * Вычислить объем холодильника
     */
    public double calculateVolume() {
        return height * width * depth;
    }
    
    /**
     * Метод, возвращающий информацию об объекте класса
     */
    public String getInfo() {
        return String.format("Холодильник: Производитель=%s, Цвет=%s, Размеры=%.2f x %.2f x %.2f см, Объем=%.2f л",
                manufacturer, color, height, width, depth, calculateVolume() / 1000.0);
    }
    
    @Override
    public String toString() {
        return getInfo();
    }
}

