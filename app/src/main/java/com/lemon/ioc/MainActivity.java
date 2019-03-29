package com.lemon.ioc;

import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.lemon.ioc.base.BaseActivity;
import com.lemon.ioc.library.InjectManager;
import com.lemon.ioc.library.annotations.ContentView;
import com.lemon.ioc.library.annotations.InjectView;
import com.lemon.ioc.library.annotations.OnClick;
import com.lemon.ioc.library.annotations.OnItemClick;
import com.lemon.ioc.library.annotations.OnItemLongClick;
import com.lemon.ioc.library.annotations.OnLongClick;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ContentView(R.layout.activity_main)
public class MainActivity extends AppCompatActivity {

    @InjectView(R.id.tv)
    TextView mTv;
    @InjectView(R.id.btn)
    Button mBtn;
    @InjectView(R.id.listview)
    ListView mListview;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //帮助所有的子类进行：布局、控件、事件的注入
        InjectManager.inject(this);
        final String[] name = {"赵大","钱二","孙三","李四","周五","吴六","郑七"};
        final String[] strings = {"11111111111","22222222222","33333333333","44444444444","55555555555","66666666666","77777777777"};
        final List<Map<String,Object>> listItems = new ArrayList<Map<String, Object>>();
        for (int i = 0 ; i < name.length ; i++){
            Map<String,Object> map = new HashMap<String, Object>();
            map.put("nameimage",null);
            map.put("name",name[i]);
            map.put("phonenum",strings[i]);
            listItems.add(map);
        }

        int[] To=new int[]{R.id.Layout_Name,R.id.Layout_Name1,R.id.Layout_Name2};
        SimpleAdapter adapter = new SimpleAdapter(this,listItems,R.layout.layout_list, new String[]{"nameimage","name","phonenum"}, To);
        mListview.setAdapter(adapter);
    }
    @Override
    protected void onResume() {
        super.onResume();
        mTv.setText("lemondeng");
        mBtn.setText("lemondeng");
    }

    @OnItemClick({R.id.listview})
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(this,"position=="+position,Toast.LENGTH_SHORT).show();
    }
    @OnItemLongClick({R.id.listview})
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(this,"longposition=="+position,Toast.LENGTH_SHORT).show();
        return false;
    }
    @OnClick({R.id.btn,R.id.tv})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.btn:
                Toast.makeText(this,"btn",Toast.LENGTH_SHORT).show();
                break;
            case R.id.tv:
                Toast.makeText(this,"tv",Toast.LENGTH_SHORT).show();
                break;
        }
    }
    @OnLongClick({R.id.btn,R.id.tv})
    public boolean onLongClick(View view){
        switch (view.getId()){
            case R.id.btn:
                Toast.makeText(this,"longbtn",Toast.LENGTH_SHORT).show();
                return true;
            case R.id.tv:
                Toast.makeText(this,"longtv",Toast.LENGTH_SHORT).show();
                return true;
        }
        return false;
    }
    //    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//    }
}
