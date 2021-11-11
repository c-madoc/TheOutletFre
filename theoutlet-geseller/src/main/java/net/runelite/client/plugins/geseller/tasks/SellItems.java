package net.runelite.client.plugins.geseller.tasks;

import lombok.extern.slf4j.Slf4j;
import net.runelite.api.ItemID;
import net.runelite.api.widgets.WidgetInfo;
import net.runelite.client.plugins.iutils.game.InventoryItem;
import net.runelite.client.plugins.iutils.game.ItemQuantity;
import net.runelite.client.plugins.geseller.Task;
import net.runelite.client.plugins.geseller.GESellerPlugin;

import java.util.List;
import java.util.Random;

@Slf4j
public class SellItems extends Task {

    @Override
    public boolean validate() {
        return true;
    }


    @Override
    public void run() {
        game.tick(1);
    }
}
