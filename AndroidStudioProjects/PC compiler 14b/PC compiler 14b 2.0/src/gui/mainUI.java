package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.Box;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTree;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

import util.FHandler;
import util.UICreator;
import util.constants;
import util.myfile;
import util.other;

public class mainUI extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4092098521539379006L;
	private JPanel contentPane;
	private JTabbedPane fileTabPane;
	private FileTab currentTab;
	private JFrame thisFrame;
	private JLabel lblCursorPos;
	private JTree tree;
	private DefaultMutableTreeNode treeNode;
	private TreePath treePath;
	private String projectPath;
	/**
	 * Create the frame.
	 */
	public mainUI(String config[], String pPath) {
		thisFrame = this;
		lblCursorPos = new JLabel("0");
		projectPath = pPath;
		setTitle(constants.APPNAME + constants.VERSION);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);

		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		final JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);

		JMenuItem mntmOpen = new JMenuItem("Open");
		mntmOpen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser fc = new JFileChooser();
				fc.showOpenDialog(fileTabPane);
				FileTab tab = null;
				try {
					tab = UICreator.createFileTab(fc.getSelectedFile(), lblCursorPos);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				fileTabPane.addTab(tab.getFileName(), null, tab,
						constants.FILETAB_TOOLTIP);
				fileTabPane.setSelectedIndex(fileTabPane.getTabCount() - 1);
			}
		});
		mnFile.add(mntmOpen);

		JMenuItem mntmSave = new JMenuItem("Save");
		mntmSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				currentTab = (FileTab) fileTabPane.getSelectedComponent();
				currentTab.saveFile();
			}
		});
		mnFile.add(mntmSave);

		JMenuItem mntmSaveAs = new JMenuItem("Save as");
		mntmSaveAs.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				currentTab = (FileTab) fileTabPane.getSelectedComponent();
				currentTab.saveAsFile();
			}
		});

		mnFile.add(mntmSaveAs);

		JMenuItem mntmExit = new JMenuItem("Exit");
		mntmExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				thisFrame.dispose();
			}
		});

		JMenuItem mntmClose = new JMenuItem("Close");
		mntmClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				currentTab = (FileTab) fileTabPane.getSelectedComponent();
				currentTab.close();
				fileTabPane.remove(currentTab);
			}
		});
		mnFile.add(mntmClose);
		mnFile.add(mntmExit);

		JMenu mnProject = new JMenu("Project");
		menuBar.add(mnProject);

		JMenuItem mntmOpenProject = new JMenuItem("Open project");
		mnProject.add(mntmOpenProject);

		JMenuItem mntmSaveProject = new JMenuItem("Save project");
		mnProject.add(mntmSaveProject);

		JMenuItem mntmPackExport = new JMenuItem("Pack & export project");
		mnProject.add(mntmPackExport);

		JMenuItem mntmRefreshProject = new JMenuItem("Refresh project");
		mnProject.add(mntmRefreshProject);
		mntmRefreshProject.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				DefaultMutableTreeNode newTree = 
						other.getFolderStructure(new File(projectPath));				
				treeNode.remove(0);
				treeNode.add(newTree);				
				tree.updateUI();		
			}
		});
		
		JMenuItem mntmCloseProject = new JMenuItem("Close project");
		mnProject.add(mntmCloseProject);

		JMenu mnTool = new JMenu("Tool");
		menuBar.add(mnTool);

		JMenuItem mntmConversionCalculator = new JMenuItem(
				"Conversion calculator");
		mnTool.add(mntmConversionCalculator);

		JMenuItem mntmAsciiTable = new JMenuItem("ASCII table");
		mnTool.add(mntmAsciiTable);

		JMenu mnHelp = new JMenu("Help");
		menuBar.add(mnHelp);

		JMenuItem mntmAbout = new JMenuItem("About");
		mntmAbout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					aboutdialog dialog = new aboutdialog();
					dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					dialog.setModal(true);
					dialog.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		mnHelp.add(mntmAbout);
		contentPane = new JPanel();
		contentPane.setBorder(new MatteBorder(0, 0, 0, 1, (Color) new Color(0,
				0, 0)));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		Box horizontalBox = Box.createHorizontalBox();
		horizontalBox.setPreferredSize(new Dimension(200, 22));
		horizontalBox.setBackground(Color.LIGHT_GRAY);
		horizontalBox
				.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		horizontalBox.setBorder(new LineBorder(new Color(0, 0, 0)));
		contentPane.add(horizontalBox, BorderLayout.SOUTH);

		JLabel lblStatus = new JLabel("Status");
		lblStatus.setHorizontalAlignment(SwingConstants.CENTER);
		lblStatus.setHorizontalTextPosition(SwingConstants.LEFT);
		lblStatus.setPreferredSize(new Dimension(80, 16));
		lblStatus.setBorder(new MatteBorder(0, 0, 0, 1, (Color) new Color(0, 0,
				0)));
		horizontalBox.add(lblStatus);


		lblCursorPos.setHorizontalAlignment(SwingConstants.CENTER);
		lblCursorPos.setPreferredSize(new Dimension(80, 16));
		lblCursorPos.setBorder(new MatteBorder(0, 0, 0, 1, (Color) new Color(0,
				0, 0)));
		horizontalBox.add(lblCursorPos);

		JLabel lblProgress = new JLabel("Progress");
		lblProgress.setHorizontalAlignment(SwingConstants.CENTER);
		lblProgress.setPreferredSize(new Dimension(80, 16));
		horizontalBox.add(lblProgress);

		JProgressBar progressBar = new JProgressBar();
		progressBar.setMaximumSize(new Dimension(32767, 16));
		progressBar.setMinimumSize(new Dimension(10, 16));
		progressBar.setPreferredSize(new Dimension(90, 16));
		lblProgress.setLabelFor(progressBar);
		horizontalBox.add(progressBar);

		fileTabPane = new JTabbedPane(JTabbedPane.TOP);
		String projectName = projectPath.substring(projectPath.lastIndexOf("\\"), projectPath.length());
		File newfile = new File(projectPath + constants.SOURCEFOLDER + "\\" + projectName + ".asm");
		/*
		 * this.getClass().
		 * getClassLoader()
		 * .getResource
		 * ("").getPath() +
		 * "code.asm");
		 */

		String name = other.createFixedLengthString(other.LEFT,
				newfile.getName(), constants.STRINGLENGTH);
		SimpleDateFormat sdf = new SimpleDateFormat(constants.DATEFORMAT);
		String date = other.createFixedLengthString(other.LEFT,
				sdf.format(new Date(System.currentTimeMillis())),
				constants.STRINGLENGTH);
		String space = other.createFixedLengthString(other.LEFT, "",
				constants.STRINGLENGTH);
		String introS = String.format(constants.INTROFORM, name, date, space,
				space, space, space);
		String configS = String.format(constants.CONFIGSTRINGFORM, config[0],
				config[1], config[2], config[3]);
		String out[] = { introS, configS };
		FHandler.outputAsm(out, newfile);

		FileTab newtab = null;
		try {
			newtab = UICreator.createFileTab(newfile, lblCursorPos);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		fileTabPane.addTab(newtab.getFileName(), null, newtab,
				newtab.getFileSize());
		contentPane.add(fileTabPane, BorderLayout.CENTER);
		fileTabPane.addChangeListener(new ChangeListener() {
			
			@Override
			public void stateChanged(ChangeEvent e) {
				if (fileTabPane.getTabCount() > 0) {
					int caretPos = ((FileTab) fileTabPane
							.getSelectedComponent()).getCaretPos();
								lblCursorPos.setText(Integer.toString(caretPos));	
				} else {
					lblCursorPos.setText("");
				}
			}
		});

		DefaultMutableTreeNode node = other.getFolderStructure(new File(
				projectPath));
		treeNode = new DefaultMutableTreeNode();
		treeNode.add(node);
		tree = new JTree(treeNode);
		tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
		// add popup menu
		JPopupMenu pmenu = createPopupMenu();
		tree.setComponentPopupMenu(pmenu);
		tree.addTreeSelectionListener(new TreeSelectionListener() {
			
			@Override
			public void valueChanged(TreeSelectionEvent e) {
				System.out.println("selected ");
				DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode)tree.getLastSelectedPathComponent();
				myfile selectedFile = (myfile) selectedNode.getUserObject();
				if ((selectedFile == null ) || (!selectedFile.isFile())) {
					return;
				}
//				System.out.println(selectedFile.getPath());
				int numberOfTabs = fileTabPane.getTabCount();
//				System.out.println("numberOfTabs " + numberOfTabs);
				for (int index = 0; index < numberOfTabs; index++) {
					FileTab tabOfTabPane = (FileTab)fileTabPane.getComponent(index);
					String filePath = tabOfTabPane.getFilePath();
					if (filePath.equals(selectedFile.getPath())) {
						return;
					}
				}
				FileTab newtab = null;
				try {
					newtab = UICreator.createFileTab(selectedFile, lblCursorPos);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				fileTabPane.addTab(newtab.getFileName(), null, newtab,
						newtab.getFileSize());		
			}
		});
		
		tree.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {

			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				int clickTime = e.getClickCount();
				int clickedButton = e.getButton();
				int x = e.getX();
				int y = e.getY();
				treePath = tree.getPathForLocation(x, y);

				if ((DefaultMutableTreeNode)tree.getLastSelectedPathComponent() == null) {
					return;
				}
				JPopupMenu pmenu = tree.getComponentPopupMenu();				
				if ((clickTime == 1) && (clickedButton == MouseEvent.BUTTON3)) {
					pmenu.show(tree, x, y);
				} else {
					
				}
			}
		});
		
		JScrollPane projectFolderStructure = new JScrollPane(tree);
		projectFolderStructure.setBounds(0, 0, 160, 1000);
