import ErrorPack.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.Objects;

public class LFForm extends JFrame {

    //LFSystem
    LFSystem system = new LFSystem();

    //Changes
    //track current user
    String[] user; //Note that we should also use the USER LIST and stuff from the system object

    // ====== NEW MODIFICATION 1: Added User object reference ======
    // This stores the current logged-in user as a User object, not just String array
    // This is needed because LFReport requires a User object to add items to their lists
    User currentUserObject;
    // ============================================================

    //SWING THINGS
    //MAIN
    private JPanel mainPanel;

    //1 LOGIN AND REGISTER PAGE
    private JPanel loginPage;
    private JLabel logoIcon;
    //1.1 INPUT DETAILS PANEL
    private JPanel inputDetails;
    private JLabel titleSelected;
    private JButton finalButton;
    private JButton redirectToOther;
    private JButton clearEntriesButton;
    //1.1.1 ID NUMBER PANEL
    private JPanel idNumberPanel;
    private JTextField inputUsername;
    //1.1.2 PASSWORD PANEL
    private JPanel passwordPanel;
    private JPasswordField inputPassword;
    //1.1.3 ADDITIONAL REGISTRATION DETAILS 1
    private JPanel additionalRegistrationDetails1;
    private JLabel contactNumberLabel;
    private JTextField inputContactNumber;
    private JLabel courseAndYearLabel;
    private JTextField inputCourseAndYear;
    //1.1.4 ADDITIONAL REGISTRATION DETAILS 2
    private JPanel additionalRegistrationDetails2;
    private JLabel studentIDLabel;
    private JTextField inputIDNumber;


    //2 LOST ITEMS PAGE
    private JPanel lostItemsPage;
    //2.1 PANEL
    //2.1.1 SEPARATOR HOLDER PANEL
    private JPanel separatorHolder;
    //2.2 LOST ITEMS HOLDER
    private JPanel lostItemsHolder;
    //2.2.1 SCROLLED ITEMS HOLDER [SCROLL PANE]
    private JScrollPane scrolledItemsHolder;
    private JPanel itemsHolder;


    //3 REPORT AN ITEM PAGE
    private JPanel reportAnItemPage;
    //3.1 PANEL
    //3.1.1 PANEL
    //3.1.2 PANEL
    private JButton iLostButton;
    private JButton iFoundAnItemButton;


    //4 PROFILE PAGE
    private JPanel profilePage;
    //4.1 PANEL
    private JLabel profileLabel;
    //4.1.1 PANEL


    //5 MESSAGE THE OWNER PAGE
    private JPanel messageTheOwnerPage;
    //5.1 PANEL
    //5.1.1 PANEL


    //6 CONSTANT PANEL [SERVES AS THE NAV BAR FOR THE FORM]
    private JPanel constantPanel;
    private JLabel constantBanner;
    //6.1 PANEL
    private JButton logoutButton;
    private JLabel greetingsLabel;
    //6.1.1 MAIN TABS HOLDER
    private JPanel mainTabsHolder;
    private JButton profileButton;
    private JButton reportAnItemButton;
    private JButton lostItemsButton;


    //7 ITEM DETAILS PAGE
    private JPanel itemDetailsPage;
    //7.1 PANEL


    //Changes
    //Left over fields (I wrote this in because they are in the form, and it does not run without these)
    //Because the reportAnItemButton now redirects to the reportPage form

    private JLabel lostItemName;
    private JLabel lostItemNameLabel;
    private JTextField inputLastSeenAt;
    private JLabel lastSeenAtLabel;
    private JLabel lastSeenOnLabel;
    private JLabel descriptionLabel;
    private JTextField inputLastSeenOn;
    private JPanel mainFieldsHolder;
    private JTextField inputLostItemName;
    private JTextField inputDescription;
    private JComboBox itemCategoryBox;


    //where is this located?
    private JPanel foundItemsPage;

