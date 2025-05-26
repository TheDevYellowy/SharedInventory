package com.yellowy.sharedinventory.mixin;

import com.yellowy.sharedinventory.Events.PlayerEvents;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public class MixinLivingEntity {
	@Inject(method = "setHealth", at = @At("TAIL"))
	public void setHealth(float health, CallbackInfo ci) {
		if ((Object) this instanceof PlayerEntity player) {
			PlayerEvents.player.HEALTH_UPDATE.invoker().update(player);
		}
	}
}
