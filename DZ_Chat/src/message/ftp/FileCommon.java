package message.ftp;

import java.io.FileOutputStream;

public class FileCommon {
	/**
	 * 
	 * @param filePath
	 * @param message
	 * @param isAppend true이면 연속해서 파일 작성, false이면 새로 파일 작성
	 * @return
	 */
	public boolean saveContent(String filePath, String message, boolean isAppend) {		
		try{            
            FileOutputStream f = new FileOutputStream(filePath, isAppend);              
            String lineToAppend = message;
            byte[] byteArr = lineToAppend.getBytes(); //converting string into byte array
            f.write(byteArr);
            f.close();
            
            //채팅시간, 유저아디@ip, 채팅내용            
        }
        catch(Exception e){
            System.out.println(e);
            return false;
        }

		return true;		
	}
}
