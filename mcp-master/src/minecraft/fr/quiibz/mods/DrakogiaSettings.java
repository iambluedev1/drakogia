package fr.quiibz.mods;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class DrakogiaSettings {
    private final File file;
    private int x = 0;
    private int y = 0;
    private int textColor = 0;
    private boolean showingMouseButtons = false;
    private boolean enabled = true;
    private String macro1 = null;
    private String macro2 = null;
    private String macro3 = null;
    private boolean canSeeFPS = true;
    private int particule = 0;
    private boolean canSeeArmorHUD = true;
    private boolean canSeeDurability = true;
    private boolean toogleSprint = false;
    
    public DrakogiaSettings(File file) {
        this.file = file;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void load() throws IOException {
        DataInputStream in = new DataInputStream(new FileInputStream(this.file));
        Throwable localThrowable3 = null;
        try {
            this.x = in.readShort();
            this.y = in.readShort();
            this.textColor = in.readInt();
            this.showingMouseButtons = in.readBoolean();
            this.enabled = in.readBoolean();
            this.macro1 = in.readUTF();
            this.macro2 = in.readUTF();
            this.macro3 = in.readUTF();
            this.canSeeFPS = in.readBoolean();
            this.particule = in.readInt();
            this.canSeeArmorHUD = in.readBoolean();
            this.canSeeDurability = in.readBoolean();
            this.toogleSprint = in.readBoolean();
        }
        catch (Throwable localThrowable1) {
            localThrowable3 = localThrowable1;
            try {
                throw localThrowable1;
            }
            catch (Throwable e) {
                e.printStackTrace();
            }
        }
        finally {
            if (in != null) {
                if (localThrowable3 != null) {
                    try {
                        in.close();
                    }
                    catch (Throwable localThrowable2) {
                        localThrowable3.addSuppressed(localThrowable2);
                    }
                } else {
                    in.close();
                }
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void save() throws FileNotFoundException, IOException {
        DataOutputStream out = new DataOutputStream(new FileOutputStream(this.file, false));
        Throwable localThrowable3 = null;
        try {
            out.writeShort(this.x);
            out.writeShort(this.y);
            out.writeInt(this.textColor);
            out.writeBoolean(this.showingMouseButtons);
            out.writeBoolean(this.enabled);
            out.writeUTF(this.macro1);
            out.writeUTF(this.macro2);
            out.writeUTF(this.macro3);
            out.writeBoolean(this.canSeeFPS);
            out.writeInt(this.particule);
            out.writeBoolean(this.canSeeArmorHUD);
            out.writeBoolean(this.canSeeDurability);
            out.writeBoolean(this.toogleSprint);
        }
        catch (Throwable localThrowable1) {
            localThrowable3 = localThrowable1;
            try {
                throw localThrowable1;
            }
            catch (Throwable e) {
                e.printStackTrace();
            }
        }
        finally {
            if (out != null) {
                if (localThrowable3 != null) {
                    try {
                        out.close();
                    }
                    catch (Throwable localThrowable2) {
                        localThrowable3.addSuppressed(localThrowable2);
                    }
                } else {
                    out.close();
                }
            }
        }
    }

    public int getX() {
        return this.x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return this.y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getTextColor() {
        return this.textColor;
    }

    public void setTextColor(int textColor) {
        this.textColor = textColor;
    }

    public boolean isEnabled() {
        return this.enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isShowingMouseButtons() {
        return this.showingMouseButtons;
    }

    public void setShowingMouseButtons(boolean showingMouseButtons) {
        this.showingMouseButtons = showingMouseButtons;
    }
    
    public void setMacro1(String text) {
        this.macro1 = text;
    }
    
    public String getMacro1() {
    	return macro1;
    }
    
    public void setMacro2(String text) {
        this.macro2 = text;
    }
    
    public String getMacro2() {
    	return macro2;
    }
    
    public void setMacro3(String text) {
        this.macro3 = text;
    }
    
    public String getMacro3() {
    	return macro3;
    }
    
    public boolean canSeeFPS() {
    	return this.canSeeFPS;
    }
    
    public void setCanSeeFPS(boolean can) {
    	this.canSeeFPS = can;
    }
    
    public int getParticules() {
        return this.particule;
    }

    public void setParticules(int particules) {
        this.particule = particules;
    }
    
    public boolean canSeeArmorHUD() {
    	return this.canSeeArmorHUD;
    }
    
    public void setCanSeeArmorHUD(boolean can) {
    	this.canSeeArmorHUD = can;
    }
    
    public boolean canSeeDurablity() {
    	return this.canSeeDurability;
    }
    
    public void setCanSeeDurability(boolean can) {
    	this.canSeeDurability = can;
    }
    
    public boolean hasToogleSprint() {
    	return this.toogleSprint;
    }
    
    public void setToogleSprint(boolean can) {
    	this.toogleSprint = can;
    }
}
