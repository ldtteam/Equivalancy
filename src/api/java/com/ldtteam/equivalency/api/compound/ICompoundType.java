package com.ldtteam.equivalency.api.compound;

import com.ldtteam.equivalency.api.util.ITranslateable;
import net.minecraftforge.registries.IForgeRegistryEntry;

/**
 * Represents a single compound that makes up part of the world.
 * Examples:
 *   * Air
 *   * Fire
 *   * Earth
 *   * Magic
 *   * Energy
 *   * etc.
 */
public interface ICompoundType extends IForgeRegistryEntry<ICompoundType>, Comparable<ICompoundType>, ITranslateable
{
}
