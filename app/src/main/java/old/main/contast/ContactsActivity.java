package old.main.contast;

import android.Manifest;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.example.ygh.falltestprogram.R;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import loading.BaseApplication;
import tools.ActionSheetDialog;
import tools.PlayTsSound;
import tools.Utils;


public class ContactsActivity extends AppCompatActivity {
	private Toolbar toolbar;
	private List<ContastBean> contastList = new ArrayList<ContastBean>();
	private TextView maddButton;
	private ListView mcontastlist;
	private contastDatabaseHelper dbHelper;
	private String mname = null;
	private String mphone = null;
	private byte[] mheadbyte = null;
	private ContastAdapter mContastAdapter;
	private TextView txt_right;
	private TextView mTxt_title;
	private BaseApplication mApplication;
	private String bjphoneString = null;
	private Context context = ContactsActivity.this;

	// private boolean islongclick = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.contacts_view);
		mApplication = (BaseApplication) getApplication();
		initView();
		initContast();
	}

	private void initView() {
		toolbar = (Toolbar) findViewById(R.id.toolbar);
		toolbar.setTitle("添加联系人");
		setSupportActionBar(toolbar);
		toolbar.setNavigationIcon(R.mipmap.back1);

		dbHelper = new contastDatabaseHelper(this, "Contast.db", null, 1);
		dbHelper.getWritableDatabase();
		mcontastlist = (ListView) findViewById(R.id.contast_list_view);
		maddButton = (TextView) findViewById(R.id.btn_add_contast);
		mContastAdapter = new ContastAdapter(ContactsActivity.this,
				R.layout.contast_list_item, contastList);
		maddButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(ContactsActivity.this,
						ContactsNew.class);
				startActivityForResult(intent, 0);
			}
		});

		toolbar.setNavigationOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				new PlayTsSound(context, R.raw.back);
				Utils.finish(ContactsActivity.this);
			}
		});

		mcontastlist.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
									int position, long id) {
				// if (!islongclick) {
				ContastBean contast = contastList.get(position);
				String phone = contast.getCellphone();
				Intent intent = new Intent(Intent.ACTION_CALL, // ����һ����ͼ
						Uri.parse("tel:" + phone));
				if (ActivityCompat.checkSelfPermission(ContactsActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
					// TODO: Consider calling
					//    ActivityCompat#requestPermissions
					// here to request the missing permissions, and then overriding
					//   public void onRequestPermissionsResult(int requestCode, String[] permissions,
					//                                          int[] grantResults)
					// to handle the case where the user grants the permission. See the documentation
					// for ActivityCompat#requestPermissions for more details.
					return;
				}
				startActivity(intent);
				// }
			}
		});

		mcontastlist.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					final int position, long id) {
				// islongclick = true;
				new ActionSheetDialog(ContactsActivity.this)
						.builder()
						.setCanceledOnTouchOutside(false)
						.addSheetItem("�༭", ActionSheetDialog.SheetItemColor.Blue,
								new ActionSheetDialog.OnSheetItemClickListener() {
									@Override
									public void onClick(int which) {
										ContastBean contast = contastList
												.get(position);
										Intent intent = new Intent(
												ContactsActivity.this,
												ContactsNew.class);
										intent.putExtra("name",
												contast.getName());
										intent.putExtra("phone",
												contast.getCellphone());
										bjphoneString = contast.getCellphone();
										Bitmap bitmap = drawableToBitmap(contast
												.getimagehead());
										byte[] bs = Bitmap2Bytes(bitmap);
										intent.putExtra("img", bs);
										mApplication.setIsclockset(true);
										startActivityForResult(intent, 0);
									}
								})
						.addSheetItem("ɾ��", ActionSheetDialog.SheetItemColor.Red,
								new ActionSheetDialog.OnSheetItemClickListener() {
									@Override
									public void onClick(int which) {
										ContastBean contast = contastList
												.get(position);
										SQLiteDatabase db = dbHelper
												.getWritableDatabase();
										db.delete("Contast", "phone=?",
												new String[] { contast
														.getCellphone() });
										contastList.remove(position);
										mContastAdapter.notifyDataSetChanged();
									}
								}).show();

				return true;
			}
		});

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		switch (resultCode) {
		case RESULT_OK:
			Bundle result = data.getExtras();
			mname = result.getString("name");
			mphone = result.getString("phone");
			mheadbyte = result.getByteArray("imghead");
			DBevent();
			Drawable drawable = new BitmapDrawable(getResources(),
					Bytes2Bimap(mheadbyte));
			ContastBean bean = new ContastBean(mname, mphone, drawable);
			contastList.add(bean);
			mcontastlist.setAdapter(mContastAdapter);
			break;
		case 10:
			Bundle result2 = data.getExtras();
			mname = result2.getString("name");
			mphone = result2.getString("phone");
			mheadbyte = result2.getByteArray("imghead");
			updateDBevent();
			initContast();
			break;

		default:
			break;
		}

	}

	private void updateDBevent() {
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("name", mname);
		values.put("phone", mphone);
		values.put("img", mheadbyte);
		db.update("Contast", values, "phone=?", new String[] { bjphoneString });

	}

	private void DBevent() {
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("name", mname);
		values.put("phone", mphone);
		values.put("img", mheadbyte);
		db.insert("Contast", null, values);
		values.clear();

	}

	/** ��BItmapת�����ֽ����� */
	public byte[] Bitmap2Bytes(Bitmap bm) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
		return baos.toByteArray();
	}

	// ���ֽ�����ת��Ϊbitmap
	private Bitmap Bytes2Bimap(byte[] b) {
		if (b.length != 0) {
			return BitmapFactory.decodeByteArray(b, 0, b.length);
		} else {
			return null;
		}
	}

	public static Bitmap drawableToBitmap(Drawable drawable) {
		// ȡ drawable �ĳ���
		int w = drawable.getIntrinsicWidth();
		int h = drawable.getIntrinsicHeight();

		// ȡ drawable ����ɫ��ʽ
		Bitmap.Config config = drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
				: Bitmap.Config.RGB_565;
		// ������Ӧ bitmap
		Bitmap bitmap = Bitmap.createBitmap(w, h, config);
		// ������Ӧ bitmap �Ļ���
		Canvas canvas = new Canvas(bitmap);
		drawable.setBounds(0, 0, w, h);
		// �� drawable ���ݻ���������
		drawable.draw(canvas);
		return bitmap;
	}

	private void initContast() {
		// TODO Auto-generated method stub
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		Cursor cursor = db.query("Contast", null, null, null, null, null, null);
		if (cursor.moveToFirst()) {
			do {
				String name = cursor.getString(cursor.getColumnIndex("name"));
				String phone = cursor.getString(cursor.getColumnIndex("phone"));
				byte[] mhead = cursor.getBlob(cursor.getColumnIndex("img"));
				if (!name.equals("")) {
					Drawable mdrawable = null;
					if (mhead == null) {
						ContastBean bean = new ContastBean(name, phone, null);
					} else {
						mdrawable = new BitmapDrawable(getResources(),
								Bytes2Bimap(mhead));
					}
					ContastBean bean = new ContastBean(name, phone, mdrawable);
					contastList.add(bean);
				}

			} while (cursor.moveToNext());

		}
		cursor.close();
		if (contastList.size() != 0) {
			mcontastlist.setAdapter(mContastAdapter);
		}

	}

}
