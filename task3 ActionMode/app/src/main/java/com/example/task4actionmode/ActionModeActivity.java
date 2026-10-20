package com.example.task4actionmode;

import android.os.Bundle;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ActionModeActivity extends AppCompatActivity {

    private ListView listView;
    private List<String> dataList;
    private MyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_action_mode); // 确保加载了正确的布局

        listView = findViewById(R.id.list_view_action);
        // 初始化数据
        dataList = new ArrayList<>(Arrays.asList("One", "Two", "Three", "Four", "Five"));
        adapter = new MyAdapter();
        listView.setAdapter(adapter);

        // 允许列表单选（为了配合高亮显示）
        listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

        // 设置长按事件，触发 ActionMode
        listView.setOnItemLongClickListener((parent, view, position, id) -> {
            // 设置当前项为选中状态
            listView.setItemChecked(position, true);
            // 启动 ActionMode
            startActionMode(new ActionModeCallback(position));
            return true;
        });
    }

    // ================= ActionMode 的回调机制 =================
    private class ActionModeCallback implements ActionMode.Callback {
        private int selectedPosition;

        public ActionModeCallback(int position) {
            this.selectedPosition = position;
        }

        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            // 加载菜单
            mode.getMenuInflater().inflate(R.menu.menu_action_mode, menu);
            // 设置顶部标题
            mode.setTitle("1 selected");
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            if (item.getItemId() == R.id.action_delete) {
                // 点击删除
                dataList.remove(selectedPosition);
                adapter.notifyDataSetChanged(); // 刷新列表
                Toast.makeText(ActionModeActivity.this, "已删除", Toast.LENGTH_SHORT).show();
                mode.finish(); // 关闭 ActionMode
                return true;
            }
            return false;
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {
            // 退出 ActionMode 时，清除选中状态
            listView.clearChoices();
            listView.requestLayout();
        }
    }

    // ================= 自定义 Adapter =================
    private class MyAdapter extends BaseAdapter {
        @Override
        public int getCount() { return dataList.size(); }

        @Override
        public Object getItem(int position) { return dataList.get(position); }

        @Override
        public long getItemId(int position) { return position; }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = getLayoutInflater().inflate(R.layout.item_action_mode, parent, false);
            }
            TextView tv = convertView.findViewById(R.id.tv_item_name);
            tv.setText(dataList.get(position));
            return convertView;
        }
    }
}