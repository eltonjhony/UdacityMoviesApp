package com.helabs.eltonjhony.udacitymovies.infrastructure;

import android.content.Context;

import com.helabs.eltonjhony.udacitymovies.R;

import javax.inject.Inject;

/**
 * Created by eltonjhony on 20/05/17.
 */

public class ApplicationMessages {

    private Context context;

    @Inject
    public ApplicationMessages(Context context) {
        this.context = context;
    }

    public String getNoNetworkAvailableMessage() {
        return context.getString(R.string.no_network_message);
    }

    public String getDefaultOverviewMessage() {
        return context.getString(R.string.no_resume_message);
    }

    public String getGenericErrorMessage() {
        return context.getString(R.string.generic_error_message);
    }
}
