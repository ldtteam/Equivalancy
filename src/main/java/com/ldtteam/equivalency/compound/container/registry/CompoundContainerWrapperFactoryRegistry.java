package com.ldtteam.equivalency.compound.container.registry;

import com.google.gson.*;
import com.ldtteam.equivalency.api.compound.container.dummy.Dummy;
import com.ldtteam.equivalency.api.compound.container.ICompoundContainer;
import com.ldtteam.equivalency.api.compound.container.wrapper.ICompoundContainerWrapperFactory;
import com.ldtteam.equivalency.api.compound.container.registry.ICompoundContainerWrapperFactoryRegistry;
import com.ldtteam.equivalency.api.util.CompositeType;
import com.ldtteam.equivalency.api.util.Configuration;
import com.ldtteam.equivalency.api.util.EquivalencyLogger;
import com.ldtteam.equivalency.api.util.Suppression;
import net.minecraft.util.Tuple;
import org.apache.commons.lang3.Validate;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiFunction;
import java.util.function.Function;

import static org.apache.commons.lang3.Validate.notNull;

public class CompoundContainerWrapperFactoryRegistry implements ICompoundContainerWrapperFactoryRegistry
{
    private static final CompoundContainerWrapperFactoryRegistry INSTANCE = new CompoundContainerWrapperFactoryRegistry();

    public static CompoundContainerWrapperFactoryRegistry getInstance()
    {
        return INSTANCE;
    }

    private final Map<Class<?>, ICompoundContainerWrapperFactory<?>> factoryMappings = new ConcurrentHashMap<>();
    private final Map<Tuple<Class<?>, Class<?>>, Function<?, ?>> converters = new ConcurrentHashMap<>();

    private CompoundContainerWrapperFactoryRegistry()
    {
    }

    /**
     * Registers a new container factory to this registry.
     *
     * @param factory The compound container factory to register.
     * @param <T> The type of compound container (ItemStack, FluidStack, IBlockState etc).
     */
    @Override
    public <T> ICompoundContainerWrapperFactoryRegistry registerFactory(@NotNull final ICompoundContainerWrapperFactory<T> factory)
    {
        notNull(factory);
        factoryMappings.put(factory.getContainedTypeClass(), factory);
        return this;
    }

    @Override
    public <T, R> ICompoundContainerWrapperFactoryRegistry registerConverter(
      @NotNull final Class<T> input, @NotNull final Class<R> output, @NotNull final Function<T, R> converter)
    {
        if(converters.keySet().stream().anyMatch(t -> t.getA() == input))
            return this;

        converters.putIfAbsent(new Tuple<>(input, output), converter);
        return this;
    }

    /**
     * Utility method to check if a given class of a compound container can be wrapped properly.
     *
     * @param clazz The class to check
     * @param <T> The type of the compound container to check.
     * @return True when a factory for type T is registered false when not.
     */
    public <T> boolean canBeWrapped(@NotNull final Class<T> clazz)
    {
        notNull(clazz);
        return getFactoryForType(clazz).isPresent();
    }

    /**
     * Utility method to check if a given instance of a possible compound container can be wrapped properly.
     *
     * @param tInstance The instance to check
     * @param <T> The type of the compound container to check.
     * @return True when a factory for type T is registered false when not.
     */
    public <T> boolean canBeWrapped(@NotNull final T tInstance)
    {
        notNull(tInstance);
        return canBeWrapped(tInstance.getClass());
    }

    /**
     * Wraps the given compound container.
     *
     * @param tInstance The instance of T to wrap. Will be brought to unit length by the factory (ItemStacks will be copied and have stack size 1).
     * @param count The count to store in the container. For ItemStacks this will be the stacks size.
     * @param <T> The type of the compound container to create.
     * @throws IllegalArgumentException When T can not be wrapped properly {@code canBeWrapped(tInstance) == false;}
     *
     * @return The wrapped instance.
     */
    @SuppressWarnings(Suppression.UNCHECKED)
    @NotNull
    public <T> ICompoundContainer<T> wrapInContainer(@NotNull final T tInstance, @NotNull final Double count) throws IllegalArgumentException
    {
        notNull(tInstance);
        final Class<T> tClass = (Class<T>) tInstance.getClass();
        return getFactoryForType(tClass).map(factory -> factory.wrap(tInstance, count)).orElseThrow(() -> new IllegalArgumentException("Unknown wrapping type."));
    }

    /**
     * Returns a JSON handler that is capable of reading and writing, all instances to disk.
     * Both collections as well as individual instances are supported.
     *
     * @return The JSON handler.
     */
    @NotNull
    public Gson getJsonHandler()
    {
        final GsonBuilder builder = new GsonBuilder()
          .setLenient()
          .registerTypeAdapter(ICompoundContainer.class, new JSONWrapperHandler(this));

        if (Configuration.persistence.prettyPrint)
            builder.setPrettyPrinting();

        return builder.create();
    }

    /**
     * Internal method to get a factory of a given type.
     *
     * @param output The class of the type to get the factory for.
     * @param <T> The type to get the wrapping factory for.
     * @return An optional, possibly containing the requested factory if registered.
     */
    @NotNull
    @SuppressWarnings(Suppression.UNCHECKED)
    private <T> Optional<ICompoundContainerWrapperFactory<T>> getFactoryForType(@NotNull final Class<T> output)
    {
        notNull(output);
        return Optional.ofNullable((ICompoundContainerWrapperFactory<T>) factoryMappings.get(output));
    }

