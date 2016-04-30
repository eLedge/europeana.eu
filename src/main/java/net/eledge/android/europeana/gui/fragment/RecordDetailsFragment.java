/*
 * Copyright (c) 2013-2015 eLedge.net and the original author or authors.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package net.eledge.android.europeana.gui.fragment;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.squareup.otto.Subscribe;

import net.eledge.android.europeana.EuropeanaApplication;
import net.eledge.android.europeana.R;
import net.eledge.android.europeana.gui.adapter.RecordViewAdapter;
import net.eledge.android.europeana.search.RecordController;
import net.eledge.android.europeana.search.event.RecordLoadedEvent;
import net.eledge.android.europeana.search.model.record.RecordObject;
import net.eledge.android.europeana.search.model.record.abstracts.RecordView;
import net.eledge.android.europeana.search.model.record.enums.RecordDetails;
import net.eledge.android.toolkit.gui.GuiUtils;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public class RecordDetailsFragment extends Fragment {

    // Controller
    private final RecordController recordController = RecordController._instance;

    private RecordViewAdapter mRecordViewAdapter;

    private Unbinder unbinder;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EuropeanaApplication.bus.register(this);
        mRecordViewAdapter = new RecordViewAdapter((EuropeanaApplication) this.getActivity().getApplication(),
                this.getActivity(), new ArrayList<RecordView>());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_record_details, container, false);
        unbinder = ButterKnife.bind(this, root);
        ListView mListView = (ListView) root.findViewById(R.id.fragment_record_details_recyclerview);
        mListView.setAdapter(mRecordViewAdapter);
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            setupClipboardAction(mListView);
        }
        return root;
    }

    @Override
    public void onResume() {
        if (recordController.record != null) {
            redrawRecordDetails(recordController.record);
        }
        super.onResume();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EuropeanaApplication.bus.unregister(this);
    }

    @Subscribe
    public void OnRecordLoadedEvent(RecordLoadedEvent event) {
        redrawRecordDetails(event.result);
    }

    private void redrawRecordDetails(RecordObject record) {
        mRecordViewAdapter.clear();
        for (RecordDetails detail : RecordDetails.getVisibles(record)) {
            mRecordViewAdapter.add(detail);
        }
        mRecordViewAdapter.notifyDataSetChanged();
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private void setupClipboardAction(ListView mListView) {
        mListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                RecordView record = mRecordViewAdapter.getItem(position);
                Activity activity = RecordDetailsFragment.this.getActivity();
                ClipboardManager clipboard = (ClipboardManager) activity.getSystemService(Context.CLIPBOARD_SERVICE);
                clipboard.setPrimaryClip(ClipData.newPlainText(GuiUtils.getString(activity, record.getLabel()),
                        StringUtils.join(record.getValues(recordController.record, (EuropeanaApplication) activity.getApplication()), ";")));
                GuiUtils.toast(activity, R.string.msg_copied2clipboard);
                return true;
            }
        });
    }

}
