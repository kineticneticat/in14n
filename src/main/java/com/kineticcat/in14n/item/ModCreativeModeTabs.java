package com.kineticcat.in14n.item;

import com.kineticcat.in14n.Industrialisation;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.event.CreativeModeTabEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Industrialisation.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModCreativeModeTabs {
    public static CreativeModeTab INDUSTRIALISATION_TAB;

    @SubscribeEvent
    public static void registerCreativeModeTabs(CreativeModeTabEvent.Register event) {
        INDUSTRIALISATION_TAB = event.registerCreativeModeTab(new ResourceLocation(Industrialisation.MODID, "tutorial_tab"),
                builder -> builder.icon(() -> new ItemStack(Items.IRON_BLOCK))
                        .title(Component.translatable("creativemodetab.indust_tab")));
    }
}
