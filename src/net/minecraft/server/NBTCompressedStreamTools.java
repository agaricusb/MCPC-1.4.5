package net.minecraft.server;

import cpw.mods.fml.common.Side;
import cpw.mods.fml.common.asm.SideOnly;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInput;
import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class NBTCompressedStreamTools
{
    /**
     * Load the gzipped compound from the inputstream.
     */
    public static NBTTagCompound a(InputStream var0) throws IOException
    {
        DataInputStream var1 = new DataInputStream(new BufferedInputStream(new GZIPInputStream(var0)));

        try
        {
            return a((DataInput)var1);
        }
        finally
        {
            var1.close();
        }
    }

    /**
     * Write the compound, gzipped, to the outputstream.
     */
    public static void a(NBTTagCompound var0, OutputStream var1) throws IOException
    {
        DataOutputStream var2 = new DataOutputStream(new GZIPOutputStream(var1));

        try
        {
            a(var0, (DataOutput)var2);
        }
        finally
        {
            var2.close();
        }
    }

    public static NBTTagCompound a(byte[] var0) throws IOException
    {
        DataInputStream var1 = new DataInputStream(new BufferedInputStream(new GZIPInputStream(new ByteArrayInputStream(var0))));
        NBTTagCompound var2;

        try
        {
            var2 = a((DataInput)var1);
        }
        finally
        {
            var1.close();
        }

        return var2;
    }

    public static byte[] a(NBTTagCompound var0) throws IOException
    {
        ByteArrayOutputStream var1 = new ByteArrayOutputStream();
        DataOutputStream var2 = new DataOutputStream(new GZIPOutputStream(var1));

        try
        {
            a(var0, (DataOutput)var2);
        }
        finally
        {
            var2.close();
        }

        return var1.toByteArray();
    }

    /**
     * Reads from a CompressedStream.
     */
    public static NBTTagCompound a(DataInput var0) throws IOException
    {
        NBTBase var1 = NBTBase.b(var0);

        if (var1 instanceof NBTTagCompound)
        {
            return (NBTTagCompound)var1;
        }
        else
        {
            throw new IOException("Root tag must be a named compound tag");
        }
    }

    public static void a(NBTTagCompound var0, DataOutput var1) throws IOException
    {
        NBTBase.a(var0, var1);
    }

    public static void b(NBTTagCompound var0, File var1) throws IOException
    {
        DataOutputStream var2 = new DataOutputStream(new FileOutputStream(var1));

        try
        {
            a(var0, (DataOutput)var2);
        }
        finally
        {
            var2.close();
        }
    }

    public static NBTTagCompound a(File var0) throws IOException
    {
        if (!var0.exists())
        {
            return null;
        }
        else
        {
            DataInputStream var1 = new DataInputStream(new FileInputStream(var0));
            NBTTagCompound var2;

            try
            {
                var2 = a((DataInput)var1);
            }
            finally
            {
                var1.close();
            }

            return var2;
        }
    }
}
