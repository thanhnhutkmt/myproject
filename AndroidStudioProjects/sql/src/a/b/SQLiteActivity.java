package a.b;

import android.app.ListActivity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.SimpleCursorAdapter;

public class SQLiteActivity extends ListActivity {
	
	private DBAdapter mDB;
	private Cursor mCursor;
	
    /** Called when the activity is first created. */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        mDB = new DBAdapter(this);
        mDB.open();
        mDB.createUser("Do Minh Thong");
        getData();
    }
    
    private void getData(){
    	mCursor = mDB.getAllUsers();
    	startManagingCursor(mCursor);
    	String[] from = new String[]{DBAdapter.KEY_NAME};
    	int[] to = new int[] {R.id.text1};
    	SimpleCursorAdapter users = new SimpleCursorAdapter(this, R.layout.users_row, mCursor, from, to);
    	setListAdapter(users);
    }
}