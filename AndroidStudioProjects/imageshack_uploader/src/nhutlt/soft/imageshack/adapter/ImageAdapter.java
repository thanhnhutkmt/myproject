package nhutlt.soft.imageshack.adapter;

import javax.xml.datatype.Duration;

import nhutlt.soft.imageshack.R;
import nhutlt.soft.imageshack.model.ThumbnailContainer;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

public class ImageAdapter extends BaseAdapter{
    private Context mContext;
    // references to our images
    private ThumbnailContainer[] mThumbnail;

    public ImageAdapter(Context context, ThumbnailContainer[] thumbNail) {
        if (context != null && thumbNail != null) {
            this.mContext = context;
            this.mThumbnail = thumbNail;
        }
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View gridViewContainer;

        if (convertView == null) { // if it's not recycled, initialize some
                                    // attributes
            gridViewContainer = new View(mContext);
            gridViewContainer = inflater.inflate(R.layout.item, null);
//                icon.setLayoutParams(new FrameLayout.LayoutParams(
//                        LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT,
//                        Gravity.BOTTOM | Gravity.RIGHT));
        }
        else {
            gridViewContainer = (View) convertView;
        }
        
        ImageView imageView = (ImageView) gridViewContainer
                .findViewById(R.id.grid_item_image);
        ImageView icon = (ImageView) gridViewContainer
                .findViewById(R.id.grid_item_ico);
        ImageView videoIcon = (ImageView) gridViewContainer
                .findViewById(R.id.grid_item_videoico);

        if (mThumbnail[position].getType() == 0) {
        	videoIcon.setVisibility(ImageView.GONE);
        } else {
        	videoIcon.setVisibility(ImageView.VISIBLE);
        }
        if (mThumbnail[position].isSelected()) {
        	icon.setImageResource(R.drawable.checkbox_full);
        } else {
            icon.setImageResource(R.drawable.checkbox_empty);
        }
        imageView.setImageBitmap(mThumbnail[position].getThumbNail());
        
        return gridViewContainer;
    }

    @Override
    public int getCount() {
        return mThumbnail.length;
    }
    
    public void setSelected(int position, View clickedItem) {
    	mThumbnail[position].select(!mThumbnail[position].isSelected());
    	ImageView icon = (ImageView) clickedItem.findViewById(R.id.grid_item_ico);
        if (mThumbnail[position].isSelected()) {
        	icon.setImageResource(R.drawable.checkbox_full);
        } else {
            icon.setImageResource(R.drawable.checkbox_empty);
        }
    }
    
    public String getName(int position) {
    	return mThumbnail[position].getName();
    }
    
    public int getType(int position) {
    	return mThumbnail[position].getType();
    }
}
