package tools;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import com.example.ygh.falltestprogram.R;

public class AlertDialog {
	private Context context;
	private Dialog dialog;
	private LinearLayout lLayout_bg;
	private TextView txt_title;
	private TextView txt_msg;
	private Button btn_neg;
	private Button btn_pos;
	private ImageView img_line;
	private ImageView img_jg;
	private Display display;
	private boolean showTitle = false;
	private boolean showMsg = false;
	private boolean showPosBtn = false;
	private boolean showNegBtn = false;
	private static final int INIT_SET = 1;
	private int count = 30;
	private boolean isclickqx = false;

	// private BaseApplication mApplication;

	public AlertDialog(Context context) {
		this.context = context;
		WindowManager windowManager = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);
		display = windowManager.getDefaultDisplay();
	}

	@SuppressWarnings("deprecation")
	@SuppressLint("InflateParams")
	public AlertDialog builder() {
		// ��ȡDialog����
		View view = LayoutInflater.from(context).inflate(
				R.layout.view_alertdialog, null);

		// ��ȡ�Զ���Dialog�����еĿؼ�
		lLayout_bg = (LinearLayout) view.findViewById(R.id.lLayout_bg);
		txt_title = (TextView) view.findViewById(R.id.txt_title);
		txt_title.setVisibility(View.GONE);
		txt_msg = (TextView) view.findViewById(R.id.txt_msg);
		txt_msg.setVisibility(View.GONE);
		btn_neg = (Button) view.findViewById(R.id.btn_neg);
		btn_neg.setVisibility(View.GONE);
		btn_pos = (Button) view.findViewById(R.id.btn_pos);
		btn_pos.setVisibility(View.GONE);
		img_line = (ImageView) view.findViewById(R.id.img_line);
		img_line.setVisibility(View.GONE);
		img_jg = (ImageView) view.findViewById(R.id.dialog_jg);

		// ����Dialog���ֺͲ���
		dialog = new Dialog(context, R.style.AlertDialogStyle);
		dialog.setContentView(view);
		// mApplication = (BaseApplication) ((Service)
		// context).getApplication();
		// ����dialog������С
		lLayout_bg.setLayoutParams(new FrameLayout.LayoutParams((int) (display
				.getWidth() * 0.85), LayoutParams.WRAP_CONTENT));

		return this;
	}

	public AlertDialog setTitle(String title) {
		showTitle = true;
		if ("".equals(title)) {
			txt_title.setText("����");
		} else {
			txt_title.setText(title);
		}
		return this;
	}

	public AlertDialog setMsg(String msg) {
		showMsg = true;
		if ("".equals(msg)) {
			txt_msg.setText("����");
		} else {
			txt_msg.setText(msg);
		}
		return this;
	}

	public AlertDialog setCancelable(boolean cancel) {
		dialog.setCancelable(cancel);
		return this;
	}

	public AlertDialog setTypeSYS(boolean cancel) {
		if (cancel) {
			dialog.getWindow().setType(
					WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
		}
		return this;
	}

	public AlertDialog setPositiveButton(String text,
			final OnClickListener listener) {
		showPosBtn = true;
		if ("".equals(text)) {
			btn_pos.setText("ȡ��");
		} else {
			btn_pos.setText(text);
		}
		btn_pos.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				listener.onClick(v);
				isclickqx = true;
				Log.e("---�Ƿ���---", "===" + isclickqx);
				dialog.cancel();
				dialog = null;
			}
		});

		return this;
	}

	public AlertDialog setNegativeButton(String text, int type,
			final OnClickListener listener) {
		showNegBtn = true;
		if ("".equals(text)) {
			btn_neg.setText("ȷ��");
		} else {
			btn_neg.setText(text);
		}
		if (type == 0) {
		} else if (type == 1) {
			img_jg.setVisibility(View.VISIBLE);
		}
		btn_neg.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				listener.onClick(v);
				isclickqx=true;
				dialog.cancel();
				dialog = null;
			}
		});
		return this;
	}

	private void setLayout() {
		if (!showTitle && !showMsg) {
			txt_title.setText("��ʾ");
			txt_title.setVisibility(View.VISIBLE);
		}

		if (showTitle) {
			txt_title.setVisibility(View.VISIBLE);
		}

		if (showMsg) {
			txt_msg.setVisibility(View.VISIBLE);
		}

		if (!showPosBtn && !showNegBtn) {
			btn_pos.setText("ȷ��");
			btn_pos.setVisibility(View.VISIBLE);
			btn_pos.setBackgroundResource(R.drawable.alertdialog_single_selector);
			btn_pos.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					dialog.cancel();
					dialog = null;
				}
			});
		}

		if (showPosBtn && showNegBtn) {
			btn_pos.setVisibility(View.VISIBLE);
			btn_pos.setBackgroundResource(R.drawable.alertdialog_right_selector);
			btn_neg.setVisibility(View.VISIBLE);
			btn_neg.setBackgroundResource(R.drawable.alertdialog_left_selector);
			img_line.setVisibility(View.VISIBLE);
		}

		if (showPosBtn && !showNegBtn) {
			btn_pos.setVisibility(View.VISIBLE);
			btn_pos.setBackgroundResource(R.drawable.alertdialog_single_selector);
		}

		if (!showPosBtn && showNegBtn) {
			btn_neg.setVisibility(View.VISIBLE);
			btn_neg.setBackgroundResource(R.drawable.alertdialog_single_selector);
		}
	}

	public void show() {
		setLayout();
		dialog.show();
	}



	private String getTimeText(String text, int count) {
		if (text != null && text.length() > 0 && count >= 0) {
			int index = text.indexOf("(");
			if (index > 0) {
				text = text.substring(0, index);
				return (text + "(" + count + "s)");
			} else {
				return (text + "(" + count + "s)");
			}

		}
		return text;
	}

}
