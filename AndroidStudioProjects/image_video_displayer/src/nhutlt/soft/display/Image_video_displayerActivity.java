package nhutlt.soft.display;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

public class Image_video_displayerActivity extends Activity {
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        GridView diplayer = (GridView) findViewById(R.id.gridview);
        ImageAdapter ia = new ImageAdapter(this);
        diplayer.setAdapter(ia);

        diplayer.setOnItemClickListener(new OnItemClickListener() {
        	@Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                Toast.makeText(Image_video_displayerActivity.this, "" + position, Toast.LENGTH_SHORT).show();
            }
        });
    }
    
    public class ImageAdapter extends BaseAdapter {
        private Context mContext;
        // references to our images
        private Bitmap[] mThumbnail;
        
        public ImageAdapter(Context context) {
            mContext = context;
            mThumbnail = loadImage();
        }

        public Object getItem(int position) {
            return null;
        }

        public long getItemId(int position) {
            return 0;
        }

        // create a new ImageView for each item referenced by the Adapter
        public View getView(int position, View convertView, ViewGroup parent) {
            ImageView imageView;
            if (convertView == null) {  // if it's not recycled, initialize some attributes
                imageView = new ImageView(mContext);
                imageView.setLayoutParams(new GridView.LayoutParams(96, 96));
                //imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                imageView.setPadding(0, 0, 0, 0);
            } else {
                imageView = (ImageView) convertView;
            }

            imageView.setImageBitmap(mThumbnail[position]);
            return imageView;
        }
        
        private Bitmap[] loadImage() {
            final String[] columns = { MediaStore.Images.Media.DATA,
                    MediaStore.Images.Media._ID };
            final String orderBy = MediaStore.Images.Media._ID;
            final String whereClause = MediaStore.Images.Media.BUCKET_DISPLAY_NAME
                    + " = ?";  // bucket name means the name of the container of the images
            final String[] imageDirectories = {"Camera"};
            
            final String[] vColumns = {MediaStore.Video.Media.DATA,
                    MediaStore.Video.Media._ID};
            final String vSelectCondition = MediaStore.Video.Media.BUCKET_DISPLAY_NAME + "= ?";
            final String[] vSelectValue = {"Camera"};
            final String vOrderBy = MediaStore.Video.Media._ID;
            
            Cursor imagecursor = managedQuery(
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI, columns,
                    whereClause, imageDirectories, orderBy);
            int numberOfImage = imagecursor.getCount();
            Cursor videocursor = managedQuery(
                    MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
                    vColumns, vSelectCondition, vSelectValue,
                    vOrderBy);
            int numberOfVideo = videocursor.getCount();
            
            Bitmap[] thumbNails = new Bitmap[numberOfImage + numberOfVideo];
            
            
            // get the index number of the ID column
            int imageColumnIndex = imagecursor
                    .getColumnIndex(MediaStore.Images.Media._ID);
            
            for (int id, i = 0; i < numberOfImage; i++) {
                // image cursor point to row number i
                imagecursor.moveToPosition(i);
                // get the index of the image at the row number i
                id = imagecursor.getInt(imageColumnIndex);
                /*
                // get the index number of the DATA column
                int dataColumnIndex = imagecursor
                        .getColumnIndex(MediaStore.Images.Media.DATA);
                        */
                // 
                thumbNails[i] = MediaStore.Images.Thumbnails.getThumbnail(
                        getApplicationContext().getContentResolver(), id,
                        MediaStore.Images.Thumbnails.MICRO_KIND, null);
            }
            imagecursor.close();
            int videoColumnIndex = videocursor.getColumnIndex(MediaStore.Video.Media._ID);
            for (int id, i = numberOfImage; i < numberOfImage + numberOfVideo; i++) {
                videocursor.moveToPosition(i - numberOfImage);
                id = videocursor.getInt(videoColumnIndex);
                thumbNails[i] = MediaStore.Video.Thumbnails.getThumbnail(
                        getApplicationContext().getContentResolver(), id, 
                        MediaStore.Video.Thumbnails.MICRO_KIND, null);
            }
            videocursor.close();
            return thumbNails;
        }

		@Override
		public int getCount() {			
			return mThumbnail.length;
		}
        
    }    
}