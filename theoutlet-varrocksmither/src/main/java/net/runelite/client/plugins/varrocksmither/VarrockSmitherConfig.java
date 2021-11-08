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

import net.runelite.client.config.*;

@ConfigGroup("VarrockSmither")
public interface VarrockSmitherConfig extends Config {

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
            position = 20,
            keyName = "barType",
            name = "Bar Type",
            description = "Select the type of bar to use.",
            section = mainConfig
    )
    default VarrockSmitherPlugin.Bar barType() {return VarrockSmitherPlugin.Bar.RUNITE;}

    @ConfigItem(
            position = 30,
            keyName = "smithItem",
            name = "Smith Item",
            description = "Select the type of item to smith.",
            section = mainConfig
    )
    default VarrockSmitherPlugin.SmithItem smithItem() {return VarrockSmitherPlugin.SmithItem.PLATEBODY;}

    @ConfigItem(
            position = 40,
            keyName = "useStamina",
            name = "Use Stamina",
            description = "Use Stamina on Low Energy?",
            section = mainConfig
    )
    default boolean useStamina() {return true;}


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
