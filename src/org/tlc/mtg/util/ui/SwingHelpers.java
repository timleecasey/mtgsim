package org.tlc.mtg.util.ui;

import javax.swing.*;
import java.awt.*;


/**
 */
public class SwingHelpers {

    /**
     * Show a frame centered, taking up 90% of the space.
     * @param jc the component to show
     */
    public static void showInFrame(JComponent jc) {
        JFrame frame = new JFrame();
        Dimension rootDim = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setSize(new Dimension(rootDim.width * 9 / 10, rootDim.height * 9 / 10));
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setContentPane(jc);
        frame.setVisible(true);
    }
}
