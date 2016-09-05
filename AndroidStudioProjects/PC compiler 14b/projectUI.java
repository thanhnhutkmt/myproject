package gui;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.FileHandler;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import util.FHandler;
import util.RelativeLayout;
import util.UICreator;
import util.constants;

public class projectUI extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4201880682592929312L;
	private JPanel contentPane;
	private JTextField projectPathTF;
	private JFrame thisFrame;
	private JTextArea headerFileTA, sourceFileTA, libFileTA, otherFileTA; 
	
	/**
	 * Create the frame.
	 */
	public projectUI() {
		thisFrame = this;
		final JPanel r[] = new JPanel[6];
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 470, 500);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel row1 = new JPanel();
		row1.setBounds(new Rectangle(10, 10, 440, 30));
		r[0] = row1;
		contentPane.add(row1);
		row1.setLayout(new RelativeLayout(RelativeLayout.X_AXIS, 10));

		JLabel lblProjectPath = new JLabel("Project path");
		row1.add(lblProjectPath, new Float(1));	
		projectPathTF = new JTextField();
		row1.add(projectPathTF, new Float(3));
		projectPathTF.setColumns(10);	
		final JButton btnBrowse = new JButton("Browse");
		btnBrowse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser fc = new JFileChooser();
				fc.showOpenDialog(btnBrowse);
				projectPathTF.setText(fc.getSelectedFile().getPath());
			}
		});
		row1.add(btnBrowse, new Float(1));
		
		final JPanel row2 = new JPanel();		
		row2.setBounds(new Rectangle(10, 50, 440, 300));
		r[1] = row2;
		contentPane.add(row2);
		row2.setLayout(null);
		
		JLabel lblHeaderFiles = new JLabel("Header files");
		row2.add(lblHeaderFiles);
		lblHeaderFiles.setBounds(10, 0, 400, 20);
		headerFileTA = new JTextArea("dsfasdf");
		JList lf = new JList();

		JScrollPane hScrollPane = new JScrollPane(headerFileTA);
		hScrollPane.setBounds(10, 30, 420, 70);
		row2.add(hScrollPane);
		final JButton btnAddHFile = new JButton("Add file");
		btnAddHFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser fc = new JFileChooser();
				fc.showOpenDialog(btnAddHFile);
				String content = headerFileTA.getText();
				content += constants.NEXTLINE + fc.getSelectedFile().getPath();
				headerFileTA.setText(content);
			}
		});
		row2.add(btnAddHFile);
		btnAddHFile.setBounds(10, 110, 110, 30);		
		
		JPanel row3 = new JPanel();
		row3.setBounds(new Rectangle(10, 120, 440, 85));
		r[2] = row3;
		contentPane.add(row3);
		row3.setLayout(new RelativeLayout(RelativeLayout.Y_AXIS, 10));
		
		JLabel lblSourceFiles = new JLabel("Source files");
		row3.add(lblSourceFiles);		
		final JPanel sourceFiles = new JPanel();
		row3.add(sourceFiles);
		JButton btnAddSFile = new JButton("Add file");
		row3.add(btnAddSFile);
		btnAddSFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser fc = new JFileChooser();
				fc.showOpenDialog(btnAddHFile);
				sourceFiles.add(UICreator.createAddFileRow(fc.getSelectedFile().getPath()));
				reSize(r, 2, 34);
			}
		});
		
		JPanel row4 = new JPanel();
		row4.setBounds(new Rectangle(10, 218, 440, 85));
		r[3] = row4;
		contentPane.add(row4);
		row4.setLayout(new RelativeLayout(RelativeLayout.Y_AXIS, 10));
		
		JLabel lblLibFiles = new JLabel("Lib files");
		row4.add(lblLibFiles);		
		final JPanel libFiles = new JPanel();
		row4.add(libFiles);
		JButton btnAddLFile = new JButton("Add file");
		row4.add(btnAddLFile);
		btnAddLFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser fc = new JFileChooser();
				fc.showOpenDialog(btnAddHFile);
				libFiles.add(UICreator.createAddFileRow(fc.getSelectedFile().getPath()));
				reSize(r, 3, 34);
			}
		});
		
		JPanel row5 = new JPanel();
		row5.setBounds(new Rectangle(10, 316, 440, 85));
		r[4] = row5;
		contentPane.add(row5);
		row5.setLayout(new RelativeLayout(RelativeLayout.Y_AXIS, 10));
		
		JLabel lblOtherFiles = new JLabel("Other files");
		row5.add(lblOtherFiles);		
		final JPanel otherFiles = new JPanel();
		row5.add(otherFiles);
		JButton btnAddOFile = new JButton("Add file");
		row5.add(btnAddOFile);
		btnAddOFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser fc = new JFileChooser();
				fc.showOpenDialog(btnAddHFile);
				otherFiles.add(UICreator.createAddFileRow(fc.getSelectedFile().getPath()));
				reSize(r, 4, 34);
			}
		});
		
		JPanel row6 = new JPanel();
		row6.setBounds(new Rectangle(10, 412, 440, 30));
		r[5] = row6;
		contentPane.add(row6);
		row6.setLayout(new RelativeLayout(RelativeLayout.X_AXIS, 50));	
		
		JButton btnBack = new JButton("Back");
		btnBack.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		row6.add(btnBack, new Float(1));
		JButton btnCreate = new JButton("Create folder");
		btnCreate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String config[] = {"", "", "", ""};
				mainUI frame = new mainUI(config);
				frame.setVisible(true);
				reSize(r, 5, 34);
			}
		});
		row6.add(btnCreate, new Float(1));			
	}	
	
	protected void reSize(JPanel[] r, int pos, int offset) {

		for (int index = pos + 1; index < r.length; index++) {
			r[index].setLocation((int)r[index].getX(), (int)r[index].getY() + offset);
		}
		Rectangle rec = r[pos].getBounds();
		rec.resize((int)rec.getWidth(), (int)rec.getHeight() + offset);
		r[pos].setBounds(rec);
		Dimension d = thisFrame.getSize();
		d.setSize((int)d.getWidth(), (int)d.getHeight() + offset);
		thisFrame.pack();
		thisFrame.setSize(d);
	}
}
