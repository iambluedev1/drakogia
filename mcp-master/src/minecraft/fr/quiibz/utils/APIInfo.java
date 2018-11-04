package fr.quiibz.utils;

import java.net.UnknownHostException;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor;

import org.lwjgl.opengl.GL11;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.network.ServerPinger;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.util.ResourceLocation;

public class APIInfo
{
    private static final ThreadPoolExecutor tpe = new ScheduledThreadPoolExecutor(5, (new ThreadFactoryBuilder()).setNameFormat("Server Pinger #%d").setDaemon(true).build());
    private final Minecraft mc;
    private ServerData data;
    private ServerPinger pinger = new ServerPinger();
    private ResourceLocation icon;
    private DynamicTexture dt;
    private long last = 0;
    private static final String __OBFID = "CL_10000817";

    public APIInfo(String defaultName, String ip)
    {
        this.data = new ServerData(defaultName, ip, true);
        this.mc = Minecraft.getMinecraft();
        this.icon = new ResourceLocation("servers/" + ip + "/icon");
        this.dt = (DynamicTexture)this.mc.getTextureManager().getTexture(this.icon);
    }

    public void pre_init()
    {
    	init(true);
    }
    
    private void init(boolean init)
    {
    	if (init)
    	{
    		this.data.pinged = true;
        	this.data.pingToServer = -2L;
        	this.data.serverMOTD = "";
        	this.data.populationInfo = "";
    	}
    	tpe.submit(new Runnable()
    	{
    		private static final String __OBFID = "CL_0000818";
    		public void run()
    		{
    			try
    			{
    				APIInfo.this.pinger.ping(APIInfo.this.data);
    			}
    			catch (UnknownHostException var2)
    			{
    				APIInfo.this.data.pingToServer = -1L;
    				APIInfo.this.data.serverMOTD = "Can\'t resolve hostname";
    			}
    			catch (Exception var3)
    			{
    				APIInfo.this.data.pingToServer = -1L;
    				APIInfo.this.data.serverMOTD = "Can\'t connect to server.";
    			}
    		}
    	});
    }
    
    public void refresh()
    {
    	long now = System.currentTimeMillis();
    	if (now - last > 10000L)
    	{
    		last = now;
    		init(false);
    	}
    	pinger.pingPendingNetworks();
    }
    
    public void close()
    {
    	pinger.clearPendingNetworks();
    }
    
    public String getPopulation()
    {
    	return data.populationInfo;
    }    
    public String getVersion()
    {
    	return data.gameVersion;
    }
    
    public String getMOTD()
    {
    	return data.serverMOTD;
    }
    
    public String getName()
    {
    	return data.serverName;
    }
    
    public long getPing()
    {
    	return data.pingToServer;
    }
    
    public boolean getAcceptedTextures()
    {
    	return data.pinged;
    }
}