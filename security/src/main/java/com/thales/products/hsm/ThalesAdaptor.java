package com.thales.products.hsm;

import com.dpi.financial.ftcom.core.codec.Parser;
import org.jpos.iso.ISOUtil;
import org.jpos.q2.iso.MUXPool;
import org.jpos.space.Space;
import org.jpos.space.SpaceFactory;
import org.jpos.space.SpaceUtil;
import org.jpos.util.NameRegistrar;

public class ThalesAdaptor {

    private HSMChannel hsm;

    public void setHSM(HSMChannel hsm) {
        this.hsm = hsm;
    }

    public ThalesAdaptor() {
    }

    protected Parser command(Parser request) throws Exception {
        // request.dump(System.out,"-->");
        // StringBuffer sbuffer = new StringBuffer(request.get("command"));
        // sbuffer.setCharAt(1, (char) (sbuffer.charAt(1) + 1));
        FSDISOMsg msg = new FSDISOMsg(request);
        Space sp = SpaceFactory.getSpace();
        long stan = SpaceUtil.nextLong(sp, "hc");
        if (stan > 9998) {
            SpaceUtil.wipeAndOut(sp, "hc", "1");
        }
        msg.setHeader(ISOUtil.zeropad(stan + "", 4).getBytes());
        if (hsm != null) {
            hsm.send(msg);
            FSDISOMsg res = (FSDISOMsg) hsm.receive();
            if (res != null) {
                // res.dump(System.out,"<--");
                return res.getFSDMsg();
            }
        } else {
            // TMUX mux=(TMUX)NameRegistrar.get("mux.thales");
            // FSDISOMsg resp=(FSDISOMsg)mux.request(msg, 15000);
            MUXPool mux = (MUXPool) NameRegistrar.get("mux.hsm-mux");
            FSDISOMsg resp = (FSDISOMsg) mux.request(msg, 8000);
            if (resp != null) {
                return resp.getFSDMsg();
            }
        }
        return null;
    }

}
