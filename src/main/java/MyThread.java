import java.io.*;

public class MyThread implements Runnable {
    static final File file = new File("C://test_java", "out.txt");
    private static final Object lock = new Object();
    static int count = 0;
    static int num = 0;
    private static boolean running = true;


    public static void main(String[] args) throws IOException, InterruptedException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        try {
            num = Integer.parseInt(reader.readLine());
        } catch (Exception e) {
            throw new IllegalArgumentException("Нужно вводить число!");
        }
        startThread(num);
    }

    public static void startThread(int input) throws IOException, InterruptedException {
        writeFile(0);
        num = input; // for test
        Thread firstThread = new Thread(new MyThread());
        Thread secondThread = new Thread(new MyThread());
        if (num > 0 && num % 2 == 0) {
            firstThread.start();
            secondThread.start();
            firstThread.join();
            secondThread.join();
            System.out.println(readFile());
        } else {
            System.out.println("Нужно ввести целое и четное число");
        }
    }

    public static void work() throws IOException {
        synchronized (lock) {
            if (count < num) {
                int tempNum = 0;
                try {
                    String s = readFile();
                    tempNum = Integer.parseInt(s);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                count = tempNum + 1;
                try {
                    writeFile(count);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                System.out.println(tempNum + " " + count + " " + Thread.currentThread().getName());
            } else {
                running = false;
            }
        }
    }

    public static void writeFile(Integer element) throws IOException {
        try (FileWriter fileWriter = new FileWriter(file);
             BufferedWriter bufferedWriter = new BufferedWriter(fileWriter)) {
            bufferedWriter.write(String.valueOf(element));
        }
    }

    public static String readFile() throws IOException {
        try (FileReader fileReader = new FileReader(file);
             BufferedReader bufferedReader = new BufferedReader(fileReader)) {
            String s = bufferedReader.readLine();
            return String.valueOf(s);
        }
    }


    @Override
    public void run() {
        while (running) {
            try {
                work();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
