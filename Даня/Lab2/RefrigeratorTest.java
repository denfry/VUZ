import java.util.ArrayList;

/**
 * Тестовый класс для холодильников
 * Вариант 1.19
 */
public class RefrigeratorTest {
    
    public static void main(String[] args) {
        // Создаем массив с типами элементов базового класса
        ArrayList<Refrigerator> refrigerators = new ArrayList<>();
        
        // Создаем объекты базового класса
        refrigerators.add(new Refrigerator("Samsung", "Белый", 180, 60, 65));
        refrigerators.add(new Refrigerator("LG", "Серебристый", 175, 58, 63));
        
        // Создаем объекты подкласса
        refrigerators.add(new RefrigeratorWithFreezer(
            "Samsung", "Белый", 185, 62, 67,
            new FreezerChamberType("FZ-001", "Ноу Фрост"), 2, 120.5
        ));
        refrigerators.add(new RefrigeratorWithFreezer(
            "LG", "Черный", 190, 65, 70,
            new FreezerChamberType("FZ-002", "Капельная система"), 3, 150.0
        ));
        refrigerators.add(new RefrigeratorWithFreezer(
            "Samsung", "Серебристый", 175, 60, 65,
            new FreezerChamberType("FZ-001", "Ноу Фрост"), 2, 80.0
        ));
        refrigerators.add(new RefrigeratorWithFreezer(
            "Bosch", "Белый", 180, 60, 65,
            new FreezerChamberType("FZ-003", "Total No Frost"), 2, 200.0
        ));
        
        System.out.println("=== ВСЕ ХОЛОДИЛЬНИКИ ===\n");
        for (Refrigerator ref : refrigerators) {
            System.out.println(ref.getInfo());
        }
        
        // Вывести информацию о холодильниках с объемом морозильной камеры больше заданной
        double minFreezerVolume = 100.0;
        System.out.println("\n=== ХОЛОДИЛЬНИКИ С ОБЪЕМОМ МОРОЗИЛЬНОЙ КАМЕРЫ БОЛЬШЕ " + 
                          minFreezerVolume + " Л ===\n");
        for (Refrigerator ref : refrigerators) {
            if (ref instanceof RefrigeratorWithFreezer) {
                RefrigeratorWithFreezer rf = (RefrigeratorWithFreezer) ref;
                if (rf.getFreezerVolume() > minFreezerVolume) {
                    System.out.println(rf.getInfo());
                }
            }
        }
        
        // Подсчитать число холодильников и число камер в холодильниках заданного производителя
        String targetManufacturer = "Samsung";
        System.out.println("\n=== СТАТИСТИКА ПО ПРОИЗВОДИТЕЛЮ: " + targetManufacturer + " ===\n");
        
        int refrigeratorCount = 0;
        int totalChambers = 0;
        
        for (Refrigerator ref : refrigerators) {
            if (ref.getManufacturer().equals(targetManufacturer)) {
                refrigeratorCount++;
                if (ref instanceof RefrigeratorWithFreezer) {
                    RefrigeratorWithFreezer rf = (RefrigeratorWithFreezer) ref;
                    totalChambers += rf.getNumberOfChambers();
                }
            }
        }
        
        System.out.println("Количество холодильников производителя " + targetManufacturer + ": " + refrigeratorCount);
        System.out.println("Общее количество камер в холодильниках производителя " + targetManufacturer + ": " + totalChambers);
    }
}

