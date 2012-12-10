package cpw.mods.fml.relauncher;

public class CoreFMLLibraries implements ILibrarySet
{
    private static String[] libraries = { "argo-2.25.jar", "guava-12.0.1.jar", "asm-all-4.0.jar" };
    private static String[] checksums = { "bb672829fde76cb163004752b86b0484bd0a7f4b", "30787e4abf3a0492db9d25ccb1e7fea86dbaa56e", "98308890597acb64047f7e896638e0d98753ae82" };

    @Override
    public String[] getLibraries()
    {
        return libraries;
    }

    @Override
    public String[] getHashes()
    {
        return checksums;
    }

    @Override
    public String getRootURL()
    {
        return "http://files.minecraftforge.net/fmllibs/%s";
    }
    
    // MCPC
    @Override
    public String getMCPCRootURL()
    {
    	return "http://repo.md-5.net/content/groups/public/mcpc/com/google/guava/guava/12.0.1/%s";
    }
}
