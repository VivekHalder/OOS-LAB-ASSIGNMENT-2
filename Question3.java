class SumPrinter{
    private static final int MIN_DIV = 1;
    private static final int MAX_DIV = 10;
    private static long curr = 1;
    private static boolean isCalculated = false;
    private static long div = 0;
    private static final Object lock = new Object();
    private static double sum = 1;

    private static final class Divider implements java.lang.Runnable{
        public void run(){
            for(int i=MIN_DIV; i<=MAX_DIV; i++){
                synchronized (lock){
                    while(isCalculated){
                        try {
                            lock.wait();
                        } catch (InterruptedException ie) {
                            ie.printStackTrace();;
                        }
                    }
                    div = curr;
                    curr *= (i+1);
                    isCalculated = true;
                    lock.notify();
                }
            }
        }
    }

    private static final class Summer implements java.lang.Runnable{
        public void run(){
            for(int i=MIN_DIV; i<=MAX_DIV; i++){
                synchronized (lock){
                    while(!isCalculated){
                        try {
                            lock.wait();
                        } catch (InterruptedException ie) {
                            ie.printStackTrace();;
                        }
                    }
                    sum += (double) 1/div;
                    isCalculated = false;
                    lock.notify();
                }
            }

            System.out.println("The sum is: "+ sum + ".");
        }
    }

    public static void main(String[] args) {
        Thread divider = new Thread(new Divider());
        Thread summer = new Thread(new Summer());

        divider.start();
        summer.start();
    }
}