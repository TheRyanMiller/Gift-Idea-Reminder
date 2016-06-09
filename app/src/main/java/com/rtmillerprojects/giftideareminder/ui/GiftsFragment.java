package com.rtmillerprojects.giftideareminder.ui;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.rtmillerprojects.giftideareminder.R;
import com.rtmillerprojects.giftideareminder.adapter.ContactsAdapter;
import com.rtmillerprojects.giftideareminder.listener.FabClickListener;
import com.rtmillerprojects.giftideareminder.model.Contact;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Ryan on 5/21/2016.
 * Great resources at: https://developer.android.com/training/camera/photobasics.html#TaskGallery
 */
public class GiftsFragment extends BaseFragment implements FabClickListener{

    private ContactsAdapter contactsAdapter;
    private LinearLayoutManager layoutManager;
    private ArrayList<Contact> contacts;
    private PackageManager packageManager;
    private ImageView mImageView;
    private String mCurrentPhotoPath;
    private Button takePicture;
    private View rootView;
    private File photoFile;
    private ImageView imageView;
    private Uri mImageUri;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    static final int REQUEST_TAKE_PHOTO = 2;


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstance){
        if(container==null) {
            return null;
        }
        rootView = inflater.inflate(R.layout.gifts_fragment, container, false);
        takePicture = (Button) rootView.findViewById(R.id.btn_take_picture);
        imageView = (ImageView) rootView.findViewById(R.id.imageView2);
        takePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dispatchTakePictureIntent();
            }
        });

        return rootView;
    }

    public GiftsFragment() {
        // Required empty public constructor
    }
/*
    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(ACA.getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }
*/
    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(ACA.getPackageManager()) != null) {
            // Create the File where the photo should go
            photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                mImageUri=Uri.fromFile(photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile));
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }
    }

    /*
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_TAKE_PHOTO && resultCode == Activity.RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            mImageView.setImageBitmap(imageBitmap);
        }
    }
    */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent)
    {
        if(requestCode==REQUEST_TAKE_PHOTO && resultCode==ACA.RESULT_OK)
        {
            //... some code to inflate/create/find appropriate ImageView to place grabbed image
            grabImage(imageView);
        }
        super.onActivityResult(requestCode, resultCode, intent);
    }
    public void grabImage(ImageView imageView)
    {
        ACA.getContentResolver().notifyChange(mImageUri, null);
        ContentResolver cr = ACA.getContentResolver();
        Bitmap bitmap;
        try
        {
            bitmap = android.provider.MediaStore.Images.Media.getBitmap(cr, mImageUri);
            imageView.setImageBitmap(bitmap);
        }
        catch (Exception e)
        {
            Toast.makeText(ACA, "Failed to load", Toast.LENGTH_SHORT).show();
            Log.d("TAG", "Failed to load", e);
        }
    }

    /*
    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }
    */

    public static GiftsFragment newInstance() {
        GiftsFragment fragment = new GiftsFragment();
        return fragment;
    }



    @Override
    public void fabClickAction() {
        Toast.makeText(ACA,"WE ARE IN GIFTS",Toast.LENGTH_SHORT).show();
    }

    public PackageManager getPackageManager() {
        return packageManager;
    }


    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        File mediaFile = new File(storageDir.getPath() + File.separator +
                "IMG_"+ timeStamp + ".jpg");
        /*
        File image = File.createTempFile(
                imageFileName,
                ".jpg",
                storageDir
        );
        */
        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = "file:" + mediaFile.getAbsolutePath();
        return mediaFile;
    }



    private void galleryAddPic() {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(mCurrentPhotoPath);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        ACA.sendBroadcast(mediaScanIntent);
    }
    private void setPic() {
        // Get the dimensions of the View
        int targetW = mImageView.getWidth();
        int targetH = mImageView.getHeight();

        // Get the dimensions of the bitmap
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        // Determine how much to scale down the image
        int scaleFactor = Math.min(photoW/targetW, photoH/targetH);

        // Decode the image file into a Bitmap sized to fill the View
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true;

        Bitmap bitmap = BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
        mImageView.setImageBitmap(bitmap);
    }
}
