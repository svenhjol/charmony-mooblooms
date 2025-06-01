package svenhjol.charmony.mooblooms.client.features.mooblooms;

import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Items;
import svenhjol.charmony.core.base.Setup;
import svenhjol.charmony.core.client.ClientRegistry;

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

            registry.modelLayer(adultModel, MoobloomModel::createBodyLayer);
            registry.modelLayer(babyModel, MoobloomModel::createBabyBodyLayer);
            registry.entityRenderer(common.registers.moobloom.get(), MoobloomRenderer::new);
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
