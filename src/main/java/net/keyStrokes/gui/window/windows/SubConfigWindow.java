package net.keyStrokes.gui.window.windows;


import net.keyStrokes.gui.window.Window;
import net.keyStrokes.gui.window.WindowManager;
import net.keyStrokes.overlay.RenderedKey;

public class SubConfigWindow extends Window {

    public RenderedKey key;

    public SubConfigWindow(WindowManager windowManager) {
        super(windowManager, "");
    }

    public void setKey(RenderedKey key) {
        this.key = key;
    }
}