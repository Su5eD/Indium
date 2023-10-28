package link.infra.indium.other;

import it.unimi.dsi.fastutil.ints.Int2ReferenceMap;
import org.jetbrains.annotations.Nullable;

public interface ClonedChunkSectionExtension {
    @Nullable
    Int2ReferenceMap<Object> indium$getBlockEntityRenderDataMap();
}
