package com.ssoward.service;

import com.ssoward.model.Give;

/**
 * Created by ssoward on 3/5/14.
 */

public interface GiveService {
    void saveGive(Give gives);

    void deleteSave(Long id);
}
