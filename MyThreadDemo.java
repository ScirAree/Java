public class MyThreadDemo {
    public static void main(String[] args) {
        // Create a new thread using a Runnable
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 5; i++) {
                    System.out.println("Hello from the thread! " + i);
                    try {
                        Thread.sleep(1000); // Sleep for 1 second
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        // Start the thread
        thread.start();

        // Main thread work
        for (int i = 0; i < 5; i++) {
            System.out.println("Hello from the main thread! " + i);
            try {
                Thread.sleep(1000); // Sleep for 1 second
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}