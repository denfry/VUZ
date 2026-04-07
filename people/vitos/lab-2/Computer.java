/**
 * Базовый класс Компьютеры
 * Задание 1. Вариант 1.11
 */
public class Computer {
    protected String manufacturer;      // Производитель
    protected String usageArea;         // Область использования
    protected int releaseYear;          // Год выпуска
    
    /**
     * Конструктор базового класса
     */
    public Computer(String manufacturer, String usageArea, int releaseYear) {
        this.manufacturer = manufacturer;
        this.usageArea = usageArea;
        this.releaseYear = releaseYear;
    }
    
    /**
     * Получить производителя
     */
    public String getManufacturer() {
        return manufacturer;
    }
    
    /**
     * Получить область использования
     */
    public String getUsageArea() {
        return usageArea;
    }
    
    /**
     * Получить год выпуска
     */
    public int getReleaseYear() {
        return releaseYear;
    }
    
    /**
     * Метод, возвращающий информацию об объекте класса
     */
    public String getInfo() {
        return String.format("Компьютер: Производитель=%s, Область использования=%s, Год выпуска=%d",
                manufacturer, usageArea, releaseYear);
    }
    
    @Override
    public String toString() {
        return getInfo();
    }
}

