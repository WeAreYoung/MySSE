/**
 * 
 */
package Interface;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import DataCore.Global;

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
	private Object[][] rowData = {{"测试", "2012-12-31-12:00"}};
	private DefaultTableModel tableModel = new DefaultTableModel(rowData,
			colomnNames);
	private JTable titleTable = new JTable(tableModel);
	private JScrollPane scrollPane = new JScrollPane(titleTable);
	
	private DefaultTableModel tableModel_notice = null;
	private DefaultTableModel tableModel_news = null;
	private DefaultTableModel tableModel_activity = null;
	private DefaultTableModel [] tableModel_array = new DefaultTableModel[3];
	
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
		scrollPane.setLocation(0,0);
		scrollPane.setSize(500,620);
		this.add(scrollPane);
	}
	
	public boolean select(int i_category) {
		
		switch (i_category) {
		case Global.NOTICE:
			
			break;
		case Global.NEWS:

			break;
		case Global.ACTIVITY:

			break;
		}
		if (this.tableModel_array[i_category] == null) {
			
			/*
			 * get data from server
			 */
			this.tableModel_array[i_category] = new DefaultTableModel();
		}
		
		return true;
	}

}
