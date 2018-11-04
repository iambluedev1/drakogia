package fr.quiibz.gui.added;

import java.io.IOException;
import java.util.logging.Logger;

import fr.quiibz.mods.DrakogiaMod;
import fr.quiibz.mods.DrakogiaSettings;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;

public class GuiDrakogiaOptions extends GuiScreen {

	private boolean dragging = false;
    private int lastMouseX;
    private int lastMouseY;
    private GuiButton buttonEnabled;   
    private GuiButton buttonShowMouseButtons;   
    private GuiButton buttonTextColor; 
    private GuiTextField textMacro1;
    private GuiTextField textMacro2;
    private GuiTextField textMacro3;
    private GuiButton buttonCanSeeFPS; 
    private GuiButton buttonParticules;
    private GuiButton buttonCanSeeArmorHUD;
    private GuiButton buttonCanSeeDurability;
    private GuiButton buttonToogleSprint;
    private static final String[] COLOR_NAMES = new String[]{"§fBlanc", "§cRouge", "§aVert", "§3Bleu", "§eJaune", "§dViolet", "§4R§ca§6i§en§ab§2o§bw"};
    private static final String[] PARTICULES = new String[]{"§eNormal", "§6Beaucoup", "§cPlein"};
	
	private final GuiScreen lastGui;
	
	public GuiDrakogiaOptions(GuiScreen p_i1046_1_)
	    {
	        this.lastGui = p_i1046_1_;
	    }
	
	public void initGui() {
    	
    	super.initGui();
    	this.buttonList.clear();
    	
    	DrakogiaSettings settings;
        DrakogiaMod.openGui = true;
        
        this.buttonEnabled = new GuiButton(0, this.width - 125, 135, 120, 20, "§fKeystrokes: " + ((settings = DrakogiaMod.getSettings()).isEnabled() ? "§aActivé" : "§cDésactivé"));
        this.buttonList.add(this.buttonEnabled);
        this.buttonShowMouseButtons = new GuiButton(1, this.width - 125, 160, 120, 20, "§fClics: " + (settings.isShowingMouseButtons() ? "§aActivé" : "§cDésactivé"));
        this.buttonList.add(this.buttonShowMouseButtons);
        this.buttonTextColor = new GuiButton(2, this.width - 125, 185, 120, 20, "§fCouleur: " + COLOR_NAMES[settings.getTextColor()]);
        this.buttonList.add(this.buttonTextColor);
                
        this.textMacro1 = new GuiTextField(20, fontRendererObj, 5, this.height - 75, 120, 20);
        this.textMacro1.setFocused(false);
        this.textMacro2 = new GuiTextField(21, fontRendererObj, 5, this.height - 50, 120, 20);
        this.textMacro2.setFocused(false);
        this.textMacro3 = new GuiTextField(21, fontRendererObj, 5, this.height - 25, 120, 20);
        this.textMacro3.setFocused(false);
        
        if(settings.getMacro1() != null) {        	
        	this.textMacro1.setText(settings.getMacro1());     
        }
        
        if(settings.getMacro2() != null) {        	
        	this.textMacro2.setText(settings.getMacro2());        	
        }
        
        if(settings.getMacro3() != null) {        	
        	this.textMacro3.setText(settings.getMacro3());        	
        }
        
        this.buttonCanSeeFPS = new GuiButton(10, this.width - 125, 105, 120, 20, "§fFPS: " + (settings.canSeeFPS() ? "§aActivé" : "§cDésactivé"));
        this.buttonList.add(this.buttonCanSeeFPS);
        
        this.buttonList.add(new GuiButton(3, this.width / 2 - 55, this.height - 25, 120, 20, "§aTerminé"));
        
        this.buttonParticules = new GuiButton(12, this.width - 125, 80, 120, 20, "§fParticules: " + PARTICULES[settings.getParticules()]);
        this.buttonList.add(this.buttonParticules);
        
        this.buttonCanSeeArmorHUD = new GuiButton(13, this.width - 125, 55, 120, 20, "§fArmorHUD: " + (settings.canSeeArmorHUD() ? "§aActivé" : "§cDésactivé"));
        this.buttonList.add(this.buttonCanSeeArmorHUD);
        
        this.buttonCanSeeDurability = new GuiButton(14, this.width - 125, 30, 120, 20, "§fDurabilité: " + (settings.canSeeDurablity() ? "§aActivé" : "§cDésactivé"));
        this.buttonList.add(this.buttonCanSeeDurability);
        
        this.buttonToogleSprint = new GuiButton(15, this.width - 125, 5, 120, 20, "§fToogleSprint: " + (settings.hasToogleSprint() ? "§aActivé" : "§cDésactivé"));
        this.buttonList.add(this.buttonToogleSprint);
        
    }
	
	public void updateScreen() {
		
        this.textMacro1.updateCursorCounter();
        this.textMacro2.updateCursorCounter();
        this.textMacro3.updateCursorCounter();
    }
    
