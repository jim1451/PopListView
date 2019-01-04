package com.test;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

public class TestPopupWindowActivity extends Activity {
    
    private boolean isShow = false;
    private PopupWindow pop;
    private myAdapter adapter;
    private ListView listView;
    private List<String> names;
    
    private EditText etLoginName;
    private ImageButton moreSelectButton;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        setUpView();
        setUpListeners();
        names = new ArrayList<String>();
        names.add("张三");
        names.add("李四");
        names.add("王五");
    }
    
    public void setUpView(){
        etLoginName = (EditText)findViewById(R.id.login_edit_account); 
        moreSelectButton = (ImageButton)findViewById(R.id.ImageButton02);
    }
    
    public void setUpListeners(){
        moreSelectButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(pop==null){
                    if(adapter==null){
                        adapter=new myAdapter();
                        listView=new ListView(TestPopupWindowActivity.this);
                        pop=new PopupWindow(listView, etLoginName.getWidth(), LayoutParams.WRAP_CONTENT);
                        listView.setAdapter(adapter);
                        pop.showAsDropDown(etLoginName);
                        isShow = true;
                    }
                }
                else if(isShow){
                    pop.dismiss();
                    isShow = false;
                }else if(!isShow){
                    pop.showAsDropDown(etLoginName);
                    isShow = true;
                }
            }
        });
    }
    
    class myAdapter extends BaseAdapter {
        LayoutInflater mInflater;
        public myAdapter() {
            mInflater=LayoutInflater.from(TestPopupWindowActivity.this);
            // TODO Auto-generated constructor stub
        }

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return names.size();
        }

        @Override
        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            // TODO Auto-generated method stub
            Holder holder=null;
            final String name = names.get(position);
            if(convertView==null){
                convertView=mInflater.inflate(R.layout.popup, null);
                holder=new Holder();
                holder.view=(TextView)convertView.findViewById(R.id.mQQ);
                holder.button=(ImageButton)convertView.findViewById(R.id.mQQDelete);
                convertView.setTag(holder);
            }
            else{
                holder=(Holder) convertView.getTag();
            }
            if(holder!=null){
                convertView.setId(position);
                holder.setId(position);
                holder.view.setText(name);
                holder.view.setOnTouchListener(new OnTouchListener() {
                    
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        // TODO Auto-generated method stub
                        
                        pop.dismiss();
                        isShow = false;
                        etLoginName.setText(name);
                        return true;
                    }
                });
                
                holder.button.setOnClickListener(new OnClickListener() {
                    
                    @Override
                    public void onClick(View v) {
                        names.remove(position);
                        adapter.notifyDataSetChanged();
                    }
                });
            }
            return convertView;
        }
        
        class Holder{
            TextView view;
            ImageButton button;
            
            void setId(int position){
                view.setId(position);
                button.setId(position);
            }
        }

    }
}