package com.ldtteam.equivalency.compound.test;

import com.google.gson.*;
import com.ldtteam.equivalency.api.compound.container.wrapper.ICompoundContainerWrapper;
import com.ldtteam.equivalency.api.compound.container.wrapper.ICompoundContainerWrapperFactory;
import org.assertj.core.util.Sets;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Type;
import java.util.Set;

public class TestWrapper implements ICompoundContainerWrapper<TestWrapper.Test>
{

    public static final class Test {
        private final String name;

        public Test(final String name) {this.name = name;}

        public String getName()
        {
            return name;
        }

        @Override
        public boolean equals(final Object o)
        {
            if (this == o)
            {
                return true;
            }
            if (!(o instanceof Test))
            {
                return false;
            }

            final Test test = (Test) o;

            return getName() != null ? getName().equals(test.getName()) : test.getName() == null;
        }

        @Override
        public int hashCode()
        {
            return getName() != null ? getName().hashCode() : 0;
        }

        @Override
        public String toString()
        {
            return "Test{" +
                     "name='" + name + '\'' +
                     '}';
        }
    }

    public static final class Factory implements ICompoundContainerWrapperFactory<Test>
    {

        /**
         * Returns a class that defines what type is contained in the container that this factory produces.
         * If {@code T == ItemStack} for example this will need to return {@code ItemStack.class}
         *
         * @return The class of T
         */
        @NotNull
        @Override
        public Class<Test> getContainedTypeClass()
        {
            return Test.class;
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
        public ICompoundContainerWrapper<Test> wrap(@NotNull final Test tInstance, @NotNull final double count)
        {
            return new TestWrapper(tInstance, count);
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
        public ICompoundContainerWrapper<Test> deserialize(final JsonElement json, final Type typeOfT, final JsonDeserializationContext context) throws JsonParseException
        {
            final JsonObject object = (JsonObject) json;
            return new TestWrapper(new Test(object.get("name").getAsString()), object.get("count").getAsDouble());
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
        public JsonElement serialize(final ICompoundContainerWrapper<Test> src, final Type typeOfSrc, final JsonSerializationContext context)
        {
            final JsonObject object = new JsonObject();
            object.addProperty("name", src.getContents().name);
            object.addProperty("count", src.getContentsCount());
            return object;
        }
    }

    private final Test contents;
    private final Double count;

    public TestWrapper(@NotNull final String name, final double count)
    {
        this(new Test(name), count);
    }

    public TestWrapper(final Test contents, final Double count) {
        this.contents = contents;
        this.count = count;
    }

    /**
     * The contents of this container.
     * Set to the 1 unit of the content type {@link Test}
     *
     * @return The contents.
     */
    @Override
    public Test getContents()
    {
        return contents;
    }

    /**
     * The amount of {@link Test}s contained in this wrapper.
     *
     * @return The amount.
     */
    @Override
    public Double getContentsCount()
    {
        return count;
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
        if (!(o instanceof TestWrapper))
            return -1;

        final TestWrapper t = (TestWrapper) o;
        return getContents().name.compareTo(t.getContents().name);
    }

    @Override
    public boolean equals(final Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (!(o instanceof TestWrapper))
        {
            return false;
        }

        final TestWrapper that = (TestWrapper) o;

        if (getContents() != null ? !getContents().equals(that.getContents()) : that.getContents() != null)
        {
            return false;
        }
        return count != null ? count.equals(that.count) : that.count == null;
    }

    @Override
    public int hashCode()
    {
        int result = getContents() != null ? getContents().hashCode() : 0;
        result = 31 * result + (count != null ? count.hashCode() : 0);
        return result;
    }

    @Override
    public String toString()
    {
        return "TestWrapper{" +
                 "contents=" + contents +
                 ", count=" + count +
                 '}';
    }
}
