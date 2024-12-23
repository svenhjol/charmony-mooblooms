package svenhjol.charmony.mooblooms;

import net.minecraft.resources.ResourceLocation;
import svenhjol.charmony.core.annotations.ModDefinition;
import svenhjol.charmony.core.base.Mod;
import svenhjol.charmony.core.enums.Side;

@ModDefinition(
    id = MoobloomsMod.ID,
    sides = {Side.Client, Side.Common},
    name = "Mooblooms",
    description = "")
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