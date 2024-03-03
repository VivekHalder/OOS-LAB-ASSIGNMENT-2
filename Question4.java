import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.lang.Thread;

class Producer extends Thread {
    private final BlockingQueue<Integer> queue;
    private final File file;

    public Producer(BlockingQueue<Integer> queue, File file) {
        this.queue = queue;
        this.file = file;
    }

    public void run() {
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                int number = Integer.parseInt(line.trim());
                queue.put(number);
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}

class Consumer extends Thread {
    private final BlockingQueue<Integer> queue;

    public Consumer(BlockingQueue<Integer> queue) {
        this.queue = queue;
    }

    public void run() {
        int sum = 0;
        try {
            while (true) {
                int number = queue.take();
                if (number == -1) {
                    break;
                }
                sum += number;
                System.out.println("Consumed: " + number);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Total Sum: " + sum);
    }
}

class Main {
    public static void main(String[] args) {
        BlockingQueue<Integer> queue = new ArrayBlockingQueue<>(10); 
        File file = new File("integers.txt");

        Producer producer = new Producer(queue, file);
        Consumer consumer = new Consumer(queue);

        producer.start();
        consumer.start();
    }
}
