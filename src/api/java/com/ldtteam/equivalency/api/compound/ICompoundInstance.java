package com.ldtteam.equivalency.api.compound;

import com.ldtteam.equivalency.api.util.ITranslateable;
import org.jetbrains.annotations.NotNull;

public interface ICompoundInstance extends Comparable<ICompoundInstance>, ITranslateable
{

    /**
     * Returns the type of the instance.
     *
     * @return The type.
     */
    @NotNull
    ICompoundType getType();

    /**
     * Returns the amount stored in this instance.
     *
     * @return The amount.
     */
    @NotNull
    Double getAmount();

}
