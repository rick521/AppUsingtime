package cn.xrick.applicationusetime;

import static cn.xrick.applicationusetime.Unit.DateTransUtils.milliseconds2hms;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.AppOpsManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import java.util.List;

import cn.xrick.applicationusetime.Unit.PackageInfo;
import cn.xrick.applicationusetime.Unit.UseTimeDataManager;

public class MainActivity extends AppCompatActivity {
    private UseTimeDataManager mUseTimeDataManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mUseTimeDataManager = UseTimeDataManager.getInstance(MainActivity.this);
        mUseTimeDataManager.refreshData(0,0);
        final List<PackageInfo> packageInfos = mUseTimeDataManager.getmPackageInfoListOrderByTime();
                for (int i = 0; i < packageInfos.size(); i++) {

                    Log.e("--->",i+packageInfos.get(i).getmPackageName()
                    +"      "
                    +packageInfos.get(i).getmUsedCount()+""
                    +"      "
                    +milliseconds2hms(packageInfos.get(i).getmUsedTime()));

                }
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.PACKAGE_USAGE_STATS)!= PackageManager.PERMISSION_GRANTED) {
            checkUsagePermission();
        }

    }

    private boolean checkUsagePermission() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
            AppOpsManager appOps = (AppOpsManager) getSystemService(Context.APP_OPS_SERVICE);
            int mode = 0;
            mode = appOps.checkOpNoThrow("android:get_usage_stats", android.os.Process.myUid(), getPackageName());
            boolean granted = mode == AppOpsManager.MODE_ALLOWED;
            if (!granted) {
                Intent intent = new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS);
                startActivityForResult(intent, 1);
                return false;
            }
        }
        return true;
    }

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            AppOpsManager appOps = (AppOpsManager) getSystemService(Context.APP_OPS_SERVICE);
            int mode = 0;
            mode = appOps.checkOpNoThrow("android:get_usage_stats", android.os.Process.myUid(), getPackageName());
            boolean granted = mode == AppOpsManager.MODE_ALLOWED;
            if (!granted) {
                Toast.makeText(this, "请开启该权限", Toast.LENGTH_SHORT).show();
            }
        }
    }


}