class OddEvenPrinter{
    private static final int MIN_NUM = 2;
    private static final int MAX_NUM = 8;
    private static boolean isOddPrinted = false;
    private static final Object lock = new Object();

    private static final class OddPrinter implements java.lang.Runnable{
        public void run(){
            for(int i=MIN_NUM; i<=MAX_NUM; i+= 2){
                synchronized (lock){
                    while(!isOddPrinted){
                        try{
                            lock.wait();
                        } catch(InterruptedException ie){
                            ie.printStackTrace();
                        }
                    }
                    System.out.print(i + " ");
                    isOddPrinted = false;
                    lock.notify();
                }
            }
        }
    }

    private static final class EvenPrinter implements java.lang.Runnable{
        public void run(){

            for(int i=MIN_NUM-1; i<=MAX_NUM; i+= 2){
                synchronized (lock){
                    while(isOddPrinted){
                        try{
                            lock.wait();
                        } catch(InterruptedException ie){
                            ie.printStackTrace();
                        }
                    }
                    System.out.print(i + " ");
                    isOddPrinted = true;
                    lock.notify();
                }
            }
        }
    } 
    
    public static void main(String[] args) {
        Thread evenThread = new Thread(new EvenPrinter());
        Thread oddThread = new Thread(new OddPrinter());

        evenThread.start();
        oddThread.start();
    }
}