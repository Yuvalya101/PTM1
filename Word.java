package test;

public class Word {
    public Tile[] tiles;
	public int row;//the location of the first tile of the word in the board
	public int col;
	public boolean vertical;//if the word writen from up to down is 
	
    
	
	public Word(Tile[] tiles,int row,int col,boolean vertical) {
		this.tiles=tiles;
        this.row=row;
        this.col = col;
        this.vertical=vertical;
    }


    
    @Override
    public String toString() {
        String str="";
        for (int i = 0; i <this.tiles.length; i++) {
            str+=this.tiles[i].letter;
        }
        return str;
    }



    public Tile[] getTiles(){
        return this.tiles;
    }

    public int getRow(){
        return this.row;
    }

    public int getCol(){
        return this.col;
    }
      
    public boolean getVertical(){
        return this.vertical;
    }

     
   @Override
   public boolean equals(Object obj) {
	   Word w = (Word) obj;
	   return this.tiles == w.tiles && this.row == w.row && this.col==w.col && this.vertical==w.vertical;
   }

  
		   
}
