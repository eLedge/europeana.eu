package net.eledge.android.eu.europeana.gui.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.eledge.android.eu.europeana.R;

public class RecordImagesFragment extends Fragment {
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View root = (View) inflater.inflate(
				R.layout.fragment_record_images, null);
		
		
		return root;
	}

}