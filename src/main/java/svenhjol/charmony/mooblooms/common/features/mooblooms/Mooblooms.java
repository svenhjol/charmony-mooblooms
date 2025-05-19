package svenhjol.charmony.mooblooms.common.features.mooblooms;

import svenhjol.charmony.api.core.FeatureDefinition;
import svenhjol.charmony.core.base.Mod;
import svenhjol.charmony.core.base.SidedFeature;
import svenhjol.charmony.api.core.Side;

@FeatureDefinition(side = Side.Common, description = """
    Mooblooms are cow-like mobs that come in a variety of flower types.
    They spawn flowers where they walk and can be milked for suspicious stew.""")
public final class Mooblooms extends SidedFeature {
    public final Registers registers;
    public final Handlers handlers;
    public final Advancements advancements;

    public Mooblooms(Mod mod) {
        super(mod);
        registers = new Registers(this);
        handlers = new Handlers(this);
        advancements = new Advancements(this);
    }

    public static Mooblooms feature() {
        return Mod.getSidedFeature(Mooblooms.class);
    }
}
