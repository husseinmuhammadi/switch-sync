package com.en.listener;

public class UTF2IranSystem {
    private static final String VK_SLASH = "\u002F";
    private static final String VK_SPACE1 = "\u0020";
    private static final String VK_SPACE = "\u00FF";
    private static final String VK_A = "\u008D";
    private static final String VK_ALEF = "\u0090";
    private static final String VK_ALEF_ = "\u0091";
    private static final String VK_BEH = "\u0092";
    private static final String VK_beh = "\u0093";
    private static final String VK_PEH = "\u0094";
    private static final String VK_peh = "\u0095";
    private static final String VK_TEH = "\u0096";
    private static final String VK_teh = "\u0097";
    private static final String VK_CEH = "\u0098";
    private static final String VK_ceh = "\u0099";
    private static final String VK_JEEM = "\u009A";
    private static final String VK_jeem = "\u009B";
    private static final String VK_CHEH = "\u009C";
    private static final String VK_cheh = "\u009D";
    private static final String VK_HAH = "\u009E";
    private static final String VK_hah = "\u009F";
    private static final String VK_KHEH = "\u00A0";
    private static final String VK_kheh = "\u00A1";
    private static final String VK_DAL = "\u00A2";
    private static final String VK_ZAL = "\u00A3";
    private static final String VK_REH = "\u00A4";
    private static final String VK_ZEH = "\u00A5";
    private static final String VK_JEH = "\u00A6";
    private static final String VK_SEEN = "\u00A7";
    private static final String VK_seen = "\u00A8";
    private static final String VK_SHEEN = "\u00A9";
    private static final String VK_sheen = "\u00AA";
    private static final String VK_SAD = "\u00AB";
    private static final String VK_sad = "\u00AC";
    private static final String VK_ZAD = "\u00AD";
    private static final String VK_zad = "\u00AE";
    private static final String VK_TAH = "\u00AF";
    private static final String VK_ZAH = "\u00E0";
    private static final String VK_AIN = "\u00E1";
    private static final String VK_AIN_ = "\u00E2";
    private static final String VK__ain_ = "\u00E3";
    private static final String VK_ain = "\u00E4";
    private static final String VK_GHAIN = "\u00E5";
    private static final String VK_GHAIN_ = "\u00E6";
    private static final String VK__ghain_ = "\u00E7";
    private static final String VK_ghain = "\u00E8";
    private static final String VK_FEH = "\u00E9";
    private static final String VK_feh = "\u00EA";
    private static final String VK_GHAF = "\u00EB";
    private static final String VK_ghaf = "\u00EC";
    private static final String VK_KAF = "\u00ED";
    private static final String VK_kaf = "\u00EE";
    private static final String VK_GAF = "\u00EF";
    private static final String VK_gaf = "\u00F0";
    private static final String VK_LAM = "\u00F1";
    private static final String VK_lam = "\u00F3";
    private static final String VK_MEEM = "\u00F4";
    private static final String VK_meem = "\u00F5";
    private static final String VK_NOON = "\u00F6";
    private static final String VK_noon = "\u00F7";
    private static final String VK_WAW = "\u00F8";
    private static final String VK_HEH = "\u00F9";
    private static final String VK__heh_ = "\u00FA";
    private static final String VK_heh = "\u00FB";
    private static final String VK_YEH = "\u00FD";
    private static final String VK_YEH_ = "\u00FC";
    private static final String VK_yeh = "\u00FE";
    private static final String VK_HAMZEH = "\u008F";
    private static final String VK_hamzeh = "\u008E";
    private static final String VK_KAMA = "\u00BA";
    private static final String VK_QUESTION = "\u00BC";
    private static final String VK_UNDERLINE = "\u00BB";
    private static final String VK_ZERO = "\u0080";
    private static final String VK_ONE = "\u0081";
    private static final String VK_TWO = "\u0082";
    private static final String VK_THREE = "\u0083";
    private static final String VK_FOUR = "\u0084";
    private static final String VK_FIVE = "\u0085";
    private static final String VK_SIX = "\u0086";
    private static final String VK_SEVEN = "\u0087";
    private static final String VK_EIGHT = "\u0088";
    private static final String VK_NINE = "\u0089";


    /**
     * @param ch
     * @param from
     * @param to
     * @return
     */
    private static char chrTran(char ch, String from, String to) {

        int fromIndex = from.indexOf((int) ch);
        return fromIndex >= 0 && fromIndex < from.length() ? to.charAt(fromIndex) : ch;
    }


