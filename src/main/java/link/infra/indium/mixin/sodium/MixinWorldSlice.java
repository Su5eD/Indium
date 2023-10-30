package link.infra.indium.mixin.sodium;

import it.unimi.dsi.fastutil.ints.Int2ReferenceMap;
import link.infra.indium.other.ClonedChunkSectionExtension;
import me.jellysquid.mods.sodium.client.world.WorldSlice;
import me.jellysquid.mods.sodium.client.world.biome.BiomeSlice;
import me.jellysquid.mods.sodium.client.world.cloned.ChunkRenderContext;
import me.jellysquid.mods.sodium.client.world.cloned.ClonedChunkSection;
import net.fabricmc.fabric.api.blockview.v2.FabricBlockView;
import net.fabricmc.fabric.api.rendering.data.v1.RenderAttachedBlockView;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(WorldSlice.class)
public abstract class MixinWorldSlice implements FabricBlockView, RenderAttachedBlockView {
    @Shadow
    @Final
    private static int SECTION_ARRAY_SIZE;
    @Shadow
    private int originX;
    @Shadow
    private int originY;
    @Shadow
    private int originZ;
    @Shadow
    @Final
    private BiomeSlice biomeSlice;

    @Shadow
    public static int getLocalSectionIndex(int x, int y, int z) {
        throw new UnsupportedOperationException();
    }

    @Shadow
    public static int getLocalBlockIndex(int x, int y, int z) {
        throw new UnsupportedOperationException();
    }

    // (Local Section -> Block Entity Render Data) table.
    @Unique
    @Nullable
    private Int2ReferenceMap<Object>[] indium$blockEntityRenderDataArrays;

    @Inject(method = "<init>", at = @At("TAIL"))
    private void initBlockEntityRenderData(ClientWorld world, CallbackInfo ci) {
        this.indium$blockEntityRenderDataArrays = new Int2ReferenceMap[SECTION_ARRAY_SIZE];
    }

    @Inject(method = "copySectionData", at = @At("RETURN"), locals = LocalCapture.CAPTURE_FAILHARD, remap = false)
    private void copyBlockEntityRenderData(ChunkRenderContext context, int sectionIndex, CallbackInfo ci, ClonedChunkSection section) {
        this.indium$blockEntityRenderDataArrays[sectionIndex] = ((ClonedChunkSectionExtension) section).indium$getBlockEntityRenderDataMap();
    }

    @Inject(method = "reset", at = @At(value = "FIELD", target = "Lme/jellysquid/mods/sodium/client/world/WorldSlice;blockEntityArrays:[Lit/unimi/dsi/fastutil/ints/Int2ReferenceMap;", shift = At.Shift.AFTER), locals = LocalCapture.CAPTURE_FAILHARD)
    private void clearBlockEntityRenderData(CallbackInfo ci, int sectionIndex) {
        this.indium$blockEntityRenderDataArrays[sectionIndex] = null;
    }

    @Override
    @Nullable
    public Object getBlockEntityRenderData(BlockPos pos) {
        int relX = pos.getX() - this.originX;
        int relY = pos.getY() - this.originY;
        int relZ = pos.getZ() - this.originZ;

        var blockEntityRenderDataMap = this.indium$blockEntityRenderDataArrays[getLocalSectionIndex(relX >> 4, relY >> 4, relZ >> 4)];

        if (blockEntityRenderDataMap == null) {
            return null;
        }

        return blockEntityRenderDataMap.get(getLocalBlockIndex(relX & 15, relY & 15, relZ & 15));
    }

    @Override
    public boolean hasBiomes() {
        return true;
    }

    @Override
    public RegistryEntry<Biome> getBiomeFabric(BlockPos pos) {
        return this.biomeSlice.getBiome(pos.getX(), pos.getY(), pos.getZ());
    }
}
