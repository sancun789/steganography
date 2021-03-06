package com.tech_viks.steganoapp;
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
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.tech_viks.steganoapp.R;

import java.util.Arrays;

public class extract1 extends Activity {
    String selected_image_URL; // stores url of the image to be used
    private static final int SELECT_PICTURE = 1;
    Button dec;
    String pass1 ;
    TextView key ;
    PBE pbe;



    @Override
    protected void onCreate(Bundle savedInstanceState) {



        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_extract1);

        dec = (Button)findViewById(R.id.extract);
        key = (TextView)findViewById(R.id.e_key);

        try{


            dec.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub

                        choose_image();

                }
            });


        }
        catch(Exception e){
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
        }


    private void choose_image(){
        pass1= key.getText().toString();
        if(   pass1!=null && !pass1.equals("") ) {

            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent,"Select Picture"), SELECT_PICTURE);

        } else {



            //test code
            /*
            pbe=new PBE();

            int x=949494491;


            //String ssz="this_is_password_and_length_is_a_bit_long" ;
            String ssz="666123" ;
            String ss="ssssssssss" ;

            try{

            //x=pbe.lenth_decrypt(ss,ssz);
                //ss=pbe.encrypt(ss,ssz);
                ss=pbe.key(ssz);

                //ss=pbe.lenth_encrypt(ssz.length(),ssz);
                x=pbe.lenth_decrypt(ss,ssz);

                ss=pbe.decrypt(ss,ssz);

               // ss=pbe.decrypt(ss,ssz);
            }

            catch(Exception e){
                Toast.makeText(extract1.this, ssz, Toast.LENGTH_LONG)
                        .show();
            }

            String qq="sad";
            String q="159";
            q=q+qq;

           //ss=String.valueOf(x);

                    Toast.makeText(extract1.this,ss , Toast.LENGTH_LONG)
                    .show();
*/


           Toast.makeText(extract1.this, "no secret key", Toast.LENGTH_LONG)
                   .show();
        }

}


    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_PICTURE) {

                Uri selectedImageUri = data.getData();

                selected_image_URL = getFilePath(extract1.this , selectedImageUri);

                if(selected_image_URL!=null || selected_image_URL!=""){
                    //Toast.makeText(FirstActivity.this, selected_image_URL, Toast.LENGTH_LONG).show();

                    Intent toDisplayMessage = new Intent(extract1.this,DisplayMessage.class);
                    toDisplayMessage.putExtra("ImageURL", selected_image_URL);






                    toDisplayMessage.putExtra("pass", pass1);



                    if(   pass1!=null && !pass1.equals("") ) {
                        startActivity(toDisplayMessage);
                        finish();

                } else {

                   Toast.makeText(extract1.this, "no secret key", Toast.LENGTH_LONG)
                          .show();
                }







                }
                else{
                    Toast.makeText(extract1.this, "Something went wrong...try again", Toast.LENGTH_LONG)
                            .show();
                }
            }
        }
    }

// ============= function to get the absolute path for an uri ===================

    public static String getFilePath(Context context, Uri uri) {
        String selection = null;
        String[] selectionArgs = null;
        // Uri is different in versions after KITKAT (Android 4.4), we need to
        if (Build.VERSION.SDK_INT >= 19 && DocumentsContract.isDocumentUri(context.getApplicationContext(), uri)) {
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                return Environment.getExternalStorageDirectory() + "/" + split[1];
            } else if (isDownloadsDocument(uri)) {
                final String id = DocumentsContract.getDocumentId(uri);
                uri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));
            } else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];
                if ("image".equals(type)) {
                    uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }
                selection = "_id=?";
                selectionArgs = new String[]{
                        split[1]
                };
            }
        }
        if ("content".equalsIgnoreCase(uri.getScheme())) {
            String[] projection = {
                    MediaStore.Images.Media.DATA
            };
            Cursor cursor = null;
            try {
                cursor = context.getContentResolver()
                        .query(uri, projection, selection, selectionArgs, null);
                int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                if (cursor.moveToFirst()) {
                    return cursor.getString(column_index);
                }
            } catch (Exception e) {
            }
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }
        return null;
    }

    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }
}