/**
 * Класс Процессор
 * Содержит информацию о производителе и модели процессора
 * Задание 1. Вариант 1.11
 */
public class Processor {
    private String manufacturer;  // Производитель процессора
    private String model;         // Модель процессора
    
    /**
     * Конструктор класса Processor
     */
    public Processor(String manufacturer, String model) {
        this.manufacturer = manufacturer;
        this.model = model;
    }
    
    /**
     * Получить производителя процессора
     */
    public String getManufacturer() {
        return manufacturer;
    }
    
    /**
     * Получить модель процессора
     */
    public String getModel() {
        return model;
    }
    
    /**
     * Получить полное название процессора
     */
    public String getFullName() {
        return manufacturer + " " + model;
    }
    
    @Override
    public String toString() {
        return getFullName();
    }
}

