package at.viet.Intent;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.View.OnClickListener;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
/**
 * @author nhutlt
 *
 */
public class input extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.input);
        final EditText numberA = (EditText) findViewById(R.id.txtNum1);
        final EditText numberB = (EditText) findViewById(R.id.txtNum2);
        final Button sendButton = (Button) findViewById(R.id.btnSend);
        
        OnClickListener sendButtonAction = new OnClickListener() {
            
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                String stringA = numberA.getText().toString();
                String stringB = numberB.getText().toString();
                int valueA, valueB;
                AlertDialog.Builder warning = new AlertDialog.Builder(input.this);
                warning.setTitle("Warning!!");
                warning.setMessage("Please input number only for A and B");
                warning.setPositiveButton("OK", new DialogInterface.OnClickListener() {                  
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub
                        
                    }
                });
                
                // test input value
                if ((stringA.equals(null)) || (stringB.equals(null))){
                    warning.show();
                }
                else
                {
                    valueA = Integer.parseInt(stringA);                
                    valueB = Integer.parseInt(stringB);
                    Bundle data = new Bundle();
                    data.putInt("valueA", valueA);
                    data.putInt("valueB", valueB);
                    Intent i = new Intent(input.this, result.class);
                    i.putExtras(data);
                    startActivity(i);
                    finish();
                }      
            }
        };
        sendButton.setOnClickListener(sendButtonAction);
    }
}