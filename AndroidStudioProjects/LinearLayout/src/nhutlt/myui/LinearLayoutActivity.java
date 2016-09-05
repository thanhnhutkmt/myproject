package nhutlt.myui;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

public class LinearLayoutActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.main);

        LinearLayout mylayout = new LinearLayout(this);
        mylayout.setOrientation(LinearLayout.VERTICAL);
        mylayout.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
        LayoutParams textViewLayoutParam = new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
        
        TextView colorRed = new TextView(this);
        colorRed.setText("RED");
        colorRed.setTextColor(Color.BLACK);
        colorRed.setBackgroundColor(Color.RED);
        colorRed.setLayoutParams(textViewLayoutParam);
        
        TextView colorGreen = new TextView(this);
        colorGreen.setText("GREEN");
        colorGreen.setTextColor(Color.BLACK);
        colorGreen.setBackgroundColor(Color.GREEN);
        colorGreen.setLayoutParams(textViewLayoutParam);
        
        TextView colorBlue = new TextView(this);
        colorBlue.setText("BLUE");
        colorBlue.setTextColor(Color.BLACK);
        colorBlue.setBackgroundColor(Color.BLUE);
        colorBlue.setLayoutParams(textViewLayoutParam);
        
        TextView colorPurple = new TextView(this);
        colorPurple.setText("PURPLE");
        colorPurple.setTextColor(Color.BLACK);
        colorPurple.setBackgroundColor(Color.MAGENTA);
        colorPurple.setLayoutParams(textViewLayoutParam);
        
        mylayout.addView(colorRed);
        mylayout.addView(colorGreen);
        mylayout.addView(colorBlue);
        mylayout.addView(colorPurple);
        
        setContentView(mylayout);
        
    }
}