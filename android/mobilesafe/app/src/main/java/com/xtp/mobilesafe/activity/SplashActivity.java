package com.xtp.mobilesafe.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.Bundle;
import android.util.Log;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.xtp.mobilesafe.R;
import com.xtp.mobilesafe.utli.StreamUtil;
import com.xtp.mobilesafe.utli.ToastUtil;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class SplashActivity extends Activity {

    //更新版本状态码
    private static final int UPDATE_VERSION = 100;
    //进入主界面状态码
    private static final int ENTER_HOME = 101;
    //url错误异常
    private static final int URL_ERROR = 102;
    //io异常
    private static final int IOE_ERROR = 103;
    //json解析异常
    private static final int JSON_ERROR = 104;

    private TextView tv_version_name ;
    private int localVersionCode ;
    private String versionDes;
    private  String downLoadUrl;
    private RelativeLayout rl_root;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case UPDATE_VERSION:
                    showUpdateDialog();
                    break;
                case ENTER_HOME:
                    enterHome();
                    break;
                case URL_ERROR:
                    ToastUtil.show(SplashActivity.this,"URL异常");
                    enterHome();
                    break;
                case IOE_ERROR:
                    ToastUtil.show(SplashActivity.this,"读取异常");
                    enterHome();
                    break;
                case JSON_ERROR:
                    ToastUtil.show(SplashActivity.this,"解析异常");
                    enterHome();
                    break;
            }
            super.handleMessage(msg);
        }
    };

    /**
     * 弹出对话框，提示用户信息
     */
    private void showUpdateDialog() {
        //对话框，是依赖于activity存在的
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setIcon(R.drawable.ic_launcher);
        builder.setTitle("版本更新");
        builder.setMessage(versionDes);
        builder.setPositiveButton("立即更新", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                downLoadApk();
            }
        });
        builder.setNegativeButton("稍后再说", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                enterHome();
            }
        });

        //点击取消事件监听
        builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                enterHome();
                dialog.dismiss();
            }
        });

        builder.show();
    }

    /**
     * 下载新版本的apk
     */
    private void downLoadApk() {
        //判断sdk是否挂载
        if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
            //获取sdcard的路径
            String fileName = Environment.getExternalStorageDirectory().getAbsolutePath()+File.separator+"mobliesafe.apk";
            HttpUtils httpUtils = new HttpUtils();
            httpUtils.download(downLoadUrl, fileName, new RequestCallBack<File>() {

                //下载成功
                @Override
                public void onSuccess(ResponseInfo<File> responseInfo) {
                    Log.i("SUCCESSED","下载成功");
                    File file = responseInfo.result;
                    installApk(file);
                }
                //下载失败
                @Override
                public void onFailure(HttpException e, String s) {
                    Log.i("Failed","下载失败");
                }

                //刚开始下载
                @Override
                public void onStart() {
                    super.onStart();
                    Log.i("START","刚开始下载");
                }

                //正在下载
                @Override
                public void onLoading(long total, long current, boolean isUploading) {
                    super.onLoading(total, current, isUploading);
                    Log.i("STARTTING","正在下载");
                }
            });
        }
    }

    /**
     * 安装apk
     */
    private void installApk(File file) {
        //安装apk是系统
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
//        intent.setData(Uri.fromFile(file));
//        intent.setType("application/vnd.android.package-archive");
        intent.setDataAndType(Uri.fromFile(file),"application/vnd.android.package-archive");
        startActivityForResult(intent,0);
    }

    /**
     * 开启一个activity后，返回结果调用的方法
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        enterHome();
    }

    /**
     * 进入应用程序主界面
     */
    private void enterHome() {
        Intent intent = new Intent(this,HomeActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //去除当前activity的头title
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_splash);
        //初始化ui
        initUI();
        initData();
        //初始化动画
        initAnimation();
    }

    /**
     * 初始化动画
     */
    private void initAnimation() {
        Animation animation = new AlphaAnimation(0,1);
        animation.setDuration(3000);
        rl_root.startAnimation(animation);
    }

    /**
     * 初始化UI
     */
    private void initUI(){
        tv_version_name = findViewById(R.id.tv_version_name);
        rl_root = findViewById(R.id.rl_root);
    }

    /**
     * 初始化数据
     */
    private void initData(){
        String versionName = getVersionName();
        tv_version_name.setText(versionName);
        //获取本地版本号
        localVersionCode = getVersionCode();
        //获取服务器版本号（json/xml）
        checkVersion();
    }

    private void checkVersion() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Message message = Message.obtain();
                long startTime = System.currentTimeMillis();
                try {
                    URL url = new URL("http:/10.0.2.2:8080/update.json");
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setConnectTimeout(2000);
                    connection.setReadTimeout(2000);
                    //connection.setRequestMethod("GET");
                    if(connection.getResponseCode()==200){
                        InputStream inputStream = connection.getInputStream();
                        String json = StreamUtil.stream2String(inputStream);
                        Log.i("TAG_VERSION",json);
                        JSONObject object = new JSONObject(json);
                        String versionName = object.getString("versionName");
                        String versionCode = object.getString("versionCode");
                        versionDes = object.getString("versionDes");
                        downLoadUrl = object.getString("downLoadUrl");
                        Log.i("versionName",versionName);
                        Log.i("versionCode",versionCode);
                        Log.i("versionDes",versionDes);
                        Log.i("downLoadUrl",downLoadUrl);
                        if(localVersionCode<Integer.parseInt(versionCode)){
                            message.what=UPDATE_VERSION;
                        }else {
                            message.what=ENTER_HOME;
                        }
                    }
                } catch (MalformedURLException e) {
                    message.what=URL_ERROR;
                    e.printStackTrace();
                } catch (IOException e) {
                    message.what=IOE_ERROR;
                    e.printStackTrace();
                } catch (JSONException e) {
                    message.what=JSON_ERROR;
                    e.printStackTrace();
                }finally {
                    long endTime = System.currentTimeMillis();
                    if(endTime-startTime<4000){
                        try {
                            Thread.sleep(4000-(endTime-startTime));
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    handler.sendMessage(message);
                }
            }
        }).start();
    }

    /**
     * 获取本地版本号
     * @return
     */
    private int getVersionCode() {
        PackageManager packageManager = getPackageManager();
        try {
            PackageInfo packageInfo = packageManager.getPackageInfo(getPackageName(), 0);
            return packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 获取版本名称
     */
    private String getVersionName() {
        PackageManager packageManager = getPackageManager();
        try {
            PackageInfo packageInfo = packageManager.getPackageInfo(getPackageName(), 0);
            return packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
}
