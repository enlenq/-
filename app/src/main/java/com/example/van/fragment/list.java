package com.example.van.fragment;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TabHost;

import androidx.fragment.app.Fragment;

import com.example.van.R;
import com.example.van.Word;
import com.example.van.wordAdapter;
import com.example.van.wrongAdapter;
import com.example.van.wrongword;

import java.util.ArrayList;
import java.util.List;


public class list extends Fragment {

    private final List<Word> wordList = new ArrayList<>();
    private final List<wrongword> wordList2 = new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.list,container,false);

        initWord();
        initWord2();
        //加载单词库
        wordAdapter arr=new wordAdapter (getActivity(), R.layout.wordlist, wordList);
        ListView listView =  view.findViewById(R.id.danci_view);
        listView.setAdapter(arr);

        //加载错词库
        wrongAdapter arr2=new wrongAdapter(getActivity(), R.layout.wronglist, wordList2);
        ListView listView2 =  view.findViewById(R.id.wrong_view);
        listView2.setAdapter(arr2);


        TabHost tab = (TabHost) view.findViewById(android.R.id.tabhost);
        //初始化TabHost容器
        tab.setup();
        //在TabHost创建标签，然后设置：标题／图标／标签页布局
        tab.addTab(tab.newTabSpec("tab1").setIndicator("单词库" , null).setContent(R.id.tab1));
        tab.addTab(tab.newTabSpec("tab2").setIndicator("错词库" , null).setContent(R.id.tab2));

        return view;
    }

    public void initWord(){
        String word="";
        String mean="";
        SQLiteDatabase db = getActivity().openOrCreateDatabase("danci.db", Context.MODE_PRIVATE,null);
        Cursor queryResult = db.rawQuery("select * from word",null);

        if (queryResult != null) {
            while (queryResult.moveToNext()) {
                word = queryResult.getString(queryResult.getColumnIndexOrThrow("word"));
                mean = queryResult.getString(queryResult.getColumnIndexOrThrow("mean"));

                Word word1 = new Word(word,mean);
                wordList.add(word1);

            }
            // 关闭游标对象
            queryResult.close();
        }
        db.close();

    }

    public void initWord2(){
        String word="";
        String mean="";
        SQLiteDatabase db = getActivity().openOrCreateDatabase("danci.db", Context.MODE_PRIVATE,null);
        Cursor queryResult = db.rawQuery("select * from wrong",null);

        if (queryResult != null) {
            while (queryResult.moveToNext()) {
                word = queryResult.getString(queryResult.getColumnIndexOrThrow("word"));
                mean = queryResult.getString(queryResult.getColumnIndexOrThrow("mean"));


                wrongword word1 = new wrongword(word,mean,R.id.delete);

                wordList2.add(word1);

            }
            // 关闭游标对象
            queryResult.close();
        }
        db.close();

    }

}
