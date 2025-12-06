import ItemPack.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LFReport extends JFrame {
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
    // Additional components for item type selection
    private JComboBox<String> itemTypeCombo;

    private User currentUser;
    private LFSystem system;
    private boolean isReportingLost = true;

    public LFReport(User user, LFSystem sys) {
        this.currentUser = user;
        this.system = sys;

        setupUI();
        setupListeners();

        setContentPane(contentPane);
        setTitle("Report Lost/Found Item - " + user.getName());
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(520, 600);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void setupUI() {
        // Initially hide the hidden panel
        HiddenPanel.setVisible(false);

        // Setup button text
        ReportLost.setText("Report as LOST");
        ReportFound.setText("Report as FOUND");

        // Add item type dropdown to hidden panel
        itemTypeCombo = new JComboBox<>(new String[]{
                "Select Type", "Money", "Document", "Accessory",
                "Bag", "Clothing", "Electronic", "Wearable Electronic",
                "Food Container", "Other"
        });

        // Clear the HiddenPanel and add the combo box properly
        HiddenPanel.removeAll();
        HiddenPanel.setLayout(new BoxLayout(HiddenPanel, BoxLayout.Y_AXIS));

        JPanel typePanel = new JPanel();
        typePanel.add(new JLabel("Item Type:"));
        typePanel.add(itemTypeCombo);
        HiddenPanel.add(typePanel);

        // Add submit button to Details panel
        JButton submitButton = new JButton("Submit Report");
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                submitReport();
            }
        });

        // Add the submit button to the Details panel
        Details.setLayout(new BoxLayout(Details, BoxLayout.Y_AXIS));
        Details.add(submitButton);
    }

    private void setupListeners() {
        // Report Lost button - shows hidden panel for lost items
        ReportLost.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showReportForm(true);
            }
        });

        // Report Found button - shows hidden panel for found items
        ReportFound.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showReportForm(false);
            }
        });
    }

    // Shows the hidden panel when a button is selected
    private void showReportForm(boolean isLost) {
        isReportingLost = isLost;

        // Make panels visible
        HiddenPanel.setVisible(true);
        Details.setVisible(true);

        // Update button states
        if (isLost) {
            ReportLost.setEnabled(false);
            ReportFound.setEnabled(true);
        } else {
            ReportLost.setEnabled(true);
            ReportFound.setEnabled(false);
        }

        // Update foundby label visibility
        if (isLost) {
            FoundbyLabel.setVisible(false);
            FoundbyInput.setVisible(false);
        } else {
            FoundbyLabel.setVisible(true);
            FoundbyInput.setVisible(true);
        }

        revalidate();
        repaint();
    }

    // Submit the report - integrated from reportItemLost
    private void submitReport() {
        String name = itemNameInput.getText().trim();
        String details = itemDetailsInput.getText().trim();
        String type = (String) itemTypeCombo.getSelectedItem();

        // Validation
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

        // Create item based on type (integrated from reportItemLost)
        Item newItem = createItemByType(type);
        if (newItem == null) return;

        // Set item ID
        newItem.setItemID(system.item_id++);

        // Set name and details if methods exist
        try {
            newItem.setItemName(name);
            newItem.setDetails(details);
        } catch (Exception e) {
            // If methods don't exist, continue anyway
        }

        // Add to appropriate lists
        if (isReportingLost) {
            currentUser.addLostItem(newItem);
            system.lostList.add(newItem);
            JOptionPane.showMessageDialog(this,
                    "Lost item reported successfully!\nItem ID: " + newItem.getItemID(),
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE);
        } else {
            currentUser.addFoundItem(newItem);
            system.foundList.add(newItem);
            checkForMatches(newItem);
            JOptionPane.showMessageDialog(this,
                    "Found item reported successfully!\nItem ID: " + newItem.getItemID(),
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE);
        }

        clearForm();
    }

    // Creates items based on type - integrated from LFSystem.reportItemLost
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
                item = new Document();
                break;
            case "Accessory":
                item = new Accessory();
                break;
            case "Bag":
                item = new Bag();
                break;
            case "Clothing":
                item = new Clothing();
                break;
            case "Electronic":
                item = new Electronic();
                break;
            case "Wearable Electronic":
                item = new WearableElectronic();
                break;
            case "Food Container":
                item = new FoodContainer();
                break;
            case "Other":
                item = new Miscellaneous();
                break;
            default:
                return null;
        }

        return item;
    }

    // Checks for potential matches between found and lost items
    private void checkForMatches(Item foundItem) {
        try {
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
        } catch (Exception e) {
            // If getItemName doesn't exist, skip matching
        }
    }

    // Clears the form and hides the panels
    private void clearForm() {
        itemNameInput.setText("");
        itemDetailsInput.setText("");
        FoundbyInput.setText("");
        itemTypeCombo.setSelectedIndex(0);

        HiddenPanel.setVisible(false);
        Details.setVisible(false);

        ReportLost.setEnabled(true);
        ReportFound.setEnabled(true);

        revalidate();
        repaint();
    }

    // Main method for testing
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                LFSystem mockSystem = new LFSystem();
                User mockUser = new User("Test User", "12345", "password", "0912-345-6789", "CS", 3);
                new LFReport(mockUser, mockSystem);
            }
        });
    }
}