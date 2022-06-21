package com.guto.project.resource;

import com.guto.project.domain.dto.PersonPersistDTO;
import com.guto.project.domain.entity.Person;
import com.guto.project.repository.PersonRepository;
import com.guto.project.service.PersonService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PersonResource.class)
@ExtendWith(SpringExtension.class)
public class PersonResourceTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PersonService personServiceMock;

    @MockBean
    private PersonRepository personRepositoryMock;

    @Test
    public void shouldCallFunctionToFindByName() throws Exception {
        String name = "Golden Wind";

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/person")
                        .param("name", name)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(personServiceMock, times(1)).findByName(eq(name));
    }

    @Test
    public void shouldCallFunctionToFindAll() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/person/all")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(personServiceMock, times(1)).findAll();
    }

    @Test
    public void shouldNotCallFunctionToFindAllBecauseURLIsIncorrect() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/person")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

        verify(personServiceMock, times(0)).findAll();
    }

    @Test
    public void shouldCallFunctionToPostPerson() throws Exception {
        String json = "{ \"name\": \"" + "Golden Wind" + "\", \"age\": 18 }";

        Mockito.when(personServiceMock.save(any())).thenReturn(new Person("Golden Wind", 18));

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/person")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        ArgumentCaptor<PersonPersistDTO> captor = ArgumentCaptor.forClass(PersonPersistDTO.class);

        verify(personServiceMock, times(1)).save(captor.capture());

        PersonPersistDTO dto = captor.getValue();
        assertEquals("Golden Wind", dto.getName());
    }

    @Test
    public void shouldNotCallFunctionToPostPersonBecauseAgeIsNull() throws Exception {
        String json = "{ \"name\": \"" + "Golden Wind" + "\" }";

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/person")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldNotCallFunctionToPostPersonBecauseNameIsNull() throws Exception {
        String json = "{ \"age\": 18 }";

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/person")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldCallFunctionToUpdatePerson() throws Exception {
        String json = "{ \"name\": \"" + "Golden Wind" + "\", \"age\": 18 }";
        Integer id = 25;

        Mockito.when(personServiceMock.update(any(), any())).thenReturn(new Person("Golden Wind", 18));

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/api/person/" + id)
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        ArgumentCaptor<PersonPersistDTO> captor = ArgumentCaptor.forClass(PersonPersistDTO.class);

        verify(personServiceMock, times(1)).update(eq(id), captor.capture());

        PersonPersistDTO dto = captor.getValue();
        assertEquals("Golden Wind", dto.getName());
    }

    @Test
    public void shouldNotCallFunctionToUpdatePersonBecauseBodyIsIncorrect() throws Exception {
        String json = "{ \"age\": 18 }";

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/api/person/" + 25)
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }
}
