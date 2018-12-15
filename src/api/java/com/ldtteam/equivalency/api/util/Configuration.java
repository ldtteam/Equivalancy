package com.ldtteam.equivalency.api.util;

import net.minecraftforge.common.config.Config;

@Config(modid = Constants.MOD_ID)
public class Configuration
{
    @Config.Comment("All configuration related to persistence of compound information.")
    public static Persistence persistence = new Persistence();

    public static class Persistence
    {
        @Config.Comment("Enables pretty printing of persistence. Makes cache more readable and debuggable, but takes up more diskspace.")
        public boolean prettyPrint = true;
    }
}