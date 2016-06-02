package me.relex.photodraweeview.sample;

import android.graphics.drawable.Animatable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.backends.pipeline.PipelineDraweeControllerBuilder;
import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.imagepipeline.image.ImageInfo;

import java.util.ArrayList;
import java.util.List;

import me.relex.circleindicator.CircleIndicator;
import me.relex.photodraweeview.PhotoDraweeView;
import me.relex.photodraweeview.sample.model.ImageModel;

public class ViewPagerActivity extends AppCompatActivity {
    private DraweePagerAdapter pagerAdapter;
    private int[] mDrawables = new int[]{
            R.drawable.viewpager_1, R.drawable.viewpager_2, R.drawable.viewpager_3, R.raw.panda1
    };

    /*private String[] mDrawables = new String[] {
            "http://b.hiphotos.baidu.com/image/pic/item/7a899e510fb30f2493c8cbedcc95d143ac4b0389.jpg",
            "https://ss0.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=1241248897,3541713755&fm=21&gp=0.jpg",
            "http://tse2.mm.bing.net/th?id=OIP.M2347b155b79e81f88f860a39135c3d04o2&w=215&h=134&c=7&rs=1&qlt=90&o=4&pid=1.1",
            "http://tse2.mm.bing.net/th?id=OIP.M30b3e6a07134754d37b5c1e607fae23fo0&pid=15.1"
    };*/

    private List<ImageModel> stringList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewpager);

        ((Toolbar) findViewById(R.id.toolbar)).setNavigationOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onBackPressed();
                    }
                });

        stringList = new ArrayList<>();
        for (int i = 0; i <
                mDrawables.length; i++) {
            ImageModel imageModel = new ImageModel();
            imageModel.setType(2);
            imageModel.setImageResource(String.valueOf(mDrawables[i]));
            stringList.add(imageModel);
        }


        CircleIndicator indicator = (CircleIndicator) findViewById(R.id.indicator);
        MultiTouchViewPager viewPager = (MultiTouchViewPager) findViewById(R.id.view_pager);
        pagerAdapter = new DraweePagerAdapter(stringList);
        viewPager.setAdapter(pagerAdapter);
        indicator.setViewPager(viewPager);

    }

    public class DraweePagerAdapter extends PagerAdapter {
        private List<ImageModel> stringList;

        public DraweePagerAdapter(List<ImageModel> stringList) {
            this.stringList = stringList;
        }

        @Override
        public int getCount() {

            return stringList.size();
        }


        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public Object instantiateItem(ViewGroup viewGroup, int position) {
            ImageModel data = stringList.get(position);
            final PhotoDraweeView photoDraweeView = new PhotoDraweeView(viewGroup.getContext());
            PipelineDraweeControllerBuilder controller = Fresco.newDraweeControllerBuilder();
            if (data.getType() == 1) {
                Uri uri = Uri.parse(data.getImageUrl());
                controller.setUri(uri);
            } else {
                controller.setUri(Uri.parse(data.getImageResource()));
            }
            controller.setAutoPlayAnimations(true);
            controller.setOldController(photoDraweeView.getController());
            controller.setControllerListener(new BaseControllerListener<ImageInfo>() {
                @Override
                public void onFinalImageSet(String id, ImageInfo imageInfo, Animatable animatable) {
                    super.onFinalImageSet(id, imageInfo, animatable);
                    if (imageInfo == null) {
                        return;
                    }
                    photoDraweeView.update(imageInfo.getWidth(), imageInfo.getHeight());
                }
            });
            photoDraweeView.setController(controller.build());

            try {
                viewGroup.addView(photoDraweeView, ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT);
            } catch (Exception e) {
                e.printStackTrace();
            }

            return photoDraweeView;
        }
    }
}
