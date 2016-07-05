package me.barry1990.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;


public class FileManagement {
	
	public static short readShortFrom(FileInputStream in) throws IOException {
		byte[] buffer = new byte[2];
		if (!(in.read(buffer) < 2)) {			
			short ret = (short) ((buffer[0] << 8) | (buffer[1] & 0xFF));
			return ret;
		}
		throw new IOException();
	}
	
	public static void writeShortTo(FileOutputStream out , short s) throws IOException {
		byte[] buffer = new byte[] {(byte) (s >> 8), (byte) (s & 0xFF)};		
		out.write(buffer);
	}
	
	public static void deleteFilesOfDir(File dir) {
		if (!dir.isDirectory())
			return;
		File[] files = dir.listFiles();
		for (File file : files)
			file.delete();
	}
	
	public static boolean deleteDirectory(File directory) {
	    if(directory.exists()) {
	        File[] files = directory.listFiles();
	        if(files != null){
	            for(File file : files) {
	                if(file.isDirectory()) {
	                	FileManagement.deleteDirectory(file);
	                }
	                else {
	                    file.delete();
	                }
	            }
	        }
	    }
	    return directory.delete();
	}

}
