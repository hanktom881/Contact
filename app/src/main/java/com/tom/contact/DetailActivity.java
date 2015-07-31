package com.tom.contact;

import android.app.Activity;
import android.database.Cursor;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.widget.ImageView;


public class DetailActivity extends Activity implements GestureDetector.OnGestureListener{
    private ImageView image;
    private GestureDetector detector;
    private Cursor cursor;
    private int pos;
    private long imageId;
    private int rowCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        imageId = getIntent().getLongExtra("IMAGE_ID", -1);
        pos = getIntent().getIntExtra("POS", -1);
        findViews();
        image.setImageURI(
                Uri.withAppendedPath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, imageId + ""));
        detector = new GestureDetector(this, this);
        cursor = getContentResolver().query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI, null, null, null, null
        );
        cursor.moveToPosition(pos);
        rowCount = cursor.getCount();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return detector.onTouchEvent(event);
    }

    private void findViews() {
        image = (ImageView) findViewById(R.id.detail_image);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_detail, menu);
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
    public boolean onDown(MotionEvent e) {
        Log.d("GESTURE", "onDown");
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {
        Log.d("GESTURE", "onShowPress");
    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        Log.d("GESTURE", "onSingleTapUp");
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        Log.d("GESTURE", "onScroll");
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {
        Log.d("GESTURE", "onLongPress");

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        Log.d("GESTURE", "onFling");
//        Log.d("FLING", velocityX+"/"+velocityY);
        float distance = e2.getX()-e1.getX();
        Log.d("DISTNACE", distance+"");
        if (distance > 100 ) {
            // to the right, previous
            if (pos>0){
                cursor.moveToPrevious();
                pos--;
                imageId = cursor.getInt(cursor.getColumnIndex(MediaStore.Images.Media._ID));
                image.setImageURI(Uri.withAppendedPath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, imageId+""));
            }

        }else if (distance < -100){
            // to the left, next
            if (pos<rowCount-1){
                cursor.moveToNext();
                pos++;
                imageId = cursor.getInt(cursor.getColumnIndex(MediaStore.Images.Media._ID));
                image.setImageURI(Uri.withAppendedPath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, imageId+""));
            }
        }
        return false;
    }
}
