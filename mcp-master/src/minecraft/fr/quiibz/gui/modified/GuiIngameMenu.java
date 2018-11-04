package fr.quiibz.gui.modified;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import fr.quiibz.gui.added.GuiDrakogiaOptions;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiOptions;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.achievement.GuiStats;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.resources.I18n;
import net.minecraft.stats.StatisticsManager;

public class GuiIngameMenu extends GuiScreen
{
    private int saveStep;
    private int visibleTime;

    /**
     * Adds the buttons (and other controls) to the screen in question. Called when the GUI is displayed and when the
     * window resizes, the buttonList is cleared beforehand.
     */
    public void initGui()
    {
    	super.initGui();
    	this.buttonList.clear();
                
    	this.buttonList.add(new GuiButton(0, this.width / 2 - 100, this.height / 2 - 50, 200, 20, "§fReprendre la partie"));
    	this.buttonList.add(new GuiButton(1, this.width / 2 - 102, this.height / 2 - 23, 100, 20, "§fVoter"));
        this.buttonList.add(new GuiButton(2, this.width / 2 - 102, this.height / 2 + 0, 100, 20, "§fOptions Drakogia"));
        this.buttonList.add(new GuiButton(3, this.width / 2 - 102, this.height / 2 + 23, 100, 20, "§fOptions Minecraft"));         
        this.buttonList.add(new GuiButton(4, this.width / 2 + 2, this.height / 2 - 23, 100, 20, "§fSite"));
        this.buttonList.add(new GuiButton(5, this.width / 2 + 2, this.height / 2 + 0, 100, 20, "§fTeamspeak"));
        this.buttonList.add(new GuiButton(6, this.width / 2 + 1, this.height / 2 + 23, 100, 20, "§fStatistiques"));
        this.buttonList.add(new GuiButton(7, this.width / 2 - 100, this.height / 2 + 50, 200, 20, "§cDéconnexion"));
    }

    /**
     * Called by the controls from the buttonList when activated. (Mouse pressed for buttons)
     */
    protected void actionPerformed(GuiButton button) throws IOException
    {
    	if (button.id == 0)
        {
        	this.mc.displayGuiScreen((GuiScreen)null);
            this.mc.setIngameFocus();
        }
        
        if (button.id == 1)
        {
        	try {
        		  Desktop desktop = java.awt.Desktop.getDesktop();
        		  URI oURL = new URI("http://www.drakogia.fr/vote");
        		  desktop.browse(oURL);
        		} catch (Exception e) {
        		  e.printStackTrace();
        		}
        }
        
        if (button.id == 2)
        {
        	this.mc.displayGuiScreen(new GuiDrakogiaOptions(this));
        }
        
        if (button.id == 3)
        {
        	this.mc.displayGuiScreen(new GuiOptions(this, this.mc.gameSettings));
        }
        
        if (button.id == 4)
        {
        	try {
        		  Desktop desktop = java.awt.Desktop.getDesktop();
        		  URI oURL = new URI("http://www.drakogia.fr");
        		  desktop.browse(oURL);
        		} catch (Exception e) {
        		  e.printStackTrace();
        		}
        }
        
        if (button.id == 5)
        {
        	try {
                Desktop.getDesktop().browse(new URI("ts3server://ts.drakogia.fr?port=9987"));
            } catch (IOException e1) {
                e1.printStackTrace();
            } catch (URISyntaxException e1) {
                e1.printStackTrace();
            }
        }
        
        if (button.id == 6)
        {
        	this.mc.displayGuiScreen(new GuiStats(this, new StatisticsManager()));
        }
        
        if(button.id == 7) 
        {
        	button.enabled = false;
            this.mc.world.sendQuittingDisconnectingPacket();
            this.mc.loadWorld((WorldClient)null);  
            this.mc.displayGuiScreen(new GuiMainMenu());
        }
        
    }

    /**
     * Called from the main game loop to update the screen.
     */
    public void updateScreen()
    {
        super.updateScreen();
        ++this.visibleTime;
    }

    /**
     * Draws the screen and all the components in it.
     */
    public void drawScreen(int mouseX, int mouseY, float partialTicks)
    {
        this.drawDefaultBackground();
        
        super.drawScreen(mouseX, mouseY, partialTicks);
    }
}
