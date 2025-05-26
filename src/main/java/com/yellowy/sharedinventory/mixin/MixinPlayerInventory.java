package com.yellowy.sharedinventory.mixin;

import com.yellowy.sharedinventory.Events.PlayerEvents;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerInventory.class)
public class MixinPlayerInventory {
	@Shadow
	@Final
	public PlayerEntity player;

	@Inject(method = "setStack", at = @At("TAIL"))
	private void setStack(int slot, ItemStack stack, CallbackInfo cb) {
		PlayerEvents.inventory.CHANGE.invoker().onChange((ServerPlayerEntity) this.player, slot);
	}

	@Inject(method = "addStack(ILnet/minecraft/item/ItemStack;)I", at = @At("TAIL"))
	private void addStack(int slot, ItemStack stack, CallbackInfoReturnable<Integer> cir) {
		PlayerEvents.inventory.CHANGE.invoker().onChange((ServerPlayerEntity) this.player, slot);
	}

	@Inject(method = "removeStack(I)Lnet/minecraft/item/ItemStack;", at = @At("TAIL"))
	private void removeStack(int slot, CallbackInfoReturnable<ItemStack> cir) {
		PlayerEvents.inventory.CHANGE.invoker().onChange((ServerPlayerEntity) this.player, slot);
	}

	@Inject(method = "insertStack(ILnet/minecraft/item/ItemStack;)Z", at = @At("TAIL"))
	private void insertStack(int slot, ItemStack stack, CallbackInfoReturnable<Boolean> cir) {
		PlayerEvents.inventory.CHANGE.invoker().onChange((ServerPlayerEntity) this.player, slot);
	}

	@Inject(method = "markDirty", at = @At("HEAD"))
	private void makrDirty(CallbackInfo ci) {
		PlayerEvents.inventory.SAVE.invoker().onSave((ServerPlayerEntity) this.player);
	}
}
