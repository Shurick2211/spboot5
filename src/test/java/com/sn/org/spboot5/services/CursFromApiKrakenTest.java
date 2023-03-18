package com.sn.org.spboot5.services;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CursFromApiKrakenTest {

    @Test
    void getCurs() {
        CursFromApiKraken curs = new CursFromApiKraken();
        curs.getCurs();
    }
}