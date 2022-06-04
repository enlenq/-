package com.example.van.fragment;

import static android.content.Context.MODE_PRIVATE;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import com.example.van.R;


public class edit extends Fragment {
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.edit,container,false);

        //滚动条
        TextView showtxt= view.findViewById(R.id.showtxt);
        showtxt.setMovementMethod(new ScrollingMovementMethod());


        //加数据
        Button insert = (Button) view.findViewById(R.id.insert);
        insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText aa = getView().findViewById(R.id.shuru);
                EditText bb = getView().findViewById(R.id.shuru2);
                String word = aa.getText().toString().trim();
                String mean = bb.getText().toString().trim();
                if(word.equals("") || mean.equals(""))
                    Toast.makeText(getActivity(),"输入不能有空",Toast.LENGTH_SHORT).show();
                else{
                    String str = "insert or replace into word(word,mean) values ('"  + word  + "','"+mean+"');";
                    SQLiteDatabase db = getActivity().openOrCreateDatabase("danci.db",MODE_PRIVATE,null);
                    db.execSQL(str);
                    Toast.makeText(getActivity(),"已添加:"+word,Toast.LENGTH_SHORT).show();
                    db.close();
                }

            }
        });


        //删数据
        Button delete = (Button) view.findViewById(R.id.delete);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText aa = getView().findViewById(R.id.shuru);
                String word = aa.getText().toString().trim();
                if(word.equals("")){Toast.makeText(getActivity(),"需要输入单词",Toast.LENGTH_SHORT).show();}
                else{
                    String str = "delete from word where word= '"  + word + "';";
                    SQLiteDatabase db = getActivity().openOrCreateDatabase("danci.db",MODE_PRIVATE,null);
                    db.execSQL(str);
                    Toast.makeText(getActivity(),"已删除:"+word,Toast.LENGTH_SHORT).show();
                    db.close();
                }

            }
        });


        //show全部数据
        Button showall = (Button) view.findViewById(R.id.showall);
        showall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String str="";
                SQLiteDatabase db = getActivity().openOrCreateDatabase("danci.db",MODE_PRIVATE,null);
                Cursor queryResult = db.rawQuery("select * from word",null);

                if (queryResult != null) {
                    while (queryResult.moveToNext()) {
                        str+=(

                        queryResult.getString(queryResult.getColumnIndexOrThrow("word")) +" "+
                        queryResult.getString(queryResult.getColumnIndexOrThrow("mean")) +"\n"

                        );
                    }
                    // 关闭游标对象
                    queryResult.close();
                }
                else Toast.makeText(getActivity(),"数据库里没有单词",Toast.LENGTH_SHORT).show();
                db.close();
                TextView show = (TextView)getView().findViewById(R.id.showtxt);
                show.setText(str);
            }
        });

        return  view;
    }

}
