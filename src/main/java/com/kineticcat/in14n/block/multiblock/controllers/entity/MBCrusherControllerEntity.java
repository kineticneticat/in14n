package com.kineticcat.in14n.block.multiblock.controllers.entity;

import com.kineticcat.in14n.block.entity.ModBlockEntities;
import com.kineticcat.in14n.block.multiblock.MBControllerEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class MBCrusherControllerEntity extends MBControllerEntity {
    public MBCrusherControllerEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.MB_CRUSHER_CONTROLLER.get(), pos, state);
    }


}
