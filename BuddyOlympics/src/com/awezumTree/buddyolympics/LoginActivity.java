package com.awezumTree.buddyolympics;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

import com.awezumTree.buddyolympics.activities.SignUpActivity;

public class LoginActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_login, menu);
        return true;
    }
    
    /**
     *  Called when the user clicks the signup button
     *	
     * Be public
     * Have a void return value
	 * Have a View as the only parameter (this will be the View that was clicked)
     * 
     */
    public void signUp(View view) {
    	
    	// An intent provides runtime binding between two components
    	Intent intent = new Intent(this, SignUpActivity.class);
    	startActivity(intent);
        
    }
    
}
