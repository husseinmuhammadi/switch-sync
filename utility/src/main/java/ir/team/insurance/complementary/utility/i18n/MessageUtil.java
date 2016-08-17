package ir.team.insurance.complementary.utility.i18n;

import java.util.ResourceBundle;

public class MessageUtil {
    public static String getMessageFromEnum(Enum anEnum, ResourceBundle resourceBundle) {
        String name = anEnum.getClass().getName().toLowerCase() +
                "." + anEnum.name().toLowerCase();
        return resourceBundle.getString(name);
    }

    public static String getMessage(String name, ResourceBundle resourceBundle) {
        return resourceBundle.getString(name);
    }
}
