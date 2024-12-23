package svenhjol.charmony.mooblooms.client.features.mooblooms;

import svenhjol.charmony.mooblooms.common.features.mooblooms.Mooblooms;
import svenhjol.charmony.mooblooms.common.features.mooblooms.Registers;

public final class Common {
    public final Registers registers;

    public Common() {
        var feature = Mooblooms.feature();
        registers = feature.registers;
    }
}
