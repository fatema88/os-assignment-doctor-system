public class Patient implements Runnable {
    private final String name;
    private final WaitingRoom waitingRoom;
    private final Semaphore treatmentDone;

    public Patient(String name, WaitingRoom waitingRoom) {
        this.name = name;
        this.waitingRoom = waitingRoom;

        this.treatmentDone = new Semaphore(0);
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public void run() {
        try {

            System.out.println(name + " arrived.");

            waitingRoom.enterWaitingRoom(this);

            treatmentDone.acquire();

            System.out.println(name + " left.");

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println(name + " was interrupted.");
        }
    }

    public void completeTreatment() {
        treatmentDone.release();
    }
}