/*
 * LiquidBounce Hacked Client
 * A free open source mixin-based injection hacked client for Minecraft using Minecraft Forge.
 * https://github.com/CCBlueX/LiquidBounce/
 */
package net.ccbluex.luck.injection.forge.mixins.gui;

import net.minecraft.client.gui.GuiMultiplayer;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(GuiMultiplayer.class)
public abstract class MixinGuiMultiplayer extends MixinGuiScreen {
//
//    private GuiButton bungeeCordSpoofButton;
//
//    @Inject(method = "initGui", at = @At("RETURN"))
//    private void initGui(CallbackInfo callbackInfo) {
//        buttonList.add(new GuiButton(997, 5, 8, 98, 20, "AntiForge"));
//        buttonList.add(bungeeCordSpoofButton = new GuiButton(998, 108, 8, 98, 20, "BungeeCord Spoof: " + (BungeeCordSpoof.enabled ? "On" : "Off")));
//        buttonList.add(new GuiButton(999, width - 104, 8, 98, 20, "Tools"));
//    }
//
//    @Inject(method = "actionPerformed", at = @At("HEAD"))
//    private void actionPerformed(GuiButton button, CallbackInfo callbackInfo) {
//        switch (button.id) {
//            case 997:
//                mc.displayGuiScreen(new GuiAntiForge((GuiScreen) (Object) this));
//                break;
//            case 998:
//                BungeeCordSpoof.enabled = !BungeeCordSpoof.enabled;
//                bungeeCordSpoofButton.displayString = "BungeeCord Spoof: " + (BungeeCordSpoof.enabled ? "On" : "Off");
//                LiquidBounce.fileManager.saveConfig(LiquidBounce.fileManager.valuesConfig);
//                break;
//            case 999:
//                mc.displayGuiScreen(new GuiTools((GuiScreen) (Object) this));
//                break;
//        }
//    }
}