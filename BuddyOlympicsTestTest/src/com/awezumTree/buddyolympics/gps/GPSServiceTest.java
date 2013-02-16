package com.awezumTree.buddyolympics.gps;

import gps.GPSService;
import junit.framework.Assert;
import android.content.Context;
import android.test.AndroidTestCase;

public class GPSServiceTest extends AndroidTestCase {
	
	Context context;

	@Override
	protected void setUp() throws Exception {
		context = getContext();
		super.setUp();
	}

	public void testGetLongitude() {
		GPSService service = new GPSService(context);
		service.subscribe();
		Assert.assertEquals(0.0, service.getLongitude());
		service.unsuscribe();
	}

	public void testGetLatitude() {
		GPSService service = new GPSService(context);
		service.subscribe();
		Assert.assertEquals(0.0, service.getLatitude());
		service.unsuscribe();
	}
}
