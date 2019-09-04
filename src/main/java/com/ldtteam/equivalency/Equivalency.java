package com.ldtteam.equivalency;

import com.ldtteam.equivalency.api.IEquivalencyAPI;
import com.ldtteam.equivalency.api.util.Constants;
import net.minecraftforge.fml.common.Mod;

import javax.annotation.Nonnull;

@Mod(modid = Constants.MOD_ID, name = Constants.MOD_NAME, version = Constants.MOD_VERSION)
public class Equivalency {

    @Nonnull
    private IEquivalencyAPI api;

    @Nonnull
    @Mod.Instance
    private static Equivalency equivalency;

    public static Equivalency getEquivalency() {
        return equivalency;
    }

    @Nonnull
    public IEquivalencyAPI getApi() {
        return api;
    }

    
}
