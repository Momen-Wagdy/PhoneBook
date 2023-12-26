// PhoneBook.java
import java.io.FileWriter;
import java.io.IOException;

public class PhoneBook {
    private Node head; // Head of the linked list

    public PhoneBook() {
        this.head = null;
    }

    // Insert a new entry into the phone book
// Insert a new entry into the phone book
    public void insertEntry(String name, String phoneNumber) {
        // Check if the phone number contains only numeric characters
        if (!phoneNumber.matches("\\d+")) {
            System.out.println("Invalid phone number. Please enter a numeric phone number.");
            return;
        }

        Node newNode = new Node(name, phoneNumber);

        // Insert at the beginning for simplicity
        newNode.next = head;
        head = newNode;

        // Save the data to a text file
        saveToFile(name, phoneNumber);
    }


    // Search for a name in the phone book (case-insensitive)
    public Node searchName(String targetName) {
        Node current = head;
        while (current != null) {
            // Convert both the target name and the current name to lowercase for case-insensitive search
            String lowerTarget = targetName.toLowerCase();
            String lowerCurrent = current.name.toLowerCase();

            if (lowerTarget.equals(lowerCurrent)) {
                return current; // Name found
            }

            current = current.next;
        }

        return null; // Name not found
    }

    // Node class
    static class Node {
        String name;
        String phoneNumber;
        Node next;

        public Node(String name, String phoneNumber) {
            this.name = name;
            this.phoneNumber = phoneNumber;
            this.next = null;
        }
    }

    // Save data to a text file
    private void saveToFile(String name, String phoneNumber) {
        try (FileWriter outFile = new FileWriter("phonebook.txt", true)) {
            outFile.write(name + " " + phoneNumber + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
