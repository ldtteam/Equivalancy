package com.ldtteam.equivalency.compound.container.itemstack;

import com.google.gson.*;
import com.ldtteam.equivalency.api.compound.container.ICompoundContainer;
import com.ldtteam.equivalency.api.compound.container.dummy.Dummy;
import com.ldtteam.equivalency.api.compound.container.factory.ICompoundContainerFactory;
import com.ldtteam.equivalency.api.compound.container.serialization.ICompoundContainerSerializer;
import com.ldtteam.equivalency.api.util.Comparators;
import com.ldtteam.equivalency.api.util.EquivalencyLogger;
import com.ldtteam.equivalency.api.util.ItemStackUtils;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.util.ResourceLocation;
import org.apache.commons.lang3.Validate;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Type;
import java.util.Collection;

public class ItemStackWrapper implements ICompoundContainer<ItemStack>
{

    public static final class ItemStackFactory implements ICompoundContainerFactory<ItemStack, ItemStack>
    {

        @NotNull
        @Override
        public Class<ItemStack> getInputType()
        {
            return ItemStack.class;
        }

        @NotNull
        @Override
        public Class<ItemStack> getOutputType()
        {
            return getInputType();
        }

        @Override
        public ICompoundContainer<ItemStack> create(@NotNull final ItemStack instance, @NotNull final double count)
        {
            final ItemStack stack = instance.copy();
            stack.setCount(1);
            return new ItemStackWrapper(stack, count);
        }
    }

    public static final class ItemFactory implements ICompoundContainerFactory<Item, ItemStack>
    {

        @NotNull
        @Override
        public Class<Item> getInputType()
        {
            return Item.class;
        }

        @NotNull
        @Override
        public Class<ItemStack> getOutputType()
        {
            return ItemStack.class;
        }

        @Override
        public ICompoundContainer<ItemStack> create(@NotNull final Item instance, @NotNull final double count)
        {
            final ItemStack stack = new ItemStack(instance, 1);
            stack.setCount(1);
            return new ItemStackWrapper(stack, count);
        }
    }

    public static final class Serializer implements ICompoundContainerSerializer<ItemStack>
    {

        @Override
        public Class<ItemStack> getType()
        {
            return ItemStack.class;
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
        public ICompoundContainer<ItemStack> deserialize(final JsonElement json, final Type typeOfT, final JsonDeserializationContext context) throws JsonParseException
        {
            try
            {
                return new ItemStackWrapper(ItemStack.read(JsonToNBT.getTagFromJson(json.getAsJsonObject().get("stack").getAsString())), json.getAsJsonObject().get("count").getAsDouble());
            }
            catch (CommandSyntaxException e)
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
        public JsonElement serialize(final ICompoundContainer<ItemStack> src, final Type typeOfSrc, final JsonSerializationContext context)
        {
            final JsonObject object = new JsonObject();
            object.addProperty("count", src.getContentsCount());
            object.addProperty("stack", src.getContents().write(new CompoundNBT()).toString());
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

        final Collection<ResourceLocation> tags = stack.getItem().getTags();
        if (tags.size() > 0)
        {
            this.hashCode = tags.hashCode();
        }
        else
        {
            this.hashCode = stack.write(new CompoundNBT()).hashCode();
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

    @Override
    public int compareTo(@NotNull final ICompoundContainer<?> o)
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
        if (stack.getItem().getTags().stream().anyMatch(r -> otherStack.getItem().getTags().contains(r)))
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
        return ItemStackUtils.compareItemStacksIgnoreStackSize(stack, that.stack);
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
