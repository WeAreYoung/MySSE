package Servelet;

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
import org.json.JSONObject;
import org.json.JSONTokener;

public class BasicServelet {

	static String urladdress = "http://192.168.1.100";

	/**
	 * @param serveletName
	 *            ���ӷ�������
	 * @param message
	 *            Post���� ����Ϊnull
	 * @param httpclient
	 *            ����null
	 * @return str �ӷ�����ȡ�õ�String��
	 */
	public static String connectGetString(String serveletName,
			List<NameValuePair> message, HttpClient httpclient) {

		HttpClient currentHttpClient;
		List<NameValuePair> currentmessage;

		if (httpclient == null) {
			currentHttpClient = new DefaultHttpClient();
		} else {
			currentHttpClient = httpclient;
		}

		if (message == null) {
			currentmessage = new ArrayList<NameValuePair>();
		} else {
			currentmessage = message;
		}

		HttpPost request = new HttpPost(urladdress + serveletName);
		String str = "";

		try {
			request.setEntity(new UrlEncodedFormEntity(currentmessage));
			HttpResponse response = currentHttpClient.execute(request);
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
				// ����
			}
			str = sb.toString();
			input.close();
		} catch (Exception e) {
			// ���ӷ��������� str = e.getMessage();
		}
		request.abort();
		return str;
	}

	/**
	 * @param serveletName
	 *            ���ӷ�������
	 * @param message
	 *            Post���� ����Ϊnull
	 * @param httpclient
	 *            ����null
	 * @return str �ӷ�����ȡ�õ�JSONObject
	 */
	public static JSONObject connectGetJSONObject(String serveletName,
			List<NameValuePair> message, HttpClient httpclient) {
		JSONObject jsonobject = null;
		try {
			jsonobject = new JSONObject(new JSONTokener(connectGetString(
					serveletName, message, httpclient)));
		} catch (Exception e) {

		}
		return jsonobject;
	}

}
