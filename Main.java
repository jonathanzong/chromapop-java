import static java.lang.System.*; import static java.lang.Math.*; import static java.lang.Character.*; import java.util.*; import java.io.*; import java.math.*;
public class Main {
	public static Scanner in;	public static void pl(){out.println();}	public static void pl(Object o ){out.println(o);} public static void pt(Object o) { out.print(o); } public static void plf(String a, Object... o) { out.printf(a, o); pl(); } public static int ni() {return in.nextInt();} public static int pi(String s){return Integer.parseInt(s);} public static String nl(){return in.nextLine();} public static String nt(){return in.next();} public static double pd(String s){return Double.parseDouble(s);}	
    public static void main(String[] args) throws Exception{
        
        Bubble[][] grid = new Bubble[10][10];
        for(int r=0;r<grid.length;r++)
        	for(int c=0;c<grid[r].length;c++)
        		grid[r][c] = new Bubble();
        
        printGrid(grid);
        Object[] arr = doClick(grid, 3,3);
        if(arr!=null){
        	SCORE += (Integer)(arr[0]);
	        printPopArea(grid,(HashSet<Bubble>)arr[1]);
	        deleteAndFill(grid,(HashSet<Bubble>)arr[1]);
	        printGrid(grid);
        }
        else{
        	pl("invalid click");
        }
        
    }
    
    static void printGrid(Bubble[][] grid){
    	pl("Score: "+SCORE);
    	for(int r=0;r<grid.length;r++){
	        for(int c=0;c<grid[r].length;c++)
	        	pt(grid[r][c]);
	        pl();
	    }
	}
	
	static void printPopArea(Bubble[][] grid,HashSet<Bubble> list){
    	for(int r=0;r<grid.length;r++){
	        for(int c=0;c<grid[r].length;c++)
	        	if(list.contains(grid[r][c]))
	        		pt(grid[r][c]);	
	        	else pt(" ");
	        pl();
	    }
	}
    
    public static void deleteAndFill(Bubble[][] grid, HashSet<Bubble> delList){
    	// rotate bubbles down in place of removed
    	for(int r=grid.length-1;r>=0;r--){
			for(int c=grid[r].length-1;c>=0;c--){
				if(delList.contains(grid[r][c])){
					int col = c;
					ArrayList<Bubble> list = new ArrayList<Bubble>();
					for(int row = 0;row<=r;row++){
						list.add(grid[row][col]);
					}
					for(int x=0;x<r&&delList.contains(list.get(list.size()-1));x++)
						Collections.rotate(list,1);
					for(int row = 0;row<=r;row++){
						grid[row][col] = list.get(row);
					}
				}
			}
		}
		// refill bubbles
		for(int r=0;r<grid.length;r++){
			for(int c=0;c<grid[r].length;c++){
				if(delList.contains(grid[r][c])){
					grid[r][c] = new Bubble();
				}
			}
		}
    }
    
    // returns Object[] {score,list to pop} or null if invalid click
    public static Object[] doClick(Bubble[][] grid, int r, int c){
    	HashSet<Bubble> delList;
    	int score;
    	if(!Bubble.isPrimary(grid[r][c].color)){
    		//check for consecutive complex colors
    		delList = new HashSet<Bubble>();
    		processClick(grid,r,c,grid[r][c].color,true,delList);
    		score = delList.size();
    		if(score >= 3){
	    		if(grid[r][c].color == 0xFFFFFF) //white x3 multiplier
	    			score *= 3;
	    		else score *= 2; //non-primary non-white x2 multiplier	    		
	    		return new Object[]{score,delList};
    		}
    		//decompose and pop primary components
    		delList = new HashSet<Bubble>();
    		for(int mask = 0xFF0000; mask>=0x0000FF; mask >>= 8){
    			int prim = grid[r][c].color & mask;
    			if(prim == 0) continue;
    			HashSet<Bubble> templist = new HashSet<Bubble>();
    			processClick(grid,r,c,prim,true,templist);
    			delList.addAll(templist);	
    		}
    		score = delList.size();
    		if(score >= 3){
    			return new Object[]{score,delList};
    		}
    	}
    	else{
    		//pop 3 or more consecutive primaries - decompose complex
    		delList = new HashSet<Bubble>();
    		processClick(grid,r,c,grid[r][c].color,true,delList);
    		score = delList.size();
    		if(score >= 3){
    			return new Object[]{score,delList};
    		}
    	}    	
    	return null;
    }
    
    
    //method fills param-traversed with the bubbles to pop
    private static void processClick(Bubble[][] grid, int r, int c, int curCommon, boolean strictcolors, HashSet<Bubble> traversed){
    	if(r<0 || r>= grid.length || c<0 || c>= grid[r].length)
    		return;
    	if(curCommon == 0)
    		return;
    	if(traversed.contains(grid[r][c]))
    		return;
    	traversed.add(grid[r][c]);
    	for(int x=-1;x<=1;x++){
    		for(int y=-1;y<=1;y++){
    			if(x==0&&y==0||x!=0&&y!=0 || r+x<0 || r+x >= grid.length || c+y<0 || c+y>=grid[r].length) continue;
    			boolean crit = strictcolors ?
    					grid[r+x][c+y].color == curCommon : // strictly equal colors
    					grid[r+x][c+y].containsColor(curCommon); // contains the search color - too OP
    			if(crit){
    				processClick(grid,r+x,c+y,curCommon,strictcolors,traversed);
    			}
    		}
    	}
    }
    
    static int SCORE = 0;
}
