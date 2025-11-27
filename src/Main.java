import javax.swing.*;

public static void main(String[] args) {
    SwingUtilities.invokeLater(new Runnable() {
        @Override
        public void run() {
            new LFForm();
        }
    });
}