plugins {
    id("multiloader-common")
    id("me.modmuss50.mod-publish-plugin")
}

sc.constants["is_cloth_config_available"] = isClothConfigAvailable

sc.replacements {
    string(scp >= "26.1", "!graphics") {
        replace("GuiGraphics", "GuiGraphicsExtractor")
    }
    string(scp >= "26.1", "draw_string") {
        replace("drawString", "text")
    }
    string(scp >= "26.1") {
        replace("gui.render.state", "renderer.state.gui")
        replace("drawCenteredString", "centeredText")
        replace("submitBlitToCurrentLayer", "addBlitToCurrentLayer")
        replace("renderContent", "extractContent")
        replace("KeyBindingHelper", "KeyMappingHelper")
        replace("registerKeyBinding", "registerKeyMapping")
    }
    string(scp >= "1.21.11") {
        replace("ResourceLocation", "Identifier")
    }
    string(scp >= "1.21.11" && !isForge, "auto_config") {
        replace("AutoConfig", "AutoConfigClient")
    }
    string(scp >= "1.21.4") {
        replace("getScrollbarPosition()", "scrollBarX()")
    }
    string(scp >= "1.21.6") {
        replace("import net.minecraft.client.renderer.RenderType",
            "import net.minecraft.client.renderer.RenderPipelines")
        replace("net.minecraftforge.eventbus.api.SubscribeEvent",
            "net.minecraftforge.eventbus.api.listener.SubscribeEvent")
    }
    string(scp >= "1.21.2") {
        replace("getDisplayName()", "getItemName()")
    }
}

if (isForge) {
    if (!isClothConfigAvailable) {
        setProp("cloth_config", "17.0.144")
    }
}

reps.clear()
reps.add(Repository("https://maven.shedaniel.me"))

deps.clear()
deps.add(Dependency("me.shedaniel.cloth:cloth-config-${mod.loader}:${getProp("cloth_config")}", "implementation"))

if (isFabric) {
    reps.add(Repository("https://maven.terraformersmc.com/releases"))

    deps.add(Dependency("net.fabricmc:fabric-loader:latest.release", "implementation"))
    deps.add(Dependency("net.fabricmc.fabric-api:fabric-api:${getProp("fabric_api")}", "implementation"))
    deps.add(Dependency("com.terraformersmc:modmenu:${getProp("modmenu")}", "api"))
}

if (isNeoForge) {
    reps.add(Repository("https://maven.neoforged.net/releases"))
}

publishMods {
    modrinth {
        if (isClothConfigAvailable) optional("cloth-config")
        if (isFabric) requires("fabric-api")
        if (isFabric) optional("modmenu")
    }
    curseforge {
        if (isClothConfigAvailable) optional("cloth-config")
        if (isFabric) requires("fabric-api")
        if (isFabric) optional("modmenu")
    }
}
