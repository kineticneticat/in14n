package com.kineticcat.in14n.block.multiblock.controllers;

import com.kineticcat.in14n.block.entity.ModBlockEntities;
import com.kineticcat.in14n.block.multiblock.MBController;
import com.kineticcat.in14n.block.multiblock.controllers.entity.MBCrusherControllerEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class MBCrusherController extends MBController {

    public MBCrusherController(Properties properties) {
        super(properties, "crusher");
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new MBCrusherControllerEntity(pos, state);
    }

    @Override
    public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        return createTickerHelper(type, ModBlockEntities.MB_CRUSHER_CONTROLLER.get(), MBCrusherControllerEntity::tick);
    }
}
