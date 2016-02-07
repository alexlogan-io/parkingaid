package parkingaid.lpg.com.parkingadd.framework;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 * Represents an object that is used to hold fragments in order to enable the swipe user interface
 */
public class AppPagerAdapter extends FragmentPagerAdapter {

    private final ArrayList<Fragment> fragments = new ArrayList<>();
    private final ArrayList<String> titles = new ArrayList<>();

    /**
     * Constructor for the AppPagerAdapter, inherits from the parent FragmentPagerAdapter constructor
     * @param fm
     */
    public AppPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    /**
     * method to add a fragment to the AppPagerAdapter
     * @param frag fragment to be added
     * @param title title of the fragment to be added
     */
    public void addFragment(Fragment frag, String title){
        fragments.add(frag);
        titles.add(title);
    }

    /**
     * method to return a fragment when given a specific position
     * @param position position of the fragment
     * @return the required fragment
     */
    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    /**
     * get the number of fragments
     * @return the number of fragment
     */
    @Override
    public int getCount() {
        return 3;
    }

    /**
     * get the title of a fragment at a specific position
     * @param position position of the fragment
     * @return title of the fragment
     */
    public CharSequence getPageTitle(int position) {
        return titles.get(position);
    }

    /**
     * built in method for refreshing fragments
     * @param object
     * @return
     */
    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }
}
