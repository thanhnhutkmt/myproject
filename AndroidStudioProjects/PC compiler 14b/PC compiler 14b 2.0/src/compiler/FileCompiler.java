/**
 * 
 */
package compiler;

import java.io.File;
import java.util.ArrayList;

import exception.PreProcessorException;

import util.FHandler;
import util.constants;
import util.other;

/**
 * @author ThanhNhut
 *
 */
public class FileCompiler {
	private final String[] preProcessor = 
		{"#define", "#error", "#if", "#ifdef", "#ifndef", 
		 "#else", "#elif", "#endif", "#include", "#line",
		 "#undef"};
	
	private ArrayList<File> listFile;
	private String currentCompiledFilePath;
	
	public FileCompiler(File codeCFile) {
		listFile = new ArrayList<File>();
		listFile.add(codeCFile);
		currentCompiledFilePath = FHandler.getFilePath(codeCFile, true);
	}
	
	public ArrayList<String> loadCodeC(File f) throws PreProcessorException {
		ArrayList<String> code = (ArrayList<String>) 
				FHandler.readFile(f, 0, 2);
		
		// compile preprocessor 
		for (int lineNumber = 0; lineNumber < code.size(); lineNumber++) {
			String codeLine = code.get(lineNumber).trim();
			for (int index = 0; index < preProcessor.length; index++) {
				switch(index) {
				 	case 0:
				 		// #define
						if (codeLine.startsWith(preProcessor[index])) {
							
						}
						break;
				 	case 1:
				 		// #error
						if (codeLine.startsWith(preProcessor[index])) {
							
						}
						break;
				 	case 2:
						if (codeLine.startsWith(preProcessor[index])) {
							
						}
						break;
				 	case 3:
						if (codeLine.startsWith(preProcessor[index])) {
							
						}
						break;
				 	case 4:
						if (codeLine.startsWith(preProcessor[index])) {
							
						}
						break;
				 	case 5:
						if (codeLine.startsWith(preProcessor[index])) {
							
						}
						break;
				 	case 6:
						if (codeLine.startsWith(preProcessor[index])) {
							
						}
						break;
				 	case 7:
						if (codeLine.startsWith(preProcessor[index])) {
							
						}
						break;
				 	case 8:
				 		// #include
						if (codeLine.startsWith(preProcessor[index])) {
							if (codeLine.contains(" ")) {
								codeLine = codeLine.substring(codeLine.indexOf(" ") + 1);								
								if (codeLine.startsWith("<") && codeLine.endsWith(">")) {
									String includeFilePath = System.getProperty("user.dir") 
											+ System.getProperty("file.separator") 
												+ constants.DFLIB 
													+ codeLine.substring(1, codeLine.length() - 1);
									File includedFile = new File(includeFilePath); 
									listFile.add(includedFile);
									code.remove(lineNumber);
									ArrayList<String> includedFileContent = loadCodeC(includedFile);									
									code.addAll(lineNumber, includedFileContent);
									lineNumber += includedFileContent.size() - 1;
								} else if (codeLine.startsWith("\"") && codeLine.endsWith("\"")) {
									String includeFilePath = codeLine.substring(1, codeLine.length() - 1);									
									File includedFile;
									if (includeFilePath.substring(
											0, includeFilePath.indexOf(
												System.getProperty("file.separator")))
													.matches("[A-Z]\\x3A")) {
										includedFile = new File(includeFilePath); 
									} else {
										includeFilePath = currentCompiledFilePath + includeFilePath;
										includedFile = new File(includeFilePath);
									}
									if (!includedFile.exists()) {
										break;
									}
									listFile.add(includedFile);
									code.remove(lineNumber);
									ArrayList<String> includedFileContent = loadCodeC(includedFile);									
									code.addAll(lineNumber, includedFileContent);
									lineNumber += includedFileContent.size() - 1;
								} else {
									throw new PreProcessorException(
											PreProcessorException.INCLUDESYNTAXERROR, lineNumber);
								}
							} else {
								throw new PreProcessorException(
										PreProcessorException.INCLUDENOSPACEERROR, lineNumber);
							}
						}
						break;
				 	case 9:
						if (codeLine.startsWith(preProcessor[index])) {
							
						}
						break;
				 	case 10:
						if (codeLine.startsWith(preProcessor[index])) {
							
						}
						break;				 		
				}
			}
		}			
		
		return code;
	}
	
