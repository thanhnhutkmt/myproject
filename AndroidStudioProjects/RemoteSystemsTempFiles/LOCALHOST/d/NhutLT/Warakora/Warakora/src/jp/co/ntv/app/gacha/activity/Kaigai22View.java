package jp.co.ntv.app.gacha.activity;

import java.util.ArrayList;

import jp.co.ntv.app.gacha.R;
import jp.co.ntv.app.gacha.adapter.MemberListAdapter;
import jp.co.ntv.app.gacha.model.KaigaiDetail.ChildItem;
import jp.co.ntv.app.gacha.model.KaigaiInfo;
import jp.co.ntv.app.gacha.net.LoadBitmapTaskUtils;
import android.content.Context;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class Kaigai22View extends LinearLayout{
    private KaigaiInfo mInfo = null;
    private ArrayList<ChildItem> mMembers = new ArrayList<ChildItem>();
    private Context mContext;
    private LoadBitmapTaskUtils mLoaderTask;
    public Kaigai22View(Context context) {
        super(context);
        mContext = context;
        __init();
    }
    
    public Kaigai22View(Context context, KaigaiInfo info, ArrayList<ChildItem> members, LoadBitmapTaskUtils loaderTask) {
        super(context);
        mContext = context;
        resetData(info, members, loaderTask);
    }
    
    public void resetData(KaigaiInfo info, ArrayList<ChildItem> members,  LoadBitmapTaskUtils loaderTask){
        mInfo = info;
        mMembers = members;
        __init();
    }
    
    private void __init() {
        removeAllViewsInLayout();
        LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        LinearLayout layout = (LinearLayout) layoutInflater.inflate(R.layout.kagai_22, null);
        addView(layout);
        // kaigai name, countries
        {
            if(mInfo != null) {
                TextView name = (TextView) findViewById(R.id.kagai20_tv_title_0102);
                name.setText(mInfo.getBranchName());
                TextView countries = (TextView) findViewById(R.id.kagai22_tv_title_0202);
                countries.setText(mInfo.getCountries()); 
            }
        }
        // kaigai members
        {
           if(mMembers != null){
               ListView lstMembers = (ListView) findViewById(R.id.kaigai22_lv_member);
               MemberListAdapter mMemberAdapter = new MemberListAdapter(mContext, mMembers, mLoaderTask);
               lstMembers.setAdapter(mMemberAdapter);
           }
        }
    }
    
}
