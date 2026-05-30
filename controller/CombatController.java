package org.minicraft02160.controller;

import org.minicraft02160.model.EquipableItem;
import org.minicraft02160.model.Player;
import org.minicraft02160.model.extras.AttackResult;
import org.minicraft02160.model.extras.CombatResult;

// manages Interaction with mob, and player
public class CombatController {

    private final MobSpawner spawner;
    private final Player player;
    private final MobDeathHandler deathHandler;

    public CombatController(MobSpawner spawner,
                            Player player,
                            MobDeathHandler deathHandler) {
        this.spawner = spawner;
        this.player = player;
        this.deathHandler = deathHandler;
    }

    // Attacking mob at spec. position, check if player input is valid
    public AttackResult playerAttackMob(int targetX, int targetY) {
        EquipableItem mainHand = player.getEquippedMainHand();
        if (mainHand == null) return AttackResult.NO_WEAPON;

        MobController target = findMobAt(targetX, targetY);
        if (target == null) return AttackResult.NO_TARGET;

        int damage = Math.max(1, mainHand.getDamage());
        target.takeDamage(damage);

        // manages action for tools
        mainHand.reduceDurability(1);
        if (mainHand.isBroken()) {
            player.unequipMainHand();
        }

        // Manages Mob death
        if (!target.isAlive()) {
            spawner.defeatMob(target);
            deathHandler.onMobDeath(target, player);
            return AttackResult.MOB_KILLED;
        }

        return AttackResult.MOB_HIT; // if the mob survived one hit
    }


    private MobController findMobAt(int x, int y) {
        for (MobController mob : spawner.getSpawnedMobs()) {
            if (mob.isAlive() && mob.getX() == x && mob.getY() == y) {
                return mob;
            }
        }
        return null;
    }

    //manages attacking mobs, mob has to be adjacent to player
    public CombatResult checkCombat() {
        for (MobController mob : spawner.getSpawnedMobs()) {
            if (!mob.isAlive()) continue;
            if (mob.isAdjacentToPlayer(player)) {
                int damage = mob.attackPlayer();
                if (damage > 0) {
                    player.takeDamage(damage);
                    if (!player.isAlive()) return CombatResult.PLAYER_DIED;
                }
            }
        }
        return CombatResult.PLAYER_ALIVE;
    }
}