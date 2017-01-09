package com.epam.test;

import org.junit.Test;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class MockTest {

    @Test
    public  void testGetJournals() throws Exception {
        assertThat(true, is(true));
        assertThat(true, is(false));
    }

}
