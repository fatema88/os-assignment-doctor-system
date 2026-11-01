
import java.util.Random;

public class Doctor extends Thread {

    private final int id;
    private final waitingRoom waitingRoom;
    private final TreatmentRooms treatmentRooms;

    private volatile boolean running = true;

    private final Random random = new Random();

    private static final int MIN_TREATMENT_TIME = 1000;
    private static final int MAX_TREATMENT_TIME = 3000;

    public Doctor(int id, waitingRoom waitingRoom,
                  TreatmentRooms treatmentRooms) {

        this.id = id;
        this.waitingRoom = waitingRoom;
        this.treatmentRooms = treatmentRooms;

        setName("Doctor-" + id);
    }

    @Override
    public void run() {

        while (running) {

            Patient patient;

            try {
                patient = waitingRoom.takePatient();

            } catch (InterruptedException e) {

                if (!running) {
                    break;
                }

                Thread.currentThread().interrupt();
                break;
            }

            boolean roomAcquired = false;

            try {

                System.out.println(getName() +
                        " is trying to get a treatment room for " +
                        patient.getName());

                roomAcquired =
                        treatmentRooms.tryAcquireRoom(3000);

                if (!roomAcquired) {

                    System.out.println(getName() +
                            " could not get a treatment room for " +
                            patient.getName());

                    waitingRoom.enterWaitingRoom(patient);

                    continue;
                }

                System.out.println(getName() +
                        " started treating " + patient.getName());

                int treatmentTime = random.nextInt(
                        MAX_TREATMENT_TIME - MIN_TREATMENT_TIME + 1
                ) + MIN_TREATMENT_TIME;

                Thread.sleep(treatmentTime);

                System.out.println(getName() +
                        " finished treating " + patient.getName());

                patient.completeTreatment();

            } catch (InterruptedException e) {

                Thread.currentThread().interrupt();
                running = false;

            } finally {

                if (roomAcquired) {

                    treatmentRooms.releaseRoom();

                    System.out.println(getName() +
                            " released the treatment room.");
                }
            }
        }

        System.out.println(getName() + " stopped working.");
    }

    public void stopDoctor() {
        running = false;
        interrupt();
    }
}
