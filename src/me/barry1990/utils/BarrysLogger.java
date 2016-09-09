package me.barry1990.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@SuppressWarnings("unused")
/**
 * BarrysLogger
 * 
 * @author Barry1990
 */
public class BarrysLogger {
	
	public static boolean PRINT_LOGS = true;
	
	private static final String RESET = 		"\u001B[0m";	
	private static final String BLACK = 		"\u001B[30m";
	private static final String DARKGRAY = 		"\u001B[30;1m";	
	private static final String DARKRED = 		"\u001B[31m";
	private static final String RED = 			"\u001B[31;1m";	
	private static final String DARKGREEN = 	"\u001B[32m";
	private static final String GREEN = 		"\u001B[32;1m";
	private static final String DARKYELLOW = 	"\u001B[33m";
	private static final String YELLOW = 		"\u001B[33;1m";
	private static final String DARKBLUE = 		"\u001B[34m";
	private static final String BLUE = 			"\u001B[34;1m";
	private static final String DARKPURPLE = 	"\u001B[35m";
	private static final String PURPLE = 		"\u001B[35;1m";
	private static final String DARKCYAN = 		"\u001B[36m";
	private static final String CYAN = 			"\u001B[36;1m";
	private static final String GRAY = 			"\u001B[37m";
	private static final String WHITE = 		"\u001B[37;1m";
	
	private static final DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss.SSSS");
	
	
	/////////////////////////////////
	// 		INFO BOOLEAN
	/////////////////////////////////
	
	public static void info(Object this_, String varName, boolean var) {
	if (!PRINT_LOGS) return;
	System.out.print(String.format(CYAN + "#I %s : boolean %s = " + RESET, this_.getClass().getName(),varName) + (var ? "true" : "false"));
	}
	
	public static void info(String varName, boolean var) {
	if (!PRINT_LOGS) return;
	System.out.print(String.format(CYAN + "#I boolean %s = " + RESET, varName) + (var ? "true" : "false"));
	}
	
	/////////////////////////////////
	// 		INFO BYTE
	/////////////////////////////////
	
	public static void info(Object this_, String varName, byte var) {
		if (!PRINT_LOGS) return;
		System.out.print(String.format(CYAN + "#I %s : byte %s = %d" + RESET, this_.getClass().getName(),varName, var));
	}
	
	public static void info(String varName, byte var) {
		if (!PRINT_LOGS) return;
		System.out.print(String.format(CYAN + "#I byte %s = %d" + RESET, varName, var));
	}
	
	/////////////////////////////////
	// 		INFO SHORT
	/////////////////////////////////
	
	public static void info(Object this_, String varName, short var) {
		if (!PRINT_LOGS) return;
		System.out.print(String.format(CYAN + "#I %s : short %s = %d" + RESET, this_.getClass().getName(),varName, var));
	}
	
	public static void info(String varName, short var) {
		if (!PRINT_LOGS) return;
		System.out.print(String.format(CYAN + "#I short %s = %d" + RESET, varName, var));
	}
	
	/////////////////////////////////
	// 		INFO INT
	/////////////////////////////////
	
	public static void info(Object this_, String varName, int var) {
		if (!PRINT_LOGS) return;
		System.out.print(String.format(CYAN + "#I %s : int %s = %d" + RESET, this_.getClass().getName(),varName, var));
	}
	
	public static void info(String varName, int var) {
		if (!PRINT_LOGS) return;
		System.out.print(String.format(CYAN + "#I int %s = %d" + RESET, varName, var));
	}
	
	/////////////////////////////////
	// 		INFO LONG
	/////////////////////////////////
	
	public static void info(Object this_, String varName, long var) {
		if (!PRINT_LOGS) return;
		System.out.print(String.format(CYAN + "#I %s : long %s = %d" + RESET, this_.getClass().getName(),varName, var));
	}
	
	public static void info(String varName, long var) {
		if (!PRINT_LOGS) return;
		System.out.print(String.format(CYAN + "#I long %s = %d" + RESET, varName, var));
	}
	
	/////////////////////////////////
	// 		INFO FLOAT
	/////////////////////////////////
	
	public static void info(Object this_, String varName, float var) {
		if (!PRINT_LOGS) return;
		System.out.print(String.format(CYAN + "#I %s : float %s = %f" + RESET, this_.getClass().getName(),varName, var));
	}
	
