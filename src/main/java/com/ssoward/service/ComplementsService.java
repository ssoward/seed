package com.ssoward.service;

import com.ssoward.model.Complement;

import java.util.List;

/**
 * Created by ssoward on 3/5/14.
 */

public interface ComplementsService {

    List<Complement> getComplements();

    void saveComplement(Complement complement);

    void deleteComplement(Long id);
}
