public class Memory {

    private int[][] memory;

    // 0 = frei
    // 1 = Wand
    // 2 = Besucht

    public Memory(int rows, int columns){
        memory = new int[rows][columns];

        // Oben & Unten Wand setzen
        for(int i = 0; i < columns; i++){
            memory[0][i] = 1;
            memory[rows-1][i] = 1;
        }


        // Links & Rechts Wand setzen
        for(int i = 0; i < rows; i++){
            memory[i][0] = 1;
            memory[i][columns-1] = 1;
        }

    }

    public String toString(){
        String result = "";
        for (int i = 0; i < memory.length; i++) {
            for (int j = 0; j < memory[i].length ; j++) {
                result += memory[i][j];
            }
            result += "\n";
        }
        return result;
    }

    public void visited(int x, int y){
        memory[y][x] = 2;
    }

    public void setChar(int x, int y,int i){
        memory[y][x] = i;
    }

    public boolean checkPosition(int x, int y, int i){
        return memory[y][x] == i;
    }

    public boolean hasPlayerWon(){
        int counter = 0;
        for (int[] ints : memory) {
            for (int i : ints) {
                if(i == 0){
                    if(counter > 8000){
                        return false;
                    }
                    counter++;
                }
            }
        }
        return true;
    }


    public void cleanUp(){
        for (int i = 0; i < memory.length; i++) {
            for (int j = 0; j < memory[i].length; j++) {
                if(memory[j][i] != 0){
                    memory[j][i] = 1;
                }
            }
        }
    }

    public int[][] getMemory(){
        return memory;
    }



}

