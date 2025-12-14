import ItemPack.*;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class LFReport extends JFrame {

    // ====== COLOR SCHEME MATCHING LFForm ======
    // Maroon and Gold colors from CIT-U branding
    private static final Color CIT_MAROON = Color.decode("#800000");
    private static final Color CIT_GOLD = Color.decode("#FFD700");
    private static final Color BACKGROUND_WHITE = Color.WHITE;
    // ==========================================

    private JPanel contentPane;
    private JPanel Header;
    private JPanel Details;
    private JPanel SubHeader;
    private JPanel HiddenPanel;
    private JPanel ReportButtons;
    private JButton ReportFound;
    private JButton ReportLost;
    private JTextField itemNameInput;
    private JTextField itemDetailsInput;
    private JLabel itemNameLabel;
    private JTextField FoundbyInput;
    private JLabel itemDetailsLabel;
    private JLabel FoundbyLabel;
    private JLabel itemType;
    private JComboBox ItemTypesCombo;
    private JLabel lastFoundLabel;
    private JTextField lastFoundInput;

    // ====== NAVIGATION BUTTONS (from .form file) ======
    private JButton LostItemsbtn;
    private JButton Profilebtn;
    private JButton ReportItemsbtn;
    // =================================================

    // ====== IMAGE UPLOAD COMPONENTS ======
    private JButton ImageUploadbtn;
    private JLabel ImageDisplay;
    private JPanel ImageDisplayLabel;
    private String selectedImagePath = null;
    // ====================================

    // ====== PARENT FORM REFERENCE ======
    private LFForm parentForm;
    // ==================================

    private User currentUser;
    private LFSystem system;

    // ====== CONSTRUCTOR: Updated to accept parent form ======
    // Now accepts three parameters: User, LFSystem, and the parent LFForm
    // This creates a link between this report window and the main application window
    public LFReport(User user, LFSystem sys, LFForm parent) {
        this.currentUser = user;
        this.system = sys;
        this.parentForm = parent;
        // =======================================================

        setupUI();
        setupListeners();
        applyColorScheme();

        setContentPane(contentPane);
        setTitle("Report Lost/Found Item - " + user.getName());
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(520, 700);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    // ====== NEW MODIFICATION: Apply CIT-U Color Scheme ======
    // Applies maroon and gold colors to match the main LFForm
    private void applyColorScheme() {
        // Set background colors
        if (contentPane != null) contentPane.setBackground(BACKGROUND_WHITE);
        if (Header != null) {
            Header.setBackground(CIT_MAROON);
            // Add header label styling if it exists
            for (Component comp : Header.getComponents()) {
                if (comp instanceof JLabel) {
                    ((JLabel) comp).setForeground(CIT_GOLD);
                    ((JLabel) comp).setFont(new Font("Serif", Font.BOLD, 18));
                }
            }
        }
        if (Details != null) Details.setBackground(BACKGROUND_WHITE);
        if (SubHeader != null) SubHeader.setBackground(BACKGROUND_WHITE);
        if (HiddenPanel != null) HiddenPanel.setBackground(BACKGROUND_WHITE);
        if (ImageDisplayLabel != null) ImageDisplayLabel.setBackground(BACKGROUND_WHITE);

        // Style navigation buttons in SubHeader to match LFForm tabs
        if (LostItemsbtn != null) {
            LostItemsbtn.setBackground(CIT_MAROON);
            LostItemsbtn.setForeground(Color.WHITE);
            LostItemsbtn.setOpaque(true);
            LostItemsbtn.setFocusPainted(false);
            LostItemsbtn.setFont(new Font("SansSerif", Font.BOLD, 12));
            LostItemsbtn.setText("Lost Items");
        }

        if (ReportItemsbtn != null) {
            ReportItemsbtn.setBackground(CIT_GOLD);
            ReportItemsbtn.setForeground(CIT_MAROON);
            ReportItemsbtn.setOpaque(true);
            ReportItemsbtn.setFocusPainted(false);
            ReportItemsbtn.setFont(new Font("SansSerif", Font.BOLD, 12));
            ReportItemsbtn.setText("Report Items");
        }

        if (Profilebtn != null) {
            Profilebtn.setBackground(CIT_MAROON);
            Profilebtn.setForeground(Color.WHITE);
            Profilebtn.setOpaque(true);
            Profilebtn.setFocusPainted(false);
            Profilebtn.setFont(new Font("SansSerif", Font.BOLD, 12));
            Profilebtn.setText("Profile");
        }

        // Style report buttons (Lost/Found) with gold
        if (ReportLost != null) {
            ReportLost.setBackground(CIT_GOLD);
            ReportLost.setForeground(CIT_MAROON);
            ReportLost.setOpaque(true);
            ReportLost.setFocusPainted(false);
            ReportLost.setFont(new Font("SansSerif", Font.BOLD, 14));
        }
        if (ReportFound != null) {
            ReportFound.setBackground(CIT_GOLD);
            ReportFound.setForeground(CIT_MAROON);
            ReportFound.setOpaque(true);
            ReportFound.setFocusPainted(false);
            ReportFound.setFont(new Font("SansSerif", Font.BOLD, 14));
        }

        // Style image upload button
        if (ImageUploadbtn != null) {
            ImageUploadbtn.setBackground(CIT_MAROON);
            ImageUploadbtn.setForeground(Color.WHITE);
            ImageUploadbtn.setOpaque(true);
            ImageUploadbtn.setFocusPainted(false);
            ImageUploadbtn.setText("Upload Image");
        }

        // Style image display label
        if (ImageDisplay != null) {
            ImageDisplay.setPreferredSize(new Dimension(200, 200));
            ImageDisplay.setBorder(BorderFactory.createLineBorder(CIT_MAROON, 2));
            ImageDisplay.setHorizontalAlignment(SwingConstants.CENTER);
            ImageDisplay.setText("No Image Selected");
            ImageDisplay.setOpaque(true);
            ImageDisplay.setBackground(Color.LIGHT_GRAY);
        }
    }
    // ========================================================

    private void setupUI() {
        HiddenPanel.setVisible(true);
        Details.setVisible(true);

        // Set button text
        ReportLost.setText("Report as LOST");
        ReportFound.setText("Report as FOUND");

        ItemTypesCombo.removeAllItems();
        ItemTypesCombo.addItem("Select Type");
        ItemTypesCombo.addItem("Money");
        ItemTypesCombo.addItem("Document");
        ItemTypesCombo.addItem("Accessory");
        ItemTypesCombo.addItem("Bag");
        ItemTypesCombo.addItem("Clothing");
        ItemTypesCombo.addItem("Electronic");
        ItemTypesCombo.addItem("Wearable Electronic");
        ItemTypesCombo.addItem("Food Container");
        ItemTypesCombo.addItem("Other");

        // Initially hide Found By field
        FoundbyLabel.setVisible(false);
        FoundbyInput.setVisible(false);
    }

    private void setupListeners() {
        // Report Lost/Found buttons
        ReportLost.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                submitReport(true);
            }
        });

        ReportFound.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                submitReport(false);
            }
        });

        // ====== NEW: Navigation Button Listeners ======
        // These buttons allow navigation back to main form pages
        // They close this report window and switch the main form view

        if (LostItemsbtn != null) {
            LostItemsbtn.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // Close this window and go to Lost Items page on main form
                    if (parentForm != null) {
                        parentForm.refreshItemsList();
                    }
                    dispose(); // Close the report window
                }
            });
        }

        if (ReportItemsbtn != null) {
            ReportItemsbtn.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // Already on report page, just refresh if needed
                    // Or could close and reopen, but that's redundant
                    JOptionPane.showMessageDialog(LFReport.this,
                            "You are already on the Report Items page!",
                            "Info",
                            JOptionPane.INFORMATION_MESSAGE);
                }
            });
        }

        if (Profilebtn != null) {
            Profilebtn.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // Close this window and go to Profile page on main form
                    if (parentForm != null) {
                        // Would need to add a method to navigate to profile
                        // For now, just close this window
                        JOptionPane.showMessageDialog(LFReport.this,
                                "Profile navigation coming soon!\nClosing report window...",
                                "Info",
                                JOptionPane.INFORMATION_MESSAGE);
                    }
                    dispose(); // Close the report window
                }
            });
        }
        // =============================================

        // ====== IMAGE UPLOAD LISTENER ======
        if (ImageUploadbtn != null) {
            ImageUploadbtn.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    selectImage();
                }
            });
        }
        // ==================================
    }

    // ====== IMAGE SELECTION METHOD ======
    // Opens file chooser to select an image file
    // Displays preview and stores the file path
    private void selectImage() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Select Item Image");

        // Filter for image files only
        FileNameExtensionFilter filter = new FileNameExtensionFilter(
                "Image Files", "jpg", "jpeg", "png", "gif", "bmp");
        fileChooser.setFileFilter(filter);

        int result = fileChooser.showOpenDialog(this);

        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            selectedImagePath = selectedFile.getAbsolutePath();

            // Display image preview
            displayImagePreview(selectedFile);
        }
    }

    // Display the selected image as a preview
    private void displayImagePreview(File imageFile) {
        try {
            ImageIcon icon = new ImageIcon(imageFile.getAbsolutePath());

            // Scale image to fit the label
            Image image = icon.getImage();
            Image scaledImage = image.getScaledInstance(200, 200, Image.SCALE_SMOOTH);
            ImageIcon scaledIcon = new ImageIcon(scaledImage);

            if (ImageDisplay != null) {
                ImageDisplay.setIcon(scaledIcon);
                ImageDisplay.setText(""); // Clear the "No Image Selected" text
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Error loading image: " + e.getMessage(),
                    "Image Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    // Save the image to the items_images directory and return the saved filename
    private String saveImage(int itemID) {
        if (selectedImagePath == null) {
            return ""; // No image selected
        }

        try {
            // Create items_images directory if it doesn't exist
            File imagesDir = new File("items_images");
            if (!imagesDir.exists()) {
                imagesDir.mkdirs();
            }

            // Get file extension
            String originalFileName = new File(selectedImagePath).getName();
            String extension = "";
            int dotIndex = originalFileName.lastIndexOf('.');
            if (dotIndex > 0) {
                extension = originalFileName.substring(dotIndex);
            }

            // Create new filename: item_[itemID]_[timestamp][extension]
            String newFileName = "item_" + itemID + "_" + System.currentTimeMillis() + extension;
            Path sourcePath = Paths.get(selectedImagePath);
            Path destinationPath = Paths.get("items_images", newFileName);

            // Copy the file
            Files.copy(sourcePath, destinationPath, StandardCopyOption.REPLACE_EXISTING);

            return newFileName; // Return just the filename, not the full path

        } catch (IOException e) {
            System.err.println("Error saving image: " + e.getMessage());
            JOptionPane.showMessageDialog(this,
                    "Warning: Could not save image file.",
                    "Image Save Warning",
                    JOptionPane.WARNING_MESSAGE);
            return "";
        }
    }
    // ===================================

    // ====== SUBMIT REPORT METHOD ======
    private void submitReport(boolean isReportingLost) {
        String name = itemNameInput.getText().trim();
        String details = itemDetailsInput.getText().trim();
        String location = lastFoundInput.getText().trim();
        String type = (String) ItemTypesCombo.getSelectedItem();

        if (name.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Please enter an item name.",
                    "Validation Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        if ("Select Type".equals(type)) {
            JOptionPane.showMessageDialog(this,
                    "Please select an item type.",
                    "Validation Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        Item newItem = createItemByType(type);
        if (newItem == null) return;

        newItem.setItemID(system.item_id++);
        newItem.setItemName(name);
        newItem.setDetails(details.isEmpty() ? "-" : details);
        newItem.setLastLocationSeen(location.isEmpty() ? "-" : location);

        // ====== SAVE IMAGE AND GET FILENAME ======
        String imagePath = saveImage(newItem.getItemID());
        // ========================================

        if (isReportingLost) {
            currentUser.addLostItem(newItem);
            system.lostList.add(newItem);

            // ====== SAVE LOST ITEM TO CSV WITH IMAGE ======
            saveItemToCSV(newItem, "LOST", imagePath);
            // ==============================================

            JOptionPane.showMessageDialog(this,
                    "Lost item reported successfully!\nItem ID: " + newItem.getItemID(),
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE);
        } else {
            String foundBy = FoundbyInput.getText().trim();
            if (!foundBy.isEmpty()) {
                newItem.found(foundBy);
            }
            currentUser.addFoundItem(newItem);
            system.foundList.add(newItem);

            // ====== SAVE FOUND ITEM TO CSV WITH IMAGE ======
            saveItemToCSV(newItem, "FOUND", imagePath);
            // ==============================================

            checkForMatches(newItem);
            JOptionPane.showMessageDialog(this,
                    "Found item reported successfully!\nItem ID: " + newItem.getItemID(),
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE);
        }

        // ====== REFRESH PARENT FORM AFTER ADDING ITEM ======
        if (parentForm != null) {
            parentForm.refreshItemsList();
        }
        // ==================================================

        clearForm();
    }
    // ==================================

    // ====== SAVE ITEM TO CSV WITH IMAGE PATH ======
    // CSV Format: itemName,location,type,status,itemID,reportedBy,imagePath
    private void saveItemToCSV(Item item, String status, String imagePath) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("Items.csv", true))) {
            String line = item.getItemName() + "," +
                    item.getLastLocationSeen() + "," +
                    item.getClass().getSimpleName() + "," +
                    status + "," +
                    item.getItemID() + "," +
                    currentUser.getName() + "," +
                    (imagePath.isEmpty() ? "-" : imagePath);
            bw.write(line);
            bw.newLine();
        } catch (IOException e) {
            System.err.println("Error writing to Items.csv: " + e.getMessage());
        }
    }
    // =============================================

    private Item createItemByType(String type) {
        Item item = null;

        switch (type) {
            case "Money":
                String amountStr = JOptionPane.showInputDialog(this, "Enter amount:");
                if (amountStr == null || amountStr.trim().isEmpty()) return null;
                try {
                    double amount = Double.parseDouble(amountStr);
                    int wallet = JOptionPane.showConfirmDialog(this,
                            "Was it in a wallet?",
                            "Wallet",
                            JOptionPane.YES_NO_OPTION);
                    item = new Money(amount, wallet == JOptionPane.YES_OPTION);
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(this, "Invalid amount!");
                    return null;
                }
                break;

            case "Document":
                String ownerName = JOptionPane.showInputDialog(this, "Owner Name (leave blank if illegible):");
                String ownerID = JOptionPane.showInputDialog(this, "Owner ID (leave blank if illegible):");
                String docType = JOptionPane.showInputDialog(this, "Document Type (ID, Certificate, etc.):");

                ownerName = (ownerName == null || ownerName.trim().isEmpty()) ? "-" : ownerName;
                ownerID = (ownerID == null || ownerID.trim().isEmpty()) ? "-" : ownerID;
                docType = (docType == null || docType.trim().isEmpty()) ? "-" : docType;

                item = new Document(ownerName, ownerID, docType);
                break;

            case "Accessory":
                String accMaterial = JOptionPane.showInputDialog(this, "Material:");
                String accType = JOptionPane.showInputDialog(this, "Type (ring, watch, etc.):");
                String accColor = JOptionPane.showInputDialog(this, "Color:");

                accMaterial = (accMaterial == null || accMaterial.trim().isEmpty()) ? "-" : accMaterial;
                accType = (accType == null || accType.trim().isEmpty()) ? "-" : accType;
                accColor = (accColor == null || accColor.trim().isEmpty()) ? "-" : accColor;

                item = new Accessory(accMaterial, accType, accColor);
                break;

            case "Bag":
                String bagBrand = JOptionPane.showInputDialog(this, "Brand:");
                String bagMaterial = JOptionPane.showInputDialog(this, "Material:");
                String bagType = JOptionPane.showInputDialog(this, "Type (backpack, tote, etc.):");
                String bagColor = JOptionPane.showInputDialog(this, "Color:");

                bagBrand = (bagBrand == null || bagBrand.trim().isEmpty()) ? "-" : bagBrand;
                bagMaterial = (bagMaterial == null || bagMaterial.trim().isEmpty()) ? "-" : bagMaterial;
                bagType = (bagType == null || bagType.trim().isEmpty()) ? "-" : bagType;
                bagColor = (bagColor == null || bagColor.trim().isEmpty()) ? "-" : bagColor;

                item = new Bag(bagBrand, bagMaterial, bagType, bagColor);
                break;

            case "Clothing":
                String size = JOptionPane.showInputDialog(this, "Size:");
                String clothBrand = JOptionPane.showInputDialog(this, "Brand:");
                String clothMaterial = JOptionPane.showInputDialog(this, "Material:");
                String clothType = JOptionPane.showInputDialog(this, "Type (shirt, pants, etc.):");
                String clothColor = JOptionPane.showInputDialog(this, "Color:");

                size = (size == null || size.trim().isEmpty()) ? "-" : size;
                clothBrand = (clothBrand == null || clothBrand.trim().isEmpty()) ? "-" : clothBrand;
                clothMaterial = (clothMaterial == null || clothMaterial.trim().isEmpty()) ? "-" : clothMaterial;
                clothType = (clothType == null || clothType.trim().isEmpty()) ? "-" : clothType;
                clothColor = (clothColor == null || clothColor.trim().isEmpty()) ? "-" : clothColor;

                item = new Clothing(size, clothBrand, clothMaterial, clothType, clothColor);
                break;

            case "Electronic":
                String model = JOptionPane.showInputDialog(this, "Model:");
                String elecBrand = JOptionPane.showInputDialog(this, "Brand:");
                String elecMaterial = JOptionPane.showInputDialog(this, "Material:");
                String elecType = JOptionPane.showInputDialog(this, "Type (phone, laptop, etc.):");
                String elecColor = JOptionPane.showInputDialog(this, "Color:");

                model = (model == null || model.trim().isEmpty()) ? "-" : model;
                elecBrand = (elecBrand == null || elecBrand.trim().isEmpty()) ? "-" : elecBrand;
                elecMaterial = (elecMaterial == null || elecMaterial.trim().isEmpty()) ? "-" : elecMaterial;
                elecType = (elecType == null || elecType.trim().isEmpty()) ? "-" : elecType;
                elecColor = (elecColor == null || elecColor.trim().isEmpty()) ? "-" : elecColor;

                item = new Electronic(model, elecBrand, elecMaterial, elecType, elecColor);
                break;

            case "Wearable Electronic":
                String wModel = JOptionPane.showInputDialog(this, "Model:");
                String wBrand = JOptionPane.showInputDialog(this, "Brand:");
                String wMaterial = JOptionPane.showInputDialog(this, "Material:");
                String wType = JOptionPane.showInputDialog(this, "Type (smartwatch, earbuds, etc.):");
                String wColor = JOptionPane.showInputDialog(this, "Color:");

                wModel = (wModel == null || wModel.trim().isEmpty()) ? "-" : wModel;
                wBrand = (wBrand == null || wBrand.trim().isEmpty()) ? "-" : wBrand;
                wMaterial = (wMaterial == null || wMaterial.trim().isEmpty()) ? "-" : wMaterial;
                wType = (wType == null || wType.trim().isEmpty()) ? "-" : wType;
                wColor = (wColor == null || wColor.trim().isEmpty()) ? "-" : wColor;

                item = new WearableElectronic(wModel, wBrand, wMaterial, wType, wColor);
                break;

            case "Food Container":
                String capacity = JOptionPane.showInputDialog(this, "Capacity:");
                String fcBrand = JOptionPane.showInputDialog(this, "Brand:");
                String fcType = JOptionPane.showInputDialog(this, "Type (tumbler, lunchbox, etc.):");
                String fcColor = JOptionPane.showInputDialog(this, "Color:");

                capacity = (capacity == null || capacity.trim().isEmpty()) ? "-" : capacity;
                fcBrand = (fcBrand == null || fcBrand.trim().isEmpty()) ? "-" : fcBrand;
                fcType = (fcType == null || fcType.trim().isEmpty()) ? "-" : fcType;
                fcColor = (fcColor == null || fcColor.trim().isEmpty()) ? "-" : fcColor;

                item = new FoodContainer(capacity, fcBrand, fcType, fcColor);
                break;

            case "Other":
                String itemCat = JOptionPane.showInputDialog(this, "Item Category:");
                itemCat = (itemCat == null || itemCat.trim().isEmpty()) ? "-" : itemCat;
                item = new Miscellaneous(itemCat);
                break;

            default:
                return null;
        }

        return item;
    }

    private void checkForMatches(Item foundItem) {
        for (Item lostItem : system.lostList) {
            if (lostItem.getItemName().equalsIgnoreCase(foundItem.getItemName())) {
                JOptionPane.showMessageDialog(this,
                        "POTENTIAL MATCH FOUND!\n" +
                                "A similar item was reported lost:\n" +
                                "Item ID: " + lostItem.getItemID() + "\n" +
                                "Name: " + lostItem.getItemName(),
                        "Match Found",
                        JOptionPane.INFORMATION_MESSAGE);
                break;
            }
        }
    }

    // ====== CLEAR FORM INCLUDING IMAGE ======
    private void clearForm() {
        itemNameInput.setText("");
        itemDetailsInput.setText("");
        FoundbyInput.setText("");
        lastFoundInput.setText("");
        ItemTypesCombo.setSelectedIndex(0);

        // Clear image selection
        selectedImagePath = null;
        if (ImageDisplay != null) {
            ImageDisplay.setIcon(null);
            ImageDisplay.setText("No Image Selected");
        }

        revalidate();
        repaint();
    }
    // =======================================

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                LFSystem mockSystem = new LFSystem();
                User mockUser = new User("Test User", "12345", "password", "0912-345-6789", "CS", 3);
                new LFReport(mockUser, mockSystem, null);
            }
        });
    }
}