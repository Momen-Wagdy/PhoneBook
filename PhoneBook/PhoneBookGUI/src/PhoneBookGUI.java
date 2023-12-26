import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PhoneBookGUI extends JFrame {
    private PhoneBook phoneBook;

    private JTextField nameTextField;
    private JTextField phoneNumberTextField;
    private JTextArea resultTextArea;
    private JLabel errorMessageLabel; // New JLabel for error message

    public PhoneBookGUI() {
        phoneBook = new PhoneBook();

        // Set up the frame
        setTitle("PhoneBook GUI");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);

        // Center the frame on the screen
        setLocationRelativeTo(null);

        // Set rose theme color
        Color roseColor = new Color(255, 182, 193); // Adjust RGB values as needed
        Color windowBackgroundColor = new Color(255, 228, 225); // Adjust RGB values for window background

        // Set the background color of the content pane
        getContentPane().setBackground(windowBackgroundColor);

        // Create components with rose theme
        nameTextField = createStyledTextField();
        phoneNumberTextField = createStyledTextField();
        resultTextArea = new JTextArea();
        resultTextArea.setEditable(false);
        resultTextArea.setBackground(nameTextField.getBackground()); // Set the background color

        JButton insertButton = createStyledButton("Insert", roseColor);
        JButton searchButton = createStyledButton("Search", roseColor);

        // Set layout
        setLayout(new GridLayout(5, 2)); // Increased rows to accommodate the error message label

        // Add components to the frame
        add(createStyledLabel("Name:", Color.BLACK)); // Set label color to black
        add(nameTextField);
        add(createStyledLabel("Phone Number:", Color.BLACK)); // Set label color to black
        add(phoneNumberTextField);
        add(insertButton);
        add(searchButton);
        add(createStyledLabel("Result:", Color.BLACK)); // Set label color to black
        add(new JScrollPane(resultTextArea));

        // Create error message label
        errorMessageLabel = createStyledLabel("", Color.RED); // Set color to red for error messages
        errorMessageLabel.setHorizontalAlignment(JLabel.CENTER); // Center-align the error message

        // Add error message label to the frame
        add(errorMessageLabel);

        // Add action listeners to buttons
        insertButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                insertEntry();
            }
        });

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchName();
            }
        });

        // Set foreground color to black for text components
        nameTextField.setForeground(Color.BLACK);
        phoneNumberTextField.setForeground(Color.BLACK);
        resultTextArea.setForeground(Color.BLACK);

        // Make sure the frame is visible
        setVisible(true);
    }

    private JTextField createStyledTextField() {
        JTextField textField = new JTextField();

        // Set the background color for the text field
        textField.setBackground(new Color(255, 182, 193)); // Use the same color as the name field
        textField.setForeground(Color.BLACK);

        textField.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        // Set a custom font for the text field
        Font customFont = new Font("Dancing Script", Font.PLAIN, 16);
        textField.setFont(customFont);

        return textField;
    }

    private JButton createStyledButton(String text, Color bgColor) {
        JButton button = new JButton(text);

        // Set the background color for the button
        button.setBackground(bgColor);
        button.setForeground(Color.BLACK);

        // Set a custom font for the button
        Font customFont = new Font("Dancing Script", Font.BOLD, 16);
        button.setFont(customFont);

        button.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2)); // Adjust thickness as needed
        button.setFocusPainted(false);

        return button;
    }

    private JLabel createStyledLabel(String text, Color fgColor) {
        JLabel label = new JLabel(text);
        label.setForeground(fgColor); // Set foreground color to black

        // Set a custom font for the label
        Font customFont = new Font("Dancing Script", Font.BOLD, 16);
        label.setFont(customFont);

        return label;
    }

    private void insertEntry() {
        String name = nameTextField.getText();
        String phoneNumber = phoneNumberTextField.getText();

        if (isValidPhoneNumber(phoneNumber)) {
            phoneBook.insertEntry(name, phoneNumber);

            // Clear text fields and error message
            nameTextField.setText("");
            phoneNumberTextField.setText("");
            errorMessageLabel.setText("");
        } else {
            // Display error message
            errorMessageLabel.setText("Invalid phone number. Please enter a numeric phone number.");
        }
    }

    private boolean isValidPhoneNumber(String phoneNumber) {
        // You can add your validation logic here
        // For example, check if the phone number contains only numeric characters
        return phoneNumber.matches("\\d+");
    }

    private void searchName() {
        String searchName = nameTextField.getText();

        PhoneBook.Node result = phoneBook.searchName(searchName);
        if (result != null) {
            resultTextArea.setText("Phone number for " + result.name + ": " + result.phoneNumber);
        } else {
            resultTextArea.setText("Name not found in the phone book.");
        }
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new PhoneBookGUI();
            }
        });
    }
}
