package net.runelite.client.plugins.varrocksmither.tasks;

import lombok.extern.slf4j.Slf4j;
import net.runelite.api.ItemID;
import net.runelite.api.widgets.WidgetInfo;
import net.runelite.client.plugins.iutils.game.InventoryItem;
import net.runelite.client.plugins.iutils.game.ItemQuantity;
import net.runelite.client.plugins.varrocksmither.Task;
import net.runelite.client.plugins.varrocksmither.VarrockSmitherPlugin;

import java.util.List;
import java.util.Random;

@Slf4j
public class Banking extends Task {

    @Override
    public boolean validate() {
        return outOfSupplies();
    }

    Random random = new Random();
    int randomEnergy = random.nextInt((70 - 50) + 1) + 50;

    @Override
    public void run() {

        VarrockSmitherPlugin.status = "Banking";
        game.tick(1);

        if (game.inventory().withId(ItemID.HAMMER).quantity() < 1) {
            log.info("Withdrawing hammer");
            bank().depositAll(true);
            bank().withdraw(ItemID.HAMMER, 1, false);
        }

        if (game.localPlayer().isIdle()) {
            if (game.widget(WidgetInfo.BANK_CONTAINER) == null) { // bank is closed
//                if (inventoryHasItems(staminaPots.get(0))
//                        || inventoryHasItems(staminaPots.get(1))
//                        || inventoryHasItems(staminaPots.get(2))
//                        || inventoryHasItems(staminaPots.get(3))) {
                if (inventoryHasItems(true, staminaPots.toArray(new ItemQuantity[]{}))) {
                    log.info("inventory has an item!");
                    useItem(staminaPots, "Drink");
                }
                game.objects().withId(34810).nearest().interact("bank");
            } else { // bank is open
                bank().depositExcept(true, ItemID.HAMMER, barId);
                if (useStamina) {
                    if (game.varb(25) == 0) { // if stamina is not active
                        if (game.energy() <= randomEnergy) {
                            if (withdrawFirstItem(staminaPots)) {
                                bank().close();
                            }
                        }
                    }
                }
                bank().withdraw(barId, 27, false);
            }
        }
    }

    protected boolean inventoryHasItems(ItemQuantity... items) {
        return inventoryHasItems(false, items);
    }

    protected boolean inventoryHasItems(boolean any, ItemQuantity... items) {
        for (var item : items) {
            if (any == (game.inventory().withId(item.id).quantity() >= item.quantity)) {
                return any;
            }
        }
        return !any;
    }

    private boolean withdrawFirstItem(List<ItemQuantity> items) {
        for (ItemQuantity item: items) {
            if (bank().contains(item)) {
                bank().withdraw(item.id, item.quantity, false);
                return true;
            }
        }
        return false;
    }

    private void useItem(List<ItemQuantity> items, String action) {

        for (ItemQuantity item : items) {
            if (game.inventory().withId(item.id).exists()) {
                InventoryItem itemAction = game.inventory().withId(item.id).first();
                itemAction.interact(action);
                return;
            }
        }
    }

}
