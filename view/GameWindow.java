package org.minicraft02160.view;

import javax.swing.*;
import java.awt.*;

public class GameWindow {

    private final JFrame frame;

    public GameWindow(String title, World world) {
        frame = new JFrame(title);

        JPanel glassPane = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                setOpaque(false);
                CraftingMenu menu = world.getCraftingMenu();
                if (menu != null && menu.isOpen()) {
                    menu.render(g);
                }
            }
        };
        glassPane.setOpaque(false);
        frame.setGlassPane(glassPane);
        glassPane.setVisible(true);
        world.setGlassPane(glassPane);

        frame.add(world);
        frame.setSize(900, 700);
        frame.pack();
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void show() {
        frame.setVisible(true);
    }

    public JFrame getFrame() {
        return frame;
    }
}

