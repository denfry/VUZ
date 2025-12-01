import java.util.ArrayList;
import java.util.Scanner;
// 2.2
public class NonZeroEvenOddIndex {
    
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Введите размер массива Zm: ");
        int m = sc.nextInt();
        
        int[] Zm = new int[m];
        System.out.println("Введите элементы массива Zm:");
        for (int i = 0; i < m; i++) {
            Zm[i] = sc.nextInt();
        }

        int[] Xk = formNonZeroEvenOddArray(Zm);

        System.out.println("Исходный массив Zm:");
        printArray(Zm);
        System.out.println("Новый массив Xk (сначала ненулевые с чётными индексами, потом с нечётными):");
        printArray(Xk);

        sc.close();
    }

    public static int[] formNonZeroEvenOddArray(int[] Zm) {
        ArrayList<Integer> list = new ArrayList<>();

        for (int i = 0; i < Zm.length; i += 2) {
            if (Zm[i] != 0) list.add(Zm[i]);
        }
        for (int i = 1; i < Zm.length; i += 2) {
            if (Zm[i] != 0) list.add(Zm[i]);
        }

        int[] result = new int[list.size()];
        for (int i = 0; i < list.size(); i++) {
            result[i] = list.get(i);
        }
        return result;
    }

    public static void printArray(int[] arr) {
        if (arr.length == 0) {
            System.out.println("(пустой массив)");
            return;
        }
        ArrayFromFileDivBy3.printArray(arr);
    }
}