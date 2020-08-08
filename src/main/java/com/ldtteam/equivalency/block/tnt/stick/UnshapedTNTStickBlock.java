package com.ldtteam.equivalency.block.tnt.stick;

import com.ldtteam.equivalency.api.util.ModBlockNames;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.util.Direction;

import static net.minecraft.state.properties.BlockStateProperties.FACING;

public class UnshapedTNTStickBlock extends AbstractTNTStickBlock
{

    public UnshapedTNTStickBlock()
    {
        this.setRegistryName(ModBlockNames.Name.Block.UNSHAPED_TNT_STICK);
    }
}
