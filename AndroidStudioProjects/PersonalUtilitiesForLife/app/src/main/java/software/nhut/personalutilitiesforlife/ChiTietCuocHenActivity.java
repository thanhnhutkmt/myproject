package software.nhut.personalutilitiesforlife;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.ClipData;
import android.content.ClipDescription;
import android.content.ClipboardManager;
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
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.ContextMenu;
import android.view.Gravity;
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
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import software.nhut.personalutilitiesforlife.constant.AppConstant;
import software.nhut.personalutilitiesforlife.data.CuocHen;
import software.nhut.personalutilitiesforlife.data.TinNhanDanhBaCuocGoi;
import util.MyClipboard;
import util.MyDateTime;
import util.MyDialog;
import util.MyPhone;
import util.MyStringFormater;

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
    private boolean stop;

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
        lvDiaDiem_ChiTietCuocHen.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                listDiaDiem.remove(position);
                adapterDiaDiem.notifyDataSetChanged();
                MyDialog.scaleListView(lvDiaDiem_ChiTietCuocHen);
                return false;
            }
        });
        lvGhiChu_ChiTietCuocHen.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                listGhiChu.remove(position);
                adapterGhiChu.notifyDataSetChanged();
                MyDialog.scaleListView(lvGhiChu_ChiTietCuocHen);
                return false;
            }
        });
        lvThongDiep_ChiTietCuocHen.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                listThongDiep.remove(position);
                adapterThongDiep.notifyDataSetChanged();
                MyDialog.scaleListView(lvThongDiep_ChiTietCuocHen);
                return false;
            }
        });
        lvDiaDiem_ChiTietCuocHen.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MyClipboard.sendTextToClipboard(ChiTietCuocHenActivity.this, listDiaDiem.get(position));
            }
        });
        lvGhiChu_ChiTietCuocHen.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MyClipboard.sendTextToClipboard(ChiTietCuocHenActivity.this, listGhiChu.get(position));
            }
        });
        lvThongDiep_ChiTietCuocHen.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MyClipboard.sendTextToClipboard(ChiTietCuocHenActivity.this, listThongDiep.get(position));
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
//                txtGioThuNgayThangNam_ChiTietCuocHen.setText(sdf.format(calendar.getTime()));
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

    private void setAlarm(Date d, String msg) {
        int alarmID = (int)(Math.random() * 10000000);
        cancelAlarm();
        while(!c.setAlarmID(this, alarmID));
        AlarmManager alarmMgr = (AlarmManager) getSystemService(ALARM_SERVICE);
        Intent intent = new Intent(this, NotificationAlarmReceiver.class);
        intent.putExtra("appointmentcontent", msg);
        PendingIntent alarmIntent = PendingIntent.getBroadcast(this, alarmID, intent, 0);
        Intent intentRemove = new Intent(this, NotificationRemoveAlarmReceiver.class);
        intentRemove.putExtra("alarmID", alarmID);
        PendingIntent alarmRemoveIntent = PendingIntent.getBroadcast(this, alarmID, intentRemove, 0);
        // setRepeating() lets you specify a precise custom interval--in this case, 5 minutes.
        if (calendar.getTimeInMillis() > System.currentTimeMillis()) {
            alarmMgr.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis() - 1000 * 60 * 5 * 3, 1000 * 60 * 5, alarmIntent);
            alarmMgr.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis() + 1000 * 60 * 5 * 4, alarmRemoveIntent);
        }
    }

    private void cancelAlarm() {
        Intent intent = new Intent(this, NotificationAlarmReceiver.class);
        ((AlarmManager) getSystemService(ALARM_SERVICE)).cancel(PendingIntent.getBroadcast(this, c.getAlarmID(), intent, PendingIntent.FLAG_NO_CREATE));
        Intent intentRemove = new Intent(this, NotificationRemoveAlarmReceiver.class);
        ((AlarmManager) getSystemService(ALARM_SERVICE)).cancel(PendingIntent.getBroadcast(this, c.getAlarmID(), intentRemove, PendingIntent.FLAG_NO_CREATE));
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
        if (c != null) {
            MyDialog.scaleListViewAtFirst(lvDiaDiem_ChiTietCuocHen);
            MyDialog.scaleListViewAtFirst(lvThongDiep_ChiTietCuocHen);
            MyDialog.scaleListViewAtFirst(lvGhiChu_ChiTietCuocHen);
        }
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
    }

    private void returnResult() {
        String time = txtGioThuNgayThangNam_ChiTietCuocHen.getText().toString();
        String title = txtTuaDe_ChiTietCuocHen.getText().toString().trim();
        if (time.length() > 0 && title.length() > 0) {
            boolean tat = (txtGioThuNgayThangNam_ChiTietCuocHen.getCurrentTextColor() == Color.RED) ? true : false;
            c = new CuocHen(txtTuaDe_ChiTietCuocHen.getText().toString(),
                    txtNoiDung_ChiTietCuocHen.getText().toString(),
                    txtGioThuNgayThangNam_ChiTietCuocHen.getText().toString(),
                    listDiaDiem, listThongDiep, listGhiChu, tat);
            setAlarm(calendar.getTime(), title);
            i.putExtra(AppConstant.CHITIETCUOCHEN_CHUOIGUI_CUOCHEN, c);
            setResult(AppConstant.CHITIETCUOCHEN_RESULTCODE, i);
            finish();
        } else {
            MyDialog.showYesNoDiaLog(this, R.string.AlertDialog_title_Chitietcuochen_confirmDialg,
                    R.string.AlertDialog_body_Chitietcuochen_confirmDialg, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    c = null;
                    setResult(AppConstant.CHITIETCUOCHEN_RESULTCODE, i);
                    finish();
                }
            }, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
        }
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
            mnuPasteTuClipBoard.setEnabled(MyClipboard.hasTextInClipboard(ChiTietCuocHenActivity.this));
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        int i = item.getItemId();
        if (i == R.id.mnuLayTinNhan) {
            createAlertDialog(R.string.txtlistTinNhanInput_Title_ChiTietCuocHen, MyPhone.getAllMessages(ChiTietCuocHenActivity.this));
        } else if (i == R.id.mnuLayTuDanhBa) {
            createAlertDialog(R.string.txtlistDanhBaInput_Title_ChiTietCuocHen, MyPhone.getAllContacts(ChiTietCuocHenActivity.this));
        } else if (i == R.id.mnuNhapTay) {
            createAlertDialog(R.string.txtInputDialog_Title_ChiTietCuocHen, null);
        } else if (i == R.id.mnuPasteTuClipBoard) {
            inputData(MyClipboard.getTextFromClipboard(ChiTietCuocHenActivity.this));
        } else if (i == R.id.mnuLayTuGoogleMap) {
            layTuGoogleMap();
        }
        return super.onContextItemSelected(item);
    }

    private void layTuGoogleMap() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.AlertDialog_Title_ChiTietCuocHen_GoogleMapAPIGooglePlaceAPI);
        TextView txtContent = new TextView(this);
        txtContent.setText(R.string.AlertDialog_Content_ChiTietCuocHen_GoogleMapAPIGooglePlaceAPI);
        ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        txtContent.setGravity(Gravity.CENTER_VERTICAL + Gravity.LEFT);
        txtContent.setLayoutParams(lp);
        txtContent.setPadding(20, 20, 20, 20);
        builder.setView(txtContent);
        builder.setPositiveButton(R.string.AlertDialog_button_ChiTietCuocHen_GooglePlacesAPI,
                new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
                try {
                    startActivityForResult(builder.build(ChiTietCuocHenActivity.this),
                            AppConstant.CHITIETCUOCHEN_OPENMAP_REQUESTCODE);
                } catch (GooglePlayServicesRepairableException e) {
                    e.printStackTrace();
                } catch (GooglePlayServicesNotAvailableException e) {
                    e.printStackTrace();
                }
            }
        });
        builder.setNegativeButton(R.string.AlertDialog_button_ChiTietCuocHen_GoogleMapAPI,
                new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent i = new Intent(ChiTietCuocHenActivity.this, BanDoActivity.class);
                startActivityForResult(i, AppConstant.CHITIETCUOCHEN_OPENMAP_REQUESTCODE);
            }
        });
        builder.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == AppConstant.CHITIETCUOCHEN_OPENMAP_REQUESTCODE) {
            if (resultCode == RESULT_OK) {
                Place place = PlacePicker.getPlace(data, this);
                String info = place.getName() + " - " + place.getAddress()
                        + " - " + place.getPhoneNumber();
                inputData(info);
            } else {
                if (MyClipboard.hasTextInClipboard(ChiTietCuocHenActivity.this))
                    inputData(MyClipboard.getTextFromClipboard(ChiTietCuocHenActivity.this));
            }
        }
    }

    private void inputData(String data) {
        if (data == null || data.trim().length() == 0) return;
        else if (lastItemClicked == txtNoiDung_ChiTietCuocHen) {
            String newContent = txtNoiDung_ChiTietCuocHen.getText().toString() + data;
            txtNoiDung_ChiTietCuocHen.setText(newContent);
        } else if (lastItemClicked == txtDiaDiem_ChiTietCuocHen) {
            listDiaDiem.add(data);
            adapterDiaDiem.notifyDataSetChanged();
            MyDialog.scaleListView(lvDiaDiem_ChiTietCuocHen);
        } else if (lastItemClicked == txtThongDiep_ChiTietCuocHen) {
            listThongDiep.add(data);
            adapterThongDiep.notifyDataSetChanged();
            MyDialog.scaleListView(lvThongDiep_ChiTietCuocHen);
        } else if (lastItemClicked == txtNguoiLienHe_ChiTietCuocHen) {
            listGhiChu.add(data);
            adapterGhiChu.notifyDataSetChanged();
            MyDialog.scaleListView(lvGhiChu_ChiTietCuocHen);
        }
    }

//    public static void setListViewHeightBasedOnChildren(ListView listView) {
//        ListAdapter listAdapter = listView.getAdapter();
//        if (listAdapter == null || listAdapter.getCount() == 0) {
//            // pre-condition
//            return;
//        }
//
//        int totalHeight = listView.getPaddingTop() + listView.getPaddingBottom();
//        for (int i = 0; i < listAdapter.getCount(); i++) {
//            View listItem = listAdapter.getView(i, null, listView);
//            listItem.measure(View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.AT_MOST), View.MeasureSpec.UNSPECIFIED);
//            totalHeight += listItem.getMeasuredHeight();
//        }
//
//        ViewGroup.LayoutParams params = listView.getLayoutParams();
//        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
//        listView.setLayoutParams(params);
//    }

    private void createAlertDialog(int idTitle, final List<TinNhanDanhBaCuocGoi> list) {
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
                    inputData(list.get(position).getContent());
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
