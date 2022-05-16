/*
 * LiquidBounce Hacked Client
 * A free open source mixin-based injection hacked client for Minecraft using Minecraft Forge.
 * https://github.com/CCBlueX/LiquidBounce/
 */
package net.ccbluex.luck.features.module.modules.movement;

import net.ccbluex.luck.event.EventTarget;
import net.ccbluex.luck.event.UpdateEvent;
import net.ccbluex.luck.features.module.Module;
import net.ccbluex.luck.features.module.ModuleCategory;
import net.ccbluex.luck.features.module.ModuleInfo;
import net.ccbluex.luck.utils.MovementUtils;
import net.ccbluex.luck.utils.Rotation;
import net.ccbluex.luck.utils.RotationUtils;
import net.ccbluex.luck.value.BoolValue;
import net.minecraft.potion.Potion;

@ModuleInfo(name = "Sprint", description = "Automatically sprints all the time.", category = ModuleCategory.MOVEMENT)
public class Sprint extends Module {

    public final BoolValue allDirectionsValue = new BoolValue("AllDirections", true);
    public final BoolValue blindnessValue = new BoolValue("Blindness", true);
    public final BoolValue foodValue = new BoolValue("Food", true);

    public final BoolValue checkServerSide = new BoolValue("CheckServerSide", false);
    public final BoolValue checkServerSideGround = new BoolValue("CheckServerSideOnlyGround", false);

    @EventTarget
    public void onUpdate(final UpdateEvent event) {
        if (!MovementUtils.isMoving() || mc.thePlayer.isSneaking() ||
                (blindnessValue.get() && mc.thePlayer.isPotionActive(Potion.blindness)) ||
                (foodValue.get() && !(mc.thePlayer.getFoodStats().getFoodLevel() > 6.0F || mc.thePlayer.capabilities.allowFlying))
                || (checkServerSide.get() && (mc.thePlayer.onGround || !checkServerSideGround.get())
                && !allDirectionsValue.get() && RotationUtils.targetRotation != null &&
                RotationUtils.getRotationDifference(new Rotation(mc.thePlayer.rotationYaw, mc.thePlayer.rotationPitch)) > 30)) {
            mc.thePlayer.setSprinting(false);
            return;
        }

        if (allDirectionsValue.get() || mc.thePlayer.movementInput.moveForward >= 0.8F)
            mc.thePlayer.setSprinting(true);
    }
}
