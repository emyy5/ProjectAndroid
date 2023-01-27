//package com.example.foodplanner;
//
//import android.content.Context;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//
//import androidx.annotation.NonNull;
//import androidx.viewpager.widget.PagerAdapter;
//
//public class SlideAdpter extends PagerAdapter {
//
//    Context context;
//    int images[] ={
//
//            R.drawable.on2,
//          R.drawable.cheff,
//            R.drawable.food
//
//    };
//    int descrobtion []={
//
//      R.string.desc_one,
//      R.string.desc_two,
//      R.string.desc_three
//    };
//    int heading []={
//
//            R.string.heading_one,
//            R.string.heading_two,
//            R.string.heading_three
//    };
//
//
//    public SlideAdpter(Context context) {
//        this.context = context;
//    }
//
//    @Override
//    public int getCount() {
//        return heading.length;
//    }
//
//    @Override
//    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
//        return view== (LinearLayout) object;
//    }
//
//    @NonNull
//    @Override
//    public Object instantiateItem(@NonNull ViewGroup container, int position) {
//        LayoutInflater layoutInflater= (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
//        View view= layoutInflater.inflate(R.layout.slide, container, false);
//        ImageView slidItems= (ImageView) view.findViewById(R.id.image3);
//
//
//    }
//}
