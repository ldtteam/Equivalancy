package com.ldtteam.equivalency.api.util;

import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;

public final class InventoryUtil
{

    private InventoryUtil()
    {
        throw new IllegalStateException("Tried to initialize: InventoryUtil but this is a Utility class.");
    }

    public static void insertIntoNextFreeOrDiscard(final ItemStack stack, final IItemHandlerModifiable handler) {
        for (int i = 0; i < handler.getSlots(); i++)
        {
            if (handler.getStackInSlot(i).isEmpty())
            {
                handler.setStackInSlot(i, stack);
                return;
            }
        }
    }
}
