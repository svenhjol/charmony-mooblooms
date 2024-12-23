package svenhjol.charmony.mooblooms.common.features.mooblooms;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.animal.Bee;
import net.minecraft.world.level.Level;
import svenhjol.charmony.core.base.Environment;

import java.util.function.Predicate;

public class BeeSearchGoal extends Goal {
    private static final int MAX_MOVE_TICKS = 1200;
    private static final int RANGE = 24;
    private final Bee bee;
    private final Level level;
    private Moobloom moobloom = null;
    private int moveTicks;
    private int lastTried = 0;

    public BeeSearchGoal(Bee bee) {
        this.bee = bee;
        this.level = bee.level();
    }

    @Override
    public boolean canUse() {
        if (bee.hasNectar()) {
            return --lastTried <= 0;
        }

        return false;
    }

    @Override
    public void start() {
        moobloom = null;
        moveTicks = 0;
        bee.resetTicksWithoutNectarSinceExitingHive();

        var box = bee.getBoundingBox().inflate(RANGE, RANGE / 2.0, RANGE);
        Predicate<Moobloom> selector = entity -> !entity.isPollinated() && entity.isAlive();
        var entities = level.getEntitiesOfClass(Moobloom.class, box, selector);

        if (!entities.isEmpty()) {
            moobloom = entities.get(level.random.nextInt(entities.size()));
            bee.setStayOutOfHiveCountdown(MAX_MOVE_TICKS);
        } else {
            lastTried = 200;
        }

        super.start();
    }

    @Override
    public void stop() {
        moveTicks = 0;
        moobloom = null;
        bee.getNavigation().stop();
        bee.getNavigation().resetMaxVisitedNodesMultiplier();
    }

    @Override
    public boolean canContinueToUse() {
        return moobloom != null && moobloom.isAlive() && moveTicks < MAX_MOVE_TICKS;
    }

    @Override
    public void tick() {
        moveTicks++;
        if (moobloom == null || !moobloom.isAlive()) return;

        if (moveTicks > MAX_MOVE_TICKS) {
            moobloom = null;
        } else if (!bee.getNavigation().isInProgress()) {
            bee.pathfindRandomlyTowards(moobloom.blockPosition());

            if (Environment.isDebugMode()) {
                bee.addEffect(new MobEffectInstance(MobEffects.GLOWING, 100));
            }
        } else {

            // Update bee tracking to take into account a moving moobloom.
            if (moveTicks % 50 == 0) {
                bee.pathfindRandomlyTowards(moobloom.blockPosition());
            }

            var dist = bee.position().distanceTo(moobloom.position());
            if (dist < 2.2) {
                bee.setHasNectar(false);

                if (Environment.isDebugMode()) {
                    bee.removeEffect(MobEffects.GLOWING);
                }

                moobloom.pollinate();
                moobloom = null;
            }
        }
    }
}
