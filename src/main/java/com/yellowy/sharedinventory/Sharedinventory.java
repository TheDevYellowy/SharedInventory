package com.yellowy.sharedinventory;

import com.yellowy.sharedinventory.Events.PlayerEvents;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.server.network.ServerPlayerEntity;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Sharedinventory implements ModInitializer {
	public static Logger LOGGER = LogManager.getLogger("sharedinventory");
	public static PlayerInventory inventory = null;

	public boolean[] changing = new boolean[]{false, false, false, false}; // 0 = inventory | 1 = health | 2 = hunger | 3 = saturation

	@Override
	public void onInitialize() {
		ServerPlayConnectionEvents.JOIN.register((handler, sender, server) -> {
			if (inventory == null) return;
			handler.player.getInventory().clone(inventory);
		});

		PlayerEvents.inventory.CHANGE.register((player, slot) -> {
			if (changing[0]) return;
			changing[0] = true;
			inventory = player.getInventory();
			for (ServerPlayerEntity other : player.server.getPlayerManager().getPlayerList()) {
				if (other.equals(player)) return;
				other.getInventory().clone(inventory);
			}
			changing[0] = false;
		});

		PlayerEvents.player.HEALTH_UPDATE.register((player) -> {
			if (changing[1]) return;
			changing[1] = true;
			float health = player.getHealth();
			for (ServerPlayerEntity other : player.getServer().getPlayerManager().getPlayerList()) {
				if (other.equals(player)) return;
				other.setHealth(health);
			}
			changing[1] = false;
		});
	}
}
