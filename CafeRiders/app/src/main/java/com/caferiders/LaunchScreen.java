package com.caferiders;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class LaunchScreen extends AppCompatActivity implements ViewPager.OnPageChangeListener ,View.OnClickListener{

    private static  int NUM_PAGES = 0;
    private ViewPager pager_introduction;
    private LinearLayout pager_indicator;
    private int[] images = {R.drawable.introduction_image_1,R.drawable.introduction_image_2,R.drawable.introduction_image_3};
    Animation animation;
    private Timer timer;
    private ArrayList<Integer> ImagesArray = new ArrayList<Integer>();
    private int dotsCount;
    private boolean continue_animation = true;
    private ImageView[] dots;
    private int currentPage = 0;
    private int currentimageindex = 0;
    private LinearLayout info_linear;
    private Button lets_go;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch_screen);
        init();
        initData();

    }

    public void init() {
        pager_introduction = (ViewPager)findViewById(R.id.pager_introduction);
        pager_introduction.setOnPageChangeListener(this);
        pager_indicator = (LinearLayout)findViewById(R.id.viewPagerCountDots);
        info_linear = (LinearLayout)findViewById(R.id.info_linear);
        lets_go = (Button)findViewById(R.id.lets_go);
        lets_go.setOnClickListener(this);
    }

    public void initData() {
        NUM_PAGES = images.length - 1;

        AnimationSet set = new AnimationSet(true);
        animation = new AlphaAnimation(0.0f, 1.0f);
        animation.setDuration(500);
        set.addAnimation(animation);
        final Runnable mUpdateResults = new Runnable() {
            public void run() {

                AnimateandSlideShow();

            }
        };
        final Handler mHandler = new Handler();

        int delay = 000; // delay for 1 sec.

        int period = 3000; // repeat every 4 sec.

        timer = new Timer();

        timer.scheduleAtFixedRate(new TimerTask() {

            public void run() {

                mHandler.post(mUpdateResults);

            }
        }, delay, period);

        animation = new TranslateAnimation(
                Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, -1.0f, Animation.RELATIVE_TO_SELF, 0.0f
        );

        animation.setDuration(900);
        set.addAnimation(animation);
        setUiPageViewController();
        for (int i = 0; i < images.length; i++)
            ImagesArray.add(images[i]);

        pager_introduction.setAdapter(new SlidingImage_Adapter(LaunchScreen.this, ImagesArray));

        // Auto start of viewpager
        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            public void run() {
                        System.out.println("Handler " + currentPage + " continue_animation " + continue_animation);
                if(continue_animation) {
                    pager_introduction.setCurrentItem(currentPage++, true);
                    if (currentPage == NUM_PAGES + 1) {
                        currentPage = 0;
                        continue_animation = false;

                    }
                }

            }
        };
        Timer swipeTimer = new Timer();
        swipeTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                if(continue_animation) {
                    System.out.println("Handler swipeTimer " + " continue_animation " + continue_animation + " currentPage " + currentPage);
                    handler.post(Update);
                }


            }
        }, delay, period);
    }

    private void setUiPageViewController() {

        dotsCount = images.length;
        dots = new ImageView[dotsCount];
        for (int i = 0; i < dotsCount; i++) {
            dots[i] = new ImageView(this);
            dots[i].setImageDrawable(getResources().getDrawable(R.drawable.nonselecteditem_dot));

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );

            params.setMargins(20, 0, 20, 0);

            pager_indicator.addView(dots[i], params);
        }

        dots[0].setImageDrawable(getResources().getDrawable(R.drawable.selecteditem_dot));
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        for (int i = 0; i < dotsCount; i++) {
            dots[i].setImageDrawable(getResources().getDrawable(R.drawable.nonselecteditem_dot));
        }

        dots[position].setImageDrawable(getResources().getDrawable(R.drawable.selecteditem_dot));

        if(position == NUM_PAGES ) {
            info_linear.setVisibility(View.VISIBLE);

        } else{
            info_linear.setVisibility(View.GONE);

        }

    }

    @Override
    public void onPageSelected(int position) {
        for (int i = 0; i < dotsCount; i++) {
            dots[i].setImageDrawable(getResources().getDrawable(R.drawable.nonselecteditem_dot));
        }

        dots[position].setImageDrawable(getResources().getDrawable(R.drawable.selecteditem_dot));
        if(position == NUM_PAGES ) {
            info_linear.setVisibility(View.VISIBLE);

        } else{
            info_linear.setVisibility(View.GONE);
        }
    }


    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.lets_go) {
            Intent i = new Intent(this,SignInSignUp.class);
            startActivity(i);
            finish();
        }
    }


    public class SlidingImage_Adapter extends PagerAdapter {


        private ArrayList<Integer> IMAGES;
        private LayoutInflater inflater;
        private Context context;


        public SlidingImage_Adapter(Context context,ArrayList<Integer> IMAGES) {
            this.context = context;
            this.IMAGES=IMAGES;
            inflater = LayoutInflater.from(context);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public int getCount() {
            return IMAGES.size();
        }

        @Override
        public Object instantiateItem(ViewGroup view, int position) {
            View imageLayout = inflater.inflate(R.layout.slidingimages_layout, view, false);

            assert imageLayout != null;
            final ImageView imageView = (ImageView) imageLayout
                    .findViewById(R.id.image);


            imageView.setImageResource(IMAGES.get(position));

            view.addView(imageLayout, 0);

            return imageLayout;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view.equals(object);
        }

        @Override
        public void restoreState(Parcelable state, ClassLoader loader) {
        }

        @Override
        public Parcelable saveState() {
            return null;
        }
    }

    private void AnimateandSlideShow() {
        try {
            if(currentimageindex == NUM_PAGES - 1) {
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        currentimageindex = 0;
                    }
                }, 100);
                info_linear.setVisibility(View.VISIBLE);
            } else if(continue_animation) {
                info_linear.setVisibility(View.GONE);

            }
            if(continue_animation) {

                for (int i = 0; i < dotsCount; i++) {
                    dots[i].setImageDrawable(getResources().getDrawable(R.drawable.nonselecteditem_dot));
                }

                dots[currentimageindex % images.length].setImageDrawable(getResources().getDrawable(R.drawable.selecteditem_dot));

                Animation rotateimage = AnimationUtils.loadAnimation(this, R.anim.left_in);

                currentimageindex++;
                pager_introduction.startAnimation(rotateimage);
            }

        } catch (Exception e){

        }
    }


}
