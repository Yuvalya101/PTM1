package test;

import java.util.ArrayList;

public class Board {
    private static Board singleBoard = null;
    public Tile[][] board;
    public char[][] bonusBoard;
    private boolean isFirst=true;
    private ArrayList<Word> wordsList = new ArrayList<>();
    
   
    private Board() {
        this.board = new Tile[15][15];
        this.bonusBoard = new char[15][15];
        //R-Red Tile(TRIPLE WORD)
        //L-LightBlue Tile(DOUBLE LETTER)
        //Y-Yellow Tile(DOUBLE WORD)
        //B-Blue Tile(TRIPLE LETTER)
        //S-Star Tile(DOUBLE WORD FOR FIRST PLAYER)
        for (int i = 0; i < 15; i++){
            for (int j = 0; j < 15; j++){
                this.bonusBoard[i][j] = ' ';
            }
        }
        //Center star: (7,7) - Yellow
        for (int i = 0; i < 15; i++) {
            for (int j = 0; j < 15; j++) {
                //Red:(0,0) , (0,7) , (7,0) , (0,14), (n,0) , (14,14) , (14,7) , (7,14)
                if ((i == 0 || i == 7 || i == 14) && (j == 0 || j == 7 || j == 14)) {
                    this.bonusBoard[i][j] = 'R';
                }
                //Yellow : (1,1) , (2,2) .... (13,13)
                if (((i == j || 14 - i == j) && ((i >= 1 && i <= 4) || (i >= 10 && i <= 13)))) {
                    this.bonusBoard[i][j] = 'Y';
                }
                //Blue : (1,5) , (1,9) .... (9,13)
                if ((i % 4 == 1) && (j % 4 == 1) && (this.bonusBoard[i][j] != 'Y')) {
                    this.bonusBoard[i][j] = 'B';
                }
                //LightBlue: (3,0) , (6,2) , (7,3) , (8,2) , (11,0) , (6,6)
                if (((i == 3 || i == 11) && j == 0) || ((i == 6 || i == 8) && j == 2) || (i == 7 && j == 3) || (i == 6 && j == 6)) {
                    this.bonusBoard[i][j] = 'L';
                    this.bonusBoard[14-i][j] = 'L';
                    this.bonusBoard[i][14-j] = 'L';
                    this.bonusBoard[14-i][14-j] = 'L';
                }
                if (((j == 3 || j == 11) && i == 0) || ((j == 6 || j == 8) && i == 2) || (j == 7 && i == 3) || (j == 6 && i == 6)) {
                    this.bonusBoard[i][j] = 'L';
                    this.bonusBoard[14-i][j] = 'L';
                    this.bonusBoard[i][14-j] = 'L';
                    this.bonusBoard[14-i][14-j] = 'L';
                }
             }
        }
        this.bonusBoard[7][7] = 'S';
    }

   
    public int getScore(Word w){
        
        int currentSum=0;
        char letter;
        int Y=0;
        int R=0;
        int S=0;

        if(w.vertical){
            for(int i=0 ; i<w.tiles.length;i++){ 
                letter = this.bonusBoard[w.row+i][w.col];
                Tile t;
                if(w.tiles[i] == null) {
                	t = board[w.row+i][w.col];
                }else {
                	t = w.tiles[i];
                }
                currentSum+= t.score;
                switch (letter) {
                    case ' ':
                        break;
                    case 'S':
                    	this.bonusBoard[w.row+i][w.col] = ' ';
                        S++;
                        break;
                    case 'R':
                    	this.bonusBoard[w.row+i][w.col] = ' ';
                        R++;
                        break;
                    case 'Y':
                    	this.bonusBoard[w.row+i][w.col] = ' ';
                        Y++;
                        break;
                    case 'B':
                        currentSum+=2*t.score;
                        break;
                    case 'L':
                        currentSum+=t.score;
                        break;
                    default:
                        currentSum+=0;
                        break;

                }
            
            }
        }
        else{
            for(int i = 0; i < w.tiles.length; i++){
                letter = this.bonusBoard[w.row][w.col+i];
                Tile t;
                if(w.tiles[i] == null) {
                	t = board[w.row][w.col+i];
                }else {
                	t = w.tiles[i];
                }
                currentSum += t.score;
                switch (letter) {
                    case ' ':
                        break;
                    case 'S':
                    	this.bonusBoard[w.row][w.col+i] = ' ';
                        S++;
                        break;
                    case 'R':
                    	this.bonusBoard[w.row][w.col+i] = ' ';
                        R++;
                        break;
                    case 'Y':
                    	this.bonusBoard[w.row][w.col+i] = ' ';
                        Y++;
                        break;
                    case 'B':
                        currentSum+=2*t.score;
                        break;
                    case 'L':
                        currentSum+=t.score;
                        break;
                    default:
                        currentSum+=0;
                        break;

                }
            }

        }

       if(Y>0){
        currentSum=2*currentSum*Y;
       }
       if(R>0){
        currentSum=3*currentSum*R;
       }
       if(S>0){
        currentSum=2*currentSum*S;
       }


        return currentSum;
    }


