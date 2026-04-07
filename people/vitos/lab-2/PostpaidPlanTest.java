/**
 * Тестовый класс для проверки работы класса PostpaidPlanManager
 * Задание 2. Вариант 2.11
 */
public class PostpaidPlanTest {
    public static void main(String[] args) {
        System.out.println("=== ЗАДАНИЕ 2: Интерфейсы и коллекции ===\n");
        System.out.println("=== Тестирование постоплатного тарифного плана ===\n");
        
        PostpaidPlanManager manager = new PostpaidPlanManager();
        
        // Тест 1: Открытие номеров
        System.out.println("--- Тест 1: Открытие номеров ---");
        manager.openNumber("+79001234567", "Иван Иванов");
        manager.openNumber("+79009876543", "Петр Петров");
        manager.openNumber("+79005555555", "Мария Сидорова");
        
        // Тест с ошибками
        System.out.println("\n--- Тест с ошибками при открытии ---");
        manager.openNumber("", "Тест");  // Пустой номер
        manager.openNumber("+79001234567", "Дубликат");  // Дубликат номера
        manager.openNumber("+79001111111", "");  // Пустое имя
        
        // Тест 2: Зачисление средств
        System.out.println("\n--- Тест 2: Зачисление средств ---");
        manager.addFunds("+79001234567", 500.0);
        manager.addFunds("+79009876543", 1000.0);
        manager.addFunds("+79005555555", 300.0);
        
        // Тест с ошибками
        System.out.println("\n--- Тест с ошибками при зачислении ---");
        manager.addFunds("+79009999999", 100.0);  // Несуществующий номер
        manager.addFunds("+79001234567", -50.0);  // Отрицательная сумма
        manager.addFunds("+79001234567", 0.0);    // Нулевая сумма
        
        // Тест 3: Получение информации об остатке средств
        System.out.println("\n--- Тест 3: Получение информации об остатке средств ---");
        double balance1 = manager.getBalance("+79001234567");
        System.out.println("Остаток средств на номере +79001234567: " + 
                          String.format("%.2f", balance1) + " руб.");
        
        double balance2 = manager.getBalance("+79009876543");
        System.out.println("Остаток средств на номере +79009876543: " + 
                          String.format("%.2f", balance2) + " руб.");
        
        // Тест 4: Списание средств за разговор
        System.out.println("\n--- Тест 4: Списание средств за разговор ---");
        manager.deductCall("+79001234567", 10, 2.5);  // 10 минут по 2.5 руб/мин = 25 руб.
        manager.deductCall("+79009876543", 5, 3.0);   // 5 минут по 3.0 руб/мин = 15 руб.
        
        // Тест с ошибками
        System.out.println("\n--- Тест с ошибками при списании за разговор ---");
        manager.deductCall("+79009999999", 10, 2.5);  // Несуществующий номер
        manager.deductCall("+79001234567", 200, 2.5); // Недостаточно средств (500 - 25 = 475, нужно 500)
        manager.deductCall("+79001234567", -5, 2.5);  // Отрицательная длительность
        
        // Тест 5: Списание средств за СМС
        System.out.println("\n--- Тест 5: Списание средств за СМС ---");
        manager.deductSms("+79001234567", 5, 2.0);    // 5 СМС по 2 руб = 10 руб.
        manager.deductSms("+79009876543", 10, 1.5);   // 10 СМС по 1.5 руб = 15 руб.
        manager.deductSms("+79005555555", 3, 2.5);    // 3 СМС по 2.5 руб = 7.5 руб.
        
        // Тест с ошибками
        System.out.println("\n--- Тест с ошибками при списании за СМС ---");
        manager.deductSms("+79009999999", 5, 2.0);   // Несуществующий номер
        manager.deductSms("+79005555555", 200, 2.5); // Недостаточно средств (300 - 7.5 = 292.5, нужно 500)
        manager.deductSms("+79001234567", -3, 2.0);  // Отрицательное количество
        
        // Тест 6: Получение информации о номерах
        System.out.println("\n--- Тест 6: Получение информации о номерах ---");
        String info1 = manager.getNumberInfo("+79001234567");
        if (info1 != null) {
            System.out.println(info1);
        }
        
        String info2 = manager.getNumberInfo("+79009876543");
        if (info2 != null) {
            System.out.println(info2);
        }
        
        String info3 = manager.getNumberInfo("+79005555555");
        if (info3 != null) {
            System.out.println(info3);
        }
        
        // Финальные остатки
        System.out.println("\n--- Финальные остатки средств ---");
        System.out.println("Номер +79001234567: " + 
                          String.format("%.2f", manager.getBalance("+79001234567")) + " руб.");
        System.out.println("Номер +79009876543: " + 
                          String.format("%.2f", manager.getBalance("+79009876543")) + " руб.");
        System.out.println("Номер +79005555555: " + 
                          String.format("%.2f", manager.getBalance("+79005555555")) + " руб.");
    }
}

