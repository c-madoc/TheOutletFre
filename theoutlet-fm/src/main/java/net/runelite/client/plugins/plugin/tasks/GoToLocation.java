package net.runelite.client.plugins.plugin.tasks;

import lombok.extern.slf4j.Slf4j;
import net.runelite.api.ItemID;
import net.runelite.api.Player;
import net.runelite.api.coords.WorldArea;
import net.runelite.api.coords.WorldPoint;
import net.runelite.client.plugins.iutils.game.InventoryItem;
import net.runelite.client.plugins.iutils.scene.Area;
import net.runelite.client.plugins.iutils.scene.Position;
import net.runelite.client.plugins.plugin.TOFiremakerPlugin;
import net.runelite.client.plugins.plugin.Task;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Slf4j
public class GoToLocation extends Task {

    @Override
    public boolean validate() {
        return !TOFiremakerPlugin.lightLogs;
    }

    private void walkToLocation() {
        Player local = game.client.getLocalPlayer();
        WorldPoint spotOne = TOFiremakerPlugin.taskConfig.location().getSpotOne();
        WorldPoint spotTwo = TOFiremakerPlugin.taskConfig.location().getSpotTwo();

        if (local == null) {
            return;
        }

        if (local.getWorldLocation().equals(spotOne)
            || local.getWorldLocation().equals(spotTwo)) {
            TOFiremakerPlugin.lightLogs = true;
            return;
        }

        if (TOFiremakerPlugin.lightSpotOne) {
            game.walkUtils.sceneWalk(spotOne, 0, 0);
        } else {
            game.walkUtils.sceneWalk(spotTwo, 0, 0);
        }
    }

    @Override
    public void run() {
        game.tick(1);
        if (!game.localPlayer().isIdle()) {
            return;
        }
        walkToLocation();
    }
}
