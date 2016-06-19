package old.main.contast;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class contastDatabaseHelper extends SQLiteOpenHelper {
	public static final String CREATE_CONTAST = "create table Contast ("
			+ "id integer primary key autoincrement, " + "name text, "
			+ "phone text," + "img blob)";


	private Context mContext;

	public contastDatabaseHelper(Context context, String name,
			CursorFactory factory, int version) {
		super(context, name, factory, version);
		mContext = context;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(CREATE_CONTAST);
//		Toast.makeText(mContext, "Create succeeded", Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//		db.execSQL("drop table if exists Book");
//		db.execSQL("drop table if exists Category");
//		onCreate(db);
	}

}
