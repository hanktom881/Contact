package com.tom.contact;

import android.app.Activity;
import android.content.ContentResolver;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ContentResolver cr = getContentResolver();
        Cursor c = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                new String[]{ContactsContract.Contacts._ID, ContactsContract.Contacts.DISPLAY_NAME, ContactsContract.CommonDataKinds.Phone.NUMBER, ContactsContract.Contacts.PHOTO_URI},
                null, null, null);
        String[] from = {ContactsContract.Contacts.DISPLAY_NAME, ContactsContract.CommonDataKinds.Phone.NUMBER, ContactsContract.Contacts.PHOTO_URI};
        int[] to = {R.id.row_name, R.id.row_number, R.id.row_image};
        SimpleCursorAdapter adapter = new SimpleCursorAdapter(
                this, R.layout.contact_row, c , from , to, 1);
        ListView list = (ListView) findViewById(R.id.list);
        list.setAdapter(adapter);
//        Cursor c = cr.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
 /*       ListView list = (ListView) findViewById(R.id.list);
        String[] from = {ContactsContract.Contacts.DISPLAY_NAME};
        int[] to = {android.R.id.text1};
        SimpleCursorAdapter adapter = new SimpleCursorAdapter(
                this, android.R.layout.simple_list_item_1,
                c, from, to, 1);
        list.setAdapter(adapter);*/
/*
        while(c.moveToNext()){
            int id = c.getInt(c.getColumnIndex(ContactsContract.Contacts._ID));
            String dname = c.getString(c.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
            int hasPhone = c.getInt(c.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));
            Log.d("CONTACT", id+"/"+dname+"/"+hasPhone);
            // Phones
            if (hasPhone==1) {
                Cursor c2 = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                        null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=?",
                        new String[]{id + ""}, null);
                while (c2.moveToNext()) {
                    String number = c2.getString(c2.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                    Log.d("PH", "  " + number);
                }
            }
        }*/
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
