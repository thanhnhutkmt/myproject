package nhutLT.soft.SQLite;

import java.util.ArrayList;
import nhutLT.soft.SQLite.DBAdapter;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.database.CharArrayBuffer;
import android.database.ContentObserver;
import android.database.Cursor;
import android.database.DataSetObserver;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MySQLiteActivity extends Activity {
    private Cursor receivedData;
    private DBAdapter dataBaseHandler; 
    public void showMessage(String title, String message){
        new AlertDialog.Builder(this)
        .setTitle(title)
        .setMessage(message)
        .setPositiveButton("YES", new OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
                //do stuff onclick of YES
                arg0.dismiss();
            }
        })
        .show();
    }
    
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
               
        final EditText etName = (EditText) findViewById(R.id.etName);
        final EditText etAge = (EditText) findViewById(R.id.etAge);
        Button addButton = (Button) findViewById(R.id.add);
        dataBaseHandler = new DBAdapter(this);        
        
        addButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub     
                ContentValues data = new ContentValues();
                //data.put("_id", 1);
                data.put("name", etName.getText().toString());
                data.put("age", Integer.parseInt(etAge.getText().toString()));
                dataBaseHandler.insertDB(data);

                receivedData = dataBaseHandler.queryDB();
                if(receivedData != null){
                    receivedData.moveToFirst();
                    String title = "Input Data";
                    String message = "ID : " + receivedData.getInt(0) + " Name : " + receivedData.getString(1) + " Age : " + receivedData.getInt(2);
                    showMessage(title, message);
                }
            }
        });
    }
}