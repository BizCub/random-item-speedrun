import com.bizcub.multiloader.MultiLoader
import dev.kikugie.stonecutter.build.StonecutterBuildExtension

apply(plugin = "dev.kikugie.fletching-table")

val stonecutter = project.extensions.getByType(StonecutterBuildExtension::class.java)

project.extensions.configure<MultiLoader>("multiloader") {
    access()

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

    addDependency("maven.shedaniel.me", "api", "me.shedaniel.cloth:cloth-config-${mod.loader}:${getDep("cloth-config")?.split("+")?.first()}")

    if (isFabric) {
        addDependency("implementation", "net.fabricmc:fabric-loader:${getDep("fabric")}")
        addDependency("implementation", "net.fabricmc.fabric-api:fabric-api:${getDep("fabric-api")}")
        addDependency("maven.terraformersmc.com/releases", "api", "com.terraformersmc:modmenu:${getDep("modmenu")}")
    }

    if (isClothConfigAvailable) addPublishDep("optional", "cloth-config")
    if (isFabric) addPublishDep("requires", "fabric-api")
    if (isFabric) addPublishDep("optional", "modmenu")
}
