package com.helabs.eltonjhony.udacitymovies.leftMenu;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.helabs.eltonjhony.udacitymovies.R;
import com.helabs.eltonjhony.udacitymovies.bus.ToggleMenuEvent;
import com.helabs.eltonjhony.udacitymovies.common.BaseFragment;
import com.helabs.eltonjhony.udacitymovies.databinding.FragmentMenuBinding;
import com.helabs.eltonjhony.udacitymovies.favorites.FavoritesFragment;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by eltonjhony on 14/09/17.
 */
public class MenuFragment extends BaseFragment {

    public static final String MAIN_CONTENT_EXTRA = "MAIN_CONTENT_EXTRA";

    private FragmentMenuBinding mBinding;

    public static MenuFragment newInstance(int mainContent) {
        Bundle args = new Bundle();
        args.putInt(MAIN_CONTENT_EXTRA, mainContent);
        MenuFragment menuFragment = new MenuFragment();
        menuFragment.setArguments(args);
        return menuFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_menu, container, false);
        setupLeftDrawerMenu();

        return getLayout().getRoot();
    }

    private void setupLeftDrawerMenu() {
        final List<DataModel> drawerList = new ArrayList<>();
        drawerList.add(new DataModel(R.drawable.ic_favorite_red_24dp, getString(R.string.favorites_label)));
        DrawerItemCustomAdapter mAdapter = new DrawerItemCustomAdapter(
                getContext(), R.layout.drawer_list_item_row, drawerList);
        getLayout().leftDrawer.setAdapter(mAdapter);

        getLayout().leftDrawer.setOnItemClickListener((parent, view, position, id) -> {
            switch (position) {
                case 0:
                    replaceFragment(FavoritesFragment.newInstance(),
                            getArguments().getInt(MAIN_CONTENT_EXTRA),
                            getFragmentManager());
                    EventBus.getDefault().post(new ToggleMenuEvent());
                    break;
            }
        });
    }

    private FragmentMenuBinding getLayout() {
        return mBinding;
    }
}
