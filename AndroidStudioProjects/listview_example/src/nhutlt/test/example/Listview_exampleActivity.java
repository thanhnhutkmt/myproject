package nhutlt.test.example;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class Listview_exampleActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        ListView lv = (ListView) findViewById(R.id.mylist);
        
        // Mang from chua ten cac cot tren 1 dong cua list
        String[] from = new String[]{"name", "age", "job"};
        // Mang to chua cac ten cac textview de dien vao noi dung phai tuong ung ve thu tu voi from
        int[] to = {R.id.name, R.id.age, R.id.job};        
        List<HashMap<String, String>> fillMaps = new ArrayList<HashMap<String, String>>();

        // Tao mang du lieu 2 chieu, trong do chieu thu nhat la ten tuong ung voi mang from, chieu thu hai la noi dung can dien vao
        // Moi lan add vao roi phai khoi tao lai de bien data tro toi vung nho moi
        // Luu y la khong duoc xoa vi fillMaps dang tro toi vung du lieu do bien data tro toi 
        HashMap<String, String> data = new HashMap<String, String>();
        data.put("name", "Minh");
        data.put("age", "23");
        data.put("job", "tester");
        fillMaps.add(data);
        data = new HashMap<String, String>();
        data.put("name", "My");
        data.put("age", "21");
        data.put("job", "tester");
        fillMaps.add(data);
        data = new HashMap<String, String>();
        data.put("name", "Tai");
        data.put("age", "25");
        data.put("job", "dev");
        fillMaps.add(data);

        SimpleAdapter listViewAdapter = new SimpleAdapter(this, fillMaps, R.layout.mylist, from, to);
        lv.setAdapter(listViewAdapter);
        lv.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {				
				
			}
		});
    }
}