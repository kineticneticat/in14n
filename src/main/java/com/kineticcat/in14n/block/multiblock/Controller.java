package com.kineticcat.in14n.block.multiblock;

import com.google.gson.Gson;
import com.mojang.logging.LogUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.registries.ForgeRegistries;
import org.slf4j.Logger;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Objects;

public class Controller extends Block {

    private String MultiblockName;
    private Pattern pattern;
    private final Logger LOGGER = LogUtils.getLogger();
    public int sizeX;
    public int sizeY;
    public int sizeZ;

    public Controller(Properties properties, String Name) {
        super(properties);
        MultiblockName = Name;
        try {
            FileReader reader = new FileReader(String.format("E:\\Projects\\Industrialisation\\src\\main\\resources\\data\\industrialisation\\multiblocks\\%s.json", MultiblockName));
            Gson gson = new Gson();
            pattern = gson.fromJson(reader, Pattern.class);

            sizeX = pattern.data[0].length;
            sizeY = pattern.data.length;
            sizeZ = pattern.data[0][0].length;

            fixDataOrder();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {

        if (level.isClientSide) {
            testMultiblockArea(level, pos);
        }

        return InteractionResult.SUCCESS;
    }

    public void fixDataOrder() {
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

    private String[][][] rotate3dArray(String[][][] arr) {
        String[][][] out = arr.clone();

        for (int x=0; x<arr.length; x++) {
            for (int y=0; y<arr[0].length; y++) {
                for (int z=0; z<arr[0][0].length; z++) {
                    out[y][x][z] = arr[x][y][z];
                }
            }
        }
        arr = out;


        return out;
    }

    public Boolean testMultiblockArea(Level level, BlockPos pos) {
        BlockPos zero = pos.subtract(new Vec3i(pattern.offset[0], pattern.offset[1], pattern.offset[2]));

        for (int x=0; x<sizeX; x++) {
            for (int y=0; y<sizeY; y++) {
                for (int z=0; z<sizeZ; z++) {
                    BlockPos testPos = zero.offset(x, y, z);
                    BlockState state = level.getBlockState(testPos);
                    ResourceLocation location = ForgeRegistries.BLOCKS.getKey(state.getBlock());
                    assert location != null;
                    String blockName = location.getNamespace()+":"+location.getPath();
                    LOGGER.info(testPos.toString()+"|"+blockName+"|"+pattern.data[x][y][z]+"|");
                    if (!(Objects.equals(pattern.data[x][y][z], "")) && !(Objects.equals(pattern.data[x][y][z], blockName))) {
                        LOGGER.info("nope");
                        return false;
                    }
                }
            }
        }
        LOGGER.info("found");
        return true;
    }

}
