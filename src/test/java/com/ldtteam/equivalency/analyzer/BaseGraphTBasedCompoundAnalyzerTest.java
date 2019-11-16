package com.ldtteam.equivalency.analyzer;

import com.google.common.collect.Sets;
import com.ldtteam.equivalency.api.EquivalencyApi;
import com.ldtteam.equivalency.compound.SimpleCompoundInstance;
import com.ldtteam.equivalency.compound.SimpleCompoundType;
import com.ldtteam.equivalency.compound.container.heat.HeatWrapper;
import com.ldtteam.equivalency.compound.test.TestWrapper;
import com.ldtteam.equivalency.heat.Heat;
import com.ldtteam.equivalency.recipe.SimpleEquivalancyRecipe;
import cpw.mods.modlauncher.Launcher;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.event.RegistryEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mockito.Mockito;

import java.util.Optional;

public class BaseGraphTBasedCompoundAnalyzerTest
{

    public static void main(String[] args) throws Exception
    {
        final BaseGraphTBasedCompoundAnalyzerTest test = new BaseGraphTBasedCompoundAnalyzerTest();
        test.setUp();
        test.calculate();
    }

    private final Logger logger = LogManager.getLogger(BaseGraphTBasedCompoundAnalyzerTest.class);
    private       World  world;

    public void setUp() throws Exception
    {
        Launcher.main("--gameDir", "../../../../../../run");

        world = Mockito.mock(World.class);

        //Setup API
        EquivalencyApi.onRegisterNewRegistry(new RegistryEvent.NewRegistry());

        //Setup types
        EquivalencyApi.getInstance().getCompoundTypeRegistry().register(new SimpleCompoundType(new StringTextComponent("Wood")).setRegistryName(new ResourceLocation("test:wood")));
        EquivalencyApi.getInstance().getCompoundTypeRegistry().register(new SimpleCompoundType(new StringTextComponent("Burnable")).setRegistryName(new ResourceLocation("test:burnable")));

        //Setup our instances.
        EquivalencyApi.getInstance().getCompoundContainerWrapperFactoryRegistry().registerFactory(new TestWrapper.Factory());
        EquivalencyApi.getInstance().getCompoundContainerWrapperFactoryRegistry().registerFactory(new HeatWrapper.Factory());

        //Setup recipes
        EquivalencyApi.getInstance().getEquivalencyRecipeRegistry().register(
          world,
          new SimpleEquivalancyRecipe(Sets.newHashSet(
            new TestWrapper("Plank", 2)
          ),
            Sets.newHashSet(
              new TestWrapper("Stick", 4)
            )
          )
        );
        EquivalencyApi.getInstance().getEquivalencyRecipeRegistry().register(
          world,
          new SimpleEquivalancyRecipe(Sets.newHashSet(
            new TestWrapper("Log", 1)
          ),
            Sets.newHashSet(
              new TestWrapper("Plank", 4)
            )
          )
        );
        EquivalencyApi.getInstance().getEquivalencyRecipeRegistry().register(
          world,
          new SimpleEquivalancyRecipe(Sets.newHashSet(
            new TestWrapper("Log", 1)
          ),
            Sets.newHashSet(
              new HeatWrapper(new Heat(), 300d)
            )
          )
        );
        EquivalencyApi.getInstance().getEquivalencyRecipeRegistry().register(
          world,
          new SimpleEquivalancyRecipe(Sets.newHashSet(
            new TestWrapper("Plank", 3)
          ),
            Sets.newHashSet(
              new TestWrapper("Slab", 6)
            )
          )
        );


        EquivalencyApi.getInstance().getEquivalencyRecipeRegistry().register(
          world,
          new SimpleEquivalancyRecipe(Sets.newHashSet(
            new TestWrapper("Plank", 1)
          ),
            Sets.newHashSet(
              new HeatWrapper(new Heat(), 300d)
            )
          )
        );
        EquivalencyApi.getInstance().getEquivalencyRecipeRegistry().register(
          world,
          new SimpleEquivalancyRecipe(Sets.newHashSet(
            new TestWrapper("Charcoal", 1)
          ),
            Sets.newHashSet(
              new HeatWrapper(new Heat(), 1600d)
            )
          )
        );
        EquivalencyApi.getInstance().getEquivalencyRecipeRegistry().register(
          world,
          new SimpleEquivalancyRecipe(Sets.newHashSet(
            new TestWrapper("Log", 1),
            new HeatWrapper(new Heat(), 200d)
          ),
            Sets.newHashSet(
              new TestWrapper("Charcoal", 1)
            )
          )
        );

        //Set the locks
        EquivalencyApi.getInstance().getLockedCompoundWrapperToTypeRegistry().registerLocking(
          world,
          new TestWrapper("Log", 1), Sets.newHashSet(
            new SimpleCompoundInstance(
              EquivalencyApi.getInstance().getCompoundTypeRegistry().getValue(new ResourceLocation("test:wood")),
              8d
            ),
            new SimpleCompoundInstance(
              EquivalencyApi.getInstance().getCompoundTypeRegistry().getValue(new ResourceLocation("test:burnable")),
              300d
            )
          )
        );
        EquivalencyApi.getInstance().getLockedCompoundWrapperToTypeRegistry().registerLocking(
          world,
          new HeatWrapper(new Heat(), 1d), Sets.newHashSet(
            new SimpleCompoundInstance(
              EquivalencyApi.getInstance().getCompoundTypeRegistry().getValue(new ResourceLocation("test:burnable")),
              1d
            )
          )
        );

        //Set the information providers
        EquivalencyApi.getInstance().getValidCompoundTypeInformationProviderRegistry().registerNewProvider(
          world,
          Heat.class,
          (wrapper, type) -> {
              if (!type.getRegistryName().toString().equals("test:burnable"))
                  return Optional.of(false);

              return Optional.of(true);
          }
        );

        EquivalencyApi.getInstance().getValidCompoundTypeInformationProviderRegistry().registerNewProvider(
          world,
          TestWrapper.Test.class,
          (wrapper, type) -> {
              if (!wrapper.getContents().getName().equals("Charcoal"))
                  return Optional.empty();

              return Optional.of(!type.getRegistryName().toString().equals("test:wood"));
          }
        );
    }

    public void calculate()
    {
        JGraphTBasedCompoundAnalyzer analyzer = new JGraphTBasedCompoundAnalyzer(world);
        analyzer.calculate();
    }
}