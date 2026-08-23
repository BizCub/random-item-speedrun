package io.github.bizcub.randomItemSpeedrun.client.gui;

import io.github.bizcub.randomItemSpeedrun.Speedrun;
import io.github.bizcub.randomItemSpeedrun.client.RandomItemSpeedrunClient;
import io.github.bizcub.randomItemSpeedrun.util.Utils;
import io.github.bizcub.randomItemSpeedrun.client.config.ConfigHelper;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.ComponentPath;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.*;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;

public class GameStartScreen extends Screen {
    private EditBox searchBox;
    private Button startButton;
    private SpeedrunWidget speedrunWidget;
    private SpeedrunInfoWidget speedrunInfoWidget;
    private ScaledItemDisplayWidget itemDisplayWidget;

    private String tempSearch;
    Speedrun focusedSpeedrun;

    public GameStartScreen() {
        super(Component.translatable("gui.game_start_screen.title"));
    }

    @Override
    protected void init() {
        super.init();
        this.speedrunWidget = addRenderableWidget(new SpeedrunWidget(this.minecraft, /* size */
                /*? >=1.20.3*/ getWidthPercent(66), getHeightPercent(79), /* pos Y */ getHeightPercent(11),
                /*? <=1.20.2*/ //getWidthPercent(66), height, /* top Y */ getHeightPercent(11), /* down Y */ getHeightPercent(90),
                /* size entry */ getHeightPercent(10)));
        /*? <=1.20.4*/ //this.speedrunWidget.setRenderBackground(false);
        /*? <=1.20.1*/ //this.speedrunWidget.setRenderTopAndBottom(false);

        this.searchBox = new EditBox(this.font, /* pos */ getWidthPercent(5.7), getHeightPercent(2.5), /* size */ getWidthPercent(22), getHeightPercent(7), this.searchBox, Component.empty());
        this.searchBox.setHint(Component.translatable("gui.recipebook.search_hint").withStyle(ChatFormatting.GRAY));
        addRenderableWidget(this.searchBox);

        //? >=1.20.2 {
        WidgetSprites sprites = new WidgetSprites(Utils.getIdentifier("default"), Utils.getIdentifier("disabled"), Utils.getIdentifier("hovered"));
        ImageButton settingsButton = this.addRenderableWidget(new ImageButton(getWidthPercent(1), getHeightPercent(2.5), getWidthPercent(3.8), getWidthPercent(3.8), sprites, button -> this.minecraft.gui.setScreen(ConfigHelper.getScreen(this))));
        //?} else {
        /*var sprite = Utils.getIdentifier("textures/gui/sprites/widgets.png");
        ImageButton settingsButton = this.addRenderableWidget(new ImageButton(getWidthPercent(1), getHeightPercent(2.5), getWidthPercent(3.8), getWidthPercent(3.8), 0, 0, getWidthPercent(3.9), sprite, getWidthPercent(12), getWidthPercent(12.1), button -> this.minecraft.gui.setScreen(ConfigHelper.getScreen(this))));
        settingsButton.setPosition(getWidthPercent(1), getHeightPercent(2.5));*///?}

        addRenderableWidget(Button.builder(CommonComponents.GUI_DONE, button -> onClose()).pos(this.width / 2 + 4, getHeightPercent(91.5)).size(125, 20).build());
        this.startButton = addRenderableWidget(Button.builder(Component.empty(), button -> changeGameStatus()).pos(this.width / 2 - 129, getHeightPercent(91.5)).size(125, 20).build());
        setStartButtonMessage();

        if (!ConfigHelper.isConfigLoaded()) {
            settingsButton.active = false;
            settingsButton.setTooltip(Tooltip.create(Component.translatable("gui.game_start_screen.config_mod_is_not_loaded")));
        }

        changeFocus();

        this.itemDisplayWidget = addRenderableWidget(new ScaledItemDisplayWidget(getWidthPercent(77), getHeightPercent(14), focusedSpeedrun.getItem(), getHeightPercent(25)));

        int offsetX = getWidthPercent(68.25);
        //? >=1.20.3 {
        this.speedrunInfoWidget = new SpeedrunInfoWidget(this.minecraft, /* size */ getWidthPercent(32), getHeightPercent(45), /* pos Y */ getHeightPercent(45.2), /* size entry */ getHeightPercent(4), this, offsetX, focusedSpeedrun);
        this.speedrunInfoWidget.setX(offsetX);
        //?} else {
        /*this.speedrunInfoWidget = new SpeedrunInfoWidget(this.minecraft, /^ size ^/ getWidthPercent(32), height, /^ topY ^/ getHeightPercent(45.2), /^ downY ^/ getHeightPercent(90), /^ size entry ^/ getHeightPercent(4), this, offsetX, focusedSpeedrun);
        this.speedrunInfoWidget.setLeftPos(offsetX);*///?}
        /*? <=1.20.4*/ //this.speedrunInfoWidget.setRenderBackground(false);
        /*? <=1.20.1*/ //this.speedrunInfoWidget.setRenderTopAndBottom(false);
        addRenderableWidget(this.speedrunInfoWidget);
    }

    private void changeGameStatus() {
        RandomItemSpeedrunClient.game.buttonPressed();
        setStartButtonMessage();
        this.onClose();
    }

    private void setStartButtonMessage() {
        if (RandomItemSpeedrunClient.game.isStarted())
            startButton.setMessage(Component.translatable("gui.game_start_screen.start_button.started"));
        else
            startButton.setMessage(Component.translatable("gui.game_start_screen.start_button.not_started"));
    }

    @Override //~ if >=26.1 'render(' -> 'extractRenderState(' {
    public void extractRenderState(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float a) {
        super.extractRenderState(graphics, mouseX, mouseY, a);//~}
        graphics.centeredText(this.font, this.title, getWidthPercent(50), getHeightPercent(4), -1);

        if (!this.searchBox.getValue().equals(this.tempSearch))
            this.speedrunWidget.refreshEntries(this.searchBox.getValue());
        this.tempSearch = this.searchBox.getValue();
    }

    public void changeFocus() {
        if (speedrunWidget == null) return;
        this.focusedSpeedrun = !RandomItemSpeedrunClient.speedruns.isEmpty()
                ? this.speedrunWidget.getFocusedSpeedrunEntry().speedrun
                : new Speedrun("air", "", Speedrun.Status.FAILURE, 0, 0);

        if (speedrunInfoWidget != null) this.speedrunInfoWidget.setEntries(focusedSpeedrun);
        if (itemDisplayWidget != null) this.itemDisplayWidget.updateItemStack(focusedSpeedrun.getItem());
    }

    @Override
    public void setFocused(GuiEventListener guiEventListener) {
        super.setFocused(guiEventListener);
        changeFocus();
    }

    @Override
    protected void changeFocus(ComponentPath componentPath) {
        super.changeFocus(componentPath);
        changeFocus();
    }

    public int getWidthPercent(double percent) {
        return Utils.getPercent(this.width, percent);
    }

    public int getHeightPercent(double percent) {
        return Utils.getPercent(this.height, percent);
    }
}
