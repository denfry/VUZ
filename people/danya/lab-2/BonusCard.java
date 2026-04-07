/**
 * Класс для описания бонусной карты
 */
public class BonusCard {
    private String cardId;
    private String customerName;
    private double bonusBalance;
    
    public BonusCard(String cardId, String customerName) {
        this.cardId = cardId;
        this.customerName = customerName;
        this.bonusBalance = 0.0;
    }
    
    public String getCardId() {
        return cardId;
    }
    
    public String getCustomerName() {
        return customerName;
    }
    
    public double getBonusBalance() {
        return bonusBalance;
    }
    
    public void setBonusBalance(double bonusBalance) {
        this.bonusBalance = bonusBalance;
    }
    
    public void addBonus(double amount) {
        this.bonusBalance += amount;
    }
    
    public void deductBonus(double amount) {
        this.bonusBalance -= amount;
    }
    
    public void resetBonus() {
        this.bonusBalance = 0.0;
    }
    
    @Override
    public String toString() {
        return String.format("Карта ID: %s, Владелец: %s, Баланс бонусов: %.2f", 
                            cardId, customerName, bonusBalance);
    }
}

