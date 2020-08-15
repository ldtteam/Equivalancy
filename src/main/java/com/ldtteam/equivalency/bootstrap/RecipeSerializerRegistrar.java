package com.ldtteam.equivalency.bootstrap;

import com.ldtteam.equivalency.api.util.Constants;
import com.ldtteam.equivalency.api.util.ModRecipeSerializerNames;
import com.ldtteam.equivalency.recipe.singularity.transmutation.SingularityTransmutationRecipe;
import net.minecraft.block.Block;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.IForgeRegistry;
import org.jetbrains.annotations.NotNull;

@Mod.EventBusSubscriber(modid = Constants.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public final class RecipeSerializerRegistrar
{

    private RecipeSerializerRegistrar()
    {
        throw new IllegalStateException("Tried to initialize: RecipeSerializerRegistrar but this is a Utility class.");
    }

    @SubscribeEvent
    public static void registerRecipeSerializers(@NotNull final RegistryEvent.Register<IRecipeSerializer<?>> event)
    {
        final IForgeRegistry<IRecipeSerializer<?>> registry = event.getRegistry();

        registry.registerAll(
          new SingularityTransmutationRecipe.Serializer().setRegistryName(ModRecipeSerializerNames.Name.SINGULARITY_TRANSMUTATION)
        );
    }
}
