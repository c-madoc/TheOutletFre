package net.runelite.client.plugins.plugin.tasks;

import lombok.extern.slf4j.Slf4j;
import net.runelite.client.plugins.iutils.game.InventoryItem;
import net.runelite.client.plugins.iutils.game.iObject;
import net.runelite.client.plugins.plugin.TOFiremakerPlugin;
import net.runelite.client.plugins.plugin.Task;

@Slf4j
public class UnnoteLogs extends Task {

    @Override
    public boolean validate() {
        return !bank.isOpen()
                && TOFiremakerPlugin.taskConfig.useNoted()
                && game.inventory().withId(TOFiremakerPlugin.taskConfig.logType().getItemId()+1).exists()
                && !game.inventory().withId(TOFiremakerPlugin.taskConfig.logType().getItemId()).exists();
    }

    private void unnoteLogs() {
        iObject bankBooth = game.objects().withName("Bank booth", "Bank chest").nearest();
        InventoryItem notedLogs = game.inventory().withId(TOFiremakerPlugin.taskConfig.logType().getItemId() + 1).first();
        if (bankBooth == null) {
            log.info("Cannot find bank booth");
            return;
        }
        if (notedLogs == null) {
            log.info("Cannot find noted logs");
            return;
        }
        notedLogs.useOn(bankBooth);
    }

    @Override
    public void run() {
        game.tick(1);
        if (!game.localPlayer().isIdle()) {
            return;
        }
        TOFiremakerPlugin.lightLogs = false;
        chatbox.chooseOption("Yes");
        unnoteLogs();
    }
}
