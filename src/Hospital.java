
import java.util.Scanner;

public class Hospital {

    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);

        int capacity = readPositiveInteger(input,
                "Enter waiting room capacity: ");

        int doctorsCount = readPositiveInteger(input,
                "Enter number of doctors: ");

        int patientsCount = readPositiveInteger(input,
                "Enter number of patients: ");

        // Create shared resources
        waitingRoom waitingRoom = new waitingRoom(capacity);
        TreatmentRooms treatmentRooms = new TreatmentRooms();

        // Create Doctors
        Doctor[] doctors = new Doctor[doctorsCount];

        for (int i = 0; i < doctorsCount; i++) {
            doctors[i] = new Doctor(i + 1, waitingRoom, treatmentRooms);
        }

        // Create Patients
        Thread[] patients = new Thread[patientsCount];

        for (int i = 0; i < patientsCount; i++) {
            Patient patient = new Patient("P" + (i + 1), waitingRoom);
            patients[i] = new Thread(patient);
        }

        System.out.println("\nPatients:");

        for (int i = 0; i < patientsCount; i++) {
            System.out.print("P" + (i + 1) + " ");
        }

        System.out.println("\n");

        // Start Doctors
        for (Doctor doctor : doctors) {
            doctor.start();
        }

        // Start Patients
        for (Thread patient : patients) {
            patient.start();
        }

        boolean interrupted = false;

        try {

            // Wait for all Patients
            for (Thread patient : patients) {
                patient.join();
            }

            System.out.println("\nAll patients have been treated.");

        } catch (InterruptedException e) {

            interrupted = true;
            System.err.println("Hospital simulation was interrupted.");

        } finally {

            // Stop Doctors
            for (Doctor doctor : doctors) {
                doctor.stopDoctor();
            }

            // Interrupt Patients if simulation was interrupted
            if (interrupted) {
                for (Thread patient : patients) {
                    patient.interrupt();
                }
            }

            // Wait for all Doctors and Patients to terminate
            boolean allFinished = false;

            while (!allFinished) {
                allFinished = true;

                try {
                    for (Thread patient : patients) {
                        patient.join();
                    }

                    for (Doctor doctor : doctors) {
                        doctor.join();
                    }

                } catch (InterruptedException e) {
                    interrupted = true;
                    allFinished = false;

                    for (Doctor doctor : doctors) {
                        doctor.stopDoctor();
                    }

                    for (Thread patient : patients) {
                        patient.interrupt();
                    }
                }
            }
        }

        if (interrupted) {
            Thread.currentThread().interrupt();
        } else {
            System.out.println("Simulation completed.");
        }

        input.close();
    }

    // Read and validate positive integer input
    private static int readPositiveInteger(
            Scanner input, String message) {

        int value;

        while (true) {

            System.out.print(message);

            if (!input.hasNextInt()) {
                System.out.println("Invalid input! Please enter an integer.");
                input.next();
                continue;
            }

            value = input.nextInt();

            if (value <= 0) {
                System.out.println("Value must be greater than zero.");
                continue;
            }

            return value;
        }
    }
}
