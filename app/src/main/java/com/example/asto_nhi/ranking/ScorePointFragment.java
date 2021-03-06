package com.example.asto_nhi.ranking;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;

public class ScorePointFragment extends Fragment {
    PagerContainer mContainer;
    ViewPager pager;
    PagerAdapter adapter;
    ImageView leftNav,rightNav;
    ArrayList<ScoreModel> scoreModelList;

     public ScorePointFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        generateData();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_score_point, container, false);
        mContainer = (PagerContainer) view.findViewById(R.id.pager_container);
        pager = mContainer.getViewPager();
        CardShadowTransformer cardShadowTransformer = new CardShadowTransformer();

        pager.setPageTransformer(false, cardShadowTransformer);
        adapter = new MyPagerAdapter(getContext(),scoreModelList);
        pager.setAdapter(adapter);
        //Necessary or the pager will only have one extra page to show
        // make this at least however many pages you can see
        pager.setOffscreenPageLimit(adapter.getCount());
        //A little space between pages
        pager.setPageMargin(0);
        //If hardware acceleration is enabled, you should also remove
        // clipping on the pager for its children.
        pager.setClipChildren(false);

        //Button
        leftNav = (ImageView)view.findViewById(R.id.button_previous);
        rightNav = (ImageView) view.findViewById(R.id.button_next);

        // Images left navigation
        leftNav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int tab = pager.getCurrentItem();
                if (tab > 0) {
                    tab--;
                    pager.setCurrentItem(tab);
                } else if (tab == 0) {
                    pager.setCurrentItem(tab);
                }
            }
        });

        // Images right navigatin
        rightNav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int tab = pager.getCurrentItem();
                tab++;
                pager.setCurrentItem(tab);
            }
        });


        // view pager on change
        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            public void onPageScrollStateChanged(int state) {}
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}

            public void onPageSelected(int position) {
                // Hide button when user change pager
                if(position==0){
                    leftNav.setVisibility(View.INVISIBLE);
                    rightNav.setVisibility(View.VISIBLE);
                }
                if(position==1){
                    leftNav.setVisibility(View.VISIBLE);
                    rightNav.setVisibility(View.VISIBLE);
                }
                if(position==2){
                    leftNav.setVisibility(View.VISIBLE);
                    rightNav.setVisibility(View.INVISIBLE);
                }

            }
        });
        // Inflate the layout for this fragment
        return view;
    }


    private void generateData(){
        scoreModelList = new ArrayList<>();
        ScoreModel scoreModel1 = new ScoreModel("01","67","23","92");
        scoreModelList.add(scoreModel1);
        ScoreModel scoreModel2 = new ScoreModel("02","27","33","93");
        scoreModelList.add(scoreModel2);
        ScoreModel scoreModel3 = new ScoreModel("03","78","65","02");
        scoreModelList.add(scoreModel3);
    }
}
