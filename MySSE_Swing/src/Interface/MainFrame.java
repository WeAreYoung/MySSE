/**
 * 
 */
package Interface;

import java.awt.HeadlessException;

import javax.swing.JFrame;
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

		this.setLayout(null);
		this.setSize(805, 625);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		category.setBounds(10, 10, 780, 100);
		category.setBorder(new TitledBorder(""));
		title.setBounds(10, 120, 300, 475);
		title.setBorder(new TitledBorder(""));
		content.setBounds(320, 120, 470, 475);
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
