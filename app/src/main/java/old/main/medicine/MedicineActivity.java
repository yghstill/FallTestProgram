package old.main.medicine;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.example.ygh.falltestprogram.R;

import java.util.ArrayList;
import java.util.List;

import loading.BaseApplication;
import tools.ActionSheetDialog;

public class MedicineActivity extends AppCompatActivity implements OnClickListener {
    private TextView txt_title;
    private ListView clocklist;
    private TextView btn_set;
    private int mHour = -1;
    private int mMinute = -1;
    //	private boolean isSet = false;
    public static final long DAY = 1000L * 60 * 60 * 24;
    private BaseApplication mApplication;
    private List<MedicineBean> medicineList = new ArrayList<MedicineBean>();
    private MedicineDatabaseHelper dbHelper;
    private MedicineAdapter medicineAdapter;
    private String mmedicine = null;
    private String mtime = null;
    private String updateflagMedicine = null;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.medicine_act);
        mApplication = (BaseApplication) getApplication();
        BaseApplication.getInstance().addActivity(this);
        initView();
        if (mApplication.getIsclockset()) {
            Intent intent = getIntent();
            Bundle result = intent.getExtras();
            mmedicine = result.getString("medicinename");
            mtime = result.getString("time");
            insertDBevent();
            mApplication.setIsclockset(false);
        }
        initMedicine();
    }

    private void initView() {
        dbHelper = new MedicineDatabaseHelper(this, "Medicine.db", null, 1);
        dbHelper.getWritableDatabase();
        clocklist = (ListView) findViewById(R.id.clock_list);
        medicineAdapter = new MedicineAdapter(MedicineActivity.this,
                R.layout.medinine_list_item, medicineList);
        btn_set = (TextView) findViewById(R.id.btn_add_clock);
        btn_set.setOnClickListener(this);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("家里情况");
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.mipmap.back1);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        clocklist.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    final int position, long id) {
                final MedicineBean medicine = medicineList.get(position);
                new ActionSheetDialog(MedicineActivity.this)
                        .builder()
                        .setCanceledOnTouchOutside(false)
                        .addSheetItem("�༭", ActionSheetDialog.SheetItemColor.Blue,
                                new ActionSheetDialog.OnSheetItemClickListener() {
                                    @Override
                                    public void onClick(int which) {
                                        Intent intent = new Intent(
                                                MedicineActivity.this,
                                                MedicineNew.class);
                                        intent.putExtra("medicine",
                                                medicine.getMedicinename());
//								intent.putExtra("time",
//										medicine.getClocktime());
                                        mApplication.setIsupdateData(true);
//								startActivityForResult(intent, 0);
                                        startActivity(intent);
                                    }
                                })
                        .addSheetItem("ɾ��", ActionSheetDialog.SheetItemColor.Red,
                                new ActionSheetDialog.OnSheetItemClickListener() {
                                    @Override
                                    public void onClick(int which) {
                                        SQLiteDatabase db = dbHelper
                                                .getWritableDatabase();
                                        db.delete("Medicine", "medicine=?",
                                                new String[]{medicine.getMedicinename()});
                                        medicineList.remove(position);
                                        medicineAdapter.notifyDataSetChanged();
                                    }
                                }).show();
            }
        });
    }


    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.btn_add_clock:
                Intent intent = new Intent(MedicineActivity.this, MedicineNew.class);
//			startActivityForResult(intent, 0);
                startActivity(intent);
                break;
            default:
                break;
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode) {
            case RESULT_OK:
                Log.e("----��-----", "RESULT_OK");
                Bundle result = data.getExtras();
                mmedicine = result.getString("medicinename");
                mtime = result.getString("time");
                insertDBevent();
                MedicineBean bean = new MedicineBean(mmedicine, mtime);
                medicineList.add(bean);
                clocklist.setAdapter(medicineAdapter);
                break;
            case 10:
                Bundle result2 = data.getExtras();
                mmedicine = result2.getString("medicine");
                mtime = result2.getString("time");
                updateDBevent();
                initMedicine();
                break;

            default:
                break;
        }

    }

    private void insertDBevent() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("medicine", mmedicine);
        values.put("time", mtime);
        db.insert("Medicine", null, values);
        values.clear();
    }

    private void updateDBevent() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("medicine", mmedicine);
        values.put("time", mtime);
        db.update("Contast", values, "medicine=?", new String[]{updateflagMedicine});
    }

    private void initMedicine() {
        // TODO Auto-generated method stub
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.query("Medicine", null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                String medicine = cursor.getString(cursor.getColumnIndex("medicine"));
                String time = cursor.getString(cursor.getColumnIndex("time"));
                if (!(medicine.equals("") && time.equals(""))) {
                    MedicineBean bean = new MedicineBean(medicine, time);
                    medicineList.add(bean);
                }

            } while (cursor.moveToNext());

        }
        cursor.close();
        if (medicineList.size() != 0) {
            clocklist.setAdapter(medicineAdapter);
        }

    }

}
