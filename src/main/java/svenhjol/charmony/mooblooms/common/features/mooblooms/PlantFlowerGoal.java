package svenhjol.charmony.mooblooms.common.features.mooblooms;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;

public class PlantFlowerGoal extends Goal {
    private final Moobloom mob;
    private final Level level;
    private boolean planting;

    public PlantFlowerGoal(Moobloom mob) {
        this.mob = mob;
        this.level = mob.level();
    }

    @Override
    public boolean canUse() {
        var pos = mob.blockPosition();

        if (level instanceof ServerLevel serverLevel && !serverLevel.getGameRules().getBoolean(GameRules.RULE_MOBGRIEFING)) {
            return false;
        }

        if (planting) return false;
        if (mob.isBaby()) return false;
        if (mob.getRandom().nextInt(1000) != 0) return false;

        return level.getBlockState(pos).isAir()
            && level.getBlockState(pos.below()).is(Blocks.GRASS_BLOCK);
    }

    @Override
    public void start() {
        this.planting = true;
    }

    @Override
    public void stop() {
        this.planting = false;
    }

    @Override
    public void tick() {
        if (planting) {
            mob.plantFlower();
            planting = false;
        }
    }
}
