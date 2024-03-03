class SeriesCalculator extends Thread {
    private int start;
    private int end;
    private int result;

    public SeriesCalculator(int start, int end) {
        this.start = start;
        this.end = end;
        this.result = 0;
    }

    public int getResult() {
        return result;
    }

    public void run() {
        for (int i = start; i <= end; i++) {
            result += i;
        }
    }
}

class Main {
    public static void main(String[] args) {
        SeriesCalculator oddSeriesCalculator = new SeriesCalculator(1, 100);
        SeriesCalculator evenSeriesCalculator = new SeriesCalculator(2, 100);

        // Start both threads
        oddSeriesCalculator.start();
        evenSeriesCalculator.start();

        try {
            oddSeriesCalculator.join();
            evenSeriesCalculator.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        int oddSum = oddSeriesCalculator.getResult();
        int evenSum = evenSeriesCalculator.getResult();

        int totalSum = oddSum + evenSum;

        System.out.println("Odd Series Sum: " + oddSum);
        System.out.println("Even Series Sum: " + evenSum);
        System.out.println("Total Sum: " + totalSum);
    }
}
