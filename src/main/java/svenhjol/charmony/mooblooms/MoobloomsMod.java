package svenhjol.charmony.mooblooms;

import net.minecraft.resources.ResourceLocation;
import svenhjol.charmony.api.core.ModDefinition;
import svenhjol.charmony.core.base.Mod;
import svenhjol.charmony.api.core.Side;

@ModDefinition(
    id = MoobloomsMod.ID,
    sides = {Side.Client, Side.Common},
    name = "Mooblooms",
    description = "Mooblooms are cow-like mobs that come in a variety of flower types. They spawn flowers where they walk and can be milked for suspicious stew.")
public final class MoobloomsMod extends Mod {
    public static final String ID = "charmony-mooblooms";
    private static MoobloomsMod instance;

    private MoobloomsMod() {}

    public static MoobloomsMod instance() {
        if (instance == null) {
            instance = new MoobloomsMod();
        }
        return instance;
    }

    public static ResourceLocation id(String path) {
        return ResourceLocation.fromNamespaceAndPath(ID, path);
    }
}