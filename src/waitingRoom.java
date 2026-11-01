
import java.util.Queue;
import java.util.LinkedList;


public class waitingRoom {

    private Queue<Patient> patients;
    private Semaphore spaces;
    private Semaphore availablePatients;
    private Semaphore mutex;

    public waitingRoom(int capacity) {
        patients = new LinkedList<>();
        spaces = new Semaphore(capacity);
        availablePatients = new Semaphore(0);
        mutex = new Semaphore(1);
    }

    // public void enterWaitingRoom(Patient patient) throws InterruptedException {
    // spaces.acquire();
    // boolean added = false;
    // try {
    // mutex.acquire();
    // try {
    // patients.add(patient);
    // added = true;
    // } finally {
    // mutex.release();
    // }
    // availablePatients.release();
    // } finally {
    // if (!added) {
    // spaces.release();
    // }
    // }
    // }

    public void enterWaitingRoom(Patient patient) throws InterruptedException {

        if (spaces.tryAcquire(0)) {
            mutex.acquire();
            try {
                patients.add(patient);
                System.out.println(patient.getName() + " entered the waiting room.");
            } finally {
                mutex.release();
            }
            availablePatients.release();
        } else {

            System.out.println("The waiting room is full.");
            System.out.println(patient.getName() + " is waiting outside for an available seat.");

            spaces.acquire();

            mutex.acquire();
            try {
                patients.add(patient);
                System.out.println(patient.getName() + " entered the waiting room.");
            } finally {
                mutex.release();
            }
            availablePatients.release();
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
