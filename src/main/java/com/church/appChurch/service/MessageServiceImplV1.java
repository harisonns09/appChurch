package com.church.appChurch.service;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
@Qualifier("v1")
public class MessageServiceImplV1 implements IMessageService {

    @Override
    public String sayCustomMessage(String original) {
        return original.toUpperCase();
    }
}