    private static String proper(String str10) {

        if (isDigit(str10))
            return str10;

        String str1 = new String(VK_A + VK_HAMZEH + VK_hamzeh + VK_ALEF + VK_ALEF_ + VK_BEH + VK_beh +
                VK_PEH + VK_peh + VK_TEH + VK_teh + VK_CEH + VK_ceh + VK_JEEM + VK_jeem +
                VK_CHEH + VK_cheh + VK_HAH + VK_hah + VK_KHEH + VK_kheh + VK_DAL + VK_ZAL +
                VK_REH + VK_ZEH + VK_JEH + VK_SEEN + VK_seen + VK_SHEEN + VK_sheen + VK_SAD +
                VK_sad + VK_ZAD + VK_zad + VK_TAH + VK_ZAH + VK_AIN + VK_AIN_ + VK__ain_ +
                VK_ain + VK_GHAIN + VK_GHAIN_ + VK__ghain_ + VK_ghain + VK_FEH + VK_feh + VK_GHAF +
                VK_ghaf + VK_KAF + VK_kaf + VK_GAF + VK_gaf + VK_LAM + VK_lam + VK_MEEM +
                VK_meem + VK_NOON + VK_noon + VK_WAW + VK_HEH + VK__heh_ + VK_heh + VK_YEH + VK_YEH_ + VK_yeh);

        String str2 = new String(VK_hamzeh + VK_beh + VK_peh + VK_teh + VK_ceh + VK_jeem + VK_cheh +
                VK_hah + VK_kheh + VK_seen + VK_sheen + VK_sad + VK_zad + VK__ain_ + VK_ain +
                VK__ghain_ + VK_ghain + VK_feh + VK_ghaf + VK_kaf + VK_gaf + VK_lam + VK_meem +
                VK_noon + VK__heh_ + VK_heh + VK_yeh);

        StringBuffer str = new StringBuffer(str10);

        for (int cur = str.length() - 1; cur >= 0; cur--) {
            int prv = cur + 1;
            int nxt = cur - 1;
            if (nxt >= 0) {
                if (str1.indexOf(str.charAt(nxt), 0) >= 0)
                    str.setCharAt(cur, chrTran(str.charAt(cur),
                            VK_BEH + VK_PEH + VK_TEH + VK_CEH + VK_JEEM + VK_CHEH + VK_HAH + VK_KHEH +
                                    VK_SEEN + VK_SHEEN + VK_SAD + VK_ZAD + VK_AIN + VK_AIN_ + VK_GHAIN + VK_GHAIN_ +
                                    VK_FEH + VK_GHAF + VK_KAF + VK_GAF + VK_LAM + VK_MEEM + VK_NOON + VK_HEH +
                                    VK_YEH + VK_YEH_ + VK_HAMZEH + VK_SPACE,

                            VK_beh + VK_peh + VK_teh + VK_ceh + VK_jeem + VK_cheh + VK_hah + VK_kheh +
                                    VK_seen + VK_sheen + VK_sad + VK_zad + VK_ain + VK__ain_ + VK_ghain + VK__ghain_ +
                                    VK_feh + VK_ghaf + VK_kaf + VK_gaf + VK_lam + VK_meem + VK_noon + VK_heh +
                                    VK_yeh + VK_yeh + VK_hamzeh + VK_SPACE1));
                else if (str1.indexOf(str.charAt(cur), 0) >= 0)
                    str.setCharAt(cur, chrTran(str.charAt(cur),
                            VK_beh + VK_peh + VK_teh + VK_ceh +
                                    VK_jeem + VK_cheh + VK_hah + VK_kheh +
                                    VK_seen + VK_sheen + VK_sad + VK_zad +
                                    VK_ain + VK__ain_ + VK_AIN_ + VK_ghain +
                                    VK__ghain_ + VK_GHAIN_ + VK_feh + VK_ghaf +
                                    VK_kaf + VK_gaf + VK_lam + VK_meem +
                                    VK_noon + VK_heh + VK_yeh + VK_SPACE,

                            VK_BEH + VK_PEH + VK_TEH + VK_CEH +
                                    VK_JEEM + VK_CHEH + VK_HAH + VK_KHEH +
                                    VK_SEEN + VK_SHEEN + VK_SAD + VK_ZAD +
                                    VK_AIN + VK_AIN_ + VK_AIN + VK_GHAIN +
                                    VK_GHAIN_ + VK_GHAIN + VK_FEH + VK_GHAF +
                                    VK_KAF + VK_GAF + VK_LAM + VK_MEEM +
                                    VK_NOON + VK_HEH + VK_YEH + VK_SPACE1));
            } else {
                if (str1.indexOf(str.charAt(cur), 0) >= 0) {
                    str.setCharAt(cur, chrTran(str.charAt(cur),
                            VK_beh + VK_peh + VK_teh + VK_ceh +
                                    VK_jeem + VK_cheh + VK_hah + VK_kheh +
                                    VK_seen + VK_sheen + VK_sad + VK_zad +
                                    VK_ain + VK__ain_ + VK_ghain + VK__ghain_ +
                                    VK_feh + VK_ghaf + VK_kaf + VK_gaf +
                                    VK_lam + VK_meem + VK_noon + VK_heh +
                                    VK_yeh + VK_SPACE,

                            VK_BEH + VK_PEH + VK_TEH + VK_CEH +
                                    VK_JEEM + VK_CHEH + VK_HAH + VK_KHEH +
                                    VK_SEEN + VK_SHEEN + VK_SAD + VK_ZAD +
                                    VK_AIN + VK_AIN_ + VK_GHAIN + VK_GHAIN_ +
                                    VK_FEH + VK_GHAF + VK_KAF + VK_GAF +
                                    VK_LAM + VK_MEEM + VK_NOON + VK_HEH +
                                    VK_YEH + VK_SPACE1));
                    //System.out.println("current :"+ cur);
                }
            }
            if (prv < str.length() && str2.indexOf(str.charAt(prv), 0) >= 0)
                str.setCharAt(cur, chrTran(str.charAt(cur),
                        VK_ALEF + VK_AIN + VK_ain + VK_GHAIN +
                                VK_ghain + VK_heh + VK_YEH + VK_SPACE,

                        VK_ALEF_ + VK_AIN_ + VK__ain_ + VK_GHAIN_ +
                                VK__ghain_ + VK__heh_ + VK_YEH_ + VK_SPACE1));
            else
                str.setCharAt(cur, chrTran(str.charAt(cur),
                        VK_ALEF_ + VK_AIN_ + VK__ain_ + VK_GHAIN_ +
                                VK__ghain_ + VK__heh_ + VK_YEH + VK_SPACE,

                        VK_ALEF + VK_AIN + VK_ain + VK_GHAIN +
                                VK_ghain + VK_heh + VK_YEH + VK_SPACE1));
        }
        return str.toString();
        //return str10;
    }


