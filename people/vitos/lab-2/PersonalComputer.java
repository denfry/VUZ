import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Подкласс - Персональный компьютер
 * Задание 1. Вариант 1.11
 */
public class PersonalComputer extends Computer {
    private Processor processor;           // Процессор: производитель, модель процессора
    private String pcModel;                // Модель ПК
    private double price;                  // Цена
    private LocalDate purchaseDate;        // Дата закупки
    private String operatingSystem;        // Установленная ОС
    
    /**
     * Конструктор подкласса
     */
    public PersonalComputer(String manufacturer, String usageArea, int releaseYear,
                           Processor processor, String pcModel, double price,
                           LocalDate purchaseDate, String operatingSystem) {
        super(manufacturer, usageArea, releaseYear);
        this.processor = processor;
        this.pcModel = pcModel;
        this.price = price;
        this.purchaseDate = purchaseDate;
        this.operatingSystem = operatingSystem;
    }
    
    /**
     * Получить процессор
     */
    public Processor getProcessor() {
        return processor;
    }
    
    /**
     * Получить модель ПК
     */
    public String getPcModel() {
        return pcModel;
    }
    
    /**
     * Получить цену
     */
    public double getPrice() {
        return price;
    }
    
    /**
     * Получить дату закупки
     */
    public LocalDate getPurchaseDate() {
        return purchaseDate;
    }
    
    /**
     * Получить установленную ОС
     */
    public String getOperatingSystem() {
        return operatingSystem;
    }
    
    /**
     * Переопределенный метод, возвращающий информацию об объекте класса
     */
    @Override
    public String getInfo() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        return String.format("Персональный компьютер: Производитель=%s, Область использования=%s, " +
                           "Год выпуска=%d, Процессор=%s, Модель ПК=%s, " +
                           "Цена=%.2f руб., Дата закупки=%s, ОС=%s",
                manufacturer, usageArea, releaseYear, processor.getFullName(),
                pcModel, price, purchaseDate.format(formatter), operatingSystem);
    }
    
    @Override
    public String toString() {
        return getInfo();
    }
}

