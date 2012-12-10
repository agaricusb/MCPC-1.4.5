package cpw.mods.fml.common;

import java.util.Set;


import cpw.mods.fml.common.versioning.ArtifactVersion;
import mcpc.com.google.common.collect.Sets.SetView;

public class MissingModsException extends RuntimeException
{

    public Set<ArtifactVersion> missingMods;

    public MissingModsException(Set<ArtifactVersion> missingMods)
    {
        this.missingMods = missingMods;
    }
}
