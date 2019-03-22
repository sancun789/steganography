package com.tech_viks.steganoapp;

import java.net.URISyntaxException;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentUris;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class FirstActivity extends Activity {

	// ============== variable declaration ===============
	Button enc,dec;


	int backFlag = 0;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_first);

		enc = (Button)findViewById(R.id.encrypt);
		dec = (Button)findViewById(R.id.retrieve);
		
		// ============ coding for encrypt button ==============
		enc.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//choose_image();
				
				//if(selected_image_URL!=null || selected_image_URL!=""){
				try{
					Intent toprocess = new Intent(FirstActivity.this,EncryptMessage.class);
					//toDisplayMessage.putExtra("ImageURL", selected_image_URL);
					startActivity(toprocess);
				}
				catch(Exception e){
					
				}
				//}
				/*else{
					Toast.makeText(FirstActivity.this, "Something went wrong...try again", Toast.LENGTH_LONG)
					.show();
				}*/
			}
		});
		
		// ============ coding for decrypt button ==============
		dec.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub



                    Intent toprocess = new Intent(FirstActivity.this,extract1.class);

                    startActivity(toprocess);



			}
		});
		
		
	}
	
	private void choose_image(){

		/*Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Select Picture"), SELECT_PICTURE);
*/



	}

	
	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		backFlag=0;
	}
	
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		if (backFlag!=0) {
			super.onBackPressed();
		}
		//super.onBackPressed();
				
		AlertDialog adb = new AlertDialog.Builder(FirstActivity.this).setTitle("Are you sure you want to exit")
				.setPositiveButton("Exit", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						backFlag+=1;
						onBackPressed();
					}
				})
				.setNegativeButton("Cancel", null).show();
	}
	
	
	



	
	
}
