package link.infra.indium.mixin.sodium;

import me.jellysquid.mods.sodium.client.world.WorldSliceLocal;
import net.fabricmc.fabric.api.blockview.v2.FabricBlockView;
import net.fabricmc.fabric.api.rendering.data.v1.RenderAttachedBlockView;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockRenderView;
import net.minecraft.world.biome.Biome;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(WorldSliceLocal.class)
public abstract class MixinWorldSliceLocal implements RenderAttachedBlockView, FabricBlockView {
    @Shadow @Final private BlockRenderView view;

    @Override
    public @Nullable Object getBlockEntityRenderData(BlockPos pos) {
        return ((FabricBlockView)this.view).getBlockEntityRenderData(pos);
    }

    @Override
    public boolean hasBiomes() {
        return ((FabricBlockView)this.view).hasBiomes();
    }

    @Override
    public RegistryEntry<Biome> getBiomeFabric(BlockPos pos) {
        return ((FabricBlockView)this.view).getBiomeFabric(pos);
    }

    @Override
    public @Nullable Object getBlockEntityRenderAttachment(BlockPos pos) {
        return ((RenderAttachedBlockView)this.view).getBlockEntityRenderAttachment(pos);
    }
}
