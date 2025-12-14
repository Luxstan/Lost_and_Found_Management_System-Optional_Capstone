import ErrorPack.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;
import java.util.Objects;
//for images
import java.awt.datatransfer.DataFlavor;
import java.awt.dnd.*;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.List;

public class LFForm extends JFrame {

    //LFSystem
    LFSystem system = new LFSystem();

    //track current user
    String[] user; //Note that we should also use the USER LIST and stuff from the system object

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


    //4 PROFILE PAGE
    private JPanel profilePage;
    //4.1 PANEL
    private JLabel profileLabel;
    //4.1.1 PANEL


    //5 MESSAGE THE OWNER PAGE
    private JPanel itemDetailsPanel;
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
    private JTextField inputLostItemName;
    //7.1 PANEL

    private JTextField inputLastSeenAt;
    private JTextField inputLastSeenOn;
    private JTextField inputDescription;
    private JPanel mainFieldsHolder;
    private JComboBox itemCategoryBox;
    private JPanel additionalDetailsPanel;
    private JScrollPane scrollableDetails;
    private JRadioButton inWalletRadio;
    private JRadioButton notInWalletRadio;
    private JButton foundButton;
    private JButton lostButton;
    private JPanel imageDropZone;
    private JLabel imagePreviewLabel;
    private JScrollPane detailsHolder;
    private JButton backButton;
    private JButton foundOrLostButton;
    private String uploadedImageFileName = ""; // stores just the filename

    private JButton submitReportButton;

    // Dynamic fields
    private JTextField accessoryColor;
    private JTextField accessoryMaterial;
    private JTextField accessoryType;

    private JTextField bagColor;
    private JTextField bagBrand;
    private JTextField bagType;

    private JTextField clothingColor;
    private JTextField clothingSize;
    private JTextField clothingBrand;
    private JTextField clothingMaterial;
    private JTextField clothingType;

    private JTextField documentOwnerName;
    private JTextField documentOwnerID;
    private JTextField documentType;

    private JTextField electronicModel;
    private JTextField electronicBrand;
    private JTextField electronicType;

    private JTextField foodContainerColor;
    private JTextField foodContainerCapacity;
    private JTextField foodContainerBrand;
    private JTextField foodContainerType;

    private JTextField moneyAmount;

    private JTextField tumblerColor;
    private JTextField tumblerCapacity;

    private JTextField othersItemType;
    private JTextField othersColor;
    private String uploadedImageFilePath = ""; // Add this near line 150


    //ORIGINALLY CALLED start(). All the goTo methods call this function to hide all the panels.
    //You will need to manually set the panel you are on to true though.
    public void hideAll() {
        loginPage.setVisible(false);
        inputDetails.setVisible(false);
        lostItemsPage.setVisible(false);
        reportAnItemPage.setVisible(false);
        profilePage.setVisible(false);
        constantPanel.setVisible(false);
        itemDetailsPanel.setVisible(false);

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
        loginPage.setVisible(true);
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

        itemsHolder.removeAll();//Fixed Bug regarding stacking of items

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

    }

    public void goToItemDetails(String[] data, JButton lostItemsButton, JButton reportAnItemButton, JButton profileButton, JScrollPane detailsHolder) {
        hideAll();
        itemDetailsPanel.setVisible(true);

        if(!constantPanel.isVisible()){
            constantPanel.setVisible(true);
        }

        //keep the same tab selected
        markSelected(lostItemsButton);
        markUnselected(reportAnItemButton);
        markUnselected(profileButton);

        configureFoundOrLostButton(data);

        // Populate the details
        populateItemDetails(data, detailsHolder);
    }