	public String[] splitFunction() throws PreProcessorException {
		ArrayList<String> codeFromFile = loadCodeC(listFile.get(0));
		int numberOfCodeLine = codeFromFile.size();
		boolean startNewFunction = false;
		ArrayList<String> functionList = new ArrayList<String>();
		String globalVarDec = "";
		String functionPrototype = "";
		ArrayList<Integer> functionPos = new ArrayList<Integer>();
		String currentLine = "";
		for (int lineNumber = 0, startPos = 0; lineNumber < numberOfCodeLine; 
				lineNumber++) {
			currentLine = codeFromFile.get(lineNumber).trim();
			if (currentLine.matches(constants.FUNCTIONHEADERPATTERN3)) {
				functionPos.add(startPos, lineNumber);
				functionPos.add(startPos + 1, 0);
				startPos += 2;
			}
		}
		
		int functionPosCurrentSize = functionPos.size(); 
		int startPos = 2;
		for (int endPos = 1; startPos < functionPosCurrentSize; 
				startPos += 2) {
			int previousFunctionHeaderpos = functionPos.get(startPos - 2);
			int endPosLineNumber = functionPos.get(startPos) - 1;
			while (endPosLineNumber > previousFunctionHeaderpos) {
				currentLine = codeFromFile.get(endPosLineNumber).trim();
				if (currentLine.equals("}")) {
					functionPos.remove(endPos);
					functionPos.add(endPos, endPosLineNumber);
					endPos += 2;
					break;
				}
				endPosLineNumber--;
			}
		}
		startPos -= 2;
		int lastFunctionStartPos = functionPos.get(startPos);
		for (int lineNumber = codeFromFile.size() - 1; 
				lineNumber > lastFunctionStartPos; lineNumber--) {
			currentLine = codeFromFile.get(lineNumber).trim();
			if (currentLine.equals("}")) {
				functionPos.remove(startPos + 1);
				functionPos.add(startPos + 1, lineNumber);
			}
		}		

		// get function
		int numberOfFunctions = functionPos.size() / 2;
		for (int functionNumber = 0; 
				functionNumber < numberOfFunctions; functionNumber++) {
			int currentFunctionStartPos = functionPos.get(functionNumber * 2);
			int currentFunctionEndPos = functionPos.get((functionNumber * 2) + 1) ;
			String currentFunction = "";
			for (int functionLine = currentFunctionStartPos;
					functionLine <= currentFunctionEndPos; functionLine++) {
				currentFunction += codeFromFile.get(functionLine);
			}
			functionList.add(currentFunction);
		}
		
		// get function prototype and global variable declaration
		int numberOfNonFunctionArea = 0;
		if (functionPos.get(0) != 0) {
//			int firstAreaStartPos = 0;
			int firstAreaEndPos = functionPos.get(0) - 1;
			for (int functionLine = 0;
					functionLine <= firstAreaEndPos; functionLine++) {
				currentLine = codeFromFile.get(functionLine).trim();
				if (currentLine.matches(constants.FUNCTIONPROTOTYPEPATTERN)) {
					functionPrototype += currentLine;
				} else if (currentLine.matches(constants.GLOBALVARPATTERN)) {
					globalVarDec += currentLine;
				} 
			}
		}			
		/*
		x      | 0 1 2 3..|
		Ystart | 1 3 5 7..| 2x + 1
		Yend   | 2 4 6 8..| 2x + 2
		*/
		numberOfNonFunctionArea = (functionPos.size() / 2) - 1;
		for (int areaNumber = 0; 
				areaNumber < numberOfNonFunctionArea; areaNumber++) {
			int areaStartPos = functionPos.get((areaNumber * 2) + 1) + 1;
			int areaEndPos = functionPos.get((areaNumber * 2) + 2) - 1;
			for (int functionLine = areaStartPos;
					functionLine <= areaEndPos; functionLine++) {
				currentLine = codeFromFile.get(functionLine).trim();
				if (currentLine.matches(constants.FUNCTIONPROTOTYPEPATTERN)) {
					functionPrototype += currentLine;
				} else if (currentLine.matches(constants.GLOBALVARPATTERN)) {
					globalVarDec += currentLine;
				} 
			}
		}

		String outPut[] = other.toArray(functionList);
		return outPut;
	}
}

//if (startNewFunction) {				
//	if (currentLine.equals("}")) {				
//		String nextLine1 = codeFromFile.get(index + 1).trim();					
//		if (nextLine1.equals("")) {
//			if ((index + 2) < numberOfCodeLine) {
//				String nextLine2 = codeFromFile.get(index + 2).trim();
//				if (nextLine2.matches(
//						constants.FUNCTIONHEADERPATTERN3) || nextLine2.matches(
//								constants.FUNCTIONPROTOTYPEPATTERN))
//				currentFunction += currentLine;					
//				functionList.add(currentFunction);
//				currentFunction = new String("");
//				startNewFunction = false;
//				index++;
//			} else {
//				currentFunction += currentLine;					
//				functionList.add(currentFunction);
//				currentFunction = new String("");
//				startNewFunction = false;
//				break;
//			}
//		}
//	} else {
//		currentFunction += currentLine;	
//	}					
//} else {
//	if (currentLine.matches(constants.FUNCTIONHEADERPATTERN3)) {
//		startNewFunction = true;
//		currentFunction = currentLine;
//	} else if (currentLine.matches(constants.FUNCTIONPROTOTYPEPATTERN)) {
//		functionPrototype += currentLine;
//	} else if (currentLine.matches(constants.GLOBALVARPATTERN)) {
//		globalVarDec += currentLine;
//	}
//}			
