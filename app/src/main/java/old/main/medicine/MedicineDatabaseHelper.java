package old.main.medicine;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class MedicineDatabaseHelper extends SQLiteOpenHelper {
	public static final String CREATE_MEDICINE = "create table Medicine ("
			+ "id integer primary key autoincrement, " + "medicine text, "
			+ "time text)";

	
	private Context mContext;
	
	public MedicineDatabaseHelper(Context context, String name,
			CursorFactory factory, int version) {
		super(context, name, factory, version);
		mContext = context;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		db.execSQL(CREATE_MEDICINE);
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub
		
	}

	
}
