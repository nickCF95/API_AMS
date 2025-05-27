package com.TestAPI_AMS.API_AMS.domain.dto.preAprovisionamiento;

import java.util.List;

public class TipoTramaC {
    TipoTrama tipoTrama;
    List<TipoTramaPregunta> tipoTramaPreg;

    public TipoTramaC() {
    }

    public TipoTramaC(TipoTrama tipoTrama, List<TipoTramaPregunta> tipoTramaPreg) {
        this.tipoTrama = tipoTrama;
        this.tipoTramaPreg = tipoTramaPreg;
    }

    public TipoTrama getTipoTrama() {
        return tipoTrama;
    }

    public void setTipoTrama(TipoTrama tipoTrama) {
        this.tipoTrama = tipoTrama;
    }

    public List<TipoTramaPregunta> getTipoTramaPreg() {
        return tipoTramaPreg;
    }

    public void setTipoTramaPreg(List<TipoTramaPregunta> tipoTramaPreg) {
        this.tipoTramaPreg = tipoTramaPreg;
    }
}
