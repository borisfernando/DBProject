import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.PrintWriter;
import java.util.concurrent.Future;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextPane;
import javax.swing.text.AttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.text.TabSet;
import javax.swing.text.TabStop;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;



public class GUI {

	private JFrame frame;
	private JTextPane tConsola2;
	private JScrollPane sConsola;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI window = new GUI();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public GUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		//Tabuladores para el JTextPane
		TabStop[] tabs = new TabStop[7];
	    tabs[0] = new TabStop(15, TabStop.ALIGN_LEFT, TabStop.LEAD_NONE);
	    tabs[1] = new TabStop(30, TabStop.ALIGN_LEFT, TabStop.LEAD_NONE);
	    tabs[2] = new TabStop(45, TabStop.ALIGN_LEFT, TabStop.LEAD_NONE);
	    tabs[3] = new TabStop(60, TabStop.ALIGN_LEFT, TabStop.LEAD_NONE);
	    tabs[4] = new TabStop(75, TabStop.ALIGN_LEFT, TabStop.LEAD_NONE);
	    tabs[5] = new TabStop(90, TabStop.ALIGN_LEFT, TabStop.LEAD_NONE);
	    tabs[6] = new TabStop(105, TabStop.ALIGN_LEFT, TabStop.LEAD_NONE);
	    TabSet tabset = new TabSet(tabs);
		
	    StyleContext sc = StyleContext.getDefaultStyleContext();
	    AttributeSet aset = sc.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.TabSet, tabset);
		
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		frame.getContentPane().add(tabbedPane, BorderLayout.CENTER);
		JPanel pConsola = new JPanel();
		tabbedPane.addTab("Console", null, pConsola, null);
		
		JButton bCompilar = new JButton("Compilar");
		bCompilar.setBounds(184, 5, 73, 23);
		tConsola2 = new JTextPane();
		tConsola2.setText("");
		tConsola2.setBackground(new Color(255, 255, 255));
		tConsola2.setBounds(1, 1, 6, 20);
		tConsola2.setParagraphAttributes(aset, false);
		TextLineNumber tln = new TextLineNumber(tConsola2);
		
		pConsola.add(tConsola2);
		pConsola.setLayout(null);
		
		sConsola = new JScrollPane(tConsola2);
		sConsola.setBounds(171, 500, 500, 23);
		sConsola.setRowHeaderView(tln);
		
		GroupLayout gl_pConsola = new GroupLayout(pConsola);
		gl_pConsola.setHorizontalGroup(
			gl_pConsola.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_pConsola.createSequentialGroup()
					.addContainerGap()
					.addComponent(sConsola, GroupLayout.DEFAULT_SIZE, 282, Short.MAX_VALUE)
					.addGap(34)
					.addComponent(bCompilar)
					.addGap(30))
		);
		gl_pConsola.setVerticalGroup(
			gl_pConsola.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pConsola.createSequentialGroup()
					.addGroup(gl_pConsola.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_pConsola.createSequentialGroup()
							.addContainerGap()
							.addComponent(sConsola, GroupLayout.DEFAULT_SIZE, 211, Short.MAX_VALUE))
						.addGroup(gl_pConsola.createSequentialGroup()
							.addGap(100)
							.addComponent(bCompilar)))
					.addContainerGap())
		);
		pConsola.setLayout(gl_pConsola);
		
		bCompilar.addActionListener(new ActionListener()  {
			public void actionPerformed(ActionEvent arg0) {
				try{
					/*String textoConsola = tConsola2.getText();
					tConsola2.setText("");
					tConsola2.setForeground(Color.BLACK);
					appendToPane(tConsola2, textoConsola, Color.BLACK);*/
					
					ANTLRInputStream input = new ANTLRInputStream(tConsola2.getText());
					GSQLLexer lexer = new GSQLLexer(input);
					CommonTokenStream tokens = new CommonTokenStream(lexer);
					GSQLParser parser = new GSQLParser(tokens);
					//Future<JDialog> tree2 = parser.database().inspect(parser);
					ParseTree tree = parser.database();
					DBVisitor visitor = new DBVisitor();
					visitor.visit(tree);
					
					System.out.println("DBACTUAL : "+visitor.returnDBActual());
				}catch(Exception e){
					e.printStackTrace();
				}
			}
		});
	}
	private void appendToPane(JTextPane tp, String msg, Color c)
	{
	    StyleContext sc = StyleContext.getDefaultStyleContext();
	    AttributeSet aset = sc.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Foreground, c);

	    aset = sc.addAttribute(aset, StyleConstants.FontFamily, "Guisha");
	    aset = sc.addAttribute(aset, StyleConstants.Alignment, StyleConstants.ALIGN_JUSTIFIED);

	    int len = tp.getDocument().getLength();
	    tp.setCaretPosition(len);
	    tp.setCharacterAttributes(aset, false);
	    tp.replaceSelection(msg);
	}

}
