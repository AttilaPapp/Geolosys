package com.oitsjustjose.geolosys.common.api.world;

import java.util.List;
import java.util.Set;

import javax.annotation.Nullable;

import net.minecraft.block.state.IBlockState;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;

public class DepositBiomeRestricted extends Deposit {
    private List<Biome> biomes;
    private List<BiomeDictionary.Type> biomeTypes;
    private boolean useWhitelist;

    public DepositBiomeRestricted(IBlockState oreBlock, IBlockState sampleBlock, int yMin, int yMax, int size,
            int chance, int[] dimensionBlacklist, List<IBlockState> blockStateMatchers, List<Biome> biomes,
            List<BiomeDictionary.Type> biomeTypes, boolean useWhitelist, float density, @Nullable String customName) {
        super(oreBlock, sampleBlock, yMin, yMax, size, chance, dimensionBlacklist, blockStateMatchers, density,
                customName);
        this.biomes = biomes;
        this.biomeTypes = biomeTypes;
        this.useWhitelist = useWhitelist;
    }

    public boolean canPlaceInBiome(Biome biome) {
        // Manually check this since it's always a fucking pain
        for (Biome b : this.biomes) {
            if (b == biome) {
                return this.useWhitelist();
            }
        }
        for (BiomeDictionary.Type type : this.biomeTypes) {
            Set<BiomeDictionary.Type> dictTypes = BiomeDictionary.getTypes(biome);
            for (BiomeDictionary.Type otherType : dictTypes) {
                if (type.equals(otherType)) {
                    return this.useWhitelist();
                }
            }
        }

        return !this.useWhitelist();
    }

    public boolean useWhitelist() {
        return this.useWhitelist;
    }

    public List<Biome> getBiomeList() {
        return this.biomes;
    }

    public List<BiomeDictionary.Type> getBiomeTypes() {
        return this.biomeTypes;
    }
}