import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import com.sun.xml.internal.fastinfoset.tools.PrintTable;


public class Tables {
	private ArrayList<String[][]> allData;
	private ArrayList<String[]> allColumn;
	private ArrayList<String[]> allTables;
	private ArrayList<String> nameTable;
	private JComboBox<Object> jCBDatabase;
	private String[] columnHeader;
	private JFrame fG;
	private int contTablas = 0;
	private static int numFila = 0;
	
	public Tables(){
		allData = new ArrayList<String[][]>();
		allColumn = new ArrayList<String[]>();
		allTables = new ArrayList<String[]>();
		nameTable = new ArrayList<String>();
		fG = new JFrame();
		contTablas = 0;
		numFila = 0;
	}
	
	public void addDatabase(String tituloD, String[] titulosTabla){
		numFila = 0;
		allTables.add(titulosTabla);
		nameTable = new ArrayList<String>();
		for (String t: titulosTabla)
			nameTable.add(t);
	}
	
	public void addTableSelect(String[] table){
		allTables.add(table);
	}
	
	public void setTableHeader(String[] columnHeader){
		this.columnHeader = columnHeader;
	}
	
	public int getIndexOfColumn(String column){
		for (int i=0; i<columnHeader.length; i++){
			if (columnHeader[i].equals(column)){
				return i;
			}
			else if(columnHeader[i].substring(columnHeader[i].indexOf(".")+1, columnHeader[i].length()).equals(column)){
				return i;
			}
		}
		return -1;
	}
	
	public void orderTable(final String[][] orderParam){
		printTable(orderParam);
		
		Collections.sort(allTables,new Comparator<String[]>() {
            public int compare(String[] strings, String[] otherStrings) {
            	int result = 0;
            	for (int i=0; i<orderParam.length; i++){
            		String[] orderActual = orderParam[i];
        			int index=-1;
        			if (orderActual[2]!=null){
        				index = getIndexOfColumn(orderActual[2]+"."+orderActual[0]);
        				if (index==-1){
        					index = getIndexOfColumn(orderActual[0]);
        				}				
        			}
        			else{
        				index = getIndexOfColumn(orderActual[0]);
        			}
        			final int indexOf = index;
        			if (index!=-1){
        				if(orderActual[1].toUpperCase().equals("ASC")){
        					Collections.reverse(allTables);
        				}
        			}
        			else{
        				System.out.println("Column "+orderActual[0]+" does not exist.");
        			}
        			if (result==0){
        				result = strings[indexOf].compareTo(otherStrings[indexOf]);
        			}
            	}
                return result;
            }
        });
		
		for(int i =0; i<orderParam.length; i++){
			
		}
		showTable();
	}
	
	
	
	public void addTables(String tituloT, String[] encabezados, int cantFilas){
		contTablas+=1;
		
		// se podria hacer un JComboBox
		String[][] aDatos = new String[cantFilas][encabezados.length];
		allColumn.add(encabezados);
		allData.add(aDatos);
		numFila = 0;
	}
	
	public void addData(String[] columnas){
		String[][] tActual = allData.get(contTablas-1);
		for (int i=0; i<columnas.length; i++){
			tActual[numFila][i] = columnas[i];
		}
		//imprimirTabla(tActual);
		allData.set(contTablas-1, tActual);
		numFila+=1;
	}
	
	public void printTable(String[][] tabla){
		for (int i=0; i<tabla.length; i++){
			for (int j=0; j<tabla[i].length; j++){
				System.out.print(tabla[i][j]+" ");
			}
			System.out.println();
		}
	}
	
	public void showTable(){
		 try{
	        	fG = new JFrame();
	        	String[][] data = new String[allTables.size()][allTables.get(0).length];
	        	for (int i=0 ; i<allTables.size(); i++){
	        		data[i] = allTables.get(i);
	        	}
	        	JTable table = new JTable(data,columnHeader);
	        	table.setPreferredScrollableViewportSize(new Dimension(1000, 70));
                table.setEnabled(false);
                table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
                table.setFillsViewportHeight(true);
                JScrollPane scrollPane = new JScrollPane(table);
                fG.getContentPane().add(scrollPane, BorderLayout.CENTER);
                fG.pack();
                fG.setVisible(true);
                fG.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	            
	        }catch(Exception e){
	            e.printStackTrace();
	        }
	}
	
	public void showTables(){
        try{
        	fG = new JFrame();
        	//System.out.println(nameTable);
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
	                //System.out.println(data.length);
	                
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
