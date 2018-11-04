package fr.quiibz.mods;

import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

import fr.quiibz.gui.added.GuiDrakogiaOptions;
import fr.quiibz.mods.keystrokes.KeystrokesRenderer;
import net.minecraft.client.Minecraft;

public class DrakogiaMod {
	
    public static DrakogiaSettings settings;
    public static KeystrokesRenderer renderer;
    public static boolean openGui;

    public static void init() {
        File verifior = new File(Minecraft.getMinecraft().mcDataDir, "keystrokes.dat");
        settings = new DrakogiaSettings(new File(Minecraft.getMinecraft().mcDataDir, "keystrokes.dat"));
        renderer = new KeystrokesRenderer();
        try {
            if (verifior.exists() && !verifior.isDirectory()) {
                settings.load();
            }
        }
        catch (IOException e) {
            Logger.getLogger("KeystrokesMod").warning("Failed to load Keystrokes settings file!");
            e.printStackTrace();
        }
    }

    public static void ClientDisp() {
        if (openGui) {
        	Minecraft.getMinecraft().displayGuiScreen(new GuiDrakogiaOptions(null));
            openGui = false;
        }
    }

    public static DrakogiaSettings getSettings() {
        return settings;
    }

    public static KeystrokesRenderer getRenderer() {
        return renderer;
    }

    public static void openGui() {
        openGui = true;
    }
}
