package dojomanager;

import dojomanager.gui.MainFrame;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

/**
 * Entry point for the Dojo Manager application.
 * Run this class in NetBeans to start the program.
 */
public class DojoManagerApp {

    public static void main(String[] args) {
        // Use a nicer look-and-feel if available
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {
            // Keep default look-and-feel
        }

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                MainFrame frame = new MainFrame();
                frame.setVisible(true);
            }
        });
    }
}
