/*

This project is a Simple Data Entry Application, created using Swing
This application allows the user to input personal information about a person
It will ask for first name, last name, phone number, and id number
The information inputted is not uploaded to a database, therefore all entries will be lost once the window is closed

 ## Usage Instructions
1. Launch the application.
2. Input the required personal information.
3. Click the "Add" button to submit the entry.
4. To remove an entry, click on it in the displayed list.

## Possible Future Features

- Implement permanent data storage in database for entries.
- Include additional data fields such as email address and date of birth.

 */

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class simpleDataEntryApp implements ActionListener {
    private static JTextField enterFirst;
    private static JTextField enterLast;
    private static JTextField enterNumber;
    private static JTextField enterID;

    private static JPanel displayPanel;
    private static int numberOfPeople = 0;
    private static HashSet<String> idSet = new HashSet<>();
    private static JLabel invalidEntry;

    public static void main(String[] args) {
        // Create GUI window
        JFrame window = new JFrame("Simple Data Entry and Display App");
        window.setSize(1500, 900);

        // Create panel for user entry
        JPanel panelOne = new JPanel();
        panelOne.setLayout(null);

            // User entry part 1 - obtain first name
        JLabel fNameField = new JLabel("First Name");
        fNameField.setBounds(10, 10, 150, 30);
        enterFirst = new JTextField();
        enterFirst.setBounds(160, 10, 100, 30);

        panelOne.add(fNameField);
        panelOne.add(enterFirst);

            // User entry part 2 - obtain last name
        JLabel lNameField = new JLabel("Last Name");
        lNameField.setBounds(10, 50, 150, 30);
        enterLast = new JTextField();
        enterLast.setBounds(160, 50, 100, 30);

        panelOne.add(lNameField);
        panelOne.add(enterLast);

            // User entry part 3 - obtain phone number
        JLabel numberField = new JLabel("Phone Number");
        numberField.setBounds(10, 100, 150, 30);
        enterNumber = new JTextField();
        enterNumber.setBounds(160, 100, 100, 30);

        panelOne.add(numberField);
        panelOne.add(enterNumber);

            // User entry part 4 - obtain id number
        JLabel idField = new JLabel("ID Number");
        idField.setBounds(10, 150, 150, 30);
        enterID = new JTextField();
        enterID.setBounds(160, 150, 100, 30);

        panelOne.add(idField);
        panelOne.add(enterID);

            // User entry part 5 - create button that inputs new entries
            // All entries will be displayed in the display panel
            // The button has an event listener waiting to trigger
        JButton add = new JButton("Add");
        add.setBounds(10, 200, 200, 25);
        add.addActionListener(new simpleDataEntryApp());

            // User entry part 6 - if a user fails to fill in all fields
            // Or the user inputs an existing ID number
            // They will be alerted here
        invalidEntry = new JLabel("");
        invalidEntry.setBounds(10, 300, 450, 25);
        panelOne.add(invalidEntry);

        // The inputted data will be displayed here
        displayPanel = new JPanel();
        displayPanel.setLayout(new BoxLayout(displayPanel, BoxLayout.Y_AXIS));
        displayPanel.setBounds(500, 50, 500, 200);

        // The data also can be removed when clicked on
        displayPanel.addMouseListener(new EntryClickListener());
        JLabel clickToRemove = new JLabel("Click on an entry below to remove it.");
        clickToRemove.setBounds(500,10,250,20);

        // Add all elements to the GUI window
        window.add(clickToRemove);
        window.add(add);
        window.add(displayPanel);
        window.add(panelOne);

        // Allow user to see the GUI window
        window.setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        String idNumber = enterID.getText();

        // If user inputs an existing ID number (duplicate person)
        // They will be alerted
        if (idSet.contains(idNumber)) {
            invalidEntry.setText("This ID Number already exists. Please try again.");
        } else {
            // Once a valid entry is inputted, reset the invalidEntry label
            invalidEntry.setText("");

            String firstName = enterFirst.getText();
            String lastName = enterLast.getText();
            String phoneNumber = enterNumber.getText();

            // Valid Entry
            if (!firstName.isEmpty() && !lastName.isEmpty() && !phoneNumber.isEmpty() && !idNumber.isEmpty()) {
                // Add Id number to set to prevent duplicates
                idSet.add(idNumber);

                // Reset input fields
                enterFirst.setText("");
                enterLast.setText("");
                enterNumber.setText("");
                enterID.setText("");

                // Keeps track of the number of entries
                numberOfPeople++;

                // Valid entry displayed --> fName lName | phoneNumber | idNumber
                JLabel entryLabel = new JLabel(firstName + " " + lastName + " | " + phoneNumber + " | " + idNumber);
                entryLabel.addMouseListener(new EntryClickListener());
                displayPanel.add(entryLabel);

                // Refresh the display
                displayPanel.revalidate();
                displayPanel.repaint();

            // If not all fields are filled, the user is alerted
            } else {
                invalidEntry.setText("Please fill in all fields before clicking Add.");
            }
        }
    }


    private static class EntryClickListener extends MouseAdapter {
        public void mouseClicked(MouseEvent e) {
            // Obtain information to remove
            Component clickedComponent = e.getComponent();

            // Remove data from set to prevent invalid duplicate warnings
            idSet.remove(clickedComponent);

            // Remove information from display panel
            displayPanel.remove(clickedComponent);

            // Adjust the position of remaining entries once an entry is removed
            Component[] components = displayPanel.getComponents();
            for (int i = 0; i < components.length; i++) {
                components[i].setBounds(500, 50 + i * 25, 250, 200);
            }

            // Refresh the display
            displayPanel.revalidate();
            displayPanel.repaint();
        }
    }
}
