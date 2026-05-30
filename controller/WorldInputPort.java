package org.minicraft02160.controller;

import java.awt.event.KeyEvent;

public interface WorldInputPort {
    boolean isInputLocked();
    void handleInteractionKey();
    void handleTileActionKey();
    boolean handleMenuKey(KeyEvent e);
    void handleMoveKey(int dx, int dy);
}
