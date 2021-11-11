package net.runelite.client.plugins.plugin.tasks;

import lombok.extern.slf4j.Slf4j;
import net.runelite.api.ObjectID;
import net.runelite.client.plugins.iutils.game.InventoryItem;
import net.runelite.client.plugins.iutils.game.iObject;
import net.runelite.client.plugins.iutils.ui.Chatbox;
import net.runelite.client.plugins.plugin.FiremakerPlugin;
import net.runelite.client.plugins.plugin.Task;

@Slf4j
public class UnnoteLogs extends Task {

    @Override
    public boolean validate() {
        return FiremakerPlugin.taskConfig.useNoted()
                && game.inventory().withId(FiremakerPlugin.taskConfig.logType().getItemId()+1).exists()
                && !game.inventory().withId(FiremakerPlugin.taskConfig.logType().getItemId()).exists();
    }

    private void unnoteLogs() {
        iObject bankBooth = game.objects().withName("Grand Exchange booth", "Bank booth", "Bank chest").nearest();
        InventoryItem notedLogs = game.inventory().withId(FiremakerPlugin.taskConfig.logType().getItemId() + 1).first();
        if (bankBooth == null) {
            log.info("Cannot find bank booth");
            return;
        }
        if (notedLogs == null) {
            log.info("Cannot find noted logs");
            return;
        }
        if (bankBooth.name().toLowerCase().contains("grand exchange")) {
            log.info("Using GE type! Switching to correct grand exchange booth.");
            bankBooth = game.objects().withId(ObjectID.GRAND_EXCHANGE_BOOTH).nearest();
        }
        notedLogs.useOn(bankBooth);
    }

    @Override
    public void run() {
        game.tick(1);
        if (!game.localPlayer().isIdle()) {
            return;
        }
        FiremakerPlugin.lightLogs = false;
        if (chatbox.chatState().equals(Chatbox.ChatState.OPTIONS_CHAT)) {
            chatbox.chooseOption("Yes");
        }
        unnoteLogs();
    }
}
