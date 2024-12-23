package svenhjol.charmony.mooblooms.client.features.mooblooms;

import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.builders.MeshTransformer;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Items;
import svenhjol.charmony.core.base.Setup;
import svenhjol.charmony.core.client.ClientRegistry;
import svenhjol.charmony.mooblooms.common.features.mooblooms.MoobloomModel;

public final class Registers extends Setup<Mooblooms> {
    public ModelLayerLocation adultModel;
    public ModelLayerLocation babyModel;

    public Registers(Mooblooms feature) {
        super(feature);
        var registry = ClientRegistry.forFeature(feature);
        var common = feature.common.get();

        if (common != null) {
            adultModel = new ModelLayerLocation(feature().id("moobloom"), "main");
            babyModel = new ModelLayerLocation(feature().id("moobloom_baby"), "main");

            EntityModelLayerRegistry.registerModelLayer(adultModel, MoobloomModel::createBodyLayer);
            EntityModelLayerRegistry.registerModelLayer(babyModel, () -> MoobloomModel.createBodyLayer().apply(MeshTransformer.scaling(0.6F)));
            EntityRendererRegistry.register(common.registers.moobloom.get(), MoobloomRenderer::new);
        }
    }

    @Override
    public Runnable boot() {
        return () -> {
            var registry = ClientRegistry.forFeature(feature());
            registry.itemTab(
                feature().common.get().registers.spawnEggItem.get(),
                CreativeModeTabs.SPAWN_EGGS,
                Items.MAGMA_CUBE_SPAWN_EGG
            );
        };
    }
}
