# 🏥 Hospital Emergency Room Simulation

A Java-based simulation of a hospital emergency department using **Multithreading, Semaphores, and the Producer–Consumer Problem** with the Bounded Buffer pattern.

This project was developed as part of the **CS241/SCS241 – Operating Systems** course at the Faculty of Computing and Artificial Intelligence, Cairo University.

## 📌 Project Overview

The simulation models a busy hospital emergency room where patients arrive, wait for available spaces, and receive treatment from multiple doctors working concurrently.

The system demonstrates how synchronization mechanisms coordinate access to shared resources, prevent race conditions, and manage limited waiting and treatment capacities.

### Key Features

- **Multithreading:** Patients and doctors operate concurrently using Java threads.
- **Producer–Consumer Pattern:** Patients enter the waiting room while doctors retrieve patients for treatment.
- **Bounded Buffer:** The waiting room has a configurable capacity.
- **Semaphore Implementation:** A custom semaphore manages resource acquisition and release.
- **Treatment Room Management:** Two treatment rooms are shared among all doctors.
- **Timed Resource Acquisition:** Doctors have up to three seconds to acquire a treatment room.
- **Patient Queue Management:** Patients wait outside when the waiting room is full and may return to the queue if treatment cannot begin in time.
- **Event Logging:** The simulation displays patient arrivals, queue activity, doctor assignments, treatment events, and departures.

## ⚙️ System Requirements

The simulation includes the following components:

| Component | Description |
|---|---|
| Patients | Arrive at the hospital and wait for treatment. |
| Doctors | Retrieve patients from the waiting room and attempt to provide treatment. |
| Waiting Room | A bounded queue with a user-defined capacity. |
| Treatment Rooms | Two shared rooms available for patient treatment. |
| Semaphores | Synchronize access to the waiting room and treatment rooms. |

### Treatment Workflow

1. A patient arrives at the hospital.
2. The patient enters the waiting room if a seat is available; otherwise, they wait outside.
3. An available doctor retrieves a patient from the waiting queue.
4. The doctor attempts to acquire a treatment room within three seconds.
5. If a room is acquired, the doctor treats the patient for a random duration.
6. If no room becomes available in time, the patient is returned to the waiting room.
7. After treatment, the doctor releases the room, and the patient leaves the hospital.

## 🛠️ Technologies Used

- **Java**
- **Java Threads and Multithreading**
- **Semaphores**
- **Producer–Consumer Synchronization**
- **Bounded Buffer**
- **Thread Synchronization**

## 📂 Project Structure

```text
Hospital-Emergency-Room/
├── src/
│   ├── Hospital.java
│   ├── Semaphore.java
│   ├── Patient.java
│   ├── Doctor.java
│   └── [Additional resource management classes]
├── README.md
└── ...
```

*Note: The structure above is illustrative. Update the file names and directories to match the actual project structure.*

## 🚀 Getting Started

### Prerequisites

- Java Development Kit (JDK) 17 or later.
- An IDE such as Visual Studio Code or IntelliJ IDEA, or a terminal with Java configured.

Verify your Java installation:

```bash
java -version
javac -version
```

### Running the Project

1. Clone the repository:

   ```bash
   git clone <https://github.com/fatema88/os-assignment-doctor-system.git>
   ```

2. Open the project directory:

   ```bash
   cd os-assignment-doctor-system
   ```

3. Open the project in your preferred Java IDE.

4. Compile and run the application according to your source file structure.

If the project uses a standard `src` directory and has no package declarations, you can compile it from the project root using:

```bash
javac -d bin src/*.java
java -cp bin Hospital
```

If your classes use Java packages or a different directory structure, adjust the commands accordingly.

## 🖥️ Example Input

```text
Waiting room capacity: 2
Number of doctors: 3
Number of patients: 5
```

## 📋 Example Output

The exact output order may vary between executions because multiple threads run concurrently.

```text
P1 arrived.
P1 entered the waiting room.
P2 arrived.
P2 entered the waiting room.
Doctor 1 is assigned to P1.
Doctor 1 is trying to acquire a treatment room.
Doctor 1 acquired a treatment room.
Doctor 1 begins treating P1.
Doctor 1 finished treating P1.
Doctor 1 released the treatment room.
P1 left.
All patients have been treated.
Simulation completed.
```

*The output above is illustrative and may not represent every possible execution scenario.*

## 🎯 Learning Objectives

This project demonstrates practical applications of operating system concepts, including:

- Process and thread coordination.
- Mutual exclusion and shared resource management.
- Semaphore-based synchronization.
- Producer–Consumer synchronization.
- Bounded buffer management.
- Timed waiting and resource allocation.
- Race condition prevention and deadlock avoidance.

## 🎓 Academic Information

**Course:** CS241/SCS241 – Operating Systems  
**Faculty:** Faculty of Computing and Artificial Intelligence  
**University:** Cairo University  
**Assignment:** Assignment 1 – Hospital Emergency Room Simulation

## 👥 Team Members

This project was developed collaboratively by:

- **Student 1:** [Alaa Wafiq] 
- **Student 2:** [Fatma Tarek] 
- **Student 3:** [Nawal Mahmoud] 
- **Student 4:** [Alaa Ahmed] 
- **Student 5:** [Habiba Abohamda]


---

*This project is intended for educational purposes as part of the Operating Systems course.*
