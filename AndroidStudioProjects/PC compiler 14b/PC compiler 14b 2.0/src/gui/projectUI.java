package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JViewport;
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
	private JTextField projectPathTF, projectNameTF;
	private JFrame thisFrame;
	private ArrayList<JPanel> groupFile;
	private String[] asmFileContent;

	/**
	 * Create the frame.
	 */
	public projectUI(String[] fileContent) {
		thisFrame = this;
		asmFileContent = fileContent;
		groupFile = new ArrayList<JPanel>();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 470, 585);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		String[] groupName = { "Header file", "Source file", "Lib",
				"Other file", "Doc file" };
		addGroupFile(groupName);

		thisFrame.setResizable(false);
		thisFrame.setTitle(constants.APPNAME + " " + constants.VERSION);
	}

	protected void reSize() {// JPanel[] r, int pos, int offset) {
		//
		// for (int index = pos + 1; index < r.length; index++) {
		// r[index].setLocation((int)r[index].getX(), (int)r[index].getY() +
		// offset);
		// }
		// Rectangle rec = r[pos].getBounds();
		// rec.resize((int)rec.getWidth(), (int)rec.getHeight() + offset);
		// r[pos].setBounds(rec);
		Dimension d = thisFrame.getSize();
		// d.setSize((int)d.getWidth(), (int)d.getHeight() + 34);
		thisFrame.pack();
		thisFrame.setSize(d);
	}

	private final int LEFTMARGIN = 10;
	private final int TOPMARGIN = 10;
	private final int WIDTH = 440;
	private final int HEIGHT = 100;

	protected void addGroupFile(String[] groupName) {
		JPanel rowProjectPath = new JPanel();
		rowProjectPath.setBounds(new Rectangle(LEFTMARGIN, TOPMARGIN, WIDTH,
				HEIGHT / 3));
		contentPane.add(rowProjectPath);
		rowProjectPath.setLayout(new RelativeLayout(RelativeLayout.X_AXIS, 10));

		JLabel lblProjectPath = new JLabel("Project path ");
		rowProjectPath.add(lblProjectPath, new Float(1));
		projectPathTF = new JTextField();
		rowProjectPath.add(projectPathTF, new Float(3));
		final JButton btnBrowse = new JButton("Browse");
		btnBrowse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser fc = new JFileChooser();
				fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				int result = fc.showDialog(thisFrame, "Select project Folder");
				if ((result == JFileChooser.CANCEL_OPTION)
						|| (result == JFileChooser.ERROR_OPTION)) {
					return;
				} else {
					projectPathTF.setForeground(Color.BLACK);
					projectPathTF.setText(fc.getSelectedFile().getPath());
				}
			}
		});
		rowProjectPath.add(btnBrowse, new Float(1));

		JPanel rowProjectName = new JPanel();
		rowProjectName
				.setBounds(new Rectangle(LEFTMARGIN, 48, 350, HEIGHT / 3));
		contentPane.add(rowProjectName);
		rowProjectName.setLayout(new RelativeLayout(RelativeLayout.X_AXIS, 10));
		JLabel lblProjectName = new JLabel("Project Name");
		rowProjectName.add(lblProjectName, new Float(1));
		projectNameTF = new JTextField();
		projectNameTF.addFocusListener(new FocusListener() {

			@Override
			public void focusLost(FocusEvent e) {

			}

			@Override
			public void focusGained(FocusEvent e) {
				projectNameTF.setForeground(Color.BLACK);
				if (projectNameTF.getText().equals(
						constants.PROJECTUI_EMPTYPROJECTNAME_ERROR)
						|| projectNameTF
								.getText()
								.equals(constants.PROJECTUI_EXISTINGPROJECTFOLDER_ERROR)) {
					projectNameTF.setText("");
				}
			}
		});
		rowProjectName.add(projectNameTF, new Float(3));

		int loopTime = 0;
		for (String group : groupName) {
			JPanel row = new JPanel();
			groupFile.add(row);
			row.setBounds(new Rectangle(LEFTMARGIN, 81 + TOPMARGIN * (loopTime + 1)
					+ HEIGHT * loopTime, WIDTH, HEIGHT));
			contentPane.add(row);
			row.setLayout(null);
			JLabel lblOtherFiles = new JLabel(group);
			final JPanel otherFiles = new JPanel();
			JScrollPane oScrollPane = new JScrollPane(otherFiles);
			JButton btnAddFile = new JButton("Add file");

			row.add(lblOtherFiles);
			row.add(oScrollPane);
			row.add(btnAddFile);

			lblOtherFiles.setBounds(10, 0, 400, 20);
			oScrollPane
					.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
			oScrollPane.setBounds(10, 25, 420, 50);
			otherFiles.setBounds(0, 0, 420, 50);
			otherFiles.setLayout(new BoxLayout(otherFiles, BoxLayout.Y_AXIS));
			btnAddFile.setBounds(10, 80, 80, 20);
			btnAddFile.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					JFileChooser fc = new JFileChooser();
					fc.showOpenDialog(thisFrame);
					otherFiles.add(UICreator.createAddFileRow(fc
							.getSelectedFile().getPath()));
					reSize();
				}
			});

			loopTime++;
		}

		// add button
		loopTime++; 
		JPanel rowButton = new JPanel();
		contentPane.add(rowButton);
		rowButton.setBounds(new Rectangle(LEFTMARGIN, TOPMARGIN
				* (loopTime - 1) + HEIGHT * loopTime, WIDTH, HEIGHT / 4));
		rowButton.setLayout(null);

		JButton btnBack = new JButton("Back");
		rowButton.add(btnBack);
		JButton btnCreate = new JButton("Create folder");
		rowButton.add(btnCreate);

		btnBack.setBounds(110, 0, 70, 20);
		btnBack.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				configUI config = new configUI();
				config.setVisible(true);
				thisFrame.setVisible(false);
			}
		});

		btnCreate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String projectPath = projectPathTF.getText();
				String projectName = projectNameTF.getText();
				boolean check = true;
				if ((projectPath == null)
						|| (projectPath
								.equals(constants.PROJECTUI_NOPROJECTPATH_ERROR))
						|| (projectPath.length() == 0)) {
					projectPathTF
							.setText(constants.PROJECTUI_NOPROJECTPATH_ERROR);
					projectPathTF.setForeground(Color.RED);
					check = false;
				}
				if ((projectName == null)
						|| (projectName.length() == 0)
						|| (projectName
								.equals(constants.PROJECTUI_EXISTINGPROJECTFOLDER_ERROR))) {
					projectNameTF
							.setText(constants.PROJECTUI_EMPTYPROJECTNAME_ERROR);
					projectNameTF.setForeground(Color.RED);
					check = false;
				} else if (projectName
						.equals(constants.PROJECTUI_EMPTYPROJECTNAME_ERROR)) {
					check = false;
				}

				if (!check) {
					return;
				} else {
					Object[] filePath = new Object[groupFile.size()];
					ArrayList<String> movingFile = new ArrayList<String>();
					int groupNumber = 0;
					for (JPanel pane : groupFile) {
						JPanel listFile = (JPanel)((JViewport)((JScrollPane) pane
								.getComponent(1)).getComponent(0)).getComponent(0);
						int numberOfFile = listFile.getComponentCount();
						ArrayList<String> fileName = new ArrayList<String>();
						
						for (int index = 0; index < numberOfFile; index++) {
							JTextField filenameTF = (JTextField) ((JPanel) listFile
									.getComponent(index)).getComponent(0);
							fileName.add(filenameTF.getText());
							JCheckBox moveCB = (JCheckBox) ((JPanel) listFile
									.getComponent(index)).getComponent(1);
							if (moveCB.isSelected()) {
								movingFile.add(filenameTF.getText());
							}
						}
						filePath[groupNumber] = (Object) fileName;
						groupNumber++;
					}
					String pathSep = System.getProperty("file.separator");
					if (!projectPath.endsWith(pathSep)) {
						projectPath += pathSep; 
					}					
					if (!FHandler.createProjectFolders(projectPath							
								+ projectName, filePath, movingFile)) {
						projectNameTF
								.setText(constants.PROJECTUI_EXISTINGPROJECTFOLDER_ERROR);
						projectNameTF.setForeground(Color.RED);
						// check = false;
						return;
					}
				}
				mainUI frame = new mainUI(asmFileContent, projectPath
						+ projectName);
				frame.setVisible(true);
				thisFrame.dispose();
			}
		});
		btnCreate.setBounds(220, 0, 110, 20);

		thisFrame.setBounds(0, 0, WIDTH + 20, 40 + TOPMARGIN * (loopTime + 1)
				+ HEIGHT * loopTime);
	}
}
