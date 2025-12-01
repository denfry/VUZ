import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;
// 2.1
public class ArrayFromFileDivBy3 {
    
    public static void main(String[] args) {
        try {
            int[] array = readArrayFromFile("input.txt");
            
            System.out.println("Массив, прочитанный из файла input.txt:");
            printArray(array);
            
            int count = countDivisibleBy3(array);
            System.out.println("Количество элементов, делящихся на 3 без остатка: " + count);
            
        } catch (Exception e) {
            System.out.println("Ошибка чтения файла: " + e.getMessage());
        }
    }

    public static int[] readArrayFromFile(String filename) throws Exception {
        Scanner fileScanner = new Scanner(new File(filename));
        ArrayList<Integer> list = new ArrayList<>();
        
        while (fileScanner.hasNextInt()) {
            list.add(fileScanner.nextInt());
        }
        fileScanner.close();
        
        int[] arr = new int[list.size()];
        for (int i = 0; i < list.size(); i++) {
            arr[i] = list.get(i);
        }
        return arr;
    }

    public static int countDivisibleBy3(int[] arr) {
        int count = 0;
        for (int x : arr) {
            if (x != 0 && x % 3 == 0) count++;
        }
        return count;
    }

    public static void printArray(int[] arr) {
        for (int x : arr) System.out.print(x + " ");
        System.out.println();
    }
}