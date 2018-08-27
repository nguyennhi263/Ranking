package com.example.asto_nhi.ranking;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class MyPagerAdapter extends PagerAdapter {

    Context mContext;
    LayoutInflater mLayoutInflater;
    ArrayList<ScoreModel> mScoreModelList;

    public MyPagerAdapter(Context context, ArrayList<ScoreModel> scoreModelList) {
        mContext = context;
        mScoreModelList=scoreModelList;
        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        return mScoreModelList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((FrameLayout) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View itemView = mLayoutInflater.inflate(R.layout.one_score_table, container, false);

        TextView textView1 = (TextView) itemView.findViewById(R.id.textView1);
        ScoreModel scoreModel = mScoreModelList.get(position);

        textView1.setText(scoreModel.getValue1());


        //
        if(position%2==0){
           // LinearLayout linearLayout = (LinearLayout) itemView.findViewById(R.id.one_score_table);
           // linearLayout.setBackgroundColor(Color.GRAY);
        }
        else {
          //  LinearLayout linearLayout = (LinearLayout) itemView.findViewById(R.id.one_score_table);
           // linearLayout.setBackgroundColor(Color.GREEN);
        }
        container.addView(itemView);
        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout) object);
    }


}


