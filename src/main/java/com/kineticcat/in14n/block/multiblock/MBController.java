package com.kineticcat.in14n.block.multiblock;

import com.mojang.logging.LogUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
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
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;

import java.util.Objects;

public class MBController extends BaseEntityBlock {

    private MBPattern pattern;
    private final Logger LOGGER = LogUtils.getLogger();
    public int sizeX;
    public int sizeY;
    public int sizeZ;
    public BlockState getPartState() {return null;}
    public String Name() {return null;} // to be overridden
    public  DirectionProperty FACING;
    public BooleanProperty ACTIVE;
    // local coords b/c annoying stuff
    public IntegerProperty LXPOS;
    public IntegerProperty LYPOS;
    public IntegerProperty LZPOS;
    private Direction direction;

    public MBController(Properties properties) {
        super(properties);

        registerDefaultState(this.getStateDefinition().any()
                .setValue(FACING, Direction.NORTH)
                .setValue(ACTIVE, Boolean.FALSE)
        );

    }
    public void gatherData() {

        pattern = MBPattern.fromFile(String.format("in14n:data/in14n/patterns/%s.json", Name()));

        sizeX = pattern.data[0].length;
        sizeY = pattern.data.length;
        sizeZ = pattern.data[0][0].length;

        fixDataOrder();

        LXPOS = IntegerProperty.create("x", 0, sizeX-1);
        LYPOS = IntegerProperty.create("y", 0, sizeY-1);
        LZPOS = IntegerProperty.create("z", 0, sizeZ-1);

        // why
        ACTIVE = BooleanProperty.create("active");
        FACING = BlockStateProperties.HORIZONTAL_FACING;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        gatherData();
        builder.add(FACING);
        builder.add(ACTIVE);
    }

    public @NotNull RenderShape getRenderShape(@NotNull BlockState state) {
        return RenderShape.MODEL;
    }

    @Override
    @NotNull
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {

        if (!level.isClientSide) {
            Direction found = test(level, pos);
            switch (found) {
                case NORTH -> handleCreation(level, pos, Direction.NORTH);
                case SOUTH -> handleCreation(level, pos, Direction.SOUTH);
                case EAST -> handleCreation(level, pos, Direction.EAST);
                case WEST -> handleCreation(level, pos, Direction.WEST);
                case UP -> handleInvalid(level, pos);
            }

        }

        return InteractionResult.SUCCESS;
    }

    public void fixDataOrder() {

        pattern.offset = new int[]{pattern.offset[1], pattern.offset[0], pattern.offset[2]};

        String[][][] newData = new String[sizeX][sizeY][sizeZ];
        for (int y=0; y<sizeY; y++) {
            for (int x=0; x<sizeX; x++) {
                System.arraycopy(pattern.data[y][x], 0, newData[x][y], 0, sizeZ);
            }
        }
        pattern.data = newData;
    }
    private Direction test(Level level, BlockPos controller) {
        BlockPos zero = controller.subtract(new Vec3i(pattern.offset[0], pattern.offset[1], pattern.offset[2]));

        //test NSEW
        if (testArea(level, controller, zero, Direction.NORTH)) return Direction.NORTH;
        if (testArea(level, controller, zero, Direction.SOUTH)) return Direction.SOUTH;
        if (testArea(level, controller, zero, Direction.EAST)) return Direction.EAST;
        if (testArea(level, controller, zero, Direction.WEST)) return Direction.WEST;

        return Direction.UP;
    }
    private Boolean testArea(Level level, BlockPos controller, BlockPos zero, Direction dir) {



        for (int x=0; x<sizeX; x++) {
            for (int y=0; y<sizeY; y++) {
                for (int z=0; z<sizeZ; z++) {
                    BlockPos testPos = zero.offset(x, y, z);
                    BlockState state = level.getBlockState(testPos);
                    ResourceLocation location = ForgeRegistries.BLOCKS.getKey(state.getBlock());
                    assert location != null;
                    String blockName = location.getNamespace()+":"+location.getPath();
//                    LOGGER.info(testPos +"|"+blockName+"|"+pattern.data[x][y][z]+"|")
                    if (!(Objects.equals(pattern.data[x][y][z], "")) && !(Objects.equals(pattern.data[x][y][z], blockName))) {
                        LOGGER.info("nope");
                        level.addParticle(ParticleTypes.SMOKE, testPos.getX(), testPos.getY(), testPos.getZ(), 0.0, 0.0, 0.0);
                        return false;
                    }
                }
            }
        }
        LOGGER.info("found");
        return true;
    }
    private void handleCreation(Level level, BlockPos pos, Direction dir) {
        BlockPos zero = pos.subtract(new Vec3i(pattern.offset[0], pattern.offset[1], pattern.offset[2]));
        for (int x=0; x<sizeX; x++) {
            for (int y=0; y<sizeY; y++) {
                for (int z=0; z<sizeZ; z++) {
                    BlockPos swapPos = zero.offset(x, y, z);
                    BlockState oldState = level.getBlockState(swapPos);
                    ResourceLocation location = ForgeRegistries.BLOCKS.getKey(oldState.getBlock());
                    assert location != null;
                    //String blockName = location.getNamespace()+":"+location.getPath();
                    BlockState newState;
                    if (!(x == pattern.offset[0] && y == pattern.offset[1] && z == pattern.offset[2])) {
                        // ensure controller is not replaced
                        newState = getPartState();
                        newState = newState.setValue(LXPOS, x);
                        newState = newState.setValue(LYPOS, y);
                        newState = newState.setValue(LZPOS, z);
                    } else {
                        newState = oldState.setValue(ACTIVE, Boolean.TRUE);
                    }
                    level.setBlockAndUpdate(swapPos, newState);
                }
            }
        }
    }

    // tell player that the mb is built wrong
    public void handleInvalid(Level level, BlockPos pos) {}


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
