package com.etebarian.meowbottomnavigaion;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import com.etebarian.navigation.IBottomNavigationListener;
import com.etebarian.navigation.MeowBottomNavigation;
import com.etebarian.navigation.Model;

public class MainActivity extends AppCompatActivity {

    private static final int ID_HOME = 1;
    private static final int ID_EXPLORE = 2;
    private static final int ID_MESSAGE = 3;
    private static final int ID_NOTIFICATION = 4;
    private static final int ID_ACCOUNT = 5;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MeowBottomNavigation bottomNavigation = findViewById(R.id.bottomNavigation);
        final TextView tv_selected = findViewById(R.id.tv_selected);
        tv_selected.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/SourceSansPro-Regular.ttf"));

        bottomNavigation.add(new Model(ID_HOME, R.drawable.ic_home));
        bottomNavigation.add(new Model(ID_EXPLORE, R.drawable.ic_explore));
        bottomNavigation.add(new Model(ID_MESSAGE, R.drawable.ic_message));
        bottomNavigation.add(new Model(ID_NOTIFICATION, R.drawable.ic_notification));
        bottomNavigation.add(new Model(ID_ACCOUNT, R.drawable.ic_account));

        /*设置下角标*/
        bottomNavigation.setCount(ID_NOTIFICATION, "3");
        bottomNavigation.show(ID_HOME,true);
        bottomNavigation.setOnShowListener(new IBottomNavigationListener() {
            @Override
            public void onClicked(Model it) {
                String name = "";
                switch (it.getId()) {
                    case ID_HOME:
                        name = "HOME";
                    case ID_EXPLORE:
                        name = "EXPLORE";
                    case ID_MESSAGE:
                        name = "MESSAGE";
                    case ID_NOTIFICATION:
                        name = "NOTIFICATION";
                    case ID_ACCOUNT:
                        name = "ACCOUNT";
                }
                tv_selected.setText(name+" page is selected");
            }
        });

        bottomNavigation.setOnClickMenuListener(new IBottomNavigationListener() {
            @Override
            public void onClicked(Model it) {
                String name = "";
                switch (it.getId()) {
                    case ID_HOME:
                        name = "HOME";
                    case ID_EXPLORE:
                        name = "EXPLORE";
                    case ID_MESSAGE:
                        name = "MESSAGE";
                    case ID_NOTIFICATION:
                        name = "NOTIFICATION";
                    case ID_ACCOUNT:
                        name = "ACCOUNT";
                }
                tv_selected.setText(name+" page is selected");
                Toast.makeText(MainActivity.this, name+" is clicked", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