	public static void info(String varName, float var) {
		if (!PRINT_LOGS) return;
		System.out.print(String.format(CYAN + "#I float %s = %f" + RESET, varName, var));
	}
	
	/////////////////////////////////
	// 		INFO DOUBLE
	/////////////////////////////////
	
	public static void info(Object this_, String varName, double var) {
		if (!PRINT_LOGS) return;
		System.out.print(String.format(CYAN + "#I %s : double %s = %f" + RESET, this_.getClass().getName(),varName, var));
	}
	
	public static void info(String varName, double var) {
		if (!PRINT_LOGS) return;
		System.out.print(String.format(CYAN + "#I double %s = %f" + RESET, varName, var));
	}
	
	/////////////////////////////////
	// 		INFO STRING
	/////////////////////////////////
	
	public static void info(Object this_, String varName, String var) {
		if (!PRINT_LOGS) return;
		System.out.print(String.format(CYAN + "#I %s : String %s = \"%s\"" + RESET, this_.getClass().getName(),varName, var));
	}
	public static void info(String varName, String var) {
		if (!PRINT_LOGS) return;
		System.out.print(String.format(CYAN + "#I String %s = \"%s\"" + RESET, varName, var));
	}
	
	/////////////////////////////////
	// 		INFO ENUM
	/////////////////////////////////
	
	public static void infoEnum(Object this_, String varName, Enum<?> var) {
		if (!PRINT_LOGS) return;
		System.out.print(String.format(CYAN + "#I %s : %s %s = %s" + RESET, this_.getClass().getName(), var.getClass().getName(), varName, var.name()));
	}
	
	public static void infoEnum(String varName, Enum<?> var) {
		if (!PRINT_LOGS) return;
		System.out.print(String.format(CYAN + "#I %s %s = %s" + RESET, var.getClass().getName(), varName, var.name()));
	}
	
	/////////////////////////////////
	// 		INFO 
	/////////////////////////////////
	
	public static void info(Object this_, String var) {
		if (!PRINT_LOGS) return;
		System.out.print(String.format(CYAN + "#I %s : %s" + RESET, this_.getClass().getName(), var));
	}
	
	public static void info(String var) {
		if (!PRINT_LOGS) return;
		System.out.print(String.format(CYAN + "#I %s" + RESET,  var));
	}

		
	/////////////////////////////////
	// 		ERROR 
	/////////////////////////////////
	
	public static void error(Object this_, String errorMessage) {
		if (!PRINT_LOGS) return;
		System.out.print(String.format(RED + "#E %s : " + WHITE + " %s" + RESET, this_.getClass().getName(),errorMessage));
	}
	
	public static void error(String errorMessage) {
		if (!PRINT_LOGS) return;
		System.out.print(String.format(RED + "#E : " + WHITE + " %s" + RESET, errorMessage));
	}
	
	/////////////////////////////////
	// 		WARNING 
	/////////////////////////////////
	
	public static void warn(Object this_, String errorMessage) {
		if (!PRINT_LOGS) return;
		System.out.print(String.format(DARKYELLOW + "#E %s : " + WHITE + " %s" + RESET, this_.getClass().getName(),errorMessage));
	}
	
	public static void warn(String errorMessage) {
		if (!PRINT_LOGS) return;
		System.out.print(String.format(DARKYELLOW + "#E : " + WHITE + " %s" + RESET, errorMessage));
	}
	
	
	/////////////////////////////////
	// 		TIMINGS 
	/////////////////////////////////
	
	public static long timing(Object this_, String message) {
		if (!PRINT_LOGS) return -1;
		Date d = new Date();
		System.out.print(String.format(PURPLE + "#T %s : " + CYAN + "%s" + WHITE + " %s" + RESET, dateFormat.format(d), this_.getClass().getName(),message));
		return d.getTime();
	}
	
	public static long timing(String message) {
		if (!PRINT_LOGS) return 0;
		Date d = new Date();
		System.out.print(String.format(PURPLE + "#T %s : " + WHITE + " %s" + RESET, dateFormat.format(d), message));
		return d.getTime();
	}
	
	public static long timing(long timing, Object this_, String message) {
		if (!PRINT_LOGS) return -1;
		Date d = new Date();
		Date d2 = new Date(d.getTime() - timing);
		System.out.print(String.format(PURPLE + "#T %s [%s]: " + CYAN + "%s" + WHITE + " %s" + RESET, dateFormat.format(d), dateFormat.format(d2), this_.getClass().getName(),message));
		return d.getTime();
	}

}
