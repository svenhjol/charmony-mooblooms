package svenhjol.charmony.mooblooms.client.features.mooblooms;

import net.minecraft.client.model.CowModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshTransformer;

public class MoobloomModel extends CowModel {
    public MoobloomModel(ModelPart modelPart) {
        super(modelPart);
    }

    public static LayerDefinition createBabyBodyLayer() {
        return createBodyLayer().apply(MeshTransformer.scaling(0.6F));
    }
}
