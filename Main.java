import java.util.Scanner;

public class Main {
    private static final Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        RequestManager manager = new RequestManager();

        loadDemoData(manager);

        System.out.println("==============================================");
        System.out.println("     FACULTY SERVICE REQUEST SYSTEM");
        System.out.println("              GROUND ZERO");
        System.out.println("==============================================");
        System.out.println("Demo data has been loaded.");
        System.out.println("You can inspect it or create your own requests.");

        int choice;

        do {
            displayMenu();
            choice = readInt("Enter choice: ");

            switch (choice) {
                case 1:
                    submitRequest(manager);
                    break;
                case 2:
                    viewRequest(manager);
                    break;
                case 3:
                    processNextRequest(manager);
                    break;
                case 4:
                    updateRequestStatus(manager);
                    break;
                case 5:
                    viewRequestHistory(manager);
                    break;
                case 6:
                    undoLastAction(manager);
                    break;
                case 7:
                    manager.displayPendingRequests();
                    break;
                case 8:
                    manager.displayAllRequests();
                    break;
                case 9:
                    System.out.println("\nThank you for using Ground Zero's Service Manager.");
                    break;
                default:
                    System.out.println("\nInvalid choice. Please select 1-9.");
            }
        } while (choice != 9);

        sc.close();
    }

    private static void displayMenu() {
        System.out.println("\n==============================================");
        System.out.println("                    MENU");
        System.out.println("==============================================");
        System.out.println("1. Submit New Request");
        System.out.println("2. View Request");
        System.out.println("3. Process Next Request");
        System.out.println("4. Update Request Status");
        System.out.println("5. View Request History");
        System.out.println("6. Undo Last Status Change");
        System.out.println("7. View Pending Queue");
        System.out.println("8. View All Requests");
        System.out.println("9. Exit");
    }

    private static void submitRequest(RequestManager manager) {
        System.out.println("\n===== SUBMIT NEW REQUEST =====");

        int studentId = readPositiveInt("Student ID: ");
        String name = readNonEmptyString("Student Name: ");
        String department = readNonEmptyString("Department: ");
        int year = readIntInRange("Year (1-4): ", 1, 4);

        int requestId = readPositiveInt("Request ID: ");

        if (manager.requestExists(requestId)) {
            System.out.println("Error: Request ID " + requestId + " already exists.");
            return;
        }

        String type = readNonEmptyString("Request Type: ");
        String description = readNonEmptyString("Description: ");

        Student student = new Student(
                studentId, name, department, year
        );

        Request request = new Request(
                requestId, student, type, description
        );

        if (manager.addRequest(request)) {
            System.out.println("\nRequest submitted successfully.");
            System.out.println("Request ID: " + requestId);
            System.out.println("Status: PENDING");
        }
    }

    private static void viewRequest(RequestManager manager) {
        System.out.println("\n===== VIEW REQUEST =====");

        int requestId = readPositiveInt("Request ID: ");
        Request request = manager.findRequest(requestId);

        if (request == null) {
            System.out.println("Error: Request not found.");
            return;
        }

        System.out.println(request);
    }

    private static void processNextRequest(RequestManager manager) {
        System.out.println("\n===== PROCESS NEXT REQUEST =====");

        Request request = manager.processNextRequest();

        if (request == null) {
            System.out.println("No pending requests in the queue.");
            return;
        }

        System.out.println("Processing Request #" + request.getRequestId());
        System.out.println("Student: " + request.getStudent().getName());
        System.out.println("Type: " + request.getType());
        System.out.println("Status: " + request.getStatus());
    }

    private static void updateRequestStatus(RequestManager manager) {
        System.out.println("\n===== UPDATE REQUEST STATUS =====");

        int requestId = readPositiveInt("Request ID: ");

        if (!manager.requestExists(requestId)) {
            System.out.println("Error: Request not found.");
            return;
        }

        Request request = manager.findRequest(requestId);

        System.out.println("Current status: " + request.getStatus());
        System.out.println("1. APPROVED");
        System.out.println("2. REJECTED");

        int choice = readInt("Choose new status: ");

        RequestStatus newStatus;

        if (choice == 1) {
            newStatus = RequestStatus.APPROVED;
        } else if (choice == 2) {
            newStatus = RequestStatus.REJECTED;
        } else {
            System.out.println("Invalid status choice.");
            return;
        }

        if (manager.changeStatus(requestId, newStatus)) {
            System.out.println(
                    "Request #" + requestId
                            + " changed to " + newStatus + "."
            );
        } else {
            System.out.println(
                    "Status was not changed. It may already have that status."
            );
        }
    }

    private static void viewRequestHistory(RequestManager manager) {
        System.out.println("\n===== VIEW REQUEST HISTORY =====");

        int requestId = readPositiveInt("Request ID: ");
        manager.displayHistory(requestId);
    }

    private static void undoLastAction(RequestManager manager) {
        System.out.println("\n===== UNDO LAST STATUS CHANGE =====");

        Action action = manager.undoLastAction();

        if (action == null) {
            System.out.println("Nothing to undo.");
            return;
        }

        System.out.println("Undo successful.");
        System.out.println(
                "Request #" + action.getRequestId()
                        + " restored from "
                        + action.getNewStatus()
                        + " to "
                        + action.getOldStatus()
        );
    }

    private static void loadDemoData(RequestManager manager) {
        Student s1 = new Student(101, "Arun", "AI&DS", 2);
        Student s2 = new Student(102, "Priya", "CSE", 2);
        Student s3 = new Student(103, "Rahul", "AI&DS", 3);

        manager.addRequest(
                new Request(
                        1001,
                        s1,
                        "Leave",
                        "Medical leave for two days"
                )
        );

        manager.addRequest(
                new Request(
                        1002,
                        s2,
                        "Late Slip",
                        "Request for late entry approval"
                )
        );

        manager.addRequest(
                new Request(
                        1003,
                        s3,
                        "Appointment",
                        "Request to meet the HoD"
                )
        );
    }

    private static int readInt(String message) {
        while (true) {
            System.out.print(message);
            String input = sc.nextLine().trim();

            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
            }
        }
    }

    private static int readPositiveInt(String message) {
        while (true) {
            int value = readInt(message);

            if (value > 0) {
                return value;
            }

            System.out.println("Please enter a positive number.");
        }
    }

    private static int readIntInRange(String message, int min, int max) {
        while (true) {
            int value = readInt(message);

            if (value >= min && value <= max) {
                return value;
            }

            System.out.println(
                    "Please enter a value between " + min + " and " + max + "."
            );
        }
    }

    private static String readNonEmptyString(String message) {
        while (true) {
            System.out.print(message);
            String input = sc.nextLine().trim();

            if (!input.isEmpty()) {
                return input;
            }

            System.out.println("Input cannot be empty.");
        }
    }
}
