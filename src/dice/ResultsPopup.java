package dice;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;

@SuppressWarnings("serial")
public class ResultsPopup extends JFrame{
	private Combo[] combos;
	public ResultsPopup(Combo[] c){
		combos = c;
	
/*----------------------------------
 * 			Events
 */
		EventHandler handler = new EventHandler();
	}
	private class EventHandler implements ActionListener{
		public void actionPerformed(ActionEvent event){
			
		}
	}
}

/*
 * I think that what will likely happen is I will have 1 main panel with FlowLayout (horizontal)
 * And I will repeatedly add new panels to it which have BoxLayout (vertical)
 * 
 * Each combo chain will get its own inner panel and display elements vertically, and you'll have many columns.
 * 
 * Combo[] rolls will be done in this class.
 */
