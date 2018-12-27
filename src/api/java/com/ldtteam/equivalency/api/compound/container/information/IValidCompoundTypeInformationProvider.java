package com.ldtteam.equivalency.api.compound.container.information;

import com.ldtteam.equivalency.api.compound.ICompoundType;
import com.ldtteam.equivalency.api.compound.container.wrapper.ICompoundContainerWrapper;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public interface IValidCompoundTypeInformationProvider<T>
{

    /**
     * Indicates which type of content is contained in the wrapper.
     *
     * @return The class of the contained type.
     */
    Class<T> getWrappedContentType();

    /**
     * Indicates to the generation system if it is even possible for a given wrapper instance to have a given compound type.
     *
     * @param wrapper The wrapper in question.
     * @param type The type to check for.
     *
     * @return An optional with nothing contained, indicating no preference. Or true or false when a preference is locked.
     */
    Optional<Boolean> canWrapperHaveCompound(@NotNull final ICompoundContainerWrapper<T> wrapper, @NotNull final ICompoundType type);
}
