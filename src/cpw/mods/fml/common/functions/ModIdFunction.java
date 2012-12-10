package cpw.mods.fml.common.functions;


import cpw.mods.fml.common.ModContainer;
import mcpc.com.google.common.base.Function;

public final class ModIdFunction implements Function<ModContainer, String>
{
    public String apply(ModContainer container)
    {
        return container.getModId();
    }
}