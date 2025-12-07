import ItemPack.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LFReport extends JFrame {

    //Works for now.

    //Header, Subheader, ReportButtons, and the labels are to be
    //Customized for design, for now they do not serve a purpose.


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

    private User currentUser;
    private LFSystem system;


    //The login is not yet integrated to this.

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

    //The report buttons, these are also submission.

    private void setupListeners() {
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
    }


    //Submit report as either lost or found.

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

        if (isReportingLost) {
            currentUser.addLostItem(newItem);
            system.lostList.add(newItem);
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
            checkForMatches(newItem);
            JOptionPane.showMessageDialog(this,
                    "Found item reported successfully!\nItem ID: " + newItem.getItemID(),
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE);
        }

        clearForm();
    }


    //Based on the reportItem on the system file.
    //Will create exceptions regarding these, probably.

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

    private void clearForm() {
        itemNameInput.setText("");
        itemDetailsInput.setText("");
        FoundbyInput.setText("");
        lastFoundInput.setText("");
        ItemTypesCombo.setSelectedIndex(0);

        revalidate();
        repaint();
    }

    //Mock login for now, not yet integrated with the login.

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