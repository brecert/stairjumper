package dev.bree.stairjumper;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.block.Block;
import net.minecraft.client.MinecraftClient;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class StairJumperMod implements ModInitializer {
	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final Logger LOGGER = LoggerFactory.getLogger("stairjumper");

	// todo: use own tag?
	public static final TagKey<Block> STAIRS = TagKey.of(RegistryKeys.BLOCK, new Identifier("minecraft", "stairs"));

	@Override
	public void onInitialize() {
		// this will override auto jump settings.
		ClientTickEvents.END_CLIENT_TICK.register((client) -> {
			if(client.player != null) {
				var world = client.player.getEntityWorld();
				var blockPos = client.player.getBlockPos();
				var state = world.getBlockState(blockPos);
				var autoJump = MinecraftClient.getInstance().options.getAutoJump();
				if (state.isIn(STAIRS)) {
					autoJump.setValue(true);
				} else {
					autoJump.setValue(false);
				}
			}
		});

		LOGGER.info("Loaded stairjumper!");
	}
}
