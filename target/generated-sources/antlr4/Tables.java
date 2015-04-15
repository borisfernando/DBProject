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
	private ArrayList<ValueType[]> allRows;
	private JComboBox<Object> jCBDatabase;
	private String[] columnHeader;
	private String[] columnSelect;
	private JFrame fG;
	private int contTablas = 0;
	private static int numFila = 0;
	
	public Tables(){
		allData = new ArrayList<String[][]>();
		allColumn = new ArrayList<String[]>();
		allTables = new ArrayList<String[]>();
		nameTable = new ArrayList<String>();
		allRows = new ArrayList<ValueType[]>();
		fG = new JFrame();
		contTablas = 0;
		numFila = 0;
	}
	
	public void reset(){
		allData = new ArrayList<String[][]>();
		allColumn = new ArrayList<String[]>();
		allTables = new ArrayList<String[]>();
		nameTable = new ArrayList<String>();
		allRows = new ArrayList<ValueType[]>();
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
	
	public void addTable(String[] table){
		allTables.add(table);
	}
	
	public void addTableSelect(ValueType[] table){
		allRows.add(table);
	}
	
	public void setColumnSelect(String[] columnSelect){
		this.columnSelect = columnSelect;
	}
	
	public void setTableHeader(String[] columnHeader){
		this.columnHeader = columnHeader;
	}
	
	public void printHeader(String[] header){
		for (String h: header){
			System.out.print(h+" ");
		}
	}
	
	public int getIndexOfColumn(String column){
		for (int i=0; i<columnHeader.length; i++){
			if (columnHeader[i].equals(column)){
				return i;
			}
			else if(columnHeader[i].substring(columnHeader[i].indexOf("."), columnHeader[i].length()).equals(column)){
				return i;
			}
		}
		return -1;
	}

	public int getIndexOfColumnSelect(String column){
		for (int i=0; i<columnSelect.length; i++){
			if (columnSelect[i].equals(column)){
				return i;
			}
			else if(columnSelect[i].substring(columnSelect[i].indexOf("."), columnSelect[i].length()).equals(column)){
				return i;
			}
		}
		return -1;
	}

	
	public void orderTable(final String[][] orderParam){
		boolean error = false;
		for (int i=0; i<orderParam.length; i++){
			String[] orderActual = orderParam[i];
			int index = getIndexOfColumn(orderActual[0]);
			if (index==-1){
				index = getIndexOfColumn("."+orderActual[0]);
				if (index==-1){
					System.out.println("Column "+orderActual[0]+" does not exist. Or is not in the select.");
					error = true;
				}
			}
		}
		if (!error){
			Collections.sort(allRows,new Comparator<ValueType[]>() {
		        public int compare(ValueType[] strings, ValueType[] otherStrings) {
		        	int result = 0;
		        	for (int i=0; i<orderParam.length; i++){
		        		String[] orderActual = orderParam[i];
		    			int index = getIndexOfColumn(orderActual[0]);
		    			if (index==-1){
		    				index = getIndexOfColumn("."+orderActual[0]);
		    			}
		    			final int indexOf = index;
		    			if (result==0){
		    				if (strings[indexOf].getName().equals("INT")){
		    					int uno = Integer.parseInt(strings[indexOf].getValue());
		    					int dos = Integer.parseInt(otherStrings[indexOf].getValue());
		    					if (orderActual[1].toUpperCase().equals("DESC")){
		    						result = dos - uno;
		    					}
		    					else{
		    						result = uno - dos;
		    					}
		    				}
		    				else if(strings[indexOf].getName().equals("FLOAT")){
		    					float uno = Float.parseFloat(strings[indexOf].getValue());
		    					float dos = Float.parseFloat(otherStrings[indexOf].getValue());
		    					int p = 1;
		    					if (orderActual[1].toUpperCase().equals("DESC")){
		    						p = -1;
		    					}
		    					if (uno>dos){
		    						result = 1*p;
		    					}
		    					else if(uno<dos){
		    						result = -1*p;
		    					}
		    					else{
		    						result = 0;
		    					}
		    				}
		    				else if (strings[indexOf].getName().equals("DATE")){
		    					String date1 = strings[indexOf].getValue();
		    					int year1 = Integer.parseInt(date1.substring(0, 4));
		    					int month1 = Integer.parseInt(date1.substring(5, 7));
		    					int day1 = Integer.parseInt(date1.substring(8,10));
		    					
		    					String date2 = otherStrings[indexOf].getValue();
		    					int year2 = Integer.parseInt(date2.substring(0, 4));
		    					int month2 = Integer.parseInt(date2.substring(5, 7));
		    					int day2 = Integer.parseInt(date2.substring(8,10));
		    					int p = 1;
		    					if (orderActual[1].toUpperCase().equals("DESC")){
		    						p = -1;
		    					}
		    					
		    					if (year1>year2){
		    						result = 1*p;
		    					}
		    					else if(year1<year2){
		    						result = -1*p;
		    					}
		    					else if(year1==year2){
		    						if (month1>month2){
			    						result = 1*p;
			    					}
			    					else if(month1<month2){
			    						result = -1*p;
			    					}
			    					else if(month1==month2){
			    						if (day1>day2){
				    						result = 1*p;
				    					}
				    					else if(day1<day2){
				    						result = -1*p;
				    					}
				    					else if(day1==day2){
				    						result = 0;
				    					}
			    					}
		    					}
		    					
		    				}
		    				else{
		    					int p = 1;
		    					if (orderActual[1].toUpperCase().equals("DESC")){
		    						p = -1;
		    					}
		    					result = (strings[indexOf].getValue().compareTo(otherStrings[indexOf].getValue()))*p;
		    				}
		    			}
		        	}
		            return result;
		        }
		    });
			showTable();
		}
	}
	
	public void setColumnSelectAll(){
		columnSelect = columnHeader; 
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
	        	Object[][] data = new Object[allRows.size()][columnSelect.length];
	        	for (int i=0 ; i<allRows.size(); i++){
	        		ValueType[] row = new ValueType[columnSelect.length];
	        		for (int j=0; j<columnSelect.length; j++){
	        			int index = getIndexOfColumn(columnSelect[j]);
	        			row[j] = allRows.get(i)[index];
	        		}
	        		data[i] = row;
	        	}
	        	JTable table = new JTable(data,columnSelect);
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
	
	public void show(){
		try{
        	fG = new JFrame();
        	Object[][] data = new Object[allTables.size()][columnSelect.length];
        	for (int i=0 ; i<allTables.size(); i++){
        		String[] row = allTables.get(i);
        		data[i] = row;
        	}
        	JTable table = new JTable(data,columnSelect);
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
