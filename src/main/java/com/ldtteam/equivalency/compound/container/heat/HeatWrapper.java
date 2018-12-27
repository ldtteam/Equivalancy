package com.ldtteam.equivalency.compound.container.heat;

import com.google.common.collect.Sets;
import com.google.gson.*;
import com.ldtteam.equivalency.api.compound.container.wrapper.ICompoundContainerWrapper;
import com.ldtteam.equivalency.api.compound.container.wrapper.ICompoundContainerWrapperFactory;
import com.ldtteam.equivalency.heat.Heat;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Set;

public class HeatWrapper implements ICompoundContainerWrapper<Heat>
{

    public static final class Factory implements ICompoundContainerWrapperFactory<Heat>
    {

        /**
         * Returns a class that defines what type is contained in the container that this factory produces.
         * If {@code T == ItemStack} for example this will need to return {@code ItemStack.class}
         *
         * @return The class of T
         */
        @NotNull
        @Override
        public Class<Heat> getContainedTypeClass()
        {
            return Heat.class;
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
        public ICompoundContainerWrapper<Heat> wrap(@NotNull final Heat tInstance, @NotNull final double count)
        {
            return new HeatWrapper(tInstance, count);
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
        public ICompoundContainerWrapper<Heat> deserialize(final JsonElement json, final Type typeOfT, final JsonDeserializationContext context) throws JsonParseException
        {
            return new HeatWrapper(new Heat(), json.getAsDouble());
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
        public JsonElement serialize(final ICompoundContainerWrapper<Heat> src, final Type typeOfSrc, final JsonSerializationContext context)
        {
            return new JsonPrimitive(src.getContentsCount());
        }
    }

    private final Heat heat;
    private final Double count;

    public HeatWrapper(final Heat heat, final Double count)
    {
        this.heat = heat;
        this.count = count;
    }

    /**
     * The contents of this container.
     * Set to the 1 unit of the content type {@link Heat}
     *
     * @return The contents.
     */
    @Override
    public Heat getContents()
    {
        return heat;
    }

    /**
     * The amount of {@link Heat}s contained in this wrapper.
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
        return Sets.newHashSet();
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
        return !(o instanceof HeatWrapper) ? -1 : (int) (getContentsCount() - o.getContentsCount());
    }

    /**
     * Returns a hash code value for the object. This method is
     * supported for the benefit of hash tables such as those provided by
     * {@link HashMap}.
     * <p>
     * The general contract of {@code hashCode} is:
     * <ul>
     * <li>Whenever it is invoked on the same object more than once during
     * an execution of a Java application, the {@code hashCode} method
     * must consistently return the same integer, provided no information
     * used in {@code equals} comparisons on the object is modified.
     * This integer need not remain consistent from one execution of an
     * application to another execution of the same application.
     * <li>If two objects are equal according to the {@code equals(Object)}
     * method, then calling the {@code hashCode} method on each of
     * the two objects must produce the same integer result.
     * <li>It is <em>not</em> required that if two objects are unequal
     * according to the {@link Object#equals(Object)}
     * method, then calling the {@code hashCode} method on each of the
     * two objects must produce distinct integer results.  However, the
     * programmer should be aware that producing distinct integer results
     * for unequal objects may improve the performance of hash tables.
     * </ul>
     * <p>
     * As much as is reasonably practical, the hashCode method defined by
     * class {@code Object} does return distinct integers for distinct
     * objects. (This is typically implemented by converting the internal
     * address of the object into an integer, but this implementation
     * technique is not required by the
     * Java&trade; programming language.)
     *
     * @return a hash code value for this object.
     *
     * @see Object#equals(Object)
     * @see System#identityHashCode
     */
    @Override
    public int hashCode()
    {
        return getContentsCount().hashCode();
    }

    /**
     * Indicates whether some other object is "equal to" this one.
     * <p>
     * The {@code equals} method implements an equivalence relation
     * on non-null object references:
     * <ul>
     * <li>It is <i>reflexive</i>: for any non-null reference value
     * {@code x}, {@code x.equals(x)} should return
     * {@code true}.
     * <li>It is <i>symmetric</i>: for any non-null reference values
     * {@code x} and {@code y}, {@code x.equals(y)}
     * should return {@code true} if and only if
     * {@code y.equals(x)} returns {@code true}.
     * <li>It is <i>transitive</i>: for any non-null reference values
     * {@code x}, {@code y}, and {@code z}, if
     * {@code x.equals(y)} returns {@code true} and
     * {@code y.equals(z)} returns {@code true}, then
     * {@code x.equals(z)} should return {@code true}.
     * <li>It is <i>consistent</i>: for any non-null reference values
     * {@code x} and {@code y}, multiple invocations of
     * {@code x.equals(y)} consistently return {@code true}
     * or consistently return {@code false}, provided no
     * information used in {@code equals} comparisons on the
     * objects is modified.
     * <li>For any non-null reference value {@code x},
     * {@code x.equals(null)} should return {@code false}.
     * </ul>
     * <p>
     * The {@code equals} method for class {@code Object} implements
     * the most discriminating possible equivalence relation on objects;
     * that is, for any non-null reference values {@code x} and
     * {@code y}, this method returns {@code true} if and only
     * if {@code x} and {@code y} refer to the same object
     * ({@code x == y} has the value {@code true}).
     * <p>
     * Note that it is generally necessary to override the {@code hashCode}
     * method whenever this method is overridden, so as to maintain the
     * general contract for the {@code hashCode} method, which states
     * that equal objects must have equal hash codes.
     *
     * @param obj the reference object with which to compare.
     * @return {@code true} if this object is the same as the obj
     * argument; {@code false} otherwise.
     *
     * @see #hashCode()
     * @see HashMap
     */
    @Override
    public boolean equals(final Object obj)
    {
        if (!(obj instanceof HeatWrapper))
            return false;

        return ((HeatWrapper) obj).getContentsCount().equals(getContentsCount());
    }

    @Override
    public String toString()
    {
        return "HeatWrapper{" +
                 "count=" + count +
                 '}';
    }
}
