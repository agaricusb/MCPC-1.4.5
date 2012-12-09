package net.minecraft.server;

import cpw.mods.fml.common.Side;
import cpw.mods.fml.common.asm.SideOnly;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.text.DecimalFormat;
import javax.swing.JComponent;
import javax.swing.Timer;
import net.minecraftforge.common.DimensionManager;

@SideOnly(Side.SERVER)
public class GuiStatsComponent extends JComponent
{
    private static final DecimalFormat a = new DecimalFormat("########0.000");

    /** An array containing the columns that make up the memory use graph. */
    private int[] b = new int[256];

    /**
     * Counts the number of updates. Used as the index into the memoryUse array to display the latest value.
     */
    private int c = 0;

    /** An array containing the strings displayed in this stats component. */
    private String[] d = new String[11];
    private final MinecraftServer e;

    public GuiStatsComponent(MinecraftServer var1)
    {
        this.e = var1;
        this.setPreferredSize(new Dimension(456, 246));
        this.setMinimumSize(new Dimension(456, 246));
        this.setMaximumSize(new Dimension(456, 246));
        (new Timer(500, new GuiStatsListener(this))).start();
        this.setBackground(Color.BLACK);
    }

    /**
     * Updates the stat values and calls paint to redraw the component.
     */
    private void a()
    {
        this.d = new String[5 + DimensionManager.getIDs().length];
        long var1 = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
        System.gc();
        this.d[0] = "Memory use: " + var1 / 1024L / 1024L + " mb (" + Runtime.getRuntime().freeMemory() * 100L / Runtime.getRuntime().maxMemory() + "% free)";
        this.d[1] = "Threads: " + NetworkManager.a.get() + " + " + NetworkManager.b.get();
        this.d[2] = "Avg tick: " + a.format(this.a(this.e.j) * 1.0E-6D) + " ms";
        this.d[3] = "Avg sent: " + (int)this.a(this.e.f) + ", Avg size: " + (int)this.a(this.e.g);
        this.d[4] = "Avg rec: " + (int)this.a(this.e.h) + ", Avg size: " + (int)this.a(this.e.i);

        if (this.e.worlds.size() > 0)
        {
            int var3 = 0;
            Integer[] var4 = DimensionManager.getIDs();
            int var5 = var4.length;

            for (int var6 = 0; var6 < var5; ++var6)
            {
                Integer var7 = var4[var6];
                this.d[5 + var3] = "Lvl " + var7 + " tick: " + a.format(this.a((long[])this.e.worldTickTimes.get(var7)) * 1.0E-6D) + " ms";
                WorldServer var8 = DimensionManager.getWorld(var7.intValue());

                if (var8 != null && var8.chunkProviderServer != null)
                {
                    this.d[5 + var3] = this.d[5 + var3] + ", " + var8.chunkProviderServer.getName();
                    this.d[5 + var3] = this.d[5 + var3] + ", Vec3: " + var8.getVec3DPool().d() + " / " + var8.getVec3DPool().c();
                }

                ++var3;
            }
        }

        this.b[this.c++ & 255] = (int)(this.a(this.e.g) * 100.0D / 12500.0D);
        this.repaint();
    }

    private double a(long[] var1)
    {
        long var2 = 0L;
        long[] var4 = var1;
        int var5 = var1.length;

        for (int var6 = 0; var6 < var5; ++var6)
        {
            long var7 = var4[var6];
            var2 += var7;
        }

        return (double)var2 / (double)var1.length;
    }

    public void paint(Graphics var1)
    {
        var1.setColor(new Color(16777215));
        var1.fillRect(0, 0, 456, 246);
        int var2;

        for (var2 = 0; var2 < 256; ++var2)
        {
            int var3 = this.b[var2 + this.c & 255];
            var1.setColor(new Color(var3 + 28 << 16));
            var1.fillRect(var2, 100 - var3, 1, var3);
        }

        var1.setColor(Color.BLACK);

        for (var2 = 0; var2 < this.d.length; ++var2)
        {
            String var4 = this.d[var2];

            if (var4 != null)
            {
                var1.drawString(var4, 32, 116 + var2 * 16);
            }
        }
    }

    /**
     * Public static accessor to call updateStats.
     */
    static void a(GuiStatsComponent var0)
    {
        var0.a();
    }
}
