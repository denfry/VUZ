import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

class QueueItem {
    private final Integer value;
    private final boolean endOfStream;

    private QueueItem(Integer value, boolean endOfStream) {
        this.value = value;
        this.endOfStream = endOfStream;
    }

    public static QueueItem value(int value) {
        return new QueueItem(value, false);
    }

    public static QueueItem end() {
        return new QueueItem(null, true);
    }

    public boolean isEndOfStream() {
        return endOfStream;
    }

    public Integer getValue() {
        return value;
    }
}

class Producer implements Runnable {
    private final BlockingQueue<QueueItem> queue;
    private final List<Integer> list;
    private final int itemsToProduce;
    private final Random random = new Random();

    public Producer(BlockingQueue<QueueItem> queue, String filename, int itemsToProduce) {
        this.queue = queue;
        this.list = new ArrayList<>();
        this.itemsToProduce = itemsToProduce;

        try (Scanner scanner = new Scanner(new File(filename))) {
            while (scanner.hasNext()) {
                if (scanner.hasNextInt()) {
                    list.add(scanner.nextInt());
                } else {
                    scanner.next();
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("Файл " + filename + " не найден. Будет использован пустой список.");
        }
    }

    @Override
    public void run() {
        try {
            if (list.isEmpty()) {
                System.out.println("Производитель: Список пуст. Завершаю работу.");
                queue.put(QueueItem.end());
                return;
            }

            for (int i = 0; i < itemsToProduce; i++) {
                int item = list.get(random.nextInt(list.size()));
                System.out.println("Производитель выбрал элемент: " + item);
                queue.put(QueueItem.value(item));
                Thread.sleep(200);
            }

            System.out.println("Производитель отправляет символ конца файла (EOF).");
            queue.put(QueueItem.end());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}

class Consumer implements Runnable {
    private final BlockingQueue<QueueItem> queue;
    private long product = 1;
    private boolean hasPositive = false;

    public Consumer(BlockingQueue<QueueItem> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        try {
            while (true) {
                QueueItem item = queue.take();

                if (item.isEndOfStream()) {
                    System.out.println("Потребитель получил символ EOF. Остановка.");
                    break;
                }

                int num = item.getValue();
                if (num > 0) {
                    product *= num;
                    hasPositive = true;
                    System.out.println("Потребитель: Обработано положительное число: " + num
                            + ". Текущее произведение: " + product);
                } else {
                    System.out.println("Потребитель: Пропущено число: " + num + " (не положительное).");
                }

                Thread.sleep(300);
            }

            if (hasPositive) {
                System.out.println("--- ИТОГ ---");
                System.out.println("Потребитель завершил работу. Итоговое произведение: " + product);
            } else {
                System.out.println("Потребитель завершил работу. Положительных элементов не было.");
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}

public class Task2 {
    public static void main(String[] args) {
        BlockingQueue<QueueItem> queue = new ArrayBlockingQueue<>(10);
        String filename = "data.txt";

        Thread producerThread = new Thread(new Producer(queue, filename, 10), "Производитель");
        Thread consumerThread = new Thread(new Consumer(queue), "Потребитель");

        producerThread.start();
        consumerThread.start();

        try {
            producerThread.join();
            consumerThread.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return;
        }
        System.out.println("Программа завершена.");
    }
}
