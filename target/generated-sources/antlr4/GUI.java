import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintStream;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
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

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					GUI frame = new GUI();
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
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 900, 500);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.X_AXIS));
		
				JPanel panel1 = new JPanel(false);
				contentPane.add(panel1);
				
				JScrollPane consoleScroll = new JScrollPane();
				consoleScroll.setBounds(10, 10, 750, 430);
				
				JTextArea consoleText = new JTextArea();
				consoleText.setFont(new Font("Courier New", Font.PLAIN, 13));
				consoleText.setForeground(Color.WHITE);
				consoleText.setBackground(Color.BLACK);
				//console.setBounds(5, 5, 442, 438);
				consoleScroll.setViewportView(consoleText);
				
				TextLineNumber lines = new TextLineNumber(consoleText);
				consoleScroll.setRowHeaderView(lines);
				
				PrintStream console =new PrintStream(new TextAreaOutputStream(consoleText));
				System.setOut(console);
				System.setErr(console);
				
				panel1.setLayout(null);
				panel1.add(consoleScroll);
				
				JButton compilar = new JButton("Compilar");
				compilar.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent arg0) {
						//Controller compile = new Controller();
						System.out.println("esto");
						//String output = compile.parse(input.getText());
						//console.setText(output);
					}
				});
				compilar.setBounds(780, 100, 100, 25);
				panel1.add(compilar);
		
	}
}