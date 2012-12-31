package Analysis;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class AnalysisData {

	/**
	 * @param jsonarray
	 *            ����һ��JSONArray����
	 * @return ����JSONArray����Ӧ�������б�
	 */
	public static ArrayList<HashMap<String, String>> getDataFromJSONArray(
			JSONArray jsonarray) {
		ArrayList<HashMap<String, String>> arraylist = new ArrayList<HashMap<String, String>>();

		if (jsonarray.length() == 0)
			return arraylist;
		try {

			for (int i = 0; i < jsonarray.length(); i++) {
				JSONObject object = jsonarray.getJSONObject(i);
				HashMap<String, String> map = new HashMap<String, String>();
				map.put("title", object.getString("title"));
				map.put("publishTime", object.getString("publishTime"));
				map.put("id", object.getString("id"));
				arraylist.add(map);
			}

		} catch (JSONException e) {
			// ���ݸ�ʽ����
		}

		return arraylist;
	}

	/**
	 * 
	 * @param jsonobject
	 *            ����һ��JSONObject
	 * @return ����JSONObject����Ӧ�������б�
	 */
	public static ArrayList<HashMap<String, String>> getDataFromJSONObject(
			JSONObject jsonobject) {
		try {
			JSONArray jsonarray = jsonobject.getJSONArray("titleArray");
			return getDataFromJSONArray(jsonarray);

		} catch (JSONException e) {
			return new ArrayList<HashMap<String, String>>();
		}
	}
}