    @SuppressWarnings({"unchecked", "SuspiciousMethodCalls"})
    @NotNull
    private <T, R> Optional<Function<T, R>> getConverter(@NotNull final Class<T> input, @NotNull final Class<R> output)
    {
        return Optional.ofNullable((Function<T, R>) converters.get(new Tuple<>(input, output)));
    }

    @SuppressWarnings({"unchecked", "SuspiciousMethodCalls"})
    @NotNull
    private <T> Optional<? extends Map.Entry<Tuple<Class<T>,Class<?>>, Function<T, ?>>> getConverter(@NotNull final Class<T> input)
    {
        return converters.entrySet()
                 .stream()
                 .filter(e -> e.getKey().getA() == input)
                 .map(e -> new HashMap.SimpleEntry<Tuple<Class<T>, Class<?>>, Function<T, ?>>(new Tuple<>((Class<T>) e.getKey().getA(), (Class<?>) e.getKey().getB()), (Function<T, ?>) e.getValue()))
                 .findFirst();
    }

    @SuppressWarnings("SuspiciousMethodCalls")
    @NotNull
    private <T, R> Optional<ICompoundContainerWrapperFactory<R>> getFactoryWithConversion(@NotNull final Class<T> input, @NotNull final Class<R> output)
    {
        if (input == output || converters.containsKey(new Tuple<>(input, output)))
            return getFactoryForType(output);

        return Optional.empty();
    }

    private <T> Optional<BiFunction<T, Double, ICompoundContainer<?>>> getWrapperExecutor(@NotNull final Class<T> input)
    {



        return getConverter(input).map(converterData -> {
            return new Tuple<Class<?>, Function<T, ?>>(converterData.getKey().getB(), (T inputInstance) -> converterData.getValue().apply(inputInstance));
        }).map(converterLogic -> {
            return getFactoryForType(converterLogic.getA()).map(factory -> {
                return (BiFunction<T, Double, ICompoundContainerWrapperFactory<?>>) (t, aDouble) -> (ICompoundContainerWrapperFactory<?>) factory.wrap(converterLogic.getB().apply(t), aDouble);
            }).get();
        })
    }

    private final class JSONWrapperHandler implements JsonSerializer<ICompoundContainer<?>>, JsonDeserializer<ICompoundContainer<?>>
    {

        private final CompoundContainerWrapperFactoryRegistry registry;

        private JSONWrapperHandler(final CompoundContainerWrapperFactoryRegistry registry) {this.registry = registry;}

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
        public ICompoundContainer<?> deserialize(final JsonElement json, final Type typeOfT, final JsonDeserializationContext context) throws JsonParseException
        {
            if (!json.isJsonObject())
                throw new IllegalArgumentException("Json is not an object");

            final JsonObject jsonObject = (JsonObject) json;
            if (!jsonObject.has("type"))
                throw new IllegalArgumentException("Json does not contain wrapper type");
            if (!jsonObject.has("data"))
                throw new IllegalArgumentException("Json does not contain wrapper data");

            final Class<?> clz;
            try
            {
                 clz = Class.forName(jsonObject.get("type").getAsString());
            }
            catch (ClassNotFoundException e)
            {
                EquivalencyLogger.bigWarningSimple("Unknown class: %s. Maybe save types have changed. Storing in dummy.", jsonObject.get("type"));
                return new Dummy(jsonObject);
            }

            final ICompoundContainerWrapperFactory<?> factory = registry.getFactoryForType(clz).orElse(null);
            if (factory == null)
            {
                EquivalencyLogger.bigWarningWithStackTrace("Unknown factory: %s. No one registered a factory. Storing in dummy.", jsonObject.get("type"));
                return new Dummy(jsonObject);
            }

            final CompositeType compositeType = new CompositeType(ICompoundContainer.class, clz);
            final Gson partSerializer = buildJsonForClass(compositeType, factory);

            return partSerializer.fromJson(jsonObject.get("data"), compositeType);
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
        public JsonElement serialize(final ICompoundContainer<?> src, final Type typeOfSrc, final JsonSerializationContext context)
        {
            //In case of old dummy data. We just return the original data.
            if (src instanceof Dummy)
                return ((Dummy) src).getOriginalData();

            Validate.notNull(src.getContents());
            final Class<?> clz = src.getContents().getClass();
            final CompositeType compositeType = new CompositeType(ICompoundContainer.class, clz);
            final ICompoundContainerWrapperFactory<?> factory = registry.getFactoryForType(clz).orElseThrow(() -> new IllegalArgumentException(String.format(
              "No known factory for type: %s",
              clz.getName())));

            final Gson partSerializer = buildJsonForClass(compositeType, factory);

            final JsonElement data = partSerializer.toJsonTree(src, compositeType);

            final JsonObject object = new JsonObject();
            object.addProperty("type", clz.getName());
            object.add("data", data);
            return object;
        }

        /**
         * Builds a serializer for a given composite type and factory.
         *
         * @param tClass The composite type containing the correct type information for the wrapper and its generic type.
         * @param jsonHandler The factory that functions as json handler.
         *
         * @return The json handler.
         */
        private Gson buildJsonForClass(@NotNull final CompositeType tClass, @NotNull final ICompoundContainerWrapperFactory<?> jsonHandler)
        {
            final GsonBuilder builder = new GsonBuilder()
              .registerTypeAdapter(tClass, jsonHandler)
              .setLenient();

            if (Configuration.persistence.prettyPrint)
            {
                builder.setPrettyPrinting();
            }

            return builder.create();
        }
    }
}
