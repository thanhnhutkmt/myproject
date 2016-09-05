package nhutlt.myui;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class TableLayoutActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        TableLayout table = new TableLayout(this);
        table.setShrinkAllColumns(true);
        table.setStretchAllColumns(true); 
        
        String[] rowLabelS = {"", "Data", "Data1", "Data2"};
        String[] rowProduct1S = {"Product1", "100", "PT", "OK"};
        String[] rowProduct2S = {"Product2", "200", "PT", "OK"};
        
        TableRow rowTitle = createRowTitle("Product info", 4);
        TableRow rowLabel = createRow(rowLabelS);
        TableRow rowProduct1 = createRow(rowProduct1S);
        TableRow rowProduct2 = createRow(rowProduct2S);
                     
        table.addView(rowTitle);
        table.addView(rowLabel);
        table.addView(rowProduct1);
        table.addView(rowProduct2);
        
        setContentView(table);
    }
    
    public TableRow createRow(String[] data){
        int i = data.length;
        TableRow row = new TableRow(this);
        TextView[] cell = new TextView[i];
        TableRow.LayoutParams param = new TableRow.LayoutParams();
        for(int j = 0; j < i; j++){
            cell[j] = new TextView(this);
            cell[j].setText(data[j]);
            cell[j].setGravity(1);//Gravity.CENTER_HORIZONTAL = 1
            row.addView(cell[j], param);
        }
        return row;
    }
    
    public TableRow createRowTitle(String Title, int column){
        TextView title = new TextView(this);
        title.setText(Title);
        title.setGravity(1);
        
        TableRow.LayoutParams paramTitle = new TableRow.LayoutParams();
        paramTitle.span = column;
        
        TableRow row = new TableRow(this);
        row.addView(title, paramTitle);
        return row;
    }
}
