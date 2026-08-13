package com.example.test.models.services.auth;

import com.example.test.models.dto.req.ActiveUserReq;
import com.example.test.models.dto.req.BlockReq;
import com.example.test.models.dto.req.LoginReq;
import com.example.test.models.dto.req.RegisterReq;
import com.example.test.models.dto.res.BlockRes;
import com.example.test.models.dto.res.LoginRes;
import com.example.test.models.dto.res.UserRes;

public interface IAuthService {
    void register(RegisterReq req);
    LoginRes login(LoginReq req);
    String activeUser(ActiveUserReq req);

}
