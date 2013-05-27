package net.eledge.android.eu.europeana.gui.activity;

import net.eledge.android.eu.europeana.Config;
import net.eledge.android.eu.europeana.R;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.widget.SearchView;

public class SearchActivity extends FragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search);

		if (Config.DEBUGMODE) {
			StrictMode.enableDefaults();
		}
		handleIntent(getIntent());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.search, menu);
	    SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
	    SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
	    searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
		return true;
	}

	@Override
	protected void onNewIntent(Intent intent) {
		setIntent(intent);
		handleIntent(intent);
	}

	private void handleIntent(Intent intent) {
		String query = null;
		if (intent != null) {
			if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
				query = intent.getStringExtra(SearchManager.QUERY);
			} else if (Intent.ACTION_VIEW.equals(intent.getAction())) {
				query = intent.getDataString();
			} else {
				onSearchRequested();
			}
			if (!TextUtils.isEmpty(query)) {
				// mQuery = query.toLowerCase();
				// mSearchFragment.search(mQuery);
			}
		}
	}

}