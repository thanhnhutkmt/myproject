package software.nhut.personalutilitiesforlife;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.ClipData;
import android.content.ClipDescription;
import android.content.ClipboardManager;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.text.InputType;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.model.LatLng;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import software.nhut.personalutilitiesforlife.constant.AppConstant;
import software.nhut.personalutilitiesforlife.data.CuocHen;
import util.MyDateTime;
import util.MyPhone;

public class ChiTietCuocHenActivity extends AppCompatActivity {
    TextView txtGioThuNgayThangNam_ChiTietCuocHen, txtDiaDiem_ChiTietCuocHen,
            txtThongDiep_ChiTietCuocHen, txtNguoiLienHe_ChiTietCuocHen, lastItemClicked;
    EditText txtTuaDe_ChiTietCuocHen, txtNoiDung_ChiTietCuocHen;
    ListView lvDiaDiem_ChiTietCuocHen, lvThongDiep_ChiTietCuocHen, lvGhiChu_ChiTietCuocHen;
    ArrayAdapter<String> adapterDiaDiem, adapterThongDiep, adapterGhiChu;
    List<String> listDiaDiem, listThongDiep, listGhiChu;
    CuocHen c;
    Intent i;
    Calendar calendar;
    SimpleDateFormat sdf;
    ClipboardManager clipboard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_cuoc_hen);

        addControls();
        addEvents();
    }

    private void addEvents() {
        txtGioThuNgayThangNam_ChiTietCuocHen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                xuLyGioThuNgayThang();
            }
        });

        txtNoiDung_ChiTietCuocHen.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                lastItemClicked = txtNoiDung_ChiTietCuocHen;
                return false;
            }
        });

        txtDiaDiem_ChiTietCuocHen.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                lastItemClicked = txtDiaDiem_ChiTietCuocHen;
                return false;
            }
        });

        txtThongDiep_ChiTietCuocHen.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                lastItemClicked = txtThongDiep_ChiTietCuocHen;
                return false;
            }
        });

        txtNguoiLienHe_ChiTietCuocHen.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                lastItemClicked = txtNguoiLienHe_ChiTietCuocHen;
                return false;
            }
        });

    }

    private void xuLyGioThuNgayThang() {
        DatePickerDialog.OnDateSetListener callBack1 = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, monthOfYear);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                txtGioThuNgayThangNam_ChiTietCuocHen.setText(sdf.format(calendar.getTime()));
            }
        };
        DatePickerDialog dpd = new DatePickerDialog(
                ChiTietCuocHenActivity.this,
                callBack1,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
        );
        dpd.show();
        TimePickerDialog.OnTimeSetListener callBack2 = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                calendar.set(Calendar.MINUTE, minute);
                txtGioThuNgayThangNam_ChiTietCuocHen.setText(sdf.format(calendar.getTime()));
            }
        };
        TimePickerDialog tpd = new TimePickerDialog(
                ChiTietCuocHenActivity.this,
                callBack2,
                calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE),
                true
        );
        tpd.show();
    }

    private void addControls() {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        txtGioThuNgayThangNam_ChiTietCuocHen = (TextView) findViewById(R.id.txtGioThuNgayThangNam_ChiTietCuocHen);
        txtTuaDe_ChiTietCuocHen = (EditText) findViewById(R.id.txtTuaDe_ChiTietCuocHen);
        txtNoiDung_ChiTietCuocHen = (EditText) findViewById(R.id.txtNoiDung_ChiTietCuocHen);
        txtDiaDiem_ChiTietCuocHen = (TextView) findViewById(R.id.txtDiaDiem_ChiTietCuocHen);
        txtThongDiep_ChiTietCuocHen = (TextView) findViewById(R.id.txtThongDiep_ChiTietCuocHen);
        txtNguoiLienHe_ChiTietCuocHen = (TextView) findViewById(R.id.txtNguoiLienHe_ChiTietCuocHen);
        lvDiaDiem_ChiTietCuocHen = (ListView) findViewById(R.id.lvDiaDiem_ChiTietCuocHen);
        lvThongDiep_ChiTietCuocHen = (ListView) findViewById(R.id.lvThongDiep_ChiTietCuocHen);
        lvGhiChu_ChiTietCuocHen = (ListView) findViewById(R.id.lvGhiChu_ChiTietCuocHen);

        calendar = Calendar.getInstance();
        sdf = new SimpleDateFormat(AppConstant.FULLTIMEFORMAT_WITHOUTNEWLINE);

        registerForContextMenu(txtNoiDung_ChiTietCuocHen);
        registerForContextMenu(txtDiaDiem_ChiTietCuocHen);
        registerForContextMenu(txtThongDiep_ChiTietCuocHen);
        registerForContextMenu(txtNguoiLienHe_ChiTietCuocHen);

        clipboard = (ClipboardManager) getSystemService(this.CLIPBOARD_SERVICE);
        c = null;
        i = getIntent();
        c = (CuocHen) i.getSerializableExtra(AppConstant.QUANLYCUOCHEN_CHUOIGUI_CUOCHEN);
        if (c != null) showCuocHen();
        else {
            listDiaDiem = new ArrayList<>();
            listThongDiep = new ArrayList<>();
            listGhiChu = new ArrayList<>();
        }
        adapterDiaDiem = new ArrayAdapter<String>(ChiTietCuocHenActivity.this, android.R.layout.simple_list_item_1, listDiaDiem);
        adapterThongDiep = new ArrayAdapter<String>(ChiTietCuocHenActivity.this, android.R.layout.simple_list_item_1, listThongDiep);
        adapterGhiChu = new ArrayAdapter<String>(ChiTietCuocHenActivity.this, android.R.layout.simple_list_item_1, listGhiChu);
        lvDiaDiem_ChiTietCuocHen.setAdapter(adapterDiaDiem);
        lvThongDiep_ChiTietCuocHen.setAdapter(adapterThongDiep);
        lvGhiChu_ChiTietCuocHen.setAdapter(adapterGhiChu);
        setListViewHeightBasedOnChildren(lvDiaDiem_ChiTietCuocHen);
        setListViewHeightBasedOnChildren(lvThongDiep_ChiTietCuocHen);
        setListViewHeightBasedOnChildren(lvGhiChu_ChiTietCuocHen);
    }

    private void showCuocHen() {
        txtGioThuNgayThangNam_ChiTietCuocHen.setText(c.getGioThuNgayThangNam());
        txtGioThuNgayThangNam_ChiTietCuocHen.setTextColor((c.isTat()) ? Color.RED : Color.BLACK);
        txtTuaDe_ChiTietCuocHen.setText(c.getTieuDe());
        txtNoiDung_ChiTietCuocHen.setText(c.getNoiDung());

        listDiaDiem = c.getListDiaDiem();
        listThongDiep = c.getListTinNhan();
        listGhiChu = c.getListNguoiLienHe();
    }

    @Override
    public void onBackPressed() {
        returnResult();
        super.onBackPressed();
    }

    private void returnResult() {
        String time = txtGioThuNgayThangNam_ChiTietCuocHen.getText().toString();
        if (time.length() > 0) {
            boolean tat = (txtGioThuNgayThangNam_ChiTietCuocHen.getCurrentTextColor() == Color.RED) ? true : false;
            c = new CuocHen(txtTuaDe_ChiTietCuocHen.getText().toString(),
                    txtNoiDung_ChiTietCuocHen.getText().toString(),
                    txtGioThuNgayThangNam_ChiTietCuocHen.getText().toString(),
                    listDiaDiem, listThongDiep, listGhiChu, tat);
        } else c = null;
        i.putExtra(AppConstant.CHITIETCUOCHEN_CHUOIGUI_CUOCHEN, c);
        setResult(AppConstant.CHITIETCUOCHEN_RESULTCODE, i);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_chitietcuochen, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.mnuXoa_chitietcuochen) {
            c = null;
            i.putExtra(AppConstant.CHITIETCUOCHEN_CHUOIGUI_CUOCHEN, c);
            setResult(AppConstant.CHITIETCUOCHEN_RESULTCODE, i);
            finish();
        } else if (id == R.id.mnuTatMo_chitietcuochen) {
            boolean tat = c.isTat();
            c.setTat(!tat);
            txtGioThuNgayThangNam_ChiTietCuocHen.setTextColor((c.isTat()) ? Color.RED : Color.BLACK);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menucontext_chitietcuochen, menu);
        MenuItem mnuLayTinNhan = menu.findItem(R.id.mnuLayTinNhan);
        MenuItem mnuLayTuDanhBa = menu.findItem(R.id.mnuLayTuDanhBa);
        MenuItem mnuNhapTay = menu.findItem(R.id.mnuNhapTay);
        MenuItem mnuLayTuGoogleMap = menu.findItem(R.id.mnuLayTuGoogleMap);
        MenuItem mnuPasteTuClipBoard = menu.findItem(R.id.mnuPasteTuClipBoard);
        if (lastItemClicked == txtNoiDung_ChiTietCuocHen) {
            mnuNhapTay.setVisible(false);
        } else if (lastItemClicked == txtDiaDiem_ChiTietCuocHen) {
            mnuLayTuDanhBa.setVisible(false);
        } else if (lastItemClicked == txtThongDiep_ChiTietCuocHen) {
            mnuLayTuDanhBa.setVisible(false);
            mnuLayTuGoogleMap.setVisible(false);
        } else if (lastItemClicked == txtNguoiLienHe_ChiTietCuocHen) {
            mnuLayTinNhan.setVisible(false);
            mnuLayTuGoogleMap.setVisible(false);
        }

        if (mnuPasteTuClipBoard.isVisible()) {
            // If the clipboard doesn't contain data, disable the paste menu item.
            // If it does contain data, decide if you can handle the data.
            if (!(clipboard.hasPrimaryClip())) {
                mnuPasteTuClipBoard.setEnabled(false);
            } else if (!(clipboard.getPrimaryClipDescription().hasMimeType(ClipDescription.MIMETYPE_TEXT_PLAIN))) {
                // This disables the paste menu item, since the clipboard has data but it is not plain text
                mnuPasteTuClipBoard.setEnabled(false);
            } else {
                // This enables the paste menu item, since the clipboard contains plain text.
                mnuPasteTuClipBoard.setEnabled(true);
            }
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        int i = item.getItemId();
        if (i == R.id.mnuLayTinNhan) {
            layTinNhan();
        } else if (i == R.id.mnuLayTuDanhBa) {
            layThongTinDanhBa();
        } else if (i == R.id.mnuNhapTay) {
            nhapTay();
        } else if (i == R.id.mnuPasteTuClipBoard) {
            layTuClipBoard();
        } else if (i == R.id.mnuLayTuGoogleMap) {
            layTuGoogleMap();
        }
        return super.onContextItemSelected(item);
    }

    private void layTuGoogleMap() {
//        PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
//        try {
//            startActivityForResult(builder.build(this), AppConstant.CHITIETCUOCHEN_OPENMAP_REQUESTCODE);
//        } catch (GooglePlayServicesRepairableException e) {
//            e.printStackTrace();
//        } catch (GooglePlayServicesNotAvailableException e) {
//            e.printStackTrace();
//        }
        Intent i = new Intent(this, BanDoActivity.class);
        startActivityForResult(i, AppConstant.CHITIETCUOCHEN_OPENMAP_REQUESTCODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == AppConstant.CHITIETCUOCHEN_OPENMAP_REQUESTCODE) {
            if (resultCode == AppConstant.CHITIETCUOCHEN_OPENMAP_RESULTCODE) {
                Place place = PlacePicker.getPlace(data, this);
                String info = place.getName() + " - " + place.getAddress()
                        + " - " + place.getPhoneNumber();
                inputData(info);
            } else {
                inputData(clipboard.getPrimaryClip().getItemAt(0).getText().toString());
            }
        }
    }

    private void layTinNhan() {
        List<String> listTinNhan = new ArrayList<>();
        String []arrayUri = {"content://sms/inbox", "content://sms/sent", "content://sms/draft"};
        for (String uri : arrayUri) {
            Cursor cur = getContentResolver().query(Uri.parse(uri), null, null, null, null);
            while (cur.moveToNext()) {
                listTinNhan.add(MyPhone.replacePhoneNumberWithContactName(cur.getString(cur.getColumnIndex("address")), getContentResolver()) + " - "
                    + MyDateTime.getDateString(cur.getString(cur.getColumnIndex("date")), AppConstant.FULLTIMEFORMAT_WITHOUTNEWLINE) + " - "
                    + cur.getString(cur.getColumnIndex("body")));
            }
            cur.close();
        }
        createAlertDialog(R.string.txtlistTinNhanInput_Title_ChiTietCuocHen, listTinNhan);
    }

    private void layThongTinDanhBa() {
        Uri uriContact = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
        Cursor cursor = getContentResolver().query(uriContact, null, null, null, null);
        List<String> listContact = new ArrayList<>();
        while (cursor.moveToNext()) {
            String a = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME))
                + " - " + cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            listContact.add(a);
        }
        cursor.close();
        createAlertDialog(R.string.txtlistDanhBaInput_Title_ChiTietCuocHen, listContact);
    }

    private void layTuClipBoard() {
        // Examines the item on the clipboard. If getText() does not return null, the clip item contains the
        // text. Assumes that this application can only handle one item at a time.
        ClipData.Item item = clipboard.getPrimaryClip().getItemAt(0);
        // Gets the clipboard as text.
        String pasteData = item.getText().toString();
        // If the string contains data, then the paste operation is done
        if (pasteData != null) inputData(pasteData);
        // If The clipboard does not contain text and it has a URI, ignore it.
    }

    private void nhapTay() {
        createAlertDialog(R.string.txtInputDialog_Title_ChiTietCuocHen, null);
    }

    private void inputData(String data) {
        if (lastItemClicked == txtNoiDung_ChiTietCuocHen) {
            String newContent = txtNoiDung_ChiTietCuocHen.getText().toString() + data;
            txtNoiDung_ChiTietCuocHen.setText(newContent);
        } else if (lastItemClicked == txtDiaDiem_ChiTietCuocHen) {
            listDiaDiem.add(data);
            adapterDiaDiem.notifyDataSetChanged();
            setListViewHeightBasedOnChildren(lvDiaDiem_ChiTietCuocHen);
        } else if (lastItemClicked == txtThongDiep_ChiTietCuocHen) {
            listThongDiep.add(data);
            adapterThongDiep.notifyDataSetChanged();
            setListViewHeightBasedOnChildren(lvThongDiep_ChiTietCuocHen);
        } else if (lastItemClicked == txtNguoiLienHe_ChiTietCuocHen) {
            listGhiChu.add(data);
            adapterGhiChu.notifyDataSetChanged();
            setListViewHeightBasedOnChildren(lvGhiChu_ChiTietCuocHen);
        }
    }

    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null || listAdapter.getCount() == 0) {
            // pre-condition
            return;
        }

        int totalHeight = listView.getPaddingTop() + listView.getPaddingBottom();
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            if (listItem instanceof ViewGroup) {
                listItem.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            }
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }

    private void createAlertDialog(int idTitle, final List<String> list) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(ChiTietCuocHenActivity.this);
        builder.setTitle(idTitle);
        if (list != null) {
            // Set up the listview to show listContact
            ListView lvTinNhan = new ListView(ChiTietCuocHenActivity.this);
            ArrayAdapter adapter = new ArrayAdapter(ChiTietCuocHenActivity.this, android.R.layout.simple_list_item_1, list);
            lvTinNhan.setAdapter(adapter);
            lvTinNhan.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                    inputData(list.get(position));
                    Toast.makeText(ChiTietCuocHenActivity.this, R.string.Toast_txtlistInput_ChiTietCuocHen, Toast.LENGTH_SHORT).show();
                    return false;
                }
            });
            builder.setView(lvTinNhan);
        } else {
            // Set up the input
            final EditText input = new EditText(ChiTietCuocHenActivity.this);
            // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
            input.setInputType(InputType.TYPE_CLASS_TEXT);// | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            builder.setView(input);

            // Set up the buttons
            builder.setPositiveButton(R.string.txtInput_NutLuu_ChiTietCuocHen, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    inputData(input.getText().toString());
                }
            });
        }
        builder.setNegativeButton(R.string.txtInput_NutHuy_ChiTietCuocHen, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();
    }
}
