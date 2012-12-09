package net.minecraft.server;

import java.io.IOException;
import org.bukkit.craftbukkit.libs.jline.console.ConsoleReader;

import cpw.mods.fml.relauncher.RelaunchClassLoader;

public class FMLLogJLineBreakProxy 
{
	public static ConsoleReader reader = null;
	
    public static void consoleReaderResetPreLog()
    {
    	if (reader != null) 
    	{
    		try {
    			reader.print("\r");
    			reader.flush();
    		} catch (IOException e) { }
    	}
    }

    public static void consoleReaderResetPostLog()
    {
    	if (reader != null) 
    	{
    		try {
    			reader.drawLine();
    		} catch (Throwable ex) {
    			reader.getCursorBuffer().clear();
    		}
    		try {
    			reader.flush();
    		} catch (IOException e) { }
    	}
    }
}
