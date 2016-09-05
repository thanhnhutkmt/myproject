/**
 * 
 */
package util;

import gui.FileTab;
import gui.projectUI;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.text.DefaultCaret;

/**
 * @author ThanhNhut
 * 
 */
public class UICreator {
	public static FileTab createFileTab(File file, JLabel lblcursorPos) throws IOException {
		String fileContent;
		byte data[] = FHandler.getNextData(0, file);
		if (data != null) {
			StringBuilder fileContentBuilder = new StringBuilder();
			for (int index = 0; index < data.length; index++) {
				fileContentBuilder.append((char) data[index]);
			}
			fileContent = fileContentBuilder.toString();
		} else {
			data = new byte[0];
			fileContent = "";
		}
		final JTextArea fileDisplayer = new JTextArea();

		fileDisplayer.setText(fileContent);
		fileDisplayer.setCaretPosition(0);
		final FileTab tab = new FileTab(data.length, file, fileDisplayer, lblcursorPos);
		
		final JScrollBar vScrollBar = tab.getVerticalScrollBar();
		vScrollBar.setOrientation(JScrollBar.VERTICAL);
		vScrollBar.setUnitIncrement(16);
		vScrollBar.addAdjustmentListener(new AdjustmentListener() {

			@Override
			public void adjustmentValueChanged(AdjustmentEvent e) {
				if (vScrollBar.getValue() == vScrollBar.getMaximum()
						- vScrollBar.getHeight()) {
					String fileContent = tab.nextString();
					DefaultCaret caret = (DefaultCaret) fileDisplayer
							.getCaret();
					caret.setUpdatePolicy(DefaultCaret.NEVER_UPDATE);
					fileDisplayer.setText(fileDisplayer.getText() + fileContent);
					caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
				}
			}
		});

		return tab;
	}

	public static JPanel createSelectPICUI(String deviceList[]) {
		final JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(2, 1, 10, 10));
		
		JPanel panel_1 = new JPanel();
		panel.add(panel_1);
		panel_1.setLayout(new RelativeLayout(RelativeLayout.X_AXIS, 16));
		
		JLabel lblNewLabel = new JLabel(constants.SELECTDEVICEUI_DEVICELISTLABEL);
		panel_1.add(lblNewLabel, new Float(1.6));

		final JComboBox<String> comboBox = new JComboBox<String>(deviceList);
		panel_1.add(comboBox, new Float(5));
		
		JPanel panel_2 = new JPanel();
		panel.add(panel_2);
		panel_2.setLayout(new RelativeLayout(RelativeLayout.X_AXIS, 25));
		
		JButton btnClose = new JButton("Close");

		panel_2.add(btnClose, new Float(2));
		
		JButton btnNext = new JButton("Next");
		panel_2.add(btnNext, new Float(2));	
		
		return panel;
	}

	@SuppressWarnings("rawtypes")
	public static JPanel createDeviceConfigUI(final String processor,
			String configFileName) {
		String data[] = (String[]) FHandler.readFile(new File(constants.DFSP
						+ configFileName), -1, 1);
		int line = 0;
		ArrayList<JLabel> groupName = new ArrayList<JLabel>();
		final ArrayList<JComboBox> groupListOption = new ArrayList<JComboBox>();
		ArrayList<String> optionString = null;
		@SuppressWarnings("unused")
		ArrayList<String> selectedOptions = new ArrayList<String>();

		do {
			if (data[line].startsWith("[[")) {
				groupName.add(new JLabel(((String) data[line]).substring(2)));
				optionString = new ArrayList<String>();
			} else if (data[line].startsWith("]]")) {
				groupListOption.add(new JComboBox<String>(other
						.toArray(optionString)));
			} else {
				if (data[line].contains(";")) {
					String option = data[line].substring(0,
							data[line].indexOf(" "));
					optionString.add(data[line].substring(data[line]
							.indexOf(";") + 2) + " " + option);
				}
			}
			line++;
		} while (line < data.length);

		final JPanel panel = new JPanel(new GridLayout(groupName.size() + 2, 1,
				5, 10));
		
		JLabel lb = new JLabel(constants.CONFIGUI_TITLE + processor);		
		lb.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 18));
		lb.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(lb);
		for (int index = 0; index < groupName.size(); index++) {
			JPanel row = new JPanel(
					new RelativeLayout(RelativeLayout.X_AXIS, 3));
			row.add(groupName.get(index), new Float(2));
			row.add(groupListOption.get(index), new Float(5));
			panel.add(row);
		}

		JButton nextBT = new JButton(constants.CONFIGUI_SELECTBT);
		nextBT.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				EventQueue.invokeLater(new Runnable() {
					public void run() {
						try {
							String CONFIG1 = "", CONFIG2 = "", PROCESSOR = "", INCLUDEFILE = "";
							for (int index = 1; index < groupListOption.size() - 2; index++) {
								String desc = (String) groupListOption.get(
										index).getSelectedItem();
								CONFIG1 += " & "
										+ desc.substring(
												desc.lastIndexOf(" _") + 1,
												desc.length());
							}
							for (int index = groupListOption.size() - 2; index < groupListOption
									.size(); index++) {
								String desc = (String) groupListOption.get(
										index).getSelectedItem();
								CONFIG2 += " & "
										+ desc.substring(
												desc.lastIndexOf(" _") + 1,
												desc.length());
							}
							CONFIG1 = CONFIG1.substring(3);
							CONFIG2 = CONFIG2.substring(3);
							PROCESSOR = other.getPICNumber(processor);
							INCLUDEFILE = "p" + PROCESSOR + ".inc";
							String config[] = { PROCESSOR, INCLUDEFILE,
									CONFIG1, CONFIG2 };
//							mainUI frame = new mainUI(config);
//							frame.setVisible(true);
							projectUI frame = new projectUI(config);
							frame.setVisible(true);
							((JFrame)panel.getParent().getParent().getParent().getParent()).dispose();							
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				});
			}
		});
		
		JButton backBT = new JButton(constants.CONFIGUI_BACKBT);
		RelativeLayout rl = new RelativeLayout(RelativeLayout.X_AXIS, 90);
		JPanel panelButton = new JPanel(rl);
		
		panelButton.add(backBT, new Float(3));
		panelButton.add(nextBT, new Float(3));
		panel.add(panelButton);

		return panel;
	}
	
	public static JPanel createAddFileRow(String filePath) {
		JPanel row = new JPanel();
		BoxLayout box = new BoxLayout(row, BoxLayout.X_AXIS);
		row.setLayout(box);
		
		JTextField tfFiles = new JTextField(filePath);
		tfFiles.setBackground(new Color(240, 240, 240));
		tfFiles.setBorder(BorderFactory.createEmptyBorder());

		row.add(tfFiles, new Float(8));
		JCheckBox chbMove = new JCheckBox("Move");
		row.add(chbMove, new Float(2));
		return row;
	}
}
