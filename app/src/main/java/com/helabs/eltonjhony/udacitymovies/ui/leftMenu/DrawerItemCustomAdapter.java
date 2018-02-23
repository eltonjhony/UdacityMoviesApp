package com.helabs.eltonjhony.udacitymovies.ui.leftMenu;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.helabs.eltonjhony.udacitymovies.databinding.DrawerListItemRowBinding;

import java.util.List;

/**
 * Created by eltonjhony on 13/09/17.
 */
public class DrawerItemCustomAdapter extends ArrayAdapter<DataModel> {

    private int mResource;
    private DrawerListItemRowBinding mBinding;

    public DrawerItemCustomAdapter(@NonNull Context context, @LayoutRes int resource, List<DataModel> data) {
        super(context, resource, data);
        mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if (convertView == null) {
            mBinding = DataBindingUtil.inflate(LayoutInflater.from(getContext()),
                    mResource, parent, false);

            final DataModel dataModel = getItem(position);
            if (dataModel != null) {
                getLayout().imageViewIcon.setImageResource(dataModel.getIcon());
                getLayout().textViewName.setText(dataModel.getName());
            }

            getLayout().getRoot().setTag(getLayout().getRoot());

            return mBinding.getRoot();
        }

        return (View) mBinding.getRoot().getTag();
    }

    private DrawerListItemRowBinding getLayout() {
        return mBinding;
    }
}
