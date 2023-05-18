package DataManager;

import java.io.File;
import java.util.ArrayList;

public class ExtendsInteraction1 extends DataInteraction1{
	public ExtendsInteraction1() {
		
	}
	public File getfileStoreLocation() {
		return storePathLocation; 
	}
	public void setFileStoreLocation(String file,String type,String detail) {
		File file1 = new File(file);
		if(file1.exists()&&file1.isDirectory()) {
			storePathLocation = file1;
			storePathType = type;
			detail_Type = detail;
			changeTitle();
		}
	}
	public void setAllDefalut() {
		storePathLocation = null;
		storePathType = null;
		detail_Type = null;
		recoveryTitle();
	}
	public void setCopyContent(String command) {
		copyContent = command;
		setCopyContentList(command);
	}
	public String getCopyContent() {
		return copyContent;
	}
	private void setCopyContentList(String command) {
		copyContent = command;
		File file = new File(command);
		if(!fileList.contains(file)&&!directoryList.contains(file)) {
			copyContents.add(command);
		if(file.exists()&&file.isFile()) {
			fileList.add(file);
		}else if(file.exists()&&file.isDirectory()) {
			directoryList.add(file);
		}
		}
	}
	public ArrayList<File> getFileList() {
		return fileList;
	}
	
}
