/**
 * Тестовый класс для бонусных карт
 * Вариант 2.19
 */
public class BonusCardTest {
    
    public static void main(String[] args) {
        BonusCardManager manager = new BonusCardManager();
        
        System.out.println("=== ТЕСТИРОВАНИЕ БОНУСНОЙ СИСТЕМЫ ===\n");
        
        // Тест 1: Открытие карт
        System.out.println("--- Тест 1: Открытие карт ---");
        manager.openCard("CARD001", "Иван Иванов");
        manager.openCard("CARD002", "Мария Петрова");
        manager.openCard("CARD003", "Петр Сидоров");
        
        // Тест на ошибку: попытка открыть карту с существующим ID
        System.out.println("\n--- Тест на ошибку: дублирование ID ---");
        manager.openCard("CARD001", "Другой человек");
        
        // Тест на ошибку: пустой ID
        System.out.println("\n--- Тест на ошибку: пустой ID ---");
        manager.openCard("", "Тест");
        
        // Тест 2: Начисление бонусов
        System.out.println("\n--- Тест 2: Начисление бонусов ---");
        manager.addBonus("CARD001", 1000.0, 5.0);  // 5% от 1000 = 50 бонусов
        manager.addBonus("CARD001", 2000.0, 3.0);  // 3% от 2000 = 60 бонусов
        manager.addBonus("CARD002", 1500.0, 10.0); // 10% от 1500 = 150 бонусов
        
        // Тест на ошибку: несуществующая карта
        System.out.println("\n--- Тест на ошибку: несуществующая карта ---");
        manager.addBonus("CARD999", 1000.0, 5.0);
        
        // Тест на ошибку: отрицательная сумма
        System.out.println("\n--- Тест на ошибку: отрицательная сумма ---");
        manager.addBonus("CARD001", -100.0, 5.0);
        
        // Тест 3: Получение информации о балансе
        System.out.println("\n--- Тест 3: Получение информации о балансе ---");
        System.out.println("Информация о карте CARD001: " + manager.getCardInfo("CARD001"));
        System.out.println("Баланс CARD001: " + manager.getBonusBalance("CARD001"));
        System.out.println("Баланс CARD002: " + manager.getBonusBalance("CARD002"));
        System.out.println("Баланс CARD003: " + manager.getBonusBalance("CARD003"));
        
        // Тест 4: Списание бонусов
        System.out.println("\n--- Тест 4: Списание бонусов ---");
        manager.deductBonus("CARD001", 30.0);
        manager.deductBonus("CARD002", 50.0);
        
        // Тест на ошибку: недостаточно бонусов
        System.out.println("\n--- Тест на ошибку: недостаточно бонусов ---");
        manager.deductBonus("CARD003", 100.0);  // У CARD003 баланс 0
        
        // Тест 5: Обнуление бонусов
        System.out.println("\n--- Тест 5: Обнуление бонусов ---");
        System.out.println("Баланс CARD002 до обнуления: " + manager.getBonusBalance("CARD002"));
        manager.resetBonus("CARD002");
        System.out.println("Баланс CARD002 после обнуления: " + manager.getBonusBalance("CARD002"));
        
        // Тест на ошибку: обнуление несуществующей карты
        System.out.println("\n--- Тест на ошибку: обнуление несуществующей карты ---");
        manager.resetBonus("CARD999");
        
        // Финальная информация о всех картах
        System.out.println("\n=== ФИНАЛЬНАЯ ИНФОРМАЦИЯ О ВСЕХ КАРТАХ ===");
        System.out.println("CARD001: " + manager.getCardInfo("CARD001"));
        System.out.println("CARD002: " + manager.getCardInfo("CARD002"));
        System.out.println("CARD003: " + manager.getCardInfo("CARD003"));
    }
}

