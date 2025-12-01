import java.util.HashMap;

/**
 * Класс для ведения бонусных карт покупателей
 * Реализует интерфейс BonusCardInterface
 * Использует HashMap для хранения карт
 * Вариант 2.19
 */
public class BonusCardManager implements BonusCardInterface {
    private HashMap<String, BonusCard> cards;  // Хранилище объектов с доступом по ключу
    
    public BonusCardManager() {
        this.cards = new HashMap<>();
    }
    
    /**
     * Открытие карты
     */
    @Override
    public boolean openCard(String cardId, String customerName) {
        // Проверка на неправильные конфигурации данных
        if (cardId == null || cardId.trim().isEmpty()) {
            System.out.println("Ошибка: ID карты не может быть пустым");
            return false;
        }
        
        if (customerName == null || customerName.trim().isEmpty()) {
            System.out.println("Ошибка: Имя покупателя не может быть пустым");
            return false;
        }
        
        // Проверка на существование карты
        if (cards.containsKey(cardId)) {
            System.out.println("Ошибка: Карта с ID " + cardId + " уже существует");
            return false;
        }
        
        // Создание новой карты
        cards.put(cardId, new BonusCard(cardId, customerName));
        System.out.println("Карта успешно открыта: " + cardId + " для " + customerName);
        return true;
    }
    
    /**
     * Начисление бонусов от стоимости покупки
     */
    @Override
    public boolean addBonus(String cardId, double purchaseAmount, double bonusPercent) {
        // Проверка на отсутствие объекта в хранилище
        if (!cards.containsKey(cardId)) {
            System.out.println("Ошибка: Карта с ID " + cardId + " не найдена");
            return false;
        }
        
        // Проверка на неправильные конфигурации данных
        if (purchaseAmount < 0) {
            System.out.println("Ошибка: Стоимость покупки не может быть отрицательной");
            return false;
        }
        
        if (bonusPercent < 0 || bonusPercent > 100) {
            System.out.println("Ошибка: Процент бонусов должен быть от 0 до 100");
            return false;
        }
        
        // Начисление бонусов
        BonusCard card = cards.get(cardId);
        double bonusAmount = purchaseAmount * bonusPercent / 100.0;
        card.addBonus(bonusAmount);
        System.out.println("Начислено бонусов: " + String.format("%.2f", bonusAmount) + 
                          " (от покупки на " + String.format("%.2f", purchaseAmount) + 
                          " с процентом " + bonusPercent + "%)");
        return true;
    }
    
    /**
     * Списание бонусов со счета
     */
    @Override
    public boolean deductBonus(String cardId, double bonusAmount) {
        // Проверка на отсутствие объекта в хранилище
        if (!cards.containsKey(cardId)) {
            System.out.println("Ошибка: Карта с ID " + cardId + " не найдена");
            return false;
        }
        
        // Проверка на неправильные конфигурации данных
        if (bonusAmount < 0) {
            System.out.println("Ошибка: Сумма списания не может быть отрицательной");
            return false;
        }
        
        BonusCard card = cards.get(cardId);
        
        // Проверка достаточности бонусов
        if (card.getBonusBalance() < bonusAmount) {
            System.out.println("Ошибка: Недостаточно бонусов. Текущий баланс: " + 
                            String.format("%.2f", card.getBonusBalance()) + 
                            ", требуется: " + String.format("%.2f", bonusAmount));
            return false;
        }
        
        // Списание бонусов
        card.deductBonus(bonusAmount);
        System.out.println("Списано бонусов: " + String.format("%.2f", bonusAmount));
        return true;
    }
    
    /**
     * Обнуление бонусов
     */
    @Override
    public boolean resetBonus(String cardId) {
        // Проверка на отсутствие объекта в хранилище
        if (!cards.containsKey(cardId)) {
            System.out.println("Ошибка: Карта с ID " + cardId + " не найдена");
            return false;
        }
        
        // Обнуление бонусов
        BonusCard card = cards.get(cardId);
        card.resetBonus();
        System.out.println("Бонусы обнулены для карты " + cardId);
        return true;
    }
    
    /**
     * Получение информации об остатке бонусов
     */
    @Override
    public double getBonusBalance(String cardId) {
        // Проверка на отсутствие объекта в хранилище
        if (!cards.containsKey(cardId)) {
            System.out.println("Ошибка: Карта с ID " + cardId + " не найдена");
            return -1;
        }
        
        return cards.get(cardId).getBonusBalance();
    }
    
    /**
     * Получение информации о карте
     */
    @Override
    public String getCardInfo(String cardId) {
        // Проверка на отсутствие объекта в хранилище
        if (!cards.containsKey(cardId)) {
            return null;
        }
        
        return cards.get(cardId).toString();
    }
}

