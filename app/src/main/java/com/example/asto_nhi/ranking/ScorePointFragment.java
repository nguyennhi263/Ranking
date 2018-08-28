package com.example.asto_nhi.ranking;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ScorePointFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ScorePointFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ScorePointFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    PagerContainer mContainer;
    ViewPager pager;
    PagerAdapter adapter;
    ImageView leftNav,rightNav;
    ArrayList<ScoreModel> scoreModelList;

     public ScorePointFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ScorePointFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ScorePointFragment newInstance(String param1, String param2) {
        ScorePointFragment fragment = new ScorePointFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
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
