package net.runelite.client.plugins.varrocksmither.tasks;

import lombok.extern.slf4j.Slf4j;
import net.runelite.api.ItemID;
import net.runelite.api.widgets.WidgetInfo;
import net.runelite.client.plugins.varrocksmither.Task;

@Slf4j
public class SmithItems extends Task {

    @Override
    public boolean validate() {
        return game.inventory().withId(ItemID.HAMMER).quantity() == 1
                && !outOfSupplies();
    }

    @Override
    public void run() {
        game.tick(1);
        if (game.localPlayer().isIdle()) { // we are idle
            if (!outOfSupplies()) { // we have supplies to smith
                if (game.widget(WidgetInfo.SMITHING_INVENTORY_ITEMS_CONTAINER) == null) { // smithing menu is not visible
                    interactObject(SMITH_AND_BANK_AREA, "Anvil", "Smith");
                } else { // smithing menu is visible
                    game.widget(312, itemWidgetChildId).interact(smithType);
                }
            }
        }
    }
}

