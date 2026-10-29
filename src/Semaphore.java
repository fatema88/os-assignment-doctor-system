public class Semaphore{
    private int permits;

    protected Semaphore(int permits){
        if(permits < 0) throw new IllegalArgumentException();
        else {
            this.permits = permits;
        }
    }
    public synchronized void acquire() throws InterruptedException {
        while(permits == 0) { wait(); }

        permits--;
    }
    public synchronized void release(){
        permits++;
        notifyAll();
    }
    public synchronized boolean tryAcquire(long timeoutMillis) throws InterruptedException{
        if (permits > 0) {
            permits--;
            return true;
        }
        if (timeoutMillis < 0) return false;

        long deadline = System.nanoTime()+ timeoutMillis * 1_000_000L;
        long remaining = timeoutMillis * 1_000_000L;

        while (permits == 0 && remaining > 0) {
            long millis = remaining / 1_000_000L;
            int nanos = (int) (remaining % 1_000_000L);

            wait(millis, nanos);

            remaining = deadline - System.nanoTime();
        }

        if (permits > 0) {
            permits--;
            return true;
        }

        return false;
    }
    
}