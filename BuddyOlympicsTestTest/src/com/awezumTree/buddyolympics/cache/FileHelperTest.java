package com.awezumTree.buddyolympics.cache;

import junit.framework.Assert;

import com.awezumTree.buddyolympics.cache.CacheException;
import com.awezumTree.buddyolympics.cache.FileHelper;

import android.test.AndroidTestCase;

public class FileHelperTest extends AndroidTestCase {
	private final static String SOME_STRING = "kambaya mylord";
	private final FileHelper fh = new FileHelper("test-cache-file");
	
	public void testWriteThenReadFromFile() {
		try {
			fh.writeToFile(SOME_STRING, getContext());
		} catch (CacheException e) {
			fail("CacheException on write: "+ e);
		}
		
		try {
			Assert.assertEquals(SOME_STRING, fh.readFromFile(getContext()));
		} catch (CacheException e) {
			fail("CacheException on read: "+ e);
		}
	}
	
	public void testWriteToFileIsIdempotent() {
		try {
			fh.writeToFile(SOME_STRING, getContext());
			fh.writeToFile(SOME_STRING, getContext());
		} catch (CacheException e) {
			fail("CacheException on write: "+ e);
		}
		
		try {
			Assert.assertEquals(SOME_STRING, fh.readFromFile(getContext()));
		} catch (CacheException e) {
			fail("CacheException on read: "+ e);
		}
	}
}
