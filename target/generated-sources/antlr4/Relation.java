import java.util.ArrayList;
import javax.swing.JFrame;


public class Relation {
	protected String[][] data;
	protected ArrayList<String[]> allColumn;
	protected JFrame fG;
	protected static int numFila = 0;
	
	public Relation(){
		allColumn = new ArrayList<String[]>();
		fG = new JFrame();
		numFila = 0;
	}
	
	public void addData(String[] columnas){
		//String[][] tActual = data.get(contTablas-1);
		for (int i=0; i<columnas.length; i++){
			//tActual[numFila][i] = columnas[i];
		}
		//imprimirTabla(tActual);
		//data.set(contTablas-1, tActual);
		numFila+=1;
	}
}
