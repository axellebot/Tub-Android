package xyz.lebot.tub.ui.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import xyz.lebot.tub.R;
import xyz.lebot.tub.data.model.StopModel;
import xyz.lebot.tub.ui.adapter.StopListAdapter;
import xyz.lebot.tub.ui.navigator.Navigator;
import xyz.lebot.tub.ui.presenter.StopFragmentPresenter;

public class StopFragment extends android.support.v4.app.Fragment {

    @BindView(R.id.fragment_stop_recycler_view)
    RecyclerView recyclerView;

    private Navigator navigator;
    private StopListAdapter stopListAdapter;
    private StopFragmentPresenter presenter;

    public StopFragment() {
        // Required empty public constructor
    }


    @Override
    public void setArguments(Bundle args) {
        super.setArguments(args);
        this.navigator = (Navigator) args.get("NAVIGATOR");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_stop, container, false);
        ButterKnife.bind(this, view);

        //Presenter
        presenter = new StopFragmentPresenter(this, navigator);

        //Adapter
        stopListAdapter = new StopListAdapter(this.getContext(), null);

        //RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        recyclerView.setAdapter(stopListAdapter);

        presenter.initialize();
        return view;
    }

    @Override
    public void onPause() {
        super.onPause();
        presenter.pause();
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.resume();
    }

    public void initList(List<StopModel> stopModels) {
        stopListAdapter.swap(stopModels);
    }

}
