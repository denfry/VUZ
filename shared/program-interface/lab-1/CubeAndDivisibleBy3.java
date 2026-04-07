import java.util.Random;
import java.util.Scanner;
// 1.1
public class CubeAndDivisibleBy3 {
    
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Введите объём куба V: ");
        double volume = sc.nextDouble();
        double side = calculateCubeSide(volume);
        System.out.printf("Сторона куба при объёме %.2f = %.3f\n\n", volume, side);

        System.out.print("Введите размер массива Xn: ");
        int n = sc.nextInt();
        
        int[] Xn = generateRandomArray(n, -50, 50); 
        System.out.println("Сгенерированный массив Xn:");
        printArray(Xn);
        
        int countDivBy3 = countDivisibleBy3(Xn);
        System.out.println("Количество элементов, делящихся на 3 без остатка: " + countDivBy3);

        sc.close();
    }

    public static double calculateCubeSide(double volume) {
        if (volume < 0) return -1; 
        return Math.cbrt(volume);
    }

    public static int countDivisibleBy3(int[] arr) {
        int count = 0;
        for (int x : arr) {
            if (x % 3 == 0 && x != 0) count++; 
        }
        return count;
    }

    public static int[] generateRandomArray(int size, int min, int max) {
        Random rnd = new Random();
        int[] arr = new int[size];
        for (int i = 0; i < size; i++) {
            arr[i] = rnd.nextInt(max - min + 1) + min;
        }
        return arr;
    }

    public static void printArray(int[] arr) {
        ArrayFromFileDivBy3.printArray(arr);
    }
}