package com.example.van;

import static android.content.Context.MODE_PRIVATE;

import android.database.sqlite.SQLiteDatabase;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;


public class wrongAdapter extends BaseAdapter {
    private Context context;//创建一个上下文对象
    private List<wrongword> datas;//创建一个List数组，里面存放Animal对象，用来接收MainActivity传过来的数据

    public wrongAdapter(Context context, int wronglist, List<wrongword> datas) {
        this.context = context;
        this.datas = datas;
    }

    @Override
    public int getCount() {
        //获取数据的长度
        return datas.size();
    }

    @Override
    public Object getItem(int i) {
        //适配器放入了很多条数据，获取数据所在的位置
        return datas.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        wrongword anm = (wrongword) getItem(i);//实例化给定位置上的对象
        View v;//创建视图v，这是是用来返回给ListView的，作为ListView的子视图
        ViewHold viewHold;//创建临时的储存器ViewHold，它的作用是把你getView方法中每次返回的View存起来，可以下次再用
        if (view == null){
            v = LayoutInflater.from(context).inflate(R.layout.wronglist,null);
            //将adapter_view视图作为子视图放入v中
            viewHold = new ViewHold();

            //绑定id，建立与wronglist视图的连接
            viewHold.buttonId = v.findViewById(R.id.delete);
            viewHold.name = v.findViewById(R.id.word);
            viewHold.mean = v.findViewById(R.id.mean);

            v.setTag(viewHold);//储器中的视图设置到v中
        }else{
            v = view;
            viewHold = (ViewHold)v.getTag();
        }
        //将制定位置上的数据显示到空间中
        viewHold.name.setText(anm.getname());
        viewHold.mean.setText(anm.getmean());
        viewHold.buttonId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Log.v("MyListViewBase", "你点击了按钮" + i);

                String str = "delete from wrong where word= '"  + anm.getname() + "';";
                SQLiteDatabase db = context.openOrCreateDatabase("danci.db",MODE_PRIVATE,null);
                db.execSQL(str);
                Toast.makeText(context,"已删除:"+anm.getname()+"\n请刷新页面",Toast.LENGTH_SHORT).show();
                db.close();

            }
        });

        return v;
    }

    class ViewHold{
        Button buttonId;
        TextView name;
        TextView mean;
    }
}
