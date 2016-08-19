package ir.team.insurance.complementary.api;


import com.dpi.financial.ftcom.model.base.BaseEntity;

import java.util.List;

public interface GeneralServiceApi<T extends BaseEntity> {
    T create(T generalEntity);

    List<T> findAll();

    T find(Long id);

    void update(T t);

    void delete(T t);
}
