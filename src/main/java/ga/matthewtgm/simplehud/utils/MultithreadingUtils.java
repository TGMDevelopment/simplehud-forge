package ga.matthewtgm.simplehud.utils;

public class MultithreadingUtils {

    private static MultithreadingUtils INSTANCE = new MultithreadingUtils();
    public static MultithreadingUtils getInstance() {
        return INSTANCE;
    }

    public void createNewThread(String name, Runnable runnable) {
        new Thread(name) {
            @Override
            public void run() {
                runnable.run();
            }
        }.start();
    }

    public Thread createThread(String name, Runnable runnable) {
        return new Thread(name) {
            @Override
            public void run() {
                runnable.run();
                super.run();
            }
        };
    }

}