plugins {
    id("me.modmuss50.mod-publish-plugin")
    id("dev.kikugie.fletching-table")
    id("com.bizcub.multiloader")
}

multiloader {
    val isClothConfigAvailable = !(isForge && scp > "1.21.3")

    sc.constants["is_cloth_config_available"] = isClothConfigAvailable

    sc.replacements {
        string(scp >= "26.2") {
            replace(".setScreen(", ".gui.setScreen(")
        }
        string(scp >= "26.1", "!graphics") {
            replace("GuiGraphics", "GuiGraphicsExtractor")
        }
        string(scp >= "26.1", "draw_string") {
            replace(".drawString(", ".text(")
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

    addDependency(
        dependency = "me.shedaniel.cloth:cloth-config-${mod.loader}:${getDep("cloth-config").split("+").first()}",
        configuration = if (isClothConfigAvailable) "implementation" else "compileOnly",
        repository = "maven.shedaniel.me",
        isPublishDepEnabled = isClothConfigAvailable,
        publishProjectId = "cloth-config"
    )

    if (isFabric) {
        addDependency(
            dependency = "net.fabricmc:fabric-loader:${getDep("fabric")}"
        )
        addDependency(
            dependency = "net.fabricmc.fabric-api:fabric-api:${getDep("fabric-api")}",
            isPublishDepEnabled = true,
            publishRequirement = "requires"
        )
        addDependency(
            dependency = "com.terraformersmc:modmenu:${getDep("modmenu")}",
            repository = "maven.terraformersmc.com/releases",
            excludedModules = listOf("eu.pb4"),
            isPublishDepEnabled = true
        )
    }
}
