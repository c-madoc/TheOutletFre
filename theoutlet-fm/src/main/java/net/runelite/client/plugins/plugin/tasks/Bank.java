package net.runelite.client.plugins.plugin.tasks;

import lombok.extern.slf4j.Slf4j;
import net.runelite.api.ItemID;
import net.runelite.api.ObjectID;
import net.runelite.client.plugins.iutils.game.iObject;
import net.runelite.client.plugins.plugin.TOFiremakerPlugin;
import net.runelite.client.plugins.plugin.Task;
import net.runelite.client.plugins.plugin.data.Variables;

@Slf4j
public class Bank extends Task {

    @Override
    public boolean validate() {
        return !game.inventory().withName("Tinderbox").exists()
                || (TOFiremakerPlugin.taskConfig.useNoted()
                    && !game.inventory().withId(TOFiremakerPlugin.taskConfig.logType().getItemId()+1).exists())
                || (!TOFiremakerPlugin.taskConfig.useNoted()
                    && !game.inventory().withId(TOFiremakerPlugin.taskConfig.logType().getItemId()).exists());
    }

    private void openBank() {
        if (bank.isOpen()) {
            log.info("Bank is open");
            return;
        }

        log.info("Opening bank");
        iObject nearestBank = null;
        if (TOFiremakerPlugin.taskConfig.location().equals(Variables.LightLocation.GE_N)
                || TOFiremakerPlugin.taskConfig.location().equals(Variables.LightLocation.GE_N)) {
            nearestBank = game.objects().withId(ObjectID.GRAND_EXCHANGE_BOOTH_10061).nearest();
        } else {
            nearestBank = game.objects().withName("Bank booth", "Bank chest").nearest();
        }

        if (nearestBank == null) {
            log.info("bank null");
            return;
        }

        nearestBank.interact("Bank");
    }

    private void withDrawItems() {
        if (!inventoryHasItem(ItemID.TINDERBOX) && bank.contains(ItemID.TINDERBOX)) {
            log.info("Withdrawing tinder");
            return;
        }

        int notedLogID = TOFiremakerPlugin.taskConfig.logType().getItemId() + 1;
        int logID = TOFiremakerPlugin.taskConfig.logType().getItemId();

        if (TOFiremakerPlugin.taskConfig.useNoted()
                && !inventoryHasItem(notedLogID)
                && bank.contains(logID)) {
            log.info("Withdrawing noted logs");
            bank.withdraw(logID, Integer.MAX_VALUE, true);
            return;
        }

        if (!TOFiremakerPlugin.taskConfig.useNoted()
                && !inventoryHasItem(logID)
                && bank.contains(logID)) {
            log.info("Withdrawing unnoted logs");
            bank.withdraw(logID, Integer.MAX_VALUE, false);
        }
    }

    @Override
    public void run() {
        game.tick(1);
        log.info("Bank");
        if (!game.localPlayer().isIdle()) {
            return;
        }
        openBank();
        withDrawItems();
    }
}
