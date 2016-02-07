package parkingaid.lpg.com.parkingadd.framework;

import android.app.ActionBar;
import android.content.Context;
import android.support.v4.view.ViewPager;
import android.view.View;

/**
 * Sets up the action bar to enable a tab view and enables the user to swipe between fragments
 * in the view
 */
public class SetUpTabView {

    public SetUpTabView() {
    }

    /**
     * Sets up the action bar to enable in line tabs
     * @param actionBar the action bar in the view
     * @return the action bar with the additional features
     */
    public ActionBar newActionBar(ActionBar actionBar){
        // Specify that the Home/Up button should not be enabled, since there is no hierarchical
        // parent.
        actionBar.setHomeButtonEnabled(false);

        // Specify that we will be displaying tabs in the action bar.
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        return actionBar;
    }

    /**
     * Updates the view with the current tab using the position of each fragment(tab)
     * @param view the current view
     * @param viewPagerId the current view id
     * @param mAppPagerAdapter the pager adapter to hold the fragments
     * @param actionBar the action bar in the view
     * @return the view containing the selected fragment
     */
    public ViewPager newViewPager(View view, int viewPagerId, final AppPagerAdapter mAppPagerAdapter, final ActionBar actionBar){
        ViewPager mViewPager = (ViewPager) view.findViewById(viewPagerId);
        //pages off screen to stop them re-loading each time
        mViewPager.setOffscreenPageLimit(2);
        mViewPager.setAdapter(mAppPagerAdapter);
        mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                actionBar.setSelectedNavigationItem(position);
                mAppPagerAdapter.notifyDataSetChanged();
            }
        });
        return mViewPager;
    }

    /**
     * Adds fragments (tabs) to the action bar in the view
     * @param mAppPagerAdapter the AppPagerAdapter for holding the fragments
     * @param actionBar the action bar in the view
     * @param listener listener for tabs in the action bar
     */
    public void addTabsToActionBar(AppPagerAdapter mAppPagerAdapter, ActionBar actionBar, ActionBar.TabListener listener){
        // For each of the sections in the app, add a tab to the action bar.
        for (int i = 0; i < mAppPagerAdapter.getCount(); i++) {
            actionBar.addTab(
                    actionBar.newTab()
                            .setText(mAppPagerAdapter.getPageTitle(i))
                            .setTabListener(listener));
        }
    }
}
