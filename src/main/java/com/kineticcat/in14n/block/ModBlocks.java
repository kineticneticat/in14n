package com.kineticcat.in14n.block;

import com.kineticcat.in14n.Industrialisation;
import com.kineticcat.in14n.block.multiblock.MBController;
import com.kineticcat.in14n.block.multiblock.controllers.MBCrusherController;
import com.kineticcat.in14n.block.multiblock.controllers.entity.MBCrusherControllerEntity;
import com.kineticcat.in14n.block.multiblock.parts.MBCrusherPart;
import com.kineticcat.in14n.item.ModItems;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, Industrialisation.MODID);

//    public static final RegistryObject<Block> TEST_BLOCK = registerBlock("test_block",
//            () -> new MBController(BlockBehaviour.Properties.of(Material.METAL)
//                    .strength(6f).requiresCorrectToolForDrops(), "test"));
    public static final RegistryObject<Block> MB_CRUSHER_CONTROLLER = registerBlock("crusher_controller",
            () -> new MBCrusherController(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)
                    .strength(6f).requiresCorrectToolForDrops()));

    public static final RegistryObject<Block> MB_CRUSHER_PART = registerBlock("crusher_part",
            () -> new MBCrusherPart(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)
                    .strength(6f).requiresCorrectToolForDrops()));

    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block) {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }

    private static <T extends Block> RegistryObject<Item> registerBlockItem(String name, RegistryObject<T> block) {
        return ModItems.ITEMS.register(name, () -> new BlockItem(block.get(),
                new Item.Properties()));
    }

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}
