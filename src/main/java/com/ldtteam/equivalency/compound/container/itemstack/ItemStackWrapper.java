package com.ldtteam.equivalency.compound.container.itemstack;

import com.ldtteam.equivalency.api.EquivalencyApi;
import com.ldtteam.equivalency.api.compound.container.dummy.Dummy;
import com.ldtteam.equivalency.api.compound.container.wrapper.ICompoundContainerWrapper;
import com.ldtteam.equivalency.api.util.Comparators;
import com.ldtteam.equivalency.api.util.EquivalencyLogger;
import com.ldtteam.equivalency.api.util.ItemStackUtils;
import com.ldtteam.equivalency.compound.container.registry.CompoundContainerWrapperFactoryRegistry;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import org.apache.commons.lang3.Validate;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class ItemStackWrapper implements ICompoundContainerWrapper<ItemStack>
{
    private final ItemStack stack;
    private final double count;

    public ItemStackWrapper(final ItemStack stack, final double count) {
        this.stack = stack.copy();
        this.stack.setCount(1);

        this.count = count;
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

    /**
     * Compares this object with the specified object for order.  Returns a
     * negative integer, zero, or a positive integer as this object is less
     * than, equal to, or greater than the specified object.
     *
     * <p>The implementor must ensure <tt>sgn(x.compareTo(y)) ==
     * -sgn(y.compareTo(x))</tt> for all <tt>x</tt> and <tt>y</tt>.  (This
     * implies that <tt>x.compareTo(y)</tt> must throw an exception iff
     * <tt>y.compareTo(x)</tt> throws an exception.)
     *
     * <p>The implementor must also ensure that the relation is transitive:
     * <tt>(x.compareTo(y)&gt;0 &amp;&amp; y.compareTo(z)&gt;0)</tt> implies
     * <tt>x.compareTo(z)&gt;0</tt>.
     *
     * <p>Finally, the implementor must ensure that <tt>x.compareTo(y)==0</tt>
     * implies that <tt>sgn(x.compareTo(z)) == sgn(y.compareTo(z))</tt>, for
     * all <tt>z</tt>.
     *
     * <p>It is strongly recommended, but <i>not</i> strictly required that
     * <tt>(x.compareTo(y)==0) == (x.equals(y))</tt>.  Generally speaking, any
     * class that implements the <tt>Comparable</tt> interface and violates
     * this condition should clearly indicate this fact.  The recommended
     * language is "Note: this class has a natural ordering that is
     * inconsistent with equals."
     *
     * <p>In the foregoing description, the notation
     * <tt>sgn(</tt><i>expression</i><tt>)</tt> designates the mathematical
     * <i>signum</i> function, which is defined to return one of <tt>-1</tt>,
     * <tt>0</tt>, or <tt>1</tt> according to whether the value of
     * <i>expression</i> is negative, zero or positive.
     *
     * @param o the object to be compared.
     * @return a negative integer, zero, or a positive integer as this object
     * is less than, equal to, or greater than the specified object.
     *
     * @throws NullPointerException if the specified object is null
     * @throws ClassCastException   if the specified object's type prevents it
     *                              from being compared to this object.
     */
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

        final int stackCompareResult = Comparators.ID_COMPARATOR.compare(stack, otherStack);

        if (stackCompareResult != 0)
            return stackCompareResult;

        return (int) (getContentsCount() - o.getContentsCount());
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
        return Objects.equals(stack, that.stack);
    }

    @Override
    public int hashCode()
    {
        int result;
        long temp;
        result = stack != null ? stack.hashCode() : 0;
        temp = Double.doubleToLongBits(count);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }
}