    protected void actionPerformed(GuiButton button)
    {     
        if (button.id == 0)
        {
        	
        }
        if (button.id == 3)
        {
        	this.mc.displayGuiScreen((GuiScreen)null);
            this.mc.setIngameFocus();
        }
        
        DrakogiaSettings settings = DrakogiaMod.getSettings();                                                                                                  
        
    	if (button.id == 0)                                                                                                                                         
    	{                                                                                                                                                           
    	settings.setEnabled(!settings.isEnabled());                                                                                                              
    	this.buttonEnabled.displayString = "§fKeystrokes: " + ((settings = DrakogiaMod.getSettings()).isEnabled() ? "§aActivé" : "§cDésactivé");
    	}                                                                                                                                                           
    	                                                                                                                                                         
    	if (button.id == 1)                                                                                                                                         
    	{                                                                                                                                                           
    	settings.setShowingMouseButtons(!settings.isShowingMouseButtons());                                                                                      
    	   this.buttonShowMouseButtons.displayString = "§fClics: " + (settings.isShowingMouseButtons() ? "§aActivé" : "§cDésactivé");                
    	}                                                                                                                                                           
    	                                                                                                                                                            
    	if (button.id == 2)                                                                                                                                         
    	{                                                                                                                                                           
    	settings.setTextColor(settings.getTextColor() == 6 ? 0 : settings.getTextColor() + 1);                                                                   
    	   this.buttonTextColor.displayString = "§fCouleur: " + COLOR_NAMES[settings.getTextColor()];                                               
    	} 
    	
    	if (button.id == 10)                                                                                                                                         
    	{                                                                                                                                                           
    	settings.setCanSeeFPS(!settings.canSeeFPS());                                                                                      
    	   this.buttonCanSeeFPS.displayString = "§fFPS: " + (settings.canSeeFPS() ? "§aActivé" : "§cDésactivé");                
    	} 
    	
    	if (button.id == 12)                                                                                                                                         
    	{                                                                                                                                                           
    	settings.setParticules(settings.getParticules() == 2 ? 0 : settings.getParticules() + 1);                                                                   
    	   this.buttonParticules.displayString = "§fParticules: " + PARTICULES[settings.getParticules()];                                               
    	}
    	
    	if (button.id == 13)                                                                                                                                         
    	{                                                                                                                                                           
    	settings.setCanSeeArmorHUD(!settings.canSeeArmorHUD());                                                                   
    	   this.buttonCanSeeArmorHUD.displayString = "§fArmorHUD: " + (settings.canSeeArmorHUD() ? "§aActivé" : "§cDésactivé");                                         
    	}
    	
    	if (button.id == 14)                                                                                                                                         
    	{                                                                                                                                                           
    	settings.setCanSeeDurability(!settings.canSeeDurablity());                                                                   
    	   this.buttonCanSeeDurability.displayString = "§fDurabilité: " + (settings.canSeeDurablity() ? "§aActivé" : "§cDésactivé");                                                             
    	}
    	
    	if (button.id == 15)                                                                                                                                         
    	{                                                                                                                                                           
    	settings.setToogleSprint(!settings.hasToogleSprint());                                                                   
    	   this.buttonToogleSprint.displayString = "§fToogleSprint: " + (settings.hasToogleSprint() ? "§aActivé" : "§cDésactivé");                                                             
    	}
        
    }
    
    public void drawScreen(int par1, int par2, float par3) {
    	         
    	this.drawDefaultBackground();
    	
    	this.drawString(fontRendererObj, "§6Macro", 50, this.height - 90, 1);
    	
    	this.textMacro1.drawTextBox();
    	this.textMacro2.drawTextBox();
    	this.textMacro3.drawTextBox();
    	
    	super.drawScreen(par1, par2, par3);
    	
    }
    
    protected void keyTyped(char p_73869_1_, int p_73869_2_)
    {
        if (this.textMacro1.isFocused())
        {
            this.textMacro1.textboxKeyTyped(p_73869_1_, p_73869_2_);
        }
        if (this.textMacro2.isFocused())
        {
            this.textMacro2.textboxKeyTyped(p_73869_1_, p_73869_2_);
        }
        if (this.textMacro3.isFocused())
        {
            this.textMacro3.textboxKeyTyped(p_73869_1_, p_73869_2_);
        }
    }
	
	public void mouseClicked(int mouseX, int mouseY, int button) {
		try {
			super.mouseClicked(mouseX, mouseY, button);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        if (button != 0) {
            return;
        }
        DrakogiaSettings settings = DrakogiaMod.getSettings();
        int startX = settings.getX();
        int startY = settings.getY();
        int endX = startX + 74;
        int endY = startY + (settings.isShowingMouseButtons() ? 74 : 50);
        if (mouseX >= startX && mouseX <= endX && mouseY >= startY && mouseY <= endY) {
            this.dragging = true;
            this.lastMouseX = mouseX;
            this.lastMouseY = mouseY;
        } else {
        	
        	this.dragging = false;
        	
        }
        
        this.textMacro1.mouseClicked(mouseX, mouseY, button);
        this.textMacro2.mouseClicked(mouseX, mouseY, button);
        this.textMacro3.mouseClicked(mouseX, mouseY, button);
    }

    protected void mouseReleased(int mouseX, int mouseY, int action) {
        this.dragging = false;
    }

    protected void mouseClickMove(int mouseX, int mouseY, int lastButtonClicked, long timeSinceMouseClick) {
        super.mouseClickMove(mouseX, mouseY, lastButtonClicked, timeSinceMouseClick);
        if (!this.dragging) {
            return;
        }
        DrakogiaSettings settings = DrakogiaMod.getSettings();
        settings.setX(settings.getX() + mouseX - this.lastMouseX);
        settings.setY(settings.getY() + mouseY - this.lastMouseY);
        this.lastMouseX = mouseX;
        this.lastMouseY = mouseY;
    }

    public void onGuiClosed() {
    	
    	DrakogiaSettings settings = DrakogiaMod.getSettings();
    	settings.setMacro1(this.textMacro1.getText());
    	settings.setMacro2(this.textMacro2.getText());
    	settings.setMacro3(this.textMacro3.getText());
    	
        try {
            DrakogiaMod.getSettings().save();
        }
        catch (IOException e) {
            Logger.getLogger("KeystrokesMod").warning("Failed to save Keystrokes settings file!");
            e.printStackTrace();
        }
        DrakogiaMod.openGui = false;
    }

    public boolean doesGuiPauseGame() {
        return false;
    }
	
}
