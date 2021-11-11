package net.runelite.client.plugins.plugin.data;

import net.runelite.api.ItemID;
import net.runelite.api.coords.WorldPoint;

public class Variables {
    public enum Log {
        LOGS("Logs", ItemID.LOGS, 1),
        OAK("Oak", ItemID.OAK_LOGS, 15),
        WILLOW("Willow", ItemID.WILLOW_LOGS, 30),
        MAPLE("Maple", ItemID.MAPLE_LOGS, 45),
        YEW("Yew", ItemID.YEW_LOGS, 60),
        MAGIC("Magic", ItemID.MAGIC_LOGS, 85);

        private final String name;
        private final int itemId;
        private final int levelRequired;

        Log(String name, int itemId, int levelRequired) {
            this.name = name;
            this.itemId = itemId;
            this.levelRequired = levelRequired;
        }

        public int getItemId() {
            return itemId;
        }

        public int getLevelRequired() {
            return levelRequired;
        }

        @Override
        public String toString() {
            return name;
        }
    }

    public enum LightLocation {
        GE_N("GE North", new WorldPoint(3178, 3497, 0), new WorldPoint(3178, 3496, 0)),
        GE_S("GE South", new WorldPoint(3178, 3483, 0), new WorldPoint(3178, 3482, 0));

        private final String name;
        private final WorldPoint spotOne;
        private final WorldPoint spotTwo;

        LightLocation(String name, WorldPoint spotOne, WorldPoint spotTwo) {
            this.name = name;
            this.spotOne = spotOne;
            this.spotTwo = spotTwo;
        }


        public WorldPoint getSpotOne() {return spotOne;}
        public WorldPoint getSpotTwo() {return spotTwo;}
        @Override
        public String toString() {
            return name;
        }
    }
}
