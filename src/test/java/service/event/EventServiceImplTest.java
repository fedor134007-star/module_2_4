package service.event;

import dto.EventDTO;
import dto.FileDTO;
import dto.UserDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import repository.hibernate.event.EventRepository;
import repository.hibernate.file.FileRepository;
import repository.hibernate.user.UserRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EventServiceImplTest {

    @Mock
    private EventRepository eventRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private FileRepository fileRepository;

    @InjectMocks
    private EventServiceImpl eventService;

    private EventDTO testEventDTO;
    private UserDTO testUserDTO;
    private FileDTO testFileDTO;

    @BeforeEach
    void setUp() {
        testUserDTO = new UserDTO();
        testUserDTO.setId(1L);
        testUserDTO.setName("John Doe");

        testFileDTO = new FileDTO();
        testFileDTO.setId(1L);
        testFileDTO.setName("test.txt");

        testEventDTO = new EventDTO();
        testEventDTO.setId(1L);
        testEventDTO.setUserId(1L);
        testEventDTO.setFile(testFileDTO);
    }

    @Test
    void create_ShouldReturnSavedEvent_WhenUserAndFileExist() {
        // given
        when(userRepository.getById(1L)).thenReturn(Optional.of(testUserDTO));
        when(fileRepository.getById(1L)).thenReturn(Optional.of(testFileDTO));
        when(eventRepository.save(any(EventDTO.class))).thenReturn(Optional.of(testEventDTO));

        // when
        EventDTO result = eventService.create(testEventDTO);

        // then
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        verify(userRepository, times(1)).getById(1L);
        verify(fileRepository, times(1)).getById(1L);
        verify(eventRepository, times(1)).save(testEventDTO);
    }

    @Test
    void create_ShouldThrowException_WhenUserNotFound() {
        // given
        when(userRepository.getById(1L)).thenReturn(Optional.empty());
        when(fileRepository.getById(1L)).thenReturn(Optional.of(testFileDTO));

        // when & then
        assertThrows(NullPointerException.class, () -> eventService.create(testEventDTO));
        verify(eventRepository, never()).save(any());
    }

    @Test
    void create_ShouldThrowException_WhenFileNotFound() {
        // given
        when(userRepository.getById(1L)).thenReturn(Optional.of(testUserDTO));
        when(fileRepository.getById(1L)).thenReturn(Optional.empty());

        // when & then
        assertThrows(NullPointerException.class, () -> eventService.create(testEventDTO));
        verify(eventRepository, never()).save(any());
    }

    @Test
    void create_ShouldReturnNull_WhenSaveFails() {
        // given
        when(userRepository.getById(1L)).thenReturn(Optional.of(testUserDTO));
        when(fileRepository.getById(1L)).thenReturn(Optional.of(testFileDTO));
        when(eventRepository.save(any(EventDTO.class))).thenReturn(Optional.empty());

        // when
        EventDTO result = eventService.create(testEventDTO);

        // then
        assertThat(result).isNull();
        verify(eventRepository, times(1)).save(testEventDTO);
    }

    @Test
    void getById_ShouldReturnEvent_WhenExists() {
        // given
        when(eventRepository.getById(1L)).thenReturn(Optional.of(testEventDTO));

        // when
        EventDTO result = eventService.getById(1L);

        // then
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        verify(eventRepository, times(1)).getById(1L);
    }

    @Test
    void getById_ShouldReturnNull_WhenNotExists() {
        // given
        when(eventRepository.getById(999L)).thenReturn(Optional.empty());

        // when
        EventDTO result = eventService.getById(999L);

        // then
        assertThat(result).isNull();
        verify(eventRepository, times(1)).getById(999L);
    }

    @Test
    void getAll_ShouldReturnListOfEvents() {
        // given
        List<EventDTO> events = Arrays.asList(testEventDTO, new EventDTO());
        when(eventRepository.getAll()).thenReturn(events);

        // when
        List<EventDTO> result = eventService.getAll();

        // then
        assertThat(result).hasSize(2);
        verify(eventRepository, times(1)).getAll();
    }

    @Test
    void delete_ShouldReturnTrue_WhenSuccessful() {
        // given
        when(eventRepository.delete(1L)).thenReturn(true);

        // when
        boolean result = eventService.delete(1L);

        // then
        assertTrue(result);
        verify(eventRepository, times(1)).delete(1L);
    }

    @Test
    void delete_ShouldReturnFalse_WhenFails() {
        // given
        when(eventRepository.delete(999L)).thenReturn(false);

        // when
        boolean result = eventService.delete(999L);

        // then
        assertFalse(result);
        verify(eventRepository, times(1)).delete(999L);
    }
}