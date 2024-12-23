package svenhjol.charmony.mooblooms.client.features.mooblooms;

import svenhjol.charmony.core.annotations.FeatureDefinition;
import svenhjol.charmony.core.base.Mod;
import svenhjol.charmony.core.base.SidedFeature;
import svenhjol.charmony.core.enums.Side;
import svenhjol.charmony.mooblooms.MoobloomsMod;

import java.util.function.Supplier;

@FeatureDefinition(side = Side.Client)
public final class Mooblooms extends SidedFeature {
    public final Supplier<Common> common;
    public final Registers registers;

    public Mooblooms(Mod mod) {
        super(mod);
        common = Common::new;
        registers = new Registers(this);
    }

    public static Mooblooms feature() {
        return MoobloomsMod.instance().sidedFeature(Mooblooms.class);
    }
}
