public class TreatmentRooms {

    private final Semaphore rooms;

    public TreatmentRooms() {
        rooms = new Semaphore(2);
    }

    public boolean tryAcquireRoom(long timeoutMillis)
            throws InterruptedException {
        return rooms.tryAcquire(timeoutMillis);
    }

    public void releaseRoom() {
        rooms.release();
    }
}