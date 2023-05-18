package algorithmLibrary;

public class AlgorithmManager1 {
	public AlgorithmManager1() {
		
	}
	
	
	//if syntax error return  ""
	public int syntaxTreeInt(String command) {
		int result  = 0;
		if(command.length()>0) {
			if(command.indexOf("set ")>-1&&command.length()>4) {
				command = command.substring(4);
				
			}
		}
		return result;
	}
	
	
	
}
