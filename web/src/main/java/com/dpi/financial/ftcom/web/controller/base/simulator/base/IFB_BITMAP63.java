package com.dpi.financial.ftcom.web.controller.base.simulator.base;

/*
 * jPOS Project [http://jpos.org]
 * Copyright (C) 2000-2008 Alejandro P. Revilla
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

import org.jpos.iso.ISOBitMapPackager;
import org.jpos.iso.ISOComponent;
import org.jpos.iso.ISOException;
import org.jpos.iso.ISOUtil;

import java.io.IOException;
import java.io.InputStream;
import java.util.BitSet;

/**
 * ISOFieldPackager Binary Bitmap
 *
 * @author apr@cs.com.uy
 * @version $Id: IFB_BITMAP63.java,v 1.1.1.1 2013/08/02 21:26:48 bhalchandra Exp $
 * @see ISOComponent
 * @see ISOBitMapPackager
 */
public class IFB_BITMAP63 extends ISOBitMapPackager {
    public IFB_BITMAP63() {
        super();
    }
    /**
     * @param len - field len
     * @param description symbolic descrption
     */
    public IFB_BITMAP63(int len, String description) {
        super(len, description);
    }
    /**
     * @param c - a component
     * @return packed component
     * @exception ISOException
     */
    public byte[] pack (ISOComponent c) throws ISOException {
        BitSet b = (BitSet) c.getValue();
        int len = 
            getLength() >= 8 ?
                (((b.length()+62)>>6)<<3) : getLength();
        return bitSet2byte (b, len);
    }
    /**
     * @param c - the Component to unpack
     * @param b - binary image
     * @param offset - starting offset within the binary image
     * @return consumed bytes
     * @exception ISOException
     */
    public int unpack (ISOComponent c, byte[] b, int offset)
        throws ISOException
    {
        int len;
        BitSet bmap = byte2BitSet (b, offset, getLength() << 3);
        c.setValue(bmap);
        len = bmap.get(1) ? 128 : 64;
        if (getLength() > 16 && bmap.get(65))
            len = 192;
        return (Math.min (getLength(), len >> 3));
    }
    public void unpack (ISOComponent c, InputStream in) 
        throws IOException, ISOException
    {
        BitSet bmap = ISOUtil.byte2BitSet (new BitSet (64), readBytes (in, 8), 0);
        if (getLength() > 8 && bmap.get (1)) {
            ISOUtil.byte2BitSet (bmap, readBytes (in, 8), 64); 
        }
        if (getLength() > 16 && bmap.get (65)) {
            ISOUtil.byte2BitSet (bmap, readBytes (in, 8), 128); 
        }
        c.setValue(bmap);
    }
    public int getMaxPackedLength() {
        return getLength() >> 3;
    }
    /*  
     *  Added these Two methods From Jpos-1.6 Revision R2606--ISOUtil.java
     *  public static BitSet byte2BitSet (byte[] b, int offset, int maxBits);
     *  public static byte[] bitSet2byte (BitSet b, int bytes);
     *  BackPorting the R2606 and R2607 changes for jpos-1.4 once we migrate to 1.6 we can discard these
     * */
    
    /**
     * Converts a binary representation of a Bitmap field
     * into a Java BitSet
     * @param b - binary representation
     * @param offset - staring offset
     * @param maxBits - max number of bits (supports 64,128 or 192)
     * @return java BitSet object
     */
    public static BitSet byte2BitSet (byte[] b, int offset, int maxBits) {
        int len = maxBits > 64 ?
            ((b[offset] & 0x80) == 0x80 ? 128 : 64) : maxBits;

        if (maxBits > 128 && 
            b.length > offset+8 && 
            (b[offset+8] & 0x80) == 0x80)
        {
            len = 192;
        } 
        BitSet bmap = new BitSet (len);
        for (int i=0; i<len; i++) 
            if (((b[offset + (i >> 3)]) & (0x80 >> (i % 8))) > 0)
                bmap.set(i+1);
        return bmap;
    }
    /**
     * converts a BitSet into a binary field
     * used in pack routines
     * @param b - the BitSet
     * @param bytes - number of bytes to return
     * @return binary representation
     */    
    public static byte[] bitSet2byte (BitSet b, int bytes)
    {
        int len = bytes * 8;
        
        byte[] d = new byte[bytes];
        for (int i=0; i<len; i++) 
            if (b.get(i+1)) 
                d[i >> 3] |= (0x80 >> (i % 8));
        //TODO: review why 2nd & 3rd bit map flags are set here??? 
        if (len>64)
            d[0] |= 0x80;
        if (len>128)
            d[8] |= 0x80;
        return d;
    }
}
