package old.main.contast;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.ygh.falltestprogram.R;

import java.util.List;

import tools.XCRoundImageView;


public class ContastAdapter extends ArrayAdapter<ContastBean> {

	private int resourceId;

	public ContastAdapter(Context context, int textViewResourceId,
			List<ContastBean> objects) {
		super(context, textViewResourceId, objects);
		resourceId = textViewResourceId;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ContastBean contast = getItem(position);
		View view;
		ViewHolder viewHolder;
		if (convertView == null) {
			view = LayoutInflater.from(getContext()).inflate(resourceId, null);
			viewHolder = new ViewHolder();
			viewHolder.headImage = (XCRoundImageView) view.findViewById(R.id.head_image_contast);
			viewHolder.contastName = (TextView) view.findViewById(R.id.contast_name);
			viewHolder.cellphone=(TextView) view.findViewById(R.id.cellphone_contast);
			view.setTag(viewHolder);
		} else {
			view = convertView;
			viewHolder = (ViewHolder) view.getTag();
		}
		if(contast.getimagehead()!=null){
			viewHolder.headImage.setImageDrawable(contast.getimagehead());
		}
		viewHolder.headImage.setType(XCRoundImageView.TYPE_ROUND);
		viewHolder.headImage.setRoundBorderRadius(20);// ����Բ�ǽǶ�
		viewHolder.contastName.setText(contast.getName());
		viewHolder.cellphone.setText(contast.getCellphone());
		return view;
	}
	
	class ViewHolder {
		
		XCRoundImageView headImage;
		
		TextView contastName;
		
		TextView cellphone;
		
	}
}
