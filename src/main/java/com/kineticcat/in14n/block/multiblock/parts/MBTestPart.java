package com.kineticcat.in14n.block.multiblock.parts;

import com.kineticcat.in14n.block.entity.ModBlockEntities;
import com.kineticcat.in14n.block.multiblock.MBPart;
import com.kineticcat.in14n.block.multiblock.parts.entity.MBTestPartEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class MBTestPart extends MBPart {


    public MBTestPart(BlockBehaviour.Properties properties) {
        super(properties, "crusher");
    }
    public String Name() {return "test";}
    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new MBTestPartEntity(pos, state);
    }

    @Override
    public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        return createTickerHelper(type, ModBlockEntities.MB_TEST_PART.get(), MBTestPartEntity::tick);
    }
}
