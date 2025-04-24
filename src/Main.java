import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    static int floor, room;
    static String[][] condo;
    static ArrayList<String> history = new ArrayList<>();
    static Scanner scanner = new Scanner(System.in);
    static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static void main(String[] args) {
        do {
            System.out.println(Colors.YELLOW + "-".repeat(36));
            System.out.println(Colors.BLUE_BRIGHT + "\n=== Application Menu ===");
            System.out.println("-".repeat(36));
            System.out.println("A. Setup condo (floor and room)");
            System.out.println("B. Display Available Room");
            System.out.println("C. Check-in Room");
            System.out.println("D. Check-out Room");
            System.out.println("E. View History Record");
            System.out.println("F. Exit");
            System.out.println(Colors.RESET + "-".repeat(36) + Colors.RESET);

            System.out.print("Enter option : ");
            String option = scanner.nextLine().toLowerCase();

            switch (option) {
                case "a": {
                    System.out.println(Colors.CYAN_BRIGHT +"*** Setup condo ***");
                    System.out.println("-".repeat(30));

                    System.out.print("Enter number of floor : ");
                    floor = getPositiveInt();
                    System.out.print("Enter number of room : ");
                    room = getPositiveInt();

                    condo = new String[floor][room];

                    System.out.println(Colors.RESET +"Condo setup complete:");
                    for (int i = 0; i < floor; i++) {
                        for (int j = 0; j < room; j++) {
                            System.out.print(" F" + (i + 1) + "-R" + (j + 1) + " | ");
                        }
                        System.out.println();
                    }
                    break;
                }
                case "b": {
                    System.out.println(Colors.CYAN_BRIGHT +"*** Display Available Room ***");
                    System.out.println("-".repeat(30));
                    if (floor == 0 || condo == null) {
                        System.out.println(Colors.RED + "Please setup Condo first!");
                    } else {
                        boolean hasAvailable = false;
                        for (int i = 0; i < floor; i++) {
                            for (int j = 0; j < room; j++) {
                                if (condo[i][j] == null) {
                                    System.out.print(" F" + (i + 1) + "-R" + (j + 1) + " | ");
                                    hasAvailable = true;
                                }
                            }
                            System.out.println();
                        }
                        if (!hasAvailable) {
                            System.out.println(Colors.RED + Colors.RESET + "No rooms available.");
                        }
                    }
                    break;
                }
                case "c": {
                    System.out.println(Colors.CYAN_BRIGHT + "*** Check-in Room ***");
                    System.out.println("-".repeat(30));
                    if (floor == 0 || condo == null) {
                        System.out.println( Colors.RED +"Please setup Condo first!");
                    } else {
                        System.out.print("Enter floor (1-" + floor + ") : ");
                        int checkInFloor = getPositiveInt() - 1;
                        if (checkInFloor < 0 || checkInFloor >= floor) {
                            System.out.println(Colors.RED+"Invalid floor number!");
                            break;
                        }

                        System.out.print("Enter room (1-" + room + ") : ");
                        int checkInRoom = getPositiveInt() - 1;
                        if (checkInRoom < 0 || checkInRoom >= room) {
                            System.out.println(Colors.RED+"Invalid room number!");
                            break;
                        }

                        if (condo[checkInFloor][checkInRoom] == null) {

                            System.out.print("Enter name : ");
                            String name = getEmptyString();
                            System.out.print("Enter gender (Male/Female/Other) : ");
                            String gender = getValidGender();
                            System.out.print("Enter age : ");
                            int age = getPositiveInt();

                            String roomId = "F" + (checkInFloor + 1) + "-R" + (checkInRoom + 1);

                            condo[checkInFloor][checkInRoom] = roomId + "," + name + "," + gender + "," + age;
                            String historyEntry = "Check-in: " + roomId + ", Name: " + name + ", Gender: " + gender + ", Age: " + age;
                            String timestamp = LocalDateTime.now().format(formatter);
                            history.add(historyEntry + " at " + timestamp);
                            System.out.println(Colors.GREEN + "Check-in successful: " + roomId);
                        } else {
                            System.out.println(Colors.RED + "Room is already occupied!");
                        }
                    }
                    break;
                }
                case "d": {
                    System.out.println(Colors.CYAN_BRIGHT +"*** Check-out Room ***");
                    System.out.println("-".repeat(30));
                    if (floor == 0 || condo == null) {
                        System.out.println( Colors.RED + "Please setup Condo first!");
                    } else {
                        System.out.print("Enter floor (1-" + floor + ") : ");
                        int checkOutFloor = getPositiveInt() - 1;
                        if (checkOutFloor < 0 || checkOutFloor >= floor) {
                            System.out.println( Colors.RED + "Invalid floor number!");
                            break;
                        }

                        System.out.print("Enter room (1-" + room + ") : ");
                        int checkOutRoom = getPositiveInt() - 1;
                        if (checkOutRoom < 0 || checkOutRoom >= room) {
                            System.out.println( Colors.RED + "Invalid room number!");
                            break;
                        }

                        if (condo[checkOutFloor][checkOutRoom] != null) {

                            String[] details = condo[checkOutFloor][checkOutRoom].split(",");
                            String roomId = details[0];
                            String name = details[1];
                            String gender = details[2];
                            String age = details[3];

                            String historyEntry = "Check-out: " + roomId + ", Name: " + name + ", Gender: " + gender + ", Age: " + age;
                            String timestamp = LocalDateTime.now().format(formatter);
                            history.add(historyEntry + " at " + timestamp);

                            condo[checkOutFloor][checkOutRoom] = null;
                            System.out.println(Colors.GREEN + "Check-out successful: " + roomId);
                        } else {
                            System.out.println(Colors.RED + "Room is already vacant!");
                        }
                    }
                    break;
                }
                case "e": {
                    System.out.println(Colors.CYAN_BRIGHT +"*** View History Record ***");
                    System.out.println("-".repeat(30));
                    if (history.isEmpty()) {
                        System.out.println(Colors.RED + "No history records available.");
                    } else {
                        System.out.println("History of Check-in and Check-out:");
                        for (int i = 0; i < history.size(); i++) {
                            System.out.println((i + 1) + ". " + history.get(i));
                        }
                    }
                    break;
                }
                case "f": {
                    System.out.println("Exit Application ...");
                    scanner.close();
                    return;
                }
                default: {
                    System.out.println(Colors.RED + "Invalid option!");
                }
            }
        } while (true);
    }


    private static int getPositiveInt() {
        while (true) {
            try {
                int input = Integer.parseInt(scanner.nextLine());
                if (input > 0) {
                    return input;
                } else {
                    System.out.print("Please enter a positive number: ");
                }
            } catch (NumberFormatException e) {
                System.out.print( Colors.RED + "Invalid input. Please enter a number: ");
            }
        }
    }


    private static String getEmptyString() {
        while (true) {
            String input = scanner.nextLine().trim();
            if (!input.isEmpty()) {
                return input;
            } else {
                System.out.print("Name cannot be empty. Please enter a name: ");
            }
        }
    }


    private static String getValidGender() {
        while (true) {
            String input = scanner.nextLine().trim().toLowerCase();
            if (input.equals("male") || input.equals("female") || input.equals("other")) {
                return input.substring(0, 1).toUpperCase() + input.substring(1);
            } else {
                System.out.print(Colors.RED + "Invalid gender. Please enter Male, Female, or Other: ");
            }
        }
    }
}