    public static Board getBoard() {
        if(Board.singleBoard == null) {
            Board.singleBoard = new Board();
            
        } 
        return Board.singleBoard;
    }

    @Override
    public String toString() {
        String str="";
        for (int i = 0; i <this.board.length; i++) {
            for (int j = 0; j < this.board[0].length; j++) {
                if(this.board[i][j]==null){
                    str+="_";
                }
                else{
                    str+=this.board[i][j].letter;
                }
            }
            str+="\n";
        }
        return str;
    }

    public Tile[][] getTiles() {
    
     Tile[][] currentBoard = new Tile[15][15];
    for (int i = 0; i < 15; i++)
      for(int j =0 ; j < 15 ; j++)
        currentBoard[i][j] = this.board[i][j];
    return currentBoard;
    }
//check validtion for the first word only and return
    public boolean isOnTheStar(Word w){
        
        if(w.getVertical()){
            if(w.getCol() == 7 && w.getRow() <= 7 && (w.getRow() + w.getTiles().length - 1) >=7)
            	return true;
        }
        else{
            if(w.getRow()==7 && w.getCol()<=7 && w.getCol()+w.getTiles().length-1>=7)
            	return true;

        }
        return false;
    }

    public boolean theWholeWord(Word w){
        int wordLength = w.tiles.length;
        
        if(0<=w.row && w.row<15 && 0<=w.col && w.col<15){
           if(w.vertical==true){
            if(wordLength+w.row-1<15){
                return true;
            }
           }
           else{
            if(wordLength+w.col-1<15){
                return true;
            }
           }
        }
     return false;  
    }

    private  Word findWordsAround(int row,int col,boolean vertical){

        int firstIndex=0 ; 
        int lastIndex=0;
        if(vertical){
            firstIndex = col;
            lastIndex =col;
            for(int i=firstIndex;i>0;i--){
                if(this.board[row][i-1]==null) {
                    break;                
                }
                else{
                    firstIndex--;
                }
            }
            for(int i=lastIndex;i<14;i++){
                if(this.board[row][i+1]==null) {
                    break;
                }   
                else{
                    lastIndex++;
                }             
            }

        }
        else{
            firstIndex = row;
            lastIndex =row;
            for(int i=row;i>0;i--){
                if(this.board[i-1][col]==null) {
                    break;                
                }
                else{
                    firstIndex--;
                }
            }
            for(int i=row;i<14;i++){
                if(this.board[i+1][col]==null) {
                    break;
                }   
                else{
                    lastIndex++;
                }             
            } 
        }

        if(firstIndex==lastIndex){
            return null;
        }
        else{
        int wordLength = lastIndex - firstIndex + 1;
        Tile[] wordTiles = new Tile[wordLength];
        for (int i = 0; i < wordLength; i++) {
            if (vertical) {
                wordTiles[i] = this.board[row][firstIndex+i];
            } else {
                wordTiles[i] = this.board[firstIndex+i][col];
            }
        }
        if(vertical){
           return new Word(wordTiles, row, firstIndex, vertical);
        }
        else{
           return new Word(wordTiles, firstIndex, col, !vertical);
        }
        // Create and return the Word object
        }
    }

