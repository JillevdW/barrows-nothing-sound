package dev.jille.barrowsnothingsound;

import com.google.common.collect.ImmutableMap;
import com.google.inject.Provides;

import javax.annotation.Nullable;
import javax.inject.Inject;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;

import lombok.extern.slf4j.Slf4j;
import net.runelite.api.*;
import net.runelite.api.events.GameStateChanged;
import net.runelite.api.events.WidgetLoaded;
import net.runelite.api.widgets.InterfaceID;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executor;

@Slf4j
@PluginDescriptor(
	name = "Barrows Nothing Sound"
)
public class BarrowsNothingSoundPlugin extends Plugin
{
	private static final HashMap<Integer, Integer> BARROWS_ITEMS = new HashMap<Integer, Integer>(
			Map.ofEntries(
					Map.entry(BarrowsItems.AHRIMSHOOD, 1),
					Map.entry(BarrowsItems.AHRIMSROBETOP, 1),
					Map.entry(BarrowsItems.AHRIMSROBESKIRT, 1),
					Map.entry(BarrowsItems.AHRIMSSTAFF, 1),
					Map.entry(BarrowsItems.DHAROKSHELM, 1),
					Map.entry(BarrowsItems.DHAROKSPLATEBODY, 1),
					Map.entry(BarrowsItems.DHAROKSPLATELEGS, 1),
					Map.entry(BarrowsItems.DHAROKSGREATAXE, 1),
					Map.entry(BarrowsItems.GUTHANSHELM, 1),
					Map.entry(BarrowsItems.GUTHANSPLATEBODY, 1),
					Map.entry(BarrowsItems.GUTHANSCHAINSKIRT, 1),
					Map.entry(BarrowsItems.GUTHANSWARSPEAR, 1),
					Map.entry(BarrowsItems.KARILSCOIF, 1),
					Map.entry(BarrowsItems.KARILSLEATHERTOP, 1),
					Map.entry(BarrowsItems.KARILSLEATHERSKIRT, 1),
					Map.entry(BarrowsItems.KARILSCROSSBOW, 1),
					Map.entry(BarrowsItems.TORAGSHELM, 1),
					Map.entry(BarrowsItems.TORAGSPLATEBODY, 1),
					Map.entry(BarrowsItems.TORAGSPLATELEGS, 1),
					Map.entry(BarrowsItems.TORAGSHAMMERS, 1),
					Map.entry(BarrowsItems.VERACSHELM, 1),
					Map.entry(BarrowsItems.VERACSBRASSARD, 1),
					Map.entry(BarrowsItems.VERACSPLATESKIRT, 1),
					Map.entry(BarrowsItems.VERACSFLAIL, 1)
			)
	);

	@Inject
	private Client client;

	@Inject
	private SoundManager soundManager;

	@Inject
	private BarrowsNothingSoundConfig config;

	@Override
	protected void startUp() throws Exception
	{
		log.info("Barrows Nothing Sound started!");
	}

	@Override
	protected void shutDown() throws Exception
	{
		log.info("Barrows Nothing Sound stopped!");
	}

	@Subscribe
	public void onWidgetLoaded(WidgetLoaded event)
	{
		if (event.getGroupId() == InterfaceID.BARROWS_REWARD)
		{
			ItemContainer barrowsRewardContainer = client.getItemContainer(InventoryID.BARROWS_REWARD);
			if (barrowsRewardContainer == null)
			{
				return;
			}

			boolean didReceiveBarrowsItem = false;
			Item[] items = barrowsRewardContainer.getItems();
			for (Item item : items)
			{
				if (BARROWS_ITEMS.containsKey(item.getId()))
				{
					didReceiveBarrowsItem = true;
					break;
				}
			}

			if (!didReceiveBarrowsItem)
			{
				soundManager.playSound();
			}
		}
	}



	@Provides
	BarrowsNothingSoundConfig provideConfig(ConfigManager configManager)
	{
		return configManager.getConfig(BarrowsNothingSoundConfig.class);
	}
}
