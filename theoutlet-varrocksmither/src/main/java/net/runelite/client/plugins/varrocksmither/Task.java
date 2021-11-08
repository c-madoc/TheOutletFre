package net.runelite.client.plugins.varrocksmither;

import lombok.extern.slf4j.Slf4j;
import net.runelite.api.ItemID;
import net.runelite.client.plugins.iutils.game.ItemQuantity;
import net.runelite.client.plugins.iutils.scene.RectangularArea;
import net.runelite.client.plugins.iutils.scripts.UtilsScript;

import java.util.List;

@Slf4j
public abstract class Task extends UtilsScript {

    public Task()
    {
    }

    public static final RectangularArea SMITH_AND_BANK_AREA = new RectangularArea(3176,3448,3190,3424,0);


    public int barId = VarrockSmitherPlugin.taskConfig.barType().getItemId();
    public int itemWidgetChildId = VarrockSmitherPlugin.taskConfig.smithItem().getWidgetChildId();
    public int barReqAmt = VarrockSmitherPlugin.taskConfig.smithItem().getBarsNeeded();
    public String smithType = VarrockSmitherPlugin.taskConfig.smithItem().getSmithType();
    public String smithingItem = VarrockSmitherPlugin.taskConfig.smithItem().name();
    public boolean useStamina = VarrockSmitherPlugin.taskConfig.useStamina();
    public List<ItemQuantity> staminaPots = List.of(
            new ItemQuantity(ItemID.STAMINA_POTION1, 1),
            new ItemQuantity(ItemID.STAMINA_POTION2, 1),
            new ItemQuantity(ItemID.STAMINA_POTION3, 1),
            new ItemQuantity(ItemID.STAMINA_POTION4, 1)
    );

    public boolean outOfSupplies() {
        return game.inventory().withId(barId).quantity() < barReqAmt;
    }

    public abstract boolean validate();

    public String getTaskDescription() {
        return this.getClass().getSimpleName();
    }

    public abstract void run();
}
