package service.user;

import dto.UserDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import repository.hibernate.user.UserRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    private UserDTO testUserDTO;

    @BeforeEach
    void setUp() {
        testUserDTO = new UserDTO();
        testUserDTO.setId(1L);
        testUserDTO.setName("John Doe");
    }

    @Test
    void create_ShouldReturnSavedUser_WhenValidUser() {
        // given
        when(userRepository.save(any(UserDTO.class))).thenReturn(Optional.of(testUserDTO));

        // when
        UserDTO result = userService.create(testUserDTO);

        // then
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getName()).isEqualTo("John Doe");
        verify(userRepository, times(1)).save(testUserDTO);
    }

    @Test
    void create_ShouldReturnNull_WhenRepositoryFails() {
        // given
        when(userRepository.save(any(UserDTO.class))).thenReturn(Optional.empty());

        // when
        UserDTO result = userService.create(testUserDTO);

        // then
        assertThat(result).isNull();
        verify(userRepository, times(1)).save(testUserDTO);
    }

    @Test
    void update_ShouldReturnUpdatedUser() {
        // given
        UserDTO updatedUser = new UserDTO();
        updatedUser.setId(1L);
        updatedUser.setName("Jane Doe");
        when(userRepository.update(any(UserDTO.class))).thenReturn(updatedUser);

        // when
        UserDTO result = userService.update(updatedUser);

        // then
        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo("Jane Doe");
        verify(userRepository, times(1)).update(updatedUser);
    }

    @Test
    void getById_ShouldReturnUser_WhenExists() {
        // given
        when(userRepository.getById(1L)).thenReturn(Optional.of(testUserDTO));

        // when
        UserDTO result = userService.getById(1L);

        // then
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        verify(userRepository, times(1)).getById(1L);
    }

    @Test
    void getById_ShouldReturnNull_WhenNotExists() {
        // given
        when(userRepository.getById(999L)).thenReturn(Optional.empty());

        // when
        UserDTO result = userService.getById(999L);

        // then
        assertThat(result).isNull();
        verify(userRepository, times(1)).getById(999L);
    }

    @Test
    void getAll_ShouldReturnListOfUsers() {
        // given
        List<UserDTO> users = Arrays.asList(testUserDTO, new UserDTO());
        when(userRepository.getAll()).thenReturn(users);

        // when
        List<UserDTO> result = userService.getAll();

        // then
        assertThat(result).hasSize(2);
        verify(userRepository, times(1)).getAll();
    }

    @Test
    void delete_ShouldReturnTrue_WhenSuccessful() {
        // given
        when(userRepository.delete(1L)).thenReturn(true);

        // when
        boolean result = userService.delete(1L);

        // then
        assertTrue(result);
        verify(userRepository, times(1)).delete(1L);
    }

    @Test
    void delete_ShouldReturnFalse_WhenFails() {
        // given
        when(userRepository.delete(999L)).thenReturn(false);

        // when
        boolean result = userService.delete(999L);

        // then
        assertFalse(result);
        verify(userRepository, times(1)).delete(999L);
    }
}