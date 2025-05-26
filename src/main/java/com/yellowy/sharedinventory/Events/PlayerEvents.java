package com.yellowy.sharedinventory.Events;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;

public class PlayerEvents {
	public static class player {
		public static final Event<HEALTH> HEALTH_UPDATE = EventFactory.createArrayBacked(HEALTH.class,
		 (listeners) -> (player) -> {
			 for (HEALTH listener : listeners) {
				 listener.update(player);
			 }
		 }
		);

		public static final Event<HUNGER> HUNGER_UPDATE = EventFactory.createArrayBacked(HUNGER.class,
		 (listeners) -> (foodLevel) -> {
			 for (HUNGER listener : listeners) {
				 listener.update(foodLevel);
			 }
		 }
		);

		public static final Event<SATURATION> SATURATION_UPDATE = EventFactory.createArrayBacked(SATURATION.class,
		 (listeners) -> (saturationLevel) -> {
			 for (SATURATION listener : listeners) {
				 listener.update(saturationLevel);
			 }
		 }
		);

		public interface HEALTH {
			void update(PlayerEntity player);
		}

		public interface HUNGER {
			void update(int foodLevel);
		}

		public interface SATURATION {
			void update(float saturationLevel);
		}
	}

	public static class inventory {
		public static final Event<change> CHANGE = EventFactory.createArrayBacked(change.class,
		 (listeners) -> (player, slot) -> {
			 for (change listener : listeners) {
				 listener.onChange(player, slot);
			 }
		 }
		);

		public static final Event<save> SAVE = EventFactory.createArrayBacked(save.class,
		 (listeners) -> (player) -> {
			 for (save listener : listeners) {
				 listener.onSave(player);
			 }
		 }
		);

		@FunctionalInterface
		public interface change {
			void onChange(ServerPlayerEntity player, int slot);
		}

		public interface save {
			void onSave(ServerPlayerEntity player);
		}
	}
}
