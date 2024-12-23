package svenhjol.charmony.mooblooms.common.features.mooblooms;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerEntityEvents;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.SpawnPlacementTypes;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.level.levelgen.Heightmap;
import svenhjol.charmony.core.base.Setup;
import svenhjol.charmony.core.common.CommonRegistry;

import java.util.function.Supplier;

public final class Registers extends Setup<Mooblooms> {
    public final Supplier<SpawnEggItem> spawnEggItem;
    public final Supplier<EntityType<Moobloom>> moobloom;
    public final Supplier<SoundEvent> milkingSound;

    public Registers(Mooblooms feature) {
        super(feature);

        var registry = CommonRegistry.forFeature(feature);

        moobloom = registry.entity("moobloom", () -> EntityType.Builder
            .of(Moobloom::new, MobCategory.CREATURE)
            .sized(0.9f, 1.4f)
            .clientTrackingRange(10));

        milkingSound = registry.sound("moobloom_milk");

        spawnEggItem = registry.item("moobloom_spawn_egg",
            key -> new SpawnEggItem(moobloom.get(), (new Item.Properties().setId(key))));

        registry.biomeSpawn(holder -> holder.is(Tags.SPAWNS_COMMON_MOOBLOOMS),
            MobCategory.CREATURE, moobloom, 30, 1, 3);

        registry.biomeSpawn(holder -> holder.is(Tags.SPAWNS_CHERRY_BLOSSOM_MOOBLOOMS),
            MobCategory.CREATURE, moobloom, 5, 1, 1);

        registry.biomeSpawn(holder -> holder.is(Tags.SPAWNS_SUNFLOWER_MOOBLOOMS),
            MobCategory.CREATURE, moobloom, 10, 1, 2);

        registry.mobSpawnPlacement(moobloom, SpawnPlacementTypes.ON_GROUND,
            Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
            Moobloom::canSpawn);

        registry.mobAttributes(moobloom, Moobloom::createAttributes);
    }

    @Override
    public Runnable boot() {
        return () -> {
            ServerEntityEvents.ENTITY_LOAD.register(feature().handlers::entityJoin);
        };
    }
}
