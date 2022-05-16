package net.keyStrokes;


import com.google.gson.*;
import net.keyStrokes.gui.KeystrokesModGUI;
import net.keyStrokes.overlay.RenderStyle;
import net.keyStrokes.overlay.RenderedCPSKey;
import net.keyStrokes.overlay.RenderedKey;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.GameSettings;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Mod(modid = "keystrokesmod", useMetadata = true)
public class KeystrokesMod {

    public static final Gson GSON = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();

    @Mod.Instance
    private static KeystrokesMod instance;

    private static long currentTick;

    private File configFile;
//    private boolean firstRun;

    private boolean showGui;

    private final List<RenderedKey> keys = new ArrayList<>();

    @Mod.EventHandler
    public void init(FMLPreInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register(this);
        ClientCommandHandler.instance.registerCommand(new KeystrokesCommand());

        initializeDefaultKeys();

        configFile = new File(Minecraft.getMinecraft().mcDataDir, "blkeystrokesmod.json");

        try {
            if (!configFile.exists()) {
//                firstRun = false;
                saveConfig();
            } else {
                loadConfig();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @SubscribeEvent
    public void onRender2D(RenderGameOverlayEvent.Post event) {
        if (!Minecraft.getMinecraft().gameSettings.showDebugInfo && Minecraft.getMinecraft().currentScreen == null && event.type == RenderGameOverlayEvent.ElementType.HOTBAR) {
            keys.stream().filter(k -> k.getConfiguration().visible).forEach(RenderedKey::render);
        }
    }

    @SubscribeEvent
    public void onPlayerTick(TickEvent.PlayerTickEvent event) {
//        if (firstRun) {
//            firstRun = false;
//
//            new Thread(() -> {
//                try {
//                    Thread.sleep(1000);
//                } catch (InterruptedException ignored) {
//                }
//
//                Minecraft.getMinecraft().addScheduledTask(() -> {
//                    String color = (char) 167 + "b";
////                    Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(color + "Looks like this is your first time running Keystrokes Mod."));
//                    Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(color + "Type /kgui to open the configuration GUI."));
//                });
//            }).start();
//        }

        if (showGui) {
            Minecraft.getMinecraft().displayGuiScreen(new KeystrokesModGUI());
            showGui = false;
        }
    }

    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase == TickEvent.Phase.START) {
            currentTick++;
        }
    }

    public void loadConfig() throws IOException {
        StringBuilder builder = new StringBuilder();

        for (String line : Files.readAllLines(configFile.toPath()))
            builder.append(line).append('\n');

        JsonObject config = new JsonParser().parse(builder.toString()).getAsJsonObject();

        for (Map.Entry<String, JsonElement> entry : config.entrySet()) {
            RenderedKey key = keys.stream().filter(k -> k.getKeyBinding().getKeyDescription().equals(entry.getKey())).findFirst().orElse(null);

            if (key != null) {
                KeyConfiguration configuration = GSON.fromJson(entry.getValue(), KeyConfiguration.class);
                key.setConfiguration(configuration);
                updateKeyType(key);
            }
        }
    }

    public void saveConfig() throws IOException {
        JsonObject config = new JsonObject();

        for (RenderedKey key : keys)
            config.add(key.getKeyBinding().getKeyDescription(), GSON.toJsonTree(key.getConfiguration()));

        Files.write(configFile.toPath(), GSON.toJson(config).getBytes(), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
    }

    public RenderedKey updateKeyType(RenderedKey key) {
        RenderedKey newKey = key;

        if (key.getConfiguration().countCPS && !(key instanceof RenderedCPSKey)) {
            keys.set(keys.indexOf(key), newKey = new RenderedCPSKey(key.getKeyBinding(), key.getConfiguration()));
        } else if (!key.getConfiguration().countCPS && key instanceof RenderedCPSKey) {
            keys.set(keys.indexOf(key), newKey = new RenderedKey(key.getKeyBinding(), key.getConfiguration()));
        }

        if (newKey != key) {
            MinecraftForge.EVENT_BUS.unregister(key);
            MinecraftForge.EVENT_BUS.register(newKey);
        }

        return newKey;
    }

    public void initializeDefaultKeys() {
        keys.forEach(MinecraftForge.EVENT_BUS::unregister);
        keys.clear();

        GameSettings gameSettings = Minecraft.getMinecraft().gameSettings;
        // Jump
        {
            KeyConfiguration configuration = new KeyConfiguration();
            configuration.style = RenderStyle.RECTANGLE;
            configuration.width = 70;
            configuration.height = 16;
            configuration.centerX = 475;
            configuration.centerY = 218;
            configuration.bounce = false;
            configuration.textScale = 1.0;
            configuration.fades = false;
            configuration.particles = false;
            configuration.particleColor = -1;
            configuration.rainbowParticles = false;
            configuration.baseColorsInactive = 3674937296571858944L;
            configuration.baseColorsActive = -9151314444947554305L;
            configuration.textColorInactive = -1;
            configuration.textColorActive = -16777216;


            RenderedKey key = new RenderedKey(gameSettings.keyBindJump, configuration);
            MinecraftForge.EVENT_BUS.register(key);
            keys.add(key);
        }
//
//        // Forward
//        {
//            KeyConfiguration configuration = new KeyConfiguration();
//            configuration.style = RenderStyle.RECTANGLE;
//            configuration.centerX = 475;
//            configuration.centerY = 160;
//
//            RenderedKey key = new RenderedKey(gameSettings.keyBindForward, configuration);
//            MinecraftForge.EVENT_BUS.register(key);
//            keys.add(key);
//        }
//        //
//
//        // Left
//        {
//            KeyConfiguration configuration = new KeyConfiguration();
//            configuration.style = RenderStyle.RECTANGLE;
//            configuration.centerX = 454;
//            configuration.centerY = 181;
//
//            RenderedKey key = new RenderedKey(gameSettings.keyBindLeft, configuration);
//            MinecraftForge.EVENT_BUS.register(key);
//            keys.add(key);
//        }
//        //
//
//        // Back
//        {
//            KeyConfiguration configuration = new KeyConfiguration();
//            configuration.style = RenderStyle.RECTANGLE;
//            configuration.centerX = 475;
//            configuration.centerY = 181;
//
//            RenderedKey key = new RenderedKey(gameSettings.keyBindBack, configuration);
//            MinecraftForge.EVENT_BUS.register(key);
//            keys.add(key);
//        }
//        //
//
//        // Right
//        {
//            KeyConfiguration configuration = new KeyConfiguration();
//            configuration.style = RenderStyle.RECTANGLE;
//            configuration.centerX = 496;
//            configuration.centerY = 181;
//
//            RenderedKey key = new RenderedKey(gameSettings.keyBindRight, configuration);
//            MinecraftForge.EVENT_BUS.register(key);
//            keys.add(key);
//        }
//        // Sprint
//        {
//            KeyConfiguration configuration = new KeyConfiguration();
//            configuration.style = RenderStyle.RECTANGLE;
//            configuration.width = 40;
//            configuration.height = 20;
//            configuration.centerX = 464;
//            configuration.centerY = 201;
//
//            RenderedKey key = new RenderedKey(gameSettings.keyBindSprint, configuration);
//            MinecraftForge.EVENT_BUS.register(key);
//            keys.add(key);
//        }
//        // Sneak
//        {
//            KeyConfiguration configuration = new KeyConfiguration();
//            configuration.style = RenderStyle.RECTANGLE;
//            configuration.width = 40;
//            configuration.height = 20;
//            configuration.centerX = 486;
//            configuration.centerY = 201;
//
//            RenderedKey key = new RenderedKey(gameSettings.keyBindSneak, configuration);
//            MinecraftForge.EVENT_BUS.register(key);
//            keys.add(key);
//        }
        //
//
//        // Attack
//        {
//            KeyConfiguration configuration = new KeyConfiguration();
//            configuration.width = 30;
//            configuration.centerX = 25;
//            configuration.centerY = 64;
//            configuration.countCPS = true;
//            configuration.textScale = 0.75;
//
//            RenderedKey key = new RenderedCPSKey(gameSettings.keyBindAttack, configuration);
//            MinecraftForge.EVENT_BUS.register(key);
//            keys.add(key);
//        }
//        //
//
//        // Use item
//        {
//            KeyConfiguration configuration = new KeyConfiguration();
//            configuration.width = 30;
//            configuration.centerX = 59;
//            configuration.centerY = 64;
//            configuration.countCPS = true;
//            configuration.textScale = 0.75;
//
//            RenderedKey key = new RenderedCPSKey(gameSettings.keyBindUseItem, configuration);
//            MinecraftForge.EVENT_BUS.register(key);
//            keys.add(key);
//        }
        //


        //
    }

    public void showGui() {
        showGui = true;
    }

    public List<RenderedKey> keys() { // This should return an unmodifiable collection... oh well
        return keys;
    }

    public File getConfigFile() {
        return configFile;
    }

    public static long getCurrentTick() {
        return currentTick;
    }

    public static KeystrokesMod getInstance() {
        return instance;
    }
}