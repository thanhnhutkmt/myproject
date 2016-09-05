import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.UIManager;
import java.awt.Font;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.regex.PatternSyntaxException;

import javax.swing.border.EtchedBorder;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;


public class RegExChecker extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextArea errorcontent;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					RegExChecker frame = new RegExChecker();
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
	public RegExChecker() {
		setTitle("Regular Expression Checker 1.0");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 625, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblRegularExpression = new JLabel("Regular Expression");
		lblRegularExpression.setBounds(10, 14, 104, 14);
		contentPane.add(lblRegularExpression);
		
		textField = new JTextField();
		textField.setFont(new Font("Tahoma", Font.BOLD, 36));
		textField.setBounds(133, 11, 466, 57);
		contentPane.add(textField);
		textField.setColumns(10);
		
		JLabel lblString = new JLabel("String");
		lblString.setBounds(10, 82, 104, 20);
		contentPane.add(lblString);
		
		textField_1 = new JTextField();
		textField_1.setFont(new Font("Tahoma", Font.PLAIN, 18));
		textField_1.setBounds(133, 82, 466, 38);
		contentPane.add(textField_1);
		textField_1.setColumns(10);
		
		JButton btnCheck = new JButton("Check");
		btnCheck.setBounds(176, 131, 73, 23);
		contentPane.add(btnCheck);
		
		JLabel lblResult = new JLabel("Result");
		lblResult.setBounds(292, 135, 46, 14);
		contentPane.add(lblResult);
		
		textField_2 = new JTextField();
		textField_2.setBackground(UIManager.getColor("Button.background"));
		textField_2.setBounds(348, 132, 86, 20);
		contentPane.add(textField_2);
		textField_2.setColumns(10);
		
		JLabel lblError = new JLabel("Error");
		lblError.setBounds(10, 163, 46, 14);
		contentPane.add(lblError);
		
		errorcontent = new JTextArea();
		errorcontent.setBackground(UIManager.getColor("Button.background"));
		JScrollPane scrollPane = new JScrollPane(errorcontent);
		scrollPane.setBounds(10, 185, 590, 60);
		contentPane.add(scrollPane);
		
		btnCheck.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String input = textField_1.getText();
				String regex = textField.getText();			 
				OutputStream out = new OutputStream() {
					
					@Override
					public void write(int b) throws IOException {
						String error = errorcontent.getText();
						error += (char)b;
						errorcontent.setText(error);
					}
				};
				PrintStream output = new PrintStream(out);
				try {
					errorcontent.setText("");
					textField_2.setText(input.matches(regex)? "Matched." : "Not matched.");
				} catch(PatternSyntaxException exception) {
					exception.printStackTrace(output);
				}
			}
		});
	}
}
