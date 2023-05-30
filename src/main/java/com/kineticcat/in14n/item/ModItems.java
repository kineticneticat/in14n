package com.kineticcat.in14n.item;

import com.kineticcat.in14n.Industrialisation;
import com.kineticcat.in14n.item.tools.Hammer;
import com.kineticcat.in14n.item.tools.Wrench;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {

    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, Industrialisation.MODID);

    public static final RegistryObject<Item> WRENCH = ITEMS.register("wrench",
            () -> new Wrench(new Item.Properties()));
    public static final RegistryObject<Item> HAMMER = ITEMS.register("hammer",
            () -> new Hammer(new Item.Properties()));


    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }

}
