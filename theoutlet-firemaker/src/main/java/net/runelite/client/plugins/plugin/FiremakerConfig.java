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

import net.runelite.client.config.*;
import net.runelite.client.plugins.plugin.data.Variables;

@ConfigGroup("TOFiremaker")
public interface FiremakerConfig extends Config {

    @ConfigItem(
            position = 10,
            keyName = "logType",
            name = "Log",
            description = "The log to light."
    )
    default Variables.Log logType() {return Variables.Log.LOGS;}

    @ConfigItem(
            position = 10,
            keyName = "location",
            name = "Location",
            description = "The location to light."
    )
    default Variables.LightLocation location() {return Variables.LightLocation.GE_N;}

    @ConfigItem(
            position = 20,
            keyName = "stopAtLevel",
            name = "Level Stop",
            description = "Stop at a certain level."
    )
    default int stopAtLevel() {return 99;}

    @ConfigItem(
            position = 30,
            keyName = "useNoted",
            name = "Note logs",
            description = "Fastest way to get unnoted logs."
    )
    default boolean useNoted() {return true;}


    // start / stop
    @ConfigItem(
            position = 100,
            keyName = "startButton",
            name = "Start/Stop",
            description = "Test button that changes variable value"
    )
    default Button startButton() {
        return new Button();
    }

}
