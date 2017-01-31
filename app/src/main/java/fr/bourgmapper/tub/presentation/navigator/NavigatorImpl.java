package fr.bourgmapper.tub.presentation.navigator;

import android.os.Bundle;
import android.support.v4.view.ViewPager;

import java.io.Serializable;

import fr.bourgmapper.tub.presentation.ui.activity.MainActivity;
import fr.bourgmapper.tub.presentation.ui.adapter.MainActivityFragmentPagerAdapter;
import fr.bourgmapper.tub.presentation.ui.fragment.HomeFragment;
import fr.bourgmapper.tub.presentation.ui.fragment.LineDetailFragment;
import fr.bourgmapper.tub.presentation.ui.fragment.LineFragment;
import fr.bourgmapper.tub.presentation.ui.fragment.StopFragment;
import fr.bourgmapper.tub.R;

/**
 * Created by axellebot on 07/11/2016.
 */

public class NavigatorImpl implements Navigator, Serializable {
    private MainActivity mainActivity;
    private ViewPager viewPager;
    private MainActivityFragmentPagerAdapter pagerAdapter;
    private PART SELECTED_PART;

    public NavigatorImpl(MainActivity mainActivity, Navigator navigator, ViewPager viewPager, MainActivityFragmentPagerAdapter pagerAdapter) {
        this.mainActivity = mainActivity;
        this.viewPager = viewPager;
        this.pagerAdapter = pagerAdapter;
        this.pagerAdapter.setNavigator(navigator);
    }

    @Override
    public void initPartHome() {
        Class<?> fragmentClass = HomeFragment.class;

        Bundle args = new Bundle();
        args.putSerializable("NAVIGATOR", this);

        addFragmentClassWithBundleToPart(PART.HOME, fragmentClass, args);
    }

    @Override
    public void initPartLine() {
        Class<?> fragmentClass = LineFragment.class;

        Bundle args = new Bundle();
        args.putSerializable("NAVIGATOR", this);

        addFragmentClassWithBundleToPart(PART.LINE, fragmentClass, args);
    }

    @Override
    public void initPartStop() {
        Class<?> fragmentClass = StopFragment.class;

        Bundle args = new Bundle();
        args.putSerializable("NAVIGATOR", this);

        addFragmentClassWithBundleToPart(PART.STOP, fragmentClass, args);
    }

    @Override
    public void navigateToPartHome() {
        SELECTED_PART = PART.HOME;
        switchPart(SELECTED_PART);
        mainActivity.setToolbarTitle(mainActivity.getResources().getString(R.string.navigation_part_home_name));
        int res = R.color.colorPartHome;
        mainActivity.setContextColor(res);
    }

    @Override
    public void navigateToPartLine() {
        SELECTED_PART = PART.LINE;
        switchPart(SELECTED_PART);
        mainActivity.setToolbarTitle(mainActivity.getResources().getString(R.string.navigation_part_line_name));
        int res = R.color.colorPartLine;
        mainActivity.setContextColor(res);
    }

    @Override
    public void navigateToPartStop() {
        SELECTED_PART = PART.STOP;
        switchPart(SELECTED_PART);
        mainActivity.setToolbarTitle(mainActivity.getResources().getString(R.string.navigation_part_stop_name));
        int res = R.color.colorPartStop;
        mainActivity.setContextColor(res);
    }

    @Override
    public void navigateBack() {
        navigateBackFromPart();
    }

    public void navigateToLineDetail(String lineId) {
        navigateToPartLine();

        Class<?> fragmentClass = LineDetailFragment.class;

        Bundle args = new Bundle();
        args.putSerializable("NAVIGATOR", this);
        args.putString("LINE_ID", lineId);

        addFragmentClassWithBundleToPart(PART.LINE, fragmentClass, args);
    }

    private void addFragmentClassWithBundleToPart(PART part, Class<?> fragmentClass, Bundle args) {
        pagerAdapter.addFragmentClassWithBundleToStackForPosition(fragmentClass, args, part.ordinal());
    }

    private void navigateBackFromPart() {
        pagerAdapter.dequeueFragmentClassStackForPosition(SELECTED_PART.ordinal());
    }

    private void switchPart(PART part) {
        viewPager.setCurrentItem(part.ordinal());
        mainActivity.setSelecteBottomNavigation(part.ordinal());
    }

    private enum PART {
        HOME,
        LINE,
        STOP
    }
}