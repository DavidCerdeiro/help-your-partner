package com.help_your_partner.task_service.infrastructure.web;

// Imports de Spring Boot Test y MockMvc
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

// Imports de Spring Security Test
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.authentication;

// Imports de JUnit 5 y Mockito
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

// Imports para las aserciones de MockMvc
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

// Imports de JSON y de tus clases
import com.fasterxml.jackson.databind.ObjectMapper;
import com.help_your_partner.task_service.application.dto.ClaimTaskCommand;
import com.help_your_partner.task_service.application.dto.GetTasksResponse;
import com.help_your_partner.task_service.application.dto.PostTaskCommand;
import com.help_your_partner.task_service.application.port.in.ClaimTaskUseCase;
import com.help_your_partner.task_service.application.port.in.CreateTaskUseCase;
import com.help_your_partner.task_service.application.port.in.GetTasksUseCase;
import com.help_your_partner.task_service.domain.model.Task;
import com.help_your_partner.task_service.domain.model.TaskStatus;
import com.help_your_partner.task_service.infrastructure.web.dto.PostTaskRequest; 

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc 
@ActiveProfiles("test")
public class TaskControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @SuppressWarnings("removal")
    @MockBean
    private ClaimTaskUseCase claimTaskUseCase;
    @SuppressWarnings("removal")
    @MockBean
    private CreateTaskUseCase createTaskUseCase;
    @SuppressWarnings("removal")
    @MockBean
    private GetTasksUseCase getTasksUseCase;

    /**
     * Helper para crear un objeto 'Authentication' simulado,
     * ya que tu controlador depende de 'getPrincipal()' para el userId
     * y 'getDetails()' para el communityId.
     */
    private Authentication createMockAuthentication(String userId, String communityId) {
        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                userId,
                null,
                new ArrayList<>()
        );
        auth.setDetails(communityId); 
        return auth;
    }

    @Test
    public void postTask_ShouldCreateTask_WhenSuccessful() throws Exception {
        // Arrange
        String userId = "user-creator-1";
        String communityId = "community-A";
        Authentication mockAuth = createMockAuthentication(userId, communityId);

        PostTaskRequest requestBody = new PostTaskRequest();
        requestBody.setTitle("New Task Title");
        requestBody.setDescription("New task description");

        // Capturador para verificar el comando pasado al use case
        ArgumentCaptor<PostTaskCommand> commandCaptor = ArgumentCaptor.forClass(PostTaskCommand.class);

        // Simular que el caso de uso funciona
        when(createTaskUseCase.createTask(commandCaptor.capture())).thenReturn(true);

        // Act & Assert
        mockMvc.perform(post("/api/tasks")
                .with(authentication(mockAuth)) // Simular la autenticación
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestBody)))
                .andExpect(status().isCreated())
                .andExpect(content().string("Task created successfully"));

        // Verificar que el caso de uso fue llamado con los datos correctos
        PostTaskCommand capturedCommand = commandCaptor.getValue();
        assertEquals("New Task Title", capturedCommand.getTitle());
        assertEquals("New task description", capturedCommand.getDescription());
        assertEquals(userId, capturedCommand.getUserId());
        assertEquals(communityId, capturedCommand.getCommunityId());
    }

    @Test
    public void postTask_ShouldReturnBadRequest_WhenUseCaseFails() throws Exception {
        // Arrange
        Authentication mockAuth = createMockAuthentication("user-1", "comm-1");
        PostTaskRequest requestBody = new PostTaskRequest();
        
        // Simular que el caso de uso falla
        when(createTaskUseCase.createTask(any(PostTaskCommand.class))).thenReturn(false);

        // Act & Assert
        mockMvc.perform(post("/api/tasks")
                .with(authentication(mockAuth))
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestBody)))
                .andExpect(status().isBadRequest()) // 400 Bad Request
                .andExpect(content().string("Failed to create task"));
    }

    @Test
    public void getTasks_ShouldReturnTasksForCommunity() throws Exception {
        // Arrange
        String communityId = "community-B";
        Authentication mockAuth = createMockAuthentication("user-2", communityId);

        // Simular la respuesta del caso de uso
        List<Task> tasks = List.of(
                new Task("t1", "Task 1", "Desc 1", "c1", null, communityId, TaskStatus.OPEN),
                new Task("t2", "Task 2", "Desc 2", "c2", "user-2", communityId, TaskStatus.CLAIMED)
        );
        GetTasksResponse mockResponse = new GetTasksResponse(tasks);
        
        when(getTasksUseCase.getTasks(communityId)).thenReturn(mockResponse);

        // Act & Assert
        mockMvc.perform(get("/api/tasks")
                .with(authentication(mockAuth)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                // Verificar la estructura y el contenido del JSON
                .andExpect(jsonPath("$.tasks").isArray())
                .andExpect(jsonPath("$.tasks.length()").value(2))
                .andExpect(jsonPath("$.tasks[0].id").value("t1"))
                .andExpect(jsonPath("$.tasks[0].title").value("Task 1"))
                .andExpect(jsonPath("$.tasks[1].status").value("CLAIMED"));

        // Verificar que se llamó al caso de uso con la communityId correcta
        verify(getTasksUseCase).getTasks(communityId);
    }

    @Test
    public void claimTask_ShouldClaimTask_WhenSuccessful() throws Exception {
        // Arrange
        String userId = "user-claimer-3";
        String taskId = "task-to-claim-123";
        Authentication mockAuth = createMockAuthentication(userId, "any-community");

        ArgumentCaptor<ClaimTaskCommand> commandCaptor = ArgumentCaptor.forClass(ClaimTaskCommand.class);

        // Simular que el caso de uso tiene éxito
        when(claimTaskUseCase.claimTask(commandCaptor.capture())).thenReturn(true);

        // Act & Assert
        mockMvc.perform(post("/api/tasks/{taskId}/claim", taskId)
                .with(authentication(mockAuth)))
                .andExpect(status().isOk()) // 200 OK
                .andExpect(content().string("Task claimed successfully"));

        // Verificar que el comando pasado al caso de uso es correcto
        ClaimTaskCommand capturedCommand = commandCaptor.getValue();
        assertEquals(taskId, capturedCommand.getTaskId());
        assertEquals(userId, capturedCommand.getClaimantId());
    }

    @Test
    public void claimTask_ShouldReturnBadRequest_WhenUseCaseFails() throws Exception {
        // Arrange
        String userId = "user-4";
        String taskId = "task-already-claimed-456";
        Authentication mockAuth = createMockAuthentication(userId, "any-community");

        // Simular que el caso de uso falla (ej. tarea no encontrada o ya reclamada)
        when(claimTaskUseCase.claimTask(any(ClaimTaskCommand.class))).thenReturn(false);

        // Act & Assert
        mockMvc.perform(post("/api/tasks/{taskId}/claim", taskId)
                .with(authentication(mockAuth)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Failed to claim task"));
    }
}