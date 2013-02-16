package com.awezumTree.buddyolympics.cache;

import java.util.HashMap;
import java.util.Map;

/**
 * Simple Registry for storing shit u'd like 
 * to have in memory.
 * 
 * Shit can be persisted to db, but U have to implement this
 * yourself. Please. Feel free. 
 * 
 * @author michaekg
 *
 */
public class SimpleRegistry {

	private static SimpleRegistry instance;
	
	private Map<String, Object> registryMap = new HashMap<String, Object>();
	
	private SimpleRegistry() {}
	
	public static SimpleRegistry getInstance() {
		if ( instance == null ) {
			instance = new SimpleRegistry();
		}
		
		return instance;
	}
	
	public void putObject(String key, Object value) {
		registryMap.put(key, value);
	}
	
	public Object getObject(String key) {
		return registryMap.get(key);
	}	
}
