package com.ldtteam.equivalency.api.util;

import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.*;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.oredict.OreDictionary;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

public class ItemStackUtils
{

    /**
     * Variable representing the empty itemstack in 1.10.
     * Used for easy updating to 1.11
     */
    public static final ItemStack EMPTY = ItemStack.EMPTY;

    /**
     * Predicate to check if an itemStack is empty.
     */
    @NotNull
    public static final Predicate<ItemStack> EMPTY_PREDICATE = ItemStackUtils::isEmpty;

    /**
     * Negation of the itemStack empty predicate (not empty).
     */
    @NotNull
    public static final Predicate<ItemStack> NOT_EMPTY_PREDICATE = EMPTY_PREDICATE.negate();

    /**
     * The compound tag for fortune enchantment id.
     */
    private static final String NBT_TAG_ENCHANT_ID = "id";

    /**
     * The compound tag for fortune enchantment level.
     */
    private static final String NBT_TAG_ENCHANT_LEVEL = "lvl";

    /**
     * The compound id for fortune enchantment.
     */
    private static final int FORTUNE_ENCHANT_ID = 35;

    /**
     * The compound id for Silk Touch enchantment.
     */
    private static final int SILK_TOUCH_ENCHANT_ID = 33;

    /**
     * Predicate describing food.
     */
    public static final Predicate<ItemStack> ISFOOD = itemStack -> !ItemStackUtils.isEmpty(itemStack) && itemStack.getItem() instanceof ItemFood;

    /**
     * Predicate describing things which work in the furnace.
     */
    public static final Predicate<ItemStack> IS_SMELTABLE = itemStack -> !ItemStackUtils.isEmpty(FurnaceRecipes.instance().getSmeltingResult(itemStack));

    /**
     * Predicate describing cookables.
     */
    public static final Predicate<ItemStack> ISCOOKABLE = ISFOOD.and(IS_SMELTABLE);

    /**
     * Private constructor to hide the implicit one.
     */
    private ItemStackUtils()
    {
        /*
         * Intentionally left empty.
         */
    }

    /**
     * Wrapper method to check if a stack is empty.
     * Used for easy updating to 1.11.
     *
     * @param stack The stack to check.
     * @return True when the stack is empty, false when not.
     */
    @NotNull
    public static Boolean isEmpty(@Nullable final ItemStack stack)
    {
        return stack == null || stack == EMPTY || stack.getCount() <= 0;
    }

    /**
     * Calculates the max level enchantment this tool has.
     *
     * @param itemStack the tool to check.
     * @return max enchantment level.
     */
    public static int getMaxEnchantmentLevel(final ItemStack itemStack)
    {
        if (itemStack == null)
        {
            return 0;
        }
        int maxLevel = 0;
        if (itemStack != null)
        {
            final NBTTagList nbttaglist = itemStack.getEnchantmentTagList();

            if (nbttaglist != null)
            {
                for (int j = 0; j < nbttaglist.tagCount(); ++j)
                {
                    final int level = nbttaglist.getCompoundTagAt(j).getShort("lvl");
                    maxLevel = level > maxLevel ? level : maxLevel;
                }
            }
        }
        return maxLevel;
    }

    /**
     * Calculates the fortune level this tool has.
     *
     * @param tool the tool to check.
     * @return fortune level.
     */
    public static int getFortuneOf(@Nullable final ItemStack tool)
    {
        if (tool == null)
        {
            return 0;
        }
        //calculate fortune enchantment
        int fortune = 0;
        if (tool.isItemEnchanted())
        {
            final NBTTagList t = tool.getEnchantmentTagList();

            for (int i = 0; i < t.tagCount(); i++)
            {
                final int id = t.getCompoundTagAt(i).getShort(NBT_TAG_ENCHANT_ID);
                if (id == FORTUNE_ENCHANT_ID)
                {
                    fortune = t.getCompoundTagAt(i).getShort(NBT_TAG_ENCHANT_LEVEL);
                }
            }
        }
        return fortune;
    }


    public static boolean hasSilkTouch(@Nullable final ItemStack tool)
    {
        if (tool == null)
        {
            return false;
        }
        boolean hasSilk = false;
        if (tool.isItemEnchanted())
        {
            final NBTTagList t = tool.getEnchantmentTagList();

            for (int i = 0; i < t.tagCount(); i++)
            {
                final int id = t.getCompoundTagAt(i).getShort(NBT_TAG_ENCHANT_ID);
                if (id == SILK_TOUCH_ENCHANT_ID)
                {
                    hasSilk = true;
                }
            }
        }
        return hasSilk;
    }

    /**
     * Assigns a string containing the grade of the toolGrade.
     *
     * @param toolGrade the number of the grade of a tool
     * @return a string corresponding to the tool
     */
    public static String swapArmorGrade(final int toolGrade)
    {
        switch (toolGrade)
        {
            case 0:
                return "Leather";
            case 1:
                return "Gold";
            case 2:
                return "Chain";
            case 3:
                return "Iron";
            case 4:
                return "Diamond";
            default:
                return "Better than Diamond";
        }
    }

    /**
     * Assigns a string containing the grade of the armor grade.
     *
     * @param toolGrade the number of the grade of an armor
     * @return a string corresponding to the armor
     */
    public static String swapToolGrade(final int toolGrade)
    {
        switch (toolGrade)
        {
            case 0:
                return "Wood or Gold";
            case 1:
                return "Stone";
            case 2:
                return "Iron";
            case 3:
                return "Diamond";
            default:
                return "Better than Diamond";
        }
    }

