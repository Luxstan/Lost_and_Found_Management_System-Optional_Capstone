import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;

public class MainMenu {

    // Define School Colors based on the image
    private static final Color CIT_MAROON = new Color(109, 21, 29); // Approximate Maroon
    private static final Color CIT_GOLD = new Color(242, 203, 5);   // Approximate Gold/Yellow
    private static final Font HEADER_FONT = new Font("Serif", Font.BOLD, 24);
    private static final Font SUBHEADER_FONT = new Font("Serif", Font.BOLD, 18);
    private static final Font UI_FONT = new Font("SansSerif", Font.BOLD, 12);

    public static void main(String[] args) {
        // Run UI in the Event Dispatch Thread
        SwingUtilities.invokeLater(() -> {
            new MainMenu().createAndShowGUI();
        });
    }

    public void createAndShowGUI() {
        JFrame frame = new JFrame("CIT-U Lost and Found");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(450, 800); // Mobile-like aspect ratio
        frame.setLayout(new BorderLayout());

        // 1. SCROLL PANE CONTAINER
        // We put the main content in a scroll pane so you can scroll through many items
        JPanel mainContainer = new JPanel();
        mainContainer.setLayout(new BoxLayout(mainContainer, BoxLayout.Y_AXIS));
        mainContainer.setBackground(Color.WHITE);

        // --- SECTION A: HEADER ---
        JPanel headerPanel = createHeader();
        mainContainer.add(headerPanel);

        // --- SECTION B: NAVIGATION ---
        JPanel navPanel = createNavBar();
        mainContainer.add(navPanel);

        // --- SECTION C: USER INFO ---
        JPanel userInfoPanel = createUserInfo();
        mainContainer.add(userInfoPanel);

        // --- SECTION D: TITLE ---
        JPanel titlePanel = createSectionTitle("LOST ITEMS");
        mainContainer.add(titlePanel);

        // --- SECTION E: ITEM GRID (The "Array of Panels") ---
        JPanel gridPanel = createItemGrid();
        mainContainer.add(gridPanel);

        // Add the container to a ScrollPane
        JScrollPane scrollPane = new JScrollPane(mainContainer);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16); // Smoother scrolling
        frame.add(scrollPane, BorderLayout.CENTER);

