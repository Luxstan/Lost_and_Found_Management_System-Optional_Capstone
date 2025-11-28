import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LFForm extends JFrame {
    private JPanel mainPanel;
    private JPanel loginPage;
    private JButton selectRegisterButton;
    private JPanel holderPanel;
    private JButton selectLoginButton;
    private JPanel inputDetails;
    private JTextField textField1;
    private JPanel idNumberPanel;
    private JPanel passwordPanel;
    private JLabel titleSelected;
    private JButton finalButton;

    public LFForm() {
        //hide other panels not in use
        inputDetails.setVisible(false);

        //add functionality to buttons
        //Login Button Pressed
        selectLoginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                holderPanel.setVisible(false);
                inputDetails.setVisible(true);
                titleSelected.setText("Login");
                finalButton.setText("Login");
            }
        });

        //Register Button Pressed
        selectRegisterButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                holderPanel.setVisible(false);
                inputDetails.setVisible(true);
                titleSelected.setText("Register");
                finalButton.setText("Register");
            }
        });

        //setup Panel
        setContentPane(mainPanel);
        setTitle("Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(520, 880);
        setLocationRelativeTo(null);
        setVisible(true);
    }

}
