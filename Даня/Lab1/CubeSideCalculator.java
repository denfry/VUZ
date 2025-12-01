import java.util.Scanner;

/**
 * Вариант 19
 * Вычислить сторону куба по его объему
 */
public class CubeSideCalculator {
    
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        
        System.out.print("Введите объём куба V: ");
        double volume = sc.nextDouble();
        
        double side = calculateCubeSide(volume);
        
        if (side < 0) {
            System.out.println("Ошибка: объём не может быть отрицательным!");
        } else {
            System.out.printf("Сторона куба при объёме %.2f = %.3f\n", volume, side);
        }
        
        sc.close();
    }
    
    /**
     * Вычисляет сторону куба по его объему
     * @param volume объём куба
     * @return сторона куба, или -1 если объём отрицательный
     */
    public static double calculateCubeSide(double volume) {
        if (volume < 0) {
            return -1;
        }
        return Math.cbrt(volume);
    }
}

