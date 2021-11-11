package net.runelite.client.plugins.plugin.tasks;

import lombok.extern.slf4j.Slf4j;
import net.runelite.api.ItemID;
import net.runelite.api.ObjectID;
import net.runelite.api.widgets.WidgetInfo;
import net.runelite.client.plugins.iutils.game.InventoryItem;
import net.runelite.client.plugins.iutils.game.ItemQuantity;
import net.runelite.client.plugins.iutils.game.iObject;
import net.runelite.client.plugins.plugin.FiremakerPlugin;
import net.runelite.client.plugins.plugin.Task;
import net.runelite.client.plugins.plugin.data.Variables;

import java.util.List;
import java.util.Random;

@Slf4j
public class Banking extends Task {

    @Override
    public boolean validate() {
        return !game.inventory().withName("Tinderbox").exists()
                || (FiremakerPlugin.taskConfig.useNoted()
                && !game.inventory().withId(FiremakerPlugin.taskConfig.logType().getItemId()+1).exists())
                || (!FiremakerPlugin.taskConfig.useNoted()
                && !game.inventory().withId(FiremakerPlugin.taskConfig.logType().getItemId()).exists());
    }

    private void openBank() {
        if (bank.isOpen()) {
            return;
        }

        iObject nearestBank;
        if (FiremakerPlugin.taskConfig.location().equals(Variables.LightLocation.GE_N)
                || FiremakerPlugin.taskConfig.location().equals(Variables.LightLocation.GE_N)) {
            nearestBank = game.objects().withId(ObjectID.GRAND_EXCHANGE_BOOTH).nearest();
        } else {
            nearestBank = game.objects().withName("Bank booth", "Bank chest").nearest();
        }

        if (nearestBank == null) {
            return;
        }

        nearestBank.interact("Bank");
        game.waitUntil(() -> bank.isOpen(), 5);
    }

    private void switchNoted(boolean noted) {
        if (noted != bank.withdrawNoted()) {
            if (!noted) {
                this.game.widget(12, 22).interact(0);
            } else {
                this.game.widget(12, 24).interact(0);
            }

            this.game.waitUntil(() -> {
                return noted == bank.withdrawNoted();
            });
        }
    }

    private void withDrawItems() {
        log.info("Withdraw items");



        int notedLogID = FiremakerPlugin.taskConfig.logType().getItemId() + 1;
        int logID = FiremakerPlugin.taskConfig.logType().getItemId();

        if (inventoryHasItem(ItemID.TINDERBOX) && (inventoryHasItem(notedLogID) || inventoryHasItem(logID))) {
            bank.close();
            log.info("We have all we need");
            return;
        }

        if (!bank.contains(ItemID.TINDERBOX) && !inventoryHasItem(ItemID.TINDERBOX)) {
            log.info("No tinderbox!");
            return;
        }

        if (!bank.contains(logID) && !inventoryHasItem(logID)) {
            log.info("No logs!");
            return;
        }

        if (!inventoryHasItem(ItemID.TINDERBOX)) {
            switchNoted(false);
            bank.withdraw(ItemID.TINDERBOX, 1, false);
            game.waitUntil(() -> inventoryHasItem(ItemID.TINDERBOX, 4));
        }

        if (!inventoryHasItem(notedLogID) || !inventoryHasItem(logID)) {

            if (FiremakerPlugin.taskConfig.useNoted()) {
                log.info("Withdrawing noted logs");
                switchNoted(true);
                bank.withdraw(logID, Integer.MAX_VALUE, true);
            } else {
                log.info("Withdrawing unnoted logs");
                switchNoted(false);
                bank.withdraw(logID, Integer.MAX_VALUE, false);
            }
        }
    }

    @Override
    public void run() {
        game.tick();
        status = "Banking";
        if (!game.localPlayer().isIdle()) {
            return;
        }
        openBank();
        withDrawItems();

    }
}
