package net.eledge.android.eu.europeana.search.task;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;

import net.eledge.android.eu.europeana.Config;
import net.eledge.android.eu.europeana.search.RecordController;
import net.eledge.android.eu.europeana.search.model.record.Record;
import net.eledge.android.eu.europeana.tools.UriHelper;
import net.eledge.android.toolkit.async.ListenerNotifier;
import net.eledge.android.toolkit.async.listener.TaskListener;
import net.eledge.android.toolkit.json.exception.JsonParserException;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.AsyncTask;
import android.text.TextUtils;

public class RecordTask extends AsyncTask<String, Void, Record>  {
	
	private final RecordController recordController = RecordController.instance;
	
	private final TaskListener<Record> listener;
	
	public RecordTask(TaskListener<Record> listener) {
		this.listener = listener;
	}

	@Override
	protected Record doInBackground(String... params) {
		if (TextUtils.isEmpty(params[0])) {
			return null;
		}
		URI url = UriHelper.getRecordURI(params[0]);
		try {
			HttpResponse response = new DefaultHttpClient().execute(new HttpGet(url));
			BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent(),
					Config.JSON_CHARSET));
			StringBuilder json = new StringBuilder();
			String line = reader.readLine();
			while (line != null) {
				if (isCancelled()) {
					return null;
				}
				json.append(line);
				line = reader.readLine();
			}
			JSONObject jsonObj = new JSONObject(json.toString());
			JSONObject object = jsonObj.getJSONObject("object");
			Record record = new Record();
			recordController.jsonParser.parseToObject(object, record);
			return record;
		} catch (IOException e) {
			// ignore
		} catch (JSONException e) {
			// ignore
		} catch (JsonParserException e) {
			// ignore
		}
		return null;	
	}
	
	@Override
	protected void onPostExecute(Record result) {
		if (listener instanceof Activity) {
			((Activity)listener).runOnUiThread(new ListenerNotifier<Record>(listener, result));
		} else {
			listener.onTaskFinished(result);
		}
	}
	
}