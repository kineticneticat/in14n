package com.kineticcat.in14n.block.multiblock;

import com.google.gson.Gson;
import com.kineticcat.in14n.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class MBPart extends BaseEntityBlock {

    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
    public IntegerProperty XPOS;
    public IntegerProperty YPOS;
    public IntegerProperty ZPOS;
    public int sizeX;
    public int sizeY;
    public int sizeZ;
    public String Name() {return null;} // to be overridden

    public MBPart(Properties properties) {
        super(properties);
        registerDefaultState(this.getStateDefinition().any()
                .setValue(FACING, Direction.NORTH)
                .setValue(XPOS, 0)
                .setValue(YPOS, 0)
                .setValue(ZPOS, 0)
        );
    }
    public void gatherData() {

        ResourceLocation file = new ResourceLocation(String.format("in14n:data/in14n/patterns/%s.json", Name()));
        String path = file.getPath();

        Util UTIL = new Util();
        String text = UTIL.getFile(path);

        Gson gson = new Gson();
        MBPattern pattern = gson.fromJson(text, MBPattern.class);

        sizeX = pattern.data[0].length;
        sizeY = pattern.data.length;
        sizeZ = pattern.data[0][0].length;



        XPOS = IntegerProperty.create("x", 0, sizeX-1);
        YPOS = IntegerProperty.create("y", 0, sizeY-1);
        ZPOS = IntegerProperty.create("z", 0, sizeZ-1);
    }
    public @NotNull RenderShape getRenderShape(@NotNull BlockState state) {
        return RenderShape.MODEL;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        gatherData();
        builder.add(FACING);
        builder.add(XPOS);
        builder.add(YPOS);
        builder.add(ZPOS);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity( BlockPos pos, BlockState state) {
        return null;
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        return null;
    }
}
