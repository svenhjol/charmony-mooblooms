package svenhjol.charmony.mooblooms.integration;

import svenhjol.charmony.core.base.Mod;
import svenhjol.charmony.core.integration.BaseModMenuPlugin;
import svenhjol.charmony.mooblooms.MoobloomsMod;

public class ModMenuPlugin extends BaseModMenuPlugin {
    @Override
    public Mod mod() {
        return MoobloomsMod.instance();
    }
}
