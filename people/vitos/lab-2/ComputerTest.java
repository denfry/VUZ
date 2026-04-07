import java.time.LocalDate;

/**
 * Тестовый класс для проверки работы классов Computer и PersonalComputer
 * Задание 1. Вариант 1.11
 */
public class ComputerTest {
    public static void main(String[] args) {
        System.out.println("=== ЗАДАНИЕ 1: Наследование классов ===\n");
        System.out.println("=== Тестирование классов Computer и PersonalComputer ===\n");
        
        // Создание массива с типами элементов базового класса
        Computer[] computers = new Computer[6];
        
        // Создание объектов базового класса
        computers[0] = new Computer("IBM", "Научные вычисления", 2020);
        computers[1] = new Computer("Dell", "Офисная работа", 2019);
        
        // Создание объектов подкласса
        computers[2] = new PersonalComputer(
            "HP", "Домашнее использование", 2021,
            new Processor("Intel", "Core i5-11400"), 
            "HP Pavilion", 45000.0,
            LocalDate.of(2021, 3, 15), "Windows 11"
        );
        
        computers[3] = new PersonalComputer(
            "Lenovo", "Игровой", 2022,
            new Processor("AMD", "Ryzen 5 5600X"),
            "Lenovo Legion", 75000.0,
            LocalDate.of(2022, 6, 20), "Windows 11"
        );
        
        computers[4] = new PersonalComputer(
            "ASUS", "Офисная работа", 2020,
            new Processor("Intel", "Core i7-10700"),
            "ASUS VivoBook", 55000.0,
            LocalDate.of(2020, 9, 10), "Windows 10"
        );
        
        computers[5] = new PersonalComputer(
            "Apple", "Дизайн и разработка", 2023,
            new Processor("Apple", "M2 Pro"),
            "MacBook Pro", 120000.0,
            LocalDate.of(2023, 1, 25), "macOS"
        );
        
        // Вывод информации о всех компьютерах
        System.out.println("--- Информация о всех компьютерах ---");
        for (int i = 0; i < computers.length; i++) {
            System.out.println((i + 1) + ". " + computers[i].getInfo());
        }
        
        System.out.println("\n--- Компьютеры с процессором Intel ---");
        printComputersByProcessor(computers, "Intel");
        
        System.out.println("\n--- Компьютеры с процессором AMD ---");
        printComputersByProcessor(computers, "AMD");
        
        System.out.println("\n--- Компьютеры с процессором Apple ---");
        printComputersByProcessor(computers, "Apple");
        
        // Подсчет числа компьютеров с установленной ОС Windows
        int windowsCount = countWindowsComputers(computers);
        System.out.println("\n--- Статистика ---");
        System.out.println("Количество компьютеров с установленной ОС Windows: " + windowsCount);
    }
    
    /**
     * Метод для вывода информации о компьютерах с заданным типом процессора
     */
    public static void printComputersByProcessor(Computer[] computers, String processorManufacturer) {
        boolean found = false;
        for (Computer computer : computers) {
            if (computer instanceof PersonalComputer) {
                PersonalComputer pc = (PersonalComputer) computer;
                if (pc.getProcessor().getManufacturer().equalsIgnoreCase(processorManufacturer)) {
                    System.out.println(pc.getInfo());
                    found = true;
                }
            }
        }
        if (!found) {
            System.out.println("Компьютеры с процессором " + processorManufacturer + " не найдены.");
        }
    }
    
    /**
     * Метод для подсчета числа компьютеров с установленной ОС Windows
     */
    public static int countWindowsComputers(Computer[] computers) {
        int count = 0;
        for (Computer computer : computers) {
            if (computer instanceof PersonalComputer) {
                PersonalComputer pc = (PersonalComputer) computer;
                String os = pc.getOperatingSystem();
                if (os != null && (os.toLowerCase().contains("windows"))) {
                    count++;
                }
            }
        }
        return count;
    }
}

