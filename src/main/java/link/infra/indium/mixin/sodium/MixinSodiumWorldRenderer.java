package link.infra.indium.mixin.sodium;

import dev.su5ed.lazurite.util.FlawlessFrames;
import me.jellysquid.mods.sodium.client.render.SodiumWorldRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(SodiumWorldRenderer.class)
public class MixinSodiumWorldRenderer {
    @ModifyVariable(method = "setupTerrain", at = @At("HEAD"), argsOnly = true, ordinal = 1)
    private boolean updateImmediatelyForFlawless(boolean updateChunksImmediately) {
        return updateChunksImmediately || FlawlessFrames.isActive();
    }
}
