package cpw.mods.fml.common.functions;


import cpw.mods.fml.common.ModContainer;
import mcpc.com.google.common.base.Function;

public class ModNameFunction implements Function<ModContainer, String>
{
    @Override
    public String apply(ModContainer input)
    {
        return input.getName();
    }

}
