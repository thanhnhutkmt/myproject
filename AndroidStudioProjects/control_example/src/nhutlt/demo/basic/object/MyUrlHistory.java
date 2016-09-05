package nhutlt.demo.basic.object;

import java.util.Stack;

public class MyUrlHistory {
	private Stack<String> mUrlHistory;
	private int mCurrentElement;
	private boolean mForward, mBack;
	
	public MyUrlHistory() {
		mUrlHistory = new Stack<String>();
		mCurrentElement = -1;
		mForward = false;
		mBack = false;
	}

	public boolean add(String in) {
		if (mCurrentElement == mUrlHistory.size() - 1) {
			mUrlHistory.add(in);
			mCurrentElement++;
		} else {
			mUrlHistory.add(in);
			mCurrentElement = mUrlHistory.size() - 1;
		}
		if (mCurrentElement > 0) {
			mBack = true;
		}
		return true;
	}
	
	public String getBack() {
		if (mCurrentElement > -1) {
			mCurrentElement--;
			if (mCurrentElement == -1) {
				mCurrentElement = 0;
				mBack = false;
			}
			if (mCurrentElement < mUrlHistory.size() - 3) {
				mForward = true;
			}
			if (mCurrentElement == 0) {
				mBack = false;
			}
			return mUrlHistory.get(mCurrentElement);
		} else {
			return null;
		}
	}
	
	public String getAt(int position) {
		if (mUrlHistory.size() > position) {
			return mUrlHistory.get(position);
		} else {
			return null;
		}
	}
	
	public String getForward() {
		if (mCurrentElement < mUrlHistory.size() - 2) {
			mCurrentElement += 2;
			if (mCurrentElement > 0) {
				mBack = true; ;
			}
			return mUrlHistory.get(mCurrentElement);
		} else {
			return null;
		}
	}
	
	public String getLastOut() {
		if (mCurrentElement < mUrlHistory.size() - 1) {
			return mUrlHistory.get(mCurrentElement + 1);
		} else {
			return null;
		}
	}
	
	public boolean getForwardStatus() {
		return mForward;
	}
	
	public boolean getBackStatus() {
		return mBack;
	}
}
