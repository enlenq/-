package com.example.van.fragment;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.van.MainActivity;
import com.example.van.R;
import com.example.van.register;

public class login extends Fragment{
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.login,container,false);
        EditText username=view.findViewById(R.id.username);
        EditText password=view.findViewById(R.id.password);


//        TextView forget= view.findViewById(R.id.forget);
//        forget.getPaint().setFlags( Paint.UNDERLINE_TEXT_FLAG );
//        forget.getPaint().setAntiAlias(true);//抗锯齿
//        forget.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(getActivity(),"好似,开香槟咯",Toast.LENGTH_SHORT).show();
//            }
//        });

        TextView zhuce= view.findViewById(R.id.zhuce);
        zhuce.getPaint().setFlags( Paint.UNDERLINE_TEXT_FLAG );
        zhuce.getPaint().setAntiAlias(true);//抗锯齿
        zhuce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(getActivity(),"敬请期待",Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(getActivity(), register.class);
                startActivity(intent);
            }
        });

        Button login=view.findViewById(R.id.login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Name = username.getText().toString();
                String Pass = password.getText().toString();
                if(Name.equals(""))
                    Toast.makeText(getActivity(),"请输入用户名",Toast.LENGTH_SHORT).show();
                if(Pass.equals(""))
                    Toast.makeText(getActivity(),"请输入密码",Toast.LENGTH_SHORT).show();

                SQLiteDatabase db = getActivity().openOrCreateDatabase("danci.db",getContext().MODE_PRIVATE,null);
                Cursor cursor = db.rawQuery("select * from user WHERE username = '"+ Name + "';",null);
                if(cursor!=null){
                    while (cursor.moveToNext()) {
                        String str =  cursor.getString(cursor.getColumnIndexOrThrow("password"));
                        if(Pass.equals(str)){
                            Toast.makeText(getActivity(),"登录成功",Toast.LENGTH_SHORT).show();
                            return;
                        }
                        else{
                            Toast.makeText(getActivity(),"密码不正确",Toast.LENGTH_SHORT).show();
                            return ;
                        }
                    }
                    Toast.makeText(getActivity(),"用户名不存在",Toast.LENGTH_SHORT).show();
                }
                else Toast.makeText(getActivity(),"用户名不存在",Toast.LENGTH_SHORT).show();
                cursor.close();
                db.close();
            }
        });



        return view;
    }
}
