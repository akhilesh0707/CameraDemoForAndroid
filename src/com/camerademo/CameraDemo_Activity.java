package com.camerademo;

import java.io.File;

import com.example.camerademo.R;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class CameraDemo_Activity extends Activity 
{
	public final String APP_TAG = "CameraDemo";
	public final static int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 1034;
	public String photoFileName = "photo.jpg";
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	public void onTakePicture(View view) 
	{
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		intent.putExtra(MediaStore.EXTRA_OUTPUT, getPhotoFileUri(photoFileName)); // set the image file name
		startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) 
		{
			if (resultCode == RESULT_OK) 
			{
				Uri takenPhotoUri = getPhotoFileUri(photoFileName);
				Bitmap takenImage = BitmapFactory.decodeFile(takenPhotoUri.getPath());
				ImageView ivImagePreview = (ImageView) findViewById(R.id.ivImagePreview);
				ivImagePreview.setImageBitmap(takenImage);   
			}
			else
			{
				Toast.makeText(this, "Picture wasn't taken!", Toast.LENGTH_SHORT).show();
			}
		}
	}


	public Uri getPhotoFileUri(String fileName) 
	{
		File mediaStorageDir = new File(
				Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), APP_TAG);

		if (!mediaStorageDir.exists() && !mediaStorageDir.mkdirs())
		{
			Log.d(APP_TAG, "failed to create directory");
		}

		return Uri.fromFile(new File(mediaStorageDir.getPath() + File.separator + fileName));
	}
}