//		JFrame projectFolderStructure = new JFrame();
//		
//		projectFolderStructure.add(scFolderStructure);
		contentPane.add(projectFolderStructure, BorderLayout.WEST);
	
	}
	
	protected JPopupMenu createPopupMenu() {
		JPopupMenu pmenu = new JPopupMenu();
		Action renameAc = new AbstractAction() {
			
			/**
			 * 
			 */
			private static final long serialVersionUID = 1854281774141418870L;

			@Override
			public void actionPerformed(ActionEvent e) {
				DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode)tree.getLastSelectedPathComponent();
				myfile selectedFile = (myfile) selectedNode.getUserObject();
				JFileChooser fc = new JFileChooser(selectedFile.getPath());
				if (fc.showSaveDialog(thisFrame) != JFileChooser.APPROVE_OPTION) {
					return;
				}
				selectedFile.renameTo(new File(fc.getSelectedFile().getPath()));
				treeNode.remove(0);
				treeNode.add(other.getFolderStructure(new File(projectPath)));
				tree.collapsePath(treePath);
				tree.updateUI();				
			}
		};
		renameAc.putValue(Action.NAME, "Rename");
		
		Action deleteAc = new AbstractAction() {
			
			/**
			 * 
			 */
			private static final long serialVersionUID = -524456416243821233L;

			@Override
			public void actionPerformed(ActionEvent e) {
				DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode)tree.getLastSelectedPathComponent();
				myfile selectedFile = (myfile) selectedNode.getUserObject();
				selectedFile.delete();
				
				treeNode.remove(0);
				treeNode.add(other.getFolderStructure(new File(projectPath)));
				tree.collapsePath(treePath);
				tree.updateUI();				
			}
		};
		deleteAc.putValue(Action.NAME, "Delete");
		Action copyAc = new AbstractAction() {
			
			/**
			 * 
			 */
			private static final long serialVersionUID = -8684029818808169526L;

			@Override
			public void actionPerformed(ActionEvent e) {
				DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode)tree.getLastSelectedPathComponent();
				myfile selectedFile = (myfile) selectedNode.getUserObject();
				JFileChooser fc = new JFileChooser(selectedFile.getPath());
				if (fc.showSaveDialog(thisFrame) != JFileChooser.APPROVE_OPTION) {
					return;
				}
				FHandler.copyFile(selectedFile.getPath(), fc.getSelectedFile().getPath());
				treeNode.remove(0);
				treeNode.add(other.getFolderStructure(new File(projectPath)));
				tree.collapsePath(treePath);
				tree.updateUI();				
			}
		};
		copyAc.putValue(Action.NAME, "Copy");
		Action addFileAc = new AbstractAction() {
			
			/**
			 * 
			 */
			private static final long serialVersionUID = -3773752338231660057L;

			@Override
			public void actionPerformed(ActionEvent e) {
				DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode)tree.getLastSelectedPathComponent();
				myfile selectedFile = (myfile) selectedNode.getUserObject();
				JFileChooser fc = new JFileChooser(selectedFile.getPath());
				if (fc.showSaveDialog(thisFrame) != JFileChooser.APPROVE_OPTION) {
					return;
				}
				try {
					fc.getSelectedFile().createNewFile();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				treeNode.remove(0);
				treeNode.add(other.getFolderStructure(new File(projectPath)));
				tree.collapsePath(treePath);
				tree.updateUI();
			}
		};
		addFileAc.putValue(Action.NAME, "New file");
		
		pmenu.add(addFileAc);
		pmenu.add(renameAc);
		pmenu.add(deleteAc);
		pmenu.add(copyAc);
		return pmenu;
	}
}