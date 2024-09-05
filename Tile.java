package test;
public class Tile {
 
    public final char letter;
    public final int score;
    
   
   private Tile(char letter,int score){
        this.letter = letter;
        this.score = score;
   }



	@Override
	public String toString() {
		return  letter + "";
	}



@Override
   public boolean equals(Object obj) {
	   Tile t = (Tile) obj;
	   return this.letter == t.letter && this.score == t.score;
   }
   
   public static class Bag{
	   private static Bag singleBag = null;
	   public int[] tilesAmount;
	   public Tile[] tiles;
	   public int amount;
	   
	   
	   private Bag(int[] tilesAmount, Tile[] tiles, int amount) {
		   this.tilesAmount = tilesAmount;
		   this.tiles = tiles;
		   this.amount = amount;
	   }
	   
	   public static Bag getBag() {
		   if(Bag.singleBag == null) {
			   Bag.singleBag = new Bag(new int[] {9,2,2,4,12,2,3,2,9,1,1,4,2,6,8,2,1,6,4,6,4,2,2,1,2,1},
					   new Tile[] {new Tile('A', 1), new Tile('B', 3), new Tile('C', 3), new Tile('D', 2), new Tile('E', 1), new Tile('F', 4), new Tile('G', 2), new Tile('H', 4), new Tile('I', 1), new Tile('J', 8), new Tile('K', 5), new Tile('L', 1), new Tile('M', 3), new Tile('N', 1), new Tile('O', 1), new Tile('P', 3), new Tile('Q', 10), new Tile('R', 1), new Tile('S', 1), new Tile('T', 1), new Tile('U', 1), new Tile('V', 4), new Tile('W', 4), new Tile('X', 8), new Tile('Y', 4), new Tile('Z', 10)},
					   98);
		   }
		   return Bag.singleBag;
	   }
	   
	   public Tile getTile(char letter) {
		   Tile t = null;
		   if(letter >= 'A' && letter <= 'Z') {
			   int index = letter - 'A';
			   if(this.tilesAmount[index] > 0) {
				   this.tilesAmount[index] --;
				   this.amount --;
				   t = this.tiles[index];
			   }
		   }
		   return t;
	   }

	   public Tile getRand() {
		   Tile t = null;
		   if(this.amount > 0) {
			   int index = (int) Math.floor(Math.random() * 26);
			   for(int i = 0; i < 26; i++) {
				   if(this.tilesAmount[index] > 0) {
					   this.tilesAmount[index] --;
					   this.amount --;
					   t = this.tiles[index];
					   break;
				   }
				   index = (index + 1) % 26;
			   }
		   }
		   return t;
	   }
	   
	   public void put(Tile t) {
		   if(this.amount < 98) {
			   if(t.letter >= 'A' && t.letter <= 'Z') {
				   int[] maxTiles = {9,2,2,4,12,2,3,2,9,1,1,4,2,6,8,2,1,6,4,6,4,2,2,1,2,1};
				   int index = t.letter - 'A';
				   if(this.tilesAmount[index] < maxTiles[index]) {
					   this.tilesAmount[index] ++;
					   this.amount ++;
				   }
			   }
		   }
	   }
	   
	   public int size() {
		   return this.amount;
	   }
	   
	   public int[] getQuantities() {
		   return this.tilesAmount.clone();
	   }
   }
    
    
}


