package nhutlt.demo.basic;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.Toast;

public class Control_exampleActivity extends Activity {
    private EditText inputText;
    private Button send;
    private ImageButton clear;
    private TextView label;
    private RadioGroup colorOption;
    private RadioButton red, green, blue;
    private CheckBox enableLabel;
    private ImageView image;
    private static final int CONFIRM = 99;
    private static final int AUTHOR = 9; 
    
    /* (non-Javadoc)
	 * @see android.app.Activity#onCreateOptionsMenu(android.view.Menu)
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater menuInflater = getMenuInflater();
		menuInflater.inflate(R.menu.menu, menu);
		return true;
	}
	
	/* (non-Javadoc)
	 * @see android.app.Activity#onOptionsItemSelected(android.view.MenuItem)
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId()) {
			case R.id.resume:
				return true;
			case R.id.exit_app:
				finish();
			case R.id.open_web:
				Intent intent = new Intent(this, web_displayer.class);
				startActivity(intent);
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
		
	}

	/* (non-Javadoc)
	 * @see android.app.Activity#setContentView(int)
	 */

	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mySetContentView(R.layout.mainland, R.layout.mainport);
        
        inputText = (EditText) findViewById(R.id.inputtext);
        send = (Button) findViewById(R.id.send);
        clear = (ImageButton) findViewById(R.id.clear);
        label = (TextView) findViewById(R.id.label);
        colorOption = (RadioGroup) findViewById(R.id.options);
        red = (RadioButton) findViewById(R.id.red);
        green = (RadioButton) findViewById(R.id.green);
        blue = (RadioButton) findViewById(R.id.blue);
        enableLabel = (CheckBox) findViewById(R.id.enable_label);
        image = (ImageView) findViewById(R.id.image);
        
        enableLabel.setChecked(true);
        label.setEnabled(true);
        inputText.setSelected(false);
        send.setSelected(true);
        send.setOnClickListener(new OnClickListener() {
            
           
            public void onClick(View v) {
	            if (!inputText.getText().toString().isEmpty()) {
	                if (enableLabel.isChecked()) {
	                    label.setText(inputText.getText());
	                	Toast.makeText(getApplicationContext(),
	                			"Send text successfully", Toast.LENGTH_SHORT).show();
	                } else {
	                    Toast.makeText(getApplicationContext(),
	                            "Label is locked", Toast.LENGTH_SHORT).show();
	                }
		        }
            }
        });
        
        clear.setOnClickListener(new OnClickListener() {
            
            public void onClick(View v) {
                if (!inputText.getText().toString().isEmpty()
                		|| !label.getText().toString().isEmpty()) {
	                        showDialog(CONFIRM);
	            }
        	}
        });

        colorOption.setOnCheckedChangeListener(new OnCheckedChangeListener() {

            
            public void onCheckedChanged(RadioGroup group, int checkedId) {
            	Toast.makeText(getBaseContext(), "radiogroup is click", Toast.LENGTH_SHORT);
                switch(checkedId) {
                    case R.id.red:
                        label.setTextColor(getResources().getColor(R.color.red));
                        break;
                    case R.id.blue:
                        label.setTextColor(getResources().getColor(R.color.blue));
                        break;
                    case R.id.green:
                        label.setTextColor(getResources().getColor(R.color.green));
                        break;
                    default:
                        
                        break;
                }
            }
        });
        
        enableLabel.setOnClickListener(new OnClickListener() {
            
            
            public void onClick(View v) {
                boolean enableLabel_status = !label.isEnabled();
                boolean label_status = !label.isEnabled();
                label.setEnabled(label_status);
                label.setText("");
                if (enableLabel_status) {
                	colorOption.performClick();
                }
                enableLabel.setChecked(enableLabel_status);
            }
        });
        
        image.setOnLongClickListener(new OnLongClickListener() {
			
			
			public boolean onLongClick(View v) {
				showDialog(AUTHOR);
				return true;
			}
		});
		
    }

	public void mySetContentView(int landscapelayoutResID, int portraitlayoutResID) {
		WindowManager wm = (WindowManager) getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
		if (wm != null) {
			int orientation = wm.getDefaultDisplay().getOrientation();
			if (orientation == 0) {
				super.setContentView(portraitlayoutResID);
			} else if (orientation == 1) {
				super.setContentView(landscapelayoutResID);
			}
		}
	}

    /* (non-Javadoc)
     * @see android.app.Activity#onCreateDialog(int)
     */
    @Override
    protected Dialog onCreateDialog(int id) {
        Dialog myDialog;
        switch(id) {
            case CONFIRM :
                AlertDialog.Builder adBuilder = new AlertDialog.Builder(this); 
                adBuilder
                .setTitle("Confirm")
                .setMessage("Do you want to clear the text?")                
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    
                    
                    public void onClick(DialogInterface dialog, int which) {
                        inputText.setText("");
                        label.setText("");
                        dialog.dismiss();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    
                    
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                
                myDialog = (Dialog) adBuilder.create();
                return myDialog;
            case AUTHOR :
                myDialog = new Dialog(this);
                myDialog.setTitle("About the author");
                myDialog.setContentView(R.layout.author);
                return myDialog;
            default:
                return null;
        }
    }
}