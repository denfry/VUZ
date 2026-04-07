import java.util.Scanner;

/**
 * Задача 19.1
 * Дан одномерный массив Xn. Найти количество элементов массива, 
 * делящихся на 3 без остатка.
 * 
 * Способ задания массива: ввод с клавиатуры
 */
public class ArrayDivisibleBy3 {
    
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        
        System.out.print("Введите размер массива Xn: ");
        int n = sc.nextInt();
        
        int[] Xn = new int[n];
        System.out.println("Введите элементы массива Xn:");
        for (int i = 0; i < n; i++) {
            Xn[i] = sc.nextInt();
        }
        
        System.out.println("Введённый массив Xn:");
        printArray(Xn);
        
        int count = countDivisibleBy3(Xn);
        System.out.println("Количество элементов, делящихся на 3 без остатка: " + count);
        
        sc.close();
    }
    
    /**
     * Находит количество элементов массива, делящихся на 3 без остатка
     * @param arr массив для проверки
     * @return количество элементов, делящихся на 3 без остатка
     */
    public static int countDivisibleBy3(int[] arr) {
        int count = 0;
        for (int x : arr) {
            if (x != 0 && x % 3 == 0) {
                count++;
            }
        }
        return count;
    }
    
    /**
     * Выводит массив на экран
     * @param arr массив для вывода
     */
    public static void printArray(int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i]);
            if (i < arr.length - 1) {
                System.out.print(" ");
            }
        }
        System.out.println();
    }
}

