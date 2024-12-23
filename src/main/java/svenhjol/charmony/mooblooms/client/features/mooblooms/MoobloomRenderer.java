package svenhjol.charmony.mooblooms.client.features.mooblooms;

import net.minecraft.client.renderer.entity.AgeableMobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import svenhjol.charmony.mooblooms.common.features.mooblooms.Moobloom;
import svenhjol.charmony.mooblooms.common.features.mooblooms.MoobloomModel;
import svenhjol.charmony.mooblooms.common.features.mooblooms.MoobloomRenderState;

@SuppressWarnings("deprecation")
public class MoobloomRenderer extends AgeableMobRenderer<Moobloom, MoobloomRenderState, MoobloomModel> {
    public MoobloomRenderer(EntityRendererProvider.Context context) {
        super(context,
            new MoobloomModel(context.bakeLayer(Mooblooms.feature().registers.adultModel)),
            new MoobloomModel(context.bakeLayer(Mooblooms.feature().registers.babyModel)), 0.7f);
        addLayer(new FlowerLayer(this));
    }

    @Override
    public ResourceLocation getTextureLocation(MoobloomRenderState renderState) {
        return renderState.type.getTexture();
    }

    @Override
    public MoobloomRenderState createRenderState() {
        return new MoobloomRenderState();
    }

    @Override
    public void extractRenderState(Moobloom moobloom, MoobloomRenderState renderState, float f) {
        super.extractRenderState(moobloom, renderState, f);
        renderState.type = moobloom.getMoobloomType();
        renderState.isPollinated = moobloom.isPollinated();
    }
}
