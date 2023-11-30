package com.kineticcat.in14n.block.entity;

import com.kineticcat.in14n.Industrialisation;
import com.kineticcat.in14n.block.ModBlocks;
import com.kineticcat.in14n.block.multiblock.controllers.entity.MBTestControllerEntity;
import com.kineticcat.in14n.block.multiblock.parts.entity.MBTestPartEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTIIES =
            DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, Industrialisation.MODID);

    public static final RegistryObject<BlockEntityType<MBTestControllerEntity>> MB_TEST_CONTROLLER =
            BLOCK_ENTIIES.register("crusher_controller", () ->
                    BlockEntityType.Builder.of(MBTestControllerEntity::new,
                            ModBlocks.MB_TEST_CONTROLLER.get()).build(null));
    public static final RegistryObject<BlockEntityType<MBTestPartEntity>> MB_TEST_PART =
            BLOCK_ENTIIES.register("crusher_part", () ->
                    BlockEntityType.Builder.of(MBTestPartEntity::new,
                            ModBlocks.MB_TEST_PART.get()).build(null));

    public static void register(IEventBus eventBus) {
        BLOCK_ENTIIES.register(eventBus);
    }
}
