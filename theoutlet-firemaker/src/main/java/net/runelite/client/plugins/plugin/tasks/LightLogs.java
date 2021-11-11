package net.runelite.client.plugins.plugin.tasks;

import lombok.extern.slf4j.Slf4j;
import net.runelite.api.ItemID;
import net.runelite.api.Tile;
import net.runelite.api.coords.WorldArea;
import net.runelite.api.coords.WorldPoint;
import net.runelite.client.plugins.iutils.game.InventoryItem;
import net.runelite.client.plugins.iutils.game.iObject;
import net.runelite.client.plugins.iutils.scene.Position;
import net.runelite.client.plugins.plugin.FiremakerPlugin;
import net.runelite.client.plugins.plugin.Task;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Slf4j
public class LightLogs extends Task {

    @Override
    public boolean validate() {
        return game.inventory().withName("Tinderbox").exists()
                && game.inventory().withId(FiremakerPlugin.taskConfig.logType().getItemId()).exists()
                && FiremakerPlugin.lightLogs;
    }

    private void standingOnObject() {
        if (game.localPlayer().position().equals(game.objects().nearest().position()) && !game.objects().nearest().name().equals("null")
                || (game.objects().withName("Fire").exists() && game.localPlayer().position().equals(game.objects().withName("Fire").nearest().position()))){
            if (game.client.getLocalPlayer() == null) {
                return;
            }

            WorldPoint localPos = game.client.getLocalPlayer().getWorldLocation();
            WorldPoint randPoint = getRandPoint(localPos, 3);
            game.walkUtils.sceneWalk(randPoint, 0, 0);
        };
    }

    public WorldPoint getRandPoint(WorldPoint sourcePoint, int randRadius) {
        if (randRadius <= 0) {
            return sourcePoint;
        }
        WorldArea sourceArea = new WorldArea(sourcePoint, 1, 1);
        WorldArea possibleArea = new WorldArea(
                new WorldPoint(sourcePoint.getX() - randRadius, sourcePoint.getY() - randRadius, sourcePoint.getPlane()),
                new WorldPoint(sourcePoint.getX() + randRadius, sourcePoint.getY() + randRadius, sourcePoint.getPlane())
        );
        List<WorldPoint> possiblePoints = possibleArea.toWorldPointList();
        List<WorldPoint> losPoints = new ArrayList<>();
        losPoints.add(sourcePoint);
        for (WorldPoint point : possiblePoints) {
            if (sourceArea.hasLineOfSightTo(game.client, point)) {
                if (game.objects().withPosition(new Position(point)).anyMatch(x -> x.name() == null && !x.name().equals("null"))) {
                    continue;
                }
                losPoints.add(point);
            }
        }
        Random rand = new Random();
        return losPoints.get(rand.nextInt(losPoints.size()));
    }

    private void lightFire() {
        InventoryItem lightLog = game.inventory().withId(FiremakerPlugin.taskConfig.logType().getItemId()).first();
        InventoryItem tinderBox = game.inventory().withId(ItemID.TINDERBOX).first();
        if (lightLog == null) {
            return;
        }
        lightLog.useOn(tinderBox);
    }


    @Override
    public void run() {
        game.tick(1);
        if (!game.localPlayer().isIdle()) {
            return;
        }
        standingOnObject();
        lightFire();
    }
}
