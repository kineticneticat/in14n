package com.kineticcat.in14n.block.multiblock.controllers.entity;

import com.kineticcat.in14n.block.entity.ModBlockEntities;
import com.kineticcat.in14n.block.multiblock.MBControllerEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public class MBTestControllerEntity extends MBControllerEntity {
    public MBTestControllerEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.MB_TEST_CONTROLLER.get(), pos, state);
    }


}
