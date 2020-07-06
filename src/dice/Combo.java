package dice;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import javax.swing.JOptionPane;

public class Combo {
	private String name; //???
	private int mul1;
	private int mul2;
	private int die;
	private int constant;
	
	private SecureRandom random;
	private Combo next = null;
	
	public Combo(String n, int m1, int m2, int d, int c){
		name = n;
		mul1 = m1;
		mul2 = m2;
		die = d;
		constant = c;
		try{
			random = SecureRandom.getInstanceStrong(); //We out here throwing mfin' DICE
		}catch(NoSuchAlgorithmException e){
			JOptionPane.showMessageDialog(null, "Failed strong instance.");
			random = new SecureRandom();
		}
	}

/*----------------------------------
 * 		 Getters
 */
	public int getRoll(){
		return mul2*((mul1*(random.nextInt(die)+1)) + constant);
	}
	public String getName(){
		return name;
	}
	public int getMul1(){
		return mul1;
	}
	public int getMul2(){
		return mul2;
	}
	public int getDie(){
		return die;
	}
	public int getConstant(){
		return constant;
	}
	public Combo getNext(){
		return next;
	}
	
/*----------------------------------
 * 		 Setters
 */
	public void setName(String n){
		name = n;
	}
	public void setMul1(int m){
		mul1 = m;
	}
	public void setMul2(int m){
		mul2 = m;
	}
	public void setDie(int d){
		die = d;
	}
	public void setConstant(int c){
		constant = c;
	}
	public void setNext(Combo c){
		next = c;
	}
	public void removeNext(){
		if(next.getNext() != null){
			next = next.getNext();
		}else{
			next = null;
		}
	}
}
