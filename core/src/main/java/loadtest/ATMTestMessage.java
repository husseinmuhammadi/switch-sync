package loadtest;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import org.jpos.iso.ISOMsg;

import com.en.listener.AtmNdc;
import com.en.listener.AtmPackager;

public class ATMTestMessage {
	
	ArrayList<String> arrList;
	
	public ATMTestMessage(){
		try {
			arrList = new ArrayList<String>();
			File file = new File("./ATMTestMessages");
			FileReader freader = new FileReader(file);
			BufferedReader bf = new BufferedReader(freader);
			String line;
			while ((line = bf.readLine()) != null) {
				arrList.add(line);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public ISOMsg getTestMsg(long number) throws Exception{
		AtmPackager atmPack = new AtmPackager("file:./cfg/atm/ndc-", "request");
		AtmNdc ndcMsgReq= new AtmNdc(atmPack);
		int count = ((int)number)%arrList.size();
		ndcMsgReq.unpack((arrList.get(count)).getBytes());
	    return ndcMsgReq;
	}
}