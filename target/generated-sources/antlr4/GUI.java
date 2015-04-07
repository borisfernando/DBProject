import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

public class GUI extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
	private JPanel contentPane;
	private String texto="";
	private static Controller compile;
	private boolean bProgram = false;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					compile = new Controller();
					GUI frame = new GUI();
					WindowListener exitListener = new WindowAdapter() {
			            @Override
			            public void windowClosing(WindowEvent e) {
			                int confirm = JOptionPane.showOptionDialog(null, "¿Desea guardar los cambios antes de salir?", "Exit Confirmation", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
			                if (confirm == 0) {
			                	String database = DBVisitor.returnDBActual();
			                	Xml.guardarDatabase(database, compile.getHm());
			                	Xml.updateDatabases();
			                	Xml.updateDeletedData();
			                	System.exit(0);
			                }
			            }
			        };
					frame.addWindowListener(exitListener);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public GUI() {
		compile = new Controller();
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 950, 500);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.X_AXIS));
		
		JPanel panel1 = new JPanel(false);
		contentPane.add(panel1);
		panel1.setLayout(null);
		
		// Input field
		final JScrollPane inputScroll = new JScrollPane();
		inputScroll.setBounds(10, 10, 750, 180);
		
		final JTextArea inputText = new JTextArea();
		inputText.setFont(new Font("Courier New", Font.PLAIN, 13));
		
		inputScroll.setViewportView(inputText);
		
		TextLineNumber lines = new TextLineNumber(inputText);
		inputScroll.setRowHeaderView(lines);
		
		panel1.add(inputScroll);
		
		// Console field
		final JScrollPane consoleScroll = new JScrollPane();
		consoleScroll.setBounds(10, 220, 750, 230);
		
		final JTextArea consoleText = new JTextArea();
		consoleText.setFont(new Font("Courier New", Font.PLAIN, 13));
		consoleText.setForeground(Color.WHITE);
		consoleText.setBackground(Color.BLACK);
		consoleText.setEditable(false);
		
		consoleScroll.setViewportView(consoleText);
		
		
		// Output Stream on console
		PrintStream console =new PrintStream(new TextAreaOutputStream(consoleText));
		System.setOut(console);
		System.setErr(console);
		
		panel1.add(consoleScroll);
		
		final JFileChooser fc = new JFileChooser();
		final JButton cargar = new JButton("Cargar archivo");
		final JButton update = new JButton("Update");
		cargar.addActionListener(new ActionListener() {
			@Override
			@SuppressWarnings("resource")
			public void actionPerformed(ActionEvent arg0) {
				int returnVal = fc.showOpenDialog(GUI.this);
				Scanner scan;
				String programa = "";
				scan = new Scanner("Sin cargar");
		        if (returnVal == JFileChooser.APPROVE_OPTION) {
		            //String file = fc.getSelectedFile();
		        	try{
		        		scan = new Scanner(fc.getSelectedFile());
		        	} catch (Exception e){
		        		System.out.println("Problema al cargar archivo");
		        	}
				}
		        bProgram = false;
		        while (scan.hasNext()){
		        	programa += scan.nextLine() + "\n";
		        }
		        System.out.println("Archivo cargado exitosamente.");
		        if (programa.length()<20000)
		        	inputText.setText(programa);
		        else{
		        	bProgram = true;
		        	texto = programa;
		        	inputText.setText("Archivo muy grande para mostrarlo en este editor.");
		        	inputText.setEditable(false);
		        	cargar.setEnabled(false);
		        	update.setEnabled(false);
		        }
			}});
		
		cargar.setBounds(776, 54, 129, 25);
		panel1.add(cargar);
		
		JButton compilar = new JButton("Compilar");
		compilar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				String textoN = "";
				if (!bProgram){
					texto = inputText.getText();
				}
				
				compile = new Controller();
				compile.parse(texto);
				
				inputText.setEditable(true);
				cargar.setEnabled(true);
	        	update.setEnabled(true);
			}
		});
		compilar.setBounds(780, 100, 100, 25);
		panel1.add(compilar);
		
		update.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				long iniciale = System.currentTimeMillis();
				String database = DBVisitor.returnDBActual();
				Xml.guardarDatabase(database, compile.getHm());
				if (database!=""){
					Xml.serializeArray(database, compile.getArrayListPk());
				}
				Xml.updateDatabases();
				Xml.updateDeletedData();
				long finale = System.currentTimeMillis();
				System.out.println("GUARDAR "+TimeUnit.MILLISECONDS.toSeconds(finale - iniciale));
				
			}
		});
		update.setBounds(780, 140, 100, 25);
		panel1.add(update);
		
		JButton reset = new JButton("Reset");
		reset.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				inputText.setEditable(true);
				inputText.setText("");
				consoleText.setText("");
				cargar.setEnabled(true);
	        	update.setEnabled(true);
	        	bProgram = false;
			}
		});
		reset.setBounds(780, 180, 110, 25);
		panel1.add(reset);
		
	}
}