import com.bizcub.multiloader.MultiLoader
import dev.kikugie.stonecutter.build.StonecutterBuildExtension
import me.modmuss50.mpp.ModPublishExtension

val stonecutter = project.extensions.getByType(StonecutterBuildExtension::class.java)

project.extensions.configure<MultiLoader>("multiloader") {
    project.afterEvaluate {
        stonecutter.let { sc ->
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
        }
    }

    addRepository("https://maven.shedaniel.me")
    addDependency("me.shedaniel.cloth:cloth-config-${mod.loader}:${getProp("cloth_config")}", "api")

    if (isFabric) {
        addRepository("https://maven.terraformersmc.com/releases")

        addDependency("net.fabricmc:fabric-loader:latest.release", "implementation")
        addDependency("net.fabricmc.fabric-api:fabric-api:${getProp("fabric_api")}", "implementation")
        addDependency("com.terraformersmc:modmenu:${getProp("modmenu")}", "api")
    }

    if (isNeoForge) {
        addRepository("https://maven.neoforged.net/releases")
    }

    project.extensions.configure<ModPublishExtension>("publishMods") {
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
}
