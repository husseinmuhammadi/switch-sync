package com.en.hsm;

import org.jpos.iso.ISOException;
import org.jpos.iso.ISOMsg;
import org.jpos.iso.ISOUtil;
import org.jpos.q2.iso.QMUX;

public class TMUX extends QMUX{

	@Override
	  public String getKey(ISOMsg m) throws ISOException {
	     byte[] header= m.getHeader();
	    return  ISOUtil.hexString(header);
	  }
	
}
