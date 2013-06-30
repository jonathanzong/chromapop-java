import java.util.ArrayList;

public class Bubble{
	
	public static final int RED = 0xFF0000;
	public static final int GREEN = 0x00FF00;
	public static final int BLUE = 0x0000FF;
	public static final int CYAN = 0x00FFFF;
	public static final int MAGENTA = 0xFF00FF;
	public static final int YELLOW = 0xFFFF00;
	public static final int WHITE = 0xFFFFFF;
	public static final int[] VALS = {RED, GREEN, BLUE, CYAN, MAGENTA, YELLOW, WHITE};
	
	//
	
	public int color;
	
	public Bubble(){
		this.color = VALS[(int)(Math.random()*VALS.length)];
	}
	
	public Bubble(int c){
		this.color = c;
	}
	
	// returns true if color becomes black after subtraction
	public boolean subtractColor(ArrayList<Integer> coltosubtr){
		for(int i:coltosubtr){
			color &= ~i;
		}
		return color == 0x000000;
	}
	
	// 0 is no common colors - but subtracting it does nothing anyway
	public int commonColor(Bubble other){
		return this.color & other.color;
	}
	
	public boolean containsColor(int c){
		return (this.color | c) == this.color;
	}
	
	public static boolean isPrimary(int c){
		return c==RED || c==GREEN || c==BLUE;
	}
	
	public String toString(){
		switch(this.color){
			case RED:
				return "R";
			case GREEN:
				return "G";
			case BLUE:
				return "B";
			case CYAN:
				return "C";
			case MAGENTA:
				return "M";
			case YELLOW:
				return "Y";
			case WHITE:
				return "W";
		}
		return super.toString();
	}
	
}