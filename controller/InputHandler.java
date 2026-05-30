package org.minicraft02160.controller;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class InputHandler {

    private final JComponent inputTarget;
    private final WorldInputPort worldInput;

    public InputHandler(JComponent inputTarget, WorldInputPort worldInput) {
        this.inputTarget = inputTarget;
        this.worldInput = worldInput;
    }

    public void install() {
        inputTarget.setFocusable(true); // needed for component to receive keyboard events
        addListeners();
        inputTarget.requestFocusInWindow();
    }

    private void addListeners() {
        inputTarget.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (worldInput.isInputLocked()) {
                    return;
                    // prevents input when transitioning and when menu is open
                }
                if (e.getKeyCode() == KeyEvent.VK_E) {
                    worldInput.handleInteractionKey();
                    return;
                }
                if (e.getKeyCode() == KeyEvent.VK_Q) {
                    worldInput.handleTileActionKey();
                    return;
                }

                if (worldInput.handleMenuKey(e)) {
                    return;
                    // When menu opened, prevents movement of other actions
                }

                int dx = 0;
                int dy = 0;

                // Allowing  keyCode and keyChar as inpu
                if (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyChar() == 'w') {
                    dy = -1;
                } else if (e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyChar() == 's') {
                    dy = 1;
                } else if (e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyChar() == 'a') {
                    dx = -1;
                } else if (e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyChar() == 'd') {
                    dx = 1;
                }

                // movement only when valid direction was set
                if (dx != 0 || dy != 0) {
                    worldInput.handleMoveKey(dx, dy);
                }
            }
        });
    }
}
