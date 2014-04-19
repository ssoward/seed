package com.ssoward.service;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by ssoward on 4/12/14.
 */
public interface FileService {
    void uploadAward(HttpServletRequest req, HttpServletResponse res) throws Exception;

    byte[] getFile(Long id);

    void deleteFile(Long id) throws IOException;
}
