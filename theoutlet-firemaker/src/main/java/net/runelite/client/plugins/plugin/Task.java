package net.runelite.client.plugins.plugin;

import lombok.extern.slf4j.Slf4j;
import net.runelite.api.ItemID;
import net.runelite.client.plugins.iutils.game.ItemQuantity;
import net.runelite.client.plugins.iutils.scene.RectangularArea;
import net.runelite.client.plugins.iutils.scripts.UtilsScript;

import java.util.List;

@Slf4j
public abstract class Task extends UtilsScript {

    public Task()
    {
    }
    public static String status = "starting...";

    public abstract boolean validate();

    public String getTaskDescription() {
        return this.getClass().getSimpleName();
    }

    public abstract void run();
}
