package com.datdevelop.effect;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;


public class FreezingEffect extends StatusEffect {
    public FreezingEffect() {
        super(
                StatusEffectCategory.NEUTRAL,
                0x2acaea
        );
    }

    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return true;
    }

    public void applyUpdateEffect(LivingEntity entity, int amplifier) {
        if(entity.isPlayer()){
            if (entity.getFrozenTicks() < 250 * (amplifier + 1)) {
                entity.setFrozenTicks(entity.getFrozenTicks() + 3 * (amplifier + 1));
                tickFrozenHands(((ServerPlayerEntity) entity));
            }
        }
    }

    private void tickFrozenHands(ServerPlayerEntity player) {
        boolean mainhand = !player.getMainHandStack().isEmpty();
        boolean offhand = !player.getOffHandStack().isEmpty();
        player.sendMessage(Text.literal("§bВам холодно..."), true);
//        try {
//            Thread.sleep(5000);
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }
        if (mainhand && offhand) {
            if (player.getRandom().nextBetween(0, 1) == 0) {
                ItemStack itemStack = player.getMainHandStack().copy();
                itemStack.setCount(1);

                player.dropItem(itemStack, false, true);
                player.getMainHandStack().decrement(1);
            } else {
                ItemStack itemStack = player.getOffHandStack().copy();
                itemStack.setCount(1);
                player.dropItem(itemStack, false, true);
                player.getOffHandStack().decrement(1);
            }
        } else {
            if (mainhand) {
                ItemStack itemStack = player.getMainHandStack().copy();
                itemStack.setCount(1);
                player.dropItem(itemStack, false, true);
                player.getMainHandStack().decrement(1);
            } else {
                ItemStack itemStack = player.getOffHandStack().copy();
                itemStack.setCount(1);
                player.dropItem(itemStack, false, true);
                player.getOffHandStack().decrement(1);
            }
        }

    }
}