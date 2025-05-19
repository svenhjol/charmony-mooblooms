package svenhjol.charmony.mooblooms.client;

import net.fabricmc.api.ClientModInitializer;
import svenhjol.charmony.api.core.Side;
import svenhjol.charmony.mooblooms.MoobloomsMod;
import svenhjol.charmony.mooblooms.client.features.mooblooms.Mooblooms;

public final class ClientInitializer implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        // Ensure charmony is launched first.
        svenhjol.charmony.core.client.ClientInitializer.init();

        // Prepare and run the mod.
        var mod = MoobloomsMod.instance();
        mod.addSidedFeature(Mooblooms.class);
        mod.run(Side.Client);
    }
}
