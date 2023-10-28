package link.infra.indium.mixin.sodium;

import it.unimi.dsi.fastutil.ints.Int2ReferenceMap;
import it.unimi.dsi.fastutil.ints.Int2ReferenceMaps;
import it.unimi.dsi.fastutil.ints.Int2ReferenceOpenHashMap;
import link.infra.indium.other.ClonedChunkSectionExtension;
import me.jellysquid.mods.sodium.client.world.cloned.ClonedChunkSection;
import net.fabricmc.fabric.api.blockview.v2.RenderDataBlockEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.ChunkSectionPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.ChunkSection;
import net.minecraft.world.chunk.WorldChunk;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClonedChunkSection.class)
public class MixinClonedChunkSection implements ClonedChunkSectionExtension {
    @Shadow
    @Final
    @Nullable
    private Int2ReferenceMap<BlockEntity> blockEntityMap;

    @Unique
    @Nullable
    private Int2ReferenceMap<Object> indium$blockEntityRenderDataMap;

    @Inject(method = "<init>", at = @At("TAIL"))
    private void onInit(World world, WorldChunk chunk, @Nullable ChunkSection section, ChunkSectionPos pos, CallbackInfo ci) {
        if (this.blockEntityMap != null) {
            this.indium$blockEntityRenderDataMap = indium$copyBlockEntityRenderData(this.blockEntityMap);
        }
    }

    @Override
    @Nullable
    public Int2ReferenceMap<Object> indium$getBlockEntityRenderDataMap() {
        return this.indium$blockEntityRenderDataMap;
    }

    @Unique
    @Nullable
    private static Int2ReferenceMap<Object> indium$copyBlockEntityRenderData(Int2ReferenceMap<BlockEntity> blockEntities) {
        Int2ReferenceOpenHashMap<Object> blockEntityRenderDataMap = null;

        // Retrieve any render data after we have copied all block entities, as this will call into the code of
        // other mods. This could potentially result in the chunk being modified, which would cause problems if we
        // were iterating over any data in that chunk.
        // See https://github.com/CaffeineMC/sodium-fabric/issues/942 for more info.
        for (var entry : Int2ReferenceMaps.fastIterable(blockEntities)) {
            Object data = ((RenderDataBlockEntity) entry.getValue()).getRenderData();

            if (data != null) {
                if (blockEntityRenderDataMap == null) {
                    blockEntityRenderDataMap = new Int2ReferenceOpenHashMap<>();
                }

                blockEntityRenderDataMap.put(entry.getIntKey(), data);
            }
        }

        if (blockEntityRenderDataMap != null) {
            blockEntityRenderDataMap.trim();
        }

        return blockEntityRenderDataMap;
    }
}
