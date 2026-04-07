/**
 * Класс для описания вида морозильной камеры
 */
public class FreezerChamberType {
    private String code;        // Код
    private String name;        // Наименование
    
    public FreezerChamberType(String code, String name) {
        this.code = code;
        this.name = name;
    }
    
    public String getCode() {
        return code;
    }
    
    public String getName() {
        return name;
    }
    
    @Override
    public String toString() {
        return String.format("%s (%s)", name, code);
    }
}

