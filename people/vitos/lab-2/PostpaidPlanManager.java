import java.util.HashMap;

/**
 * Класс для ведения постоплатного тарифного плана сотовой компании
 * Реализует интерфейс PostpaidPlanInterface
 * Использует HashMap для хранения номеров
 * Задание 2. Вариант 2.11
 */
public class PostpaidPlanManager implements PostpaidPlanInterface {
    private HashMap<String, PhoneNumber> numbers;  // Хранилище объектов с доступом по ключу
    
    public PostpaidPlanManager() {
        this.numbers = new HashMap<>();
    }
    
    /**
     * Открытие номера
     */
    @Override
    public boolean openNumber(String phoneNumber, String customerName) {
        // Проверка на неправильные конфигурации данных
        if (phoneNumber == null || phoneNumber.trim().isEmpty()) {
            System.out.println("Ошибка: Номер телефона не может быть пустым");
            return false;
        }
        
        if (customerName == null || customerName.trim().isEmpty()) {
            System.out.println("Ошибка: Имя клиента не может быть пустым");
            return false;
        }
        
        // Проверка формата номера (простая проверка)
        if (!phoneNumber.matches("\\+?[0-9]{10,15}")) {
            System.out.println("Ошибка: Неверный формат номера телефона");
            return false;
        }
        
        // Проверка на существование номера
        if (numbers.containsKey(phoneNumber)) {
            System.out.println("Ошибка: Номер " + phoneNumber + " уже существует");
            return false;
        }
        
        // Создание нового номера
        numbers.put(phoneNumber, new PhoneNumber(phoneNumber, customerName));
        System.out.println("Номер успешно открыт: " + phoneNumber + " для " + customerName);
        return true;
    }
    
    /**
     * Зачисление средств на счет
     */
    @Override
    public boolean addFunds(String phoneNumber, double amount) {
        // Проверка на отсутствие объекта в хранилище
        if (!numbers.containsKey(phoneNumber)) {
            System.out.println("Ошибка: Номер " + phoneNumber + " не найден");
            return false;
        }
        
        // Проверка на неправильные конфигурации данных
        if (amount <= 0) {
            System.out.println("Ошибка: Сумма зачисления должна быть положительной");
            return false;
        }
        
        // Зачисление средств
        PhoneNumber number = numbers.get(phoneNumber);
        number.addFunds(amount);
        System.out.println("Зачислено средств: " + String.format("%.2f", amount) + 
                          " руб. на номер " + phoneNumber);
        return true;
    }
    
    /**
     * Списание средств за разговор
     */
    @Override
    public boolean deductCall(String phoneNumber, int duration, double costPerMinute) {
        // Проверка на отсутствие объекта в хранилище
        if (!numbers.containsKey(phoneNumber)) {
            System.out.println("Ошибка: Номер " + phoneNumber + " не найден");
            return false;
        }
        
        // Проверка на неправильные конфигурации данных
        if (duration <= 0) {
            System.out.println("Ошибка: Длительность разговора должна быть положительной");
            return false;
        }
        
        if (costPerMinute < 0) {
            System.out.println("Ошибка: Стоимость минуты не может быть отрицательной");
            return false;
        }
        
        PhoneNumber number = numbers.get(phoneNumber);
        double totalCost = duration * costPerMinute;
        
        // Проверка достаточности средств
        if (number.getBalance() < totalCost) {
            System.out.println("Ошибка: Недостаточно средств для разговора. " +
                            "Текущий баланс: " + String.format("%.2f", number.getBalance()) + 
                            " руб., требуется: " + String.format("%.2f", totalCost) + " руб.");
            return false;
        }
        
        // Списание средств
        number.deductFunds(totalCost);
        System.out.println("Списано за разговор: " + String.format("%.2f", totalCost) + 
                          " руб. (длительность: " + duration + " мин., стоимость: " + 
                          String.format("%.2f", costPerMinute) + " руб./мин.)");
        return true;
    }
    
    /**
     * Списание средств за СМС
     */
    @Override
    public boolean deductSms(String phoneNumber, int smsCount, double costPerSms) {
        // Проверка на отсутствие объекта в хранилище
        if (!numbers.containsKey(phoneNumber)) {
            System.out.println("Ошибка: Номер " + phoneNumber + " не найден");
            return false;
        }
        
        // Проверка на неправильные конфигурации данных
        if (smsCount <= 0) {
            System.out.println("Ошибка: Количество СМС должно быть положительным");
            return false;
        }
        
        if (costPerSms < 0) {
            System.out.println("Ошибка: Стоимость СМС не может быть отрицательной");
            return false;
        }
        
        PhoneNumber number = numbers.get(phoneNumber);
        double totalCost = smsCount * costPerSms;
        
        // Проверка достаточности средств
        if (number.getBalance() < totalCost) {
            System.out.println("Ошибка: Недостаточно средств для отправки СМС. " +
                            "Текущий баланс: " + String.format("%.2f", number.getBalance()) + 
                            " руб., требуется: " + String.format("%.2f", totalCost) + " руб.");
            return false;
        }
        
        // Списание средств
        number.deductFunds(totalCost);
        System.out.println("Списано за СМС: " + String.format("%.2f", totalCost) + 
                          " руб. (количество: " + smsCount + ", стоимость: " + 
                          String.format("%.2f", costPerSms) + " руб./СМС)");
        return true;
    }
    
    /**
     * Получение информации об остатке средств
     */
    @Override
    public double getBalance(String phoneNumber) {
        // Проверка на отсутствие объекта в хранилище
        if (!numbers.containsKey(phoneNumber)) {
            System.out.println("Ошибка: Номер " + phoneNumber + " не найден");
            return -1;
        }
        
        return numbers.get(phoneNumber).getBalance();
    }
    
    /**
     * Получение информации о номере
     */
    @Override
    public String getNumberInfo(String phoneNumber) {
        // Проверка на отсутствие объекта в хранилище
        if (!numbers.containsKey(phoneNumber)) {
            return null;
        }
        
        return numbers.get(phoneNumber).toString();
    }
}

