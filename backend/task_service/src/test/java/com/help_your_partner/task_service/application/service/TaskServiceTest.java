package com.help_your_partner.task_service.application.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.ArgumentCaptor;

import com.help_your_partner.task_service.application.dto.ClaimTaskCommand;
import com.help_your_partner.task_service.application.dto.GetTasksResponse;
import com.help_your_partner.task_service.application.dto.PostTaskCommand;
import com.help_your_partner.task_service.domain.model.Task;
import com.help_your_partner.task_service.domain.model.TaskStatus;
import com.help_your_partner.task_service.domain.port.out.TaskRepository;

import java.util.List;
import java.util.Optional;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TaskService taskService;


    @Test
    public void testCreateTask_Success() {
        // Arrange
        PostTaskCommand command = new PostTaskCommand(
                "Test Task",
                "This is a test task.",
                "creator1",
                "community1"
        );


        // Capturador para verificar la tarea que se guarda
        ArgumentCaptor<Task> taskCaptor = ArgumentCaptor.forClass(Task.class);

        when(taskRepository.save(taskCaptor.capture())).thenReturn(Optional.of(new Task()));

        // Act
        boolean result = taskService.createTask(command);

        // Assert
        assertTrue(result, "El método createTask debería devolver true si el guardado es exitoso");

        // Verificar que el repositorio fue llamado
        verify(taskRepository).save(any(Task.class));

        // Verificar el contenido de la tarea guardada
        Task capturedTask = taskCaptor.getValue();
        assertEquals("Test Task", capturedTask.getTitle());
        assertEquals("This is a test task.", capturedTask.getDescription());
        assertEquals("creator1", capturedTask.getCreatorId());
        assertEquals("community1", capturedTask.getCommunityId());
        assertNull(capturedTask.getClaimantId());
        assertEquals(TaskStatus.OPEN, capturedTask.getStatus());
    }

    @Test
    public void testCreateTask_RepositorySaveFails() {
        // Arrange
        PostTaskCommand command = new PostTaskCommand(
                "Test Task",
                "This is a test task.",
                "creator1",
                "community1"
        );

        // Simular que el guardado en el repositorio falla
        when(taskRepository.save(any(Task.class))).thenReturn(Optional.empty());

        // Act
        boolean result = taskService.createTask(command);

        // Assert
        assertFalse(result, "El método createTask debería devolver false si el guardado falla");
        verify(taskRepository).save(any(Task.class)); // Verificar que se intentó guardar
    }

    @Test
    public void testClaimTask_Success() {
        // Arrange
        String taskId = "task-123";
        String claimantId = "claimer-007";
        ClaimTaskCommand command = new ClaimTaskCommand(taskId, claimantId);

        // Crear una tarea simulada que existe en la BBDD
        Task existingTask = new Task(taskId, "Task to claim", "Desc", "creator1", null, "community1");
        existingTask.setStatus(TaskStatus.OPEN); // Asegurarse de que está ABIERTA

        // Simular la búsqueda
        when(taskRepository.findById(taskId)).thenReturn(existingTask);

        // Simular el guardado exitoso de la tarea actualizada
        when(taskRepository.save(any(Task.class))).thenReturn(Optional.of(existingTask));

        // Capturador para verificar la tarea actualizada
        ArgumentCaptor<Task> taskCaptor = ArgumentCaptor.forClass(Task.class);

        // Act
        boolean result = taskService.claimTask(command);

        // Assert
        assertTrue(result, "Debería poder reclamar una tarea OPEN");

        // Verificar que se llamó a findById y save
        verify(taskRepository).findById(taskId);
        verify(taskRepository).save(taskCaptor.capture());

        // Verificar que la tarea capturada tiene los nuevos valores
        Task savedTask = taskCaptor.getValue();
        assertEquals(TaskStatus.CLAIMED, savedTask.getStatus());
        assertEquals(claimantId, savedTask.getClaimantId());
    }

    @Test
    public void testClaimTask_TaskNotFound() {
        // Arrange
        String taskId = "task-non-existent";
        ClaimTaskCommand command = new ClaimTaskCommand(taskId, "claimer-007");

        // Simular que la tarea no se encuentra (devuelve null)
        when(taskRepository.findById(taskId)).thenReturn(null);

        // Act
        boolean result = taskService.claimTask(command);

        // Assert
        assertFalse(result, "No se puede reclamar una tarea que no existe");
        verify(taskRepository).findById(taskId); // Verificar que se buscó
        verify(taskRepository, never()).save(any()); // Verificar que NUNCA se intentó guardar
    }

    @Test
    public void testClaimTask_TaskAlreadyClaimed() {
        // Arrange
        String taskId = "task-456";
        ClaimTaskCommand command = new ClaimTaskCommand(taskId, "claimer-007");

        // Crear una tarea simulada que ya está RECLAMADA
        Task existingTask = new Task(taskId, "Already claimed", "Desc", "creator1", "other-claimer", "community1");
        existingTask.setStatus(TaskStatus.CLAIMED);

        // Simular la búsqueda
        when(taskRepository.findById(taskId)).thenReturn(existingTask);

        // Act
        boolean result = taskService.claimTask(command);

        // Assert
        assertFalse(result, "No se puede reclamar una tarea que ya está CLAIMED");
        verify(taskRepository).findById(taskId);
        verify(taskRepository, never()).save(any()); // No se debe guardar si no está OPEN
    }
    
    @Test
    public void testClaimTask_TaskCompleted() {
        // Arrange
        String taskId = "task-789";
        ClaimTaskCommand command = new ClaimTaskCommand(taskId, "claimer-007");

        // Crear una tarea simulada que está COMPLETADA
        Task existingTask = new Task(taskId, "Completed task", "Desc", "creator1", "other-claimer", "community1");
        existingTask.setStatus(TaskStatus.COMPLETED); // Asumiendo que existe este estado

        // Simular la búsqueda
        when(taskRepository.findById(taskId)).thenReturn(existingTask);

        // Act
        boolean result = taskService.claimTask(command);

        // Assert
        assertFalse(result, "No se puede reclamar una tarea que está COMPLETED");
        verify(taskRepository).findById(taskId);
        verify(taskRepository, never()).save(any());
    }

    @Test
    public void testClaimTask_SaveFailsAfterClaim() {
        // Arrange
        String taskId = "task-123";
        ClaimTaskCommand command = new ClaimTaskCommand(taskId, "claimer-007");

        Task existingTask = new Task(taskId, "Task to claim", "Desc", "creator1", null, "community1");
        existingTask.setStatus(TaskStatus.OPEN);

        when(taskRepository.findById(taskId)).thenReturn(existingTask);

        // Simular que el guardado falla (devuelve Optional.empty())
        when(taskRepository.save(any(Task.class))).thenReturn(Optional.empty());

        // Act
        boolean result = taskService.claimTask(command);

        // Assert
        assertFalse(result, "Debería devolver false si el guardado falla después de reclamar");
        verify(taskRepository).findById(taskId);
        verify(taskRepository).save(any(Task.class)); // Se intentó guardar
    }

    @Test
    public void testGetTasks_ReturnsTasks() {
        // Arrange
        String communityId = "community1";
        List<Task> mockTasks = List.of(
                new Task("t1", "Task 1", "d1", "c1", null, communityId),
                new Task("t2", "Task 2", "d2", "c2", "claimant", communityId)
        );

        // Simular la respuesta del repositorio
        when(taskRepository.findByCommunityId(communityId)).thenReturn(mockTasks);

        // Act
        GetTasksResponse response = taskService.getTasks(communityId);

        // Assert
        assertNotNull(response);
        assertEquals(2, response.getTasks().size());
        assertEquals(mockTasks, response.getTasks()); // La lista debe ser la misma
        verify(taskRepository).findByCommunityId(communityId);
    }

    @Test
    public void testGetTasks_ReturnsEmptyList() {
        // Arrange
        String communityId = "community-empty";

        // Simular una respuesta vacía del repositorio
        when(taskRepository.findByCommunityId(communityId)).thenReturn(Collections.emptyList());

        // Act
        GetTasksResponse response = taskService.getTasks(communityId);

        // Assert
        assertNotNull(response);
        assertTrue(response.getTasks().isEmpty(), "La lista de tareas debería estar vacía");
        verify(taskRepository).findByCommunityId(communityId);
    }
}