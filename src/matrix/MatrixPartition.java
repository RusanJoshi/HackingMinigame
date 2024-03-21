package matrix;

import java.awt.Color;
import java.awt.GridLayout;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.Border;

public class MatrixPartition extends JPanel {
    ArrayList<MatrixCell> matrixCellArrayList = new ArrayList<>();
    int matrixDimension;
    int columnNumber;
    Border highlightBorder = BorderFactory.createTitledBorder(null,"00",2,0,null,Color.WHITE);

    public ArrayList<MatrixCell> getMatrixCellArrayList() {
        return matrixCellArrayList;
    }
    public void setColumnNumber(int columnNumber) {
        this.columnNumber = columnNumber;
    }

    public MatrixPartition(int pMatrixDimension){
        this.setLayout(new GridLayout(5,0));
        this.setBackground(Color.BLACK);
        this.setSize(60,250);
        this.setBorder(highlightBorder);
        this.matrixDimension = pMatrixDimension;
        this.columnNumber = 0;

        addMatrixCellsToPartition();
    }

    // instantiates, initializes, and sets rowNumber(int) value to MatrixCells
    // MatrixCell is then added ArrayList matrixCellArrayList
    private void addMatrixCellsToPartition(){
        for(int count = 0; count < matrixDimension; count++){
            matrixCellArrayList.add(new MatrixCell());
            matrixCellArrayList.get(count).setRowNumber(count);
            this.add(matrixCellArrayList.get(count));
        }
    }

    // updates the value corresponding with the number of occurrences in the partition
    public void updateOccurrenceValue(int pOccurrence){
        highlightBorder = BorderFactory.createTitledBorder(null,String.valueOf(pOccurrence),2,0,null,Color.WHITE);
        this.setBorder(highlightBorder);
    }
}
