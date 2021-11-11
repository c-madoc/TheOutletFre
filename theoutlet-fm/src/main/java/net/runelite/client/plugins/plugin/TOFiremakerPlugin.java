/*
 * Copyright (c) 2018, SomeoneWithAnInternetConnection
 * Copyright (c) 2018, oplosthee <https://github.com/oplosthee>
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this
 *    list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
 * ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package net.runelite.client.plugins.plugin;

import com.google.inject.Provides;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Client;
import net.runelite.api.GameState;
import net.runelite.api.events.ConfigButtonClicked;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.PluginDependency;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.plugins.plugin.tasks.Bank;
import net.runelite.client.plugins.plugin.tasks.GoToLocation;
import net.runelite.client.plugins.plugin.tasks.LightLogs;
import net.runelite.client.plugins.iutils.iUtils;
import net.runelite.client.plugins.iutils.scripts.iScript;
import net.runelite.client.plugins.plugin.tasks.UnnoteLogs;
import net.runelite.client.ui.overlay.OverlayManager;
import org.pf4j.Extension;

import javax.inject.Inject;
import java.time.Instant;


@Extension
@PluginDependency(iUtils.class)
@PluginDescriptor(
        name = "TheOutlet: Firemaker",
        enabledByDefault = false,
        description = "Makes fires in various locations",
        tags = {"outlet", "firemaking", "firemaker", "fm", "skill"}
)
@Slf4j
public class TOFiremakerPlugin extends iScript {

    @Inject private Client client;
    @Inject private TOFiremakerConfig config;
    public static TOFiremakerConfig taskConfig;
    @Inject private OverlayManager overlayManager;
    @Inject private TOFiremakerOverlay overlay;
    @Inject private ConfigManager configManager;

    private static TaskSet tasks = new TaskSet();
    public static boolean lightLogs;
    public static boolean lightSpotOne;

    Instant botTimer;
    public static String status = "starting...";

    @Provides
    TOFiremakerConfig provideConfig(ConfigManager configManager) {
        return configManager.getConfig(TOFiremakerConfig.class);
    }

    @Override
    protected void startUp() {}

    @Override
    protected void shutDown() {
        stop();
    }

    @Override
    public void onStart() {
        log.info("Starting TheOutlet Firemaker.");
        taskConfig = config;

        if (client != null && game.localPlayer() != null && client.getGameState() == GameState.LOGGED_IN) {
            loadTasks();
            botTimer = Instant.now();
            overlayManager.add(overlay);
        } else {
            log.info("Start logged in!");
            stop();
        }
    }

    @Override
    public void onStop() {
        log.info("Stopping TheOutlet Firemaker.");
        overlayManager.remove(overlay);
        botTimer = null;
        tasks.clear();
    }

    @Override
    public void loop() {
        if (client != null && client.getLocalPlayer() != null) {
            var task = tasks.getValidTask();

            if (task != null) {
                status = task.getTaskDescription();
                task.run();
            } else {
                status = "Cannot continue - Stopping.";
                game.tick(5);
                log.info(status);
                stop();
            }
        }
    }

    private void loadTasks() {
        tasks.clear();
        tasks.addAll(
                injector.getInstance(Bank.class),
                injector.getInstance(UnnoteLogs.class),
                injector.getInstance(GoToLocation.class),
                injector.getInstance(LightLogs.class)
        );
    }

    @Subscribe
    private void onConfigButtonPressed(ConfigButtonClicked configButtonClicked) {
        if (!configButtonClicked.getGroup().equalsIgnoreCase("TOFiremaker")) {
            return;
        }

        if (configButtonClicked.getKey().equals("startButton")) {
            execute();
        }
    }

}