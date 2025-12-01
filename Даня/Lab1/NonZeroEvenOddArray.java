import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

/**
 * Задача 19.2
 * Дан одномерный массив Zm. Сформировать массив Хк, состоящий из 
 * ненулевых элементов массива Zm, сначала с четным, а затем с нечетным индексом.
 * 
 * Способ задания массива: генерация случайных чисел в заданном диапазоне
 */
public class NonZeroEvenOddArray {
    
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        
        System.out.print("Введите размер массива Zm: ");
        int m = sc.nextInt();
        
        System.out.print("Введите минимальное значение (для генерации): ");
        int min = sc.nextInt();
        
        System.out.print("Введите максимальное значение (для генерации): ");
        int max = sc.nextInt();
        
        int[] Zm = generateRandomArray(m, min, max);
        
        System.out.println("Сгенерированный массив Zm:");
        printArray(Zm);
        
        int[] Xk = formNonZeroEvenOddArray(Zm);
        
        System.out.println("Новый массив Xk (сначала ненулевые с чётными индексами, потом с нечётными):");
        if (Xk.length == 0) {
            System.out.println("(пустой массив - нет ненулевых элементов)");
        } else {
            printArray(Xk);
        }
        
        sc.close();
    }
    
    /**
     * Формирует массив из ненулевых элементов исходного массива:
     * сначала элементы с чётными индексами, затем с нечётными
     * @param Zm исходный массив
     * @return новый массив Xk
     */
    public static int[] formNonZeroEvenOddArray(int[] Zm) {
        ArrayList<Integer> list = new ArrayList<>();
        
        // Сначала добавляем ненулевые элементы с чётными индексами
        for (int i = 0; i < Zm.length; i += 2) {
            if (Zm[i] != 0) {
                list.add(Zm[i]);
            }
        }
        
        // Затем добавляем ненулевые элементы с нечётными индексами
        for (int i = 1; i < Zm.length; i += 2) {
            if (Zm[i] != 0) {
                list.add(Zm[i]);
            }
        }
        
        // Преобразуем ArrayList в массив
        int[] result = new int[list.size()];
        for (int i = 0; i < list.size(); i++) {
            result[i] = list.get(i);
        }
        
        return result;
    }
    
    /**
     * Генерирует массив случайных чисел в заданном диапазоне
     * @param size размер массива
     * @param min минимальное значение
     * @param max максимальное значение
     * @return массив случайных чисел
     */
    public static int[] generateRandomArray(int size, int min, int max) {
        Random rnd = new Random();
        int[] arr = new int[size];
        for (int i = 0; i < size; i++) {
            arr[i] = rnd.nextInt(max - min + 1) + min;
        }
        return arr;
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

