/**
 * Интерфейс для ведения постоплатного тарифного плана сотовой компании
 * Задание 2. Вариант 2.11
 */
public interface PostpaidPlanInterface {
    /**
     * Открытие номера
     * @param phoneNumber номер телефона
     * @param customerName имя клиента
     * @return true если операция выполнена успешно, false в противном случае
     */
    boolean openNumber(String phoneNumber, String customerName);
    
    /**
     * Зачисление средств на счет
     * @param phoneNumber номер телефона
     * @param amount сумма для зачисления
     * @return true если операция выполнена успешно, false в противном случае
     */
    boolean addFunds(String phoneNumber, double amount);
    
    /**
     * Списание средств за разговор
     * @param phoneNumber номер телефона
     * @param duration длительность разговора в минутах
     * @param costPerMinute стоимость минуты разговора
     * @return true если операция выполнена успешно, false в противном случае
     */
    boolean deductCall(String phoneNumber, int duration, double costPerMinute);
    
    /**
     * Списание средств за СМС
     * @param phoneNumber номер телефона
     * @param smsCount количество СМС
     * @param costPerSms стоимость одной СМС
     * @return true если операция выполнена успешно, false в противном случае
     */
    boolean deductSms(String phoneNumber, int smsCount, double costPerSms);
    
    /**
     * Получение информации об остатке средств
     * @param phoneNumber номер телефона
     * @return остаток средств или -1 если номер не найден
     */
    double getBalance(String phoneNumber);
    
    /**
     * Получение информации о номере
     * @param phoneNumber номер телефона
     * @return информация о номере или null если номер не найден
     */
    String getNumberInfo(String phoneNumber);
}

