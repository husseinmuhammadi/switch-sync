package com.dpi.financial.ftcom.model.base.hibernate;

import org.hibernate.dialect.MySQL57InnoDBDialect;
import org.hibernate.dialect.MySQL5InnoDBDialect;

public class EnhancedMySQL5InnoDBDialect extends MySQL5InnoDBDialect {

    @Override
    public String getTableTypeString() {
        return "  ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE utf8_general_ci ";
    }

}
