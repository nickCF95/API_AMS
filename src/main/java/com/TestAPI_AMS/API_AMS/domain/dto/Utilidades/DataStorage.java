package com.TestAPI_AMS.API_AMS.domain.dto.Utilidades;

import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class DataStorage {

    private static final HashMap<String, Integer[]> unitiesAMS = new HashMap<>(){{
        put("eai_tt_r", new Integer[]{3, 0, -1});
        put("eae_tt_r", new Integer[]{3, 1, -1});
        put("eri_tt_r", new Integer[]{4, 0, -1});
        put("ere_tt_r", new Integer[]{4, 1, -1});
        put("ceai_r", new Integer[]{6, 0, -1});
        put("ceae_r", new Integer[]{6, 1, -1});
        put("ceri_r", new Integer[]{7, 0, -1});
        put("cere_r", new Integer[]{7, 1, -1});
        put("v1_r", new Integer[]{1, -1, 1});
        put("i1_r", new Integer[]{2, -1, 1});
        put("v2_r", new Integer[]{1, -1, 2});
        put("i2_r", new Integer[]{2, -1, 2});
        put("v3_r", new Integer[]{1, -1, 3});
        put("i3_r", new Integer[]{2, -1, 3});
        put("pi_r", new Integer[]{5, -1, 1});
        put("pf1_r", new Integer[]{13, -1, 1});
        put("pf2_r", new Integer[]{13, -1, 2});
        put("pf3_r", new Integer[]{13, -1, 3});
        put("pfmono_r", new Integer[]{13, -1, 1});
        put("frec_r", new Integer[]{15, -1, -1});
    }};

    public HashMap<String, Integer[]> getUnitiesAMS() {
        return new HashMap<>(unitiesAMS);
    }

    public Integer[] getUnityAMS(String field) {
        return this.unitiesAMS.get(field);
    }
}