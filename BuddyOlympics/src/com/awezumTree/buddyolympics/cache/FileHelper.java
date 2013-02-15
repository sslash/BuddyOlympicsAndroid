package com.awezumTree.buddyolympics.cache;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import android.content.Context;

public class FileHelper {
	private final String filename;
	
	public FileHelper(String filename) {
		this.filename = filename;
	}
	
	
	public void writeToFile(String value, Context context) throws CacheException {
		FileOutputStream fos;
		try {
			fos = context.openFileOutput(filename, Context.MODE_PRIVATE);
			fos.write(value.getBytes());
			fos.close();
		} catch (FileNotFoundException e) {
			throw new CacheException(e);
		} catch (IOException e) {
			throw new CacheException(e);
		} catch (NullPointerException e) {
			throw new CacheException(e);
		}
	}
	
	public String readFromFile(Context context) throws CacheException {
		
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
		} catch (FileNotFoundException e) {
			throw new CacheException(e);
		} catch (IOException e) {
			throw new CacheException(e);
		}
		
		return out;
	}
}
