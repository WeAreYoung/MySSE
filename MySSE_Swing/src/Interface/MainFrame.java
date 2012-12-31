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
	public MainFrame() throws HeadlessException {
		// TODO Auto-generated constructor stub
	}


	/**
	 * @param args
	 */
	public static void main(String[] args) {
		JFrame jf=new JFrame();
		jf.setBounds(0, 0, 1360, 720);
		jf.setVisible(true);
		JPanel p=new JPanel();
		jf.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		ContentPanel content=new ContentPanel();
		TitleTablePanel title=new TitleTablePanel(content);
		CategorySelect category=new CategorySelect(title);
		category.setBounds(0, 0, 1360, 100);
		category.setBorder(new TitledBorder("1"));
		title.setBounds(0, 100, 500, 620);
		title.setBorder(new TitledBorder("2"));
		content.setBounds(500, 100, 860, 620);
		content.setBorder(new TitledBorder("3"));
		jf.add(category);
		jf.add(title);
		jf.add(content);
		jf.add(p);
		// TODO Auto-generated method stub

	}

}
