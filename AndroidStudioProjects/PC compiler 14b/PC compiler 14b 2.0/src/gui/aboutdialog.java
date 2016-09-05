package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JTextArea;

import util.constants;

public class aboutdialog extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = -798124269257875729L;

	/**
	 * Create the dialog.
	 */
	public aboutdialog() {
		setTitle(constants.APPNAME + constants.VERSION);
		setBounds(100, 100, 450, 300);
		
		JButton btnOk = new JButton("Ok");
		btnOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
			}
		});
		btnOk.setPreferredSize(new Dimension(30, 30));
		getContentPane().add(btnOk, BorderLayout.SOUTH);
		
		JTextArea txtrCopyrightAll = new JTextArea();
		txtrCopyrightAll.setText(constants.ABOUTSTRING);
		getContentPane().add(txtrCopyrightAll, BorderLayout.CENTER);
		
	}

}
