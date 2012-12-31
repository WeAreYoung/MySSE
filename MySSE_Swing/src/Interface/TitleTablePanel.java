/**
 * 
 */
package Interface;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import Analysis.AnalysisData;
import DataCore.Global;
import Servelet.BasicServelet;

/**
 * @author Mark
 * 
 */
public class TitleTablePanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8707949178916448461L;

	static int height_row = 30;
	static int count_category = 3;
	private ContentPanel delegateContentPanel;
	private String[] colomnNames = { "标题", "发布时间" };
	private Object[][] rowData = { { "测试", "2012-12-31-12:00" } };
	private DefaultTableModel tableModel = new DefaultTableModel(rowData,
			colomnNames);
	private JTable titleTable = new JTable(tableModel);
	private JScrollPane scrollPane = new JScrollPane(titleTable);

	private DefaultTableModel tableModel_notice = null;
	private DefaultTableModel tableModel_news = null;
	private DefaultTableModel tableModel_activity = null;
	private DefaultTableModel[] tableModel_array = new DefaultTableModel[3];


	private ArrayList<HashMap<String, String>> arrayList = null; 
	public void setTableModel_notice(DefaultTableModel _newModel) {
		this.tableModel_notice = _newModel;
	}

	public void setTableModel_news(DefaultTableModel _newModel) {
		this.tableModel_news = _newModel;
	}
	
	public void setTableModel_activity(DefaultTableModel _newModel) {
		this.tableModel_activity = _newModel;
	}

	/**
	 * 
	 */
	public TitleTablePanel(ContentPanel _contentPanel) {
		// TODO Auto-generated constructor stub
		this.delegateContentPanel = _contentPanel;
		this.tableModel_array[Global.NOTICE] = this.tableModel_notice;
		this.tableModel_array[Global.NEWS] = this.tableModel_news;
		this.tableModel_array[Global.ACTIVITY] = this.tableModel_activity;
		this.titleTable.setRowHeight(TitleTablePanel.height_row);
		this.setLayout(null);
		scrollPane.setLocation(0, 0);
		scrollPane.setSize(300, 770);
		this.titleTable.addMouseListener(new MyMouseAdapter(this));
//		this.titleTable.sete
		this.add(scrollPane);
	}

	public boolean select(int i_category) {
		
		switch (i_category) {
		case Global.NOTICE:
			arrayList = AnalysisData.getDataFromJSONObject(BasicServelet
					.connectGetJSONObject("notice/allTitle.php", null, null));
			break;
		case Global.NEWS:
			arrayList = AnalysisData.getDataFromJSONObject(BasicServelet
					.connectGetJSONObject("news/allTitle.php", null, null));
			break;
		case Global.ACTIVITY:
			arrayList = AnalysisData.getDataFromJSONObject(BasicServelet
					.connectGetJSONObject("activity/allTitle.php", null, null));
			break;
		}
		Object [][] rowData = new Object[arrayList.size()][3];
		int index_row =0;
		for (HashMap<String, String> iter:arrayList) {
			rowData[index_row][0] = iter.get("title");
			rowData[index_row][1] = iter.get("publishTime");
			index_row++;
		}
		if (this.tableModel_array[i_category] == null) {
			this.tableModel_array[i_category] = new DefaultTableModel();
		}
		this.tableModel_array[i_category].setDataVector(rowData, this.colomnNames);
		this.titleTable.setModel(this.tableModel_array[i_category]);
		return true;
	}
	
	class MyMouseAdapter extends MouseAdapter{
		/*
		 * @see
		 * java.awt.event.MouseListener#mouseClicked(java.awt.event.MouseEvent)
		 */
		private TitleTablePanel parent = null;
		MyMouseAdapter(TitleTablePanel _parent) {
			parent = _parent;
		}
		@Override
		public void mouseClicked(MouseEvent e) {
			if (e.getClickCount() == 1) {
				HashMap<String, String> map = arrayList.get(parent.titleTable.getSelectedRow());
				String id = map.get("id");
				parent.delegateContentPanel.get_id(id);
			}
		}
	}
}