    private void configureFoundOrLostButton(String[] data) {
        // Remove all existing action listeners to prevent duplicates
        for (ActionListener al : foundOrLostButton.getActionListeners()) {
            foundOrLostButton.removeActionListener(al);
        }

        // Set button text and style based on status
        if (data[6].equals("Lost")) {
            foundOrLostButton.setText("I found this");
            foundOrLostButton.setBackground(Color.decode("#006400")); // Dark green
            foundOrLostButton.setForeground(Color.WHITE);
        } else { // Found
            foundOrLostButton.setText("This is mine");
            foundOrLostButton.setBackground(Color.decode("#FFD700")); // Gold
            foundOrLostButton.setForeground(Color.decode("#800000")); // Maroon
        }

        foundOrLostButton.setOpaque(true);
        foundOrLostButton.setBorderPainted(false);
        foundOrLostButton.setFocusPainted(false);

        // Add new action listener with current data
        foundOrLostButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(
                        null,
                        data[1] + " is now notified\nWait for a reply",
                        "Notification Sent",
                        JOptionPane.INFORMATION_MESSAGE
                );
            }
        });
    }

    private void populateItemDetails(String[] data, JScrollPane detailsHolder) {
        // Create a main panel to hold all details
        JPanel mainDetailsPanel = new JPanel();
        mainDetailsPanel.setLayout(new BoxLayout(mainDetailsPanel, BoxLayout.Y_AXIS));
        mainDetailsPanel.setBackground(Color.WHITE);
        mainDetailsPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // ROW 1: PICTURE - CENTER, FILL WIDTH, ADAPT HEIGHT with border
        if (data.length > 8 && !data[8].equals("N/A")) {
            try {
                File imageFile = new File("assets/reportedItems/" + data[8]);
                if (imageFile.exists()) {
                    ImageIcon imageIcon = new ImageIcon(imageFile.getAbsolutePath());

                    // Get the actual viewport width to prevent horizontal scrolling
                    int viewportWidth = detailsHolder.getViewport().getWidth();
                    // Account for padding (20 left + 20 right) and border (2 per side)
                    int maxWidth = viewportWidth - 44;

                    // Ensure minimum reasonable width
                    if (maxWidth < 100) maxWidth = 400;

                    int originalWidth = imageIcon.getIconWidth();
                    int originalHeight = imageIcon.getIconHeight();

                    // Scale to fit width while preserving aspect ratio
                    double scale = (double) maxWidth / originalWidth;
                    int newWidth = maxWidth;
                    int newHeight = (int) (originalHeight * scale);

                    Image scaledImage = imageIcon.getImage().getScaledInstance(
                            newWidth, newHeight, Image.SCALE_SMOOTH);

                    JLabel imageLabel = new JLabel(new ImageIcon(scaledImage));
                    imageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
                    imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
                    imageLabel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));

                    // Wrap in a panel to ensure centering
                    JPanel imagePanel = new JPanel();
                    imagePanel.setLayout(new BoxLayout(imagePanel, BoxLayout.X_AXIS));
                    imagePanel.setBackground(Color.WHITE);
                    imagePanel.setAlignmentX(Component.LEFT_ALIGNMENT);
                    imagePanel.add(Box.createHorizontalGlue());
                    imagePanel.add(imageLabel);
                    imagePanel.add(Box.createHorizontalGlue());

                    mainDetailsPanel.add(imagePanel);
                    mainDetailsPanel.add(Box.createRigidArea(new Dimension(0, 20)));
                }
            } catch (Exception e) {
                // No image or error loading
            }
        }

        // Status message with reportedBy
        String statusMessage;
        Color statusColor;
        if (data[6].equals("Found")) {
            statusMessage = "This item is missing its owner";
            statusColor = Color.decode("#000000");
        } else {
            statusMessage = data[1] + " lost this item";
            statusColor = Color.RED;
        }

        JLabel statusLabel = new JLabel(statusMessage);
        statusLabel.setFont(new Font("Arial", Font.ITALIC, 15));
        statusLabel.setForeground(statusColor);
        statusLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        mainDetailsPanel.add(statusLabel);
        mainDetailsPanel.add(Box.createRigidArea(new Dimension(0, 15)));

        // ROW 3: Item Name
        JLabel nameLabel = new JLabel("Item Name: " + data[2]);
        nameLabel.setFont(new Font("Arial", Font.BOLD, 20));
        nameLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        mainDetailsPanel.add(nameLabel);
        mainDetailsPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        // ROW 4: Description (with word wrap to prevent horizontal scroll)
        JTextArea descriptionArea = new JTextArea("Description: " + data[5]);
        descriptionArea.setFont(new Font("Arial", Font.PLAIN, 14));
        descriptionArea.setLineWrap(true);
        descriptionArea.setWrapStyleWord(true);
        descriptionArea.setEditable(false);
        descriptionArea.setBackground(Color.WHITE);
        descriptionArea.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Calculate max width to prevent horizontal scrolling
        int viewportWidth = detailsHolder.getViewport().getWidth();
        int maxTextWidth = viewportWidth - 44; // Account for padding
        if (maxTextWidth < 100) maxTextWidth = 400;
        descriptionArea.setMaximumSize(new Dimension(maxTextWidth, Integer.MAX_VALUE));

        mainDetailsPanel.add(descriptionArea);
        mainDetailsPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        // ROW 5: Last Seen Location and Time
        JLabel locationLabel = new JLabel("Item last seen: " + data[3] + " on " + data[4]);
        locationLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        locationLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        mainDetailsPanel.add(locationLabel);
        mainDetailsPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        // Category
        JLabel categoryLabel = new JLabel("Category: " + data[7]);
        categoryLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        categoryLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        mainDetailsPanel.add(categoryLabel);
        mainDetailsPanel.add(Box.createRigidArea(new Dimension(0, 15)));

        // ROW N: Additional Details (if not N/A)
        if (data.length > 9) {
            JLabel additionalHeader = new JLabel("Additional Details:");
            additionalHeader.setFont(new Font("Arial", Font.BOLD, 16));
            additionalHeader.setAlignmentX(Component.LEFT_ALIGNMENT);
            mainDetailsPanel.add(additionalHeader);
            mainDetailsPanel.add(Box.createRigidArea(new Dimension(0, 10)));

            addCategorySpecificDetails(data, mainDetailsPanel);
        }

        // Add glue at the bottom to push everything to the top
        mainDetailsPanel.add(Box.createVerticalGlue());

        // Set the panel as the viewport view
        detailsHolder.setViewportView(mainDetailsPanel);
        detailsHolder.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        detailsHolder.revalidate();
        detailsHolder.repaint();
    }

    private void addCategorySpecificDetails(String[] data, JPanel panel) {
        String category = data[7];
        int startIndex = 9; // Where additional fields begin

        switch (category) {
            case "Accessory":
                addDetailIfNotNA(panel, "Color", data, startIndex);
                addDetailIfNotNA(panel, "Material", data, startIndex + 1);
                addDetailIfNotNA(panel, "Type", data, startIndex + 2);
                break;

            case "Bag":
                addDetailIfNotNA(panel, "Color", data, startIndex);
                addDetailIfNotNA(panel, "Brand", data, startIndex + 1);
                addDetailIfNotNA(panel, "Type", data, startIndex + 2);
                break;

            case "Clothing":
                addDetailIfNotNA(panel, "Color", data, startIndex);
                addDetailIfNotNA(panel, "Size", data, startIndex + 1);
                addDetailIfNotNA(panel, "Brand", data, startIndex + 2);
                addDetailIfNotNA(panel, "Material", data, startIndex + 3);
                addDetailIfNotNA(panel, "Type", data, startIndex + 4);
                break;

            case "Document":
                addDetailIfNotNA(panel, "Owner Name", data, startIndex);
                addDetailIfNotNA(panel, "Owner ID", data, startIndex + 1);
                addDetailIfNotNA(panel, "Document Type", data, startIndex + 2);
                break;

            case "Electronic":
                addDetailIfNotNA(panel, "Model", data, startIndex);
                addDetailIfNotNA(panel, "Brand", data, startIndex + 1);
                addDetailIfNotNA(panel, "Type", data, startIndex + 2);
                break;

            case "Food Container":
                addDetailIfNotNA(panel, "Color", data, startIndex);
                addDetailIfNotNA(panel, "Capacity", data, startIndex + 1);
                addDetailIfNotNA(panel, "Brand", data, startIndex + 2);
                addDetailIfNotNA(panel, "Type", data, startIndex + 3);
                break;

            case "Money":
                addDetailIfNotNA(panel, "Amount", data, startIndex);
                addDetailIfNotNA(panel, "In Wallet", data, startIndex + 1);
                break;

            case "Tumbler":
                addDetailIfNotNA(panel, "Color", data, startIndex);
                addDetailIfNotNA(panel, "Capacity", data, startIndex + 1);
                break;

            case "Others":
                addDetailIfNotNA(panel, "Item Type", data, startIndex);
                addDetailIfNotNA(panel, "Color", data, startIndex + 1);
                break;
        }
    }

    private void addDetailIfNotNA(JPanel panel, String label, String[] data, int index) {
        if (data.length > index && !data[index].equals("N/A")) {
            JLabel detailLabel = new JLabel("• " + label + ": " + data[index]);
            detailLabel.setFont(new Font("Arial", Font.PLAIN, 14));
            detailLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
            panel.add(detailLabel);
            panel.add(Box.createRigidArea(new Dimension(0, 5)));
        }
    }

    //create individual items
    private JPanel createItemPanel(String[] data) {
        JPanel panel = new JPanel();
        panel.setPreferredSize(new Dimension(200, 200));
        panel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        panel.setBackground(Color.WHITE);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        // Add hover effect
        panel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        // Add click listener
        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                goToItemDetails(data, lostItemsButton, reportAnItemButton, profileButton, detailsHolder);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                panel.setBackground(Color.decode("#F5F5F5"));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                panel.setBackground(Color.WHITE);
            }
        });

        //JPanel panel = new JPanel();
        panel.setPreferredSize(new Dimension(200, 200)); // Increased height for image
        panel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        panel.setBackground(Color.WHITE);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        // 1. Reported By (username) - LEFT with margin
        JLabel username = new JLabel(data[1]);
        username.setForeground(Color.GRAY);
        username.setFont(new Font("Arial", Font.PLAIN, 13));
        username.setAlignmentX(Component.LEFT_ALIGNMENT);
        username.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2)); // 2px margin

        // 2. Item Name - LEFT with margin
        JLabel name = new JLabel(data[2]);
        name.setForeground(Color.BLACK);
        name.setFont(new Font("Arial", Font.BOLD, 17));
        name.setAlignmentX(Component.LEFT_ALIGNMENT);
        name.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2)); // 2px margin

        // 3. Last Seen Location - LEFT with margin
        JLabel location = new JLabel("Last Seen: " + data[3]);
        location.setForeground(Color.DARK_GRAY);
        location.setFont(new Font("Arial", Font.PLAIN, 12));
        location.setAlignmentX(Component.LEFT_ALIGNMENT);
        location.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2)); // 2px margin

        // Add text elements first
        panel.add(username);
        panel.add(name);
        panel.add(location);
        panel.add(Box.createRigidArea(new Dimension(0, 5))); // Small spacing

        // 4. Image (if exists) - CENTERED, fills remaining space (no margin)
        if (data.length > 8 && !data[8].equals("N/A")) {
            try {
                File imageFile = new File("assets/reportedItems/" + data[8]);
                if (imageFile.exists()) {
                    ImageIcon imageIcon = new ImageIcon(imageFile.getAbsolutePath());

                    // Calculate available space (panel height - text height - spacing)
                    int availableHeight = 200 - 60; // Approximate height for 3 text lines
                    int availableWidth = 190; // Slightly less than panel width for padding

                    int originalWidth = imageIcon.getIconWidth();
                    int originalHeight = imageIcon.getIconHeight();

                    // Scale to fit available space while preserving aspect ratio
                    double widthRatio = (double) availableWidth / originalWidth;
                    double heightRatio = (double) availableHeight / originalHeight;
                    double scale = Math.min(widthRatio, heightRatio);

                    int newWidth = (int) (originalWidth * scale);
                    int newHeight = (int) (originalHeight * scale);

                    Image scaledImage = imageIcon.getImage().getScaledInstance(
                            newWidth, newHeight, Image.SCALE_SMOOTH);

                    // Create a wrapper panel to center the image
                    JPanel imagePanel = new JPanel();
                    imagePanel.setLayout(new BorderLayout());
                    imagePanel.setBackground(Color.WHITE);
                    imagePanel.setAlignmentX(Component.LEFT_ALIGNMENT);
                    imagePanel.setMaximumSize(new Dimension(200, availableHeight));

                    JLabel imageLabel = new JLabel(new ImageIcon(scaledImage));
                    imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
                    imageLabel.setVerticalAlignment(SwingConstants.CENTER);

                    imagePanel.add(imageLabel, BorderLayout.CENTER);
                    panel.add(imagePanel);
                }
            } catch (Exception e) {
                // No image, continue without it
            }
        } else {
            // Add glue to push content up if no image
            panel.add(Box.createVerticalGlue());
        }

        return panel;
    }

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

    private void setUpItemCategoryBox(JComboBox comboBox) {
        comboBox.addItem("Item Category");
        comboBox.addItem("Accessory");
        comboBox.addItem("Bag");
        comboBox.addItem("Clothing");
        comboBox.addItem("Document");
        comboBox.addItem("Electronic");
        comboBox.addItem("Food Container");
        comboBox.addItem("Money");
        comboBox.addItem("Tumbler");
        comboBox.addItem("Others");
    }

    //is called when comboBox selected accessory
    private void addAccessoryFields(JPanel panel) {
        JLabel colorLabel = new JLabel("Color:");
        colorLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        accessoryColor = new JTextField();
        accessoryColor.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        accessoryColor.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel materialLabel = new JLabel("Material:");
        materialLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        accessoryMaterial = new JTextField();
        accessoryMaterial.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        accessoryMaterial.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel typeLabel = new JLabel("Type:");
        typeLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        accessoryType = new JTextField();
        accessoryType.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        accessoryType.setAlignmentX(Component.LEFT_ALIGNMENT);

        panel.add(colorLabel);
        panel.add(accessoryColor);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(materialLabel);
        panel.add(accessoryMaterial);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(typeLabel);
        panel.add(accessoryType);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
    }

    //is called when comboBox selected bag
    private void addBagFields(JPanel panel) {
        JLabel colorLabel = new JLabel("Color:");
        colorLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        bagColor = new JTextField();
        bagColor.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        bagColor.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel brandLabel = new JLabel("Brand:");
        brandLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        bagBrand = new JTextField();
        bagBrand.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        bagBrand.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel typeLabel = new JLabel("Type:");
        typeLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        bagType = new JTextField();
        bagType.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        bagType.setAlignmentX(Component.LEFT_ALIGNMENT);

        panel.add(colorLabel);
        panel.add(bagColor);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(brandLabel);
        panel.add(bagBrand);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(typeLabel);
        panel.add(bagType);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
    }

    //is called when comboBox selected clothing
    private void addClothingFields(JPanel panel) {
        JLabel colorLabel = new JLabel("Color:");
        colorLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        clothingColor = new JTextField();
        clothingColor.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        clothingColor.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel sizeLabel = new JLabel("Size:");
        sizeLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        clothingSize = new JTextField();
        clothingSize.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        clothingSize.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel brandLabel = new JLabel("Brand:");
        brandLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        clothingBrand = new JTextField();
        clothingBrand.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        clothingBrand.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel materialLabel = new JLabel("Material:");
        materialLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        clothingMaterial = new JTextField();
        clothingMaterial.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        clothingMaterial.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel typeLabel = new JLabel("Type:");
        typeLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        clothingType = new JTextField();
        clothingType.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        clothingType.setAlignmentX(Component.LEFT_ALIGNMENT);

        panel.add(colorLabel);
        panel.add(clothingColor);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(sizeLabel);
        panel.add(clothingSize);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(brandLabel);
        panel.add(clothingBrand);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(materialLabel);
        panel.add(clothingMaterial);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(typeLabel);
        panel.add(clothingType);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
    }

    //is called when comboBox selected document
    private void addDocumentFields(JPanel panel) {
        JLabel ownerNameLabel = new JLabel("Owner Name:");
        ownerNameLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        documentOwnerName = new JTextField();
        documentOwnerName.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        documentOwnerName.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel ownerIDLabel = new JLabel("Owner ID:");
        ownerIDLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        documentOwnerID = new JTextField();
        documentOwnerID.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        documentOwnerID.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel typeLabel = new JLabel("Document Type:");
        typeLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        documentType = new JTextField();
        documentType.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        documentType.setAlignmentX(Component.LEFT_ALIGNMENT);

        panel.add(ownerNameLabel);
        panel.add(documentOwnerName);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(ownerIDLabel);
        panel.add(documentOwnerID);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(typeLabel);
        panel.add(documentType);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
    }

    //is called when comboBox selected electronics
    private void addElectronicFields(JPanel panel) {
        JLabel modelLabel = new JLabel("Model:");
        modelLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        electronicModel = new JTextField();
        electronicModel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        electronicModel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel brandLabel = new JLabel("Brand:");
        brandLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        electronicBrand = new JTextField();
        electronicBrand.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        electronicBrand.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel typeLabel = new JLabel("Type:");
        typeLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        electronicType = new JTextField();
        electronicType.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        electronicType.setAlignmentX(Component.LEFT_ALIGNMENT);

        panel.add(modelLabel);
        panel.add(electronicModel);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(brandLabel);
        panel.add(electronicBrand);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(typeLabel);
        panel.add(electronicType);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
    }

    //is called when comboBox selected foodcontainer
    private void addFoodContainerFields(JPanel panel) {
        JLabel colorLabel = new JLabel("Color:");
        colorLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        foodContainerColor = new JTextField();
        foodContainerColor.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        foodContainerColor.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel capacityLabel = new JLabel("Capacity:");
        capacityLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        foodContainerCapacity = new JTextField();
        foodContainerCapacity.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        foodContainerCapacity.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel brandLabel = new JLabel("Brand:");
        brandLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        foodContainerBrand = new JTextField();
        foodContainerBrand.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        foodContainerBrand.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel typeLabel = new JLabel("Type:");
        typeLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        foodContainerType = new JTextField();
        foodContainerType.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        foodContainerType.setAlignmentX(Component.LEFT_ALIGNMENT);

        panel.add(colorLabel);
        panel.add(foodContainerColor);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(capacityLabel);
        panel.add(foodContainerCapacity);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(brandLabel);
        panel.add(foodContainerBrand);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(typeLabel);
        panel.add(foodContainerType);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
    }

    //is called when comboBox selected money
    private void addMoneyFields(JPanel panel) {
        JLabel amountLabel = new JLabel("Amount:");
        amountLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        moneyAmount = new JTextField();
        moneyAmount.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        moneyAmount.setAlignmentX(Component.LEFT_ALIGNMENT);

        panel.add(amountLabel);
        panel.add(moneyAmount);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));

        addMoneyWalletRadioButtons(panel);
    }

    //is called when comboBox selected tumbler
    private void addTumblerFields(JPanel panel) {
        JLabel colorLabel = new JLabel("Color:");
        colorLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        tumblerColor = new JTextField();
        tumblerColor.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        tumblerColor.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel capacityLabel = new JLabel("Capacity:");
        capacityLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        tumblerCapacity = new JTextField();
        tumblerCapacity.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        tumblerCapacity.setAlignmentX(Component.LEFT_ALIGNMENT);

        panel.add(colorLabel);
        panel.add(tumblerColor);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(capacityLabel);
        panel.add(tumblerCapacity);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
    }

    //is called when comboBox selected other
    private void addOthersFields(JPanel panel) {
        JLabel itemTypeLabel = new JLabel("Specify Item Type:");
        itemTypeLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        othersItemType = new JTextField();
        othersItemType.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        othersItemType.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel colorLabel = new JLabel("Color:");
        colorLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        othersColor = new JTextField();
        othersColor.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        othersColor.setAlignmentX(Component.LEFT_ALIGNMENT);

        panel.add(itemTypeLabel);
        panel.add(othersItemType);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(colorLabel);
        panel.add(othersColor);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
    }

    private void addMoneyWalletRadioButtons(JPanel panel) {
        JLabel label = new JLabel("In Wallet:");
        label.setAlignmentX(Component.LEFT_ALIGNMENT);

        JPanel radioPanel = new JPanel();
        radioPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        radioPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        radioPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));

        inWalletRadio = new JRadioButton("Yes");
        notInWalletRadio = new JRadioButton("No");

        ButtonGroup buttonGroup = new ButtonGroup();
        buttonGroup.add(inWalletRadio);
        buttonGroup.add(notInWalletRadio);

        radioPanel.add(inWalletRadio);
        radioPanel.add(notInWalletRadio);

        panel.add(label);
        panel.add(radioPanel);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
    }

    //get the last report number recorded in items.csv
    private int getNextReportNumber() {
        int lastReportNumber = 0;

        try (BufferedReader br = new BufferedReader(new FileReader("Items.csv"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length > 0) {
                        int currentNumber = Integer.parseInt(data[0]);
                        if (currentNumber > lastReportNumber) {
                            lastReportNumber = currentNumber;
                        }
                }
            }
        } catch(IOException e){
            //do nothing
        }

        return lastReportNumber + 1;
    }

    private void recordReport(String reportedBy, String status, String itemName, String lastSeenAt, String lastSeenOn, String description, int categoryIndex) {
        int reportNumber = getNextReportNumber();
        String category = (String) itemCategoryBox.getItemAt(categoryIndex);

// Rename and move image file if exists
        String imageFileName = "N/A";
        if (!uploadedImageFilePath.isEmpty()) {
            try {
                File destDir = new File("assets/reportedItems");
                if (!destDir.exists()) {
                    destDir.mkdirs();
                }

                // Create filename with report number
                imageFileName = reportNumber + uploadedImageFileName; // extension stored in uploadedImageFileName
                File destFile = new File(destDir, imageFileName);

                // Copy file with new name
                Files.copy(new File(uploadedImageFilePath).toPath(), destFile.toPath(),
                        StandardCopyOption.REPLACE_EXISTING);
            } catch (Exception ex) {
                imageFileName = "N/A";
            }
        }

        String reportData = reportNumber + "," + reportedBy + "," + itemName + "," +
                lastSeenAt + "," + lastSeenOn + "," + description + "," +
                status + "," + category + "," + imageFileName;

        // Rest of the switch statement stays the same...
        switch (categoryIndex) {
            case 1: // Accessory
                reportData = reportData + "," + (accessoryColor.getText().isEmpty() ? "N/A" : accessoryColor.getText()) +
                        "," + (accessoryMaterial.getText().isEmpty() ? "N/A" : accessoryMaterial.getText()) +
                        "," + (accessoryType.getText().isEmpty() ? "N/A" : accessoryType.getText());
                break;
            case 2: // Bag
                reportData = reportData + "," + (bagColor.getText().isEmpty() ? "N/A" : bagColor.getText()) +
                        "," + (bagBrand.getText().isEmpty() ? "N/A" : bagBrand.getText()) +
                        "," + (bagType.getText().isEmpty() ? "N/A" : bagType.getText());
                break;
            case 3: // Clothing
                reportData = reportData + "," + (clothingColor.getText().isEmpty() ? "N/A" : clothingColor.getText()) +
                        "," + (clothingSize.getText().isEmpty() ? "N/A" : clothingSize.getText()) +
                        "," + (clothingBrand.getText().isEmpty() ? "N/A" : clothingBrand.getText()) +
                        "," + (clothingMaterial.getText().isEmpty() ? "N/A" : clothingMaterial.getText()) +
                        "," + (clothingType.getText().isEmpty() ? "N/A" : clothingType.getText());
                break;
            case 4: // Document
                reportData = reportData + "," + (documentOwnerName.getText().isEmpty() ? "N/A" : documentOwnerName.getText()) +
                        "," + (documentOwnerID.getText().isEmpty() ? "N/A" : documentOwnerID.getText()) +
                        "," + (documentType.getText().isEmpty() ? "N/A" : documentType.getText());
                break;
            case 5: // Electronic
                reportData = reportData + "," + (electronicModel.getText().isEmpty() ? "N/A" : electronicModel.getText()) +
                        "," + (electronicBrand.getText().isEmpty() ? "N/A" : electronicBrand.getText()) +
                        "," + (electronicType.getText().isEmpty() ? "N/A" : electronicType.getText());
                break;
            case 6: // Food Container
                reportData = reportData + "," + (foodContainerColor.getText().isEmpty() ? "N/A" : foodContainerColor.getText()) +
                        "," + (foodContainerCapacity.getText().isEmpty() ? "N/A" : foodContainerCapacity.getText()) +
                        "," + (foodContainerBrand.getText().isEmpty() ? "N/A" : foodContainerBrand.getText()) +
                        "," + (foodContainerType.getText().isEmpty() ? "N/A" : foodContainerType.getText());
                break;
            case 7: // Money
                String inWallet = "N/A";
                if (inWalletRadio != null && notInWalletRadio != null) {
                    if (inWalletRadio.isSelected()) inWallet = "Yes";
                    else if (notInWalletRadio.isSelected()) inWallet = "No";
                }
                reportData = reportData + "," + (moneyAmount.getText().isEmpty() ? "N/A" : moneyAmount.getText()) +
                        "," + inWallet;
                break;
            case 8: // Tumbler
                reportData = reportData + "," + (tumblerColor.getText().isEmpty() ? "N/A" : tumblerColor.getText()) +
                        "," + (tumblerCapacity.getText().isEmpty() ? "N/A" : tumblerCapacity.getText());
                break;
            case 9: // Others
                reportData = reportData + "," + (othersItemType.getText().isEmpty() ? "N/A" : othersItemType.getText()) +
                        "," + (othersColor.getText().isEmpty() ? "N/A" : othersColor.getText());
                break;
        }

        // Write to CSV
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("Items.csv", true))) {
            bw.write(reportData);
            bw.newLine();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error saving report: " + e.getMessage());
        }
    }

    private void clearReportForm() {
        inputLostItemName.setText("");
        inputLastSeenAt.setText("");
        inputLastSeenOn.setText("");
        inputDescription.setText("");
        itemCategoryBox.setSelectedIndex(0);

        lostButton.setSelected(false);
        foundButton.setSelected(false);

        // Clear image
        uploadedImageFileName = "";
        uploadedImageFilePath = "";
        imagePreviewLabel.setIcon(null);
        imagePreviewLabel.setText("Drag & Drop Image Here");

        additionalDetailsPanel.removeAll();
        additionalDetailsPanel.revalidate();
        additionalDetailsPanel.repaint();
    }

    private void setupImageDragAndDrop() {
        // Create the drop target
        new DropTarget(imageDropZone, new DropTargetListener() {
            @Override
            public void dragEnter(DropTargetDragEvent dtde) {
                // Visual feedback when dragging over
                imageDropZone.setBackground(Color.decode("#FFE5B4"));
            }

            @Override
            public void dragOver(DropTargetDragEvent dtde) {
                // Accept the drag
                dtde.acceptDrag(DnDConstants.ACTION_COPY);
            }

            @Override
            public void dropActionChanged(DropTargetDragEvent dtde) {
            }

            @Override
            public void dragExit(DropTargetEvent dte) {
                // Reset background when drag leaves
                imageDropZone.setBackground(Color.WHITE);
            }

            @Override
            public void drop(DropTargetDropEvent dtde) {
                try {
                    // Accept the drop
                    dtde.acceptDrop(DnDConstants.ACTION_COPY);

                    // Get the transferred data
                    List<File> droppedFiles = (List<File>) dtde.getTransferable().getTransferData(DataFlavor.javaFileListFlavor);

                    if (!droppedFiles.isEmpty()) {
                        File imageFile = droppedFiles.get(0);

                        // Validate it's an image
                        String fileName = imageFile.getName().toLowerCase();
                        if (fileName.endsWith(".jpg") || fileName.endsWith(".jpeg") ||
                                fileName.endsWith(".png") || fileName.endsWith(".gif")) {

                            // In setupImageDragAndDrop(), after validation:
                            uploadedImageFilePath = imageFile.getAbsolutePath();
                            String extension = fileName.substring(fileName.lastIndexOf("."));
                            uploadedImageFileName = extension;

                            File destFile = imageFile; // Use original file for preview

                            // Show preview
                            ImageIcon imageIcon = new ImageIcon(destFile.getAbsolutePath());
                            int maxWidth = imageDropZone.getWidth() - 20;
                            int maxHeight = imageDropZone.getHeight() - 20;
                            int originalWidth = imageIcon.getIconWidth();
                            int originalHeight = imageIcon.getIconHeight();

                            // Calculate scaling factor while preserving aspect ratio
                            double widthRatio = (double) maxWidth / originalWidth;
                            double heightRatio = (double) maxHeight / originalHeight;
                            double scale = Math.min(widthRatio, heightRatio);

                            int newWidth = (int) (originalWidth * scale);
                            int newHeight = (int) (originalHeight * scale);

                            Image scaledImage = imageIcon.getImage().getScaledInstance(
                                    newWidth,
                                    newHeight,
                                    Image.SCALE_SMOOTH
                            );

                            imagePreviewLabel.setIcon(new ImageIcon(scaledImage));
                            imagePreviewLabel.setText("");

                            // Reset background
                            imageDropZone.setBackground(Color.WHITE);

                            JOptionPane.showMessageDialog(null,
                                    "Image uploaded successfully!");

                        } else {
                            JOptionPane.showMessageDialog(null,
                                    "Please upload only image files (JPG, PNG, GIF)");
                        }
                    }

                    dtde.dropComplete(true);

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null,
                            "Error uploading image: " + ex.getMessage());
                    dtde.dropComplete(false);
                }
            }
        });
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

        reportAnItemButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                goToReportAnItem(user, lostItemsButton, reportAnItemButton, profileButton);
            }
        });

        profileButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                goToProfile(user, lostItemsButton, reportAnItemButton, profileButton);
            }
        });

        logoutButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                user = null;

                goToLogin();
                loginPage.setVisible(true);
            }
        });

        itemCategoryBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // refresh panel
                additionalDetailsPanel.removeAll();

                //set layout for additional panels
                additionalDetailsPanel.setLayout(new BoxLayout(additionalDetailsPanel, BoxLayout.Y_AXIS));

                switch (itemCategoryBox.getSelectedIndex()) {
                    case 0:
                        //do nothing
                        break;
                    case 1: //Accessory
                        addAccessoryFields(additionalDetailsPanel);
                        break;
                    case 2: //Bag
                        addBagFields(additionalDetailsPanel);
                        break;
                    case 3: //Clothing
                        addClothingFields(additionalDetailsPanel);
                        break;
                    case 4: //Document
                        addDocumentFields(additionalDetailsPanel);
                        break;
                    case 5: //Electronic
                        addElectronicFields(additionalDetailsPanel);
                        break;
                    case 6: //Food Container
                        addFoodContainerFields(additionalDetailsPanel);
                        break;
                    case 7: //Money
                        addMoneyFields(additionalDetailsPanel);
                        break;
                    case 8: //Tumbler
                        addTumblerFields(additionalDetailsPanel);
                        break;
                    case 9: //Others
                        addOthersFields(additionalDetailsPanel);
                        break;
                    default:
                        break;
                }
                //refresh and resize
                additionalDetailsPanel.revalidate();
                additionalDetailsPanel.repaint();
            }
        });

        lostButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    if (inputLostItemName.getText().isEmpty()) {
                        throw new EmptyField("Item Name");
                    }
                    if (inputLastSeenAt.getText().isEmpty()) {
                        throw new EmptyField("Last Seen At");
                    }
                    if (inputLastSeenOn.getText().isEmpty()) {
                        throw new EmptyField("Last Seen On");
                    }
                    if (inputDescription.getText().isEmpty()) {
                        throw new EmptyField("Description");
                    }
                    if (itemCategoryBox.getSelectedIndex() == 0) {
                        JOptionPane.showMessageDialog(null, "Please select an item category");
                        return;
                    }
                    //report as I lost this item
                    recordReport(user[0], "Lost", inputLostItemName.getText(), inputLastSeenAt.getText(), inputLastSeenOn.getText(), inputDescription.getText(), itemCategoryBox.getSelectedIndex());

                    int reportNum = getNextReportNumber() - 1;
                    //validate success
                    JOptionPane.showMessageDialog(null, "Lost item reported successfully!");

                    clearReportForm();

                } catch (EmptyField ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage());
                }
            }
        });

        foundButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    if (inputLostItemName.getText().isEmpty()) {
                        throw new EmptyField("Item Name");
                    }
                    if (inputLastSeenAt.getText().isEmpty()) {
                        throw new EmptyField("Last Seen At");
                    }
                    if (inputLastSeenOn.getText().isEmpty()) {
                        throw new EmptyField("Last Seen On");
                    }
                    if (inputDescription.getText().isEmpty()) {
                        throw new EmptyField("Description");
                    }
                    if (itemCategoryBox.getSelectedIndex() == 0) {
                        JOptionPane.showMessageDialog(null, "Please select an item category");
                        return;
                    }

                    //report as I found this item
                    recordReport(user[0], "Found", inputLostItemName.getText(), inputLastSeenAt.getText(), inputLastSeenOn.getText(), inputDescription.getText(), itemCategoryBox.getSelectedIndex());

                    int reportNum = getNextReportNumber() - 1;
                    //validate success
                    JOptionPane.showMessageDialog(null, "Found item reported successfully!");

                    clearReportForm();

                } catch (EmptyField ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage());
                }
            }
        });

        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                goToLostItems(user, lostItemsButton, reportAnItemButton, profileButton, itemsHolder);
            }
        });

        //setup Panel
        imagePreviewLabel.setText("Drag & Drop Image Here");
        imagePreviewLabel.setHorizontalAlignment(SwingConstants.CENTER);
        imagePreviewLabel.setVerticalAlignment(SwingConstants.CENTER);
        setContentPane(mainPanel);
        //Instantly sets the form as the log-in page
        goToLogin();
        setTitle("Lost and Found Management System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(520, 780);
        setLocationRelativeTo(null);
        setVisible(true);
        logoIcon.setIcon(new ImageIcon("assets/cit_logo.png")); //300x212 dimensions
        constantBanner.setIcon(new ImageIcon("assets/cit_logo.png")); //TODO get banner picture and change this
        redirectToOther.setOpaque(false);
        redirectToOther.setContentAreaFilled(false);
        redirectToOther.setBorderPainted(false);
        logoutButton.setOpaque(false);
        logoutButton.setContentAreaFilled(false);
        logoutButton.setBorderPainted(false);
        itemsHolder.setLayout(new GridLayout(0, 2, 10, 10));
        setUpItemCategoryBox(itemCategoryBox);
        setupImageDragAndDrop();
        scrolledItemsHolder.getVerticalScrollBar().setUnitIncrement(15);//n pixels per scroll [I'd suggest somewhere between 10-20 inclusive] works good in small - medium lists
        scrollableDetails.getVerticalScrollBar().setUnitIncrement(15);
        backButton.setOpaque(false);
        backButton.setContentAreaFilled(false);
        backButton.setBorderPainted(false);
        detailsHolder.getVerticalScrollBar().setUnitIncrement(15);
    }
}
