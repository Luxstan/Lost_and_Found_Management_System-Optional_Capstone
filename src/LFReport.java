import ItemPack.*;
import javax.swing.*;
import java.awt.*;


//Im gonna need to redo this, I understand that it works but I dont understand how it is.
//Gonna redo when I have better understanding.

public class LFReport extends JFrame {
    // LFReport Private variables
    private final User currentUser;
    private final LFSystem system;

    private JButton FoundReportBTN;
    private JButton LostReportButton;
    private JPanel HiddenPanel;
    private JPanel MainPanel;
    private JPanel ReportPanel;
    private JTextField itemName;
    private JTextArea Details;
    private JComboBox<String> itemTypeCombo;
    private boolean isReportingLost = true;

    public LFReport(User user, LFSystem sys) {
        this.currentUser = user;
        this.system = sys;

        setupUI();
        setTitle("Report Lost/Found Item - " + user.getName());
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(500, 450);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void setupUI() {
        JPanel container = new JPanel(new BorderLayout());

        // Main Panel
        MainPanel = new JPanel();
        MainPanel.setLayout(new BoxLayout(MainPanel, BoxLayout.Y_AXIS));
        MainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20));

        JLabel titleLabel = new JLabel("Report an Item");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        MainPanel.add(titleLabel);
        MainPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        container.add(MainPanel, BorderLayout.NORTH);

        // Hidden Panel
        HiddenPanel = new JPanel();
        HiddenPanel.setLayout(new BoxLayout(HiddenPanel, BoxLayout.Y_AXIS));
        HiddenPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        HiddenPanel.setVisible(false);

        // Item Type dropdown
        JPanel typePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        typePanel.add(new JLabel("Item Type:"));
        String[] itemTypes = {
                "Select Type", "Money", "Document", "Accessory",
                "Bag", "Clothing", "Electronic", "Wearable Electronic",
                "Food Container", "Other"
        };
        itemTypeCombo = new JComboBox<>(itemTypes);
        itemTypeCombo.setPreferredSize(new Dimension(300, 30));
        typePanel.add(itemTypeCombo);
        typePanel.setMaximumSize(new Dimension(450, 40));
        HiddenPanel.add(typePanel);
        HiddenPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        // Item Name
        JPanel namePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        namePanel.add(new JLabel("Item Name:"));
        itemName = new JTextField(25);
        namePanel.add(itemName);
        namePanel.setMaximumSize(new Dimension(450, 40));
        HiddenPanel.add(namePanel);
        HiddenPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        // Details
        JPanel detailsPanel = new JPanel(new BorderLayout());
        detailsPanel.add(new JLabel("Details:"), BorderLayout.NORTH);
        Details = new JTextArea(5, 30);
        Details.setLineWrap(true);
        Details.setWrapStyleWord(true);
        JScrollPane scrollPane = new JScrollPane(Details);
        detailsPanel.add(scrollPane, BorderLayout.CENTER);
        detailsPanel.setMaximumSize(new Dimension(450, 120));
        HiddenPanel.add(detailsPanel);
        HiddenPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        // Submit button
        JButton submitBtn = new JButton("Submit Report");
        submitBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        submitBtn.addActionListener(e -> submitReport(isReportingLost));
        HiddenPanel.add(submitBtn);

        container.add(HiddenPanel, BorderLayout.CENTER);

        // Report Panel
        ReportPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        ReportPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 20, 20));

        LostReportButton = new JButton("Report as LOST");
        LostReportButton.addActionListener(e -> showReportForm(true));

        FoundReportBTN = new JButton("Report as FOUND");
        FoundReportBTN.addActionListener(e -> showReportForm(false));

        ReportPanel.add(LostReportButton);
        ReportPanel.add(FoundReportBTN);

        container.add(ReportPanel, BorderLayout.SOUTH);

        setContentPane(container);
    }

    private void showReportForm(boolean isLost) {
        isReportingLost = isLost;
        HiddenPanel.setVisible(true);

        if (isLost) {
            LostReportButton.setEnabled(false);
            FoundReportBTN.setEnabled(true);
        } else {
            LostReportButton.setEnabled(true);
            FoundReportBTN.setEnabled(false);
        }

        revalidate();
        repaint();
    }

    private void submitReport(boolean isLost) {
        String name = itemName.getText().trim();
        String details = Details.getText().trim();
        String type = (String) itemTypeCombo.getSelectedItem();

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

        if (isLost) {
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

    private Item createItemByType(String type) {
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
                    return new Money(amount, wallet == JOptionPane.YES_OPTION);
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(this, "Invalid amount!");
                    return null;
                }
            case "Document":
                return new Document();
            case "Accessory":
                return new Accessory();
            case "Bag":
                return new Bag();
            case "Clothing":
                return new Clothing();
            case "Electronic":
                return new Electronic();
            case "Wearable Electronic":
                return new WearableElectronic();
            case "Food Container":
                return new FoodContainer();
            case "Other":
                return new Miscellaneous();
            default:
                return null;
        }
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

    private void clearForm() {
        itemName.setText("");
        Details.setText("");
        itemTypeCombo.setSelectedIndex(0);
        HiddenPanel.setVisible(false);
        LostReportButton.setEnabled(true);
        FoundReportBTN.setEnabled(true);
        revalidate();
        repaint();
    }

    public String getItemName() {
        return itemName.getText();
    }

    public String getDetails() {
        return Details.getText();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            LFSystem mockSystem = new LFSystem();
            User mockUser = new User("Test User", "12345", "password", "0912-345-6789", "CS", 3);
            new LFReport(mockUser, mockSystem);
        });
    }
}