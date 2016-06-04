package com.rtmillerprojects.giftideareminder.ui;

import android.Manifest;
import android.content.ContentUris;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.rtmillerprojects.giftideareminder.R;
import com.rtmillerprojects.giftideareminder.adapter.ContactsAdapter;
import com.rtmillerprojects.giftideareminder.model.Contact;
import com.rtmillerprojects.giftideareminder.util.DatabaseHelper;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Ryan on 5/29/2016.
 */
public class ContactsFragment extends BaseFragment{

    private LinearLayoutManager layoutManager;
    @Bind(R.id.recycler_view) RecyclerView recyclerView;
    private ArrayList<Contact> contacts;
    private TextView noContactsText;
    private Uri uriContact;
    private String contactID;
    DatabaseHelper db;

    private static final int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 1;
    private static final int RESULT_PICK_CONTACT = 100; // For the Intent selection
    private static final int REQUEST_CODE_PICK_CONTACTS = 5;
    private static final String TAG = "ContactsFragment";
    private String contactName;
    private Bitmap contactPhoto;
    private String contactNumber;
    private ContactsAdapter contactsAdapter;


    public ContactsFragment() {
        // Required empty public constructor
    }
    @Override public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(contacts == null){contacts = new ArrayList<>();}
    }
    public static ContactsFragment newInstance() {
        ContactsFragment fragment = new ContactsFragment();
        return fragment;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstance){
        //inflate the contacts layout
        View rootView = inflater.inflate(R.layout.contacts_fragment, container, false);
        ButterKnife.bind(this, rootView);
        db = DatabaseHelper.getInstance(ACA);
        //need to pull in contacts from db
        layoutManager = new LinearLayoutManager(ACA);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        contacts = db.getAllContacts();
        ContactsAdapter contactsAdapter = new ContactsAdapter(contacts, ACA);
        recyclerView.setAdapter(contactsAdapter);
        recyclerView.setLayoutManager(layoutManager);

        noContactsText = (TextView) rootView.findViewById(R.id.noContactsText);
        adjustContactVisibility(contactsAdapter);

        Button addContact = (Button) rootView.findViewById(R.id.btn_addContact);
        addContact.setOnClickListener(new View.OnClickListener(){
            @Override public void onClick(View view) {
                //new Intent(Intent.ACTION_PICK, );
                //Intent in =
                startActivityForResult(new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI), RESULT_PICK_CONTACT);
            }

        });
        //addContact.setText(contactName);
        return rootView;
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults){
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_CONTACTS: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(ACA,"Contacts permissions are now obtained",Toast.LENGTH_SHORT).show();
                }
                break;
            }
        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RESULT_PICK_CONTACT && resultCode == ACA.RESULT_OK) {
            Log.d(TAG, "Response: " + data.toString());
            uriContact = data.getData();
            Cursor cursorID = ACA.getContentResolver().query(uriContact,
                    new String[]{ContactsContract.Contacts._ID},
                    null, null, null);
            if (cursorID.moveToFirst()) {
                contactID = cursorID.getString(cursorID.getColumnIndex(ContactsContract.Contacts._ID));
            }
            contactName = retrieveContactName(contactID);
            contactNumber = retrieveContactNumber(contactID);
            contactPhoto = retrieveContactPhoto(contactID);
            Log.d(TAG, "Contact ID: " + contactID);
            Log.d(TAG, "SRSLY CONTACT NAME IS: " + contactName);
            Log.d(TAG, "SRSLY CONTACT NUMBER IS: " + contactNumber);
            Contact newContact = new Contact(contactName,contactNumber, contactPhoto);
            db.insertContact(newContact);
            contacts = db.getAllContacts();
            if (contactsAdapter == null) {
                contactsAdapter = new ContactsAdapter(contacts, ACA);
                recyclerView.setAdapter(contactsAdapter);
            }
            adjustContactVisibility(contactsAdapter);
            recyclerView.getAdapter().notifyDataSetChanged();
        }
    }
    private Bitmap retrieveContactPhoto(String theContactId) {

        Bitmap photo = null;
        try {
            InputStream inputStream = ContactsContract.Contacts.openContactPhotoInputStream(ACA.getContentResolver(),
                    ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI, new Long(contactID)));
            if (inputStream != null) {
                photo = BitmapFactory.decodeStream(inputStream);
            }
            assert inputStream != null;
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return photo;
    }

    private String retrieveContactNumber(String theContactId) {
        String contactNumber = null;
        // getting contacts ID
        // Using the contact ID now we will get contact phone number
        Cursor cursorPhone = ACA.getContentResolver().query(
                //// The content URI of the words table
                ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                // The columns to return for each row
                new String[]{ContactsContract.CommonDataKinds.Phone.NUMBER},
                // Selection criteria
                ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ? AND " +
                        ContactsContract.CommonDataKinds.Phone.TYPE + " = " +
                        ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE,
                // Selection criteria
                new String[]{theContactId},
                //Sort Order
                null);
        if (cursorPhone.moveToFirst()) {
            contactNumber = cursorPhone.getString(cursorPhone.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
        }
        cursorPhone.close();
        Log.d(TAG, "Contact Phone Number: " + contactNumber);
        return contactNumber;
    }

    private String retrieveContactName(String theContactId) {
        String contactName = null;
        // querying contact data store
        Cursor cursor = ACA.getContentResolver().query(uriContact, null, null, null, null);
        if (cursor.moveToFirst()) {
            // DISPLAY_NAME = The display name for the contact.
            // HAS_PHONE_NUMBER =   An indicator of whether this contact has at least one phone number.
            contactName = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
        }
        cursor.close();
        Log.d(TAG, "CONTACT SOURCE_ID: " + theContactId);
        return contactName;
    }
    private void adjustContactVisibility(ContactsAdapter contactsAdapter){
        int numContacts = contactsAdapter.getItemCount();
        noContactsText.setText(""+numContacts);
        if(numContacts>0){
            noContactsText.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
        }
        else{
            recyclerView.setVisibility(View.VISIBLE);
            noContactsText.setVisibility(View.VISIBLE);
        }
    }

    private void doStuff(){
        DatabaseHelper db;
        db = DatabaseHelper.getInstance(ACA);
    }

}
