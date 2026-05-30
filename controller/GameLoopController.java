package org.minicraft02160.controller;

import org.minicraft02160.model.extras.CombatResult;
import org.minicraft02160.Minicraft;
import org.minicraft02160.model.Player;
import org.minicraft02160.view.World;
import org.minicraft02160.view.GameWindow;

import javax.swing.*;

public class GameLoopController {

    private final Timer gameTimer;
    private int gameSeconds = 0;
    private final GameWindow window;

    public GameLoopController(World world, Player player, MobSpawner spawner, CombatController combatController, GameWindow window) {
        this.window = window;
        // Main game loop that runs every 1sec
        this.gameTimer = new Timer(1000, e -> {
            gameSeconds++;

            spawner.advanceTime(1);
            spawner.updateAllMobs(player);

            // Check if nearby mobs attack player
            if (combatController.checkCombat() == CombatResult.PLAYER_DIED) {
                ((Timer) e.getSource()).stop();
                SwingUtilities.invokeLater(() -> {
                    String[] options = {"Respawn", "Close"};
                    int choice = JOptionPane.showOptionDialog(
                            window.getFrame(),
                            "💀 Game Over!\nYou were defeated!",
                            "Game Over",
                            JOptionPane.DEFAULT_OPTION,
                            JOptionPane.ERROR_MESSAGE,
                            null,
                            options,
                            options[0]
                    );
                    // restart game if respawn is choosen
                    if (choice == 0) {
                        window.getFrame().dispose();
                        Minicraft.restartGame();
                    } else {
                        window.getFrame().dispose();
                        System.exit(0);
                    }
                });

                return;
            }

            world.tick();
            world.repaint();
            world.repaintOverlay();

            if (gameSeconds % 10 == 0) {
                System.out.println("⏱ Time: " + gameSeconds + "s"
                        + " | Mobs alive: " + spawner.getTotalMobCount()
                        + " | Player HP: "  + player.getLives());
            }
        });
    }

    public void start() {
        gameTimer.start();
        System.out.println("🎮 Game started!");
        System.out.println("⏳ First mob spawns in 30 seconds...");
    }

}

