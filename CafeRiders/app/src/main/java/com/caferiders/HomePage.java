package com.caferiders;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
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
import android.view.Window;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TimePicker;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;
import java.util.Timer;
import java.util.TimerTask;

public class HomePage extends AppCompatActivity implements View.OnClickListener,DatePickerDialog.OnDateSetListener {

    private LinearLayout pager_indicator;
    private ViewPager pager_introduction;
    private int[] images = {R.drawable.home_banner,R.drawable.home_banner2,R.drawable.home_banner3};
    Animation animation;
    private Timer timer;
    private ArrayList<Integer> ImagesArray = new ArrayList<Integer>();
    private int dotsCount;
    private int currentPage = 0;
    private int currentimageindex = 0;
    private ImageView[] dots;
    private LinearLayout happy_customers,featured_article;
    private Button book_now;
    private Dialog mBottomSheetDialog;
    private Button end_time,start_date,start_time,end_date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        init();
        initData();
    }
    public void init() {
        pager_introduction = (ViewPager)findViewById(R.id.pager_introduction);
        pager_indicator = (LinearLayout)findViewById(R.id.viewPagerCountDots);
        happy_customers = (LinearLayout)findViewById(R.id.happy_customers);
        featured_article = (LinearLayout)findViewById(R.id.featured_article);
        book_now = (Button)findViewById(R.id.book_now);
        book_now.setOnClickListener(this);
        mBottomSheetDialog = new Dialog(this);
        mBottomSheetDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        mBottomSheetDialog.setContentView(R.layout.schedule_booking);
        mBottomSheetDialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        Button book_now = (Button)mBottomSheetDialog.findViewById(R.id.book_bike_now);
        start_date = (Button)mBottomSheetDialog.findViewById(R.id.start_date);
        start_time = (Button)mBottomSheetDialog.findViewById(R.id.start_time);
        end_date = (Button)mBottomSheetDialog.findViewById(R.id.end_date);
        end_time = (Button)mBottomSheetDialog.findViewById(R.id.end_time);
        book_now.setOnClickListener(this);
        start_date.setOnClickListener(this);
        start_time.setOnClickListener(this);
        end_date.setOnClickListener(this);
        end_time.setOnClickListener(this);
    }
    public void initData() {
        setupHappyCustomers();
        setupFeaturedArticle();
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

        pager_introduction.setAdapter(new SlidingImage_Adapter(HomePage.this, ImagesArray));

        // Auto start of viewpager
        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            public void run() {

                pager_introduction.setCurrentItem(currentPage++, true);
                if (currentPage == images.length + 1) {
                    currentPage = 0;

                }
            }
        };
        Timer swipeTimer = new Timer();
        swipeTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                    handler.post(Update);
                }
        }, delay, period);
    }

    public void setupHappyCustomers() {
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        for (int i=0;i<4;i++){
          View  view = inflater.inflate(R.layout.happy_customer_item, null);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(1000, ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.setMargins(10, 20, 10, 20);
            happy_customers.addView(view,layoutParams);
        }
    }

    public void setupFeaturedArticle() {
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        for (int i=0;i<4;i++){
            View  view = inflater.inflate(R.layout.featured_article_item, null);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(1000, ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.setMargins(10, 20, 10, 20);
            featured_article.addView(view,layoutParams);
        }
    }


    private void AnimateandSlideShow() {
        try {

            for (int i = 0; i < dotsCount; i++) {
                dots[i].setImageDrawable(getResources().getDrawable(R.drawable.nonselecteditem_dot));
            }

            dots[currentimageindex % images.length].setImageDrawable(getResources().getDrawable(R.drawable.selecteditem_dot));

            Animation rotateimage = AnimationUtils.loadAnimation(this, R.anim.left_in);

            currentimageindex++;
            pager_introduction.startAnimation(rotateimage);


        } catch (Exception e){

        }
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
    public void onClick(View view) {
        final Calendar myCalendar = Calendar.getInstance(TimeZone.getDefault());
        String myFormat = "MMM dd, yyyy"; //In which you need put here
        final SimpleDateFormat sdformat = new SimpleDateFormat(myFormat, Locale.US);
        int year = myCalendar.get(Calendar.YEAR);
        int month = myCalendar.get(Calendar.MONTH);
        int day = myCalendar.get(Calendar.DATE);
        int hour = myCalendar.get(Calendar.HOUR);
        int min = myCalendar.get(Calendar.MINUTE);
        if(view.getId() == R.id.book_now) {
            mBottomSheetDialog.show();
        } else if(view.getId() == R.id.book_bike_now) {
            Intent i = new Intent(HomePage.this,BikeList.class);
            startActivity(i);
        } else if(view.getId() == R.id.start_date) {
            DatePickerDialog dialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                    myCalendar.set(Calendar.YEAR, i);
                    myCalendar.set(Calendar.MONTH, i1);
                    myCalendar.set(Calendar.DAY_OF_MONTH, i2);

                    start_date.setText(sdformat.format(myCalendar.getTime()));
                }
            }, year, month, day);
            dialog.show();
        } else if(view.getId() == R.id.end_date) {
            DatePickerDialog dialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                    myCalendar.set(Calendar.YEAR, i);
                    myCalendar.set(Calendar.MONTH, i1);
                    myCalendar.set(Calendar.DAY_OF_MONTH, i2);

                    end_date.setText(sdformat.format(myCalendar.getTime()));
                }
            }, year, month, day);
            dialog.show();

        } else if(view.getId() == R.id.start_time) {
            TimePickerDialog mTimePicker;
            mTimePicker = new TimePickerDialog(HomePage.this, new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                    start_time.setText(selectedHour + ":" + selectedMinute);
                }
            }, hour, min, true);//Yes 24 hour time
            mTimePicker.setTitle("Select Time");
            mTimePicker.show();
        } else if(view.getId() == R.id.end_time) {

            TimePickerDialog mTimePicker;
            mTimePicker = new TimePickerDialog(HomePage.this, new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                    end_time.setText(selectedHour + ":" + selectedMinute);
                }
            }, hour, min, true);//Yes 24 hour time
            mTimePicker.setTitle("Select Time");
            mTimePicker.show();
        }



//        Intent i = new Intent(this,ScheduleBooking.class);
//        startActivity(i);
    }

    @Override
    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {

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


}
