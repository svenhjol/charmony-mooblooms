package svenhjol.charmony.mooblooms.client.features.mooblooms;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.level.block.PinkPetalsBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import svenhjol.charmony.mooblooms.common.features.mooblooms.FlowerBlockState;
import svenhjol.charmony.mooblooms.common.features.mooblooms.MoobloomModel;
import svenhjol.charmony.mooblooms.common.features.mooblooms.MoobloomRenderState;

public class FlowerLayer extends RenderLayer<MoobloomRenderState, MoobloomModel> {
    public FlowerLayer(RenderLayerParent<MoobloomRenderState, MoobloomModel> renderLayerParent) {
        super(renderLayerParent);
    }

    /**
     * Copypasta from MushroomCowMushroomLayer with adjustments to scale and another flower added.
     */
    @Override
    public void render(PoseStack pose, MultiBufferSource bufferSource, int light, MoobloomRenderState renderState, float f, float g){
        if (!renderState.isBaby && !renderState.isInvisible) {
            var coords = LivingEntityRenderer.getOverlayCoords(renderState, 0.0f);
            var dispatcher = Minecraft.getInstance().getBlockRenderer();
            var type = renderState.type;
            var flower = type.getFlower();
            var flowerState = flower.getBlockState();

            if (flower.equals(FlowerBlockState.PINK_PETALS)) {
                renderCherryBlossomMoobloom(pose, flowerState, dispatcher, bufferSource, light, coords, renderState);
                return;
            } else if (flower.equals(FlowerBlockState.SUNFLOWER)) {
                flowerState = flowerState.setValue(DoublePlantBlock.HALF, DoubleBlockHalf.UPPER);
            }

            renderDefaultMoobloom(pose, flowerState, dispatcher, bufferSource, light, coords, renderState);
        }
    }

    public void renderDefaultMoobloom(PoseStack pose, BlockState state, BlockRenderDispatcher dispatcher,
                                      MultiBufferSource bufferSource, int light, int coords, MoobloomRenderState renderState) {
        pose.pushPose();
        pose.translate(0.2d, -0.35d, 0.5d);
        pose.mulPose(Axis.YP.rotationDegrees(-48.0f));
        pose.scale(-0.75f, -0.75f, 0.75f);
        pose.translate(-0.5d, -0.65d, -0.5d);
        dispatcher.renderSingleBlock(state, pose, bufferSource, light, coords);
        pose.popPose();

        pose.pushPose();
        pose.translate(0.2d, -0.35d, 0.5d);
        pose.mulPose(Axis.YP.rotationDegrees(42.0f));
        pose.translate(0.4d, 0.0d, -0.6d);
        pose.mulPose(Axis.YP.rotationDegrees(-48.0f));
        pose.scale(-0.75f, -0.75f, 0.75f);
        pose.translate(-0.5d, -0.65d, -0.5d);
        dispatcher.renderSingleBlock(state, pose, bufferSource, light, coords);
        pose.popPose();

        pose.pushPose();
        pose.translate(0.2d, -0.35d, 0.5d);
        pose.mulPose(Axis.YP.rotationDegrees(42.0f));
        pose.translate(-0.05, 0.0d, -0.4d);
        pose.mulPose(Axis.YP.rotationDegrees(-48.0f));
        pose.scale(-0.75f, -0.75f, 0.75f);
        pose.translate(-0.5d, -0.65d, -0.5d);
        dispatcher.renderSingleBlock(state, pose, bufferSource, light, coords);
        pose.popPose();

        if (renderState.isPollinated) {
            pose.pushPose();
            (this.getParentModel()).getHead().translateAndRotate(pose);
            pose.translate(0.0d, -0.7d, -0.2d);
            pose.mulPose(Axis.YP.rotationDegrees(-78.0f));
            pose.scale(-0.75f, -0.75f, 0.75f);
            pose.translate(-0.5d, -0.65d, -0.5d);
            dispatcher.renderSingleBlock(state, pose, bufferSource, light, coords);
            pose.popPose();
        }
    }

    public void renderCherryBlossomMoobloom(PoseStack pose, BlockState state, BlockRenderDispatcher dispatcher,
                                            MultiBufferSource bufferSource, int light, int coords, MoobloomRenderState renderState) {
        var x = -0.25d;
        var y = -0.15d;
        var z = -0.25d;

        if (renderState.isPollinated) {
            state = state.setValue(PinkPetalsBlock.AMOUNT, 4);
            x = -0.48d;
            z = -0.48d;
        }

        pose.pushPose();
        pose.translate(0d, 0d, 0d);
        pose.mulPose(Axis.YP.rotationDegrees(-48.0f));
        pose.scale(-0.75f, -0.75f, 0.75f);
        pose.translate(x, y, z);
        dispatcher.renderSingleBlock(state, pose, bufferSource, light, coords);
        pose.popPose();
    }
}
