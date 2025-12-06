import ErrorPack.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.Objects;

public class LFForm extends JFrame {

    //track current user
    String[] user;

    //swing things vv
    private JPanel mainPanel;
    private JPanel loginPage;
    private JButton selectRegisterButton;
    private JPanel holderPanel;
    private JButton selectLoginButton;
    private JPanel inputDetails;
    private JPanel idNumberPanel;
    private JPanel passwordPanel;
    private JLabel titleSelected;
    private JButton finalButton;
    private JTextField inputUsername;
    private JButton redirectToOther;
    private JPanel additionalRegistrationDetails2;
    private JPanel additionalRegistrationDetails1;
    private JTextField inputIDNumber;
    private JLabel studentIDLabel;
    private JTextField inputContactNumber;
    private JTextField inputCourseAndYear;
    private JLabel contactNumberLabel;
    private JLabel courseAndYearLabel;
    private JButton clearEntriesButton;
    private JPasswordField inputPassword;
    private JPanel lostItemsPage;
    private JPanel findItemPage;
    private JPanel profilePage;
    private JPanel messageTheOwnerPage;
    private JLabel logoIcon;
    private JPanel constantPanel;
    private JLabel constantBanner;
    private JButton profileButton;
    private JButton findItemButton;
    private JButton lostItemsButton;
    private JPanel mainTabsHolder;
    private JLabel greetingsLabel;
    private JButton logoutButton;

    public static void goToLogin(JPanel holderPanel, JPanel inputDetails, JLabel titleSelected, JButton finalButton, JButton redirectToOther, JPanel additionalRegistrationDetails1, JPanel additionalRegistrationDetails2) {
        holderPanel.setVisible(false);
        inputDetails.setVisible(true);
        titleSelected.setText("Login");
        finalButton.setText("Login");
        redirectToOther.setText("Register?");
        additionalRegistrationDetails1.setVisible(false);
        additionalRegistrationDetails2.setVisible(false);
    }
    public static void goToRegister(JPanel holderPanel, JPanel inputDetails, JLabel titleSelected, JButton finalButton, JButton redirectToOther, JPanel additionalRegistrationDetails1, JPanel additionalRegistrationDetails2){
        holderPanel.setVisible(false);
        inputDetails.setVisible(true);
        titleSelected.setText("Register");
        finalButton.setText("Register");
        redirectToOther.setText("Login?");
        additionalRegistrationDetails1.setVisible(true);
        additionalRegistrationDetails2.setVisible(true);
    }

    public static void goToLostItems(JPanel loginPage, JPanel lostItemsPage, JPanel findItemPage, JPanel profilePage, JPanel messageTheOwnerPage, JPanel constantPanel, JLabel greetingsLabel, String[] user) {
        loginPage.setVisible(false);
        lostItemsPage.setVisible(true);
        findItemPage.setVisible(false);
        profilePage.setVisible(false);
        messageTheOwnerPage.setVisible(false);
        if(!constantPanel.isVisible()){
            constantPanel.setVisible(true);
        }
        if(greetingsLabel.getText().equals("Change This")){
            greetingsLabel.setText("Welcome " + user[0] + "! (" +  user[2] + ")");
        }
    }

    public static void goToFindItem(JPanel lostItemsPage, JPanel findItemPage, JPanel profilePage, JPanel messageTheOwnerPage) {
        lostItemsPage.setVisible(false);
        findItemPage.setVisible(true);
        profilePage.setVisible(false);
        messageTheOwnerPage.setVisible(false);
    }

    //Find user's account through inputted username/studentID if it exists and return password to be used in validating
    //CSV format: username,password,IDNumber,contactNumber,courseAndYear
    //indices:      [0]      [1]       [2]        [3]          [4]
    public static String[] findAccount(String findThis, String findThroughWhat){
        String[] currentIDNumber;
        String curr;
        int findThrough = -1;

        try(BufferedReader br = new BufferedReader(new FileReader("records.csv"))){
            //determine through which method to find record
            switch (findThroughWhat) {
                case "Username":
                    findThrough = 0;
                    break;
                case  "Password":
                    findThrough = 1;
                    break;
                case  "ID Number":
                    findThrough = 2;
                    break;
                case "Contact Number":
                    findThrough = 3;
                    break;
                case "Course and Year":
                    findThrough = 4;
                default:
                    break;
            }

            while((curr = br.readLine()) != null){
                currentIDNumber = curr.split(",");
                if(currentIDNumber[findThrough].equals(findThis)){
                    return currentIDNumber;
                }
            }
        } catch(IOException e){
            //do nothing
        }
        return null;
    }

