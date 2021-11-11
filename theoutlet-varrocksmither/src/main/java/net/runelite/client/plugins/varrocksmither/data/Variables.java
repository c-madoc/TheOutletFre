package net.runelite.client.plugins.varrocksmither.data;

import net.runelite.api.ItemID;

public class Variables {
    public enum Bar {
        BRONZE("Bronze", ItemID.BRONZE_BAR, 1),
        IRON("Iron", ItemID.IRON_BAR, 15),
        STEEL("Steel", ItemID.STEEL_BAR, 30),
        MITHRIL("Mithril", ItemID.MITHRIL_BAR, 50),
        ADAMANTITE("Adamantite", ItemID.ADAMANTITE_BAR, 70),
        RUNITE("Runite", ItemID.RUNITE_BAR, 85);

        private final String name;
        private final int itemId;
        private final int levelRequired;

        Bar(String name, int itemId, int levelRequired) {
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

    public enum SmithItem {
        TWO_HANDER("2-hand sword", 3, 13, "Smith"),
        PLATELEGS("Plate legs", 3, 20, "Smith"),
        PLATESKIRT("Plate skirt", 3, 21, "Smith"),
        PLATEBODY("Plate body", 5, 22, "Smith"),
        NAILS("Nails", 1, 23, "Smith set"),
        DART_TIPS("Dart tips", 1, 29, "Smith set"),
        ARROW_HEADS("Arrowtips", 1, 30, "Smith set"),
        THROWING_KNIVES("Knives", 1, 31, "Smith set");

        private final String name;
        private final int barsNeeded;
        private final int widgetChildId;
        private final String smithType;

        SmithItem(String name, int barsNeeded, int widgetChildId, String smithType) {
            this.name = name;
            this.barsNeeded = barsNeeded;
            this.widgetChildId = widgetChildId;
            this.smithType = smithType;
        }

        public int getBarsNeeded() {
            return barsNeeded;
        }
        public int getWidgetChildId() {
            return widgetChildId;
        }
        public String getSmithType() { return smithType; }

        @Override
        public String toString() {
            return name;
        }
    }

}
