package ib.ganz.etoll.Transaksi.ui;

import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.support.annotation.Nullable;

import ib.ganz.etoll.Transaksi.ui.frags.PrefsFragment;

/**
 * Created by suyashg on 10/09/17.
 */

public class UnifiedNavigationActivity extends PreferenceActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getFragmentManager().beginTransaction()
                .replace(android.R.id.content, new PrefsFragment()).commit();

    }
}