    private boolean tilesAround(int row,int col){
        if ((row > 0 && this.board[row - 1][col] != null)
                || (row < 14 && this.board[row + 1][col] != null)
                || (col > 0 && this.board[row][col - 1] != null)
                || (col < 14 && this.board[row][col + 1] != null))
            return true;
           
     return false;
    }

    public boolean theWordIsLeaning(Word w){
        int iter;
        if(w.vertical==true){
            iter=w.row;  
        }
        else{
            iter=w.col;
        }
        for(int i =0;i<w.tiles.length;i++,iter++){ 
            if(w.vertical){
                if(this.board[iter][w.col]!=null && (w.getTiles()[i]==null || w.getTiles()[i]==this.board[iter][w.col]))return true;
               
                if(tilesAround(iter,w.col)==true)return true;
            }
            else{
                if(this.board[w.row][iter]!=null && (w.getTiles()[i]==null || w.getTiles()[i]==this.board[w.row][iter]))return true;
                if(tilesAround(w.row,iter)==true)return true;

            }
        }
        return false;
    }

    public boolean boardLegal(Word w) {

    	if (theWholeWord(w) == true) {
            if(isFirst == true)
                if(isOnTheStar(w) == true)
                    return true;
                else
                	return false;
            
            if(theWordIsLeaning(w) == true)
            		return true;
    	}  

        return false;
    }
    
    public boolean dictionaryLegal(Word w){
        return true;
    }
   
    public ArrayList<Word> getWords(Word w){
       ArrayList<Word> newWordsList= new ArrayList<>();
       Word currentWord;
       Word nWord;

       Tile[] wordTiles = new Tile[w.tiles.length];
        for (int i = 0; i < w.tiles.length; i++) {
            if (w.vertical) {
                wordTiles[i] = this.board[w.row+i][w.col];
            } else {
                wordTiles[i] = this.board[w.row][w.col+i];
            }
        }

       currentWord= new Word(wordTiles, w.row, w.col, w.vertical);
       wordsList.add(currentWord);
       newWordsList.add(currentWord);

       for(int i=0;i<w.tiles.length;i++){
            if(w.vertical){
                if(w.tiles[i]!=null){
                    nWord = findWordsAround(w.row+i,w.col, w.vertical);
                    if(nWord!=null){
                        newWordsList.add(nWord);
                    }
                }
            }
            else{
                if(w.tiles[i]!=null){
                    nWord = findWordsAround(w.row,w.col+ i, w.vertical);
                    if(nWord!=null){
                        newWordsList.add(nWord);
                    }
                }
            }
       }

        return newWordsList;
    }



    public int tryPlaceWord(Word w){
        int score=0;
        if(boardLegal(w)==false || dictionaryLegal(w)==false) return 0;
        
        if(w.vertical){
            for(int i=0;i<w.tiles.length;i++){
                if(this.board[w.row+i][w.col]==null){
                    this.board[w.row+i][w.col]=w.tiles[i];
                }
            }
        }
        else{
            for(int i=0;i<w.tiles.length;i++){
                if(this.board[w.row][w.col+i]==null){
                    this.board[w.row][w.col+i]=w.tiles[i];
                }
            }

        }
        if(isFirst){isFirst=false;}
        ArrayList<Word> words = getWords(w);
        for (int i = 0; i < words.size(); i++) {
            score+=getScore(words.get(i));
            
        }
        return score;     
    }

   
}
