/**
 * 
 */
package Interface;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

import DataCore.Global;

/**
 * @author Mark
 * 
 */
public class CategorySelect extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -833635473722496397L;

	private TitleTablePanel delegateTiTleTablePanel;

	private ImageIcon notice_img = new ImageIcon("116.gif");
	private ImageIcon news_img = new ImageIcon("117.gif");
	private ImageIcon activity_img = new ImageIcon("118.gif");
	private JButton notice = new JButton(notice_img);
	private JButton news = new JButton(news_img);
	private JButton activity = new JButton(activity_img);

	/**	
	 * 
	 */
	public CategorySelect(TitleTablePanel _delegateTitleTablePanel) {
		this.delegateTiTleTablePanel = _delegateTitleTablePanel;
		notice.addActionListener(new ButtonAction());
		news.addActionListener(new ButtonAction());
		activity.addActionListener(new ButtonAction());

		this.setLayout(new GridLayout(1, 3));
		notice.setIcon(notice_img);

		this.add(notice);
		this.add(news);
		this.add(activity);
		// TODO Auto-generated constructor stub

		ArrayList<HashMap<String, String>> arraylist = new ArrayList<HashMap<String, String>>();
		for (HashMap<String, String> map : arraylist) {
			String title = (String) map.get("title");
		}

	}

	class ButtonAction implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			Object name = e.getSource();
			if (name == notice) {
				delegateTiTleTablePanel.select(Global.NOTICE);
			} else if (name == news) {
				delegateTiTleTablePanel.select(Global.NEWS);
			} else if (name == activity) {
				delegateTiTleTablePanel.select(Global.ACTIVITY);
			}
		}
	}

}
