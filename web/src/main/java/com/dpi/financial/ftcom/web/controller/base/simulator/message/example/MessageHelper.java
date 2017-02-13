package com.dpi.financial.ftcom.web.controller.base.simulator.message.example;



import com.dpi.financial.ftcom.web.controller.base.simulator.std.message.base.TransactionBase;
import com.dpi.financial.ftcom.web.controller.base.simulator.utils.HexUtils;
import com.dpi.financial.ftcom.web.controller.base.simulator.utils.IranSystem;
import com.dpi.financial.ftcom.web.controller.base.simulator.message.type.*;


public class MessageHelper {
	public static TransactionBase getDefaultFundTransferCr() {
		TransactionBase fundTransferCr = new FundTransferCr();
		
		String cardAcceptorNameLocationBinary = "4D45422020202020202020202020202020202020202054454852414E202020202020205448524952";
		cardAcceptorNameLocationBinary = "FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF5448524952";
		cardAcceptorNameLocationBinary = "9591FE91F7F9FF93F9FF95A4A290A196FFF5F396FFFF97FAA490F6FFFFFFFFFFFFFFFF5448524952";
		cardAcceptorNameLocationBinary = "9591FE91F7F9FF93F9FF95A4A290A196FFF5F396FFFF97FAA490F6FFFFFFFFFFFFFFFF5448524952";

		IranSystem iranSystem = new IranSystem(HexUtils.hexStringToByteArray(cardAcceptorNameLocationBinary));
		
		fundTransferCr.setCardAcceptorNameLocation(iranSystem.getString());		
		return fundTransferCr;
	}
}
