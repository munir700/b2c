package com.digitify.identityscanner.modules.docscanner.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.digitify.identityscanner.R;
import com.digitify.identityscanner.databinding.FragmentScanResultsBinding;
import com.digitify.identityscanner.fragments.BaseFragment;
import com.digitify.identityscanner.modules.docscanner.interfaces.IScanResults;
import com.digitify.identityscanner.modules.docscanner.models.IdentityScannerResult;
import com.digitify.identityscanner.modules.docscanner.viewmodels.IdentityScannerViewModel;
import com.digitify.identityscanner.modules.docscanner.viewmodels.ScanResultsViewModel;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

public class ScanResultsFragment extends BaseFragment implements IScanResults.View {
    private static String TAG = ScanResultsFragment.class.getName();
    private static String RESULTS = "scan_results";

    public static ScanResultsFragment get(IdentityScannerResult result) {
        ScanResultsFragment fragment = new ScanResultsFragment();
        Bundle b = new Bundle();
        b.putParcelable(RESULTS, result);
        fragment.setArguments(b);
        return fragment;
    }

    private ScanResultsViewModel viewModel;
    private IdentityScannerViewModel parentViewModel;
    private IdentityScannerResult result;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        FragmentScanResultsBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_scan_results, container, false);
        View view = binding.getRoot();
        binding.setModel(getViewModel());
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);
        getViewModel().registerLifecycleOwner(this);
        getViewModel().init(getResults(), this);
    }

    @Override
    protected String getScreenTitle() {
        return getString(R.string.review_scan_results_title);
    }

    @Override
    public IdentityScannerResult getResults() {
        if (result == null) {
            Bundle b = getArguments();
            if (b != null) {
                result = b.getParcelable(RESULTS);
            }
        }
        return result;
    }

    @Override
    public ScanResultsViewModel getViewModel() {
        if (viewModel == null)
            viewModel = ViewModelProviders.of(this).get(ScanResultsViewModel.class);
        return viewModel;
    }

    @Override
    public IdentityScannerViewModel getParentViewModel() {
        if (parentViewModel == null)
            parentViewModel = ViewModelProviders.of(getActivity()).get(IdentityScannerViewModel.class);
        return parentViewModel;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_scan_result, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        if (item.getItemId() == R.id.action_done) {
            getParentViewModel().onResultsAccepted(getResults());
            return true;
        } else if (item.getItemId() == R.id.action_retake) {
            getParentViewModel().onResultNotAccepted();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
