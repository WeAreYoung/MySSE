/**
 * 
 */
package Interface;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 * @author Mark
 * 
 */
public class TitleTablePanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8707949178916448461L;

	static int count_category = 3;
	private ContentPanel delegateContentPanel;
	private String[] colomnNames = { "标题", "发布时间" };
	private Object[][] rowData = null;
	private DefaultTableModel tableModel = new DefaultTableModel(rowData,
			colomnNames);
	private JTable titleTable = new JTable();
	private JScrollPane scrollPane = new JScrollPane(titleTable);
	
	private DefaultTableModel tableModel_notice = null;
	private DefaultTableModel tableModel_news = null;
	private DefaultTableModel tableModel_activity = null;
	
	private DefaultTableModel [] tableModel_array = new DefaultTableModel[3];
	/**
	 * 
	 */
	public TitleTablePanel(ContentPanel _contentPanel) {
		// TODO Auto-generated constructor stub
		this.delegateContentPanel = _contentPanel;
		this.tableModel_array[0] = this.tableModel_notice;
		this.tableModel_array[1] = this.tableModel_news;
		this.tableModel_array[2] = this.tableModel_activity;
	}
	
	public void select(int i_categoty) {
		
	}

}
