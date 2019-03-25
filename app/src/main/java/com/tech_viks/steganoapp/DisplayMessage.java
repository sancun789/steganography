package com.tech_viks.steganoapp;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuItem;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;
import android.widget.TextView;
import android.widget.Toast;
import java.io.FileWriter;
import java.io.IOException;

public class DisplayMessage extends Activity {

	// ========= variable declaration =============
	TextView message ;

	String imageToProcess;
	String pass;

	ProcessImage process_image;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		try{
		process_image = new ProcessImage();
		
		setContentView(R.layout.activity_display_message);
		message = (TextView)findViewById(R.id.messageText);
		message.setMovementMethod(ScrollingMovementMethod.getInstance());

		Bundle bndl = getIntent().getExtras();

		imageToProcess = bndl.getString("ImageURL");

		if(imageToProcess==null || imageToProcess==""){
		 Toast.makeText(getApplicationContext(), "no image seleted", Toast.LENGTH_LONG).show();
		}





		pass=bndl.getString("pass");

		String message_to_show = process_image.displayMessage(imageToProcess,pass);


				if(!message_to_show.equals("wrong password"))
				{
					Toast.makeText(getApplicationContext(), "correct secret key", Toast.LENGTH_LONG).show();
					//Toast.makeText(getApplicationContext(), message_to_show, Toast.LENGTH_LONG).show();
					message.setText("Message:" + message_to_show);
					//stringtxt(message_to_show);
					Toast.makeText(getApplicationContext(), "save the message at /sdcard/steganography", Toast.LENGTH_LONG).show();

				} else {
					Toast.makeText(getApplicationContext(), "wrong password", Toast.LENGTH_LONG).show();

				}





		}
		catch(Exception e){
			Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
			
		}
	}
	
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		//super.onBackPressed();
		Intent backPage = new Intent(DisplayMessage.this,FirstActivity.class);
		backPage.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(backPage);
		finish();
	}


	public static void stringtxt(String str){
		try {
			FileWriter fw = new FileWriter("/sdcard/steganography" + "/message.txt");//SD卡中的路径
			fw.flush();
			fw.write(str);
			fw.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
}
