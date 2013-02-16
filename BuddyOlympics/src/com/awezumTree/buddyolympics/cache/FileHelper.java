package com.awezumTree.buddyolympics.cache;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;

import android.content.Context;
import android.util.Log;

public class FileHelper {
	private final String filename;
	
	protected FileHelper(String filename) {
		this.filename = filename;
	}
	
	
	protected void writeToFile(String value, Context context) throws CacheException {
		FileOutputStream fos;
		try {
			fos = getFileOutputStream(context);
			fos.write(value.getBytes());
			fos.close();
		} catch (Exception e) {
			throw new CacheException(e);
		}
	}
	
	protected String readFromFile(Context context) throws CacheException {
		
		FileInputStream in;
		String out;
		try {
			in = context.openFileInput(filename);
		    InputStreamReader inputStreamReader = new InputStreamReader(in);
		    BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
		    StringBuilder sb = new StringBuilder();
		    String line;
		    while ((line = bufferedReader.readLine()) != null) {
		        sb.append(line);
		    }
		    out = sb.toString();
		} catch (Exception e) {
			throw new CacheException(e);
		}
		
		return out;
	}
	
	private FileOutputStream getFileOutputStream(Context context) throws Exception{
		Log.d("LOLCAT", "get files dir - " + context.getFilesDir());
		return context.openFileOutput(filename, Context.MODE_PRIVATE);
	}
}
