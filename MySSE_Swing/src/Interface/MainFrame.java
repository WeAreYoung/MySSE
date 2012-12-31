/**
 * 
 */
package Interface;

import java.awt.HeadlessException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

/**
 * @author Mark
 *
 */
public class MainFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1590347189659897856L;

	/**
	 * @throws HeadlessException
	 */

	private ContentPanel content=new ContentPanel();
	private TitleTablePanel title=new TitleTablePanel(content);
	private  CategorySelect category=new CategorySelect(title);
	
	public MainFrame() throws HeadlessException {
		// TODO Auto-generated constructor stub

		this.setBounds(0, 0, 1360, 720);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		category.setBounds(0, 0, 1360, 100);
		category.setBorder(new TitledBorder(""));
		title.setBounds(0, 100, 500, 620);
		title.setBorder(new TitledBorder(""));
		content.setBounds(500, 100, 860, 620);
		content.setBorder(new TitledBorder(""));
		
		this.add(category);
		this.add(title);
		this.add(content);
		
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new MainFrame();
		// TODO Auto-generated method stub
	}

}
