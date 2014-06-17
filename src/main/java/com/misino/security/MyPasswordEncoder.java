package com.misino.security;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.security.SecureRandom;

/**
 * Created by misino on 1/1/14.
 */
public class MyPasswordEncoder extends BCryptPasswordEncoder
{
    MyPasswordEncoder(int strength, int seed) {
        super(strength, new SecureRandom( SecureRandom.getSeed( seed ) ));
    }
}
