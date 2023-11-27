package com.kineticcat.in14n.block.multiblock;

import com.google.gson.Gson;
import com.mojang.logging.LogUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

public class MBController extends BaseEntityBlock {

    private final MBPattern pattern;
    private final Logger LOGGER = LogUtils.getLogger();
    public int sizeX;
    public int sizeY;
    public int sizeZ;
    public BlockState getPartState() {return null;}

    public MBController(Properties properties, String name) {
        super(properties);
        // this is shit and probably slow but idk a better way
        ResourceLocation test = new ResourceLocation(String.format("in14n:data/in14n/patterns/%s.json", name));
        String path = test.getPath();
        BufferedReader readIn = new BufferedReader(new InputStreamReader(Objects.requireNonNull(getClass().getClassLoader()
                .getResourceAsStream(path)), StandardCharsets.UTF_8));
        String FullFile = "";
        String line = "";
        while (true) {
            try {
                line = readIn.readLine();
            } catch (IOException e) {
                // cant really fix it
                e.printStackTrace();
            }
            if (line != null) {
                FullFile = FullFile + line + "\n";
            } else {
                break;
            }
        }
        Gson gson = new Gson();
        pattern = gson.fromJson(FullFile, MBPattern.class);

        sizeX = pattern.data[0].length;
        sizeY = pattern.data.length;
        sizeZ = pattern.data[0][0].length;

        fixDataOrder();
    }

    public @NotNull RenderShape getRenderShape(@NotNull BlockState state) {
        return RenderShape.MODEL;
    }

    @Override
    @NotNull
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {

        if (!level.isClientSide) {
            Boolean found = testMultiblockArea(level, pos);
            if (found) {
                swapParts(level, pos);
            }
        }

        return InteractionResult.SUCCESS;
    }

    public void fixDataOrder() {

        pattern.offset = new int[]{pattern.offset[1], pattern.offset[0], pattern.offset[2]};

        String[][][] newData = new String[sizeX][sizeY][sizeZ];
        for (int y=0; y<sizeY; y++) {
            for (int x=0; x<sizeX; x++) {
                for (int z=0; z<sizeZ; z++) {
                    newData[x][y][z] = pattern.data[y][x][z];
                }
            }
        }
        pattern.data = newData;
    }

    //i cannot be fucked to deal with this rn
//    private String[][][] rotate3dArray(String[][][] arr) {
//        String[][][] out = arr.clone();
//
//        for (int x=0; x<arr.length; x++) {
//            for (int y=0; y<arr[0].length; y++) {
//                for (int z=0; z<arr[0][0].length; z++) {
//                    out[y][x][z] = arr[x][y][z];
//                }
//            }
//        }
//        arr = out;
//
//
//        return out;
//    }

    private Boolean testMultiblockArea(Level level, BlockPos pos) {

        BlockPos zero = pos.subtract(new Vec3i(pattern.offset[0], pattern.offset[1], pattern.offset[2]));

        for (int x=0; x<sizeX; x++) {
            for (int y=0; y<sizeY; y++) {
                for (int z=0; z<sizeZ; z++) {
                    BlockPos testPos = zero.offset(x, y, z);
                    BlockState state = level.getBlockState(testPos);
                    ResourceLocation location = ForgeRegistries.BLOCKS.getKey(state.getBlock());
                    assert location != null;
                    String blockName = location.getNamespace()+":"+location.getPath();
//                    LOGGER.info(testPos +"|"+blockName+"|"+pattern.data[x][y][z]+"|");
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
    private void swapParts(Level level, BlockPos pos) {
        BlockPos zero = pos.subtract(new Vec3i(pattern.offset[0], pattern.offset[1], pattern.offset[2]));
        for (int x=0; x<sizeX; x++) {
            for (int y=0; y<sizeY; y++) {
                for (int z=0; z<sizeZ; z++) {
                    BlockPos swapPos = zero.offset(x, y, z);
                    BlockState state = level.getBlockState(swapPos);
                    ResourceLocation location = ForgeRegistries.BLOCKS.getKey(state.getBlock());
                    assert location != null;
                    String blockName = location.getNamespace()+":"+location.getPath();
                    if (!(x == pattern.offset[0] && y == pattern.offset[1] && z == pattern.offset[2])) {
                        level.setBlockAndUpdate(swapPos, getPartState());
                        BlockEntity entity = level.getBlockEntity(swapPos);
                        assert entity != null;
                        CompoundTag nbt = entity.serializeNBT();
                        nbt.putString("Replaces", blockName);
                        entity.deserializeNBT(nbt);
                        level.blockUpdated(swapPos, getPartState().getBlock());
                    }
                }
            }
        }
    }




    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return null;
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        return null;
    }
}
