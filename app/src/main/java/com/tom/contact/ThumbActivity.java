package com.tom.contact;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.SimpleCursorAdapter;


public class ThumbActivity extends Activity implements AdapterView.OnItemClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thumb);
        GridView grid = (GridView) findViewById(R.id.grid);
        ContentResolver cr = getContentResolver();
        Cursor c = cr.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, null, null, null, null);
        while(c.moveToNext()){
            int id = c.getInt(c.getColumnIndex(MediaStore.Images.Media._ID));
            Log.d("CURSOR", id+"");
        }
//        Log.d("THUMB", c.getCount()+"");
        String[] from = {MediaStore.Images.Media.DATA, MediaStore.Images.Media.DISPLAY_NAME};
        int[] to = {R.id.thumb_image, R.id.thumb_text};
        SimpleCursorAdapter adapter = new SimpleCursorAdapter(this, R.layout.thumbnail, c, from, to, 1);
        grid.setAdapter(adapter);
        grid.setOnItemClickListener(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_thumb, menu);
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

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Log.d("ITEM", position+"/"+id);
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra("IMAGE_ID", id);
        intent.putExtra("POS", position);
        startActivity(intent);

    }
}
