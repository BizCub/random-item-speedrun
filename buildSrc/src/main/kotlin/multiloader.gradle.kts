plugins {
    id("java")
}

sc.constants["is_cloth_config_available"] = isClothConfigAvailable

sc.replacements {
    string(scp >= "26.1") {
        replace("GuiGraphics", "GuiGraphicsExtractor")
        replace("drawString", "text")
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

configurations.all {
    resolutionStrategy {
        force("net.fabricmc:fabric-loader:latest.release")
    }
}

if (isForge) {
    setProp("forge", "${mod.mc}-${getProp("forge")}")

    if (!isClothConfigAvailable) {
        setProp("cloth_config", "17.0.144")
    }
}

if (isNeoForge) {
    val neoVersion = mod.mc.substring(2)
    val neoLoader = getProp("neoforge")
    setProp("neoforge", if (neoVersion.contains(".")) "$neoVersion.$neoLoader" else "$neoVersion.0.$neoLoader")
}

project.extra["loom.platform"] = mod.loader

configurations.all {
    resolutionStrategy {
        force("net.fabricmc:fabric-loader:latest.release")
    }
}

base.archivesName.set("${mod.mixin}-${mod.loader}")
version = "${mod.version}+${mod.pub_start}"

tasks.processResources {
    properties(
        listOf("fabric.mod.json", "META-INF/*.toml"),
        "ModMenu"       to $$"$ModMenu",
        "id"            to mod.id,
        "mixin"         to mod.mixin,
        "name"          to mod.name,
        "description"   to mod.description,
        "version"       to mod.version,
        "modrinth"      to mod.modrinth,
        "github"        to mod.github,
        "author"        to "Bizarre Cube",
        "license"       to "MIT"
    )
}

java {
    val java = when {
        scp >= "26.1"   -> 25
        scp >= "1.20.5" -> 21
        scp >= "1.18"   -> 17
        scp >= "1.17"   -> 16
        else            -> 8
    }
    targetCompatibility = JavaVersion.toVersion(java)
    sourceCompatibility = JavaVersion.toVersion(java)
}
