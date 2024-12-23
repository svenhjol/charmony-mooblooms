package svenhjol.charmony.mooblooms.common;

import net.fabricmc.api.ModInitializer;
import svenhjol.charmony.core.enums.Side;
import svenhjol.charmony.mooblooms.MoobloomsMod;
import svenhjol.charmony.mooblooms.common.features.mooblooms.Mooblooms;

public final class CommonInitializer implements ModInitializer {
    @Override
    public void onInitialize() {
        // Ensure charmony is launched first.
        svenhjol.charmony.core.common.CommonInitializer.init();

        // Prepare and run the mod.
        var mod = MoobloomsMod.instance();
        mod.addSidedFeature(Mooblooms.class);
        mod.run(Side.Common);
    }
}
