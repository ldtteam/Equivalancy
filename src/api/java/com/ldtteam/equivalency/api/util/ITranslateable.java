package com.ldtteam.equivalency.api.util;

import net.minecraft.util.text.ITextComponent;
import org.jetbrains.annotations.NotNull;

/**
 * Represents a object that can be translated to the language of the player.
 */
public interface ITranslateable
{

    /**
     * Determines the translation key for this object.
     * @return the translation key.
     */
    @NotNull
    ITextComponent getTranslation();
}
