package com.dpi.financial.ftcom.utility.system;

import com.dpi.financial.ftcom.utility.type.Platform;

/**
 * Simple static class to retrieve platform information.
 * For more detailed OS information use the org.apache.commons.lang.SystemUtils library:
 * http://commons.apache.org/lang/api-2.4/org/apache/commons/lang/SystemUtils.html
 *
 * @author PjotrC
 */
public class OperationSystem {


    private static Platform m_os = null;


    public static Platform getOS() {
        if(m_os == null) {
            String os = System.getProperty("os.name").toLowerCase();

            m_os = Platform.unsupported;
            if(os.indexOf("win")   >= 0) m_os = Platform.Windows;		// Windows
            if(os.indexOf("mac")   >= 0) m_os = Platform.Mac;			// Mac
            if(os.indexOf("nux")   >= 0) m_os = Platform.Unix;			// Linux
            if(os.indexOf("nix")   >= 0) m_os = Platform.Unix;			// Unix
            if(os.indexOf("sunos") >= 0) m_os = Platform.Solaris;		// Solaris
        }

        return m_os;
    }

    public static boolean isWindows() {
        return (getOS() == Platform.Windows);
    }
    public static boolean isMac() {
        return (getOS() == Platform.Mac);
    }
    public static boolean isUnix() {
        return (getOS() == Platform.Unix);
    }
    public static boolean isSolaris() {
        return (getOS() == Platform.Solaris);
    }


    // for testing
    public static void main(String[] args) {

        // A switch block is always better than else if forks!
        // On Java 7 it is even possible to switch for strings - no enums are needed.
        switch( getOS() ) {
            case Windows:		System.out.println("This is Windows");					break;
            case Mac:			System.out.println("This is Mac");						break;
            case Unix:			System.out.println("This is Unix (including Linux)");	break;
            case Solaris:		System.out.println("This is Solaris");					break;
            case unsupported:	System.out.println("Your OS is not support!");			break;
        }

        // But the easiest way would be following (including also the version number):
        System.out.println("This is " + System.getProperty("os.name"));
    }

}