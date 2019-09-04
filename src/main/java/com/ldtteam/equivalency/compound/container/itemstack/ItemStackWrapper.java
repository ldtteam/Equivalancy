package com.ldtteam.equivalency.compound.container.itemstack;

import com.google.gson.*;
import com.ldtteam.equivalency.api.EquivalencyApi;
import com.ldtteam.equivalency.api.compound.container.dummy.Dummy;
import com.ldtteam.equivalency.api.compound.container.wrapper.ICompoundContainerWrapper;
import com.ldtteam.equivalency.api.compound.container.wrapper.ICompoundContainerWrapperFactory;
import com.ldtteam.equivalency.api.util.Comparators;
import com.ldtteam.equivalency.api.util.Constants;
import com.ldtteam.equivalency.api.util.EquivalencyLogger;
import com.ldtteam.equivalency.api.util.ItemStackUtils;
import com.ldtteam.equivalency.compound.container.registry.CompoundContainerWrapperFactoryRegistry;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.nbt.NBTException;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTUtil;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.common.crafting.JsonContext;
import net.minecraftforge.common.util.JsonUtils;
import net.minecraftforge.oredict.OreDictionary;
import org.apache.commons.lang3.Validate;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class ItemStackWrapper implements ICompoundContainerWrapper<ItemStack>
{

    public static final class Factory implements ICompoundContainerWrapperFactory<ItemStack>
    {

        /**
         * Returns a class that defines what type is contained in the container that this factory produces.
         * If {@code T == ItemStack} for example this will need to return {@code ItemStack.class}
         *
         * @return The class of T
         */
        @NotNull
        @Override
        public Class<ItemStack> getContainedTypeClass()
        {
            return ItemStack.class;
        }

        /**
         * Method used to wrap the instance of T into a wrapper that holds the relevant information and allows for sorting and
         * efficient storage and compare of two wrapped instances.
         * <p>
         * This method should "clone" the given instance and make the clone have unit length. So:
         * {@code final ItemStack clone = ItemStack.copy(); clone.setStackSize(1);}
         * for an ItemStack. Adapt for relevant T implementation.
         *
         * @param tInstance The instance to wrap.
         * @param count     The count to wrap.
         * @return The wrapped instance.
         */
        @NotNull
        @Override
        public ICompoundContainerWrapper<ItemStack> wrap(@NotNull final ItemStack tInstance, @NotNull final double count)
        {
            final ItemStack stack = tInstance.copy();
            stack.setCount(1);
            return new ItemStackWrapper(stack, count);
        }

        /**
         * Gson invokes this call-back method during deserialization when it encounters a field of the
         * specified type.
         * <p>In the implementation of this call-back method, you should consider invoking
         * {@link JsonDeserializationContext#deserialize(JsonElement, Type)} method to create objects
         * for any non-trivial field of the returned object. However, you should never invoke it on the
         * the same type passing {@code json} since that will cause an infinite loop (Gson will call your
         * call-back method again).
         *
         * @param json    The Json data being deserialized
         * @param typeOfT The type of the Object to deserialize to
         * @return a deserialized object of the specified type typeOfT which is a subclass of {@code T}
         *
         * @throws JsonParseException if json is not in the expected format of {@code typeofT}
         */
        @Override
        public ICompoundContainerWrapper<ItemStack> deserialize(final JsonElement json, final Type typeOfT, final JsonDeserializationContext context) throws JsonParseException
        {
            try
            {
                return new ItemStackWrapper(new ItemStack(JsonToNBT.getTagFromJson(json.getAsJsonObject().get("stack").getAsString())), json.getAsJsonObject().get("count").getAsDouble());
            }
            catch (NBTException e)
            {
                EquivalencyLogger.getLogger().error(e);
            }

            return null;
        }

        /**
         * Gson invokes this call-back method during serialization when it encounters a field of the
         * specified type.
         *
         * <p>In the implementation of this call-back method, you should consider invoking
         * {@link JsonSerializationContext#serialize(Object, Type)} method to create JsonElements for any
         * non-trivial field of the {@code src} object. However, you should never invoke it on the
         * {@code src} object itself since that will cause an infinite loop (Gson will call your
         * call-back method again).</p>
         *
         * @param src       the object that needs to be converted to Json.
         * @param typeOfSrc the actual type (fully genericized version) of the source object.
         * @return a JsonElement corresponding to the specified object.
         */
        @Override
        public JsonElement serialize(final ICompoundContainerWrapper<ItemStack> src, final Type typeOfSrc, final JsonSerializationContext context)
        {
            final JsonObject object = new JsonObject();
            object.addProperty("count", src.getContentsCount());
            object.addProperty("stack", src.getContents().writeToNBT(new NBTTagCompound()).toString());
            return object;
        }
    }

    private final ItemStack stack;
    private final double count;

    private final int hashCode;

    public ItemStackWrapper(final ItemStack stack, final double count) {
        this.stack = stack.copy();
        this.stack.setCount(1);

        this.count = count;

        if (stack.isEmpty())
        {
            this.hashCode = 0;
            return;
        }

        final int[] oreIds = OreDictionary.getOreIDs(stack);
        if (oreIds.length > 0)
        {
            this.hashCode = OreDictionary.getOres(OreDictionary.getOreName(oreIds[0])).get(0).writeToNBT(new NBTTagCompound()).hashCode();
        }
        else
        {
            this.hashCode = stack.writeToNBT(new NBTTagCompound()).hashCode();
        }
    }

    /**
     * The contents of this container.
     * Set to the 1 unit of the content type {@link ItemStack}
     *
     * @return The contents.
     */
    @Override
    public ItemStack getContents()
    {
        return stack;
    }

    /**
     * The amount of {@link ItemStack}s contained in this wrapper.
     *
     * @return The amount.
     */
    @Override
    public Double getContentsCount()
    {
        return count;
    }

    /**
     * Returns a set of wrappers that this wrapper is equivalent to.
     * Allows for equivalency between object types (different ItemStacks (oredic), but also ItemStack -> BlockState mappings)
     *
     * @return The equivalent mappings.
     */
    @Override
    public Set<ICompoundContainerWrapper<?>> isEquivalentTo()
    {
        //TODO Add BlockState equivalency somehow.
        final int[] oreIds = OreDictionary.getOreIDs(stack);

        return Arrays.stream(oreIds)
          .mapToObj(OreDictionary::getOreName)
          .flatMap(name -> OreDictionary.getOres(name).stream())
          .distinct()
          .map(stack -> EquivalencyApi.getInstance().getCompoundContainerWrapperFactoryRegistry().wrapInContainer(stack, (double) stack.getCount()))
          .collect(Collectors.toSet());
    }

    @Override
    public int compareTo(@NotNull final ICompoundContainerWrapper<?> o)
    {
        //Dummies are after us. :D
        if (o instanceof Dummy)
            return -1;

        final Object contents = Validate.notNull(o.getContents());
        if (!(contents instanceof ItemStack))
        {
            return ItemStack.class.getName().compareTo(contents.getClass().getName());
        }

        final ItemStack otherStack = (ItemStack) contents;
        if (OreDictionary.itemMatches(stack, otherStack, false))
            return 0;

        return Comparators.ID_COMPARATOR.compare(stack, otherStack);
    }

    @Override
    public boolean equals(final Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (!(o instanceof ItemStackWrapper))
        {
            return false;
        }

        final ItemStackWrapper that = (ItemStackWrapper) o;

        if (Double.compare(that.count, count) != 0)
        {
            return false;
        }
        return EquivalencyApi.getInstance().getItemStackEquivalentHelperRegistry().areItemStacksEquivalentExceptForStack(this.stack, that.stack);
    }

    @Override
    public int hashCode()
    {
        return hashCode;
    }

    @Override
    public String toString()
    {
        return "ItemStackWrapper{" +
                 "stack=" + stack +
                 ", count=" + count +
                 '}';
    }
}
