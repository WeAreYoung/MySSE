package com.example.servelet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

public class BaseServelet {

	static String urladdress = "http://192.168.1.100/";

	public static String connectGetString(String servletName,
			List<NameValuePair> message, HttpClient httpclient) {

		HttpClient httpcurrentclient;
		if (httpclient == null) {
			httpcurrentclient = new DefaultHttpClient();
		} else {
			httpcurrentclient = httpclient;
		}
		List<NameValuePair> currentmessage;
		if (message == null) {
			currentmessage = new ArrayList<NameValuePair>();
		} else {
			currentmessage = message;
		}
		HttpPost request = new HttpPost(urladdress + servletName);
		String str;

		try {
			request.setEntity(new UrlEncodedFormEntity(currentmessage,
					HTTP.UTF_8));
			HttpResponse response = httpcurrentclient.execute(request);
			HttpEntity entity = response.getEntity();
			InputStream content = entity.getContent();
			BufferedReader input = new BufferedReader(new InputStreamReader(
					content, "GB2312"));
			StringBuilder sb = new StringBuilder();
			String line = null;
			try {
				while ((line = input.readLine()) != null) {
					sb.append(line);
				}
			} catch (IOException e) {
			}
			str = sb.toString();
			input.close();

		} catch (Exception e) {
			str = e.toString();
		}
		request.abort();
		return str;
	}

	public static JSONObject connectGetJSONObject(String servletName,
			List<NameValuePair> message, HttpClient httpclient) {

		JSONObject jsonobject = null;
		try {
			jsonobject = new JSONObject(new JSONTokener(connectGetString(
					servletName, message, httpclient)));
		} catch (JSONException e) {
		}

		return jsonobject;

	}
}
