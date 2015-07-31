package com.tom.contact;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;


public class ContactActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        ListView list = (ListView) findViewById(R.id.contact_list);
        ContentResolver cr = getContentResolver();
        Cursor c = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                new String[]{ContactsContract.Contacts._ID, ContactsContract.Contacts.DISPLAY_NAME, ContactsContract.CommonDataKinds.Phone.NUMBER, ContactsContract.Contacts.PHOTO_THUMBNAIL_URI},
                null, null, null);
        String[] from = {ContactsContract.Contacts.DISPLAY_NAME, ContactsContract.CommonDataKinds.Phone.NUMBER, ContactsContract.Contacts.PHOTO_THUMBNAIL_URI};
        int[] to = {R.id.row_name, R.id.row_number, R.id.row_image};
        ContactAdapter adapter = new ContactAdapter(this, R.layout.contact_row, c, from, to, 1);
        list.setAdapter(adapter);
    }

    class ContactAdapter extends SimpleCursorAdapter{

        public ContactAdapter(Context context, int layout, Cursor c, String[] from, int[] to, int flags) {
            super(context, layout, c, from, to, flags);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView==null){
                Cursor c = getCursor();
                c.moveToPosition(position);
                View v = getLayoutInflater().inflate(R.layout.contact_row, null);
                TextView tvName = (TextView) v.findViewById(R.id.row_name);
                TextView tvNumber = (TextView) v.findViewById(R.id.row_number);
                ImageView ivPhoto = (ImageView) v.findViewById(R.id.row_image);
                tvName.setText(c.getString(c.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME)));
                tvNumber.setText(c.getString(c.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)));
                Uri photoUri = Uri.parse(c.getString(c.getColumnIndex(ContactsContract.Contacts.PHOTO_THUMBNAIL_URI)));
                ivPhoto.setImageURI(photoUri);
                convertView = v;
            }
            return convertView;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_contact, menu);
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
