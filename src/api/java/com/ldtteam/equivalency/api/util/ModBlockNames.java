package com.ldtteam.equivalency.api.util;

import net.minecraft.util.ResourceLocation;

public final class ModBlockNames
{

    private ModBlockNames()
    {
        throw new IllegalStateException("Tried to initialize: ModBlockNames but this is a Utility class.");
    }

    public static final class Path {
        public static final class Block {
            public static final String UNSHAPED_TNT_STICK = "tnt_stick_unshaped";
            public static final String SHAPED_TNT_STICK = "tnt_stick_shaped";
            public static final String TNT_STACK_STABLE = "tnt_stack_stable";
        }
    }

    public static final class Name {
        public static final class Block {
            public static final ResourceLocation UNSHAPED_TNT_STICK = new ResourceLocation(Constants.MOD_ID, Path.Block.UNSHAPED_TNT_STICK);
            public static final ResourceLocation SHAPED_TNT_STICK = new ResourceLocation(Constants.MOD_ID, Path.Block.SHAPED_TNT_STICK);
            public static final ResourceLocation TNT_STACK_STABLE = new ResourceLocation(Constants.MOD_ID, Path.Block.TNT_STACK_STABLE);
        }
    }
}
