import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

class LineItem {
    private final String line;
    private final boolean endOfFile;

    private LineItem(String line, boolean endOfFile) {
        this.line = line;
        this.endOfFile = endOfFile;
    }

    public static LineItem line(String value) {
        return new LineItem(value, false);
    }

    public static LineItem end() {
        return new LineItem(null, true);
    }

    public boolean isEndOfFile() {
        return endOfFile;
    }

    public String getLine() {
        return line;
    }
}

class Producer implements Runnable {
    private final BlockingQueue<LineItem> queue;
    private final String fileName;

    public Producer(BlockingQueue<LineItem> queue, String fileName) {
        this.queue = queue;
        this.fileName = fileName;
    }

    @Override
    public void run() {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println("Производитель считал строку: " + line);
                queue.put(LineItem.line(line));
                Thread.sleep(150);
            }
            queue.put(LineItem.end());
            System.out.println("Производитель отправил признак конца файла.");
        } catch (IOException e) {
            System.out.println("Ошибка чтения файла: " + e.getMessage());
            try {
                queue.put(LineItem.end());
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}

class Consumer implements Runnable {
    private final BlockingQueue<LineItem> queue;

    public Consumer(BlockingQueue<LineItem> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        try {
            while (true) {
                LineItem item = queue.take();
                if (item.isEndOfFile()) {
                    System.out.println("Потребитель получил признак конца файла.");
                    break;
                }

                int wordsCount = countWords(item.getLine());
                System.out.println("Потребитель обработал строку: \"" + item.getLine() + "\". Количество слов: " + wordsCount);
                Thread.sleep(200);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private int countWords(String line) {
        String trimmed = line.trim();
        if (trimmed.isEmpty()) {
            return 0;
        }
        return trimmed.split("\\s+").length;
    }
}

public class Task2 {
    public static void main(String[] args) {
        BlockingQueue<LineItem> queue = new ArrayBlockingQueue<>(5);
        String fileName = "data.txt";

        Thread producer = new Thread(new Producer(queue, fileName), "Производитель");
        Thread consumer = new Thread(new Consumer(queue), "Потребитель");

        producer.start();
        consumer.start();

        try {
            producer.join();
            consumer.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return;
        }

        System.out.println("Обработка строк завершена.");
    }
}
