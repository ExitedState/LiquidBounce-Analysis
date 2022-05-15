/*
 * LiquidBounce Hacked Client
 * A free open source mixin-based injection hacked client for Minecraft using Minecraft Forge.
 * https://github.com/CCBlueX/LiquidBounce/
 */
package net.ccbluex.liquidbounce.injection.forge.mixins.gui;

import net.minecraft.client.gui.GuiSpectator;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(GuiSpectator.class)
public class MixinGuiSpectator {
//
//    @Inject(method = "renderTooltip", at = @At("RETURN"))
//    private void renderTooltipPost(ScaledResolution p_175264_1_, float p_175264_2_, CallbackInfo callbackInfo) {
//        LiquidBounce.eventManager.callEvent(new Render2DEvent(p_175264_2_));
//        AWTFontRenderer.Companion.garbageCollectionTick();
//    }
}