package me.barry1990.skygrid;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public final class TitleManager {

	private static Class<?>	title			= getNMSClass("PacketPlayOutTitle");
	private static Class<?>	enumtitleaction	= TitleManager.title.getDeclaredClasses()[0];
	private static Class<?>	chatserial		= getNMSClass("IChatBaseComponent").getDeclaredClasses()[0];

	public static void sendActionBar(Player p, String text) {

		try {
			TitleManager.sendPacket(p, "PacketPlayOutChat", new Class[] { getNMSClass("IChatBaseComponent"), byte.class }, TitleManager.chatserial.getMethod("a", String.class).invoke(null, "{\"text\":\"" + text + "\"}"), (byte) 2);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void sendHeaderAndFooter(Player p, String header, String footer) {

		try {
			Object packet = TitleManager.getNMSClass("PacketPlayOutPlayerListHeaderFooter").newInstance();
			TitleManager.getField(packet.getClass().getDeclaredField("a")).set(packet, TitleManager.chatserial.getMethod("a", String.class).invoke(null, "{\"text\":\"" + header + "\"}"));
			TitleManager.getField(packet.getClass().getDeclaredField("b")).set(packet, TitleManager.chatserial.getMethod("a", String.class).invoke(null, "{\"text\":\"" + footer + "\"}"));
			TitleManager.sendPacket(p, packet);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void sendTitles(Player p, String title, String subtitle, int fadeIn, int stay, int fadeOut) {

		TitleManager.clear(p);
		TitleManager.sendTimings(p, fadeIn, stay, fadeOut);
		TitleManager.sendTitle(p, title);
		TitleManager.sendSubTitle(p, subtitle);
	}

	private static void sendTitle(Player p, String title) {

		sendTitlePackage(p, title, "TITLE");
	}

	private static void sendSubTitle(Player p, String subtitle) {

		sendTitlePackage(p, subtitle, "SUBTITLE");
	}

	private static void sendTitlePackage(Player p, String text, String field) {

		try {
			Object t = TitleManager.title.newInstance();
			TitleManager.getField(t.getClass().getDeclaredField("a")).set(t, TitleManager.getField(TitleManager.enumtitleaction.getDeclaredField(field)).get(null));
			TitleManager.getField(t.getClass().getDeclaredField("b")).set(t, TitleManager.chatserial.getMethod("a", String.class).invoke(null, "{\"text\":\"" + text + "\"}"));		
			TitleManager.sendPacket(p, t);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void sendTimings(Player p, int fadeIn, int stay, int fadeOut) {

		try {
			Object t = TitleManager.title.newInstance();
			TitleManager.getField(t.getClass().getDeclaredField("a")).set(t, TitleManager.getField(TitleManager.enumtitleaction.getDeclaredField("TIMES")).get(null));
			TitleManager.getField(t.getClass().getDeclaredField("c")).set(t, fadeIn);
			TitleManager.getField(t.getClass().getDeclaredField("d")).set(t, stay);
			TitleManager.getField(t.getClass().getDeclaredField("e")).set(t, fadeOut);
			TitleManager.sendPacket(p, t);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/*
	private static void reset(Player p) {

		try {
			Object t = title.newInstance();
			getField(t.getClass().getDeclaredField("a")).set(t, getField(enumtitleaction.getDeclaredField("RESET")).get(null));
			sendPacket(p, t);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	*/

	private static void clear(Player p) {

		try {
			Object t = TitleManager.title.newInstance();
			TitleManager.getField(t.getClass().getDeclaredField("a")).set(t, TitleManager.getField(TitleManager.enumtitleaction.getDeclaredField("CLEAR")).get(null));
			TitleManager.sendPacket(p, t);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void sendPacket(Player p, Object packet) {

		try {
			Object nmsPlayer = getNMSPlayer(p);
			Object connection = nmsPlayer.getClass().getField("playerConnection").get(nmsPlayer);
			connection.getClass().getMethod("sendPacket", TitleManager.getNMSClass("Packet")).invoke(connection, packet);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void sendPacket(Player p, String packetName, Class<?>[] parameterclass, Object... parameters) {

		try {
			Object nmsPlayer = getNMSPlayer(p);
			Object connection = nmsPlayer.getClass().getField("playerConnection").get(nmsPlayer);
			Object packet = Class.forName(nmsPlayer.getClass().getPackage().getName() + "." + packetName).getConstructor(parameterclass).newInstance(parameters);
			connection.getClass().getMethod("sendPacket", TitleManager.getNMSClass("Packet")).invoke(connection, packet);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static Class<?> getNMSClass(String name) {

		String version = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
		try {
			return Class.forName("net.minecraft.server." + version + "." + name);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return null;
		}
	}

	private static Object getNMSPlayer(Player p) {

		try {
			return p.getClass().getMethod("getHandle").invoke(p);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private static Field getField(Field f) {

		f.setAccessible(true);
		return f;
	}
}