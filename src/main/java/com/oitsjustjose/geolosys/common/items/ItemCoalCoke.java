package com.oitsjustjose.geolosys.common.items;

import com.oitsjustjose.geolosys.Geolosys;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.furnace.FurnaceFuelBurnTimeEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemCoalCoke extends Item {
    public ItemCoalCoke() {
        this.setHasSubtypes(true);
        this.setCreativeTab(CreativeTabs.MISC);
        this.setRegistryName(new ResourceLocation(Geolosys.MODID, "COAL_COKE"));
        this.setUnlocalizedName(this.getRegistryName().toString().replaceAll(":", "."));
        ForgeRegistries.ITEMS.register(this);
        this.registerModels();
        MinecraftForge.EVENT_BUS.register(this);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> list) {
        if (this.isInCreativeTab(tab)) {
            for (int i = 0; i < Types.CoalCoke.values().length; ++i) {
                list.add(new ItemStack(this, 1, i));
            }
        }
    }

    @Override
    public String getUnlocalizedName(ItemStack stack) {
        return stack.getItem().getRegistryName().toString().replaceAll(":", ".") + "."
                + Types.CoalCoke.byMetadata(stack.getMetadata()).getName();
    }

    private void registerModels() {
        for (int i = 0; i < Types.CoalCoke.values().length; i++) {
            Geolosys.getInstance().clientRegistry.register(new ItemStack(this, 1, i),
                    new ResourceLocation(this.getRegistryName().toString() + "_" + Types.CoalCoke.byMetadata(i).name()),
                    "inventory");
        }
    }

    @SubscribeEvent
    public void registerFuels(FurnaceFuelBurnTimeEvent event) {
        if (event.getItemStack().getItem() != this) {
            return;
        }
        event.setBurnTime(Types.CoalCoke.byMetadata(event.getItemStack().getMetadata()).getBurnTime() * 200);
    }
}
