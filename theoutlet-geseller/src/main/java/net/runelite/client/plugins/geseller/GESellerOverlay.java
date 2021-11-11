package net.runelite.client.plugins.geseller;

import com.openosrs.client.ui.overlay.components.table.TableAlignment;
import com.openosrs.client.ui.overlay.components.table.TableComponent;
import lombok.extern.slf4j.Slf4j;
import net.runelite.client.ui.overlay.OverlayMenuEntry;
import net.runelite.client.ui.overlay.OverlayPanel;
import net.runelite.client.ui.overlay.OverlayPosition;
import net.runelite.client.ui.overlay.components.TitleComponent;
import net.runelite.client.util.ColorUtil;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.awt.*;
import java.time.Duration;
import java.time.Instant;

import static net.runelite.api.MenuAction.RUNELITE_OVERLAY_CONFIG;
import static net.runelite.client.ui.overlay.OverlayManager.OPTION_CONFIGURE;
import static org.apache.commons.lang3.time.DurationFormatUtils.formatDuration;

@Slf4j
@Singleton
class GESellerOverlay extends OverlayPanel {
    private final GESellerPlugin plugin;
    private final GESellerConfig config;

    String timeFormat;

    @Inject
    private GESellerOverlay(final GESellerPlugin plugin, final GESellerConfig config) {
        super(plugin);
        setPosition(OverlayPosition.BOTTOM_LEFT);
        this.plugin = plugin;
        this.config = config;
        getMenuEntries().add(new OverlayMenuEntry(RUNELITE_OVERLAY_CONFIG, OPTION_CONFIGURE, "Tutorial Island overlay"));
    }

    @Override
    public Dimension render(Graphics2D graphics) {
        if (plugin.botTimer == null) {
            return null;
        }

        TableComponent tableComponent = new TableComponent();
        tableComponent.setColumnAlignments(TableAlignment.LEFT, TableAlignment.RIGHT);

        Duration duration = Duration.between(plugin.botTimer, Instant.now());
        timeFormat = (duration.toHours() < 1) ? "mm:ss" : "HH:mm:ss";
        tableComponent.addRow("Time running:", formatDuration(duration.toMillis(), timeFormat));

        tableComponent.addRow("Status:", GESellerPlugin.status);

        TableComponent tableStatsComponenet = new TableComponent();
        tableStatsComponenet.setColumnAlignments(TableAlignment.LEFT, TableAlignment.RIGHT);

        TableComponent tableDelayComponenet = new TableComponent();
        tableDelayComponenet.setColumnAlignments(TableAlignment.LEFT, TableAlignment.RIGHT);

        if (!tableComponent.isEmpty()) {
            panelComponent.setBackgroundColor(ColorUtil.fromHex("#121212"));
            panelComponent.setPreferredSize(new Dimension(270, 200));
            panelComponent.setBorder(new Rectangle(5, 5, 5, 5));
            panelComponent.getChildren().add(TitleComponent.builder()
                    .text("Cetti's Varrock Smither")
                    .color(ColorUtil.fromHex("#40C4FF"))
                    .build());
            panelComponent.getChildren().add(tableComponent);
            panelComponent.getChildren().add(TitleComponent.builder()
                    .text("Stats")
                    .color(ColorUtil.fromHex("#FFA000"))
                    .build());
            panelComponent.getChildren().add(tableStatsComponenet);
        }
        return super.render(graphics);
    }
}