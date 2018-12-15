package com.ldtteam.equivalency.api.client.drawable;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Collection;

public interface ICombiningDrawable extends ISizedDrawable
{
    /**
     * The drawables that make up this {@link ICombiningDrawable}.
     *
     * @return The drawables.
     */
    @SideOnly(Side.CLIENT)
    Collection<ISizedDrawable> getDrawables();
}
