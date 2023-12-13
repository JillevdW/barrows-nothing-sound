package dev.jille.barrowsnothingsound;

import net.runelite.client.RuneLite;
import net.runelite.client.externalplugins.ExternalPluginManager;

public class BarrowsNothingSoundPluginTest
{
	public static void main(String[] args) throws Exception
	{
		ExternalPluginManager.loadBuiltin(BarrowsNothingSoundPlugin.class);
		RuneLite.main(args);
	}
}