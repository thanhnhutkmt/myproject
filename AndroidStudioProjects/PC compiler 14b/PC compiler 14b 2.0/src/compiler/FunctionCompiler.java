/**
 * 
 */
package compiler;

import java.io.File;
import java.util.ArrayList;
import java.util.StringTokenizer;

import exception.DeclareException;

import util.FHandler;
import util.other;

/**
 * @author ThanhNhut
 *
 */
public class FunctionCompiler {
	private static final String FUNCTIONFORM = "";
	private String functionACode;
	public FunctionCompiler() {
		functionACode = FUNCTIONFORM;
	}
		
	@SuppressWarnings("unchecked")
	public Object[] splitStructure(String functionBody) {
		ArrayList<Object> Groups = new ArrayList<Object>();	
		Object[] structures = null;
		
		String[] group = null;
		int index = 0;		
		int startNewGroup = 0;
		ArrayList<String> groupByMark = (ArrayList<String>)
				other.splitCode(functionBody, "{", 2);
		for (int ind = 0; ind < groupByMark.size(); ind = ind + index) {
			ArrayList<String> temp = (ArrayList<String>)
					other.splitCode(groupByMark.remove(ind), "}", 2);
			groupByMark.addAll(ind, temp);
			index = temp.size();
		}
		
		String whileRegex = 
				"\\x77\\x68\\x69\\x6C\\x65\\s{0,}" +
				"\\x28[\\p{ASCII}]{0,}\\x29[\\p{ASCII}]{0,}";
		String whileRegexPlus = 
				"\\x77\\x68\\x69\\x6C\\x65\\s{0,}" +
				"\\x28[\\p{ASCII}]{0,}\\x29" +
				"[\\p{Space}]{0,}\\x3B[\\p{ASCII}]{0,}";
		for (index = 0; index < groupByMark.size(); index++) {
			String editedLine = groupByMark.get(index);				
			if (editedLine.matches(whileRegex)) {
				if (!editedLine.matches(whileRegexPlus)) {
					char [] chars = editedLine.toCharArray();
					int conditionGroup = 1;
					for (int ind = editedLine.indexOf("("); 
							ind < chars.length; ind++) {
						if (chars[ind] == '(') {
							conditionGroup++;
						} 
						if (chars[ind] == ')') {
							conditionGroup--;
						}
						if (conditionGroup == 1) {
							editedLine = editedLine.substring(0, ind + 1) + ";"
									+ editedLine.substring(ind + 1);
							groupByMark.remove(index);
							groupByMark.add(index, editedLine);
							break;
						}
					}
				}
			}
		}
		
		for (int ind = 0; ind < groupByMark.size(); ind = ind + index) {
			ArrayList<String> temp = (ArrayList<String>)
					other.splitCode(groupByMark.remove(ind), ";", 2);
			groupByMark.addAll(ind, temp);
			index = temp.size();
		}
		
		for (index = 0; index < groupByMark.size(); index++) {
			String editedLine = groupByMark.get(index);
			if (!editedLine.endsWith(";")) {
				if ((index + 1) < groupByMark.size()) {
					if (groupByMark.get(index + 1).equals(";")) {
						String lineIndex = groupByMark.remove(index) + ";";
						groupByMark.remove(index);
						groupByMark.add(index, lineIndex);
					}
				}
			}
		}
		
		index = 0;
		for (int lineIndex = 0; lineIndex < groupByMark.size(); lineIndex++) {
			String line = groupByMark.get(lineIndex);
			if (startNewGroup == 0) {
				if (line.startsWith("char") || line.startsWith("int") || 
						line.startsWith("ushort") || line.startsWith("short") ||
						line.startsWith("ulong") || line.startsWith("long") ||
						line.startsWith("float")) {
					group = new String[1];
					group[0] = line;		
					if (line.contains("{")) {
						// array declare
						startNewGroup++; 
						continue;
					}
				} else if (line.startsWith("if") || line.startsWith("else") 
						|| line.startsWith("while") || line.startsWith("do")
							|| line.startsWith("switch")) {
					group = new String[3];
					group[0] = line.substring(0, line.indexOf("{") + 1);
					group[1] = "";
					group[2] = "";

					startNewGroup++;
					continue;
				} else if (line.startsWith("for")) {
					group = new String[3];
					group[0] = line + groupByMark.get(lineIndex + 1) 
							+ groupByMark.get(lineIndex + 2);
					group[1] = "";
					group[2] = "";

					startNewGroup++;
					lineIndex += 2;
					continue;				
				} else {
					group = new String[1];
					group[0] = line;		
					if (line.contains("{")) {
						// array declare
						startNewGroup++; 
						continue;
					}
				}
			} else {
				if (line.contains("{")) {
					startNewGroup++; 
				} 
				if (line.contains("}")) {
					startNewGroup--;
				}
				if (!((group[0].startsWith("if") || group[0].startsWith("else") 
						|| group[0].startsWith("while") || group[0].startsWith("do") 
						|| group[0].startsWith("for") || group[0].startsWith("switch")))) {
					group[0] += line;
				} else {
					if (startNewGroup != 0) {
							group[1] += line;
					} else {
						if (group[0].startsWith("do")) {
							group[2] += line + groupByMark.get(lineIndex + 1);	
							lineIndex++;
						} else {
							group[2] += line;
						}
					}					
				}
				if (startNewGroup != 0) {
					continue;
				}
			}
			Groups.add(index, group);
			index++;			
		}		
		structures = Groups.toArray();
		return structures;
	}
	
	public String[] splitFunction(String functionCode) throws DeclareException {
		String[] functionParts = new String[3];
		String headerRegex = "\\w+\\s{1,}\\w+\\p{Punct}[\\s{0,}\\w+\\s{1,}\\w+\\s{0,}[\\p{Punct}]{0,}]{0,}\\p{Punct}\\s{0,}\\p{Punct}";
		String header = functionCode.substring(0, functionCode.indexOf("{") + 1);
		String body = functionCode.substring(functionCode.indexOf("{") + 1, functionCode.length() - 1);
		String footer = functionCode.substring(functionCode.length() - 1);

		if (!header.matches(headerRegex)) {
			throw new DeclareException(DeclareException.FUNCTIONHEADERERROR);
		} else if (!footer.equals("}")){
			throw new DeclareException(DeclareException.FUNCTIONFOOTERERROR);
		}
		functionParts[0] = header;
		functionParts[1] = body;
		functionParts[2] = footer;
		return functionParts;
	}
}
