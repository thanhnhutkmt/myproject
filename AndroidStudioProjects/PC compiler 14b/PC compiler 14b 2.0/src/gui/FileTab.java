/**
 * 
 */
package gui;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import util.FHandler;
import util.other;

/**
 * @author ThanhNhut
 *
 */
public class FileTab extends JScrollPane{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5347020511249662898L;

	private File file;
	private long currentPos;
	private String fileName;
	private long fileSize;
	private JLabel cursorPosLB;
	private JTextArea fileDisplayer;
	
	public FileTab(long fileReadPos, File f, JTextArea textArea, JLabel pos) {
		super(textArea);
		fileName = f.getName();
		fileSize = f.length();
		fileDisplayer = textArea;
		currentPos = fileReadPos;	
		cursorPosLB = pos;
		file = f;		
		if (fileDisplayer != null) {
			fileDisplayer.addMouseListener(new MouseListener() {
				
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
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void mouseClicked(MouseEvent e) {
					int caretPos = fileDisplayer.getCaretPosition();
					cursorPosLB.setText(Integer.toString(caretPos));
				}
			});
			
			fileDisplayer.addKeyListener(new KeyListener() {
				
				@Override
				public void keyTyped(KeyEvent e) {

				}
				
				@Override
				public void keyReleased(KeyEvent e) {

				}
				
				@Override
				public void keyPressed(KeyEvent e) {
					int caretPos = fileDisplayer.getCaretPosition();
					cursorPosLB.setText(Integer.toString(caretPos));
				}
			});
		}
	}

	public String getFileName() {
		return fileName;
	}

	public String getFileSize() {
		return other.getStringFileSize(fileSize);
	}
	
	public String getFilePath() {
		return file.getPath();
	}
	
	public void saveFile() {
		File tempFile = new File(file.getPath() + "t");
		FHandler.createNewFile(fileDisplayer.getText(), file, tempFile, currentPos, true);
	}
	
	public void saveAsFile() {
		JFileChooser fc = new JFileChooser();
		fc.showSaveDialog(this);	
		FHandler.createNewFile(fileDisplayer.getText(), file, fc.getSelectedFile(), currentPos, false);
	}
	
	public void close() {
		fileName = null;
		fileSize = 0;
		fileDisplayer = null;
		file = null;	
		currentPos = 0;
	}

	public long getCurrentPos() {
		return currentPos;
	}

	public void setCurrentPos(long currentPos) {
		this.currentPos = currentPos;
	}
	
	public String nextString() {
		byte data[] = FHandler.getNextData(currentPos, file);
		
		String fileContent = "";
		if ((data != null) && (data.length > 0)) {		
			StringBuilder fileContentBuilder = new StringBuilder();
			for (int index = 0; index < data.length; index++) {
			    fileContentBuilder.append((char)data[index]);
			}
			fileContent = fileContentBuilder.toString();
			currentPos += data.length;
		}
		
		return fileContent;
	}
	
	public void setText(String text) {
		fileDisplayer.setText(text);
	}
	
	public int getCaretPos() {
		if (fileDisplayer != null) {
			return fileDisplayer.getCaretPosition();
		} else {
			return 0;
		}
	}
}
