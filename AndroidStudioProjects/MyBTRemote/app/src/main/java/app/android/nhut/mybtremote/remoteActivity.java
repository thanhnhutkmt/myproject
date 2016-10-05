package app.android.nhut.mybtremote;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.TextureView;
import android.view.View;
import android.widget.Button;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Set;
import java.util.UUID;

public class remoteActivity extends ActionBarActivity implements OnClickListener{
    private Button Logout, Lock, Hibernate, Turnoff, Vp, Vm, Mute,
            Switchapp, Closeapp, Confirm, LeftmouseBT, MiddlemouseBT,
            RightmouseBT, Leftmouse, Rightmouse, Upmouse, Downmouse,
            Scrollup, Scrolldown;
    private TextView tvLabel;
    private EditText tvAddress;
    private OutputStream sender;
    private Context context;
    public static final String APPTAG = "Bluetooth TV Remote";
    public static final int REQUEST_BT_ENABLE = 1;
    public static final UUID uuid = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");//new UUID("04c6093b00001000800000805f9b34fb", false);//UUID.fromString("04c6093b-0000-1000-8000-00805f9b34fb");//
    public static final byte LOCKSCREEN = 0;
    public static final byte HIBERNATE = 1;
    public static final byte LOGOFF = 2;
    public static final byte RESTART = 3;
    public static final byte SHUTDOWN = 4;
    public static final byte VOLUMEPLUS = 5;
    public static final byte VOLUMEMINUS = 6;
    public static final byte VOLUMEMUTE = 7;
    public static final byte SWITCHAPP = 8;
    public static final byte CLOSEAPP = 9;
    public static final byte CONFIRM = 10;
    public static final byte LEFTMOUSEBT = 11;
    public static final byte MIDDLEMOUSEBT = 12;
    public static final byte RIGHTMOUSEBT = 13;
    public static final byte LEFTMOUSE = 14;
    public static final byte RIGHTMOUSE = 15;
    public static final byte UPMOUSE = 16;
    public static final byte DOWNMOUSE = 17;
    public static final byte SCROOLUP = 18;
    public static final byte SCROLLDOWN = 19;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.remote_activity);
        Logout = (Button) findViewById(R.id.Logout);
        Lock = (Button) findViewById(R.id.Lock);
        Hibernate = (Button) findViewById(R.id.Hibernate);
        Turnoff = (Button) findViewById(R.id.Turnoff);
        Vp = (Button) findViewById(R.id.Vp);
        Vm = (Button) findViewById(R.id.Vm);
        Mute = (Button) findViewById(R.id.Mute);
        Switchapp = (Button) findViewById(R.id.Switchapp);
        Closeapp = (Button) findViewById(R.id.Closeapp);
        Confirm = (Button) findViewById(R.id.Enter);
        LeftmouseBT = (Button) findViewById(R.id.LeftmouseBT);
        MiddlemouseBT = (Button) findViewById(R.id.MiddlemouseBT);
        RightmouseBT = (Button) findViewById(R.id.RightmouseBT);
        Leftmouse = (Button) findViewById(R.id.Leftmouse);
        Rightmouse = (Button) findViewById(R.id.Rightmouse);
        Upmouse = (Button) findViewById(R.id.Upmouse);
        Downmouse = (Button) findViewById(R.id.Downmouse);
        Scrollup = (Button) findViewById(R.id.Scrollup);
        Scrolldown = (Button) findViewById(R.id.Scrolldown);
        tvLabel = (TextView) findViewById(R.id.TVlabel);
        tvAddress = (EditText) findViewById(R.id.tvaddress);

        Logout.setOnClickListener(this);
        Lock.setOnClickListener(this);
        Hibernate.setOnClickListener(this);
        Turnoff.setOnClickListener(this);
        Vp.setOnClickListener(this);
        Vm.setOnClickListener(this);
        Mute.setOnClickListener(this);
        Switchapp.setOnClickListener(this);
        Closeapp.setOnClickListener(this);
        Confirm.setOnClickListener(this);
        LeftmouseBT.setOnClickListener(this);
        MiddlemouseBT.setOnClickListener(this);
        RightmouseBT.setOnClickListener(this);
        Leftmouse.setOnClickListener(this);
        Rightmouse.setOnClickListener(this);
        Upmouse.setOnClickListener(this);
        Downmouse.setOnClickListener(this);
        Scrollup.setOnClickListener(this);
        Scrolldown.setOnClickListener(this);
        tvLabel.setOnClickListener(this);
        context = getApplicationContext();
    }

    private void connectTV() {
        Toast toast = Toast.makeText(context, "Connect TV " + uuid.toString(), Toast.LENGTH_SHORT);
        toast.show();
        if (sender != null) {
            try {
                sender.close();
            } catch (IOException e) {
                Log.println(Log.INFO, APPTAG, e.getMessage());
            }
        }
        BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mBluetoothAdapter == null) {
            // Device does not support Bluetooth
            Log.println(Log.INFO, APPTAG, "No bluetooth device found on this system.");
            return;
        } else if (!mBluetoothAdapter.isEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, REQUEST_BT_ENABLE);
            toast = Toast.makeText(context, "request BT", Toast.LENGTH_SHORT);
            toast.show();
        }

        Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();
        // If there are paired devices
        if ((pairedDevices != null) && (pairedDevices.size() > 0)) {
            // Loop through paired devices
            Intent discoverableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
            discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 60);
            startActivity(discoverableIntent);
            mBluetoothAdapter.cancelDiscovery();
            for (BluetoothDevice device : pairedDevices) {
                toast = Toast.makeText(context, "found device address " + device.getAddress(), Toast.LENGTH_SHORT);
                toast.show();
                if (device.getAddress().equals(tvAddress.getText().toString())) {
                    try {
                        toast = Toast.makeText(context, "match address " + device.getAddress(), Toast.LENGTH_SHORT);
                        toast.show();
                        BluetoothSocket btSocket = device.createRfcommSocketToServiceRecord(uuid);
                        toast = Toast.makeText(context, "Start to connect device " + device.getAddress(), Toast.LENGTH_SHORT);
                        toast.show();
                        btSocket.connect();
                        toast = Toast.makeText(context, "Connect sucessfully to device " + device.getAddress(), Toast.LENGTH_SHORT);
                        toast.show();
                        sender = btSocket.getOutputStream();
                        if (sender != null) {
                            tvLabel.setTextColor(0xff33b5e5);
                        }
                    } catch (IOException e) {
                        Log.println(Log.ERROR, APPTAG, "Build socket error");
                        Log.println(Log.ERROR, APPTAG, e.getMessage());
                        toast = Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG);
                        toast.show();
                    }
                    break;
                }
            }
        } else {
            Log.println(Log.DEBUG, APPTAG, "No paired device found");
            toast = Toast.makeText(context, "No paired device found", Toast.LENGTH_LONG);
            toast.show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_BT_ENABLE) {
            // it's my enable bluetooth request
            if (resultCode == Activity.RESULT_OK) {
                Log.println(Log.INFO, "Remote BT log", "BT enabled.");
            } else {
                Log.println(Log.INFO, "Remote BT log", "BT can not be enabled!");
            }
        }
    }

    @Override
    public void onClick(View v) {
        try {
            if (sender != null) {
                sender.write(getCommand(v.getId()));
            } else {
                connectTV();
            }
        } catch (IOException e) {
            Log.println(Log.ERROR, APPTAG, "Send command error");
            Log.println(Log.ERROR, APPTAG, e.getMessage());
            Toast toast = Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG);
            toast.show();
            sender = null;
            tvLabel.setTextColor(0xffcc0000);
        }
    }

    private byte getCommand(int button) {
        switch (button) {
            case R.id.Logout:       return LOGOFF;
            case R.id.Lock:         return LOCKSCREEN;
            case R.id.Hibernate:    return HIBERNATE;
            case R.id.Turnoff:      return SHUTDOWN;
            case R.id.Vp:           return VOLUMEPLUS;
            case R.id.Vm:           return VOLUMEMINUS;
            case R.id.Mute:         return VOLUMEMUTE;
            case R.id.Switchapp:    return SWITCHAPP;
            case R.id.Closeapp:     return CLOSEAPP;
            case R.id.Enter:        return CONFIRM;
            case R.id.LeftmouseBT:  return LEFTMOUSEBT;
            case R.id.MiddlemouseBT:return MIDDLEMOUSEBT;
            case R.id.RightmouseBT: return RIGHTMOUSEBT;
            case R.id.Leftmouse:    return LEFTMOUSE;
            case R.id.Rightmouse:   return RIGHTMOUSE;
            case R.id.Upmouse:      return UPMOUSE;
            case R.id.Downmouse:    return DOWNMOUSE;
            case R.id.Scrollup:     return SCROOLUP;
            case R.id.Scrolldown:   return SCROLLDOWN;
            default:                return (byte)255;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_remote, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
