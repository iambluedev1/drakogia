package fr.quiibz.mods.keystrokes;

import java.awt.Color;
import java.io.IOException;

import fr.quiibz.gui.added.GuiDrakogiaOptions;
import fr.quiibz.mods.DrakogiaMod;
import fr.quiibz.mods.DrakogiaSettings;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;

public class KeystrokesRenderer
{
  public static final int[] COLORS = { 16777215, 16711680, 65280, 255, 16776960, 11141290 };
  private final Minecraft mc = Minecraft.getMinecraft();
  private final Key[] movementKeys = new Key[4];
  private final MouseButton[] mouseButtons = new MouseButton[2];
  
  public KeystrokesRenderer()
  {
    this.movementKeys[0] = new Key(this.mc.gameSettings.keyBindForward, 26, 2);
    this.movementKeys[1] = new Key(this.mc.gameSettings.keyBindBack, 26, 26);
    this.movementKeys[2] = new Key(this.mc.gameSettings.keyBindLeft, 2, 26);
    this.movementKeys[3] = new Key(this.mc.gameSettings.keyBindRight, 50, 26);
    this.mouseButtons[0] = new MouseButton(0, 2, 50);
    this.mouseButtons[1] = new MouseButton(1, 38, 50);
  }
  
  public static void RenderKeyStrokes()
  {
    KeystrokesRenderer k = new KeystrokesRenderer();
    if ((Minecraft.getMinecraft().currentScreen != null) && 
      ((Minecraft.getMinecraft().currentScreen instanceof GuiDrakogiaOptions))) {
      try {
		Minecraft.getMinecraft().currentScreen.handleInput();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
    }
  }
  
  public void renderKeystrokes()
  {
    DrakogiaSettings settings = DrakogiaMod.getSettings();
    if (settings.isEnabled())
    {
      int x = settings.getX();
      int y = settings.getY();
      int textColor = getColor(settings.getTextColor());
      boolean showingMouseButtons = settings.isShowingMouseButtons();
      ScaledResolution res = new ScaledResolution(this.mc);
      byte width = 74;
      int height = showingMouseButtons ? 74 : 50;
      if (x < 0)
      {
        settings.setX(0);
        x = settings.getX();
      }
      else if (x > res.getScaledWidth() - width)
      {
        settings.setX(res.getScaledWidth() - width);
        x = settings.getX();
      }
      if (y < 0)
      {
        settings.setY(0);
        y = settings.getY();
      }
      else if (y > res.getScaledHeight() - height)
      {
        settings.setY(res.getScaledHeight() - height);
        y = settings.getY();
      }
      drawMovementKeys(x, y, textColor);
      if (showingMouseButtons) {
        drawMouseButtons(x, y, textColor);
      }
    }
  }
  
  public int getColor(int index)
  {
    return index == 6 ? Color.HSBtoRGB((float)(System.currentTimeMillis() % 1000L) / 1000.0F, 0.8F, 0.8F) : COLORS[index];
  }
  
  private void drawMovementKeys(int x, int y, int textColor)
  {
    Key[] var4 = this.movementKeys;
    int var5 = var4.length;
    for (int var6 = 0; var6 < var5; var6++)
    {
      Key key = var4[var6];
      key.renderKey(x, y, textColor);
    }
  }
  
  private void drawMouseButtons(int x, int y, int textColor)
  {
    MouseButton[] var4 = this.mouseButtons;
    int var5 = var4.length;
    for (int var6 = 0; var6 < var5; var6++)
    {
      MouseButton button = var4[var6];
      button.renderMouseButton(x, y, textColor);
    }
  }
}
