/**
 * Подкласс - Холодильник с морозильной камерой
 * Вариант 1.19
 */
public class RefrigeratorWithFreezer extends Refrigerator {
    private FreezerChamberType chamberType;  // Вид камеры: код, наименование
    private int numberOfChambers;            // Количество камер
    private double freezerVolume;             // Объем морозильной камеры
    
    /**
     * Конструктор подкласса
     */
    public RefrigeratorWithFreezer(String manufacturer, String color, 
                                   double height, double width, double depth,
                                   FreezerChamberType chamberType, 
                                   int numberOfChambers, 
                                   double freezerVolume) {
        super(manufacturer, color, height, width, depth);
        this.chamberType = chamberType;
        this.numberOfChambers = numberOfChambers;
        this.freezerVolume = freezerVolume;
    }
    
    /**
     * Получить вид камеры
     */
    public FreezerChamberType getChamberType() {
        return chamberType;
    }
    
    /**
     * Получить количество камер
     */
    public int getNumberOfChambers() {
        return numberOfChambers;
    }
    
    /**
     * Получить объем морозильной камеры
     */
    public double getFreezerVolume() {
        return freezerVolume;
    }
    
    /**
     * Переопределенный метод, возвращающий информацию об объекте класса
     */
    @Override
    public String getInfo() {
        return String.format("Холодильник с морозильной камерой: Производитель=%s, Цвет=%s, " +
                           "Размеры=%.2f x %.2f x %.2f см, Объем=%.2f л, " +
                           "Вид камеры=%s, Количество камер=%d, Объем морозильной камеры=%.2f л",
                manufacturer, color, height, width, depth, calculateVolume() / 1000.0,
                chamberType, numberOfChambers, freezerVolume);
    }
    
    @Override
    public String toString() {
        return getInfo();
    }
}

