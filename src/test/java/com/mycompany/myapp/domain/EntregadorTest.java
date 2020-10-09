package com.mycompany.myapp.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.mycompany.myapp.web.rest.TestUtil;

public class EntregadorTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Entregador.class);
        Entregador entregador1 = new Entregador();
        entregador1.setId(1L);
        Entregador entregador2 = new Entregador();
        entregador2.setId(entregador1.getId());
        assertThat(entregador1).isEqualTo(entregador2);
        entregador2.setId(2L);
        assertThat(entregador1).isNotEqualTo(entregador2);
        entregador1.setId(null);
        assertThat(entregador1).isNotEqualTo(entregador2);
    }
}
