package com.example.task3recyclerview; // 保留你新项目的第一行包名

import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private MyAdapter adapter;
    private List<String> dataList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recycler_view);

        // 初始数据
        dataList = new ArrayList<>(Arrays.asList("Lion", "Tiger", "Monkey", "Dog", "Cat", "Elephant"));

        // 设置布局管理器（垂直列表）
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // 实例化 Adapter，并在内部处理点击事件
        adapter = new MyAdapter(dataList, position -> {
            // 点击时，获取点击的名字，并拼接一个 "New " 加进去
            String clickedName = dataList.get(position);
            dataList.add("New " + clickedName);

            // ⚠️ 关键点：必须用 notifyItemInserted 而不是 notifyDataSetChanged，这样列表才会有插入动画且不会错位！
            adapter.notifyItemInserted(dataList.size() - 1);

            // 自动滚动到刚添加的那一项
            recyclerView.smoothScrollToPosition(dataList.size() - 1);

            Toast.makeText(this, "已添加: New " + clickedName, Toast.LENGTH_SHORT).show();
        });

        recyclerView.setAdapter(adapter);
    }
}