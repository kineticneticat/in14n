package com.kineticcat.in14n.block.multiblock.parts.entity;

import com.kineticcat.in14n.block.entity.ModBlockEntities;
import com.kineticcat.in14n.block.multiblock.MBPartEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public class MBTestPartEntity extends MBPartEntity {
    public MBTestPartEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.MB_TEST_PART.get(), pos, state);
    }


}
