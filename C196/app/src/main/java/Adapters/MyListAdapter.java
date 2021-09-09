package Adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.c196.R;

import Fragments.CourseList;
import Fragments.TermList;

public class MyListAdapter extends FragmentPagerAdapter {

    private Context mContext;

    public MyListAdapter(Context context, FragmentManager fragmentManager) {
        super(fragmentManager);
        mContext = context;
    }


    @Override
    public Fragment getItem(int i) {
        if(i == 0){
            return new CourseList();
        }else{
            return new TermList();
        }
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (position == 0) {
            return mContext.getString(R.string.courses);
        } else{
            return mContext.getString(R.string.term);
        }
    }
}
