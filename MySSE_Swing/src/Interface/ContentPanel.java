package Interface;

import java.io.IOException;

import javax.swing.JEditorPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class ContentPanel extends JPanel {
	JEditorPane content=new JEditorPane();
	JScrollPane scroll=new JScrollPane(content);
	/**
	 * 
	 */
	private static final long serialVersionUID = -1983274982800634874L;

	public ContentPanel() {
		content.setEditable(false);
		this.setLayout(null);
		scroll.setSize(470, 475);
		this.add(scroll);
		content.setContentType("text/html");
		// TODO Auto-generated constructor stub
	}
	public void get_id(String id)
	{
		String url="http://192.168.1.100/content.php?id="+id;
		try {
			content.setPage(url);
		} catch (IOException e) {
			
		}
		
	}
}
