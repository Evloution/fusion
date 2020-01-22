package com.elink.fusion.permission;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;

import androidx.appcompat.app.AlertDialog;

import java.util.ArrayList;

/**
 * @author Evloution_
 * @date 2018/12/5
 * @explain
 */
public class PermissionUtil {
    /**
     * 返回缺失的权限
     *
     * @param context
     * @param permissions
     * @return 返回缺少的权限，null 意味着没有缺少权限
     */
    public static String[] getDeniedPermissions(Context context, String[] permissions) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            ArrayList<String> deniedPermissionList = new ArrayList<>();
            for (String permission : permissions) {
                if (context.checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED) {
                    deniedPermissionList.add(permission);
                }
            }
            int size = deniedPermissionList.size();
            if (size > 0) {
                return deniedPermissionList.toArray(new String[deniedPermissionList.size()]);
            }
        }
        return null;
    }

    /**
     * 登陆界面申请的权限
     *
     * @param activity
     * @param text
     */
    public static void PermissionDialog(final Activity activity, String text, final int REQUEST) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setCancelable(false);
        builder.setMessage(text);
        builder.setNegativeButton("不了", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                activity.finish();
            }
        });
        builder.setPositiveButton("去设置", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                intent.setData(Uri.parse("package:" + activity.getPackageName()));
                // activity.startActivity(intent);
                activity.startActivityForResult(intent, REQUEST);
            }
        });
        builder.create().show();

        /*View view = LayoutInflater.from(activity).inflate(R.layout.undevice_popup_layout, null, false);
        final AlertDialog alertDialog = new AlertDialog.Builder(activity).setView(view).showProgress();
        TextView textInfo = view.findViewById(R.id.textview);
        final Button buttonOk = view.findViewById(R.id.btn_ok);
        Button buttonCancel = view.findViewById(R.id.btn_cancel);

        textInfo.setText(text);
        buttonOk.setText("去设置");
        buttonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoadingUtils.loadingDialogEnd(activity);
                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                intent.setData(Uri.parse("package:" + activity.getPackageName()));
                activity.startActivity(intent);
            }
        });

        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
                activity.finish();
            }
        });

        // 点击返回键和外部都不可取消
        alertDialog.setCancelable(false);
        alertDialog.showProgress();*/
    }


    /**
     * 权限的提示文字
     * 可以根据应用需求自行更改
     *
     * @param perms
     * @return
     */
    public static String permissionText(String[] perms) {
        StringBuilder sb = new StringBuilder();
        for (String s : perms) {
            if (s.equals(Manifest.permission.CAMERA)) {
                sb.append("相机权限(用于拍照);\n");
            } else if (s.equals(Manifest.permission.ACCESS_FINE_LOCATION)) {
                sb.append("位置权限(用于获取当前位置);\n");
            } else if (s.equals(Manifest.permission.READ_PHONE_STATE)){
                sb.append("手机权限(用于读取手机信息);\n");
            }else if (s.equals(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                sb.append("存储(用于存储必要信息，缓存数据);\n");
            }
        }
        return "程序运行需要如下权限：\n\n" + sb.toString();
    }
}
