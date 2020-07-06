package dice;

import java.util.ArrayList;

public class MetaCombo {
	private ArrayList<Combo> meta;
	private String name;
	
	public MetaCombo(String n){
		meta = new ArrayList<Combo>();
		name = n;
	}
	
/*----------------------------------
 * 		  Getters
 */
	public Combo[] getCombos(){
		return meta.toArray(new Combo[meta.size()]);
	}
	public String getName(){
		return name;
	}
/*----------------------------------
 * 		  Setters
 */
	public void addCombo(Combo c){
		meta.add(c);
	}
	public void removeCombo(int i){
		meta.remove(i);
	}
	public void addChain(int i, Combo c){
		meta.get(i).setNext(c);
	}
	public void removeChain(int i, int e){	//untested until I get ComboPopup display to work
		if(e == 1){
			meta.set(i, meta.get(i).getNext());
		}
		Combo c = meta.get(i);
		for(int in = 1; in < (e-1); ++in){
			c = c.getNext();
		}
		c.setNext(c.getNext().getNext());
	}
	public void setName(String n){
		name = n;
	}
}
