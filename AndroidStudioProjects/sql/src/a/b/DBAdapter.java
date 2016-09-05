package a.b;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.util.Log;

public class DBAdapter {

	public static final String TAG ="DBAdapter";
	
	public static final String KEY_ID = "_id";
	public static final String KEY_NAME = "name";
	
	private DatabaseHelper mDbHelper;
	private SQLiteDatabase mDB;
	
	private static final String DATABASE_CREATE = "create table users (_id integer primary key autoincrement, "
		+ "name text not null);";
	private static final String DATABASE_NAME = "Database_Demo";
	private static final String DATABASE_TABLE = "users";
	private static final int DATABASE_VERSION = 2;
	
	private final Context mContext;
	
	private static class DatabaseHelper extends SQLiteOpenHelper{

		public DatabaseHelper(Context context, String name,
				CursorFactory factory, int version) {
			super(context, name, factory, version);
			// TODO Auto-generated constructor stub
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			// TODO Auto-generated method stub
			db.execSQL(DATABASE_CREATE);
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			// TODO Auto-generated method stub
			Log.i(TAG, "Upgrading DB");
			db.execSQL("DROP TABLE IF EXISTS users");
			onCreate(db);
		}
	}
	
	public DBAdapter(Context ctx){
		this.mContext = ctx;
	}
	
	public DBAdapter open()
	{
		mDbHelper = new DatabaseHelper(mContext, DATABASE_NAME, null, DATABASE_VERSION);
		mDB = mDbHelper.getWritableDatabase();
		return this;
	}
	
	public void close(){
		mDbHelper.close();
	}
	
	public long createUser(String name){
		ContentValues inititalValues = new ContentValues();
		inititalValues.put(KEY_NAME, name);
		return mDB.insert(DATABASE_TABLE, null, inititalValues);
	}
	
	public boolean deleteUser(long rowId)
	{
		return mDB.delete(DATABASE_TABLE, KEY_ID + "=" + rowId, null) >0;
	}
	public Cursor getAllUsers(){
		return mDB.query(DATABASE_TABLE, new String[] {KEY_ID, KEY_NAME}, null, null, null, null, null);
	}
}
