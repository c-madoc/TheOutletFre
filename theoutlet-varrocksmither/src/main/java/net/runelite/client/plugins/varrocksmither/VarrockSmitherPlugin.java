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
package net.runelite.client.plugins.varrocksmither;

import com.google.inject.Provides;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Client;
import net.runelite.api.GameState;
import net.runelite.api.ItemID;
import net.runelite.api.events.ConfigButtonClicked;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.PluginDependency;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.plugins.iutils.iUtils;
import net.runelite.client.plugins.iutils.scripts.iScript;
import net.runelite.client.plugins.iutils.scripts.iScript;
import net.runelite.client.plugins.varrocksmither.tasks.Banking;
import net.runelite.client.plugins.varrocksmither.tasks.SmithItems;
import net.runelite.client.ui.overlay.OverlayManager;
import org.pf4j.Extension;

import javax.inject.Inject;
import java.time.Instant;


@Extension
@PluginDependency(iUtils.class)
@PluginDescriptor(
        name = "TheOutlet: Varrock Smither",
        enabledByDefault = false,
        description = "Smiths at the Varrock anvils",
        tags = {"theoutlet", "smith", "varrock"}
)
@Slf4j
public class VarrockSmitherPlugin extends iScript {

    @Inject private Client client;
    @Inject private VarrockSmitherConfig config;
    public static VarrockSmitherConfig taskConfig;
    @Inject private OverlayManager overlayManager;
    @Inject private VarrockSmitherOverlay overlay;
    @Inject private ConfigManager configManager;

    private static TaskSet tasks = new TaskSet();

    Instant botTimer;
    public static String status = "starting...";



    @Provides
    VarrockSmitherConfig provideConfig(ConfigManager configManager) {
        return configManager.getConfig(VarrockSmitherConfig.class);
    }

    @Override
    protected void startUp() {
        configManager.setConfiguration("bank", "bankPinKeyboard", true);
    }

    @Override
    protected void shutDown() {
        stop();
    }

    @Override
    public void onStart() {
        log.info("Starting Varrock Smither.");
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
        log.info("Stopping Varrock Smither.");
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
                injector.getInstance(SmithItems.class),
                injector.getInstance(Banking.class)
        );
    }

    @Subscribe
    private void onConfigButtonPressed(ConfigButtonClicked configButtonClicked) {
        if (!configButtonClicked.getGroup().equalsIgnoreCase("VarrockSmither")) {
            return;
        }

        if (configButtonClicked.getKey().equals("startButton")) {
            execute();
        }
    }

}