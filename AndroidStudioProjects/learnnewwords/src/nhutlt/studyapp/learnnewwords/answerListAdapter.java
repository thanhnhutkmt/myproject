package nhutlt.studyapp.learnnewwords;

import java.util.List;

import nhutlt.studyapp.learnnewwords.R;



import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.os.Environment;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class answerListAdapter extends BaseAdapter {
	private Context context;
	private List<String> answerChoices;
	private int layout;
	private boolean[] selectItem;
//	private AssetManager amgr;
	
	public answerListAdapter(Context context, 
			int itemLayout, List<String> answerChoice, 
							boolean[] selectItem) {
		this.context = context;
		this.answerChoices = answerChoice;
		this.layout = itemLayout;
		if (selectItem == null || selectItem.length == 0) {
			this.selectItem = new boolean[answerChoices.size()];
		} else {
			this.selectItem = selectItem;
		}
	}

	/* (non-Javadoc)
	 * @see android.widget.ArrayAdapter#getView(int, android.view.View, android.view.ViewGroup)
	 */
	public View getView(int position, View convertView, ViewGroup parent) {
		final int pos = position;
		LayoutInflater inflater = (LayoutInflater) this.context
		        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View fileView;
			final TextView answer;
			final ImageView checkbox;
			
		if(convertView == null) {
			fileView = inflater.inflate(layout, null);
			answer = (TextView) fileView.findViewById(R.id.answer);
			checkbox = (ImageView) fileView.findViewById(R.id.checkbox);		
		} else {
			answer = (TextView) convertView.findViewById(R.id.answer);
			checkbox = (ImageView) convertView.findViewById(R.id.checkbox);
			fileView = (View) convertView;
		}
		
		String answerContent = answerChoices.get(position);
		Typeface font = null;
		if(answerContent.contains("[")) {
			 font = Typeface.createFromAsset(context.getAssets(), "PHTIB__.TTF");	
		} else {
			font = Typeface.createFromAsset(context.getAssets(), "ARIAL.TTF");
		}		
		answer.setTypeface(font);
		answer.setText(answerContent);		

		checkbox.setVisibility(ImageView.VISIBLE);
		checkbox.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				selectItem[pos] = !selectItem[pos];
				if (selectItem[pos]) {
					answer.setTextColor(context.getResources().getColor(R.color.selectcolor));
					checkbox.setImageDrawable(context.getResources().getDrawable(R.drawable.checkbox_check));
				} else {
					answer.setTextColor(context.getResources().getColor(R.color.black));
					checkbox.setImageDrawable(context.getResources().getDrawable(R.drawable.checkbox_uncheck));
				}
			}
		});
		
		if (selectItem[position]) {
			answer.setTextColor(context.getResources().getColor(R.color.selectcolor));
			checkbox.setImageDrawable(context.getResources().getDrawable(R.drawable.checkbox_check));
		} else {
			answer.setTextColor(context.getResources().getColor(R.color.black));
			checkbox.setImageDrawable(context.getResources().getDrawable(R.drawable.checkbox_uncheck));
		}
		return fileView;
	}

	public int getCount() {
		return answerChoices.size();
	}

	public Object getItem(int position) {
//		Log.v("NhutLT",	"listed postion " + position);
		return null;
	}

	public long getItemId(int position) {
//		Log.v("NhutLT",	"listed postion " + position);
		return 0;
	}
	
	public String[] getAnswer() {
		String answers[] = new String[3];
		int index = 0;
		int numberOfSelect = 0;
		while (index < selectItem.length) {
			if (selectItem[index]) {
				numberOfSelect++;
				if (numberOfSelect == 4) {
					return null;
				}
			}
			index++;
		}
		
		index = 0;
		for(int answer = 0; index < selectItem.length; index++) {
			if(selectItem[index]) {
				answers[answer] = answerChoices.get(index);
				answer++;
				if (answer == 3) {
					break;
				}
			}
		}
		for (index = 0; index < 3; index++) {
			if (answers[index] == null) {
				answers[index] = ""; 
			}
		}
		return answers;
	}
}
