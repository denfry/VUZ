/**
 * Главный класс для запуска обоих заданий лабораторной работы
 * Лабораторная работа 2
 */
public class Main {
    public static void main(String[] args) {
        System.out.println("╔════════════════════════════════════════════════════════════╗");
        System.out.println("║     ЛАБОРАТОРНАЯ РАБОТА 2                                  ║");
        System.out.println("║     Задание 1: Наследование классов                       ║");
        System.out.println("║     Задание 2: Интерфейсы и коллекции                    ║");
        System.out.println("╚════════════════════════════════════════════════════════════╝\n");
        
        // Запуск задания 1
        System.out.println("\n" + "=".repeat(60) + "\n");
        ComputerTest.main(new String[0]);
        
        // Запуск задания 2
        System.out.println("\n" + "=".repeat(60) + "\n");
        PostpaidPlanTest.main(new String[0]);
        
        System.out.println("\n" + "=".repeat(60));
        System.out.println("Все задания выполнены!");
    }
}

