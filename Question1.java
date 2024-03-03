class ChildThread extends java.lang.Thread{
    public void run(){
        System.out.println("In child thread");
    }
}

class Main{
    public static void main(String[] args) {
        System.out.println("In main thread");

        ChildThread t = new ChildThread();
        t.start();
    }
}