    //Record new registered account using this function
    //CSV format would be username,password,IDNumber,contactNumber,courseAndYear
    public static void recordAccount(String username, String password, String IDNumber, String contactNumber, String courseAndYear) throws EmptyField, InvalidIDFormat, InvalidNumberFormat, InvalidCourseAndYearFormat{
        try(BufferedWriter bw = new BufferedWriter(new FileWriter("records.csv",true))){
            if(username.isEmpty()){
                throw new EmptyField("Username");
            }
            if(password.isEmpty()){
                throw new  EmptyField("Password");
            }
            if(!IDNumber.matches("^\\d{2}-\\d{4}-\\d{3}$")){
                if(IDNumber.isEmpty()){
                    throw new EmptyField("ID Number");
                }
                throw new InvalidIDFormat();
            }
            if(!contactNumber.matches("^09\\d{9}$")){
                if(contactNumber.isEmpty()){
                    throw new EmptyField("Contact Number");
                }
                throw new InvalidNumberFormat();
            }
            if(!courseAndYear.matches("^[A-Za-z0-9]{3,5}-\\d$")){
                if(courseAndYear.isEmpty()){
                    throw new EmptyField("Course and Year");
                }
                throw new InvalidCourseAndYearFormat();
            }
            bw.write(username+","+password+","+IDNumber+","+contactNumber+","+courseAndYear);
            bw.newLine();
        } catch(IOException e){
            //do nothing
        }
    }

    public LFForm() {
        //hide other panels not in use
        inputDetails.setVisible(false);
        lostItemsPage.setVisible(false);
        findItemPage.setVisible(false);
        profilePage.setVisible(false);
        constantPanel.setVisible(false);

        //add functionality to buttons
        //Selecting Login Button Pressed
        selectLoginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                goToLogin(holderPanel, inputDetails, titleSelected, finalButton, redirectToOther, additionalRegistrationDetails1, additionalRegistrationDetails2);
            }
        });

        //Selecting Register Button Pressed
        selectRegisterButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                goToRegister(holderPanel, inputDetails, titleSelected, finalButton, redirectToOther,  additionalRegistrationDetails1, additionalRegistrationDetails2);
            }
        });

        redirectToOther.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(titleSelected.getText().equals("Login")){
                    goToRegister(holderPanel, inputDetails, titleSelected, finalButton, redirectToOther, additionalRegistrationDetails1, additionalRegistrationDetails2);
                } else if(titleSelected.getText().equals("Register")){
                    clearEntriesButton.doClick();
                    goToLogin(holderPanel, inputDetails, titleSelected, finalButton, redirectToOther, additionalRegistrationDetails1, additionalRegistrationDetails2);
                }
            }
        });

        //Clearing Entries
        clearEntriesButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                inputUsername.setText("");
                inputContactNumber.setText("");
                inputIDNumber.setText("");
                inputPassword.setText("");
                inputContactNumber.setText("");
                inputCourseAndYear.setText("");
            }
        });

        //LOGGING IN OR REGISTERING
        finalButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    String[] onRecord = user = findAccount(inputUsername.getText(),"Username");
                    String enteredPassword = new String(inputPassword.getPassword());
                    //dependent on what the finalButton is displaying
                    switch (finalButton.getText()) {
                        //LOGGING IN WITH USERNAME AND PASSWORD
                        case "Login":
                            if (onRecord!=null) {
                                if (Objects.equals(enteredPassword, onRecord[1])) {
                                    goToLostItems(loginPage, lostItemsPage, findItemPage, profilePage, messageTheOwnerPage, constantPanel, greetingsLabel, user);
                                } else {
                                    throw new IncorrectPassword();
                                }
                            } else {
                                throw new UsernameNotFound();
                            }
                            break;
                        //REGISTERING NEW ACCOUNT
                        case "Register":
                            if(onRecord==null) {
                                onRecord = findAccount(inputIDNumber.getText(),"ID Number");
                                if(onRecord!=null) {
                                    throw new AlreadyExists("ID Number", onRecord[2]);
                                }
                            } else {
                                throw new AlreadyExists("Username",onRecord[0]);
                            }
                            recordAccount(inputUsername.getText(), enteredPassword, inputIDNumber.getText(), inputContactNumber.getText(), inputCourseAndYear.getText());
                            JOptionPane.showMessageDialog(null, "Registration Successful");
                            clearEntriesButton.doClick();
                            break;
                        default:
                            JOptionPane.showMessageDialog(null, "Error. Try again.");
                    }
                } catch(AlreadyExists | EmptyField | UsernameNotFound | IncorrectPassword | InvalidIDFormat | InvalidNumberFormat | InvalidCourseAndYearFormat f){
                    JOptionPane.showMessageDialog(null, f.getMessage());
                }
            }
        });

        lostItemsButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                goToLostItems(loginPage, lostItemsPage, findItemPage, profilePage, messageTheOwnerPage, constantPanel, greetingsLabel, user);
            }
        });

        //setup Panel
        setContentPane(mainPanel);
        setTitle("Lost and Found Management System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(520, 880);
        setLocationRelativeTo(null);
        setVisible(true);
        logoIcon.setIcon(new ImageIcon("assets/logocit-1-300x212.png"));
        redirectToOther.setOpaque(false);
        redirectToOther.setContentAreaFilled(false);
        redirectToOther.setBorderPainted(false);
        logoutButton.setOpaque(false);
        logoutButton.setContentAreaFilled(false);
        logoutButton.setBorderPainted(false);
    }

}
