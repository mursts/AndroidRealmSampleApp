package jp.mursts.android.androidrealmsampleapp;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import io.realm.Realm;
import io.realm.RealmChangeListener;
import jp.mursts.android.androidrealmsampleapp.models.Todo;
import jp.mursts.android.androidrealmsampleapp.models.TodoDAO;

public class ItemDetailFragment extends Fragment {

    private Realm mRealm;

    public static final String ARG_ITEM_ID = "item_id";

    private Todo mTodo;

    private TextView mRemarkView;
    private CollapsingToolbarLayout appBarLayout;

    public ItemDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_ITEM_ID)) {

            Activity activity = this.getActivity();

            mRealm = Realm.getInstance(activity);

            appBarLayout = (CollapsingToolbarLayout) activity.findViewById(R.id.toolbar_layout);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.item_detail, container, false);

        mRemarkView = (TextView) rootView.findViewById(R.id.item_detail);

        mTodo = TodoDAO.loadOne(mRealm, getArguments().getLong(ARG_ITEM_ID));
        mTodo.addChangeListener(new RealmChangeListener() {
            @Override
            public void onChange() {
                updateView();
            }
        });

        return rootView;
    }

    @Override
    public void onDestroyView () {
        mRealm.close();
        super.onDestroyView();
    }

    private void updateView() {
        if (appBarLayout != null && mTodo != null) {
            appBarLayout.setTitle(mTodo.getTodo());
            mRemarkView.setText(mTodo.getRemark());
        }
    }
}
