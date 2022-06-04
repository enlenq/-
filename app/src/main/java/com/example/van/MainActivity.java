package com.example.van;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.view.LayoutInflaterCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.van.fragment.login;
import com.google.android.material.bottomnavigation.BottomNavigationView;


public class MainActivity extends AppCompatActivity {

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

        //创建
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 获取页面上的底部导航栏控件
        BottomNavigationView navView = findViewById(R.id.nav_view);

        // 配置navigation与底部菜单之间的联系
        // 底部菜单的样式里面的item里面的ID与navigation布局里面指定的ID必须相同，否则会出现绑定失败的情况
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.ZJYC,R.id.study,R.id.edit,R.id.list,R.id.login).build();
        // 建立fragment容器的控制器，这个容器就是页面的上的fragment容器
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);

        // 启动
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);

        //数据库相关
        openorcreate();
        init();


    }



    //创建/打开数据库
    public void openorcreate(){
        //创建文件
        SQLiteDatabase db = openOrCreateDatabase("danci.db",MODE_PRIVATE,null);
        // 创建表
        String str =
        "create table if not exists word (id integer primary key,word text not null UNIQUE,mean text not null);";
        String str2 =
        "create table if not exists wrong (id integer primary key,word text not null UNIQUE,mean text not null);";
        String str3 =
        "create table if not exists user (id integer primary key,username text not null UNIQUE,password text not null);";
        String str3_0 =
        "insert or replace into user(username,password) values ('admin','admin');";
        db.execSQL(str);
        db.execSQL(str2);
        db.execSQL(str3);
        db.execSQL(str3_0);
        db.close();
    }

    public void init() {
          String[][] a =new String[][]{
                  {"adhere", "坚守于"}, {"abnormal", "反常的"}, {"monopoly", "专利"}, {"vent", "表达"},
                  {"litter", "垃圾"}, {"terror", "恐怖"}, {"transfer", "调任"}, {"scare", "惊吓"},
                  {"revive", "恢复精神"}, {"shiver", "颤动"}, {"lessen", "变小"}, {"identical", "相同的"},
                  {"plausible", "似可信的"},{"perspective", "透视的"}, {"accelerate", "提前"},
                  {"adjacent", "邻近的"}, {"anxiety", "担心"}, {"capsule", "简缩"}, {"enrich", "使肥沃"},
                  {"differentiate", "区别"}, {"alternate", "交替"}, {"qualification", "条件"}, {"bounce", "弹跳"},
                  {"encounter", "偶然碰到"}, {"dilute", "稀释的"}, {"reinforce", "得到加强"}, {"advantage", "有利条件"},
                  {"consumption", "消耗"}, {"calculate", "计算"}, {"mature", "成熟的"}, {"virtual", "虚拟的"},
                  {"adapt", "使适应"}, {"innovation", "创新"}, {"transit", "经过"}, {"compromise", "妥协"},
                  {"thesis", "毕业论文"}, {"eliminate ", "除去"}, {"lean", "倾斜倚屈身"}, {"heighten", "增加"},
                  {"drawback", "缺点"}, {"doom", "命运"}, {"choke", "窒息"}, {"uphold", "支撑"}, {"combination", "结合"},
                  {"occupation ", "职业"}, {"rupture", "破裂"}, {"casual", "偶然的"}, {"precise", "精确的"},
                  {"insulate", "使...绝缘"},{"occasional", "偶然的"}, {"bankrupt", "贫穷的"}, {"integral", "构成整体所必需的"},
                  {"outbreak", "爆发"}, {"subsidy", "补助金"}, {"occupy", "占领"}, {"skeptical", "怀疑的"},
                  {"approach", "途径"}, {"memorial", "纪念的"}, {"afflict", "折磨"}, {"ignorant", "无知的"},
                  {"exert", "施加"}, {"idle", "无目的的"}, {"overcome", "战胜"}, {"stable", "马厩"},
                  {"toxic", "有毒物质"}, {"expose", "使暴露"}, {"vigorous", "精力充沛的"}, {"challenge", "..挑战"},
                  {"haunt", "徘徊"}, {"designate", "指定的"}, {"fancy", "想像的"}, {"donate", "捐赠"},
                  {"slash", "猛砍"}, {"remedy", "治疗"}, {"furnish", "装备"}, {"cheerful", "快乐的"},
                  {"conserve", "保存"}, {"economical", "经济的"}, {"drain", "排出"}, {"murmur", "低语"},
                  {"violate", "亵渎"}, {"mall", "商业街"}, {"forerunner", "预兆"}, {"foresee", "预知"},
                  {"esteem", "尊敬"}, {"furthermore", "此外"}, {"explicit", "明确的"}, {"betray", "误导"},
                  {"radioactive", "放射性的"}, {"resort", "手段"},
          };

        SQLiteDatabase db = openOrCreateDatabase("danci.db",MODE_PRIVATE,null);
        for(int i=0 ;i<a.length;i++){
            String str=
        "insert or replace into word(word,mean) values ('"  + a[i][0]  + "','"+ a[i][1] +"');";
            db.execSQL(str);
        }
    }

}