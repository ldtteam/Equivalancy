package com.ldtteam.equivalency.data.recipe.singularity.transmutation;

import com.google.common.collect.Lists;
import com.ldtteam.datagenerators.recipes.RecipeIngredientJson;
import com.ldtteam.datagenerators.recipes.RecipeIngredientKeyJson;
import com.ldtteam.equivalency.api.util.Constants;
import net.minecraft.data.DataGenerator;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.GatherDataEvent;

import java.util.List;

@Mod.EventBusSubscriber(modid = Constants.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class RedstoneSingularityTransmutationRecipeDataGenerator extends AbstractSingularityTransmutationRecipeDataGenerator
{
    @SubscribeEvent
    public static void dataGeneratorSetup(final GatherDataEvent event)
    {
        event.getGenerator().addProvider(new RedstoneSingularityTransmutationRecipeDataGenerator(event.getGenerator()));
    }

    private RedstoneSingularityTransmutationRecipeDataGenerator(final DataGenerator generator)
    {
        super(generator);
    }

    @Override
    protected Item getItem()
    {
        return Items.REDSTONE;
    }

    @Override
    protected List<RecipeIngredientKeyJson> getIngredients()
    {
        return Lists.newArrayList(
          new RecipeIngredientKeyJson(
            new RecipeIngredientJson("minecraft:coal", false)
          ),
          new RecipeIngredientKeyJson(
            new RecipeIngredientJson("minecraft:coal", false)
          )
        );
    }

    @Override
    protected float getMassIncrease()
    {
        return 40f;
    }

    @Override
    public String getName()
    {
        return "Redstone singularity crafting";
    }
}
