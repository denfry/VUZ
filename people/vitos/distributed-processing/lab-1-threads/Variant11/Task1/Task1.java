import java.util.Random;
import java.util.Scanner;

class Store {
    private int goodsLeft;
    private int operationsDone;
    private final int maxOperations;

    public Store(int initialGoods, int maxOperations) {
        this.goodsLeft = initialGoods;
        this.maxOperations = maxOperations;
        this.operationsDone = 0;
    }

    public synchronized boolean buy(int requestedAmount, String buyerName) {
        if (isFinished()) {
            return false;
        }

        operationsDone++;
        int purchasedAmount = Math.min(requestedAmount, goodsLeft);
        goodsLeft -= purchasedAmount;

        System.out.println(
                buyerName + " хочет купить " + requestedAmount + " ед. товара. " +
                "Куплено: " + purchasedAmount + ". Остаток: " + goodsLeft +
                ". Операция " + operationsDone + " из " + maxOperations
        );

        if (goodsLeft == 0) {
            System.out.println("Товар полностью закончился.");
        }

        if (operationsDone >= maxOperations) {
            System.out.println("Достигнут лимит операций.");
        }

        return !isFinished();
    }

    public synchronized boolean isFinished() {
        return goodsLeft <= 0 || operationsDone >= maxOperations;
    }

    public synchronized int getGoodsLeft() {
        return goodsLeft;
    }
}

class Buyer implements Runnable {
    private final Store store;
    private final Random random;

    public Buyer(Store store, long seed) {
        this.store = store;
        this.random = new Random(seed);
    }

    @Override
    public void run() {
        while (!store.isFinished()) {
            int requestedAmount = 1 + random.nextInt(25);
            boolean continueWork = store.buy(requestedAmount, Thread.currentThread().getName());
            if (!continueWork) {
                break;
            }

            try {
                Thread.sleep(150 + random.nextInt(150));
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }
        }

        System.out.println(Thread.currentThread().getName() + " завершил покупки.");
    }
}

public class Task1 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int initialGoods = readPositiveInt(scanner, "Введите начальный объем товара: ");
        int maxOperations = readPositiveInt(scanner, "Введите максимальное число операций: ");

        Store store = new Store(initialGoods, maxOperations);

        Thread buyer1 = new Thread(new Buyer(store, 11L), "Покупатель-1");
        Thread buyer2 = new Thread(new Buyer(store, 29L), "Покупатель-2");

        buyer1.start();
        buyer2.start();

        try {
            buyer1.join();
            buyer2.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return;
        }

        System.out.println("Работа магазина завершена. Остаток товара: " + store.getGoodsLeft());
    }

    private static int readPositiveInt(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            if (scanner.hasNextInt()) {
                int value = scanner.nextInt();
                if (value > 0) {
                    return value;
                }
                System.out.println("Введите число больше 0.");
            } else {
                System.out.println("Введите целое число.");
                scanner.next();
            }
        }
    }
}
