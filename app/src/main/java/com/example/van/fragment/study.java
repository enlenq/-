package com.example.van.fragment;

import static android.content.Context.MODE_PRIVATE;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Random;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.van.R;

public class study extends Fragment{


    RadioButton A;
    RadioButton B;
    RadioButton C;
    RadioButton D;
    TextView timu;
    String select = new String();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.study,container,false);
        timu =  view.findViewById(R.id.timu);
        A = view.findViewById(R.id.radioButton1);
        B = view.findViewById(R.id.radioButton2);
        C = view.findViewById(R.id.radioButton3);
        D = view.findViewById(R.id.radioButton4);

        String a[] = getword();
        if(a==null){
            Toast.makeText(getActivity(),"单词库存不足",Toast.LENGTH_SHORT).show();
        }
        else {
            final String[] xuanxiang = {shuati()};

            Button confirm = (Button) view.findViewById(R.id.confirm);
            confirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    RadioGroup radioGroup = getView().findViewById(R.id.radioGroup);
                    RadioButton radiobutton = getView().findViewById(radioGroup.getCheckedRadioButtonId());
                    select = radiobutton.getText().toString();
//                Toast.makeText(getActivity(),xuanxiang[0],Toast.LENGTH_SHORT).show();
//                Toast.makeText(getActivity(),select,Toast.LENGTH_SHORT).show();
                    if (select.equals(xuanxiang[0])) {
                        Toast.makeText(getActivity(), "回答正确", Toast.LENGTH_SHORT).show();
                    } else {
                        wrongadd();
                        Toast.makeText(getActivity(), "回答错误", Toast.LENGTH_SHORT).show();
                    }
                    //Toast.makeText(getActivity(),select,Toast.LENGTH_SHORT).show();
                    xuanxiang[0] = shuati();
                }
            });
        }


        return view;
    }

    //刷题
    //生成题目,选项,返回答案
    public String shuati(){
        String word[] = getword();
        if(word!=null){
        Random r=new Random();
        StringBuilder aa= new StringBuilder(new String());//记录4个选项的序号
        int[] a=new int[4];//记录4个选项的序号
        int b=1;
        //生成4个随机数并加载在radiobutton
        for(int i=0;i<4;i++){
            //System.out.println(a.nextInt(c));
            int c=r.nextInt(word.length);
            while(aa.toString().contains(String.valueOf(c))){
                c=r.nextInt(word.length);
            }

            a[i]=Integer.valueOf(c);
            switch(b++){
                case 1:
                    A.setText(word[a[i]].substring(word[a[i]].indexOf(",")+1));
                    break;
                case 2:
                    B.setText(word[a[i]].substring(word[a[i]].indexOf(",")+1));
                    break;
                case 3:
                    C.setText(word[a[i]].substring(word[a[i]].indexOf(",")+1));
                    break;
                case 4:
                    D.setText(word[a[i]].substring(word[a[i]].indexOf(",")+1));
                    break;
            }

            aa.append(c);
            aa.append(" ");
        }

        //随机选一个答案生成题目
        int res=r.nextInt(4);
        timu.setText(word[a[res]].substring(0,word[a[res]].indexOf(",")));

//        RadioButton radiobutton=getActivity().findViewById(R.id.radioButton1);
//        Toast.makeText(getActivity(),radiobutton.getText().toString(),Toast.LENGTH_SHORT).show();
//        radiobutton.setText(word[i].substring(word[i].indexOf(","),word[i].length()));

//        中文 word[i].substring(word[i].indexOf(",")+1,word[i].length())
//        英文 word[i].substring(0,word[i].indexOf(","))

        return word[a[res]].substring(word[a[res]].indexOf(",")+1);

        }
        return null;
    }

    //把做错的单词记到错题表里
    public void wrongadd(){
        String word = timu.getText().toString();
        String wrong1 =new String();
        String wrong2 =new String();
        SQLiteDatabase db = getActivity().openOrCreateDatabase("danci.db",MODE_PRIVATE,null);
        Cursor queryResult = db.rawQuery("select * from word WHERE word='"+word+"';",null);
        if (queryResult != null) {
            while (queryResult.moveToNext()) {
                 wrong1=queryResult.getString(queryResult.getColumnIndexOrThrow("word"));
                 wrong2=queryResult.getString(queryResult.getColumnIndexOrThrow("mean"));
            }
            // 关闭游标对象
            queryResult.close();
        }
        else Toast.makeText(getActivity(),"数据库里没有单词",Toast.LENGTH_SHORT).show();
        word = "insert or replace into wrong(word,mean) values ('"  + wrong1  + "','"+wrong2+"');";
        db.execSQL(word);
        db.close();

    }

    //取出所有数
    public String[] getword(){
        String str="";
        SQLiteDatabase db = getActivity().openOrCreateDatabase("danci.db",MODE_PRIVATE,null);
        Cursor queryResult = db.rawQuery("select * from word",null);
        if (queryResult != null) {
            while (queryResult.moveToNext()) {
                str+=(
                        queryResult.getString(queryResult.getColumnIndexOrThrow("word")) +","+
                        queryResult.getString(queryResult.getColumnIndexOrThrow("mean")) +"\n"

                );
            }
            // 关闭游标对象
            queryResult.close();
        }
        else Toast.makeText(getActivity(),"数据库里没有单词",Toast.LENGTH_SHORT).show();
        db.close();
        return str.split("\n");
    }
}
