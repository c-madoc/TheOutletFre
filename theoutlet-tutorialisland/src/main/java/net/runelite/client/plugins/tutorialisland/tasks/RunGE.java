package net.runelite.client.plugins.tutorialisland.tasks;

import lombok.extern.slf4j.Slf4j;
import net.runelite.client.plugins.tutorialisland.Task;
import net.runelite.client.plugins.tutorialisland.TutorialIslandPlugin;

@Slf4j
public class RunGE extends Task {

    @Override
    public boolean validate() {
        return questProgress() > 671 &&
                TutorialIslandPlugin.taskConfig.runGE() &&
                !game.localPlayer().position().inside(GRAND_EXCHANGE);
    }

    @Override
    public void run() {

        log.info("Running to Grand Exchange.");
        TutorialIslandPlugin.status = "Running to Grand Exchange";
        game.tick(1);
        walking.walkTo(GRAND_EXCHANGE);

    }

    private int questProgress() {
        return game.varp(281);
    }
}
