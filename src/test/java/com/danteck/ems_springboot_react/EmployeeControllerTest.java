package com.danteck.ems_springboot_react;

import com.danteck.ems_springboot_react.controller.EmployeeController;
import com.danteck.ems_springboot_react.model.Employee;
import com.danteck.ems_springboot_react.repository.EmployeeRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class EmployeeControllerTest {

    private MockMvc mockMvc;

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private EmployeeController employeeController;

    private AutoCloseable closeable; // Для хранения ссылки на моки

    @BeforeEach
    void setUp() {
        closeable = MockitoAnnotations.openMocks(this); // Открытие моков
        mockMvc = MockMvcBuilders.standaloneSetup(employeeController).build();
    }

    @Test
    void getAllEmployees_ShouldReturnListOfEmployees() throws Exception {
        Employee employee1 = new Employee("John", "Doe", "john.doe@example.com");
        Employee employee2 = new Employee("Jane", "Smith", "jane.smith@example.com");

        when(employeeRepository.findAll()).thenReturn(Arrays.asList(employee1, employee2));

        mockMvc.perform(get("/api/v1/employees"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].firstName").value("John"))
                .andExpect(jsonPath("$[1].firstName").value("Jane"));
    }

    @Test
    void getEmployeeById_ShouldReturnEmployee_WhenExists() throws Exception {
        Employee employee = new Employee("John", "Doe", "john.doe@example.com");
        when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));

        mockMvc.perform(get("/api/v1/employees/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("John"));
    }

    @Test
    void getEmployeeById_ShouldReturn404_WhenNotFound() throws Exception {
        when(employeeRepository.findById(1L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/v1/employees/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void createEmployee_ShouldReturnCreatedEmployee() throws Exception {
        Employee employee = new Employee("John", "Doe", "john.doe@example.com");
        when(employeeRepository.save(new Employee("John", "Doe", "john.doe@example.com"))).thenReturn(employee);

        when(employeeRepository.save(any(Employee.class))).thenReturn(employee);

        mockMvc.perform(post("/api/v1/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"firstName\": \"John\", \"lastName\": \"Doe\", \"email\": \"john.doe@example.com\"}"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("John"));
    }

    @Test
    void updateEmployee_ShouldReturnUpdatedEmployee() throws Exception {
        Employee existingEmployee = new Employee("John", "Doe", "john.doe@example.com");
        Employee updatedEmployee = new Employee("John", "Smith", "john.smith@example.com");

        when(employeeRepository.findById(1L)).thenReturn(Optional.of(existingEmployee));
        when(employeeRepository.save(existingEmployee)).thenReturn(updatedEmployee);

        mockMvc.perform(put("/api/v1/employees/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"firstName\": \"John\", \"lastName\": \"Smith\", \"email\": \"john.smith@example.com\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.lastName").value("Smith"));
    }

    @Test
    void deleteEmployeeById_ShouldReturnDeletedEmployee() throws Exception {
        Employee employee = new Employee("John", "Doe", "john.doe@example.com");

        when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));

        mockMvc.perform(delete("/api/v1/employees/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("John"));
    }

    @AfterEach
    void tearDown() throws Exception {
        closeable.close(); // Закрытие моков
    }
}
