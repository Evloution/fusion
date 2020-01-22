package com.elink.fusion.util;

import android.app.Dialog;
import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import androidx.core.content.FileProvider;


import com.elink.fusion.R;

import java.io.File;

import static android.content.Context.DOWNLOAD_SERVICE;

/**
 * @author Evloution_
 * @date 2018/11/10
 * @explain 更新 APP工具类，下载APK。
 */
public class DownLoadUtils {

    private static DownloadManager mDownloadManager;
    private static long mId;

    public static String getVersionInfoFromServer(Context context, String versionName) {
        //比较版本信息
        int result = APKVersionCodeUtils.compareVersions(APKVersionCodeUtils.getVersionName(context), versionName);
        Log.i("TAG", "本地版本：" + APKVersionCodeUtils.getVersionName(context));
        Log.i("TAG", "服务器版本：" + versionName);
        if (result == 1) { // 不是最新版本
            return "1";
        }
        return "0";
    }

    public static void showDialog(final Context context, String publishDesc, String newVersion, final String path, final String versionName) {
        final Dialog dialog = new Dialog(context);
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        TextView version, content;
        Button left, right;
        View view = inflater.inflate(R.layout.version_update, null, false);
        version = (TextView) view.findViewById(R.id.version);
        content = (TextView) view.findViewById(R.id.content);
        left = (Button) view.findViewById(R.id.left);
        right = (Button) view.findViewById(R.id.right);
        content.setText(publishDesc);
        version.setText("新版本号：" + newVersion);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                downLoadApk(context, path, versionName);
            }
        });
        dialog.setContentView(view);
        dialog.setCancelable(false);
        Window dialogWindow = dialog.getWindow();
        dialogWindow.setGravity(Gravity.CENTER);
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        WindowManager wm = (WindowManager)
                context.getSystemService(Context.WINDOW_SERVICE);
        lp.width = wm.getDefaultDisplay().getWidth() / 10 * 9;
        dialogWindow.setAttributes(lp);
        dialog.show();
    }

    private static void downLoadApk(Context context, String path, String appName) {
        //此处使用DownLoadManager开启下载任务
        mDownloadManager = (DownloadManager) context.getSystemService(DOWNLOAD_SERVICE);
        //将要请求下载的文件的Uri传递给Download Manager
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(path));
        //设置允许下载的网络类型
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE);
        // 下载过程和下载完成后通知栏有通知消息。
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE | DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setTitle("下载更新");
        request.setDescription("apk正在下载...");
        // 设置为可见和可管理
        request.setVisibleInDownloadsUi(true);
        // 设置为可被媒体扫描器找到
        request.allowScanningByMediaScanner();
        request.setMimeType("application/vnd.android.package-archive");
        //设置保存目录  /storage/emulated/0/Android/包名/files/Download
        request.setDestinationInExternalFilesDir(context, Environment.DIRECTORY_DOWNLOADS, appName + ".apk");
        //进入下载队列
        mId = mDownloadManager.enqueue(request);
        //广播监听下载完成
        listener(context, mId);
    }

    private static void listener(Context context, final long id) {
        IntentFilter intentFilter = new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE);

        BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                DownloadManager manager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
                long longExtra = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
                if (id == longExtra) {
                    DownloadManager.Query query = new DownloadManager.Query();
                    query.setFilterById(longExtra);

                    Cursor cursor = manager.query(query);
                    if (cursor.moveToFirst()) {
                        // 获取文件下载路径
                        String fileName = cursor.getString(cursor.getColumnIndex(DownloadManager.COLUMN_LOCAL_URI));
                        // 如果文件名不为空，说明文件已存在,则进行自动安装apk
                        if (fileName != null) {
                            openAPK(context, fileName);
                        }
                    }
                    cursor.close();
                }
            }
        };
        context.registerReceiver(broadcastReceiver, intentFilter);
    }

    private static void openAPK(Context context, String fileSavePath) {
        File file = new File(Uri.parse(fileSavePath).getPath());
        String filePath = file.getAbsolutePath();
        Intent intent = new Intent(Intent.ACTION_VIEW);
        Uri data = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {//判断版本大于等于7.0
            // 注意 下面参数com.ausee.fileprovider 为apk的包名加上.fileprovider，
            data = FileProvider.getUriForFile(context, "com.elink.fusion.fileprovider", new File(filePath));
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);// 给目标应用一个临时授权
        } else {
            data = Uri.fromFile(file);
        }
        intent.setDataAndType(data, "application/vnd.android.package-archive");
        context.startActivity(intent);
    }
}
