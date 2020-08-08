package com.ldtteam.equivalency.api.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public final class Constants
{

    private Constants()
    {
        throw new IllegalStateException("Tried to initialize: Constants but this is a Utility class.");
    }

    public static final String MOD_ID = "equivalency";
    public static final String MOD_NAME = "Equivalancy";
    public static final String MOD_VERSION = "%VERSION%";

    public static class DataGenerator {

        public static final  Gson   GSON                           = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
        public static final  String EN_US_LANG                     = "assets/" + Constants.MOD_ID + "/lang/en_us.json";
        public static final  String ITEM_MODEL_DIR                 = "assets/" + Constants.MOD_ID + "/models/item/";
        private static final String DATAPACK_DIR                   = "data/" + MOD_ID + "/";
        public static final  String RECIPES_DIR                    = DATAPACK_DIR + "recipes/";
        public static final  String TAGS_DIR                       = DATAPACK_DIR + "tags/";
        public static final  String FORGE_TAGS_DIR                 = "data/forge/" + "tags/";
        public static final  String LOOT_TABLES_DIR                = DATAPACK_DIR + "loot_tables/blocks";
        private static final String RESOURCEPACK_DIR               = "assets/" + MOD_ID + "/";
        public static final  String BLOCKSTATE_DIR                 = RESOURCEPACK_DIR + "blockstates/";
        public static final  String TIMBER_FRAMES_BLOCK_MODELS_DIR = RESOURCEPACK_DIR + "models/block/timber_frames/";
        public static final  String SHINGLE_SLABS_BLOCK_MODELS_DIR = RESOURCEPACK_DIR + "models/block/shingle_slab/";
        public static final  String SHINGLES_BLOCK_MODELS_DIR      = RESOURCEPACK_DIR + "models/block/shingle/";
    }
}
