package com.example.van;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.view.LayoutInflaterCompat;


public class register extends AppCompatActivity {

    public static Typeface typeface;
    public static Typeface typeface1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //先设置一下字体
        if (typeface == null)  {
            typeface = Typeface.createFromAsset(getAssets(), "font/ark-pixel-12px-zh_cn.ttf");
        }
        if (typeface1 == null)  {
            typeface1 = Typeface.createFromAsset(getAssets(), "font/ark-pixel-12px-zh_cn.ttf");
        }
        LayoutInflaterCompat.setFactory(LayoutInflater.from(this), (parent, name, context, attrs) -> {
            AppCompatDelegate delegate = getDelegate();
            View view = delegate.createView(parent, name, context, attrs);

            if ( view!= null && (view instanceof TextView))   {
                ((TextView) view).setTypeface(typeface1);
            }
            if( view!=null && (view instanceof EditText)) {
                ((EditText) view).setTypeface(typeface);
            }
            if( view!=null && (view instanceof EditText)) {
                ((EditText) view).setTypeface(typeface);
            }
            return view;
        });

        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        //Toast.makeText(this,"dsagdsfsd",Toast.LENGTH_SHORT).show();



    }



    public void regist(View view){
        EditText name = findViewById(R.id.name);
        EditText pass1 = findViewById(R.id.Password);
        EditText pass2 = findViewById(R.id.Password2);
        String Name = name.getText().toString();
        String Pass1 = pass1.getText().toString();
        String Pass2 = pass2.getText().toString();

        if(!Pass1.equals(Pass2)){
            Toast.makeText(this,"两次密码不一致",Toast.LENGTH_SHORT).show();
            return;
        }

        SQLiteDatabase db = openOrCreateDatabase("danci.db",MODE_PRIVATE,null);

        Cursor cursor = db.rawQuery("select * from user WHERE username='" + Name + "' ;",null);
        if(cursor!=null){
            while (cursor.moveToNext()) {
                String str = cursor.getString(cursor.getColumnIndexOrThrow("username"));;
                if(Name.equals(str)){
                    Toast.makeText(this,"用户名已存在",Toast.LENGTH_SHORT).show();
                    return;
                }
                else break;
            }

        }

        String str="insert or replace into user(username,password) values ('"  + Name  + "','"+ Pass1 +"');";
        db.execSQL(str);
        db.close();
        Toast.makeText(this,"注册成功",Toast.LENGTH_SHORT).show();

        Intent intent=new Intent(register.this, MainActivity.class);
        //intent.putExtra("id",4);
        startActivity(intent);
    }
}