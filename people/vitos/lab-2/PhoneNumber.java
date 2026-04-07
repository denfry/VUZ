/**
 * Класс для хранения информации о номере телефона
 * Задание 2. Вариант 2.11
 */
public class PhoneNumber {
    private String phoneNumber;    // Номер телефона
    private String customerName;   // Имя клиента
    private double balance;       // Остаток средств
    
    /**
     * Конструктор класса PhoneNumber
     */
    public PhoneNumber(String phoneNumber, String customerName) {
        this.phoneNumber = phoneNumber;
        this.customerName = customerName;
        this.balance = 0.0;
    }
    
    /**
     * Получить номер телефона
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }
    
    /**
     * Получить имя клиента
     */
    public String getCustomerName() {
        return customerName;
    }
    
    /**
     * Получить остаток средств
     */
    public double getBalance() {
        return balance;
    }
    
    /**
     * Зачислить средства
     */
    public void addFunds(double amount) {
        this.balance += amount;
    }
    
    /**
     * Списать средства
     */
    public boolean deductFunds(double amount) {
        if (this.balance >= amount) {
            this.balance -= amount;
            return true;
        }
        return false;
    }
    
    @Override
    public String toString() {
        return String.format("Номер: %s, Клиент: %s, Остаток средств: %.2f руб.",
                phoneNumber, customerName, balance);
    }
}

