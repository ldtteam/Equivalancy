package com.ldtteam.equivalency.api.client.drawable;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.Collection;

public interface ICombiningDrawable extends ISizedDrawable
{
    /**
     * The drawables that make up this {@link ICombiningDrawable}.
     *
     * @return The drawables.
     */
    @OnlyIn(Dist.CLIENT)
    Collection<ISizedDrawable> getDrawables();
}
