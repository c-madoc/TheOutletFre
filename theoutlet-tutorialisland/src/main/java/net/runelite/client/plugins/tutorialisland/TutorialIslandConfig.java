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
package net.runelite.client.plugins.tutorialisland;

import net.runelite.client.config.*;

@ConfigGroup("TutorialIsland")
public interface TutorialIslandConfig extends Config {

    /*
    different styles for the config menu
    Create Sections for each Item thats going to be in the config menu
    - Section
    -- Item
    -- Item
    */

    // Section
    @ConfigSection(
            position = 0,
            keyName = "mainConfig",
            name = "Main Config",
            description = ""
    )
    String mainConfig = "Main Config";

    @ConfigItem(
            position = 10,
            keyName = "userName",
            name = "Username",
            description = "Enter a username, or blank for random.",
            section = mainConfig
    )
    default String userName() {return "";}


    @ConfigItem(
            position = 20,
            keyName = "gameMode",
            name = "Game Mode",
            description = "Select a game mode.",
            section = mainConfig
    )
    default TutorialIslandPlugin.GameMode gameMode() {return TutorialIslandPlugin.GameMode.REGULAR;}

    @ConfigItem(
            position = 30,
            keyName = "bankPin",
            name = "Required Bank PIN",
            description = "Enter a bank pin. Must be used for Ironmen.",
            hidden = true,

            unhide = "gameMode",
            unhideValue = "Ironman || Hardcore Ironman || Ultimate Ironman",
            section = mainConfig
    )
    default String bankPin() {return "0420";}

    @ConfigItem(
            position = 100,
            keyName = "runGE",
            name = "Run to GE",
            description = "Run to GE after completion?",
            section = mainConfig
    )
    default boolean runGE() {return false;}

    @ConfigItem(
            position = 40,
            keyName = "customLook",
            name = "Custom Look",
            description = "Enable for customization features.",
            section = mainConfig
    )
    default boolean customLook() {return false;}

    @ConfigItem(
            position = 41,
            keyName = "setFemale",
            name = "Start as Female",
            description = "Start as Female.",
            hidden = true,
            unhide = "customLook",
            unhideValue = "true",
            section = mainConfig
    )
    default boolean setFemale() {return false;}

    @ConfigItem(
            position = 42,
            keyName = "randomAppearance",
            name = "Set Random Appearance",
            description = "Set Random Appearance.",
            hidden = true,
            unhide = "customLook",
            unhideValue = "true",
            section = mainConfig
    )
    default boolean randomAppearance() {return false;}


    // start / stop
    @ConfigItem(
            position = 100,
            keyName = "startButton",
            name = "Start/Stop",
            description = "Test button that changes variable value",
            section = mainConfig
    )
    default Button startButton() {
        return new Button();
    }

}
