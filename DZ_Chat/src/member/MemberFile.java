package member;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;

public class MemberFile {
	public MemberFile(Member member) throws Exception{
		FileOutputStream fos = new FileOutputStream("./MemberFile.txt");
		ObjectOutputStream oos = new ObjectOutputStream(fos);
		
		oos.writeObject(member);
		
		oos.flush();
		oos.close();
		fos.close();

	}
	
}
