package old.main.medicine;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.ygh.falltestprogram.R;

import java.util.List;


public class MedicineAdapter extends ArrayAdapter<MedicineBean>{
	private int resourceId;


	public MedicineAdapter(Context context, int textViewResourceId,
			List<MedicineBean> objects) {
		super(context, textViewResourceId, objects);
		resourceId = textViewResourceId;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		MedicineBean medicineBean = getItem(position);
		View view;
		ViewHolder viewHolder;
		if (convertView == null) {
			view = LayoutInflater.from(getContext()).inflate(resourceId, null);
			viewHolder = new ViewHolder();
			viewHolder.medicinename = (TextView) view.findViewById(R.id.medicine_name);
			viewHolder.clocktime=(TextView) view.findViewById(R.id.time_medicine);
			view.setTag(viewHolder);
		} else {
			view = convertView;
			viewHolder = (ViewHolder) view.getTag();
		}
		viewHolder.medicinename.setText(medicineBean.getMedicinename());
		viewHolder.clocktime.setText(medicineBean.getClocktime());
		return view;
	}
	
	class ViewHolder {
		TextView medicinename;
		TextView clocktime;
	}
	
	

}
