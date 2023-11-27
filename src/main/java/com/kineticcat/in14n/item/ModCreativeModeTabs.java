package com.kineticcat.in14n.item;


import com.kineticcat.in14n.Industrialisation;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ModCreativeModeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Industrialisation.MODID);

    public static final RegistryObject<CreativeModeTab> IN14N_TAB = CREATIVE_MODE_TABS.register("tutorial_tab",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(Items.IRON_BLOCK))
                    .title(Component.translatable("creativetab.in14n_tab"))
                    .displayItems((pParameters, pOutput) -> {
                        pOutput.accept(ModItems.WRENCH.get());
                        pOutput.accept(ModItems.HAMMER.get());


                    })
                    .build());


    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }
}