    private static boolean isFarsiString(String str) {
        for (int i = 0; i < str.length(); i++) {
            char ch = str.charAt(i);
            if ((ch >= 128 && ch < 176) || (ch >= 224 && ch <= 255))
                return true;
        }
        return false;
    }

    /**
     * @param str
     * @return
     */
    private static boolean isDigit(String str) {
        boolean flag = true;
        for (int i = 0; i < str.length(); i++) {
            char ch = str.charAt(i);
            if ((ch < 0x80 || ch > 0x89) && ch != 47 && ch != 32 && ch != 255)
                flag = false;
        }
        return flag;
    }


    private static boolean isConversionRequiered(String str) {
        boolean flag = false;
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) > 0xff)
                flag = true;
        }
        return flag;
    }


    /**
     * @param str
     * @return
     */
    private static String fromStandard(String str) {
        StringBuffer uStr = new StringBuffer(str);
        String ConvertStr = new String("");
        for (int cur = uStr.length() - 1; cur >= 0; cur--) {
            uStr.setCharAt(cur, chrTran(uStr.charAt(cur),

                    // unicode characters
                    "\u0660\u0661\u0662\u0663\u0664" +
                            "\u0665\u0666\u0667\u0668\u0669" +
                            "\u0020\u0622\u0627\u0628\u067E\u062A\u062B\u062C" +
                            "\u0686\u062D\u062E\u062F\u0630\u0631\u0632\u0698" + //8E
                            "\u0633\u0634\u0635\u0636\u0637\u0638\u0639\u063A" +
                            "\u0641\u0642\u06A9\u0643\u06AF\u0644\u0645\u0646\u0648" +
                            "\u0647\u06CC\u064A\u0626\u0621",

                    VK_ZERO + VK_ONE + VK_TWO + VK_THREE + VK_FOUR +
                            VK_FIVE + VK_SIX + VK_SEVEN + VK_EIGHT + VK_NINE +
                            VK_SPACE + VK_A + VK_ALEF + VK_BEH + VK_PEH + VK_TEH + VK_CEH + VK_JEEM +
                            VK_CHEH + VK_HAH + VK_KHEH + VK_DAL + VK_ZAL + VK_REH + VK_ZEH + VK_JEH +
                            VK_SEEN + VK_SHEEN + VK_SAD + VK_ZAD + VK_TAH + VK_ZAH + VK_AIN + VK_GHAIN +
                            VK_FEH + VK_GHAF + VK_KAF + VK_KAF + VK_GAF + VK_LAM + VK_MEEM + VK_NOON + VK_WAW +
                            VK_HEH + VK_YEH + VK_YEH + VK_HAMZEH + VK_hamzeh));
        }
        ConvertStr = uStr.toString();

        if (!isFarsiString(ConvertStr) || isDigit(ConvertStr))
            return ConvertStr;

        return ConvertStr;


    }


    /**
     * Conert str to Iransystem with Context
     *
     * @param str
     * @return String
     */
    private String reverse(String s) {
        return new StringBuilder(s).reverse().toString();
    }

    public byte[] convert(String str) {
        if (str.length() != 0)
            if (!isConversionRequiered(str))
                return str2Byte(str);

        String s = reverse(proper(reverse(fromStandard(str))));
        String[] ss = null;
        String spliter = null;
        if (s.contains("\r\n")) {
            ss = s.split("\r\n");
            spliter = "\r\n";
        } else if (s.contains("\r")) {
            ss = s.split("\r");
            spliter = "\r";
        } else if (s.contains("\n")) {
            ss = s.split("\n");
            spliter = "\n";
        } else {
            ss = new String[]{s};
            spliter = "";
        }
        StringBuilder sb = new StringBuilder();
        if (ss != null) {
            for (String s1 : ss) {
                StringBuilder sbf = new StringBuilder();
                StringBuilder sbe = new StringBuilder();
                if (s1.length() < 1)
                    continue;
                if (s1.length() < 2) {
                    sbf.append(s1);
                    sbf.append(spliter);
                    continue;
                }
                int f = 0;
                int e = s1.length();
                boolean isDirect = true;
                while (f < e) {

                    int fi = f;
                    while (fi < e && (isPersianLetterOrSign(s1.charAt(fi)) && !isDirect || !isPersianLetterOrSign(s1.charAt(fi)) && isDirect))
                        fi++;

                    int ei = e;
                    while (ei > fi && (isPersianLetterOrSign(s1.charAt(ei - 1)) && !isDirect || !isPersianLetterOrSign(s1.charAt(ei - 1)) && isDirect))
                        ei--;

                    if (fi > f) {
                        String strf = s1.substring(f, fi);
//                        if (isDirect)
//                            strf = reverse(strf);
                        if(!isDirect)
                            sbf.append(strf);
                        else
                            sbe.insert(0, strf);
                        f = fi;
                    }

                    if (ei < e) {
                        String stre = s1.substring(ei, e);
//                        if (isDirect)
//                            stre = reverse(stre);
                        if(!isDirect)
                            sbe.insert(0, stre);
                        else
                            sbf.append(stre);

                        e = ei;
                    }
                    f = fi;
                    e = ei;

                    s1 = reverse(s1);
                    int t = f;
                    f = s1.length() - e;
                    e = s1.length() - t;
//                    StringBuilder sbt=sbf;
//                    sbf=sbe;
//                    sbe=sbt;

                    isDirect = !isDirect;
                }
                sb.append(sbf);
                sb.append(sbe);
                sb.append(spliter);
            }

        }
        String res=sb.toString();
        if(res.endsWith(spliter))
        	res=res.substring(0,res.lastIndexOf(spliter));
        return str2Byte(res);
    }

    private boolean isPersianLetterOrSign(char ch) {
        if (ch >= 138 && ch <= 175)
            return true;
        if (ch >= 224 && ch <= 255)
            return true;
//        if(ch==32||ch==10||ch==13||ch==27)
//            return true;
        return false;
    }

    private byte[] str2Byte(String s) {
        char[] c = s.toCharArray();
        byte[] res = new byte[c.length];
        for (int i = 0; i < res.length; i++) {
            res[i] = (byte) c[i];

        }
        return res;
    }


}