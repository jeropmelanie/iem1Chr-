package com.jeropmelanie.iem1chr.ui;

import android.os.Bundle;
import android.widget.GridView;
import android.widget.ArrayAdapter;
import androidx.appcompat.app.AppCompatActivity;
import com.jeropmelanie.iem1chr.R;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import java.util.ArrayList;
import java.util.List;

public class AppDrawerActivity extends AppCompatActivity {
    private GridView appGrid;
    private PackageManager packageManager;
    private List<String> appList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_drawer);

        appGrid = findViewById(R.id.app_grid);
        packageManager = getPackageManager();
        appList = new ArrayList<>();

        loadInstalledApps();
    }

    private void loadInstalledApps() {
        List<ApplicationInfo> packages = packageManager.getInstalledApplications(
            PackageManager.GET_META_DATA);

        appList.clear();
        for (ApplicationInfo app : packages) {
            if ((app.flags & ApplicationInfo.FLAG_SYSTEM) == 0) {
                appList.add(app.packageName);
            }
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
            android.R.layout.simple_list_item_1, appList);
        appGrid.setAdapter(adapter);
    }
}
