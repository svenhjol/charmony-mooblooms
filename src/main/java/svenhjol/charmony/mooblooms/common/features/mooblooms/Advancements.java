package svenhjol.charmony.mooblooms.common.features.mooblooms;

import net.minecraft.world.entity.player.Player;
import svenhjol.charmony.core.base.Setup;
import svenhjol.charmony.core.helper.AdvancementHelper;

public final class Advancements extends Setup<Mooblooms> {
    public Advancements(Mooblooms feature) {
        super(feature);
    }

    public void milkedMoobloom(Player player) {
        AdvancementHelper.trigger("milked_moobloom", player);
    }

    public void milkedRareMoobloom(Player player) {
        AdvancementHelper.trigger("milked_rare_moobloom", player);
    }
}
