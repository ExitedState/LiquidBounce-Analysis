package net.ccbluex.keyStrokes.gui.window.windows;


import net.ccbluex.keyStrokes.gui.window.Window;
import net.ccbluex.keyStrokes.gui.window.WindowManager;
import net.ccbluex.keyStrokes.overlay.RenderedKey;

public class SubConfigWindow extends Window {

    public RenderedKey key;

    public SubConfigWindow(WindowManager windowManager) {
        super(windowManager, "");
    }

    public void setKey(RenderedKey key) {
        this.key = key;
    }
}