    /**
     * Method to check if two ItemStacks can be merged together.
     *
     * @param existingStack The existing stack.
     * @param mergingStack  The merging stack
     * @return True when they can be merged, false when not.
     */
    @NotNull
    public static Boolean areItemStacksMergable(final ItemStack existingStack, final ItemStack mergingStack)
    {
        if (!compareItemStacksIgnoreStackSize(existingStack, mergingStack))
        {
            return false;
        }

        return existingStack.getMaxStackSize() >= (getSize(existingStack) + getSize(mergingStack));
    }

    /**
     * Method to compare to stacks, ignoring their stacksize.
     *
     * @param itemStack1 The left stack to compare.
     * @param itemStack2 The right stack to compare.
     * @return True when they are equal except the stacksize, false when not.
     */
    @NotNull
    public static Boolean compareItemStacksIgnoreStackSize(final ItemStack itemStack1, final ItemStack itemStack2)
    {
        return compareItemStacksIgnoreStackSize(itemStack1, itemStack2, true, true);
    }

    /**
     * get the size of the stack.
     * This is for compatibility between 1.10 and 1.11
     *
     * @param stack to get the size from
     * @return the size of the stack
     */
    @NotNull
    public static int getSize(final ItemStack stack)
    {
        if (ItemStackUtils.isEmpty(stack))
        {
            return 0;
        }

        return stack.getCount();
    }

    /**
     * Method to compare to stacks, ignoring their stacksize.
     *
     * @param itemStack1 The left stack to compare.
     * @param itemStack2 The right stack to compare.
     * @param matchMeta  Set to true to match meta data.
     * @param matchNBT   Set to true to match nbt
     * @return True when they are equal except the stacksize, false when not.
     */
    @NotNull
    public static Boolean compareItemStacksIgnoreStackSize(final ItemStack itemStack1, final ItemStack itemStack2, final boolean matchMeta, final boolean matchNBT)
    {
        if (!isEmpty(itemStack1) &&
              !isEmpty(itemStack2) &&
              itemStack1.getItem() == itemStack2.getItem() &&
              (itemStack1.getItemDamage() == itemStack2.getItemDamage() || !matchMeta))
        {
            // Then sort on NBT
            if (itemStack1.hasTagCompound() && itemStack2.hasTagCompound())
            {
                // Then sort on stack size
                return ItemStack.areItemStackTagsEqual(itemStack1, itemStack2) || !matchNBT;
            }
            else
            {
                return true;
            }
        }
        return false;
    }

    /**
     * Method to check if a stack is in a list of stacks.
     * @param stacks the list of stacks.
     * @param stack the stack.
     * @return true if so.
     */
    public static boolean compareItemStackListIgnoreStackSize(final List<ItemStack> stacks, final ItemStack stack)
    {
        return compareItemStackListIgnoreStackSize(stacks, stack, true, true);
    }

    /**
     * Method to check if a stack is in a list of stacks.
     * @param stacks the list of stacks.
     * @param stack the stack.
     * @param matchMeta if meta has to match.
     * @param matchNBT if nbt has to match.
     * @return true if so.
     */
    public static boolean compareItemStackListIgnoreStackSize(final List<ItemStack> stacks, final ItemStack stack, final boolean matchMeta, final boolean matchNBT)
    {
        for (final ItemStack tempStack : stacks)
        {
            if (compareItemStacksIgnoreStackSize(tempStack, stack, matchMeta, matchNBT))
            {
                return true;
            }
        }
        return false;
    }

    /**
     * set the size of the stack.
     * This is for compatibility between 1.10 and 1.11
     *
     * @param stack to set the size to
     * @param size  of the stack
     */
    @NotNull
    public static void setSize(@NotNull final ItemStack stack, final int size)
    {
        stack.setCount(size);
    }

    /**
     * Increase or decrease the stack size.
     *
     * @param stack  to set the size to
     * @param amount to increase the stack's size of (negative value to decrease)
     */
    public static void changeSize(@NotNull final ItemStack stack, final int amount)
    {
        stack.setCount(stack.getCount() + amount);
    }

    /**
     * Update method to allow for easy reading the ItemStack data from NBT.
     *
     * @param compound The compound to read from.
     * @return The ItemStack stored in the NBT Data.
     */
    @NotNull
    public static ItemStack deserializeFromNBT(@NotNull final NBTTagCompound compound)
    {
        return new ItemStack(compound);
    }

    /**
     * Check if the itemStack is some preferrable type of fuel.
     * @param stack the itemStack to test.
     * @return true if so.
     */
    public boolean isPreferrableFuel(@NotNull final ItemStack stack)
    {
        return stack.isItemEqualIgnoreDurability(new ItemStack(Items.COAL))
                 || stack.isItemEqualIgnoreDurability(new ItemStack(Blocks.LOG))
                 || stack.isItemEqualIgnoreDurability(new ItemStack(Blocks.LOG2));
    }

    /**
     * Checks if a stack is a type of sapling, using Oredict
     *
     * @param stack the stack to check.
     * @return true if sapling.
     */
    public static boolean isStackSapling(@Nullable final ItemStack stack)
    {
        if (ItemStackUtils.isEmpty(stack))
        {
            return false;
        }

        for (final int oreId : OreDictionary.getOreIDs(stack))
        {
            if (OreDictionary.getOreName(oreId).contains("sapling"))
            {
                return true;
            }
        }
        return false;
    }
}
