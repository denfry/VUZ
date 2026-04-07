import java.util.Random;
import java.util.Scanner;

class GasStation {
    private int gasolineReserve;
    private int successfulRefuelings = 0;
    private final int maxRefuelings;
    private final int maxRefuelAmount;

    public GasStation(int initialReserve, int maxRefuelings, int maxRefuelAmount) {
        this.gasolineReserve = initialReserve;
        this.maxRefuelings = maxRefuelings;
        this.maxRefuelAmount = maxRefuelAmount;
    }

    public synchronized boolean refuel(int amount, String threadName) {
        if (isFinished()) {
            return false;
        }

        System.out.println(threadName + " пытается заправиться на " + amount + " л. (Остаток на заправке: " + gasolineReserve + " л)");

        if (gasolineReserve >= amount) {
            gasolineReserve -= amount;
            successfulRefuelings++;
            System.out.println(threadName + " успешно заправился. Остаток бензина: " + gasolineReserve
                    + " л. (Успешных заправок: " + successfulRefuelings + "/" + maxRefuelings + ")");
            return true;
        }

        System.out.println(threadName + " не смог заправиться: недостаточно бензина.");
        return gasolineReserve > 0;
    }

    public synchronized boolean isFinished() {
        return successfulRefuelings >= maxRefuelings || gasolineReserve <= 0;
    }

    public int getMaxRefuelAmount() {
        return maxRefuelAmount;
    }
}

class Car implements Runnable {
    private final GasStation station;
    private final Random random = new Random();

    public Car(GasStation station) {
        this.station = station;
    }

    @Override
    public void run() {
        while (!station.isFinished()) {
            int amount = 1 + random.nextInt(station.getMaxRefuelAmount());
            boolean keepGoing = station.refuel(amount, Thread.currentThread().getName());
            if (!keepGoing) {
                break;
            }
            try {
                Thread.sleep(100 + random.nextInt(400));
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
        System.out.println(Thread.currentThread().getName() + " завершил работу.");
    }
}

public class Task1 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int threadCount = readThreadCount(scanner);
        GasStation station = new GasStation(1000, 15, 50);

        Thread[] threads = new Thread[threadCount];
        for (int i = 0; i < threadCount; i++) {
            threads[i] = new Thread(new Car(station), "Автомобиль-" + (i + 1));
            threads[i].start();
        }

        for (int i = 0; i < threadCount; i++) {
            try {
                threads[i].join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }
        }
        System.out.println("Все заправки завершены. Процесс окончен.");
    }

    private static int readThreadCount(Scanner scanner) {
        while (true) {
            System.out.print("Введите количество потоков: ");
            if (scanner.hasNextInt()) {
                int threadCount = scanner.nextInt();
                if (threadCount > 0) {
                    return threadCount;
                }
                System.out.println("Количество потоков должно быть больше 0.");
            } else {
                System.out.println("Введите целое число.");
                scanner.next();
            }
        }
    }
}
