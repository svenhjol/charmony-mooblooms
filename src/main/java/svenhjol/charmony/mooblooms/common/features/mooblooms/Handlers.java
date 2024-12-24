package svenhjol.charmony.mooblooms.common.features.mooblooms;

import net.minecraft.core.Direction;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.ConversionParams;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.Bee;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.DirectionalPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.PinkPetalsBlock;
import net.minecraft.world.level.gameevent.GameEvent;
import svenhjol.charmony.core.base.Setup;

public final class Handlers extends Setup<Mooblooms> {
    public Handlers(Mooblooms feature) {
        super(feature);
    }

    public void entityJoin(Entity entity, Level level) {
        if (entity instanceof Bee bee) {
            var hasGoal = bee.getGoalSelector().getAvailableGoals().stream().anyMatch(
                goal -> goal.getGoal() instanceof BeeSearchGoal);

            if (!hasGoal) {
                bee.getGoalSelector().addGoal(4, new BeeSearchGoal(bee));
            }
        }
    }

    public InteractionResult interact(Moobloom moobloom, Player player, InteractionHand hand) {
        var held = player.getItemInHand(hand);
        var level = moobloom.level();

        if (held.getItem() == Items.BOWL && !moobloom.isBaby()) {
            if (!level.isClientSide() && moobloom.isPollinated()) {
                ItemStack stew;
                var effects = moobloom.getMoobloomType().getFlower().getEffects();

                if (!effects.effects().isEmpty()) {
                    stew = new ItemStack(Items.SUSPICIOUS_STEW);
                    stew.set(DataComponents.SUSPICIOUS_STEW_EFFECTS, effects);
                } else {
                    stew = new ItemStack(Items.MUSHROOM_STEW);
                }

                moobloom.playSound(feature().registers.milkingSound.get(), 1.0F, 1.0F);

                var out = ItemUtils.createFilledResult(held, player, stew, false);
                player.setItemInHand(hand, out);
                moobloom.setPollinated(false);
            }

            return InteractionResult.SUCCESS;

        } else if (held.getItem() == Items.SHEARS && moobloom.readyForShearing()) {

            if (!level.isClientSide()) {
                shear(moobloom, (ServerLevel)level, SoundSource.PLAYERS, held);
                moobloom.gameEvent(GameEvent.SHEAR, player);
                held.hurtAndBreak(1, player, Moobloom.getSlotForHand(hand));
            }

            return InteractionResult.SUCCESS;
        }

        return InteractionResult.PASS;
    }

    public void plantFlower(Moobloom moobloom) {
        var level = moobloom.level();
        var pos = moobloom.blockPosition();
        var flower = moobloom.getMoobloomType().getFlower();

        if (flower.equals(FlowerBlockState.PINK_PETALS)) {
            level.setBlock(pos, flower.getBlockState().setValue(PinkPetalsBlock.AMOUNT,
                level.getRandom().nextInt(3) + 1), 2);
        } else if (flower.equals(FlowerBlockState.SUNFLOWER)) {
            ((BlockItem)Items.SUNFLOWER)
                .place(new DirectionalPlaceContext(level, pos, Direction.NORTH, ItemStack.EMPTY, Direction.NORTH));
        } else {
            level.setBlock(pos, flower.getBlockState(), 2);
        }

        level.levelEvent(2001, pos, Block.getId(Blocks.GRASS_BLOCK.defaultBlockState()));
    }

    /**
     * Some copypasta from MushroomCow.
     * @see net.minecraft.world.entity.animal.MushroomCow#shear
     */
    public void shear(Moobloom moobloom, ServerLevel level, SoundSource soundSource, ItemStack shears) {
        level.playSound(null, moobloom, SoundEvents.MOOSHROOM_SHEAR, soundSource, 1.0f, 1.0f);

        moobloom.convertTo(EntityType.COW, ConversionParams.single(moobloom, false, false), (cow) -> {
            level.sendParticles(ParticleTypes.EXPLOSION, moobloom.getX(), moobloom.getY(0.5f), moobloom.getZ(), 1, 0.0f, 0.0f, 0.0f, 0.0f);

            var flower = new ItemStack(moobloom.getMoobloomType().getFlower().getBlock());
            for (int i = 0; i < 5; ++i) {
                level.addFreshEntity(new ItemEntity(level, moobloom.getX(), moobloom.getY(1.0D), moobloom.getZ(), flower));
            }
        });
    }
}
