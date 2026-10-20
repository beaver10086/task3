package com.example.myapplication;

import android.app.AlertDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    String[] names = {"Lion", "Tiger", "Monkey", "Dog", "Cat", "Elephant"};
    int[] images = {R.drawable.lion, R.drawable.tiger, R.drawable.monkey,
            R.drawable.dog, R.drawable.cat, R.drawable.elephant};

    private TextView tvTest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 找到测试文本
        tvTest = findViewById(R.id.tv_test);

        // ================= 实验二：测试对话框按钮 =================
        Button btnShowDialog = findViewById(R.id.btn_show_dialog);
        btnShowDialog.setOnClickListener(v -> showCustomDialog());

        // ================= 实验三：右下角悬浮菜单按钮 =================
        // ================= 实验三：右下角悬浮菜单按钮 =================
        FloatingActionButton fabMenu = findViewById(R.id.fab_menu);
        fabMenu.setOnClickListener(v -> {
            // 使用 PopupMenu 替代系统的 openOptionsMenu，防止菜单位置错乱
            android.widget.PopupMenu popupMenu = new android.widget.PopupMenu(MainActivity.this, v);
            popupMenu.getMenuInflater().inflate(R.menu.menu_main, popupMenu.getMenu());

            popupMenu.setOnMenuItemClickListener(item -> {
                int id = item.getItemId();
                if (id == R.id.font_small) {
                    tvTest.setTextSize(TypedValue.COMPLEX_UNIT_SP, 10);
                    Toast.makeText(MainActivity.this, "字体已设为小号", Toast.LENGTH_SHORT).show();
                } else if (id == R.id.font_medium) {
                    tvTest.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
                    Toast.makeText(MainActivity.this, "字体已设为中号", Toast.LENGTH_SHORT).show();
                } else if (id == R.id.font_large) {
                    tvTest.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
                    Toast.makeText(MainActivity.this, "字体已设为大号", Toast.LENGTH_SHORT).show();
                } else if (id == R.id.menu_normal) {
                    Toast.makeText(MainActivity.this, "点击了普通菜单项", Toast.LENGTH_SHORT).show();
                } else if (id == R.id.color_red) {
                    tvTest.setTextColor(Color.RED);
                    Toast.makeText(MainActivity.this, "字体颜色已设为红色", Toast.LENGTH_SHORT).show();
                } else if (id == R.id.color_black) {
                    tvTest.setTextColor(Color.BLACK);
                    Toast.makeText(MainActivity.this, "字体颜色已设为黑色", Toast.LENGTH_SHORT).show();
                }
                return true;
            });
            // 显示菜单
            popupMenu.show();
        });

        // ================= 实验一：ListView 列表与通知 =================
        List<Map<String, Object>> listData = new ArrayList<>();
        for (int i = 0; i < names.length; i++) {
            Map<String, Object> map = new HashMap<>();
            map.put("name", names[i]);
            map.put("image", images[i]);
            listData.add(map);
        }

        SimpleAdapter adapter = new SimpleAdapter(
                this, listData, R.layout.item_list,
                new String[]{"name", "image"},
                new int[]{R.id.tv_name, R.id.iv_img}
        );

        ListView listView = findViewById(R.id.list_view);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener((parent, view, position, id) -> {
            String selectedName = names[position];
            Toast.makeText(MainActivity.this, "选中了: " + selectedName, Toast.LENGTH_SHORT).show();
            sendNotification(selectedName);
        });

        // 动态申请通知权限 (针对 Android 13 及以上)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (checkSelfPermission(android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{android.Manifest.permission.POST_NOTIFICATIONS}, 101);
            }
        }
    } // <--- 这里非常关键！必须要有这个大括号结束 onCreate

    // ================= 实验三：创建 XML 菜单 =================
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    // ================= 实验三：处理菜单点击事件 =================
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.font_small) {
            tvTest.setTextSize(TypedValue.COMPLEX_UNIT_SP, 10);
            Toast.makeText(this, "字体已设为小号", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.font_medium) {
            tvTest.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
            Toast.makeText(this, "字体已设为中号", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.font_large) {
            tvTest.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
            Toast.makeText(this, "字体已设为大号", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.menu_normal) {
            Toast.makeText(this, "点击了普通菜单项", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.color_red) {
            tvTest.setTextColor(Color.RED);
            Toast.makeText(this, "字体颜色已设为红色", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.color_black) {
            tvTest.setTextColor(Color.BLACK);
            Toast.makeText(this, "字体颜色已设为黑色", Toast.LENGTH_SHORT).show();
        }

        return super.onOptionsItemSelected(item);
    }

    // ================= 实验二：显示自定义弹窗 =================
    private void showCustomDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        android.view.View dialogView = getLayoutInflater().inflate(R.layout.dialog_custom, null);

        builder.setView(dialogView)
                .setPositiveButton("Sign in", (dialog, which) -> {
                    EditText etUser = dialogView.findViewById(R.id.et_username);
                    String username = etUser.getText().toString();
                    Toast.makeText(this, "尝试登录: " + username, Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("Cancel", (dialog, which) -> {
                    dialog.dismiss();
                });

        builder.create().show();
    }

    // ================= 实验一：发送通知 =================
    private void sendNotification(String title) {
        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        String channelId = "my_channel";

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelId, "测试通知", NotificationManager.IMPORTANCE_DEFAULT);
            manager.createNotificationChannel(channel);
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, channelId)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(title)
                .setContentText("这是一条来自 " + title + " 的通知")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        manager.notify(1, builder.build());
    }
}