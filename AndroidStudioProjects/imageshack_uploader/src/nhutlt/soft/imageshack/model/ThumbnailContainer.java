package nhutlt.soft.imageshack.model;

import android.graphics.Bitmap;

public class ThumbnailContainer {
	/** thumbnail of image */
	private Bitmap mThumbnail;
	/** type of bitmap : 0 - image 1 - video */
	private int mType;
	/** name of image or video */
	private String mName;
	/** selecting state of image or video */
	private boolean mSelected;
	
	public ThumbnailContainer() {
		mThumbnail = null;
		mName = null;
		mType = -1;
		select(false);
	}
	/** 
	 * Assign value for class 
	 * @param thumbnail : small image of image or video
	 *        type      : 0 - image 1 - video
	 *        name      : name of image or video
	 * @return boolean true - success, false - error
	 */
	public boolean addThumbNailInfo(Bitmap thumbnail, int type, String name) {
		if (thumbnail != null && (type == 0 || type == 1) && name != null) {
			mThumbnail = thumbnail;
			mType = type;
			mName = name;
			return true;
		} else {
			return false;
		}
	}
	
	public ThumbnailContainer getThumbNailInfo() {
		if (mThumbnail != null && (mType == 0 || mType == 1)
				&& mName != null) {
			return this;
		} else {
			return null;
		}
	}
	
	public Bitmap getThumbNail() {
		return mThumbnail;
	}
	
	public int getType() {
		return mType;
	}
	
	public String getName() {
		return mName;
	}
	
	public boolean isSelected() {
		return mSelected;
	}
	
	public void select(boolean mSelected) {
		this.mSelected = mSelected;
	}
}
