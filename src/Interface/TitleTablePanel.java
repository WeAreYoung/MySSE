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

	private ContentPanel delegateContentPanel;
	private String[] colomnNames = { "标题", "发布时间" };
	private Object[][] rowData = null;
	private DefaultTableModel tableModel = new DefaultTableModel(rowData,
			colomnNames);
	private JTable titleTable = new JTable(tableModel);
	private JScrollPane scrollPane = new JScrollPane(titleTable);

	/**
	 * 
	 */
	public TitleTablePanel(ContentPanel _contentPanel) {
		// TODO Auto-generated constructor stub
		this.delegateContentPanel = _contentPanel;

	}

}
