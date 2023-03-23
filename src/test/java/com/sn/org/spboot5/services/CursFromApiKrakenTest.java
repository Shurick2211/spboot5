package com.sn.org.spboot5.services;

import com.sn.org.spboot5.kraken.CursFromApiKraken;
import org.junit.jupiter.api.Test;

class CursFromApiKrakenTest {

    @Test
    void getCurs() {
        CursFromApiKraken curs = new CursFromApiKraken();
        curs.getCurs();
    }
}