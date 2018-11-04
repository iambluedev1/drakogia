package fr.drakogia.api.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.util.Iterator;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_12_R1.CraftServer;

public class Server {

    public static double getServerLagAverage() {
        double recentTps[] = ((CraftServer) Bukkit.getServer()).getHandle().getServer().recentTps;
        double result = 0;
        for (double d : recentTps)
            result += d;
        return result / recentTps.length;
    }
    
    public static int getServerProcessors(){
    	return Runtime.getRuntime().availableProcessors();
    }
    
    public static long getFreeMemory(){
    	return Runtime.getRuntime().freeMemory() / (1000 * 1000);
    }
    
    public static String getMaxMemory(){
    	return (Runtime.getRuntime().maxMemory() == Long.MAX_VALUE ? "no limit" : String.valueOf(Runtime.getRuntime().maxMemory() / (1000 * 1000)));
    }
    
    public static long getTotalMemory(){
    	return Runtime.getRuntime().totalMemory() / (1000 * 1000);
    }
    
    public static String getJvmArguments(){
    	RuntimeMXBean runtimemxbean = ManagementFactory.getRuntimeMXBean();
		List<String> list = runtimemxbean.getInputArguments();
		int i = 0;
		StringBuilder stringbuilder = new StringBuilder();
		Iterator<String> iterator = list.iterator();

		while(iterator.hasNext()) {
			String s = (String)iterator.next();

			if(s.startsWith("-X")) {
				if(i++ > 0) {
					stringbuilder.append(" ");
				}

				stringbuilder.append(s);
			}
		}

		return String.format("%d total; %s", new Object[] {Integer.valueOf(i), stringbuilder.toString()});
    }
    
    public static String getDedicatedServerInformation() throws IOException{
    	String line;
    	StringBuilder stringbuilder = new StringBuilder();
		Process p = Runtime.getRuntime().exec("uname -a");
		BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
		while((line = input.readLine()) != null) {
			stringbuilder.append(line);
		}
		return stringbuilder.toString();
    }
    
    public static String getDedicatedServerMemoryUsage() throws IOException{
    	String line;
    	StringBuilder stringbuilder = new StringBuilder();
		Process p = Runtime.getRuntime().exec("free -m");
		BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
		while((line = input.readLine()) != null) {
			stringbuilder.append(line);
		}
		return stringbuilder.toString();
    }
    
    public static String getDedicatedServerSpaceUsage() throws IOException {
    	String line;
    	StringBuilder stringbuilder = new StringBuilder();
		Process p = Runtime.getRuntime().exec("df -h");
		BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
		while((line = input.readLine()) != null) {
			stringbuilder.append(line);
		}
		return stringbuilder.toString();
    }
}
