import javax.swing.*;
import java.util.*;

public static void main(String[] args) {
    SwingUtilities.invokeLater(new Runnable() {
        @Override
        public void run() {
            //new LFForm();
        }
    });

    //test program
    LFSystem system = new LFSystem();
    system.runSystem();


}