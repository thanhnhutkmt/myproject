package gui;

import java.awt.CardLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;

import util.FHandler;
import util.UICreator;
import util.constants;
import util.other;

public class configUI extends JFrame {

	private static final long serialVersionUID = 8583609417781634362L;
	private Hashtable<String, String> configFileTable;
	private String[] deviceList;
	private JComboBox<String> comboBoxDevice;
	private JFrame thisFrame;
	/**
	 * Create the frame.
	 */
	@SuppressWarnings("unchecked")
	public configUI() {
		thisFrame = this;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		final JPanel pane = (JPanel) getContentPane();
		getDeviceList();
		
		final CardLayout layout = new CardLayout();		
		final JPanel selectPanel = UICreator.createSelectPICUI(deviceList);
		pane.setLayout(layout);
		pane.add(selectPanel);

		JButton btnNext = (JButton)((JPanel)selectPanel.getComponent(1)).getComponent(1);
		JButton btnBack = (JButton)((JPanel)selectPanel.getComponent(1)).getComponent(0);
		comboBoxDevice = (JComboBox<String>)((JPanel)selectPanel.getComponent(0)).getComponent(1);
		btnNext.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {				
				EventQueue.invokeLater(new Runnable() {
					public void run() {
						try {
							String device = (String)comboBoxDevice.getSelectedItem();
							String configFile = "";
							Set<String> groupDevice = configFileTable.keySet();
							for (String group : groupDevice) {
								if (group.contains(device)) {
								    configFile = configFileTable.get(group);
								}
							}
							JPanel config = UICreator.createDeviceConfigUI(device, configFile);
							JButton btnBack = (JButton)((JPanel)config.getComponent(config.getComponentCount() - 1)).getComponent(0);
							btnBack.addActionListener(new ActionListener() {
								
								@Override
								public void actionPerformed(ActionEvent e) {
									getDeviceList();
									((JPanel)selectPanel.getComponent(0)).remove(1);
									
									comboBoxDevice = new JComboBox<String>(deviceList);
									((JPanel)selectPanel.getComponent(0)).add(comboBoxDevice, new Float(5));
									layout.previous(pane);
									pane.remove(1);
									thisFrame.setBounds(100, 100, 290, 160);
									//thisFrame.pack();
								}
							});
							pane.add(config, constants.CONFIGUI_TITLE);
							
							layout.next(pane);		
//							layout.show(pane, constants.CONFIGUI_TITLE);
							thisFrame.pack();

						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				});
			}
		});
				
		btnBack.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				thisFrame.dispose();
			}
		});
		
		setTitle(constants.APPNAME + " " + constants.VERSION);		
		layout.first(pane);
		thisFrame.setBounds(100, 100, 290, 160);
//		pack();
	}
	
	private void getDeviceList() {
		String path = System.getProperty("user.dir") + "\\" +constants.DFDLIST;
		String data[] = (String[]) FHandler.readFile(new File(path), -1, 1);
		ArrayList<String> list = new ArrayList<String>();
		String temp = "";
		StringBuilder groupDeviceBuilder = new StringBuilder();
		configFileTable = new Hashtable<String, String>();
		for (int index = 0; index < data.length; index++) {		
			if (data[index].startsWith("[[")) {
				temp = data[index].substring(2);				
			} else if (data[index].startsWith("]]>>")) {
				configFileTable.put(groupDeviceBuilder.toString(), data[index].substring(4));
				groupDeviceBuilder = new StringBuilder();
				continue;
			} else {
				temp = data[index];
			}
			groupDeviceBuilder.append(temp + " ");
			list.add(temp);
		}
		deviceList = other.toArray(list);
	}
}