package old.main.contast;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.ygh.falltestprogram.R;

import java.io.ByteArrayOutputStream;
import java.io.File;

import loading.BaseApplication;
import tools.ActionSheetDialog;
import tools.AlertDialog;
import tools.PlayTsSound;
import tools.XCRoundImageView;

public class ContactsNew extends AppCompatActivity implements OnClickListener {
    private Toolbar toolbar;
    private EditText mnameEdt;
    private EditText mrealtion;
    private EditText mcellphone;
    private ImageView mdelname;
    private ImageView mdelrealtion;
    private ImageView mdelphone;
    private Context context = ContactsNew.this;
    private Button btn_save;
    /**
     * 标记是拍照还是相册0 是拍照1是相册
     */
    private int cameraorpic;

    /**
     * 编辑头像相册选取
     */
    private static final int REQUESTCODE_PICK = 1;
    /**
     * 设置头像
     */
    private static final int REQUESTCODE_CUTTING = 2;
    /**
     * 编辑头像拍照选取
     */
    private static final int PHOTO_REQUEST_TAKEPHOTO = 3;
    /**
     * 头像
     */
    private XCRoundImageView mhead;
    private Bitmap mheadphoto;
    private byte[] bitmapByte = null;
    private BaseApplication mApplication;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.context_add_view);
        mApplication = (BaseApplication) getApplication();
        initView();
        if (mApplication.getIsclockset()) {
            initdata();
        }
    }

    private void initView() {
        // 初始化top
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("添加联系人");
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.mipmap.back1);
        //初始化控件
        mhead = (XCRoundImageView) findViewById(R.id.img_contast_head);
        mhead.setType(XCRoundImageView.TYPE_ROUND);
        mhead.setRoundBorderRadius(20);// 设置圆角角度
        mnameEdt = (EditText) findViewById(R.id.contast_edt_name);
        mrealtion = (EditText) findViewById(R.id.contast_edt_relation);
        mcellphone = (EditText) findViewById(R.id.contast_edt_cellphone);
        mdelname = (ImageView) findViewById(R.id.img_del_name);
        mdelrealtion = (ImageView) findViewById(R.id.img_del_relation);
        mdelphone = (ImageView) findViewById(R.id.img_del_cellphone);
        btn_save= (Button) findViewById(R.id.btn_save);
        btn_save.setOnClickListener(this);
        mhead.setOnClickListener(this);
        mdelname.setOnClickListener(this);
        mdelrealtion.setOnClickListener(this);
        mdelphone.setOnClickListener(this);
        mnameEdt.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                if (mnameEdt.getText().toString().length() > 0) {
                    mdelname.setVisibility(View.VISIBLE);
                    initmsucess();
                } else {
                    mdelname.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable arg0) {
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1,
                                          int arg2, int arg3) {
            }
        });
        mrealtion.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                if (mrealtion.getText().toString().length() > 0) {
                    mdelrealtion.setVisibility(View.VISIBLE);
                    initmsucess();
                } else {
                    mdelrealtion.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable arg0) {
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1,
                                          int arg2, int arg3) {
            }
        });
        mcellphone.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                if (mcellphone.getText().toString().length() > 0) {
                    mdelphone.setVisibility(View.VISIBLE);
                    initmsucess();
                } else {
                    mdelphone.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable arg0) {
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1,
                                          int arg2, int arg3) {
            }
        });

        /**
         * 返回
         */
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new PlayTsSound(context, R.raw.back);
                if (!mnameEdt.getText().toString().equals("")) {
                    new AlertDialog(context).builder().setTitle("警告")
                            .setMsg("您还没有保存，确定要退出吗？")
                            .setPositiveButton("确定", new OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    mApplication.setIsclockset(false);
                                    finish();
                                }
                            }).setNegativeButton("取消", 0, new OnClickListener() {
                        @Override
                        public void onClick(View arg0) {
                        }
                    }).show();
                } else {
                    mApplication.setIsclockset(false);
                    finish();
                }
            }
        });

    }


    /**
     * 按钮状态监听
     */
    private void initmsucess() {
        if (mnameEdt.getText().toString().length() > 0
                & mrealtion.getText().toString().length() > 0
                & mcellphone.getText().toString().length() > 0
               ) {
            btn_save.setEnabled(true);
        } else {
            btn_save.setEnabled(false);
        }
    }


    private void initdata() {
        Intent intent = getIntent();
        byte[] bs = intent.getExtras().getByteArray("img");
        Bitmap bitmap = Bytes2Bimap(bs);
        Drawable drawable = new BitmapDrawable(getResources(), bitmap);
        mhead.setImageDrawable(drawable);
        bitmapByte = bs;
        String[] mString = intent.getExtras().getString("name").split("\\(");
        mnameEdt.setText(mString[0]);
        mrealtion.setText(mString[1].substring(0, mString[1].length() - 1));
        mcellphone.setText(intent.getExtras().getString("phone"));
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.img_contast_head:
                popCheck();
                break;
            case R.id.img_del_name:
                mnameEdt.setText("");
                break;
            case R.id.img_del_relation:
                mrealtion.setText("");
                break;
            case R.id.img_del_cellphone:
                mcellphone.setText("");
                break;
            case R.id.btn_save:
                String savename = mnameEdt.getText().toString();
                final String saverealtion = mrealtion.getText().toString();
                savename = savename + "(" + saverealtion + ")";
                final String savephone = mcellphone.getText().toString();

                Intent intent = new Intent(ContactsNew.this,
                        ContactsActivity.class);
                intent.putExtra("imghead", bitmapByte);
                intent.putExtra("name", savename);
                intent.putExtra("phone", savephone);
                if (!mApplication.getIsclockset()) {
                    setResult(RESULT_OK, intent);
                    finish();
                } else {
                    mApplication.setIsclockset(false);
                    setResult(10, intent);
                    finish();
                }
            default:
                break;
        }
    }

    public void popCheck() {

        new ActionSheetDialog(ContactsNew.this)
                .builder()
                .setCancelable(false)
                .setCanceledOnTouchOutside(false)
                .addSheetItem("拍照", ActionSheetDialog.SheetItemColor.Blue,
                        new ActionSheetDialog.OnSheetItemClickListener() {
                            @Override
                            public void onClick(int which) {
                                cameraorpic = 0;
                                openCamera();
                            }
                        })
                .addSheetItem("打开相册", ActionSheetDialog.SheetItemColor.Blue,
                        new ActionSheetDialog.OnSheetItemClickListener() {
                            @Override
                            public void onClick(int which) {
                                cameraorpic = 1;
                                skipPic();
                            }
                        }).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // 判断请求码是编辑就跳到编辑
        switch (requestCode) {
            case REQUESTCODE_PICK:
                if (data == null || data.getData() == null) {
                    return;
                }
                startPhotoZoom(data.getData());
                break;
            case REQUESTCODE_CUTTING:
                if (data != null) {
                    setPicToView(data);
                }
                break;
            case PHOTO_REQUEST_TAKEPHOTO:
                startPhotoZoom(Uri.fromFile(outFile));
                break;
            default:
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * save the picture data 设置头像并保存头像
     *
     * @param picdata
     */
    private void setPicToView(Intent picdata) {
        Bundle extras = picdata.getExtras();
        if (extras != null) {
            Bitmap photo = extras.getParcelable("data");
            Drawable drawable = new BitmapDrawable(getResources(), photo);
            mhead.setImageDrawable(drawable);
            bitmapByte = Bitmap2Bytes(photo);
            // /** 可用于图像上传 */
            // UpdateAvater("url",Bitmap2Bytes(photo));
        }
    }

    public void UpdateAvater(String url, byte[] bs) {
        // RequestParams params=new RequestParams();
        // params.put("此处是上传的参数名字", bs);
        // HttpUtil.post(url, params, new AsyncHttpResponseHandler() {
        //
        // @Override
        // public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
        // //成功做什么
        // }
        //
        // @Override
        // public void onFailure(int arg0, Header[] arg1, byte[] arg2, Throwable
        // arg3) {
        // // TODO Auto-generated method stub
        // //失败做什么
        // }
        // });
    }

    /**
     * 将BItmap转换成字节数组
     */
    public byte[] Bitmap2Bytes(Bitmap bm) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
        return baos.toByteArray();
    }

    // 将字节数组转换为bitmap
    private Bitmap Bytes2Bimap(byte[] b) {
        if (b.length != 0) {
            return BitmapFactory.decodeByteArray(b, 0, b.length);
        } else {
            return null;
        }
    }

    /**
     * 设置可编辑头像
     */
    public void startPhotoZoom(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", true);
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", 300);
        intent.putExtra("outputY", 300);
        intent.putExtra("return-data", true);
        intent.putExtra("noFaceDetection", true);
        startActivityForResult(intent, REQUESTCODE_CUTTING);
    }

    /**
     * 打开相册
     */
    private void skipPic() {
        Intent pickIntent = new Intent(Intent.ACTION_PICK, null);
        pickIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                "image/*");
        startActivityForResult(pickIntent, REQUESTCODE_PICK);
    }

    /**
     * 指定拍摄图片文件位置避免获取到缩略图
     */
    File outFile;

    /**
     * 打开相机
     */
    private void openCamera() {
        String state = Environment.getExternalStorageState();
        if (state.equals(Environment.MEDIA_MOUNTED)) {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            File outDir = Environment
                    .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
            if (!outDir.exists()) {
                outDir.mkdirs();
            }
            outFile = new File(outDir, System.currentTimeMillis() + ".jpg");
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(outFile));
            intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
            startActivityForResult(intent, PHOTO_REQUEST_TAKEPHOTO);
        } else {
            Log.e("CAMERA", "请确认已经插入SD卡");
        }
    }

}
