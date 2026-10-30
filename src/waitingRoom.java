import java.util.Queue;
import java.util.LinkedList;

public class WaitingRoom {

    private Queue<Patient> patients;
    private Semaphore spaces;
    private Semaphore availablePatients;
    private Semaphore mutex;

    public WaitingRoom(int capacity) {
        patients = new LinkedList<>();
        spaces = new Semaphore(capacity);
        availablePatients = new Semaphore(0);
        mutex = new Semaphore(1);
    }

    public void enterWaitingRoom(Patient patient) throws InterruptedException {
        spaces.acquire();
        boolean added = false;
        try {
            mutex.acquire();
            try {
                patients.add(patient);
                added = true;
            } finally {
                mutex.release();
            }
            availablePatients.release();
        } finally {
            if (!added) {
                spaces.release();
            }
        }
    }

    public Patient takePatient() throws InterruptedException {
        availablePatients.acquire();
        boolean patientRemoved = false;
        try {
            mutex.acquire();
            try {
                Patient patient = patients.poll();
                if (patient != null) {
                    patientRemoved = true;
                }
                return patient;
            } finally {
                mutex.release();
            }
        } finally {
            if (patientRemoved) {
                spaces.release();
            } else {
                availablePatients.release();
            }
        }
    }
}
