#include <iostream>
#include <fstream>
#include <string>
#include <cctype> // for std::isdigit

// Node structure for the linked list
struct Node {
    std::string name;
    std::string phoneNumber;
    Node* next;

    Node(const std::string& n, const std::string& num) : name(n), phoneNumber(num), next(nullptr) {}
};

// PhoneBook class to manage the linked list and binary search
class PhoneBook {
private:
    Node* head; // Head of the linked list

public:
    PhoneBook() : head(nullptr) {}

    // Insert a new entry into the phone book
    void insertEntry(const std::string& name, const std::string& phoneNumber) {
        // Validate phone number input
        for (char c : phoneNumber) {
            if (!std::isdigit(c)) {
                std::cerr << "Error: Phone number should contain only digits.\n";
                return; // Exit function if phone number is invalid
            }
        }

        Node* newNode = new Node(name, phoneNumber);

        // Insert at the beginning for simplicity
        newNode->next = head;
        head = newNode;

        // Save the data to a text file
        saveToFile(name, phoneNumber);
    }

    // Search for a name in the phone book (case-insensitive)
    Node* searchName(const std::string& targetName) {
        Node* current = head;
        while (current != nullptr) {
            // Convert both the target name and the current name to lowercase for case-insensitive search
            std::string lowerTarget = targetName;
            std::string lowerCurrent = current->name;

            for (char& c : lowerTarget) {
                c = std::tolower(c);
            }

            for (char& c : lowerCurrent) {
                c = std::tolower(c);
            }

            if (lowerTarget == lowerCurrent) {
                return current; // Name found
            }

            current = current->next;
        }

        return nullptr; // Name not found
    }

    // Save data to a text file
    void saveToFile(const std::string& name, const std::string& phoneNumber) {
        std::ofstream outFile("phonebook.txt", std::ios::app); // Open the file in append mode
        if (outFile.is_open()) {
            outFile << name << " " << phoneNumber << "\n";
            outFile.close();
        } else {
            std::cerr << "Error opening the file for writing.\n";
        }
    }
};

int main() {
    PhoneBook phoneBook;

    while (true) {
        std::cout << "Choose an option:\n";
        std::cout << "1. Insert phone number and name\n";
        std::cout << "2. Search for a name\n";
        std::cout << "3. Exit\n";

        int choice;
        std::cin >> choice;

        if (choice == 1) {
            std::string name, phoneNumber;
            std::cout << "Enter name: ";
            std::cin >> name;
            std::cout << "Enter phone number: ";
            std::cin >> phoneNumber;

            phoneBook.insertEntry(name, phoneNumber);
        } else if (choice == 2) {
            std::string searchName;
            std::cout << "Enter name to search: ";
            std::cin >> searchName;

            Node* result = phoneBook.searchName(searchName);
            if (result != nullptr) {
                std::cout << "Phone number for " << result->name << ": " << result->phoneNumber << "\n";
            } else {
                std::cout << "Name not found in the phone book.\n";
            }
        } else if (choice == 3) {
            break; // Exit the program
        } else {
            std::cout << "Invalid choice. Please enter a valid option.\n";
        }
    }

    return 0;
}
