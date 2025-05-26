package com.yellowy.sharedinventory.mixin;

import com.yellowy.sharedinventory.Events.PlayerEvents;
import net.minecraft.entity.player.HungerManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(HungerManager.class)
public class MixinHungerManager {
	@Inject(method = "setFoodLevel", at = @At("TAIL"))
	public void setFoodLevel(int foodLevel, CallbackInfo ci) {
		PlayerEvents.player.HUNGER_UPDATE.invoker().update(foodLevel);
	}

	@Inject(method = "setSaturationLevel", at = @At("TAIL"))
	public void setSaturationLevel(float saturationLevel, CallbackInfo ci) {
		PlayerEvents.player.SATURATION_UPDATE.invoker().update(saturationLevel);
	}
}
