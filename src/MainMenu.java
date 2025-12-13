import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;

public class MainMenu extends JPanel {

    // Define School Colors
    private static final Color CIT_MAROON = new Color(109, 21, 29);
    private static final Color CIT_GOLD = new Color(242, 203, 5);
    private static final Font HEADER_FONT = new Font("Serif", Font.BOLD, 24);
    private static final Font UI_FONT = new Font("SansSerif", Font.BOLD, 12);

    public MainMenu() {
        // 1. Set Layout for the WHOLE panel
        this.setLayout(new BorderLayout());
        this.setBackground(Color.WHITE);

        // 2. Create the scrolling container
        // We use a vertical BoxLayout so items stack top-to-bottom
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBackground(Color.WHITE);

        // 3. Add Sections
        contentPanel.add(createHeader());
        contentPanel.add(createNavBar());
        contentPanel.add(createUserInfo());
        contentPanel.add(createSectionTitle("LOST ITEMS"));
        contentPanel.add(createItemGrid()); // This is your array of panels

        // 4. Wrap the content in a ScrollPane
        JScrollPane scrollPane = new JScrollPane(contentPanel);
        scrollPane.setBorder(null); // Remove default border
        scrollPane.getVerticalScrollBar().setUnitIncrement(16); // Make scrolling faster

        // 5. Add ScrollPane to this MainMenu Panel
        this.add(scrollPane, BorderLayout.CENTER);
    }

    // --- HELPER METHODS TO BUILD UI ---

    private JPanel createHeader() {
        JPanel header = new JPanel(new GridBagLayout());
        header.setPreferredSize(new Dimension(450, 100));
        header.setBackground(CIT_MAROON);

        // This stops the header from shrinking if the window gets small
        header.setMaximumSize(new Dimension(Integer.MAX_VALUE, 120));

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
        nav.setMaximumSize(new Dimension(Integer.MAX_VALUE, 60));

        nav.add(createStyledButton("Lost Items", CIT_GOLD, Color.BLACK));
        nav.add(createStyledButton("Find Item", CIT_MAROON, Color.WHITE));
        nav.add(createStyledButton("Profile", CIT_MAROON, Color.WHITE));

        return nav;
    }

    private JButton createStyledButton(String text, Color bg, Color fg) {
        JButton btn = new JButton(text);
        btn.setBackground(bg);
        btn.setForeground(fg);
        btn.setFocusPainted(false);
        btn.setFont(UI_FONT);
        return btn;
    }

    private JPanel createUserInfo() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(new EmptyBorder(10, 20, 10, 20));
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));

        JLabel welcome = new JLabel("Welcome, Student! (12-3456)");
        JLabel logout = new JLabel("Logout");
        logout.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)); // Make it look clickable

        panel.add(welcome, BorderLayout.WEST);
        panel.add(logout, BorderLayout.EAST);
        return panel;
    }

    private JPanel createSectionTitle(String titleText) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(Color.WHITE);
        panel.setBorder(new EmptyBorder(10, 20, 20, 20));
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 60));

        JLabel title = new JLabel(titleText);
        title.setFont(new Font("Serif", Font.BOLD, 18));
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
        // GridLayout(0, 2) = Infinite rows, 2 columns
        JPanel grid = new JPanel(new GridLayout(0, 2, 15, 15));
        grid.setBackground(Color.WHITE);
        grid.setBorder(new EmptyBorder(0, 20, 20, 20));

        // Add dummy data (Replace these URLs with your local file paths later)
        grid.add(new ItemCard("Charlie Kirk", "Salt Shakers", "Found at canteen", "https://m.media-amazon.com/images/I/61y+E1-Qo-L._AC_SL1500_.jpg"));
        grid.add(new ItemCard("Sailor Moon", "Magic Wand", "Pink and shiny", "https://static.wikia.nocookie.net/sailormoon/images/5/52/Spiral_Heart_Moon_Rod_toy_2002.jpg"));
        grid.add(new ItemCard("Kira", "Death Note", "Dangerous notebook", "https://m.media-amazon.com/images/I/61y-gA+-tAL.jpg"));
        grid.add(new ItemCard("Ash", "Pikachu", "Yellow mouse", "https://i.pinimg.com/736x/2f/61/8c/2f618c6422234057eaad1d6df48074d2.jpg"));

        return grid;
    }

    // --- TESTING METHOD (Delete this before submitting if you want) ---
    public static void main(String[] args) {
        JFrame frame = new JFrame("Test Preview");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(450, 800);

        // Add our MainMenu panel to the frame
        frame.add(new MainMenu());

        frame.setVisible(true);
    }

    // --- INNER CLASS FOR THE CARDS ---
    static class ItemCard extends JPanel {
        public ItemCard(String username, String title, String desc, String imgUrl) {
            setLayout(new BorderLayout());
            setBackground(Color.WHITE);
            setBorder(new EmptyBorder(0, 0, 10, 0));

            // Text Panel
            JPanel textPanel = new JPanel();
            textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));
            textPanel.setBackground(Color.WHITE);

            JLabel uLbl = new JLabel(username);
            uLbl.setForeground(Color.GRAY);
            uLbl.setFont(new Font("SansSerif", Font.PLAIN, 10));
            JLabel tLbl = new JLabel(title);
            tLbl.setFont(new Font("SansSerif", Font.BOLD, 12));
            JLabel dLbl = new JLabel(desc);
            dLbl.setFont(new Font("SansSerif", Font.PLAIN, 10));

            textPanel.add(uLbl);
            textPanel.add(tLbl);
            textPanel.add(dLbl);
            textPanel.add(Box.createVerticalStrut(5));
            add(textPanel, BorderLayout.NORTH);

            // Image
            JLabel imgLabel = new JLabel("Loading...");
            imgLabel.setHorizontalAlignment(SwingConstants.CENTER);
            imgLabel.setBorder(new LineBorder(Color.BLACK, 2));
            imgLabel.setPreferredSize(new Dimension(150, 150));

            // Simple Image Loader Thread so UI doesn't freeze
            new Thread(() -> {
                try {
                    URL url = new URL(imgUrl);
                    BufferedImage img = ImageIO.read(url);
                    if (img != null) {
                        Image scaled = img.getScaledInstance(150, 150, Image.SCALE_SMOOTH);
                        ImageIcon icon = new ImageIcon(scaled);
                        SwingUtilities.invokeLater(() -> imgLabel.setIcon(icon));
                        SwingUtilities.invokeLater(() -> imgLabel.setText(""));
                    }
                } catch (Exception e) {
                    SwingUtilities.invokeLater(() -> imgLabel.setText("No Image"));
                }
            }).start();

            add(imgLabel, BorderLayout.CENTER);
        }
    }
}