        frame.setVisible(true);
    }

    private JPanel createHeader() {
        // A panel attempting to mimic the curved maroon/gold header
        JPanel header = new JPanel();
        header.setPreferredSize(new Dimension(450, 120));
        header.setBackground(CIT_MAROON);
        header.setLayout(new GridBagLayout()); // Center the text

        JLabel titleLabel = new JLabel("<html><center>CEBU INSTITUTE OF TECHNOLOGY<br>UNIVERSITY</center></html>");
        titleLabel.setForeground(CIT_GOLD);
        titleLabel.setFont(HEADER_FONT);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);

        header.add(titleLabel);
        return header;
    }

    private JPanel createNavBar() {
        JPanel nav = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        nav.setBackground(Color.WHITE);

        // Button styling helper
        JButton btnLost = createStyledButton("Lost Items", CIT_GOLD, Color.BLACK);
        JButton btnFind = createStyledButton("Find Item", CIT_MAROON, Color.WHITE);
        JButton btnProfile = createStyledButton("Profile", CIT_MAROON, Color.WHITE);

        nav.add(btnLost);
        nav.add(btnFind);
        nav.add(btnProfile);

        return nav;
    }

    private JButton createStyledButton(String text, Color bg, Color fg) {
        JButton btn = new JButton(text);
        btn.setBackground(bg);
        btn.setForeground(fg);
        btn.setFocusPainted(false);
        btn.setFont(UI_FONT);
        btn.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(bg.darker(), 1),
                new EmptyBorder(8, 15, 8, 15)
        ));
        return btn;
    }

    private JPanel createUserInfo() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(new EmptyBorder(10, 20, 10, 20));

        JLabel welcome = new JLabel("Welcome, [Username]! ([UserID])");
        welcome.setFont(UI_FONT);

        JLabel logout = new JLabel("Logout");
        logout.setFont(UI_FONT);
        logout.setHorizontalAlignment(SwingConstants.RIGHT);

        panel.add(welcome, BorderLayout.WEST);
        panel.add(logout, BorderLayout.EAST);
        return panel;
    }

    private JPanel createSectionTitle(String titleText) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(Color.WHITE);
        panel.setBorder(new EmptyBorder(10, 20, 20, 20));

        JLabel title = new JLabel(titleText);
        title.setFont(SUBHEADER_FONT);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        JSeparator sep = new JSeparator();
        sep.setForeground(Color.BLACK);
        sep.setMaximumSize(new Dimension(400, 2));

        panel.add(title);
        panel.add(Box.createVerticalStrut(5));
        panel.add(sep);

        return panel;
    }

    private JPanel createItemGrid() {
        // This is where we create the "Array of Panels"
        // Using GridLayout(0, 2) means unlimited rows, 2 columns
        JPanel grid = new JPanel(new GridLayout(0, 2, 15, 15));
        grid.setBackground(Color.WHITE);
        grid.setBorder(new EmptyBorder(0, 20, 20, 20));

        // Create the specific items shown in your image
        // Note: I am using web URLs for the images so this code works instantly for you.
        // In your real project, change the URL string to a local file path (e.g., "src/images/salt.jpg").

        List<ItemCard> items = new ArrayList<>();

        items.add(new ItemCard(
                "Charlie Kirk",
                "Salt and Pepper",
                "I lost my salt and pepper fr...",
                "https://m.media-amazon.com/images/I/61y+E1-Qo-L._AC_SL1500_.jpg" // Salt shakers
        ));

        items.add(new ItemCard(
                "[Username]",
                "Title",
                "Description...",
                "https://static.wikia.nocookie.net/sailormoon/images/5/52/Spiral_Heart_Moon_Rod_toy_2002.jpg" // Sailor moon wand
        ));

        items.add(new ItemCard(
                "[Username]",
                "Title",
                "Description...",
                "https://m.media-amazon.com/images/I/61y-gA+-tAL.jpg" // Death note
        ));

        items.add(new ItemCard(
                "[Username]",
                "Title",
                "Description...",
                "https://i.pinimg.com/736x/2f/61/8c/2f618c6422234057eaad1d6df48074d2.jpg" // Pikachu
        ));

        // Loop through array and add to grid
        for (ItemCard card : items) {
            grid.add(card);
        }

        return grid;
    }

    // ==========================================
    // CUSTOM COMPONENT: ItemCard
    // This represents one block in your grid
    // ==========================================
    static class ItemCard extends JPanel {
        private String username;
        private String itemTitle;
        private String description;
        private String imagePath;

        public ItemCard(String username, String itemTitle, String description, String imagePath) {
            this.username = username;
            this.itemTitle = itemTitle;
            this.description = description;
            this.imagePath = imagePath;

            this.setLayout(new BorderLayout());
            this.setBackground(Color.WHITE);
            // Add a little padding inside the card
            this.setBorder(new EmptyBorder(0, 0, 10, 0));

            // 1. Text Info Panel (Top)
            JPanel textPanel = new JPanel();
            textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));
            textPanel.setBackground(Color.WHITE);

            JLabel userLbl = new JLabel(username);
            userLbl.setForeground(Color.GRAY);
            userLbl.setFont(new Font("SansSerif", Font.PLAIN, 10));

            JLabel titleLbl = new JLabel(itemTitle);
            titleLbl.setFont(new Font("SansSerif", Font.BOLD, 12));

            JLabel descLbl = new JLabel(description);
            descLbl.setFont(new Font("SansSerif", Font.PLAIN, 10));

            textPanel.add(userLbl);
            textPanel.add(titleLbl);
            textPanel.add(descLbl);
            textPanel.add(Box.createVerticalStrut(5)); // Space between text and image

            this.add(textPanel, BorderLayout.NORTH);

            // 2. Image Panel (Center)
            JLabel imageLabel = new JLabel();
            imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
            imageLabel.setBorder(new LineBorder(Color.BLACK, 2));
            imageLabel.setPreferredSize(new Dimension(150, 150)); // Fixed square size

            // Load Image logic
            ImageIcon icon = loadImage(imagePath, 150, 150);
            if (icon != null) {
                imageLabel.setIcon(icon);
            } else {
                imageLabel.setText("Img Not Found");
            }

            this.add(imageLabel, BorderLayout.CENTER);
        }

        // Helper to load and resize image
        private ImageIcon loadImage(String path, int w, int h) {
            try {
                // Try loading from URL (for this demo)
                URL url = new URL(path);
                BufferedImage img = ImageIO.read(url);

                // If you are using local files, use this instead:
                // BufferedImage img = ImageIO.read(new File(path));

                if (img != null) {
                    Image scaled = img.getScaledInstance(w, h, Image.SCALE_SMOOTH);
                    return new ImageIcon(scaled);
                }
            } catch (Exception e) {
                // e.printStackTrace(); // Uncomment to debug image loading
            }
            return null;
        }
    }
}