    //ORIGINALLY CALLED start(). All the goTo methods call this function to hide all the panels.
    //You will need to manually set the panel you are on to true though.
    public void hideAll() {
        loginPage.setVisible(false);
        inputDetails.setVisible(false);
        lostItemsPage.setVisible(false);
        reportAnItemPage.setVisible(false);
        profilePage.setVisible(false);
        constantPanel.setVisible(false);
        messageTheOwnerPage.setVisible(false);
        itemDetailsPage.setVisible(false);

        additionalRegistrationDetails1.setVisible(false);
        additionalRegistrationDetails2.setVisible(false);
    }

    public void goToLogin() {
        hideAll();
        loginPage.setVisible(true);
        inputDetails.setVisible(true);

        titleSelected.setText("Login");
        finalButton.setText("Login");
        redirectToOther.setText("Register?");
    }

    public void goToRegister(){
        hideAll();
        inputDetails.setVisible(true);
        additionalRegistrationDetails1.setVisible(true);
        additionalRegistrationDetails2.setVisible(true);

        titleSelected.setText("Register");
        finalButton.setText("Register");
        redirectToOther.setText("Login?");
    }

    public void goToLostItems(String[] user, JButton lostItemsButton, JButton reportAnItemButtonButton, JButton profileButton, JPanel itemsHolder) {
        hideAll();
        lostItemsPage.setVisible(true);

        if(!constantPanel.isVisible()){
            constantPanel.setVisible(true);
        }
        if(greetingsLabel.getText().equals("Change This")){
            greetingsLabel.setText("Welcome " + user[0] + "! (" +  user[2] + ")");
        }
        markSelected(lostItemsButton);
        markUnselected(reportAnItemButtonButton);
        markUnselected(profileButton);

        //Changes
        // ====== NEW MODIFICATION 2: Clear existing items before loading ======
        // This prevents duplicate items from showing up when refreshing the page
        // Important for when items are added via LFReport and we need to reload
        itemsHolder.removeAll();
        // =====================================================================

        try (BufferedReader br = new BufferedReader(new FileReader("Items.csv"))) {
            String line;

            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");

                // Create an item panel for each record
                JPanel itemPanel = createItemPanel(data);

                // Add to grid
                itemsHolder.add(itemPanel);
            }

        } catch (IOException e) {
            //do nothing
        }
        //Changes
        // ====== NEW MODIFICATION 3: Refresh the display ======
        // These calls ensure the UI updates immediately after adding new items
        itemsHolder.revalidate();
        itemsHolder.repaint();
        // =====================================================
    }

    //create individual items
    private static JPanel createItemPanel(String[] data) {
        JPanel panel = new JPanel();
        panel.setPreferredSize(new Dimension(200, 120));//change size if boxes too small/big
        panel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        // Example fields (adjust once you finalize CSV format)
        JLabel name = new JLabel("Item: " + data[0]);
        JLabel location = new JLabel("Location: " + (data.length > 1 ? data[1] : "N/A")); //change accordingly to final csv format

        panel.add(name);
        panel.add(location);

        return panel;
    }

    //This is unused as reportAnItemPage now directs to LFReport now instead of ReportAnItem page

    public void goToReportAnItem(String[] user, JButton lostItemsButton, JButton reportAnItemButtonButton, JButton profileButton) {
        hideAll();
        reportAnItemPage.setVisible(true);

        if(!constantPanel.isVisible()){
            constantPanel.setVisible(true);
        }
        if(greetingsLabel.getText().equals("Change This")){
            greetingsLabel.setText("Welcome " + user[0] + "! (" +  user[2] + ")");
        }
        markUnselected(lostItemsButton);
        markSelected(reportAnItemButtonButton);
        markUnselected(profileButton);
    }

    public void goToProfile(String[] user, JButton lostItemsButton, JButton reportAnItemButtonButton, JButton profileButton) {
        hideAll();
        profilePage.setVisible(true);

        if(!constantPanel.isVisible()){
            constantPanel.setVisible(true);
        }
        if(greetingsLabel.getText().equals("Change This")){
            greetingsLabel.setText("Welcome " + user[0] + "! (" +  user[2] + ")");
        }
        markUnselected(lostItemsButton);
        markUnselected(reportAnItemButtonButton);
        markSelected(profileButton);
    }

    //Find user's account through inputted username/studentID if it exists and return password to be used in validating
    //CSV format: username,password,IDNumber,contactNumber,courseAndYear
    //indices:      [0]      [1]       [2]        [3]          [4]
    public static String[] findAccount(String findThis, String findThroughWhat){
        String[] currentIDNumber;
        String curr;
        int findThrough = switch (findThroughWhat) {
            case "Username" -> 0;
            case "Password" -> 1;
            case "ID Number" -> 2;
            case "Contact Number" -> 3;
            case "Course and Year" -> 4;
            default -> -1;
        };

        if (findThrough == -1) return null;

        try (BufferedReader br = new BufferedReader(new FileReader("records.csv"))) {
            while ((curr = br.readLine()) != null) {
                currentIDNumber = curr.split(",");
                if (currentIDNumber.length > findThrough && currentIDNumber[findThrough].equals(findThis)) {
                    return currentIDNumber;
                }
            }
        } catch (IOException ignored) {}
        return null;
    }

    //Record new registered account using this function
    //CSV format would be username,password,IDNumber,contactNumber,courseAndYear
    public static void recordAccount(String username, String password, String IDNumber, String contactNumber, String courseAndYear) throws EmptyField, InvalidIDFormat, InvalidNumberFormat, InvalidCourseAndYearFormat {
        if (username.isEmpty()) throw new EmptyField("Username");
        if (password.isEmpty()) throw new EmptyField("Password");
        if (IDNumber.isEmpty()) throw new EmptyField("ID Number");
        if (contactNumber.isEmpty()) throw new EmptyField("Contact Number");
        if (courseAndYear.isEmpty()) throw new EmptyField("Course and Year");

        if (!IDNumber.matches("^\\d{2}-\\d{4}-\\d{3}$")) throw new InvalidIDFormat();
        if (!contactNumber.matches("^09\\d{9}$")) throw new InvalidNumberFormat();
        if (!courseAndYear.matches("^[A-Za-z0-9]{3,5}-\\d$")) throw new InvalidCourseAndYearFormat();

        try (BufferedWriter bw = new BufferedWriter(new FileWriter("records.csv", true))) {
            bw.write(username + "," + password + "," + IDNumber + "," + contactNumber + "," + courseAndYear);
            bw.newLine();
        } catch (IOException ignored) {}
    }

    //Changes
    // ====== NEW MODIFICATION 4: Create User object from CSV data ======
    // This method converts the String[] from CSV into a proper User object
    // Required because LFReport needs a User object to work with
    // CSV format: username,password,IDNumber,contactNumber,courseAndYear (e.g., "CS-3")
    private User createUserObject(String[] userData) {
        String username = userData[0];
        String idNumber = userData[2];
        String password = userData[1];
        String contactNo = userData[3];
        String courseAndYear = userData[4];

        // Parse the courseAndYear field (format: "CS-3" or "BSIT-4")
        String[] courseYear = courseAndYear.split("-");
        String course = courseYear[0];
        int year = Integer.parseInt(courseYear[1]);

        // Return a new User object with all the parsed data
        return new User(username, idNumber, password, contactNo, course, year);
    }
    // ==================================================================

    //tabs color changer
    public static void markSelected(JButton a){
        a.setBackground(Color.decode("#FFD700"));
        a.setForeground(Color.decode("#800000"));
        a.setOpaque(true);
    }

    public static void markUnselected(JButton a){
        a.setBackground(Color.decode("#800000"));
        a.setForeground(Color.white);
        a.setOpaque(true);
    }

    public LFForm() {
        //add functionality to buttons
        redirectToOther.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(titleSelected.getText().equals("Login")){
                    goToRegister();
                } else if(titleSelected.getText().equals("Register")){
                    clearEntriesButton.doClick();
                    goToLogin();
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
                                    // ====== NEW MODIFICATION 5: Create User object on login ======
                                    // When user logs in successfully, create a User object
                                    // This object is needed for LFReport to add items to user's lists
                                    currentUserObject = createUserObject(onRecord);
                                    // =============================================================

                                    goToLostItems(user, lostItemsButton, reportAnItemButton, profileButton, itemsHolder);
                                    clearEntriesButton.doClick();
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
                goToLostItems(user, lostItemsButton, reportAnItemButton, profileButton, itemsHolder);
            }
        });

        //Changes
        // ====== NEW MODIFICATION 6: Updated report button to open LFReport window ======
        // Instead of just changing the view, this now opens the LFReport form in a new window
        // Passes currentUserObject (User), system (LFSystem), and reference to this form
        // The reference to this form allows LFReport to refresh the items list after adding items
        reportAnItemButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (currentUserObject != null) {
                    // Open the LFReport window with current user and system
                    new LFReport(currentUserObject, system, LFForm.this);
                } else {
                    JOptionPane.showMessageDialog(null, "Error: User not logged in");
                }
            }
        });
        // ===============================================================================
        //Changes
        // ====== NEW MODIFICATION 7: Added listeners for report page buttons ======
        // If you have "I Lost" and "I Found" buttons on the reportAnItemPage,
        // these will also open the LFReport window
        // This provides multiple ways to access the report functionality
        if (iLostButton != null) {
            iLostButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    if (currentUserObject != null) {
                        new LFReport(currentUserObject, system, LFForm.this);
                    }
                }
            });
        }

        if (iFoundAnItemButton != null) {
            iFoundAnItemButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    if (currentUserObject != null) {
                        new LFReport(currentUserObject, system, LFForm.this);
                    }
                }
            });
        }
        // =========================================================================

        profileButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                goToProfile(user, lostItemsButton, reportAnItemButton, profileButton);
            }
        });

        logoutButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                user = null;

                //Changes
                // ====== NEW MODIFICATION 8: Clear User object on logout ======
                // Important: set currentUserObject to null when logging out
                // This prevents unauthorized access to previous user's data
                currentUserObject = null;
                // =============================================================

                goToLogin();
                loginPage.setVisible(true);
            }
        });

        //setup Panel
        setContentPane(mainPanel);
        //Instantly sets the form as the log-in page
        goToLogin();
        setTitle("Lost and Found Management System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(520, 780);
        setLocationRelativeTo(null);
        setVisible(true);
        logoIcon.setIcon(new ImageIcon("assets/cit_logo.png")); //300x212 dimensions
        redirectToOther.setOpaque(false);
        redirectToOther.setContentAreaFilled(false);
        redirectToOther.setBorderPainted(false);
        logoutButton.setOpaque(false);
        logoutButton.setContentAreaFilled(false);
        logoutButton.setBorderPainted(false);
        itemsHolder.setLayout(new GridLayout(0, 2, 10, 10));

        //Set up properties of certain fields beforehand
        scrolledItemsHolder.getVerticalScrollBar().setUnitIncrement(15); //n pixels per scroll [I'd suggest somewhere between 10-20 inclusive] works good in small - medium lists
    }
    //Changes
    // ====== NEW MODIFICATION 9: Method to refresh items list ======
    // This method is called from LFReport after successfully adding an item
    // It reloads the lost items page to show the newly added item
    // This creates a seamless experience where changes are immediately visible
    public void refreshItemsList() {
        goToLostItems(user, lostItemsButton, reportAnItemButton, profileButton, itemsHolder);
    }
    // ==============================================================
}