package com.kineticcat.in14n.block.multiblock.controllers;

import com.kineticcat.in14n.block.ModBlocks;
import com.kineticcat.in14n.block.entity.ModBlockEntities;
import com.kineticcat.in14n.block.multiblock.MBController;
import com.kineticcat.in14n.block.multiblock.controllers.entity.MBTestControllerEntity;
import com.kineticcat.in14n.block.multiblock.parts.entity.MBTestPartEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class MBTestController extends MBController {

    public MBTestController(Properties properties) {
        super(properties);
    }
    @Override
    public String Name() {return "test";}
    @Override
    public BlockState getPartState() {
        return ModBlocks.MB_TEST_PART.get().defaultBlockState();
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new MBTestControllerEntity(pos, state);
    }

    @Override
    public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        return createTickerHelper(type, ModBlockEntities.MB_TEST_CONTROLLER.get(), MBTestPartEntity::tick);
    }
}
