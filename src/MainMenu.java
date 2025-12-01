import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainMenu extends JFrame{
    private JButton ReportMissingItemBtn;
    private JButton FoundItemBtn;
    private JButton ExitBtn;
    private JLabel MainMenu;
    private JPanel JPanel;


    public MainMenu(){
        setContentPane(JPanel);
        setTitle("Lost and Found Management System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800,600);
        setLocationRelativeTo(null);

        ReportMissingItemBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(MainMenu.this, "Report Missing Item");
            }
        });
        FoundItemBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(MainMenu.this, "Click");
            }
        });
        ExitBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}
