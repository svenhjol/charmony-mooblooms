package svenhjol.charmony.mooblooms.client.features.mooblooms;

import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import svenhjol.charmony.mooblooms.common.features.mooblooms.MoobloomType;

public class MoobloomRenderState extends LivingEntityRenderState {
    public MoobloomType type;
    public boolean isPollinated;
}
