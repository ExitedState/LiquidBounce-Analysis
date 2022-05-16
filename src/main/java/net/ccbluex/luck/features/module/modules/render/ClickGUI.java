/*
 * LiquidBounce Hacked Client
 * A free open source mixin-based injection hacked client for Minecraft using Minecraft Forge.
 * https://github.com/CCBlueX/LiquidBounce/
 */
package net.ccbluex.luck.features.module.modules.render;

import net.ccbluex.luck.LiquidBounce;
import net.ccbluex.luck.event.EventTarget;
import net.ccbluex.luck.event.PacketEvent;
import net.ccbluex.luck.features.module.Module;
import net.ccbluex.luck.features.module.ModuleCategory;
import net.ccbluex.luck.features.module.ModuleInfo;
import net.ccbluex.luck.ui.client.clickgui.ClickGui;
import net.ccbluex.luck.ui.client.clickgui.style.styles.LiquidBounceStyle;
import net.ccbluex.luck.ui.client.clickgui.style.styles.NullStyle;
import net.ccbluex.luck.ui.client.clickgui.style.styles.SlowlyStyle;
import net.ccbluex.luck.utils.render.ColorUtils;
import net.ccbluex.luck.value.BoolValue;
import net.ccbluex.luck.value.FloatValue;
import net.ccbluex.luck.value.IntegerValue;
import net.ccbluex.luck.value.ListValue;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S2EPacketCloseWindow;
import org.lwjgl.input.Keyboard;

import java.awt.*;

@ModuleInfo(name = "ClickGUI", description = "Opens the ClickGUI.", category = ModuleCategory.RENDER, keyBind = Keyboard.KEY_RSHIFT, canEnable = false)
public class ClickGUI extends Module {
    private final ListValue styleValue = new ListValue("Style", new String[] {"luck", "Null", "Slowly"}, "Slowly") {
        @Override
        protected void onChanged(final String oldValue, final String newValue) {
            updateStyle();
        }
    };

    public final FloatValue scaleValue = new FloatValue("Scale", 1F, 0.7F, 2F);
    public final IntegerValue maxElementsValue = new IntegerValue("MaxElements", 15, 1, 20);

    private static final IntegerValue colorRedValue = new IntegerValue("R", 0, 0, 255);
    private static final IntegerValue colorGreenValue = new IntegerValue("G", 160, 0, 255);
    private static final IntegerValue colorBlueValue = new IntegerValue("B", 255, 0, 255);
    private static final BoolValue colorRainbow = new BoolValue("Rainbow", false);

    public static Color generateColor() {
        return colorRainbow.get() ? ColorUtils.rainbow() : new Color(colorRedValue.get(), colorGreenValue.get(), colorBlueValue.get());
    }

    @Override
    public void onEnable() {
        updateStyle();

        mc.displayGuiScreen(LiquidBounce.clickGui);
    }

    private void updateStyle() {
        switch(styleValue.get().toLowerCase()) {
            case "liquidbounce":
                LiquidBounce.clickGui.style = new LiquidBounceStyle();
                break;
            case "null":
                LiquidBounce.clickGui.style = new NullStyle();
                break;
            case "slowly":
                LiquidBounce.clickGui.style = new SlowlyStyle();
                break;
        }
    }

    @EventTarget(ignoreCondition = true)
    public void onPacket(final PacketEvent event) {
        final Packet<?> packet = event.getPacket();

        if (packet instanceof S2EPacketCloseWindow && mc.currentScreen instanceof ClickGui) {
            event.cancelEvent();
        }
    }
}
