package net.smileycorp.jeri;

import mezz.jei.startup.PlayerJoinedWorldEvent;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

@EventBusSubscriber(Side.CLIENT)
public class JERIClientHandler {

	@SubscribeEvent(priority = EventPriority.LOWEST)
	public static void playerJoin(PlayerJoinedWorldEvent event) {
		Minecraft.getMinecraft().addScheduledTask(()-> {
			for (JEIPluginJERI plugin : JustEnoughRecipeIntegrations.JERI_PLUGINS)
				plugin.onPlayerJoinWorld(JustEnoughRecipeIntegrations.getRegistry());});
	}

}
