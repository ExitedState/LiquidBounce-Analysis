/*
 * LiquidBounce Hacked Client
 * A free open source mixin-based injection hacked client for Minecraft using Minecraft Forge.
 * https://github.com/CCBlueX/LiquidBounce/
 */
package net.ccbluex.luck.features.module.modules.misc;

import net.ccbluex.luck.event.EventTarget;
import net.ccbluex.luck.event.UpdateEvent;
import net.ccbluex.luck.features.module.Module;
import net.ccbluex.luck.features.module.ModuleCategory;
import net.ccbluex.luck.features.module.ModuleInfo;
import net.ccbluex.luck.utils.misc.RandomUtils;
import net.ccbluex.luck.utils.timer.MSTimer;
import net.ccbluex.luck.utils.timer.TimeUtils;
import net.ccbluex.luck.value.BoolValue;
import net.ccbluex.luck.value.IntegerValue;
import net.ccbluex.luck.value.TextValue;

import java.util.Random;

@ModuleInfo(name = "Spammer", description = "Spams the chat with a given message.", category = ModuleCategory.MISC)
public class Spammer extends Module {

    private final IntegerValue maxDelayValue = new IntegerValue("MaxDelay", 4900, 0, 5000) {
        @Override
        protected void onChanged(final Integer oldValue, final Integer newValue) {
            final int minDelayValueObject = minDelayValue.get();

            if(minDelayValueObject > newValue)
                set(minDelayValueObject);
            delay = TimeUtils.randomDelay(minDelayValue.get(), maxDelayValue.get());
        }
    };

    private final IntegerValue minDelayValue = new IntegerValue("MinDelay", 4800, 0, 5000) {

        @Override
        protected void onChanged(final Integer oldValue, final Integer newValue) {
            final int maxDelayValueObject = maxDelayValue.get();

            if(maxDelayValueObject < newValue)
                set(maxDelayValueObject);
            delay = TimeUtils.randomDelay(minDelayValue.get(), maxDelayValue.get());
        }
    };

    private final TextValue messageValue = new TextValue("Message", "Hello");
    private final BoolValue customValue = new BoolValue("Custom", false);

    private final MSTimer msTimer = new MSTimer();
    private long delay = TimeUtils.randomDelay(minDelayValue.get(), maxDelayValue.get());

    @EventTarget
    public void onUpdate(UpdateEvent event) {
        if(msTimer.hasTimePassed(delay)) {
            mc.thePlayer.sendChatMessage(customValue.get() ? replace(messageValue.get()) : messageValue.get() + " >" + RandomUtils.randomString(5 + new Random().nextInt(5)) + "<");
            msTimer.reset();
            delay = TimeUtils.randomDelay(minDelayValue.get(), maxDelayValue.get());
        }
    }

    private String replace(String object) {
        final Random r = new Random();

        while(object.contains("%f"))
            object = object.substring(0, object.indexOf("%f")) + r.nextFloat() + object.substring(object.indexOf("%f") + "%f".length());

        while(object.contains("%i"))
            object = object.substring(0, object.indexOf("%i")) + r.nextInt(10000) + object.substring(object.indexOf("%i") + "%i".length());

        while(object.contains("%s"))
            object = object.substring(0, object.indexOf("%s")) + RandomUtils.randomString(r.nextInt(8) + 1) + object.substring(object.indexOf("%s") + "%s".length());

        while(object.contains("%ss"))
            object = object.substring(0, object.indexOf("%ss")) + RandomUtils.randomString(r.nextInt(4) + 1) + object.substring(object.indexOf("%ss") + "%ss".length());

        while(object.contains("%ls"))
            object = object.substring(0, object.indexOf("%ls")) + RandomUtils.randomString(r.nextInt(15) + 1) + object.substring(object.indexOf("%ls") + "%ls".length());
        return object;
    }

}
