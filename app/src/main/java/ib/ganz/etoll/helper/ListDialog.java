package ib.ganz.etoll.helper;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.Arrays;
import java.util.List;

import ib.ganz.etoll.R;
import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by limakali on 4/21/2018.
 */

public class ListDialog<T> extends DialogFragment
{
    ListView lvItemDialog;
    View ctrLoading;

    Class<T> t;
    CompositeDisposable c;
    List<T> l;
    ArrayAdapter<T> adp;

    OnGettingData<T> onGettingData;
    OnSelected<T> onSelected;

    @SuppressWarnings("unchecked")
    public static <T> void go(FragmentManager manager, OnSelected<T> o1, T... t)
    {
        Class<T> clazz = (Class<T>) t[0].getClass();
        go(Arrays.asList(t), clazz, manager, null, o1);
    }

    @SuppressWarnings("unchecked")
    public static <T> void go(List<T> l, Class<T> t, FragmentManager manager, OnGettingData<T> o, OnSelected<T> o1)
    {
        Bundle b = new Bundle();
        b.putString("l", Gxon.toJsonArray(l));

        ListDialog<T> d = new ListDialog<>();
        d.setArguments(b);
        d.show(manager, o, o1, t);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        c = new CompositeDisposable();
        l = Gxon.fromJsonArray(getArguments().getString("l"), t);
        adp = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, l);

        return inflateDialog();
    }

    protected Dialog inflateDialog()
    {
        View v = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_list, null);

        lvItemDialog = v.findViewById(R.id.lvItemDialog);
        ctrLoading = v.findViewById(R.id.ctrLoading);

        if (lvItemDialog == null)
        {
            Toast.makeText(getActivity(), "listview null", Toast.LENGTH_LONG).show();
            return new AlertDialog.Builder(getActivity())
                    .setView(v)
                    .create();
        }

        lvItemDialog.setAdapter(adp);
        lvItemDialog.setOnItemClickListener((av, vw, i, lo) ->
        {
            if (onSelected != null) onSelected.onSelected(l.get(i));
            dismiss();
        });

        if (l.isEmpty())
        {
            ctrLoading.setVisibility(View.VISIBLE);

            if (onGettingData != null)
            {
                onGettingData.getData(list ->
                {
                    ctrLoading.setVisibility(View.GONE);
                    l.clear();
                    l.addAll(list);
                    adp.notifyDataSetChanged();

                }, () -> ctrLoading.setVisibility(View.GONE));
            }
        }
        else
        {
            ctrLoading.setVisibility(View.GONE);
        }

        return new AlertDialog.Builder(getActivity())
                .setView(v)
                .create();
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();

        if (c != null && !c.isDisposed())
        {
            c.dispose();
        }
    }

    public void show(FragmentManager manager, OnGettingData<T> o, OnSelected<T> o1, Class<T> t)
    {
        super.show(manager, "u");
        this.onGettingData = o;
        this.onSelected = o1;
        this.t = t;
    }

    public interface OnSelected<T>
    {
        void onSelected(T u);
    }

    public interface OnGettingData<T>
    {
        void getData(OnDataLoaded<T> ol, OnDataError oe);
    }

    public interface OnDataLoaded<T>
    {
        void onLoaded(List<T> l);
    }

    public interface OnDataError
    {
        void onError();
    }
}
