package com.example.test.models.services;
import com.example.test.models.dto.req.LogCreateReq;
import com.example.test.models.dto.res.LogRes;
public interface ILogService { LogRes create(LogCreateReq req); }
