import ErrorPack.IDNotFound;
import ErrorPack.IncorrectPassword;
import ErrorPack.InvalidIDFormat;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.Objects;

public class LFForm extends JFrame {
    private JPanel mainPanel;
    private JPanel loginPage;
    private JButton selectRegisterButton;
    private JPanel holderPanel;
    private JButton selectLoginButton;
    private JPanel inputDetails;
    private JPanel idNumberPanel;
    private JPanel passwordPanel;
    private JLabel titleSelected;
    private JButton finalButton;
    private JTextField inputPassword;
    private JTextField inputID;
    private JButton redirectToOther;

    public static void goToLogin(JPanel holderPanel, JPanel inputDetails, JLabel titleSelected, JButton finalButton, JButton redirectToOther) {
        holderPanel.setVisible(false);
        inputDetails.setVisible(true);
        titleSelected.setText("Login");
        finalButton.setText("Login");
        redirectToOther.setText("Register?");
    }
    public static void goToRegister(JPanel holderPanel, JPanel inputDetails, JLabel titleSelected, JButton finalButton, JButton redirectToOther){
        holderPanel.setVisible(false);
        inputDetails.setVisible(true);
        titleSelected.setText("Register");
        finalButton.setText("Register");
        redirectToOther.setText("Login?");
    }

    //Find user's inputted IDNumber if it exists and return password to be used in validating
    public static String findIDNumber(String idNumber){
        String[] currentIDNumber;
        String curr;
        try(BufferedReader br = new BufferedReader(new FileReader("records.csv"))){
            while((curr = br.readLine()) != null){
                currentIDNumber = curr.split(",");
                if(currentIDNumber[0].equals(idNumber)){
                    return currentIDNumber[1];
                }
            }
        } catch(IOException e){
            //do nothing
        }
        return "";
    }

    //Record new registered account using this function
    public static void recordAccount(String IDNumber, String password) throws InvalidIDFormat{
        try(BufferedWriter bw = new BufferedWriter(new FileWriter("records.csv",true))){
            if(!IDNumber.matches("^\\d{2}-\\d{4}-\\d{3}$")){
                throw new InvalidIDFormat();
            }
            bw.write(IDNumber+","+password);
            bw.newLine();
        } catch(IOException e){
            //do nothing
        }
    }

    public LFForm() {
        //hide other panels not in use
        inputDetails.setVisible(false);

        //add functionality to buttons
        //Selecting Login Button Pressed
        selectLoginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                goToLogin(holderPanel, inputDetails, titleSelected, finalButton, redirectToOther);
            }
        });

        //Selecting Register Button Pressed
        selectRegisterButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                goToRegister(holderPanel, inputDetails, titleSelected, finalButton, redirectToOther);
            }
        });

        redirectToOther.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(titleSelected.getText().equals("Login")){
                    goToRegister(holderPanel, inputDetails, titleSelected, finalButton, redirectToOther);
                } else if(titleSelected.getText().equals("Register")){
                    goToLogin(holderPanel, inputDetails, titleSelected, finalButton, redirectToOther);
                }
            }
        });

        //LOGGING IN OR REGISTERING
        finalButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    String onRecord = findIDNumber(inputID.getText());
                    switch (finalButton.getText()) {
                        //LOGGING IN WITH USERNAME AND PASSWORD
                        case "Login":
                            if (!Objects.equals(onRecord, "")) {
                                if (Objects.equals(inputPassword.getText(), onRecord)) {
                                    JOptionPane.showMessageDialog(null, "Login Successful"); //temporary
                                } else {
                                    throw new IncorrectPassword();
                                }
                            } else {
                                throw new IDNotFound();
                            }
                            break;
                        //REGISTERING NEW ACCOUNT
                        case "Register":
                            recordAccount(inputID.getText(), inputPassword.getText());
                            break;
                        default:
                            JOptionPane.showMessageDialog(null, "Error. Redirecting to Login and Registration page.");
                    }
                } catch (IDNotFound | IncorrectPassword | InvalidIDFormat f) {
                    JOptionPane.showMessageDialog(null, f.getMessage());
                } finally {
                    inputID.setText("");
                    inputPassword.setText("");
                }
            }
        });

        finalButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        //setup Panel
        setContentPane(mainPanel);
        setTitle("Lost and Found Management System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(520, 880);
        setLocationRelativeTo(null);
        setVisible(true);
        redirectToOther.setOpaque(false);
        redirectToOther.setContentAreaFilled(false);
        redirectToOther.setBorderPainted(false);
    }

}
