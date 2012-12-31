/**
 * 
 */
package Interface;

import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
    private JButton notice=new JButton();
    private JButton news=new JButton();
    private JButton activity=new JButton();
    private ImageIcon notice_img=new ImageIcon("116.gif");
    
	/**	
	 * 
	 */
	public CategorySelect(TitleTablePanel _delegateTitleTablePanel) {
            this.delegateTiTleTablePanel=_delegateTitleTablePanel;
            notice.addActionListener(new ButtonAction());
            news.addActionListener(new ButtonAction());
            activity.addActionListener(new ButtonAction());
            
            this.setLayout(null);
            notice.setBounds(new Rectangle(450,25,120,50));
            news.setBounds(new Rectangle(620,25,120,50));
            activity.setBounds(new Rectangle(790,25,120,50));
            notice.setIcon(notice_img);
            
            this.add(notice);
            this.add(news);
            this.add(activity);
		// TODO Auto-generated constructor stub
	}
        class ButtonAction implements ActionListener
        {
            public void actionPerformed(ActionEvent e)
            {
                Object name=e.getSource();
                if(name == notice)
                {
                    delegateTiTleTablePanel.select(Global.NOTICE);
                }
                else if(name==news)
                {
                    delegateTiTleTablePanel.select(Global.NEWS);
                }
                else if(name==activity)
                {
                	int i=2;
                    delegateTiTleTablePanel.select(Global.ACTIVITY);
                }
            }
        }
}
