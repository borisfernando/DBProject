import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;


public class Table {
	private ArrayList<ArrayList<String[]>> allDatabases;
	private ArrayList<String[][]> allData;
	private ArrayList<String[]> allColumn;
	private ArrayList<String[]> allTables;
	private ArrayList<String> nameTable;
	private JComboBox<Object> jCBDatabase;
	private JFrame fG;
	private int contTablas = 0;
	private static int numFila = 0;
	
	public Table(){
		allDatabases = new ArrayList<ArrayList<String[]>>();
		allData = new ArrayList<String[][]>();
		allColumn = new ArrayList<String[]>();
		allTables = new ArrayList<String[]>();
		nameTable = new ArrayList<String>();
		fG = new JFrame();
		contTablas = 0;
		numFila = 0;
	}
	
	public void agregarDatabase(String tituloD, String[] titulosTabla){
		numFila = 0;
		allTables.add(titulosTabla);
		nameTable = new ArrayList<String>();
		for (String t: titulosTabla)
			nameTable.add(t);
	}
	
	public void agregarTabla(String tituloT, String[] encabezados, int cantFilas){
		contTablas+=1;
		
		// se podria hacer un JComboBox
		String[][] aDatos = new String[cantFilas][encabezados.length];
		allColumn.add(encabezados);
		allData.add(aDatos);
		numFila = 0;
	}
	
	public void agregarDatosTabla(String[] columnas){
		String[][] tActual = allData.get(contTablas-1);
		for (int i=0; i<columnas.length; i++){
			tActual[numFila][i] = columnas[i];
		}
		//imprimirTabla(tActual);
		allData.set(contTablas-1, tActual);
		numFila+=1;
	}
	
	public void imprimirTabla(String[][] tabla){
		for (int i=0; i<tabla.length; i++){
			for (int j=0; j<tabla[i].length; j++){
				System.out.print(tabla[i][j]+" ");
			}
			System.out.println();
		}
	}
	
	public void mostrarTabla(){
        try{
        	fG = new JFrame();
        	System.out.println(nameTable);
        	String[] n = new String[nameTable.size()];
        	for (int i=0; i<nameTable.size(); i++){        		
        		n[i] = nameTable.get(i);
        	}
        	jCBDatabase = new JComboBox<Object>(n);
        	
        	JButton bV = new JButton("Visualizar");
        	JPanel pPrincipal = new JPanel();
        	pPrincipal.add(jCBDatabase);
        	pPrincipal.add(bV);
        	fG.getContentPane().add(pPrincipal);
            bV.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					String nombre = jCBDatabase.getSelectedItem().toString();
					JFrame frame = new JFrame(nombre);
					int numT = nameTable.indexOf(nombre);
	                String[] columnNames = allColumn.get(numT);
	                String[][] data = allData.get(numT);
	                System.out.println(data.length);
	                
	                JTable table = new JTable(data,columnNames);
	                table.setPreferredScrollableViewportSize(new Dimension(1000, 70));
	                table.setEnabled(false);
	                table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
	                table.setFillsViewportHeight(true);
	                JScrollPane scrollPane = new JScrollPane(table);
	                frame.getContentPane().add(scrollPane, BorderLayout.CENTER);
	                frame.pack();
	                frame.setVisible(true);
	                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	                
				}
			});
            fG.pack();
            fG.setVisible(true);
            fG.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            numFila = 0;
            
        }catch(Exception e){
            e.printStackTrace();
        }
    }
	
}
