package nhutlt.soft.imageshack;

import java.io.IOException;

import nhutlt.soft.imageshack.adapter.ImageAdapter;
import nhutlt.soft.imageshack.constant.Constants;
import nhutlt.soft.imageshack.model.ThumbnailContainer;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.CheckBox;
import android.widget.GridView;
import android.widget.Toast;

public class Imageshack_uploaderActivity extends Activity {
    private GridView mDiplayer;
    private ImageAdapter mImageAdapter;
    private Cursor mImageCursor, mVideoCursor;
    
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        mDiplayer = (GridView) findViewById(R.id.gridview);
        try {
			mImageAdapter = new ImageAdapter(this, loadImageVideo());
		} catch (IOException e) {
			e.printStackTrace();
			Log.v("NhutLT", e.getMessage());
		} 
        mDiplayer.setAdapter(mImageAdapter);

        mDiplayer.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                //Toast.makeText(Imageshack_uploaderActivity.this, "" + position, Toast.LENGTH_SHORT).show();
                mImageAdapter.setSelected(position, v);
                Intent showFullView = new Intent(getApplicationContext(), fullviewscreen.class);
                showFullView.putExtra("FileName", mImageAdapter.getName(position));
                showFullView.putExtra("Type", mImageAdapter.getType(position));
                startActivity(showFullView);
            }
        });
    }
    
    private ThumbnailContainer[] loadImageVideo() throws IOException{
        final String[] columns = { MediaStore.Images.Media.DATA,
                MediaStore.Images.Media._ID, MediaStore.Images.Media.DISPLAY_NAME};
        final String orderBy = MediaStore.Images.Media._ID;
        final String whereClause = MediaStore.Images.Media.BUCKET_DISPLAY_NAME
                + " = ?";  // bucket name means the name of the container of the images
        final String[] imageDirectories = {"Camera"};
        
        final String[] vColumns = {MediaStore.Video.Media.DATA,
                MediaStore.Video.Media._ID, MediaStore.Video.Media.DISPLAY_NAME};
        final String vSelectCondition = MediaStore.Video.Media.BUCKET_DISPLAY_NAME + "= ?";
        final String[] vSelectValue = {"Camera"};
        final String vOrderBy = MediaStore.Video.Media._ID;
        
        mImageCursor = managedQuery(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI, columns,
                whereClause, imageDirectories, orderBy);
        int numberOfImage = mImageCursor.getCount();
        mVideoCursor = managedQuery(
                MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
                vColumns, vSelectCondition, vSelectValue,
                vOrderBy);
        int numberOfVideo = mVideoCursor.getCount();
        
        ThumbnailContainer[] thumbNails = 
                new ThumbnailContainer[numberOfImage + numberOfVideo];
        
        // get the index number of the ID column
        int imageColumnIndex = mImageCursor
                .getColumnIndex(MediaStore.Images.Media._ID);
        // get the index number of the display name column
        int imageNameColumnIndex = mImageCursor
                .getColumnIndex(MediaStore.Images.Media.DISPLAY_NAME);

        for (int id, i = 0; i < numberOfImage; i++) {
        	mImageCursor.moveToPosition(i);
            id = mImageCursor.getInt(imageColumnIndex);
            thumbNails[i] = new ThumbnailContainer();
            if (!thumbNails[i].addThumbNailInfo(MediaStore.Images.Thumbnails.getThumbnail(
                    getApplicationContext().getContentResolver(), id,
                    MediaStore.Images.Thumbnails.MICRO_KIND, null), Constants.typeOfImage,
                    mImageCursor.getString(imageNameColumnIndex))) {
            	throw new IOException("Add image thumbnail error");
            }
        }
        //mImageCursor.close();
                    
        int videoColumnIndex = mVideoCursor
                .getColumnIndex(MediaStore.Video.Media._ID);
        int videoNameColumnIndex = mVideoCursor
                .getColumnIndex(MediaStore.Video.Media.DISPLAY_NAME);
        for (int id, i = numberOfImage; i < numberOfImage + numberOfVideo; i++) {
        	mVideoCursor.moveToPosition(i - numberOfImage);
            id = mVideoCursor.getInt(videoColumnIndex);
            thumbNails[i] = new ThumbnailContainer();
            if (!thumbNails[i].addThumbNailInfo(MediaStore.Video.Thumbnails.getThumbnail(
                    getApplicationContext().getContentResolver(), id, 
                    MediaStore.Video.Thumbnails.MICRO_KIND, null), Constants.typeOfVideo, 
                    mVideoCursor.getString(videoNameColumnIndex))) {
            	throw new IOException("Add video thumbnail error");
            }
        }
        
        //mVideoCursor.close();
        return thumbNails;
    }
}