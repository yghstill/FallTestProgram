package old.me.tool;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ygh.falltestprogram.R;

public class CalculatorActivity extends AppCompatActivity {

	// ��plus ��subtract ��multiply ��divide
	private EditText etResult;
	private Button btPlus;
	private Button btSub;
	private Button btMultiply;
	private Button btDivide;
	private Button btDelete;
	private Button btCe;
	private Button btPoint;
	private Button btDeng;
	private Button bt0;
	private Button bt1;
	private Button bt2;
	private Button bt3;
	private Button bt4;
	private Button bt5;
	private Button bt6;
	private Button bt7;
	private Button bt8;
	private Button bt9;
	private boolean flag = false;
	private Toolbar toolbar;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.calculator);
		etResult = (EditText) findViewById(R.id.etResult);
		btPlus = (Button) findViewById(R.id.btPlus);
		btSub = (Button) findViewById(R.id.btSubtract);
		btMultiply = (Button) findViewById(R.id.btMultiply);
		btDivide = (Button) findViewById(R.id.btDivide);
		btDelete = (Button) findViewById(R.id.btDelete);
		btDeng = (Button) findViewById(R.id.btDeng);
		btPoint = (Button) findViewById(R.id.btPoint);
		btCe = (Button) findViewById(R.id.btCe);
		bt0 = (Button) findViewById(R.id.bt0);
		bt1 = (Button) findViewById(R.id.bt1);
		bt2 = (Button) findViewById(R.id.bt2);
		bt3 = (Button) findViewById(R.id.bt3);
		bt4 = (Button) findViewById(R.id.bt4);
		bt5 = (Button) findViewById(R.id.bt5);
		bt6 = (Button) findViewById(R.id.bt6);
		bt7 = (Button) findViewById(R.id.bt7);
		bt8 = (Button) findViewById(R.id.bt8);
		bt9 = (Button) findViewById(R.id.bt9);

		etResult.setInputType(InputType.TYPE_NULL);

		toolbar = (Toolbar) findViewById(R.id.toolbar);
		toolbar.setTitle("计算器");
		setSupportActionBar(toolbar);
		toolbar.setNavigationIcon(R.mipmap.back1);

		toolbar.setNavigationOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});

		/*
		 * .��ť
		 */

		btPoint.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				String str1 = etResult.getText().toString();
				if (str1.equals("")) {
					etResult.setText("");
				} else {
					etResult.setText(str1 + btPoint.getText().toString());
				}
			}
		});

		/*
		 * 0---------9�����ְ�ť
		 */
		bt0.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				if (flag == true) {
					etResult.setText(bt0.getText().toString());
					flag = false;
				} else {
					String str1 = etResult.getText().toString();
					if (str1.equals("")) {
						etResult.setText("");
					} else {
						etResult.setText(str1 + bt0.getText().toString());
					}
				}

			}
		});

		bt1.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				if (flag == true) {
					etResult.setText(bt1.getText().toString());
					flag = false;
				} else {
					String str1 = etResult.getText().toString();
					etResult.setText(str1 + bt1.getText().toString());
				}
			}
		});

		bt2.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				if (flag == true) {
					etResult.setText(bt2.getText().toString());
					flag = false;
				} else {
					String str1 = etResult.getText().toString();
					etResult.setText(str1 + bt2.getText().toString());
				}
			}
		});

		bt3.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				if (flag == true) {
					etResult.setText(bt3.getText().toString());
					flag = false;
				} else {
					String str1 = etResult.getText().toString();
					etResult.setText(str1 + bt3.getText().toString());
				}
			}
		});

		bt4.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				if (flag == true) {
					etResult.setText(bt4.getText().toString());
					flag = false;
				} else {
					String str1 = etResult.getText().toString();
					etResult.setText(str1 + bt4.getText().toString());
				}
			}
		});

		bt5.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				if (flag == true) {
					etResult.setText(bt5.getText().toString());
					flag = false;
				} else {
					String str1 = etResult.getText().toString();
					etResult.setText(str1 + bt5.getText().toString());
				}
			}
		});

		bt6.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				if (flag == true) {
					etResult.setText(bt6.getText().toString());
					flag = false;
				} else {
					String str1 = etResult.getText().toString();
					etResult.setText(str1 + bt6.getText().toString());
				}
			}
		});

		bt7.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				if (flag == true) {
					etResult.setText(bt7.getText().toString());
					flag = false;
				} else {
					String str1 = etResult.getText().toString();
					etResult.setText(str1 + bt7.getText().toString());
				}
			}
		});

		bt8.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				if (flag == true) {
					etResult.setText(bt8.getText().toString());
					flag = false;
				} else {
					String str1 = etResult.getText().toString();
					etResult.setText(str1 + bt8.getText().toString());
				}
			}
		});

		bt9.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				if (flag == true) {
					etResult.setText(bt9.getText().toString());
					flag = false;
				} else {
					String str1 = etResult.getText().toString();
					etResult.setText(str1 + bt9.getText().toString());
				}
			}
		});

		/*
		 * ce�� ���
		 */
		btCe.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				etResult.setText("");
			}
		});

		/*
		 * Delete��
		 */
		btDelete.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				String str = etResult.getText().toString();
				if (str.length() == 0) {
					etResult.setText("");
				} else {
					etResult.setText(str.subSequence(0, str.length() - 1));
				}
			}
		});

		/*
		 * + ��-�� * ��/�ĸ���ť
		 */
		btPlus.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				final String str = etResult.getText().toString();
				if (str.equals("")) {
					etResult.setText("");
				} else {
					etResult.setText(str + btPlus.getText().toString());
				}
			}
		});

		btSub.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				String str = etResult.getText().toString();
				if (str.equals("")) {
					etResult.setText("");
				} else {
					etResult.setText(str + btSub.getText().toString());
				}
			}
		});

		btMultiply.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				String str = etResult.getText().toString();
				if (str.equals("")) {
					etResult.setText("");
				} else {
					etResult.setText(str + btMultiply.getText().toString());
				}
			}
		});

		btDivide.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				String str = etResult.getText().toString();
				if (str.equals("")) {
					etResult.setText("");
				} else {
					etResult.setText(str + btDivide.getText().toString());
				}
			}
		});

		/*
		 * = 按钮
		 */
		btDeng.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				String str = etResult.getText().toString();
				flag = true;
				/*
				 * if(str.charAt(str.length()-1)=='0' &&
				 * str.charAt(str.length()-2)=='/'){
				 * Toast.makeText(CalculatorActivity.this, "除数不能为0！",
				 * Toast.LENGTH_LONG).show(); }else{ }
				 */
				if (str.indexOf('+') > 0) {
					Double num1 = Double.parseDouble(str.substring(0,
							str.indexOf('+')));
					Double num2 = Double.parseDouble(str.substring(str
							.indexOf('+') + 1));
					Double result = num1 + num2;
					etResult.setText(result.toString());
				} else if (str.indexOf('-') > 0) {
					Double num1 = Double.parseDouble(str.substring(0,
							str.indexOf('-')));
					Double num2 = Double.parseDouble(str.substring(str
							.indexOf('-') + 1));
					Double result = num1 - num2;
					etResult.setText(result.toString());
				} else if (str.indexOf('x') > 0) {
					Double num1 = Double.parseDouble(str.substring(0,
							str.indexOf('x')));
					Double num2 = Double.parseDouble(str.substring(str
							.indexOf('x') + 1));
					Double result = num1 * num2;
					etResult.setText(result.toString());
				} else if (str.indexOf('÷') > 0) {
					Double num1 = Double.parseDouble(str.substring(0,
							str.indexOf('÷')));
					Double num2 = Double.parseDouble(str.substring(str
							.indexOf('÷') + 1));
					if (num2 == 0) {
						Toast.makeText(CalculatorActivity.this, "除数不能为0！",
								Toast.LENGTH_LONG).show();
					} else {
						Double result = num1 / num2;
						etResult.setText(result.toString());
					}
				}
			}
		});
	}

}
