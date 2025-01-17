package com.danteck.ems_springboot_react;

import com.danteck.ems_springboot_react.exception.ResourceNotFoundException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ResourceNotFoundExceptionTest {

    @Test
    void testExceptionMessage() {
        ResourceNotFoundException exception = new ResourceNotFoundException("Employee not found");
        assertEquals("Employee not found", exception.getMessage());
    }
}
