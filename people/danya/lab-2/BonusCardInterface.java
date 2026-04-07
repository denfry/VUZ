/**
 * Интерфейс для ведения бонусной карты покупателя
 * Вариант 2.19
 */
public interface BonusCardInterface {
    /**
     * Открытие карты
     * @param cardId идентификатор карты
     * @param customerName имя покупателя
     * @return true если операция выполнена успешно, false иначе
     */
    boolean openCard(String cardId, String customerName);
    
    /**
     * Начисление бонусов от стоимости покупки
     * @param cardId идентификатор карты
     * @param purchaseAmount стоимость покупки
     * @param bonusPercent процент бонусов (например, 5.0 для 5%)
     * @return true если операция выполнена успешно, false иначе
     */
    boolean addBonus(String cardId, double purchaseAmount, double bonusPercent);
    
    /**
     * Списание бонусов со счета
     * @param cardId идентификатор карты
     * @param bonusAmount количество бонусов для списания
     * @return true если операция выполнена успешно, false иначе
     */
    boolean deductBonus(String cardId, double bonusAmount);
    
    /**
     * Обнуление бонусов
     * @param cardId идентификатор карты
     * @return true если операция выполнена успешно, false иначе
     */
    boolean resetBonus(String cardId);
    
    /**
     * Получение информации об остатке бонусов
     * @param cardId идентификатор карты
     * @return остаток бонусов, или -1 если карта не найдена
     */
    double getBonusBalance(String cardId);
    
    /**
     * Получение информации о карте
     * @param cardId идентификатор карты
     * @return информация о карте в виде строки, или null если карта не найдена
     */
    String getCardInfo(String cardId);
}

